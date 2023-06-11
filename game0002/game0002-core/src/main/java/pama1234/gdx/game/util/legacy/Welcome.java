package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.physics.PathPoint;

public class Welcome extends Entity<RealGame>{
  private static final String[] slogan= {"————魔道中人专用","————玩虫子！","————加我微信！lizerun2017","————加我QQ！1507585905","————粒子系统！"};
  private static final String title="炼蛊模拟器";
  public final PathPoint point;
  public Graphics g;
  public int textSize=128;
  public static int sloganPos;
  public Welcome(RealGame p,float x,float y) {
    super(p);
    point=new PathPoint(0,0,x,y);
  }
  // @Override
  public void refresh() {
    sloganPos=(int)p.random(slogan.length);
    // if(g==null) g=new Graphics(p,1,1);
    g=new Graphics(p,(int)Math.ceil(p.textWidth(title)),textSize*2);
    drawp();
  }
  public void drawp() {
    g.begin();
    // p.textColor(0);
    p.text(title,0,0);
    p.text(slogan[sloganPos],g.width()-p.textWidth(slogan[sloganPos]),textSize*1.33f);
    g.end();
  }
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    p.image(g.texture,point.pos.x-p.width/2,point.pos.y-p.height/2);
  }
}
