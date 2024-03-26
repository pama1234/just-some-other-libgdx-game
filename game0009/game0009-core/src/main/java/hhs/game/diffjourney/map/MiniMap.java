package hhs.game.diffjourney.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.EntityLayers;
import hhs.gdx.hsgame.screens.LayersScreen;
import hhs.gdx.hsgame.tools.Resource;

public class MiniMap extends BasicEntity implements EntityLayers.Stackable{

  FitViewport viewport;
  OrthographicCamera localCam;
  AvailableMap map;
  Pixmap pixmap;
  Texture pixTexture;
  public int sr;
  public int mapBound=50,blockSize=50;

  public MiniMap(AvailableMap map) {
    this.map=map;
    pos.set(Resource.width-Resource.u*4,Resource.height-Resource.u*4);
    size.set(Resource.u*4,Resource.u*4);
    localCam=new OrthographicCamera(Resource.width,Resource.height);
    localCam.position.set(Resource.width/2,Resource.height/2,0);
    viewport=new FitViewport(size.x,size.y,localCam);
    pixmap=new Pixmap(mapBound,mapBound,Pixmap.Format.RGBA8888);
    pixTexture=new Texture(pixmap);
  }
  @Override
  public void setSize(float w,float h) {
    super.setSize(w,h);
    viewport.setWorldSize(size.x,size.y);
  }

  @Override
  public EntityLayers.Layer getLayer() {
    return EntityLayers.Layer.FRONT;
  }

  @Override
  public void dispose() {}

  int suit(float p) {
    return ((int)p/blockSize)*blockSize;
  }
  public Vector2 tmp=new Vector2(Vector2.Zero);
  @Override
  public void update(float delta) {
    super.update(delta);
    localCam.update();
    int i,j,xindex=0,yindex=0;
    sr=mapBound*blockSize/2;
    int sx=suit(cam.position.x+sr),sy=suit(cam.position.y+sr);
    pixmap.setColor(0,0,0,0);
    pixmap.fill();
    for(j=suit(cam.position.y-sr);j<=sy;j+=blockSize,yindex++) {
      for(i=suit(cam.position.x-sr),xindex=0;i<=sx;i+=blockSize,xindex++) {
        if(map instanceof BasicEntity be) switch(map.getBlock((i-(int)be.pos.x)/blockSize,(j-(int)be.pos.y)/blockSize)) {
          case '#':
            pixmap.setColor(Color.WHITE);
            break;
          default:
            pixmap.setColor(0,0,0,0);
            break;
        }
        pixmap.drawPixel(xindex,mapBound-1-yindex);
      }
    }
    pixmap.setColor(Color.RED);
    for(BasicEntity be:((LayersScreen)screen).layers.middle.sons) {
      tmp.set(be.pos).sub(cam.position.x,cam.position.y).sub(cam.position.x-blockSize*mapBound/2,cam.position.y-blockSize*mapBound/2);
      tmp.scl(1/blockSize);
      if(tmp.x>=0&&tmp.y>=0&&mapBound>tmp.x&&mapBound>tmp.y) {
        pixmap.drawPixel((int)tmp.x,(int)tmp.y);
      }
    }
    pixmap.setColor(Color.GREEN);
    pixmap.drawPixel(mapBound/2,mapBound/2);
    //    for(int i = 0; i < mapBound; ++i) {
    //    	for(int j = 0; j < mapBound; ++j) {
    //    		switch(map.getBlock(i,j)){
    //          case '#':
    //          pixmap.setColor(Color.WHITE);
    //          break;
    //          default:
    //          pixmap.setColor(0,0,0,0);
    //          break;
    //        }
    //        pixmap.drawPixel(i,mapBound-1-j);
    //    	}
    //    }
  }

  @Override
  public void render(SpriteBatch batch) {
    pixTexture.draw(pixmap,0,0);
    //viewport.apply();
    batch.setProjectionMatrix(localCam.combined);
    batch.draw(pixTexture,pos.x,pos.y,size.x,size.y);
    batch.setProjectionMatrix(cam.combined);
  }

  @Override
  public void debugDraw(ShapeRenderer sr) {
    sr.setProjectionMatrix(localCam.combined);
    super.debugDraw(sr);
    sr.setProjectionMatrix(cam.combined);
    // TODO: Implement this method
  }

}
