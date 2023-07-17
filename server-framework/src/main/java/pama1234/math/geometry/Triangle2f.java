package pama1234.math.geometry;

import pama1234.math.vec.Vec2f;

public class Triangle2f{
  public Vec2f[] vertices;
  public Triangle2f(float x1,float y1,float x2,float y2,float x3,float y3) {
    vertices=new Vec2f[] {new Vec2f(x1,y1),new Vec2f(x2,y2),new Vec2f(x3,y3)};
  }
  public Triangle2f(Vec2f a,Vec2f b,Vec2f c) {
    vertices=new Vec2f[] {a,b,c};
  }
  public Vec2f getVertex(int index) {
    if(index<0||index>=3) {
      throw new IllegalArgumentException("Invalid index");
    }
    return vertices[index];
  }
}