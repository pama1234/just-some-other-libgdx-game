package pama1234.gdx.game.app.app0001;

import static pama1234.gdx.game.ui.generator.InfoUtil.info0001;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import pama1234.gdx.game.state.state0005.State0005Util;
import pama1234.gdx.game.state.state0005.State0005Util.StateCenter0005;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.util.ControlBindUtil;
import pama1234.gdx.game.util.ParticleScreen3D;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.element.Graphics;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("3D 粒子系统 单机模式")
public class Screen0001 extends ParticleScreen3D{
  // public MainMenu mainMenu;
  
  public boolean displayHint;
  public Decal infoD;
  public Decal logo;
  public boolean tempTest;//TODO
  public ConfigInfo configInfo;
  {
    // isAndroid=true;
  }
  @Override
  public void setup() {
    setupCamera();
    backgroundColor(0);
    textColor(255);
    
    stateCenter=new StateCenter0005(this);
    State0005Util.loadState0005(this,stateCenter);
    // state(stateCenter.game);
    state(stateCenter.startMenu);
    controlBind=new ControlBindUtil();
    infoD=Decal.newDecal(drawInfoImage(),true);
    logo=Decal.newDecal(256,256,new TextureRegion(FileUtil.loadTexture("logo/logo-ingame.png")),true);
    logo.setPosition(0,-512,0);
    //TODO
    if(isAndroid) {
      var buttons=UiGenerator.genButtons_0001(this);
      centerScreenAddAll(buttons);
      addAndroidCam3DButtons();
    }
    centerScreen.add.add(configInfo=new ConfigInfo(this));
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
    // cam3d.camera.far=600f;
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