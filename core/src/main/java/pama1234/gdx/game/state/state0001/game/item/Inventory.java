package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.entity.Creature;
import pama1234.math.UtilMath;

public class Inventory{
  public Creature pc;
  public InventorySlot[] data;
  public boolean displayHotSlot;
  public int hotSlotSize=9;
  public int selectSlot;
  public Inventory(Creature pc,int size) {
    this.pc=pc;
    data=new InventorySlot[size];
    for(int i=0;i<data.length;i++) data[i]=new InventorySlot();
  }
  public void displayHotSlot() {
    if(!displayHotSlot) return;
    for(int i=0;i<hotSlotSize;i++) {
      // if(data[i]==null) continue;
      Item ti=data[i].item;
      if(ti==null) continue;
      displayInInventory(pc.p,ti,pc.cx(),pc.cy(),36,(float)i/hotSlotSize);
    }
    Screen0011 p=pc.p;
    float i=(float)selectSlot/hotSlotSize;
    i+=(float)(p.frameCount%timeF)/timeF;
    TextureRegion tr=data[selectSlot].item.type.tiles[data[selectSlot].item.displayType[0]];
    // p.image(tr,
    //   pc.cx()+UtilMath.sin(i*UtilMath.PI2)*(float)36-tr.getRegionWidth()/2f,
    //   pc.cy()+UtilMath.cos(i*UtilMath.PI2)*36-tr.getRegionHeight()/2f);
    p.fill(127);
    // p.noFill();
    // p.doStroke();
    // p.stroke(127);
    // p.strokeWeight(p.strokeWeight*2);
    // p.rect(
    //   pc.cx()+UtilMath.sin(i*UtilMath.PI2)*(float)36-tr.getRegionWidth()/2f-2,
    //   pc.cy()+UtilMath.cos(i*UtilMath.PI2)*36-tr.getRegionHeight()/2f-2,
    //   tr.getRegionWidth()+4,tr.getRegionHeight()+4);
    // p.doFill();
    // p.strokeWeight(p.strokeWeight/2);
    // p.noStroke();
    drawSelectSlot(p,i,tr);
  }
  public void drawSelectSlot(Screen0011 p,float i,TextureRegion tr) {
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
  public static class InventorySlot{
    public Item item;
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