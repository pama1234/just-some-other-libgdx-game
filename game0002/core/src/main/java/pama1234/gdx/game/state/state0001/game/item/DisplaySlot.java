package pama1234.gdx.game.state.state0001.game.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.state.state0001.game.entity.LivingEntity;
import pama1234.gdx.game.state.state0001.game.item.Item.ItemSlot;
import pama1234.math.UtilMath;

public class DisplaySlot{
  public ItemSlot data;
  public float cx,cy,x1,y1,x2,y2,w1,h1,w2,h2;
  public DisplaySlot(ItemSlot pos) {
    this.data=pos;
  }
  public void update(float x,float y) {
    cx=x+9;
    cy=y+9;
    setDisplaySize(data.item);
    updatePosition();
  }
  public void centerUpdate(LivingEntity pc,float x,float y) {
    cx=pc.cx()+x;
    cy=pc.cy()+y;
    setDisplaySize(data.item);
    updatePosition();
  }
  public void circleUpdate(LivingEntity pc,float i,float r) {
    cx=pc.cx()+UtilMath.sin(i*UtilMath.PI2)*r;
    cy=pc.cy()+UtilMath.cos(i*UtilMath.PI2)*r;
    setDisplaySize(data.item);
    updatePosition();
  }
  public void updatePosition() {
    x1=cx-w1/2f;
    y1=cy-h1/2f;
    x2=cx+w1/2f;
    y2=cy+h1/2f;
  }
  public void setDisplaySize(Item in) {
    if(in==null) {
      w1=h1=18;
      w2=h2=0;
    }else {
      TextureRegion tr=in.type.tiles[in.displayType()];
      w1=tr.getRegionWidth();
      h1=tr.getRegionHeight();
      w2=tr.getRegionWidth()/3f*2;
      h2=tr.getRegionHeight()/3f*2;
    }
  }
  public float w3() {
    return (w1-w2)/2f;
  }
  public float h3() {
    return (h1-h2)/2f;
  }
}