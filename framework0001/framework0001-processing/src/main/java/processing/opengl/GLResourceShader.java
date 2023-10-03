package processing.opengl;

import processing.opengl.PGraphicsOpenGL.Disposable;

class GLResourceShader extends Disposable<PShader>{
  int glProgram;
  int glVertex;
  int glFragment;
  private PGL pgl;
  final private int context;
  public GLResourceShader(PShader sh) {
    super(sh);
    this.pgl=sh.pgl.graphics.getPrimaryPGL();
    sh.glProgram=pgl.createProgram();
    sh.glVertex=pgl.createShader(PGL.VERTEX_SHADER);
    sh.glFragment=pgl.createShader(PGL.FRAGMENT_SHADER);
    this.glProgram=sh.glProgram;
    this.glVertex=sh.glVertex;
    this.glFragment=sh.glFragment;
    this.context=sh.context;
  }
  @Override
  public void disposeNative() {
    if(pgl!=null) {
      if(glFragment!=0) {
        pgl.deleteShader(glFragment);
        glFragment=0;
      }
      if(glVertex!=0) {
        pgl.deleteShader(glVertex);
        glVertex=0;
      }
      if(glProgram!=0) {
        pgl.deleteProgram(glProgram);
        glProgram=0;
      }
      pgl=null;
    }
  }
  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof GLResourceShader)) {
      return false;
    }
    GLResourceShader other=(GLResourceShader)obj;
    return other.glProgram==glProgram&&
      other.glVertex==glVertex&&
      other.glFragment==glFragment&&
      other.context==context;
  }
  @Override
  public int hashCode() {
    int result=17;
    result=31*result+glProgram;
    result=31*result+glVertex;
    result=31*result+glFragment;
    result=31*result+context;
    return result;
  }
}