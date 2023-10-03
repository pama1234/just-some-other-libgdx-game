package processing.opengl;

import processing.opengl.PGraphicsOpenGL.Disposable;

//////////////////////////////////////////////////////////////
class GLResourceTexture extends Disposable<Texture>{
  int glName;
  private PGL pgl;
  private final int context;
  public GLResourceTexture(Texture tex) {
    super(tex);
    pgl=tex.pg.getPrimaryPGL();
    pgl.genTextures(1,PGraphicsOpenGL.intBuffer);
    tex.glName=PGraphicsOpenGL.intBuffer.get(0);
    this.glName=tex.glName;
    this.context=tex.context;
  }
  @Override
  public void disposeNative() {
    if(pgl!=null) {
      if(glName!=0) {
        PGraphicsOpenGL.intBuffer.put(0,glName);
        pgl.deleteTextures(1,PGraphicsOpenGL.intBuffer);
        glName=0;
      }
      pgl=null;
    }
  }
  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof GLResourceTexture)) {
      return false;
    }
    GLResourceTexture other=(GLResourceTexture)obj;
    return other.glName==glName&&
      other.context==context;
  }
  @Override
  public int hashCode() {
    int result=17;
    result=31*result+glName;
    result=31*result+context;
    return result;
  }
}