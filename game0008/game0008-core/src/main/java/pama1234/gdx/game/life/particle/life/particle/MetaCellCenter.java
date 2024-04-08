package pama1234.gdx.game.life.particle.life.particle;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.life.particle.element.ParticleSandbox;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.wrapper.ArrayEntityCenter;
import pama1234.math.vec.Vec2i;
import pama1234.server.game.life.particle.core.MetaCellCenterServer;
import pama1234.server.game.life.particle.core.MetaInfoUnit;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class MetaCellCenter extends ArrayEntityCenter<Screen0045,MetaCell>{
  private static final float minRefreshDist=1f/(1<<8);
  public static final int textSize=12;

  public MetaCellCenterServer core;

  public int x,y;
  public Vec2i select=new Vec2i();
  public int refresh=2;
  // public MetaCell[] cells;
  public MetaInfoUnit[][] matrix;
  public ParticleSandbox particleSandbox;

  public Color fillColor_1=UtilScreenColor.newColorFromInt(0xff6D5CB7);

  public MetaCellCenter(Screen0045 p,ParticleSandbox particleSandbox,int x,int y,int w,int h) {
    super(p);
    this.particleSandbox=particleSandbox;
    this.x=x;
    this.y=y;
    // core=new MetaCellCenterServer(null);
    core=particleSandbox.core.metaCenter;
  }
  @Override
  public void display() {
    boolean flag=false;
    for(MetaCell i:list) {
      if(Math.abs(i.core.point.pos.x-i.core.point.des.x)>minRefreshDist||
        Math.abs(i.core.point.pos.y-i.core.point.des.y)>minRefreshDist) {
        flag=true;
        break;
      }
    }
    if(flag) refreshLayer();
    else while(refresh>0) {
      refresh--;
      refreshLayer();
    }
  }
  public void refreshLayer() {}
  @Override
  public void keyPressed(char key,int keyCode) {
    super.keyPressed(key,keyCode);
    switch(keyCode) {
      case Keys.UP:
        select.y--;
        if(select.y<0) select.y+=list.size();
        refresh=1;
        break;
      case Keys.DOWN:
        select.y++;
        if(select.y>=list.size()) select.y-=list.size();
        refresh=1;
        break;
      case Keys.LEFT:
        select.x--;
        if(select.x<0) select.x+=list.size();
        refresh=1;
        break;
      case Keys.RIGHT:
        select.x++;
        if(select.x>=list.size()) select.x-=list.size();
        refresh=1;
        break;
    }
  }
  @Override
  public void refresh() {
    for(var e:add) core.add.add(e.core);
    for(var e:remove) core.remove.add(e.core);
    core.refresh();
    super.refresh();
  }
  public void basicRefresh() {
    super.refresh();
  }
}
