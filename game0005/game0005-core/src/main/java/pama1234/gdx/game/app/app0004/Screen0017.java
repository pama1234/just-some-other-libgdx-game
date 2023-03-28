package pama1234.gdx.game.app.app0004;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.math.Tools;

public class Screen0017 extends ScreenCore2D{
  @Override
  public void setup() {
    Tools.time();
    for(int i=0;i<1024;i++) test_1();
    System.out.println(Tools.period()+"ms");//161ms
    Tools.time();
    for(int i=0;i<1024;i++) test_2();
    System.out.println(Tools.period()+"ms");//4ms
  }
  public void test_1() {
    // Coordinate coord=new Coordinate(10,20);
    // Point temp=new GeometryFactory().createPoint(coord);
    // System.out.println(temp);
    //---
    // 构造三角形的三个顶点
    Coordinate p1=new Coordinate(0,0);
    Coordinate p2=new Coordinate(3,0);
    Coordinate p3=new Coordinate(0,3);
    // 构造三角形对象
    org.locationtech.jts.geom.Polygon triangle=new GeometryFactory().createPolygon(new Coordinate[] {p1,p2,p3,p1});
    // 构造待判断的点对象
    Point point=new GeometryFactory().createPoint(new Coordinate(1,1));
    // 判断点是否在三角形内
    boolean isInTriangle=triangle.contains(point);
    // if(isInTriangle) {
    //   System.out.println("点在三角形内");
    // }else {
    //   System.out.println("点不在三角形内");
    // }
  }
  public void test_2() {
    // 构造三角形的三个顶点
    Vector2 p1=new Vector2(0,0);
    Vector2 p2=new Vector2(3,0);
    Vector2 p3=new Vector2(0,3);
    // 构造三角形对象
    Polygon triangle=new Polygon(new float[] {p1.x,p1.y,p2.x,p2.y,p3.x,p3.y});
    // 构造待判断的点对象
    Vector2 point=new Vector2(1,1);
    // 判断点是否在三角形内
    boolean isInTriangle=Intersector.isPointInTriangle(point,p1,p2,p3);
    // if(isInTriangle) {
    //   System.out.println("点在三角形内");
    // }else {
    //   System.out.println("点不在三角形内");
    // }
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}