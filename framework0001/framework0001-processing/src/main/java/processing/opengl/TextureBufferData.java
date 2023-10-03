package processing.opengl;

import java.nio.IntBuffer;

/**
 * This class stores a buffer copied from the buffer source.
 *
 */
class TextureBufferData{
  /**
   *
   */
  private final Texture texture;
  int w,h;
  // Native buffer object.
  Object natBuf;
  // Buffer viewed as int.
  IntBuffer rgbBuf;
  TextureBufferData(Texture texture,Object nat,IntBuffer rgb,int w,int h) {
    this.texture=texture;
    natBuf=nat;
    rgbBuf=rgb;
    this.w=w;
    this.h=h;
  }
  void dispose() {
    try {
      // Disposing the native buffer.
      this.texture.disposeBufferMethod.invoke(this.texture.bufferSource,natBuf);
      natBuf=null;
      rgbBuf=null;
    }catch(Exception e) {
      e.printStackTrace();
    }
  }
}