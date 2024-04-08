package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * 地图tile互转世界坐标
 * 世界坐标是啥？就是你绘制角色对象用的坐标
 */
public class Isometric2{

  public static Vector2 tile2world(Vector2 p) {
    Vector2 map=new Vector2(p.x,p.y);
    Vector2 screen=new Vector2();
    screen.x=(map.x+map.y)*32;
    screen.y=-(map.x-map.y)*16;
    return screen;
  }

  public static Vector2 world2tile(Vector2 p) {
    p.x/=64;
    p.y=(p.y-32/2f)/32+p.x;
    p.x-=p.y-p.x;
    p.x=MathUtils.floor(p.x);
    p.y=MathUtils.floor(p.y);
    return p;
  }
}
