package pama1234.gdx.game.util.legacy;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.physics.PathPoint;

public class Welcome extends Entity<RealGame>{
  private static final String[] slogan= {"————人工生命爱好者专用","————玩虫子！","————加我微信！lizerun2017","————加我QQ！1507585905","————自组织系统！"};
  private static final String title="粒子生命";
  public final PathPoint point;
  public Graphics g;
  public int textSize=64;
  public static int sloganPos;
  public Welcome(RealGame p,float x,float y) {
    super(p);
    point=new PathPoint(0,0,x,y);
  }
  // @Override
  public void refresh() {
    sloganPos=(int)p.random(slogan.length);
    // if(g==null) g=new Graphics(p,1,1);
    if(g==null) g=new Graphics(p,(int)Math.ceil(p.textWidthNoScale(title)*3),textSize*2);
    drawp();
  }
  public void drawp() {
    g.beginShape();
    p.clear();
    // p.textScale();
    // p.withScreen();
    p.textColor(191);
    // p.textSize(16);
    p.textScale(3);
    p.text(title,0,0);
    p.textScale(2);
    // p.text(slogan[sloganPos],g.width()/3f*2-p.textWidthNoScale(slogan[sloganPos])*2,textSize*1.33f);
    p.text(slogan[sloganPos],32,textSize*1.33f);
    p.textScale(1);
    g.endShape();
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
