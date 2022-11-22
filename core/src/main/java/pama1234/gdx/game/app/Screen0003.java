package pama1234.gdx.game.app;

import static com.badlogic.gdx.math.MathUtils.log;
import static com.badlogic.gdx.math.MathUtils.map;
import static pama1234.math.Tools.inBox;
import static pama1234.math.UtilMath.dist;
import static pama1234.math.UtilMath.pow;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.game.app.server.with3d.particle.CellGroup3D;
import pama1234.gdx.game.app.server.with3d.particle.CellGroupGenerator3D;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;

/**
 * 3D 粒子系统
 */
public class Screen0003 extends UtilScreen3D{
  CellGroup3D group;
  // PlayerCenter<Player3D> playerCenter;
  ArrayList<ArrayList<GraphicsData>> graphicsList;
  ArrayList<DecalData> decals;
  // boolean doUpdate=true;//TODO
  boolean doUpdate;
  Thread updateCell;
  Vector3 posCache=new Vector3();
  // static final float viewDist=512;
  // static final float viewDist=1024;
  float viewDist=1024;
  // static final float logn=32,logViewDist=log(viewDist,logn/4);
  static final int layerSize=3;
  static final int gsize=8;
  float multDist=1;
  boolean displayHint=true;
  String[] hint=new String[] {
    "粒子不会撞墙啦！",
    "规则是很经典的贪吃蛇！",
    "将来会做成可爱的联机游戏！",
    "aparapi可能无法工作",
    "本程序需要java17运行",
    "采用并行计算！修了很久很久的算法哦",
    "经过测试，在1050Ti上可流畅运行1024*12个粒子",
    "采用异步计算（和渲染！！用户视角不易卡顿",
    "透明贴图渲染会有问题，请在阅读后关闭提示",
    "以下功能请通过测试得出用法",
    "WASD和空格和shift移动视角",
    "滚轮调整视角速度",
    "右键或alt或esc更改鼠标功能",
    "RF调整位置倍数",
    "Z暂停时间，XC控制鼠标灵敏度",
    "NM调整视野范围，谨慎操作",
    "H关闭提示，改粒子数据需要改动源代码才行",
    "pama1234出品，谢谢观看",
    "————在很久之后，他终于把半自制的3D框架写好啦"
  };
  Decal dhint;
  Decal logo;
  // final int tu=16;
  Button[] buttons;
  int bu;
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
    public int layer;
    public DecalData(Decal g,int layer) {
      this.decal=g;
      this.layer=layer;
    }
  }
  // public abstract class Button extends SpriteEntity{
  //   public Button(UtilScreen p,Sprite s) {
  //     super(p,s);
  //     // new Sprite
  //   }
  //   public Button(UtilScreen p,TextureRegion s) {
  //     this(p,new Sprite(s));
  //     // new Sprite
  //   }
  //   @Override
  //   public void frameResized(int w,int h) {
  //     s.setScale(tu/u);
  //   }
  //   @Override
  //   public void display() {}
  //   public void displayWithCam() {
  //     super.display();
  //   }
  // }
  public abstract class Button extends Entity{
    public Button(UtilScreen p) {
      super(p);
    }
    @Override
    public void display() {}
    public abstract void displayScreen();
    public abstract boolean inButton(float x,float y);
    public abstract void execute();
    @Override
    public void touchEnded(TouchInfo info) {
      if(inButton(info.sx,info.sy)&&inButton(info.x,info.y)) execute();
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
    CellGroupGenerator3D gen=new CellGroupGenerator3D(0,0);
    group=gen.GenerateFromMiniCore();
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
    Graphics tg=new Graphics(this,360,16*hint.length);
    tg.beginDraw();
    for(int i=0;i<hint.length;i++) text(hint[i],0,16*i);
    tg.endDraw();
    TextureRegion tr=new TextureRegion(tg.texture);
    dhint=Decal.newDecal(tr,true);
    logo=Decal.newDecal(256,256,new TextureRegion(loadTexture("logo/logo.png")),true);
    logo.setPosition(0,-512,0);
    //TODO
    if(isAndroid) {
      // final int tu=constrain((int)u,4,64)*4;
      // buttonsG=new Graphics(this,tu*5,tu*4);
      // buttonsG.beginDraw();
      // // triangle(0,0,0,tu,tu,0);
      // text("Z",0,0);
      // buttonsG.endDraw();
      // buttonsT=loadTexture("touchCtrl/buttons.png");
      buttons=new Button[] {
        // new Button(this,new Sprite(new TextureRegion(buttonsT))) {
        // }};,
        // new Button(this,new Sprite(buttonsG.texture,0,0,tu,tu)) {
        // },
        new Button(this) {
          @Override
          public void displayScreen() {
            // System.out.println("abc");
            textColor(0);
            fill(211,244,196);
            rect(0,0,bu,bu);
            text("Z",0,0);
            // noFill();
            // doStroke();
            // stroke(255);
          }
          @Override
          public boolean inButton(float x,float y) {
            return inBox(x,y,0,0,bu,bu);
          }
          @Override
          public void execute() {
            doUpdate=!doUpdate;
          }
        }
      };
      for(int i=0;i<buttons.length;i++) center.add.add(buttons[i]);
    }
  }
  @Override
  public void update() {}
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
    synchronized(group) {
      for(int i=0;i<group.size;i++) {
        float tx=group.x(i)*multDist;
        float ty=group.y(i)*multDist;
        float tz=group.z(i)*multDist;
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
        }
        td.setPosition(tx,ty,tz);
        td.lookAt(cam.camera.position,cam.camera.up);
        td.setColor(1,1,1,colorF(tdist));
        decal(td);
      }
    }
    if(displayHint) decal(dhint);
    logo.lookAt(cam.camera.position,cam.camera.up);
    decal(logo);
    flushDecal();
    // withScreen();
    // text("moveSpeed "+cam3d.moveSpeed,0,0);
    // text("viewSpeed "+cam3d.viewSpeed,0,u);
    if(isAndroid) {
      withScreen();
      // image(buttonsG.texture,0,0);
      // rect(0,0,u,u*4);
      // text("Z",16,16);
      for(int i=0;i<buttons.length;i++) buttons[i].displayScreen();
    }
  }
  @Override
  public void frameResized() {
    // println(u);
    // strokeWeight(u);
    bu=pus*16;
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
      multDist+=1/4f;
      if(multDist>8) multDist=8;
    }
    if(key=='F') {
      multDist-=1/4f;
      if(multDist<1/4f) multDist=1/4f;
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
  }
  @Override
  public void dispose() {
    super.dispose();
    updateCell.interrupt();
    if((!updateCell.isInterrupted())||(updateCell.isAlive())) updateCell.stop();
  }
}