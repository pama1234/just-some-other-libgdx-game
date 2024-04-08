package hhs.game.sort.games;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool;

public class ViewableArray{

  public int arr[];
  Rect rect[];
  int i,si,sj,tmp,oi,oj,li;
  float time=0;

  public float maxHeight;
  public float preSize,sc;

  Texture num;

  static Pool<swapArr> sa;

  public ViewableArray(MyGame g,int size) {
    preSize=10;
    sc=preSize*size/Gdx.graphics.getWidth();
    maxHeight=Gdx.graphics.getHeight()*sc-Gdx.graphics.getHeight()*sc/8;
    arr=new int[size];
    for(int i=0;i<size;i++) {
      arr[i]=MathUtils.random(0,(int)maxHeight);
    }
    rect=new Rect[size];
    for(int i=0;i<size;i++) {
      rect[i]=new Rect(i*preSize,Constant.h/8,preSize/2f,arr[i],Color.BLACK);
    }
    li=si=sj=0;

    num=g.ttool.createTexture(50,200,Color.WHITE);

    sa=new Pool<>() {
      @Override
      protected swapArr newObject() {
        return new swapArr();
      }
    };
  }

  public void arrayChange() {
    for(int i=0;i<arr.length;i++) {
      rect[i].set(i*preSize,Constant.h/8,preSize/2f,arr[i],rect[i].color);
    }
  }

  public void reset() {
    for(int i=0;i<arr.length;i++) {
      arr[i]=MathUtils.random(0,(int)maxHeight);
    }
    arrayChange();
  }

  public void swap(int i,int j) {
    rect[si].color=Color.BLACK;
    rect[sj].color=Color.BLACK;

    si=i;
    sj=j;
    rect[i].color=Color.YELLOW;
    rect[j].color=Color.RED;

    tmp=arr[i];
    arr[i]=arr[j];
    arr[j]=tmp;

    arrayChange();
  }
  public void ct(int i,int j) {
    rect[si].color=Color.BLACK;
    rect[sj].color=Color.BLACK;

    si=i;
    sj=j;
    rect[i].color=Color.YELLOW;
    rect[j].color=Color.RED;
  }
  public void one(int i) {
    rect[li].color=Color.BLACK;

    li=i;
    rect[i].color=Color.RED;
  }

  public void viewArray(SpriteBatch batch,float delta) {
    time+=delta;
    for(i=0;i<arr.length;i++) {
      batch.setColor(rect[i].color);
      batch.draw(num,rect[i].x,rect[i].y,rect[i].width,rect[i].height);
      batch.setColor(Color.WHITE);
    }
  }

  public static enum SortType{
    quickSort,
    bubbleSort;
  }
  static class swapArr extends Thread implements Pool.Poolable{

    float ix,jx;
    Rect i,j;

    public void set(Rect i,Rect j) {
      ix=i.x;
      jx=j.x;
      this.i=i;
      this.j=j;
    }
    @Override
    public void run() {
      int time=0;
      while(time<1) {
        i.x=MathUtils.lerp(ix,jx,time);
        j.x=MathUtils.lerp(jx,ix,time);
        try {
          Thread.sleep(((long)Gdx.graphics.getDeltaTime()*1000));
        }catch(InterruptedException e) {
          return;
        }
        time+=Gdx.graphics.getDeltaTime();
      }
      sa.free(this);
    }

    @Override
    public void reset() {
      ix=0;
      jx=0;
      i=j=null;
    }

  }
}
