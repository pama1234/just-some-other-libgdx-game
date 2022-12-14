/****************************************************************************
 ** Copyright (c) 2013-2018 Mazatech S.r.l. All rights reserved.
 ** 
 ** Redistribution and use in source and binary forms, with or without modification, are
 * permitted (subject to the limitations in the disclaimer below) provided that the following
 * conditions are met:
 ** 
 ** - Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 ** 
 ** - Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 ** 
 ** - Neither the name of Mazatech S.r.l. nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 ** 
 ** NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 ** OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 ** OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ** 
 ** For any information, please contact info@mazatech.com
 ** 
 ****************************************************************************/
package com.mazatech.amanithsvg.gamecards;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

// libGDX
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Timer;

// AmanithSVG
import com.mazatech.svgt.*;
import com.mazatech.gdx.*;

public class Game extends ApplicationAdapter implements InputProcessor{
    private OrthographicCamera _camera;
    private SpriteBatch _batch=null;
    // SVG background documents
    private SVGDocument[] _backgroundDocs= {null,null,null,null};
    // the current background (0..3)
    private int _backgroundIdx;
    private boolean _backgroundRegenerate=false;
    // the actual background texture
    private SVGTexture _backgroundTexture=null;
    // SVG atlas generator
    private SVGTextureAtlasGenerator _atlasGen=null;
    private SVGTextureAtlas _atlas=null;
    // the SVG scaler (for animal sprites)
    private SVGScaler _scaler;
    private float _spritesGenerationScale=-1.0f;
    // associate each animal type the respective texture region
    private Map<CardType,SVGTextureAtlasRegion> _animalsSprites=null;
    // the deck of cards
    private Card[] _cards=null;
    private Card _selectedCard0=null;
    private Card _selectedCard1=null;
    // while waiting (e.g. we are giving time to the player in order to memorize the wrong selected
    // animal couple) we "disable" user touch input
    private boolean _waiting=false;
    private static int[] CARDS_INDEXES_PORTRAIT= {0,1,2,3,4,5,6,7,8,9,10,11};
    private static int[] CARDS_INDEXES_LANDSCAPE= {9,6,3,0,10,7,4,1,11,8,5,2};
    private void generateAnimalSprites(int screenWidth,int screenHeight) {
        // calculate the scale factor according to the current window/screen resolution
        float scale=_scaler.scaleFactorCalc(screenWidth,screenHeight);
        // re-generate sprites just if needed (e.g. we don't want to generate them if device has been rotated)
        if(scale!=_spritesGenerationScale) {
            // dispose previous textures atlas
            if(_atlas!=null) {
                _atlas.dispose();
                _atlas=null;
            }
            // set generation scale (all other parameters have been set when instantiating the SVGTextureAtlasGenerator class)
            _atlasGen.setScale(scale);
            // do the real generation
            try {
                _atlas=_atlasGen.generateAtlas();
            }catch(SVGTextureAtlasGenerator.SVGTextureAtlasPackingException e) {
                Gdx.app.log("CardsGame","Some SVG elements cannot be packed!");
                Gdx.app.log("CardsGame","Specified maximum texture dimensions (in conjunction with specified scale factor), do not allow the packing of all SVG elements");
            }
            // empty the previous map
            _animalsSprites.clear();
            // now associate to each animal type the respective texture region
            for(SVGTextureAtlasPage page:_atlas.getPages()) {
                for(SVGTextureAtlasRegion region:page.getRegions()) {
                    _animalsSprites.put(CardType.fromName(region.getElemName()),region);
                }
            }
            _spritesGenerationScale=scale;
        }
    }
    private void generateBackground(int screenWidth,int screenHeight) {
        // destroy previous backgound texture
        if(_backgroundTexture!=null) {
            _backgroundTexture.dispose();
            _backgroundTexture=null;
        }
        _backgroundTexture=new SVGTexture(_backgroundDocs[_backgroundIdx],screenWidth,screenHeight,SVGColor.Clear,false);
    }
    private void disposeCards(int screenWidth,int screenHeight) {
        int[] cardsIndexes;
        int slotsPerRow,slotsPerColumn;
        SVGTextureAtlasRegion region=_animalsSprites.get(CardType.BackSide);
        int cardWidth=region.getRegionWidth();
        int cardHeight=region.getRegionWidth();
        if(screenWidth<=screenHeight) {
            // number of card slots in each dimension
            slotsPerRow=3;
            slotsPerColumn=4;
            cardsIndexes=Game.CARDS_INDEXES_PORTRAIT;
        }else {
            // number of card slots in each dimension
            slotsPerRow=4;
            slotsPerColumn=3;
            cardsIndexes=Game.CARDS_INDEXES_LANDSCAPE;
        }
        int ofsX=(int)Math.floor(screenWidth*0.05);
        int ofsY=(int)Math.floor(screenHeight*0.05);
        int horizSeparator=((screenWidth-(slotsPerRow*cardWidth)-(2*ofsX))/(slotsPerRow-1));
        int vertSeparator=((screenHeight-(slotsPerColumn*cardHeight)-(2*ofsY))/(slotsPerColumn-1));
        int cardIdx=0;
        for(int y=0;y<slotsPerColumn;++y) {
            for(int x=0;x<slotsPerRow;++x) {
                region=_animalsSprites.get(_cards[cardsIndexes[cardIdx]].animalType);
                _cards[cardsIndexes[cardIdx]].x=ofsX+(x*(cardWidth+horizSeparator));
                _cards[cardsIndexes[cardIdx]].y=ofsY+(y*(cardHeight+vertSeparator));
                _cards[cardsIndexes[cardIdx]].width=region.getRegionWidth();
                _cards[cardsIndexes[cardIdx]].height=region.getRegionHeight();
                cardIdx++;
            }
        }
    }
    private void selectCard(int screenX,int screenY) {
        for(Card card:_cards) {
            if(card.active) {
                // check if the card has been touched
                if((screenX>card.x)&&(screenX<(card.x+card.width))&&(screenY>card.y)&&(screenY<(card.y+card.height))) {
                    // card is already in the current selection
                    if((card!=_selectedCard0)&&(card!=_selectedCard1)) {
                        // select the first card
                        if(_selectedCard0==null) {
                            _selectedCard0=card;
                            // show card front face
                            _selectedCard0.backSide=false;
                        }else
                            // select the second card
                            if((_selectedCard1==null)&&(card!=_selectedCard0)) {
                                // show card front face
                                _selectedCard1=card;
                                _selectedCard1.backSide=false;
                                // if the couple does not match simply turn cards backside, else animate and hide them
                                if(_selectedCard0.animalType==_selectedCard1.animalType) {
                                    // good couple, wait some seconds
                                    _waiting=true;
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            _waiting=false;
                                            _selectedCard0.active=_selectedCard1.active=false;
                                            _selectedCard0=_selectedCard1=null;
                                            // check if current game is completed
                                            if(gameFinished()) {
                                                // start a new game: shuffle the cards deck and change the background
                                                startNewGame();
                                                // we postpone the background texture creation at the next render() call
                                                _backgroundRegenerate=true;
                                            }
                                        }
                                    },1.5f);
                                }else {
                                    // wrong couple, wait some seconds
                                    _waiting=true;
                                    Timer.schedule(new Timer.Task() {
                                        @Override
                                        public void run() {
                                            _waiting=false;
                                            _selectedCard0.backSide=_selectedCard1.backSide=true;
                                            _selectedCard0=_selectedCard1=null;
                                        }
                                    },1.5f);
                                }
                            }
                    }
                    break;
                }
            }
        }
    }
    private boolean gameFinished() {
        for(int i=0;i<_cards.length;++i) {
            if(_cards[i].active) {
                return false;
            }
        }
        // game is completed if all cards are inactive
        return true;
    }
    private void startNewGame() {
        CardType[] animalCouples=new CardType[_cards.length];
        // start with a random animal
        CardType currentAnimal=CardType.random();
        // generate animal couples
        for(int i=0;i<(animalCouples.length/2);++i) {
            animalCouples[i*2]=currentAnimal;
            animalCouples[(i*2)+1]=currentAnimal;
            currentAnimal=currentAnimal.next();
        }
        // shuffle couples
        Random rnd=new Random();
        int n=animalCouples.length;
        // Knuth shuffle
        while(n>1) {
            n--;
            int i=rnd.nextInt(n+1);
            CardType temp=animalCouples[i];
            animalCouples[i]=animalCouples[n];
            animalCouples[n]=temp;
        }
        // assign cards
        for(int i=0;i<_cards.length;++i) {
            // cards start as active and backside
            _cards[i].active=true;
            _cards[i].backSide=true;
            _cards[i].animalType=animalCouples[i];
        }
        // select a new background
        _backgroundIdx=(_backgroundIdx+1)%4;
        // no selection
        _selectedCard0=null;
        _selectedCard1=null;
        _waiting=false;
    }
    @Override
    public void create() {
        // let the game intercept input events
        Gdx.input.setInputProcessor(this);
        Gdx.gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        // initialize AmanithSVG
        SVGAssets.init();
        // the scaler will calculate the correct scaling factor, actual parameters say:
        // "We have created all the SVG files (that we are going to pack in atlas) so that, at 768 x 640 (the 'reference resolution'), they
        // do not need additional scaling (the last passed parameter value 1.0f is the basic scale relative to the 'reference resolution').
        // If the device has a different screen resolution, we want to scale SVG contents depending on the actual width and height (MatchWidthOrHeight), equally important (0.5f)"
        _scaler=new SVGScaler(768,640,SVGScalerMatchMode.MatchWidthOrHeight,0.5f,1.0f);
        // scale, maxTexturesDimension (take care of OpenGL and AmanithSVG limitations), border, pow2Textures, dilateEdgesFix, clearColor
        _atlasGen=new SVGTextureAtlasGenerator(1.0f,Math.min(SVGTextureUtils.getGlMaxTextureDimension(),AmanithSVG.svgtSurfaceMaxDimension()),1,false,false,SVGColor.Clear);
        // SVG file, explodeGroups, scale
        // NB: because 'animals.svg' has been designed for a 768 x 640 resolution (see the file header), we do not want to adjust the scale further (i.e. we pass 1.0f as additional scale factor)
        // Note that at the 768 x 640 'reference resolution', each animal sprite will have a dimension of 128 x 128: it will ALWAYS guarantee that we can easily place them on a 4 x 3 grid (landscape layout) or
        // on a 3 x 4 grid (portrait layout), REGARDLESS OF SCREEN RESOLUTION
        _atlasGen.add(Gdx.files.internal("svg/animals.svg"),true,1.0f);
        // create backgrounds documents
        _backgroundDocs[0]=SVGAssets.createDocument(Gdx.files.internal("svg/gameBkg1.svg"));
        _backgroundDocs[1]=SVGAssets.createDocument(Gdx.files.internal("svg/gameBkg2.svg"));
        _backgroundDocs[2]=SVGAssets.createDocument(Gdx.files.internal("svg/gameBkg3.svg"));
        _backgroundDocs[3]=SVGAssets.createDocument(Gdx.files.internal("svg/gameBkg4.svg"));
        for(int i=0;i<4;++i) {
            // backgrounds viewBox must cover the whole drawing surface, so we use SVGTMeetOrSlice.Slice (default is SVGTMeetOrSlice.Meet)
            _backgroundDocs[i].setAspectRatio(new SVGAlignment(SVGTAlign.XMidYMid,SVGTMeetOrSlice.Slice));
        }
        // select a random background
        _backgroundIdx=(int)(Math.random()*3.0f);
        // associate each animal type the respective texture region
        _animalsSprites=new HashMap<CardType,SVGTextureAtlasRegion>();
        // create cards array
        _cards=new Card[12];
        for(int i=0;i<_cards.length;++i) {
            _cards[i]=new Card();
        }
        // create the batch (used by 'render' function)
        _batch=new SpriteBatch();
        // setup orthographic camera
        _camera=new OrthographicCamera();
        _camera.setToOrtho(false,Gdx.graphics.getBackBufferWidth(),Gdx.graphics.getBackBufferHeight());
        _camera.update();
        // start a new game (i.e. select random cards and initilize them as 'backside')
        startNewGame();
    }
    @Override
    public void resize(int width,int height) {
        if((width>0)&&(height>0)) {
            // update OpenGL viewport
            Gdx.gl.glViewport(0,0,width,height);
            // update current projection matrix
            _camera.setToOrtho(false,width,height);
            _camera.update();
            // generate background
            generateBackground(width,height);
            // generate sprites
            generateAnimalSprites(width,height);
            // place cards on the screen
            disposeCards(width,height);
        }
    }
    @Override
    public void render() {
        int screenWidth=Gdx.graphics.getBackBufferWidth();
        int screenHeight=Gdx.graphics.getBackBufferHeight();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _batch.setProjectionMatrix(_camera.combined);
        // if we have completed the current game, we have to generate the new background texture
        // NB: we use this global flag because we cannot generate the background within the selectCard()
        // function. Textures MUST be generated from the OpenGL thread.
        if(_backgroundRegenerate) {
            _backgroundRegenerate=false;
            // generate background
            generateBackground(screenWidth,screenHeight);
        }
        _batch.begin();
        // draw the background (texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY)
        _batch.draw(_backgroundTexture,0.0f,0.0f,screenWidth,screenHeight,0,0,_backgroundTexture.getWidth(),_backgroundTexture.getHeight(),false,true);
        // draw cards
        for(int i=0;i<_cards.length;++i) {
            // draw active cards only
            if(_cards[i].active) {
                SVGTextureAtlasRegion region=_animalsSprites.get(_cards[i].backSide?CardType.BackSide:_cards[i].animalType);
                if(region!=null) {
                    _batch.draw(region,_cards[i].x,_cards[i].y);
                }
            }
        }
        _batch.end();
    }
    @Override
    public void dispose() {
        for(int i=0;i<4;++i) {
            _backgroundDocs[i].dispose();
        }
        _batch.dispose();
        _backgroundTexture.dispose();
        _atlas.dispose();
        _atlasGen.dispose();
        SVGAssets.dispose();
    }
    @Override
    public boolean touchDown(int screenX,int screenY,int pointer,int button) {
        // ignore if its not left mouse button or first touch pointer
        if((button!=Input.Buttons.LEFT)||(pointer>0)) {
            return false;
        }
        if(!_waiting) {
            // NB: 2D coordinates (screenX, screenY) relative to the upper left corner of the screen, with
            // the positive x-axis pointing to the right and the y-axis pointing downward.
            // in order to be consistent with the SpriteBatch.draw coordinates system, we flip y coordinate
            selectCard(screenX,Gdx.graphics.getBackBufferHeight()-screenY);
        }
        return true;
    }
    @Override
    public boolean touchUp(int screenX,int screenY,int pointer,int button) {
        return ((button!=Input.Buttons.LEFT)||(pointer>0))?false:true;
    }
    @Override
    public boolean touchDragged(int screenX,int screenY,int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX,int screenY) {
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean scrolled(float amountX,float amountY) {
        // TODO Auto-generated method stub
        return false;
    }
}
