package pama1234.gdx.game.life.particle.life.particle.element;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.physics.PathPoint;

public class Welcome extends Entity<Screen0045>{
  private static final String[] slogan= {
    "————模拟虫子！",
    "————独立游戏！",
    "————自组织系统！",
    "————人生，在无聊和焦虑之间来回切换",
    "————Pama1234制作！"
  };
  public static String title="粒子生命：升天";
  public final PathPoint point;
  public Graphics g;
  public int textSize=64;
  public static int sloganPos;
  public Welcome(Screen0045 p,float x,float y) {
    super(p);
    point=new PathPoint(0,0,x,y);
  }
  public void refresh() {
    sloganPos=(int)p.random(slogan.length);
    if(g==null) g=new Graphics(p,(int)Math.ceil(p.textWidthNoScale(title)*9),textSize*2);
    drawLazyImage();
  }
  public void drawLazyImage() {
    g.begin();
    p.clear();
    p.textColor(224);
    p.textScale(3);
    p.text(title,g.width()/3f,0);
    p.textScale(1);
    p.textColor(191);
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
