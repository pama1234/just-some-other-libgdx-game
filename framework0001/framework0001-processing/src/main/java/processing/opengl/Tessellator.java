package processing.opengl;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUtessellator;
import com.jogamp.opengl.glu.GLUtessellatorCallbackAdapter;

class Tessellator implements TessellatorInterface{
  protected GLUtessellator tess;
  protected TessellatorCallbackInterface callback;
  protected GLUCallback gluCallback;
  public Tessellator(TessellatorCallbackInterface callback) {
    this.callback=callback;
    tess=GLU.gluNewTess();
    gluCallback=new GLUCallback();
    GLU.gluTessCallback(tess,GLU.GLU_TESS_BEGIN,gluCallback);
    GLU.gluTessCallback(tess,GLU.GLU_TESS_END,gluCallback);
    GLU.gluTessCallback(tess,GLU.GLU_TESS_VERTEX,gluCallback);
    GLU.gluTessCallback(tess,GLU.GLU_TESS_COMBINE,gluCallback);
    GLU.gluTessCallback(tess,GLU.GLU_TESS_ERROR,gluCallback);
  }
  @Override
  public void setCallback(int flag) {
    GLU.gluTessCallback(tess,flag,gluCallback);
  }
  @Override
  public void setWindingRule(int rule) {
    setProperty(GLU.GLU_TESS_WINDING_RULE,rule);
  }
  public void setProperty(int property,int value) {
    GLU.gluTessProperty(tess,property,value);
  }
  @Override
  public void beginPolygon() {
    beginPolygon(null);
  }
  @Override
  public void beginPolygon(Object data) {
    GLU.gluTessBeginPolygon(tess,data);
  }
  @Override
  public void endPolygon() {
    GLU.gluTessEndPolygon(tess);
  }
  @Override
  public void beginContour() {
    GLU.gluTessBeginContour(tess);
  }
  @Override
  public void endContour() {
    GLU.gluTessEndContour(tess);
  }
  @Override
  public void addVertex(double[] v) {
    addVertex(v,0,v);
  }
  @Override
  public void addVertex(double[] v,int n,Object data) {
    GLU.gluTessVertex(tess,v,n,data);
  }
  protected class GLUCallback extends GLUtessellatorCallbackAdapter{
    @Override
    public void begin(int type) {
      callback.begin(type);
    }
    @Override
    public void end() {
      callback.end();
    }
    @Override
    public void vertex(Object data) {
      callback.vertex(data);
    }
    @Override
    public void combine(double[] coords,Object[] data,
      float[] weight,Object[] outData) {
      callback.combine(coords,data,weight,outData);
    }
    @Override
    public void error(int errnum) {
      callback.error(errnum);
    }
  }
}