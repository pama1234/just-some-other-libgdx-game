package processing.opengl;

class TextureInfo{
  private final FontTexture fontTexture;
  int texIndex;
  int width;
  int height;
  int[] crop;
  float u0,u1;
  float v0,v1;
  int[] pixels;
  TextureInfo(FontTexture fontTexture,int tidx,int cropX,int cropY,int cropW,int cropH,
    int[] pix) {
    this.fontTexture=fontTexture;
    texIndex=tidx;
    crop=new int[4];
    // The region of the texture corresponding to the glyph is surrounded by a
    // 1-pixel wide border to avoid artifacts due to bilinear sampling. This
    // is why the additions and subtractions to the crop values.
    crop[0]=cropX+1;
    crop[1]=cropY+1+cropH-2;
    crop[2]=cropW-2;
    crop[3]=-cropH+2;
    pixels=pix;
    updateUV();
    updateTex();
  }
  void updateUV() {
    width=this.fontTexture.textures[texIndex].glWidth;
    height=this.fontTexture.textures[texIndex].glHeight;
    u0=(float)crop[0]/(float)width;
    u1=u0+(float)crop[2]/(float)width;
    v0=(float)(crop[1]+crop[3])/(float)height;
    v1=v0-(float)crop[3]/(float)height;
  }
  void updateTex() {
    this.fontTexture.textures[texIndex].setNative(pixels,crop[0]-1,crop[1]+crop[3]-1,
      crop[2]+2,-crop[3]+2);
  }
}