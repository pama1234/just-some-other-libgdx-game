package pama1234.gdx.game.app.app0003;

import static pama1234.gdx.game.asset.MusicAsset.moonlightSonata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.asset.MusicAsset;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.gif.GifDecoder;
import pama1234.gdx.util.info.MouseInfo;

public class Screen0009 extends ScreenCore2D{
  TextureRegion background;
  Animation<TextureRegion> earth;
  float time;
  @Override
  public void setup() {
    noStroke();
    MusicAsset.moonlightSonata=MusicAsset.load("Beethoven-Moonlight-Sonata.mp3");
    moonlightSonata.play();
    //---
    background=FileUtil.loadTextureRegion("image/background.png");
    GifDecoder gdec=new GifDecoder();
    gdec.read(Gdx.files.internal("image/bigEarth.gif").read());
    earth=gdec.getAnimation(Animation.PlayMode.LOOP);
  }
  @Override
  public void update() {
    time+=frameRate/4;
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {
    TextureRegion kf=earth.getKeyFrame(time);
    image(kf,0,0);
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
