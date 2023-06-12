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
    // System.out.println(p.usedCamera);
    sloganPos=(int)p.random(slogan.length);
    // if(g==null) g=new Graphics(p,1,1);
    if(g==null) g=new Graphics(p,(int)Math.ceil(p.textWidthNoScale(title)*9),textSize*2);
    drawp();
  }
  public void drawp() {
    g.begin();
    p.clear();
    // p.textScale(1);
    // p.strokeWeight(1);
    // p.textScale();
    // p.withScreen();
    p.textColor(224);
    // p.textSize(16);
    p.textScale(3);
    p.text(title,g.width()/3f,0);
    p.textScale(1);
    p.textColor(191);
    // p.text(slogan[sloganPos],g.width()/3f*2-p.textWidthNoScale(slogan[sloganPos])*2,textSize*1.33f);
    p.text(slogan[sloganPos],g.width()/2f,textSize*1.33f);
    p.textScale(1);
    g.end();
  }
  @Override
  public void update() {
    point.update();
  }
  @Override
  public void display() {
    p.image(g.texture,point.pos.x-g.width()/2,point.pos.y-g.height()/2);
  }
}
