package pama1234.gdx.game.cgj.app.app0001;

import static pama1234.gdx.game.cgj.ui.generator.InfoUtil.info0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import pama1234.gdx.game.cgj.app.app0002.MainMenu;
import pama1234.gdx.game.cgj.ui.generator.UiGenerator;
import pama1234.gdx.game.cgj.util.ControlBindUtil;
import pama1234.gdx.game.cgj.world.World0001;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.wrapper.DisplayEntity;

/**
 * 3D 粒子系统 单机模式
 */
public class Screen0033 extends ScreenCore3D{
  public MainMenu mainMenu;
  public ControlBindUtil controlBind;
  public World0001 world;
  public boolean displayHint;
  public Decal infoD;
  public Decal logo;
  public boolean tempTest;//TODO
  public ConfigInfo configInfo;
  public static class GraphicsData{
    public Graphics g;
    public TextureRegion tr;
    public GraphicsData(Graphics g,TextureRegion tr) {
      this.g=g;
      this.tr=tr;
    }
  }
  public static class DecalData{//TODO
    public Decal decal;
    public int layer;
    public DecalData(Decal g,int layer) {
      this.decal=g;
      this.layer=layer;
    }
  }
  public Screen0033(MainMenu mainMenu) {
    this.mainMenu=mainMenu;
  }
  @Override
  public void setup() {
    setupCamera();
    backgroundColor(0);
    textColor(255);
    //---
    controlBind=new ControlBindUtil();
    world=new World0001(this);
    world.init();
    infoD=Decal.newDecal(drawInfoImage(),true);
    logo=Decal.newDecal(256,256,new TextureRegion(FileUtil.loadTexture("logo/logo-ingame.png")),true);
    logo.setPosition(0,-512,0);
    //TODO
    if(isAndroid) {
      // if(true) {
      buttons=UiGenerator.genButtons_0001(this);
      for(Button<?> e:buttons) centerScreen.add.add(e);
    }
    centerScreen.add.add(configInfo=new ConfigInfo(this));
    centerScreen.add.add(world);
    centerCam.add.add(new DisplayEntity(world::displayCam));
  }
  public TextureRegion drawInfoImage() {
    Graphics tg=new Graphics(this,360,16*info0001.length);
    tg.beginShape();
    for(int i=0;i<info0001.length;i++) text(info0001[i],0,16*i);
    tg.endShape();
    TextureRegion tr=new TextureRegion(tg.texture);
    return tr;
  }
  public void setupCamera() {
    cam.point.f=0.1f;//TODO
    cam3d.viewDir.f=0.1f;
    cam.point.set(0,0,-240);
  }
  @Override
  public void update() {}
  @Override
  public void displayWithCam() {
    // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    // Gdx.gl20.glDepthMask(false);
    // world.displayCam();
    if(displayHint) decal(infoD);
    logo.lookAt(cam.camera.position,cam.camera.up);
    decal(logo);
    flushDecal();
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {}
  @Override
  public void keyPressed(char key,int keyCode) {
    if(controlBind.isKey(ControlBindUtil.addViewSpeed,keyCode)) {
      cam3d.viewSpeed+=1/4f;
      if(cam3d.viewSpeed>8) cam3d.viewSpeed=8;
    }
    if(controlBind.isKey(ControlBindUtil.subViewSpeed,keyCode)) {
      cam3d.viewSpeed-=1/4f;
      if(cam3d.viewSpeed<0) cam3d.viewSpeed=0;
    }
    if(key=='R') {
      float tmd=multDist;
      multDist+=1/4f;
      if(multDist>8) multDist=8;
      cam.point.set(cam.point.des.x/tmd*multDist,cam.point.des.y/tmd*multDist,cam.point.des.z/tmd*multDist);
    }
    if(key=='F') {
      float tmd=multDist;
      multDist-=1/4f;
      if(multDist<1/4f) multDist=1/4f;
      cam.point.set(cam.point.des.x/tmd*multDist,cam.point.des.y/tmd*multDist,cam.point.des.z/tmd*multDist);
    }
    if(key=='H') displayHint=!displayHint;
    if(key=='N') {//TODO
      float tvd=cam3d.viewDist();
      tvd/=2;
      if(tvd<2) tvd=2;
      cam3d.viewDist(tvd);
    }
    if(key=='M') {
      float tvd=cam3d.viewDist();
      tvd*=2;
      if(tvd>2048) tvd=2048;
      cam3d.viewDist(tvd);
    }
    if(isAndroid&&key=='T') fullSettings=!fullSettings;//TODO
    if(key=='P') tempTest=!tempTest;
  }
  @Override
  public void dispose() {
    super.dispose();
  }
}