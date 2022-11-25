package pama1234.gdx.game.app;

import static com.badlogic.gdx.math.MathUtils.log;
import static com.badlogic.gdx.math.MathUtils.map;
import static pama1234.gdx.game.ui.InfoGenerator.info;
import static pama1234.math.UtilMath.dist;
import static pama1234.math.UtilMath.pow;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.SocketHints;

import pama1234.data.ByteUtil;
import pama1234.gdx.game.app.server.with3d.particle.CellGroup3D;
import pama1234.gdx.game.app.server.with3d.particle.CellGroupGenerator3D;
import pama1234.gdx.game.net.CellData;
import pama1234.gdx.game.net.ServerInfo;
import pama1234.gdx.game.net.SocketData;
import pama1234.gdx.game.ui.Button;
import pama1234.gdx.game.ui.TextButtonGenerator;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.wrapper.Center;

/**
 * 3D 粒子系统
 */
public class Screen0003 extends UtilScreen3D{
  ServerInfo serverInfo;
  //---
  ServerSocket serverSocket;
  Center<SocketData> centerS;
  //---
  SocketData client;
  // int[] serverTypeData;
  // float[][] serverVecData;
  volatile CellData[] cellData;
  //---
  Thread serverT,acceptT,clientT;
  //---
  public CellGroup3D group;
  // PlayerCenter<Player3D> playerCenter;
  public ArrayList<ArrayList<GraphicsData>> graphicsList;
  public ArrayList<DecalData> decals;
  // boolean doUpdate=true;//TODO
  public boolean doUpdate;
  public Thread updateCell;
  public Vector3 posCache=new Vector3();
  public float viewDist=1024;
  // static final float logn=32,logViewDist=log(viewDist,logn/4);
  public static final int layerSize=3;
  public static final int gsize=8;
  public float multDist=1;
  public boolean displayHint=true;
  public Decal infoD;
  public Decal logo;
  // final int tu=16;
  public Button[] buttons;
  public int bu;
  public boolean fullSettings;
  public boolean configInfo;
  public boolean tempTest;//TODO
  // Graphics buttonsG;
  // Texture buttonsT;
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
    // public int type;//TODO
    public int layer;
    public DecalData(Decal g,int layer) {
      this.decal=g;
      this.layer=layer;
    }
    // public DecalData(Decal g,int type,int layer) {
    //   this.decal=g;
    //   this.type=type;
    //   this.layer=layer;
    // }
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
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    // group=gen.randomGenerate();
    group=gen.GenerateFromMiniCore();
    // serverTypeData=new int[gen.arraySizeOut];
    cellData=new CellData[gen.arraySizeOut];
    for(int i=0;i<cellData.length;i++) {
      cellData[i]=new CellData();
    }
    //---
    serverInfo=new ServerInfo("192.168.2.105",12347);
    centerS=new Center<>();
    //---
    SocketHints tsh=new SocketHints();
    tsh.keepAlive=true;
    tsh.performancePrefConnectionTime=0;
    tsh.performancePrefLatency=2;
    tsh.performancePrefBandwidth=1;
    //---
    ServerSocketHints tssh=new ServerSocketHints();
    serverSocket=Gdx.net.newServerSocket(Protocol.TCP,serverInfo.addr,serverInfo.port,tssh);
    acceptT=new Thread(()-> {
      while(!stop) centerS.add.add(new SocketData(serverSocket.accept(tsh)));
    });
    acceptT.start();
    //---
    // System.out.println(cellData.length+" "+group.size);
    serverT=new Thread(()-> {
      byte[] outData=new byte[20];
      while(!stop) {
        synchronized(centerS.list) {
          synchronized(group) {
            for(SocketData e:centerS.list) {
              try {
                // i.o.write(new byte[] {(byte)((frameCount>>24)&0xff),(byte)((frameCount>>16)&0xff),(byte)((frameCount>>8)&0xff),(byte)(frameCount&0xff)});
                for(int i=0;i<group.size;i++) {
                  ByteUtil.intToByte(i,outData);
                  ByteUtil.intToByte(group.type[i],outData,4);
                  ByteUtil.floatToByte(group.x(i),outData,8);
                  ByteUtil.floatToByte(group.y(i),outData,12);
                  ByteUtil.floatToByte(group.z(i),outData,16);
                  e.o.write(outData);
                }
                e.o.flush();
              }catch(IOException exception) {
                exception.printStackTrace();
              }
            }
          }
        }
      }
    });
    serverT.start();
    //---
    client=new SocketData(Gdx.net.newClientSocket(Protocol.TCP,serverInfo.addr,serverInfo.port,tsh));
    //---
    clientT=new Thread(()-> {
      // byte[] td=new byte[4];
      byte[] inData=new byte[20];
      int ti;
      while(!stop) {
        try {
          // int ti=client.i.readNBytes(td,0,4);
          // println(ti+" "+Arrays.toString(td)+" "+ByteUtil.byteToInt(td));
          // synchronized(cellData) {
          for(int i=0;i<cellData.length;i++) {
            ti=0;
            while(ti==0) ti=client.i.readNBytes(inData,0,inData.length);
            cellData[i].id=ByteUtil.byteToInt(inData);
            cellData[i].type=ByteUtil.byteToInt(inData,4);
            cellData[i].x=ByteUtil.byteToFloat(inData,8);
            cellData[i].y=ByteUtil.byteToFloat(inData,12);
            cellData[i].z=ByteUtil.byteToFloat(inData,16);
          }
          // }
        }catch(Exception e) {
          e.printStackTrace();
        }
      }
    });
    clientT.start();
    //---
    // playerCenter=new PlayerCenter<Player3D>();
    noStroke();
    // graphicsList=new ArrayList<>(group.colors.length);
    graphicsList=new ArrayList<ArrayList<GraphicsData>>(layerSize);
    decals=new ArrayList<>(group.size);
    // decals=new ArrayList<ArrayList<Decal>>(tl);
    final int ts=group.colors.length;
    int tsize=group.size/ts;
    // int[] tsizelist=new int[] {32,64,256,512};
    graphicsList.add(0,new ArrayList<GraphicsData>(ts));
    for(int i=0;i<ts;i++) {
      int tgsize=tgsizeF(0);
      Graphics tg=new Graphics(this,tgsize*2,tgsize*2);
      tg.beginDraw();
      fillHex(group.colors[i]&0x40ffffff);
      circle(tgsize,tgsize,tgsize);
      fillHex(group.colors[i]);
      circle(tgsize,tgsize,tgsize/2);
      tg.endDraw();
      TextureRegion tr=new TextureRegion(tg.texture);
      graphicsList.get(0).add(0*ts+i,new GraphicsData(tg,tr));
      for(int j=0;j<tsize;j++) {
        Decal td=Decal.newDecal(CellGroup3D.DIST,CellGroup3D.DIST,tr,true);
        decals.add(i*tsize+j,new DecalData(td,0));
        // decals.add(i*tsize+j,new DecalData(td,i,0));
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
        fillHex(group.colors[i]&0x40ffffff);
        circle(tgsize,tgsize,tgsize);
        fillHex(group.colors[i]);
        circle(tgsize,tgsize,tgsize/2);
        tg.endDraw();
        TextureRegion tr=new TextureRegion(tg.texture);
        graphicsList.get(k).add(i,new GraphicsData(tg,tr));
      }
    }
    // System.out.println(decals.size());
    updateCell=new Thread() {
      @Override
      public void run() {
        while(!stop) {
          if(doUpdate) group.update();
          else try {
            sleep(1000);
          }catch(InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
    updateCell.start();
    //TODO
    Graphics tg=new Graphics(this,360,16*info.length);
    tg.beginDraw();
    for(int i=0;i<info.length;i++) text(info[i],0,16*i);
    tg.endDraw();
    TextureRegion tr=new TextureRegion(tg.texture);
    infoD=Decal.newDecal(tr,true);
    logo=Decal.newDecal(256,256,new TextureRegion(loadTexture("logo/logo-ingame.png")),true);
    logo.setPosition(0,-512,0);
    //TODO
    if(isAndroid) {
      buttons=TextButtonGenerator.genButtons(this);
      for(int i=0;i<buttons.length;i++) center.add.add(buttons[i]);
    }
  }
  public int getButtonUnitLength() {
    return bu;
  }
  @Override
  public void update() {
    centerS.refresh();
  }
  public boolean isVisible(Camera cam,Decal in,float r) {
    return cam.frustum.sphereInFrustum(in.getPosition(),r);
  }
  public float colorF(float in) {
    in/=viewDist/2;
    in=2-in;
    if(in>1) in=1;
    if(in<0) in=0;
    return in;
  }
  public int layerF(float dist) {
    int out=(int)map(dist,0,viewDist,layerSize,0);
    if(out>=layerSize) out=layerSize-1;
    return out;
    // return (int)constrain(map(log(dist,logn),-logViewDist,logViewDist,layerSize,0),0,layerSize-1);
  }
  public static void main(String[] args) {
    pow(1,1);
    log(1,1);
  }
  @Override
  public void display() {
    // Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
    // Gdx.gl20.glDepthMask(false);
    // synchronized(group) {
    // synchronized(cellData) {
    // for(int i=0;i<group.size;i++) {
    if(tempTest) for(int i=0;i<group.size;i++) {
      float tx=group.x(i)*multDist;
      float ty=group.y(i)*multDist;
      float tz=group.z(i)*multDist;
      // float tx=cellData[i].x*multDist;
      // float ty=cellData[i].y*multDist;
      // float tz=cellData[i].z*multDist;
      float tdist=dist(tx,ty,tz,cam.x(),cam.y(),cam.z());
      // Decal td=decals.get(layerF(tdist)).get(i);
      final DecalData tdd=decals.get(i);
      final Decal td=tdd.decal;
      if(tdist>viewDist) continue;
      if(!isVisible(cam.camera,td,CellGroup3D.DIST/2)) continue;
      final int tlf=layerF(tdist);
      if(tlf!=tdd.layer) {
        tdd.layer=tlf;
        td.setTextureRegion(graphicsList.get(tlf).get(group.type[i]).tr);
        // td.setTextureRegion(graphicsList.get(tlf).get(cellData[i].type).tr);
      }
      td.setPosition(tx,ty,tz);
      td.lookAt(cam.camera.position,cam.camera.up);
      td.setColor(1,1,1,colorF(tdist));
      decal(td);
    }
    else for(int i=0;i<cellData.length;i++) {
      // float tx=group.x(i)*multDist;
      // float ty=group.y(i)*multDist;
      // float tz=group.z(i)*multDist;
      float tx=cellData[i].x*multDist;
      float ty=cellData[i].y*multDist;
      float tz=cellData[i].z*multDist;
      float tdist=dist(tx,ty,tz,cam.x(),cam.y(),cam.z());
      // Decal td=decals.get(layerF(tdist)).get(i);
      // System.out.println(cellData[i].id);
      final DecalData tdd=decals.get(cellData[i].id);
      // if(tdd.type!=cellData[i].type) System.out.println(cellData[i].type+" "+tdd.type);
      final Decal td=tdd.decal;
      if(tdist>viewDist) continue;
      if(!isVisible(cam.camera,td,CellGroup3D.DIST/2)) continue;
      final int tlf=layerF(tdist);
      if(tlf!=tdd.layer) {
        tdd.layer=tlf;
        // td.setTextureRegion(graphicsList.get(tlf).get(group.type[i]).tr);
        td.setTextureRegion(graphicsList.get(tlf).get(cellData[i].type).tr);
      }
      td.setPosition(tx,ty,tz);
      td.lookAt(cam.camera.position,cam.camera.up);
      td.setColor(1,1,1,colorF(tdist));
      decal(td);
    }
    // }
    // }
    if(displayHint) decal(infoD);
    logo.lookAt(cam.camera.position,cam.camera.up);
    decal(logo);
    flushDecal();
    // withScreen();
    // text("moveSpeed "+cam3d.moveSpeed,0,0);
    // text("viewSpeed "+cam3d.viewSpeed,0,u);
    if(isAndroid) {
      withScreen();
      // beginBlend();
      for(int i=0;i<buttons.length;i++) buttons[i].displayScreen();
      // endBlend();
    }
    if(configInfo) {
      beginBlend();
      fill(127,191);
      textColor(255,191);
      final float tx=width/2f+pus,ty=bu*0.5f+pus;
      rect(tx,ty,pu*9+pus*2,pu*5+pus*2);
      text("移动速度   "+cam3d.moveSpeed,tx+pus,ty+pus);
      text("视角灵敏度 "+cam3d.viewSpeed,tx+pus,ty+pus+pu);
      text("视野距离   "+viewDist,tx+pus,ty+pus+pu*2);
      text("位置缩放   "+multDist,tx+pus,ty+pus+pu*3);
      text(serverInfo.toString(),tx+pus,ty+pus+pu*4);
    }
    endBlend();
  }
  @Override
  public void frameResized() {
    bu=pus*24;
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
    if(key=='N') {
      viewDist/=2;
      if(viewDist<2) viewDist=2;
    }
    if(key=='M') {
      viewDist*=2;
      if(viewDist>2048) viewDist=2048;
    }
    if(isAndroid&&key=='T') fullSettings=!fullSettings;//TODO
    if(key=='I') configInfo=!configInfo;
    if(key=='P') tempTest=!tempTest;
  }
  @Override
  public void dispose() {
    super.dispose();
    updateCell.interrupt();
    if((!updateCell.isInterrupted())||(updateCell.isAlive())) updateCell.stop();
    serverSocket.dispose();
    centerS.refresh();
    for(SocketData i:centerS.list) i.s.dispose();
    client.s.dispose();
  }
}