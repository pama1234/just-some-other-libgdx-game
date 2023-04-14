package pama1234.gdx.game.app.app0004;

import org.locationtech.jts.algorithm.Angle;
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
    Tools.time();
    for(int i=0;i<1024;i++) test_3();
    System.out.println(Tools.period()+"ms");//ms
    Tools.time();
    for(int i=0;i<1024;i++) test_4();
    System.out.println(Tools.period()+"ms");//ms
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
    // boolean isInTriangle=
    triangle.contains(point);
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
    // Polygon triangle=new Polygon(new float[] {p1.x,p1.y,p2.x,p2.y,p3.x,p3.y});
    // 构造待判断的点对象
    Vector2 point=new Vector2(1,1);
    // 判断点是否在三角形内
    // boolean isInTriangle=
    Intersector.isPointInTriangle(point,p1,p2,p3);
    // if(isInTriangle) {
    //   System.out.println("点在三角形内");
    // }else {
    //   System.out.println("点不在三角形内");
    // }
  }
  public void test_3() {
    Coordinate center=new Coordinate(0,0);
    double radius=5;
    double startAngle=Math.toRadians(30);
    double endAngle=Math.toRadians(120);
    // 根据起始角度和结束角度计算扇形的顶点坐标数组
    int segments=30;
    Coordinate[] coords=new Coordinate[segments+3];
    coords[0]=center;
    for(int i=0;i<=segments;i++) {
      double angle=startAngle+(endAngle-startAngle)*((double)i/segments);
      double x=center.x+radius*Math.cos(angle);
      double y=center.y+radius*Math.sin(angle);
      coords[i+1]=new Coordinate(x,y);
    }
    coords[segments+2]=center;
    // 构造扇形对象
    // org.locationtech.jts.geom.Polygon sector=new GeometryFactory().createPolygon(coords);
    // 构造待判断的点对象
    Point point=new GeometryFactory().createPoint(new Coordinate(1,1));
    // 判断点是否在扇形内
    double angle=Angle.angle(center,point.getCoordinate());
    // boolean isInSector=
    isInSector(center,radius,startAngle,endAngle,point,angle);
    // Distance.pointToSegment(point.getCoordinate(),center,(Coordinate)sector.getExteriorRing().getCoordinateSequence())<=radius;
    // if(isInSector) {
    //   System.out.println("点在扇形内");
    // }else {
    //   System.out.println("点不在扇形内");
    // }
  }
  private boolean isInSector(Coordinate center,double radius,double startAngle,double endAngle,Point point,double angle) {
    return angle>=startAngle&&angle<=endAngle&&
      point.getCoordinate().distance(center)<=radius;
  }
  public void test_4() {
    // 构造扇形的中心点、半径、起始角度和结束角度
    Vector2 center=new Vector2(0,0);
    float radius=5;
    float startAngle=30;
    float endAngle=120;
    // 构造扇形的顶点坐标数组
    int segments=30;
    float[] vertices=new float[(segments+1)*2];
    vertices[0]=center.x;
    vertices[1]=center.y;
    for(int i=0;i<segments;i++) {
      float angle=startAngle+(endAngle-startAngle)*((float)i/segments);
      vertices[(i+1)*2]=center.x+radius*(float)Math.cos(Math.toRadians(angle));
      vertices[(i+1)*2+1]=center.y+radius*(float)Math.sin(Math.toRadians(angle));
    }
    // 构造扇形对象
    Polygon sector=new Polygon(vertices);
    // 构造待判断的点对象
    Vector2 point=new Vector2(1,1);
    // 判断点是否在扇形内
    // boolean isInSector=
    Intersector.isPointInPolygon(sector.getTransformedVertices(),0,vertices.length,point.x,point.y);
    // if(isInSector) {
    //   System.out.println("点在扇形内");
    // }else {
    //   System.out.println("点不在扇形内");
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