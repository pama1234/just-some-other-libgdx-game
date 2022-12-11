package pama1234.gdx.game.app;

import static pama1234.gdx.game.BackgroundMusic.moonlightSonata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.BackgroundMusic;
import pama1234.gdx.game.util.GifDecoder;
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
    BackgroundMusic.load_0001();
    moonlightSonata.play();
    // alsoSprachZarathustra.play();
    //---
    background=FileUtil.loadTextureRegion("image/background.png");
    earth=GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,
    Gdx.files.internal("image/earth.gif").read());
    // Gdx.files.internal("image/earth.gif").read());
  }
  @Override
  public void update() {
    shaderUpdate();
    time+=frameRate;
  }
  public void shaderUpdate() {}
  @Override
  public void mousePressed(MouseInfo info) {
    // if(moonlightSonata.isPlaying()) moonlightSonata.pause();
    // else moonlightSonata.play();
  }
  @Override
  public void displayWithCam() {
    image(earth.getKeyFrame(time),0,0);
    // image(background,0,0);
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
}
