package processing.opengl;

///////////////////////////////////////////////////////////////////////////
// Parameters object
/**
 * This class stores the parameters for a texture: target, internal format, minimization filter
 * and magnification filter.
 */
public class TextureParameters{
  /**
   * Texture target.
   */
  public int target;
  /**
   * Texture internal format.
   */
  public int format;
  /**
   * Texture filtering (POINT, LINEAR, BILINEAR or TRILINEAR).
   */
  public int sampling;
  /**
   * Use mipmaps or not.
   */
  public boolean mipmaps;
  /**
   * Wrapping mode along U.
   */
  public int wrapU;
  /**
   * Wrapping mode along V.
   */
  public int wrapV;
  /**
   * Sets all the parameters to default values.
   */
  public TextureParameters() {
    this.target=Texture.TEX2D;
    this.format=Texture.ARGB;
    this.sampling=Texture.BILINEAR;
    this.mipmaps=true;
    this.wrapU=Texture.CLAMP;
    this.wrapV=Texture.CLAMP;
  }
  public TextureParameters(int format) {
    this.target=Texture.TEX2D;
    this.format=format;
    this.sampling=Texture.BILINEAR;
    this.mipmaps=true;
    this.wrapU=Texture.CLAMP;
    this.wrapV=Texture.CLAMP;
  }
  public TextureParameters(int format,int sampling) {
    this.target=Texture.TEX2D;
    this.format=format;
    this.sampling=sampling;
    this.mipmaps=true;
    this.wrapU=Texture.CLAMP;
    this.wrapV=Texture.CLAMP;
  }
  public TextureParameters(int format,int sampling,boolean mipmaps) {
    this.target=Texture.TEX2D;
    this.format=format;
    this.mipmaps=mipmaps;
    if(sampling==Texture.TRILINEAR&&!mipmaps) {
      this.sampling=Texture.BILINEAR;
    }else {
      this.sampling=sampling;
    }
    this.wrapU=Texture.CLAMP;
    this.wrapV=Texture.CLAMP;
  }
  public TextureParameters(int format,int sampling,boolean mipmaps,int wrap) {
    this.target=Texture.TEX2D;
    this.format=format;
    this.mipmaps=mipmaps;
    if(sampling==Texture.TRILINEAR&&!mipmaps) {
      this.sampling=Texture.BILINEAR;
    }else {
      this.sampling=sampling;
    }
    this.wrapU=wrap;
    this.wrapV=wrap;
  }
  public TextureParameters(TextureParameters src) {
    set(src);
  }
  public void set(int format) {
    this.format=format;
  }
  public void set(int format,int sampling) {
    this.format=format;
    this.sampling=sampling;
  }
  public void set(int format,int sampling,boolean mipmaps) {
    this.format=format;
    this.sampling=sampling;
    this.mipmaps=mipmaps;
  }
  public void set(TextureParameters src) {
    this.target=src.target;
    this.format=src.format;
    this.sampling=src.sampling;
    this.mipmaps=src.mipmaps;
    this.wrapU=src.wrapU;
    this.wrapV=src.wrapV;
  }
}