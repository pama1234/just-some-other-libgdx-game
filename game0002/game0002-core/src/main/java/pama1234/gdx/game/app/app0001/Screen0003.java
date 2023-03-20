package pama1234.gdx.game.app.app0001;

import static com.badlogic.gdx.math.MathUtils.map;
import static pama1234.gdx.game.ui.generator.InfoUtil.info0001;
import static pama1234.math.UtilMath.dist;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.SocketHints;

import pama1234.game.app.server.server0001.game.net.SocketData0001;
import pama1234.game.app.server.server0001.game.net.SocketData0001.Token;
import pama1234.game.app.server.server0001.game.net.data.Client0001Core;
import pama1234.game.app.server.server0001.game.net.io.ClientRead;
import pama1234.game.app.server.server0001.game.net.io.ClientWrite;
import pama1234.game.app.server.server0001.particle.Var;
import pama1234.gdx.game.net.SocketWrapperGDX;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.game.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.util.ClientPlayerCenter3D;
import pama1234.gdx.game.util.ControllerClientPlayer3D;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.Tools;
import pama1234.util.net.NetAddressInfo;

/**
 * 3D 粒子系统 联机 客户端
 */
public class Screen0003 extends ScreenCore3D{
  public SocketData0001 clientSocket;
  public Client0001Core clientCore;
  //---
  public ClientRead clientRead;
  public ClientWrite clientWrite;
  //---
  @Deprecated
  public ClientPlayerCenter3D playerCenter;//TODO
  public ControllerClientPlayer3D yourself;
  public ArrayList<ArrayList<GraphicsData>> graphicsList;
  public ArrayList<DecalData> decals;
  public boolean doUpdate;
  public Vector3 posCache=new Vector3();
  public static final int layerSize=3;
  public static final int gsize=8;
  public boolean displayHint;
  public Decal infoD;
  public Decal logo;
  public ConfigInfo configInfo;
  public int tempCellSize=128;
  public int tempColorSize=12;
  public int tempSize=tempCellSize*tempColorSize;
  public static class GraphicsData{
    public Graphics g;
    public TextureRegion tr;
    public GraphicsData(Graphics g,TextureRegion tr) {
      this.g=g;
      this.tr=tr;
    }
  }
  public static class DecalData{
    public Decal decal;
    public int layer;
    public DecalData(Decal g,int layer) {
      this.decal=g;
      this.layer=layer;
    }
  }
  public int tgsizeF(int k) {
    // return (int)pow(gsize,(k+2)*0.3f);
    return 8*(k*2+1);
  }
  @Override
  public void setup() {
    cam.point.set(0,0,-320);
    backgroundColor(0);
    textColor(255);
    clientCore=new Client0001Core(tempSize,"pama"+String.format("%04d",(int)(random(0,10000))));
    playerCenter=new ClientPlayerCenter3D(this);
    yourself=new ControllerClientPlayer3D(this,clientCore.yourself);
    //---
    dataServerInfo=new NetAddressInfo("192.168.2.105",12347);
    //---
    SocketHints tsh=new SocketHints();
    tsh.connectTimeout=10000;
    tsh.socketTimeout=5000;
    tsh.keepAlive=true;
    tsh.performancePrefConnectionTime=0;
    tsh.performancePrefLatency=2;
    tsh.performancePrefBandwidth=1;
    //---
    clientSocket=new SocketData0001(new Token(yourself.data.name()),new SocketWrapperGDX(Gdx.net.newClientSocket(Protocol.TCP,dataServerInfo.addr,dataServerInfo.port,tsh)));
    new Thread() {
      public void run() {
        while(!clientSocket.s.isConnected()) {
          try {
            sleep(200);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
        (clientRead=new ClientRead(clientCore,clientSocket)).start();
        (clientWrite=new ClientWrite(clientCore,clientSocket)).start();
      }
    }.start();
    noStroke();
    graphicsList=new ArrayList<ArrayList<GraphicsData>>(layerSize);
    decals=new ArrayList<>(clientCore.cellData.length);
    final int ts=tempColorSize;
    int tsize=tempCellSize;
    int[] colors=new int[tempColorSize];
    for(int i=0;i<colors.length;i++) colors[i]=Tools.hsbColor((float)i/colors.length*255,0xff,0xff);
    graphicsList.add(0,new ArrayList<GraphicsData>(ts));
    for(int i=0;i<ts;i++) {
      int tgsize=tgsizeF(0);
      Graphics tg=new Graphics(this,tgsize*2,tgsize*2);
      tg.beginShape();
      fillHex(colors[i]&0x40ffffff);
      circle(tgsize,tgsize,tgsize);
      fillHex(colors[i]);
      circle(tgsize,tgsize,tgsize/2);
      tg.endShape();
      TextureRegion tr=new TextureRegion(tg.texture);
      graphicsList.get(0).add(0*ts+i,new GraphicsData(tg,tr));
      for(int j=0;j<tsize;j++) {
        Decal td=Decal.newDecal(Var.DIST,Var.DIST,tr,true);
        decals.add(i*tsize+j,new DecalData(td,0));
      }
    }
    for(int k=1;k<layerSize;k++) {
      graphicsList.add(k,new ArrayList<GraphicsData>(ts));
      for(int i=0;i<ts;i++) {
        int tgsize=tgsizeF(k);
        Graphics tg=new Graphics(this,tgsize*2,tgsize*2);
        tg.beginShape();
        fillHex(colors[i]&0x40ffffff);
        circle(tgsize,tgsize,tgsize);
        fillHex(colors[i]);
        circle(tgsize,tgsize,tgsize/2);
        tg.endShape();
        TextureRegion tr=new TextureRegion(tg.texture);
        graphicsList.get(k).add(i,new GraphicsData(tg,tr));
      }
    }
    //TODO
    Graphics tg=new Graphics(this,360,16*info0001.length);
    tg.beginShape();
    for(int i=0;i<info0001.length;i++) text(info0001[i],0,16*i);
    tg.endShape();
    TextureRegion tr=new TextureRegion(tg.texture);
    infoD=Decal.newDecal(tr,true);
    logo=Decal.newDecal(256,256,new TextureRegion(FileUtil.loadTexture("logo/logo-ingame.png")),true);
    logo.setPosition(0,-512,0);
    //TODO
    if(isAndroid) {
      buttons=UiGenerator.genButtons_0001(this);
      for(Button<?> e:buttons) centerScreen.add.add(e);
    }
    centerScreen.add.add(configInfo=new ConfigInfo(this));
    centerCam.add.add(playerCenter);
    centerCam.add.add(yourself);//TODO
  }
  @Override
  public void update() {
    if(clientSocket.stop) clientSocket.dispose();
  }
  public float colorF(float in) {
    in/=cam3d.viewDist()/2;
    in=2-in;
    if(in>1) in=1;
    if(in<0) in=0;
    return in;
  }
  public int layerF(float dist) {
    int out=(int)map(dist,0,cam3d.viewDist(),layerSize,0);
    if(out>=layerSize) out=layerSize-1;
    return out;
  }
  public boolean isVisible(Camera cam,Decal in,float r) {
    return cam.frustum.sphereInFrustum(in.getPosition(),r);
  }
  @Override
  public void displayWithCam() {
    for(int i=0;i<clientCore.cellData.length;i++) {
      float tx=clientCore.cellData[i].x*multDist;
      float ty=clientCore.cellData[i].y*multDist;
      float tz=clientCore.cellData[i].z*multDist;
      float tdist=dist(tx,ty,tz,cam.x(),cam.y(),cam.z());
      final DecalData tdd=decals.get(clientCore.cellData[i].id);
      final Decal td=tdd.decal;
      if(!isVisible(cam.camera,td,Var.DIST/2)) continue;
      final int tlf=layerF(tdist);
      if(tlf!=tdd.layer) {
        tdd.layer=tlf;
        td.setTextureRegion(graphicsList.get(tlf).get(clientCore.cellData[i].type).tr);
      }
      td.setPosition(tx,ty,tz);
      td.lookAt(cam.camera.position,cam.camera.up);
      float colorF=colorF(tdist);
      td.setColor(colorF,colorF,colorF,1);
      decal(td);
    }
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
    if(key=='Z') doUpdate=!doUpdate;
    if(key=='X') {
      cam3d.viewSpeed+=1/4f;
      if(cam3d.viewSpeed>8) cam3d.viewSpeed=8;
    }
    if(key=='C') {
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
  }
  @Override
  public void dispose() {
    super.dispose();
    clientSocket.dispose();
  }
}