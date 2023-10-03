package processing.opengl;

import processing.opengl.PGraphicsOpenGL.Disposable;

class GLResourceVertexBuffer extends Disposable<VertexBuffer>{
  int glId;
  private PGL pgl;
  final private int context;
  public GLResourceVertexBuffer(VertexBuffer vbo) {
    super(vbo);
    pgl=vbo.pgl.graphics.getPrimaryPGL();
    pgl.genBuffers(1,PGraphicsOpenGL.intBuffer);
    vbo.glId=PGraphicsOpenGL.intBuffer.get(0);
    this.glId=vbo.glId;
    this.context=vbo.context;
  }
  @Override
  public void disposeNative() {
    if(pgl!=null) {
      if(glId!=0) {
        PGraphicsOpenGL.intBuffer.put(0,glId);
        pgl.deleteBuffers(1,PGraphicsOpenGL.intBuffer);
        glId=0;
      }
      pgl=null;
    }
  }
  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof GLResourceVertexBuffer)) {
      return false;
    }
    GLResourceVertexBuffer other=(GLResourceVertexBuffer)obj;
    return other.glId==glId&&
      other.context==context;
  }
  @Override
  public int hashCode() {
    int result=17;
    result=31*result+glId;
    result=31*result+context;
    return result;
  }
}