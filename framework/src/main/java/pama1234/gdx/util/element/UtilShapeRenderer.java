package pama1234.gdx.util.element;

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
}
