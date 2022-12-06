package pama1234.gdx.game.ui;

import pama1234.gdx.game.app.ScreenCore;
import pama1234.gdx.util.entity.Entity;

public class ConfigInfo extends Entity<ScreenCore>{
  public boolean active;
  public ConfigInfo(ScreenCore p) {
    super(p);
  }
  @Override
  public void keyPressed(char key,int keyCode) {
    if(key=='I') active=!active;
  }
  @Override
  public void display() {
    if(!active) return;
    p.withScreen();
    p.beginBlend();
    p.fill(127,191);
    p.textColor(255,191);
    final float tx=p.width/2f+p.pus,ty=p.bu*0.5f+p.pus;
    p.rect(tx,ty,p.pu*10+p.pus*2,p.pu*5+p.pus*2);
    p.text("移动速度   "+p.cam3d.moveSpeed,tx+p.pus,ty+p.pus);
    p.text("视角灵敏度 "+p.cam3d.viewSpeed,tx+p.pus,ty+p.pus+p.pu);
    p.text("视野距离   "+p.cam3d.viewDist(),tx+p.pus,ty+p.pus+p.pu*2);
    p.text("位置缩放   "+p.multDist,tx+p.pus,ty+p.pus+p.pu*3);
    p.text(p.dataServerInfo.toString(),tx+p.pus,ty+p.pus+p.pu*4);
    p.endBlend();
  }
}
