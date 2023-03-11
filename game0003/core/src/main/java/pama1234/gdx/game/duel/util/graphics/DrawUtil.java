package pama1234.gdx.game.duel.util.graphics;

import pama1234.gdx.game.duel.Duel;

public class DrawUtil{
  public static void displayDemo(Duel duel) {
    int fu=Duel.INTERNAL_CANVAS_SIDE_LENGTH;
    duel.pushStyle();
    duel.stroke(0);
    duel.strokeWeight(2);
    duel.doFill();
    duel.beginBlend();
    duel.fill(255,200);
    duel.rect(fu*0.05f,fu*0.15f,
      fu*0.9f,
      fu*0.7f);
    duel.endBlend();
    duel.setTextColor(0);
    // duel.drawText("    Z key:",200,180);
    // duel.drawText("    X key:",200,250);
    // duel.drawText("Arrow key:",200,345);
    // duel.drawText("Weak shot\n (auto aiming)",300,180);
    // duel.drawText("Lethal shot\n (manual aiming,\n  requires charge)",300,250);
    // duel.drawText("Move\n (or aim lethal shot)",300,345);
    // duel.drawText("- Press Z key to start -",fu*0.3f,430);
    // duel.drawText("(Click to hide this window)",fu*0.3f,475);
    duel.drawText("      Z 按键:",180,180);
    duel.drawText("      X 按键:",180,250);
    duel.drawText("左手触摸屏幕:",180,345);
    duel.drawText("普通攻击\n (自动瞄准)",300,180);
    duel.drawText("致命大招\n (手动瞄准,\n  需要蓄力)",300,250);
    duel.drawText("移动\n (或使用大招时进行瞄准)",300,345);
    duel.drawText("- 按 Z 键开始游戏 -",fu*0.3f,430);
    duel.drawText("(轻触显示或隐藏此界面)",fu*0.3f,460);
    duel.setTextColor(92);
    duel.drawText("由FAL制作！( https://www.fal-works.com/ )",70,480);
    duel.drawText("由Pama1234移植到安卓版！( https://space.bilibili.com/646050693 )",70,500);
    duel.drawText("原型版本，视觉BUG很多，敬请关注此开源项目！会更新联机版！",70,520);
    duel.popStyle();
    duel.strokeWeight(1);
  }
}