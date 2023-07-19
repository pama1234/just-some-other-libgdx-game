package pama1234.gdx.util.box2d;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.element.UtilPolygonSpriteBatch;
import pama1234.gdx.util.entity.Entity;

public class BodyEntity<T extends UtilScreen>extends Entity<T>{
  public Body body;
  public ArrayList<FixtureData> fixtureList;
  public BodyEntity(T p,Body body) {
    super(p);
    this.body=body;
    fixtureList=new ArrayList<>();
    updateFixture();
  }
  public void updateFixture() {
    fixtureList.clear();
    Fixture fixture=body.getFixtureList();
    while(fixture!=null) {
      fixtureList.add(FixtureData.getNew(p,fixture));
      fixture=fixture.getNext();
    }
  }
  @Override
  public void display() {
    Vec2 pos=body.getPosition();
    p.pushMatrix();
    p.translate(pos.x,pos.y);
    p.rotate(body.getAngle());
    for(FixtureData i:fixtureList) i.display(p);
    p.popMatrix();
  }
  public static class FixtureData{
    public static FixtureData getNew(UtilScreen p,Fixture fixture) {
      return new FixtureData(p,fixture);
    }
    public Fixture fixture;
    // public Shape shape;
    //---
    public EdgeShape edgeShape;
    public PolygonRegion polygon;
    public FixtureData(UtilScreen p,Fixture fixture) {
      this.fixture=fixture;
      Shape shape=shape();
      switch(shape.getType()) {
        case CIRCLE: {}
          break;
        case EDGE: {
          edgeShape=(EdgeShape)shape;
        }
          break;
        case POLYGON: {
          PolygonShape ps=(PolygonShape)shape;
          Vec2[] vscs=ps.getVertices();
          int vertexCount=ps.getVertexCount();
          float[] vertices=new float[vertexCount*2];
          for(int i=0;i<vertexCount;i++) {
            Vec2 v=vscs[i];
            vertices[i*2]=v.x;
            vertices[i*2+1]=v.y;
          }
          polygon=new PolygonRegion(UtilPolygonSpriteBatch.defaultRegion,vertices,UtilPolygonSpriteBatch.earTriangulator.computeTriangles(vertices).toArray());
        }
          break;
        case CHAIN: {}
          break;
        default:
          break;
      }
    }
    public Shape shape() {
      return fixture.getShape();
    }
    public Body body() {
      return fixture.getBody();
    }
    public void display(UtilScreen p) {
      // Body body=body();
      Shape shape=shape();
      switch(shape.getType()) {
        case CIRCLE: {
          p.circle(0,0,shape.getRadius());
        }
          break;
        case EDGE: {
          p.line(edgeShape.m_vertex1.x,edgeShape.m_vertex1.y,edgeShape.m_vertex2.x,edgeShape.m_vertex2.y);
        }
          break;
        case POLYGON: {
          p.pushMatrix();
          p.polygon(polygon,0,0);
          p.popMatrix();
        }
          break;
        case CHAIN: {}
          break;
        default:
          break;
      }
    }
  }
}
