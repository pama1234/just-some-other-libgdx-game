package pama1234.gdx.terminal;

import com.badlogic.gdx.graphics.Color;

public final class KTerminalGlyph{
  private boolean isSubCellEnabled;
  @NotNull
  private SubCellGlyph subCellGlyph;
  private int value;
  private float foregroundColor;
  private float backgroundColor;
  private float rotation;
  private float scale;
  private float offsetX;
  private float offsetY;
  private boolean isFlippedX;
  private boolean isFlippedY;
  public final char getChar() {
    return (char)this.value;
  }
  public final void setChar(char input) {
    this.value=input;
  }
  public final boolean isSubCellEnabled() {
    return this.isSubCellEnabled;
  }
  public final void setSubCellEnabled(boolean var1) {
    this.isSubCellEnabled=var1;
  }
  @NotNull
  public final SubCellGlyph getSubCellGlyph() {
    return this.subCellGlyph;
  }
  public final void setSubCellGlyph(@NotNull SubCellGlyph var1) {
    // Intrinsics.checkParameterIsNotNull(var1,"<set-?>");
    this.subCellGlyph=var1;
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    this.value=value;
    this.foregroundColor=foregroundColor;
    this.backgroundColor=backgroundColor;
    this.rotation=rotation;
    this.scale=scale;
    this.offsetX=offsetX;
    this.offsetY=offsetY;
    this.isFlippedX=isFlippedX;
    this.isFlippedY=isFlippedY;
    return this;
  }
  // $FF: synthetic method
  public static KTerminalGlyph set$default(KTerminalGlyph var0,int var1,float var2,float var3,float var4,float var5,float var6,float var7,boolean var8,boolean var9,int var10,Object var11) {
    if((var10&8)!=0) {
      var4=0.0F;
    }
    if((var10&16)!=0) {
      var5=1.0F;
    }
    if((var10&32)!=0) {
      var6=0.0F;
    }
    if((var10&64)!=0) {
      var7=0.0F;
    }
    if((var10&128)!=0) {
      var8=false;
    }
    if((var10&256)!=0) {
      var9=false;
    }
    return var0.set(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX) {
    return set$default(this,(int)value,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,false,256,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY) {
    return set$default(this,(int)value,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,false,false,384,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX) {
    return set$default(this,(int)value,foregroundColor,backgroundColor,rotation,scale,offsetX,0.0F,false,false,448,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor,float rotation,float scale) {
    return set$default(this,(int)value,foregroundColor,backgroundColor,rotation,scale,0.0F,0.0F,false,false,480,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor,float rotation) {
    return set$default(this,(int)value,foregroundColor,backgroundColor,rotation,0.0F,0.0F,0.0F,false,false,496,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(int value,float foregroundColor,float backgroundColor) {
    return set$default(this,(int)value,foregroundColor,backgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,504,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    return this.set((int)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
  }
  // $FF: synthetic method
  public static KTerminalGlyph set$default(KTerminalGlyph var0,char var1,float var2,float var3,float var4,float var5,float var6,float var7,boolean var8,boolean var9,int var10,Object var11) {
    if((var10&8)!=0) {
      var4=0.0F;
    }
    if((var10&16)!=0) {
      var5=1.0F;
    }
    if((var10&32)!=0) {
      var6=0.0F;
    }
    if((var10&64)!=0) {
      var7=0.0F;
    }
    if((var10&128)!=0) {
      var8=false;
    }
    if((var10&256)!=0) {
      var9=false;
    }
    return var0.set(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX) {
    return set$default(this,(char)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,false,256,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY) {
    return set$default(this,(char)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,false,false,384,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX) {
    return set$default(this,(char)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,0.0F,false,false,448,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor,float rotation,float scale) {
    return set$default(this,(char)var1,foregroundColor,backgroundColor,rotation,scale,0.0F,0.0F,false,false,480,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor,float rotation) {
    return set$default(this,(char)var1,foregroundColor,backgroundColor,rotation,0.0F,0.0F,0.0F,false,false,496,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(char var1,float foregroundColor,float backgroundColor) {
    return set$default(this,(char)var1,foregroundColor,backgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,504,(Object)null);
  }
  @NotNull
  public final KTerminalGlyph set(@NotNull KTerminalGlyph glyph) {
    // Intrinsics.checkParameterIsNotNull(glyph,"glyph");
    return this.set(glyph.value,glyph.foregroundColor,glyph.backgroundColor,glyph.rotation,glyph.scale,glyph.offsetX,glyph.offsetY,glyph.isFlippedX,glyph.isFlippedY);
  }
  @NotNull
  public final KTerminalGlyph copy() {
    KTerminalGlyph output=new KTerminalGlyph(this.value,this.foregroundColor,this.backgroundColor,this.rotation,this.scale,this.offsetX,this.offsetY,this.isFlippedX,this.isFlippedY);
    output.isSubCellEnabled=this.isSubCellEnabled;
    output.subCellGlyph=this.subCellGlyph.copy();
    return output;
  }
  @NotNull
  public final KTerminalGlyph reset() {
    this.value=0;
    this.foregroundColor=Color.CLEAR.toFloatBits();
    this.backgroundColor=Color.CLEAR.toFloatBits();
    this.rotation=0.0F;
    this.scale=1.0F;
    this.offsetX=0.0F;
    this.offsetY=0.0F;
    this.isFlippedX=false;
    this.isFlippedY=false;
    this.isSubCellEnabled=false;
    this.subCellGlyph=new SubCellGlyph();
    return this;
  }
  public final int getValue() {
    return this.value;
  }
  public final void setValue(int var1) {
    this.value=var1;
  }
  public final float getForegroundColor() {
    return this.foregroundColor;
  }
  public final void setForegroundColor(float var1) {
    this.foregroundColor=var1;
  }
  public final float getBackgroundColor() {
    return this.backgroundColor;
  }
  public final void setBackgroundColor(float var1) {
    this.backgroundColor=var1;
  }
  public final float getRotation() {
    return this.rotation;
  }
  public final void setRotation(float var1) {
    this.rotation=var1;
  }
  public final float getScale() {
    return this.scale;
  }
  public final void setScale(float var1) {
    this.scale=var1;
  }
  public final float getOffsetX() {
    return this.offsetX;
  }
  public final void setOffsetX(float var1) {
    this.offsetX=var1;
  }
  public final float getOffsetY() {
    return this.offsetY;
  }
  public final void setOffsetY(float var1) {
    this.offsetY=var1;
  }
  public final boolean isFlippedX() {
    return this.isFlippedX;
  }
  public final void setFlippedX(boolean var1) {
    this.isFlippedX=var1;
  }
  public final boolean isFlippedY() {
    return this.isFlippedY;
  }
  public final void setFlippedY(boolean var1) {
    this.isFlippedY=var1;
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    this.value=value;
    this.foregroundColor=foregroundColor;
    this.backgroundColor=backgroundColor;
    this.rotation=rotation;
    this.scale=scale;
    this.offsetX=offsetX;
    this.offsetY=offsetY;
    this.isFlippedX=isFlippedX;
    this.isFlippedY=isFlippedY;
    this.subCellGlyph=new SubCellGlyph();
  }
  // $FF: synthetic method
  public KTerminalGlyph(int var1,float var2,float var3,float var4,float var5,float var6,float var7,boolean var8,boolean var9,int var10,DefaultConstructorMarker var11) {
    if((var10&1)!=0) {
      var1=0;
    }
    if((var10&2)!=0) {
      var2=Color.CLEAR.toFloatBits();
    }
    if((var10&4)!=0) {
      var3=Color.CLEAR.toFloatBits();
    }
    if((var10&8)!=0) {
      var4=0.0F;
    }
    if((var10&16)!=0) {
      var5=1.0F;
    }
    if((var10&32)!=0) {
      var6=0.0F;
    }
    if((var10&64)!=0) {
      var7=0.0F;
    }
    if((var10&128)!=0) {
      var8=false;
    }
    if((var10&256)!=0) {
      var9=false;
    }
    this(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX) {
    this((int)value,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,false,256,null);
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY) {
    this((int)value,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,false,false,384,null);
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX) {
    this((int)value,foregroundColor,backgroundColor,rotation,scale,offsetX,0.0F,false,false,448,null);
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor,float rotation,float scale) {
    this((int)value,foregroundColor,backgroundColor,rotation,scale,0.0F,0.0F,false,false,480,null);
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor,float rotation) {
    this((int)value,foregroundColor,backgroundColor,rotation,0.0F,0.0F,0.0F,false,false,496,null);
  }
  public KTerminalGlyph(int value,float foregroundColor,float backgroundColor) {
    this((int)value,foregroundColor,backgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,504,null);
  }
  public KTerminalGlyph(int value,float foregroundColor) {
    this((int)value,foregroundColor,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,508,null);
  }
  public KTerminalGlyph(int value) {
    this((int)value,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,510,null);
  }
  public KTerminalGlyph() {
    this((int)0,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,511,null);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    this((int)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
  }
  // $FF: synthetic method
  public KTerminalGlyph(char var1,float var2,float var3,float var4,float var5,float var6,float var7,boolean var8,boolean var9,int var10,DefaultConstructorMarker var11) {
    if((var10&8)!=0) {
      var4=0.0F;
    }
    if((var10&16)!=0) {
      var5=1.0F;
    }
    if((var10&32)!=0) {
      var6=0.0F;
    }
    if((var10&64)!=0) {
      var7=0.0F;
    }
    if((var10&128)!=0) {
      var8=false;
    }
    if((var10&256)!=0) {
      var9=false;
    }
    this(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX) {
    this((char)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,false,256,null);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY) {
    this((char)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,false,false,384,null);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX) {
    this((char)var1,foregroundColor,backgroundColor,rotation,scale,offsetX,0.0F,false,false,448,null);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor,float rotation,float scale) {
    this((char)var1,foregroundColor,backgroundColor,rotation,scale,0.0F,0.0F,false,false,480,null);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor,float rotation) {
    this((char)var1,foregroundColor,backgroundColor,rotation,0.0F,0.0F,0.0F,false,false,496,null);
  }
  public KTerminalGlyph(char var1,float foregroundColor,float backgroundColor) {
    this((char)var1,foregroundColor,backgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,504,null);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    this((int)0,Color.CLEAR.toFloatBits(),Color.CLEAR.toFloatBits(),rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
    this.subCellGlyph.getSubCells()[0][0].setColor(topLeftColor);
    this.subCellGlyph.getSubCells()[1][0].setColor(topRightColor);
    this.subCellGlyph.getSubCells()[0][1].setColor(bottomLeftColor);
    this.subCellGlyph.getSubCells()[1][1].setColor(bottomRightColor);
    this.isSubCellEnabled=true;
  }
  // $FF: synthetic method
  public KTerminalGlyph(float var1,float var2,float var3,float var4,float var5,float var6,float var7,float var8,boolean var9,boolean var10,int var11,DefaultConstructorMarker var12) {
    if((var11&16)!=0) {
      var5=0.0F;
    }
    if((var11&32)!=0) {
      var6=1.0F;
    }
    if((var11&64)!=0) {
      var7=0.0F;
    }
    if((var11&128)!=0) {
      var8=0.0F;
    }
    if((var11&256)!=0) {
      var9=false;
    }
    if((var11&512)!=0) {
      var10=false;
    }
    this(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX) {
    this(topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,rotation,scale,offsetX,offsetY,isFlippedX,false,512,null);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,float rotation,float scale,float offsetX,float offsetY) {
    this(topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,rotation,scale,offsetX,offsetY,false,false,768,null);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,float rotation,float scale,float offsetX) {
    this(topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,rotation,scale,offsetX,0.0F,false,false,896,null);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,float rotation,float scale) {
    this(topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,rotation,scale,0.0F,0.0F,false,false,960,null);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,float rotation) {
    this(topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,rotation,0.0F,0.0F,0.0F,false,false,992,null);
  }
  public KTerminalGlyph(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor) {
    this(topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,0.0F,0.0F,0.0F,0.0F,false,false,1008,null);
  }
  public final int component1() {
    return this.value;
  }
  public final float component2() {
    return this.foregroundColor;
  }
  public final float component3() {
    return this.backgroundColor;
  }
  public final float component4() {
    return this.rotation;
  }
  public final float component5() {
    return this.scale;
  }
  public final float component6() {
    return this.offsetX;
  }
  public final float component7() {
    return this.offsetY;
  }
  public final boolean component8() {
    return this.isFlippedX;
  }
  public final boolean component9() {
    return this.isFlippedY;
  }
  @NotNull
  public final KTerminalGlyph copy(int value,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    return new KTerminalGlyph(value,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
  }
  // $FF: synthetic method
  public static KTerminalGlyph copy$default(KTerminalGlyph var0,int var1,float var2,float var3,float var4,float var5,float var6,float var7,boolean var8,boolean var9,int var10,Object var11) {
    if((var10&1)!=0) {
      var1=var0.value;
    }
    if((var10&2)!=0) {
      var2=var0.foregroundColor;
    }
    if((var10&4)!=0) {
      var3=var0.backgroundColor;
    }
    if((var10&8)!=0) {
      var4=var0.rotation;
    }
    if((var10&16)!=0) {
      var5=var0.scale;
    }
    if((var10&32)!=0) {
      var6=var0.offsetX;
    }
    if((var10&64)!=0) {
      var7=var0.offsetY;
    }
    if((var10&128)!=0) {
      var8=var0.isFlippedX;
    }
    if((var10&256)!=0) {
      var9=var0.isFlippedY;
    }
    return var0.copy(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  @NotNull
  public String toString() {
    return "KTerminalGlyph(value="+this.value+", foregroundColor="+this.foregroundColor+", backgroundColor="+this.backgroundColor+", rotation="+this.rotation+", scale="+this.scale+", offsetX="+this.offsetX+", offsetY="+this.offsetY+", isFlippedX="+this.isFlippedX+", isFlippedY="+this.isFlippedY+")";
  }
  public int hashCode() {
    int var10000=((((((Integer.hashCode(this.value)*31+Float.hashCode(this.foregroundColor))*31+Float.hashCode(this.backgroundColor))*31+Float.hashCode(this.rotation))*31+Float.hashCode(this.scale))*31+Float.hashCode(this.offsetX))*31+Float.hashCode(this.offsetY))*31;
    byte var10001=(byte)(this.isFlippedX?1:0);
    if(var10001!=0) {
      var10001=1;
    }
    var10000=(var10000+var10001)*31;
    var10001=(byte)(this.isFlippedY?1:0);
    if(var10001!=0) {
      var10001=1;
    }
    return var10000+var10001;
  }
  public boolean equals(@Nullable Object var1) {
    if(this!=var1) {
      if(var1 instanceof KTerminalGlyph) {
        KTerminalGlyph var2=(KTerminalGlyph)var1;
        if(this.value==var2.value&&Float.compare(this.foregroundColor,var2.foregroundColor)==0&&Float.compare(this.backgroundColor,var2.backgroundColor)==0&&Float.compare(this.rotation,var2.rotation)==0&&Float.compare(this.scale,var2.scale)==0&&Float.compare(this.offsetX,var2.offsetX)==0&&Float.compare(this.offsetY,var2.offsetY)==0&&this.isFlippedX==var2.isFlippedX&&this.isFlippedY==var2.isFlippedY) {
          return true;
        }
      }
      return false;
    }else {
      return true;
    }
  }
}
