package pama1234.gdx.game.duel.util.graphics;

import com.badlogic.gdx.graphics.Texture;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.UtilMath;

public class DemoInfo extends Entity<Duel>{
  public Graphics text;
  public DemoInfo(Duel p) {
    super(p);
    text=new Graphics(p,576,460);
    text.begin();
    p.beginShape();
    p.beginBlend();
    p.background(p.theme().background,200);
    p.doStroke();
    p.stroke(p.theme().stroke);
    p.strokeWeightOriginal(2);
    p.noFill();
    p.rect(0,0,text.texture.getWidth(),text.texture.getHeight());
    p.endBlend();
    p.endShape();
    p.setTextScale(1);
    p.strokeWeightOriginal(1);
    p.setTextColor(p.theme().text);
    // p.textColor(p.theme().text);
    if(p.isAndroid) drawText_ch_android(p,UtilMath.min(p.width,p.height));
    else drawText_ch(p,UtilMath.min(p.width,p.height));
    text.end();
  }
  public static void displayDemo(Duel p) {
    int fu=UtilMath.min(p.width,p.height);
    Texture img=p.demoInfo.text.texture;
    int tf=UtilMath.max(1,(int)(fu/img.getHeight()));
    int tw=img.getWidth()*tf;
    int th=img.getHeight()*tf;
    p.image(img,(p.width-tw)/2f,(p.height-th)/2f,tw,th);
  }
  public static void drawText_en(Duel p,int fu) {
    p.fullText("    Z key:",200,180);
    p.fullText("    X key:",200,250);
    p.fullText("Arrow key:",200,345);
    p.fullText("Weak shot\n (auto aiming)",300,180);
    p.fullText("Lethal shot\n (manual aiming,\n  requires charge)",300,250);
    p.fullText("Move\n (or aim lethal shot)",300,345);
    p.fullText("- Press Z key to start -",192,430);
    p.fullText("(Click to hide this window)",192,475);
  }
  public static void drawText_ch(Duel p,int fu) {
    p.setTextScale(3);
    p.fullText("几何决斗！",180,20);
    p.setTextScale(1);
    p.fullText("      Z 按键:",180,100);
    p.fullText("      X 按键:",180,170);
    p.fullText("左手触摸屏幕:",180,245);
    p.fullText("普通攻击\n (自动瞄准)",300,100);
    p.fullText("致命大招\n (手动瞄准,\n  需要蓄力)",300,170);
    p.fullText("移动\n (或使用大招时进行瞄准)",300,245);
    p.fullText("- 按 Z 键开始游戏 -",192,330);
    p.fullText("(轻触显示或隐藏此界面)",192,360);
    p.setTextColor(p.theme().text,192);
    p.fullText("由FAL制作！( https://www.fal-works.com/ )",20,400);
    p.fullText("由Pama1234移植到安卓版！( https://space.bilibili.com/646050693 )",20,420);
    p.fullText("原型版本，视觉BUG很多，敬请关注此开源项目！会更新联机版！",20,440);
  }
  public static void drawText_ch_android(Duel p,int fu) {
    p.setTextScale(3);
    p.fullText("几何决斗！",180,20);
    p.setTextScale(2);
    p.fullText("      Z 按键:",60,100);
    p.fullText("      X 按键:",60,140);
    p.fullText("左手触摸屏幕:",60,190);

    p.fullText("普通攻击",300,100);
    p.fullText("致命大招",300,140);
    p.fullText("移动或瞄准",300,190);

    p.fullText("- 按 Z 键开始游戏 -",160,300);
    p.fullText("(轻触显示或隐藏此界面)",160,340);
    p.setTextColor(p.theme().text,192);
    p.setTextScale(1);
    p.fullText("由FAL制作！( https://www.fal-works.com/ )",20,380);
    p.fullText("由Pama1234移植到安卓版！( https://space.bilibili.com/646050693 )",20,400);
    p.fullText("敬请关注此开源项目！会更新联机版！",20,420);
  }
}