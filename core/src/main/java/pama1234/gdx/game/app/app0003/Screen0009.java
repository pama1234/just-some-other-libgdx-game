package pama1234.gdx.game.app.app0003;

import static pama1234.gdx.game.asset.MusicAsset.moonlightSonata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.game.util.gif.GifDecoder;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0009 extends ScreenCore2D{
  TextureRegion background;
  Animation<TextureRegion> earth;
  float time;
  @Override
  public void setup() {
    noStroke();
    // moonlightSonata=Gdx.audio.newMusic(Gdx.files.internal("music/Beethoven-Moonlight-Sonata.mp3"));
    MusicAsset.moonlightSonata=MusicAsset.load("Beethoven-Moonlight-Sonata.mp3");
    moonlightSonata.play();
    // alsoSprachZarathustra.play();
    //---
    background=FileUtil.loadTextureRegion("image/background.png");
    earth=GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,
      Gdx.files.internal("image/bigEarth.gif").read());
    // Gdx.files.internal("image/earth.gif").read());
  }
  @Override
  public void update() {
    // shaderUpdate();
    time+=frameRate/4;
  }
  // public void shaderUpdate() {}
  @Override
  public void mousePressed(MouseInfo info) {
    // if(moonlightSonata.isPlaying()) moonlightSonata.pause();
    // else moonlightSonata.play();
  }
  @Override
  public void displayWithCam() {
    TextureRegion kf=earth.getKeyFrame(time);
    image(kf,0,0);
    // text(kf.getRegionWidth()+" "+kf.getRegionHeight(),0,0);
    // text(kf.getU2()+" "+kf.getV2(),0,textSize());
    // image(background,0,0);
    // SpriteBatch.createDefaultShader();
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
