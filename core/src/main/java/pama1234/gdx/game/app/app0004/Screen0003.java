package pama1234.gdx.game.app.app0004;

import static com.badlogic.gdx.math.MathUtils.log;
import static com.badlogic.gdx.math.MathUtils.map;
import static pama1234.gdx.game.ui.InfoGenerator.info;
import static pama1234.math.UtilMath.dist;
import static pama1234.math.UtilMath.pow;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.SocketHints;

import pama1234.game.app.server.game.net.SocketData;
import pama1234.game.app.server.game.net.data.ClientCore;
import pama1234.game.app.server.game.net.io.ClientRead;
import pama1234.game.app.server.game.net.io.ClientWrite;
import pama1234.game.app.server.particle.Var;
import pama1234.gdx.game.net.SocketWrapperGDX;
import pama1234.gdx.game.ui.ConfigInfo;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;
import pama1234.gdx.game.util.ClientPlayerCenter3D;
import pama1234.gdx.game.util.ControllerClientPlayer3D;
import pama1234.gdx.util.FileUtil;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.math.Tools;
import pama1234.util.net.ServerInfo;

/**
 * 3D 粒子系统 联机 客户端
 */
public class Screen0003 extends ScreenCore3D{
  // public ServerInfo dataServerInfo;
  //---
  // public ServerSocket serverSocket;
  // public Center<SocketData> socketCenter;
  //---
  public SocketData clientSocket;
  // public volatile CellData[] cellData;
  public ClientCore clientCore;
  //---
  public ClientRead clientRead;
  public ClientWrite clientWrite;
  // public Thread acceptT,serverReadT,clientReadT,serverWriteT,clientWriteT;
  //---
  // public CellGroup3D group;
  @Deprecated
  public ClientPlayerCenter3D playerCenter;//TODO
  public ControllerClientPlayer3D yourself;
  // public ServerPlayer3D yourself;
  public ArrayList<ArrayList<GraphicsData>> graphicsList;
  public ArrayList<DecalData> decals;
  // boolean doUpdate=true;//TODO
  public boolean doUpdate;
  // public Thread updateCell;
  public Vector3 posCache=new Vector3();
  // public float viewDist=1024;//TODO
  // static final float logn=32,logViewDist=log(viewDist,logn/4);
  public static final int layerSize=3;
  public static final int gsize=8;
  // public float multDist=1;
  public boolean displayHint;
  // public boolean displayHint=true;
  public Decal infoD;
  public Decal logo;
  // final int tu=16;
  // public Button[] buttons;
  // public int bu;
  // public boolean fullSettings;
  // public boolean configInfo;
  public boolean tempTest;//TODO
  public ConfigInfo configInfo;
  // Graphics buttonsG;
  // Texture buttonsT;
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
  public static class DecalData{//TODO
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
    // CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // // group=gen.randomGenerate();
    // group=gen.GenerateFromMiniCore();
    // serverTypeData=new int[gen.arraySizeOut];
    // cellData=new CellData[gen.arraySizeOut];
    clientCore=new ClientCore(tempSize,"pama"+String.format("%04d",(int)(random(0,10000))));
    // cellData=new CellData[tempSize];
    // for(int i=0;i<cellData.length;i++) cellData[i]=new CellData();
    playerCenter=new ClientPlayerCenter3D(this);
    // yourself=new ControllerClientPlayer3D(this,"pama1234",cam.point);
    yourself=new ControllerClientPlayer3D(this,clientCore.yourself);
    // yourself=new ServerPlayer3D("pama1234",0,0,0);
    //---
    dataServerInfo=new ServerInfo("192.168.2.105",12347);
    // stateServerInfo=new ServerInfo("192.168.2.105",12346);
    //---
    SocketHints tsh=new SocketHints();
    tsh.connectTimeout=10000;
    tsh.socketTimeout=5000;
    tsh.keepAlive=true;
    tsh.performancePrefConnectionTime=0;
    tsh.performancePrefLatency=2;
    tsh.performancePrefBandwidth=1;
    //---
    //---
    // sleep(10000);   
    // clientStateSocket=new SocketData("pama1234",Gdx.net.newClientSocket(Protocol.TCP,stateServerInfo.addr,stateServerInfo.port,tsh));
    clientSocket=new SocketData(yourself.data.name(),new SocketWrapperGDX(Gdx.net.newClientSocket(Protocol.TCP,dataServerInfo.addr,dataServerInfo.port,tsh)));
    new Thread() {
      public void run() {
        while(!clientSocket.s.isConnected()) {
          try {
            sleep(200);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
        //TODO
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
      tg.beginDraw();
      fillHex(colors[i]&0x40ffffff);
      circle(tgsize,tgsize,tgsize);
      fillHex(colors[i]);
      circle(tgsize,tgsize,tgsize/2);
      tg.endDraw();
      TextureRegion tr=new TextureRegion(tg.texture);
      graphicsList.get(0).add(0*ts+i,new GraphicsData(tg,tr));
      for(int j=0;j<tsize;j++) {
        Decal td=Decal.newDecal(Var.DIST,Var.DIST,tr,true);
        decals.add(i*tsize+j,new DecalData(td,0));
      }
    }
    for(int k=1;k<layerSize;k++) {
      graphicsList.add(k,new ArrayList<GraphicsData>(ts));
      // k++;
      // decals.add(k,new ArrayList<Decal>(ts));
      for(int i=0;i<ts;i++) {
        int tgsize=tgsizeF(k);
        Graphics tg=new Graphics(this,tgsize*2,tgsize*2);
        tg.beginDraw();
        fillHex(colors[i]&0x40ffffff);
        circle(tgsize,tgsize,tgsize);
        fillHex(colors[i]);
        circle(tgsize,tgsize,tgsize/2);
        tg.endDraw();
        TextureRegion tr=new TextureRegion(tg.texture);
        graphicsList.get(k).add(i,new GraphicsData(tg,tr));
      }
    }
    //TODO
    Graphics tg=new Graphics(this,360,16*info.length);
    tg.beginDraw();
    for(int i=0;i<info.length;i++) text(info[i],0,16*i);
    tg.endDraw();
    TextureRegion tr=new TextureRegion(tg.texture);
    infoD=Decal.newDecal(tr,true);
    logo=Decal.newDecal(256,256,new TextureRegion(FileUtil.loadTexture("logo/logo-ingame.png")),true);
    logo.setPosition(0,-512,0);
    //TODO
    if(isAndroid) {
      buttons=ButtonGenerator.genButtons_0001(this);
      for(Button e:buttons) centerScreen.add.add(e);
    }
    centerScreen.add.add(configInfo=new ConfigInfo(this));
    centerCam.add.add(playerCenter);
    centerCam.add.add(yourself);//TODO
    // updateViewDist();
  }
  // public int getButtonUnitLength() {
  //   return bu;
  // }
  @Override
  public void update() {
    // centerSocket.refresh();
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
    // return (int)constrain(map(log(dist,logn),-logViewDist,logViewDist,layerSize,0),0,layerSize-1);
  }
  // public void updateViewDist() {
  //   cam3d.camera.far=viewDist;
  // }
  public boolean isVisible(Camera cam,Decal in,float r) {
    return cam.frustum.sphereInFrustum(in.getPosition(),r);
  }
  @Override
  public void displayWithCam() {
    // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    // Gdx.gl20.glDepthMask(false);
    // synchronized(cellData) {
    for(int i=0;i<clientCore.cellData.length;i++) {
      float tx=clientCore.cellData[i].x*multDist;
      float ty=clientCore.cellData[i].y*multDist;
      float tz=clientCore.cellData[i].z*multDist;
      float tdist=dist(tx,ty,tz,cam.x(),cam.y(),cam.z());
      final DecalData tdd=decals.get(clientCore.cellData[i].id);
      final Decal td=tdd.decal;
      // if(tdist>viewDist) continue;
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
    // }
    if(displayHint) decal(infoD);
    logo.lookAt(cam.camera.position,cam.camera.up);
    decal(logo);
    flushDecal();
  }
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    // super.frameResized();
  }
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
      // updateViewDist();
    }
    if(key=='M') {
      float tvd=cam3d.viewDist();
      tvd*=2;
      if(tvd>2048) tvd=2048;
      cam3d.viewDist(tvd);
      // updateViewDist();
    }
    if(isAndroid&&key=='T') fullSettings=!fullSettings;//TODO
    // if(key=='I') configInfo=!configInfo;
    if(key=='P') tempTest=!tempTest;
  }
  @Override
  public void dispose() {
    super.dispose();
    clientSocket.dispose();
  }
  public static void main(String[] args) {
    pow(1,1);
    log(1,1);
  }
}