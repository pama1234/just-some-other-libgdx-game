package pama1234.gdx.game.app.app0004;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0017 extends ScreenCore2D{
  @Override
  public void setup() {
    Coordinate coord=new Coordinate(10,20);
    Point point=new GeometryFactory().createPoint(coord);
    System.out.println(point);
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