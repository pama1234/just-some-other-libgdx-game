package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.math.Vector2;

public class VerticesUtils{

  /**
   * @param vertices float[] 原始的顶点坐标
   *                 矩形变菱形 传入参数最好是原始的矩形坐标，矩形坐标作为参数好计算得到传进来，然后这里偏移进行变成菱形坐标
   * @return 转换成世界坐标
   */
  public static float[] toOffsetVertices(float[] vertices) {
    float[] worldVertices=new float[vertices.length];
    float yOff=8;
    for(int i=0;i<vertices.length;i+=2) {
      Vector2 worldPos=worldToVertices(vertices[i],vertices[i+1]);
      worldVertices[i]=worldPos.x;
      worldVertices[i+1]=worldPos.y+yOff;
    }
    return worldVertices;
  }
  public static Vector2 worldToVertices(float x,float y) {
    return worldToVertices(new Vector2(x,y));
  }

  public static Vector2 worldToVertices(Vector2 p) {
    Vector2 real=new Vector2(0,0);
    //this seems to turn things around for the creature ways not the debug collision physic world
    real.x+=p.x;
    real.y-=0.5*p.x;
    real.x+=p.y;
    real.y+=0.5*p.y;
    return real;
  }
}
