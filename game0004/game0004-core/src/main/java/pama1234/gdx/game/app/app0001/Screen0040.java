package pama1234.gdx.game.app.app0001;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;

import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.graphics.UtilPolygonSpriteBatch;
import pama1234.math.UtilMath;
import pama1234.math.hash.PerlinNoise.ImprovedNoise4D;

public class Screen0040 extends ScreenCore3D{
  public boolean test;
  public String log="";

  public Decal[] decals;
  public Color colorCache=new Color();
  public float maxDist=120;
  public float nearConst=16,farConst=8,alphaConst=0.8f;
  {
    // isAndroid=true;
  }
  @Override
  public void setup() {
    noStroke();
    backgroundColor(191);
    // textColor(255);
    if(isAndroid) {
      addAndroidCam3DButtons();
    }

    cam3d.point.des.set(0,0,-40);
    // buttons=new TextButtonCam[] {
    //   new TextButtonCam<Screen0040>(this,true,()->true,self-> {},self-> {},self-> {
    //     test=!test;
    //     log+=" "+test;
    //     if(log.length()>10) log=log.substring(log.length()-10);
    //   },self->self.text="测试按钮",()->18,()->40,()->0),
    // };
    // centerCamAddAll(buttons);

    // PerlinNoise3f noise=new PerlinNoise3f(new HashNoise3f(0));
    // int rSize=256,gSize=256,bSize=256;
    int rSize,gSize,bSize;
    // rSize=gSize=bSize=16;
    rSize=gSize=bSize=64;
    decals=new Decal[rSize*gSize*bSize];
    TextureRegion tr=UtilPolygonSpriteBatch.defaultRegion;
    Vector3 vecCache=new Vector3();
    float dsize=32;
    float rin=1/8f;
    float length=4;
    for(int i=0;i<rSize;i++) {
      for(int j=0;j<gSize;j++) {
        for(int k=0;k<bSize;k++) {
          fill(i,j,0);
          Decal nd=Decal.newDecal(tr,true);
          color(colorCache,
            (float)i/(rSize-1)*255,
            (float)j/(gSize-1)*255,
            (float)k/(bSize-1)*255,127);
          nd.setColor(colorCache);
          vecCache.set(i*length,j*length,k*length);
          vecCache.add(
            ImprovedNoise4D.noise(i*rin,j*rin,k*rin,0)*dsize,
            ImprovedNoise4D.noise(i*rin,j*rin,k*rin,1)*dsize,
            ImprovedNoise4D.noise(i*rin,j*rin,k*rin,2)*dsize);
          nd.setPosition(vecCache);
          // var pos=nd.getPosition().cpy();
          // nd.setPosition(pos);
          decals[i*gSize*bSize+j*bSize+k]=nd;
        }
      }
    }
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    beginBlend();
    for(var i:decals) {
      var pos=cam3d.point.pos;
      float dist=i.getPosition().dst(pos.x,pos.y,pos.z);
      if(dist>maxDist) continue;
      if(!isVisible(cam3d.camera,i,4)) continue;
      i.lookAt(cam.camera.position,cam.camera.up);
      colorCache.set(i.getColor());
      float f=maxDist-dist;
      colorCache.a=UtilMath.clamp(f>maxDist/4*3f?(maxDist-f)/nearConst:f/farConst,0,1)*alphaConst;
      i.setColor(colorCache);
      decal(i);
    }
    flushDecal();
    // drawCursor();
    // text(log,40,20);
    endBlend();
  }
  public boolean isVisible(Camera cam,Decal in,float r) {
    // return cam.frustum.
    return cam.frustum.sphereInFrustum(in.getPosition(),r);
  }
  @Override
  public void frameResized() {}
}
