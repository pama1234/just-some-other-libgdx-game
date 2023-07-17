package pama1234.gdx.util.element;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class UtilShapeRenderer extends ShapeRenderer{
  public void arcNoBorder(float x,float y,float radius,float start,float degrees,int segments) {
    if(segments<=0) throw new IllegalArgumentException("segments must be > 0.");
    float colorBits=getColor().toFloatBits();
    float theta=(2*MathUtils.PI*(degrees/360.0f))/segments;
    float cos=MathUtils.cos(theta);
    float sin=MathUtils.sin(theta);
    float cx=radius*MathUtils.cos(start*MathUtils.degreesToRadians);
    float cy=radius*MathUtils.sin(start*MathUtils.degreesToRadians);
    ImmediateModeRenderer renderer=getRenderer();
    if(getCurrentType()==ShapeType.Line) {
      check(ShapeType.Line,ShapeType.Filled,segments*2);
      // renderer.color(colorBits);
      // renderer.vertex(x,y,0);
      // renderer.color(colorBits);
      // renderer.vertex(x+cx,y+cy,0);
      for(int i=0;i<segments;i++) {
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
        float temp=cx;
        cx=cos*cx-sin*cy;
        cy=sin*temp+cos*cy;
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
      }
      // renderer.color(colorBits);
      // renderer.vertex(x+cx,y+cy,0);
    }else {
      check(ShapeType.Line,ShapeType.Filled,segments*3+3);
      for(int i=0;i<segments;i++) {
        renderer.color(colorBits);
        renderer.vertex(x,y,0);
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
        float temp=cx;
        cx=cos*cx-sin*cy;
        cy=sin*temp+cos*cy;
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
      }
      renderer.color(colorBits);
      renderer.vertex(x,y,0);
      renderer.color(colorBits);
      renderer.vertex(x+cx,y+cy,0);
    }
    // float temp=cx;
    cx=0;
    cy=0;
    renderer.color(colorBits);
    renderer.vertex(x+cx,y+cy,0);
  }
  @Override
  public void polygon(float[] vertices) {
    polygon(vertices,0,vertices.length);
  }
  @Override
  public void polygon(float[] vertices,int offset,int count) {
    polygon(vertices,offset,count,0,0);
  }
  public void polygon(float[] vertices,int offset,int count,float x,float y) {
    if(count<6) throw new IllegalArgumentException("Polygons must contain at least 3 points.");
    if(count%2!=0) throw new IllegalArgumentException("Polygons must have an even number of vertices.");
    check(ShapeType.Line,ShapeType.Filled,count);
    float colorBits=getColor().toFloatBits();
    float firstX=vertices[0];
    float firstY=vertices[1];
    ImmediateModeRenderer renderer=getRenderer();
    for(int i=offset,n=offset+count;i<n;i+=2) {
      float x1=vertices[i];
      float y1=vertices[i+1];
      float x2;
      float y2;
      if(i+2>=count) {
        x2=firstX;
        y2=firstY;
      }else {
        x2=vertices[i+2];
        y2=vertices[i+3];
      }
      renderer.color(colorBits);
      renderer.vertex(x1+x,y1+y,0);
      renderer.color(colorBits);
      renderer.vertex(x2+x,y2+y,0);
    }
  }
  public void polygon(PolygonRegion in,float x,float y) {
    float[] vertices=in.getVertices();
    polygon(vertices,0,vertices.length,x,y);
  }
  /** Draws a circle using {@link ShapeType#Line} or {@link ShapeType#Filled}. */
  @Override
  public void circle(float x,float y,float radius,int segments) {
    if(segments<=0) throw new IllegalArgumentException("segments must be > 0.");
    float colorBits=getColor().toFloatBits();
    float angle=2*MathUtils.PI/segments;
    float cos=MathUtils.cos(angle);
    float sin=MathUtils.sin(angle);
    float cx=radius,cy=0;
    ImmediateModeRenderer renderer=getRenderer();
    if(getCurrentType()==ShapeType.Line) {
      check(ShapeType.Line,ShapeType.Filled,segments*2+2);
      for(int i=0;i<segments;i++) {
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
        float temp=cx;
        cx=cos*cx-sin*cy;
        cy=sin*temp+cos*cy;
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
      }
      // Ensure the last segment is identical to the first.
      renderer.color(colorBits);
      renderer.vertex(x+cx,y+cy,0);
    }else {
      check(ShapeType.Line,ShapeType.Filled,segments*3+3);
      segments--;
      for(int i=0;i<segments;i++) {
        renderer.color(colorBits);
        renderer.vertex(x,y,0);
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
        float temp=cx;
        cx=cos*cx-sin*cy;
        cy=sin*temp+cos*cy;
        renderer.color(colorBits);
        renderer.vertex(x+cx,y+cy,0);
      }
      // Ensure the last segment is identical to the first.
      renderer.color(colorBits);
      renderer.vertex(x,y,0);
      renderer.color(colorBits);
      renderer.vertex(x+cx,y+cy,0);
    }
    // float temp=cx;
    cx=radius;
    cy=0;
    renderer.color(colorBits);
    renderer.vertex(x+cx,y+cy,0);
  }
}