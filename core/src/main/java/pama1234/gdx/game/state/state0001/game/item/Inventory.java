package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.Creature;
import pama1234.math.UtilMath;

public class Inventory<T extends Item>{
  public Creature pc;
  public InventorySlot<T>[] data;
  public boolean displayHotSlot;
  public int hotSlotSize=9;
  public int selectSlot;
  public Inventory(Creature pc,int size) {
    this.pc=pc;
    data=new InventorySlot[size];
    for(int i=0;i<data.length;i++) data[i]=new InventorySlot<T>();
  }
  public void displayHotSlot() {
    if(!displayHotSlot) return;
    Screen0011 p=pc.p;
    p.beginBlend();
    // p.fill(1);
    p.tint(255,191);
    for(int i=0;i<hotSlotSize;i++) {//TODO change to class HotSlot
      // if(data[i]==null) continue;
      Item ti=data[i].item;
      if(ti==null) continue;
      displayInInventory(pc.p,ti,pc.cx(),pc.cy(),36,(float)i/hotSlotSize);//TODO tint image
    }
    p.noTint();
    // p.endBlend();
    drawSelectSlotFrame();
    p.endBlend();
  }
  public void drawSelectSlotFrame() {
    Screen0011 p=pc.p;
    // p.beginBlend();
    p.fill(14,229,234,127);
    drawSlotFrame(p,(float)selectSlot/hotSlotSize+(float)(p.frameCount%timeF)/timeF,data[selectSlot].item.type.tiles[data[selectSlot].item.displayType[0]]);
    // p.endBlend();
  }
  public void drawSlotFrame(Screen0011 p,float i,TextureRegion tr) {
    float r=2;
    float tx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*(float)36;
    float ty=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*36;
    float tx1=tx-tr.getRegionWidth()/2f-r;
    float ty1=ty-tr.getRegionHeight()/2f-r;
    float tx2=tx+tr.getRegionWidth()/2f+r;
    float ty2=ty+tr.getRegionHeight()/2f+r;
    float tw=tr.getRegionWidth()+r*2;
    float th=tr.getRegionHeight()+r*2;
    p.rect(tx1,ty1,1,th);
    p.rect(tx1,ty1,tw,1);
    p.rect(tx2-1,ty1,1,th);
    p.rect(tx1,ty2-1,tw,1);
  }
  public static class InventorySlot<T extends Item>{
    public T item;
  }
  public static class HotSlot<T extends Item>{
    public InventorySlot<T> pos;
    public float x,y,w,h;
  }
  public static int timeF=720;
  public static void displayInInventory(Screen0011 p,Item in,float x,float y,float r,float i) {
    i+=(float)(p.frameCount%timeF)/timeF;
    TextureRegion tr=in.type.tiles[in.displayType[0]];
    p.image(tr,
      x+UtilMath.sin(i*UtilMath.PI2)*r-tr.getRegionWidth()/2f,
      y+UtilMath.cos(i*UtilMath.PI2)*r-tr.getRegionHeight()/2f);
  }
}