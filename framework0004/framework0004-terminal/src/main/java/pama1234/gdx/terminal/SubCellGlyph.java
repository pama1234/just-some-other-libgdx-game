package pama1234.gdx.terminal;

import com.badlogic.gdx.graphics.Color;

public final class SubCellGlyph{
  private float resetColor;
  private int defaultTopLeftValue;
  private int defaultTopRightValue;
  private int defaultBottomLeftValue;
  private int defaultBottomRightValue;
  @NotNull
  private final SubCellData[][] subCells;
  public final float getResetColor() {
    return this.resetColor;
  }
  public final void setResetColor(float var1) {
    this.resetColor=var1;
  }
  public final int getDefaultTopLeftValue() {
    return this.defaultTopLeftValue;
  }
  public final void setDefaultTopLeftValue(int var1) {
    this.defaultTopLeftValue=var1;
  }
  public final int getDefaultTopRightValue() {
    return this.defaultTopRightValue;
  }
  public final void setDefaultTopRightValue(int var1) {
    this.defaultTopRightValue=var1;
  }
  public final int getDefaultBottomLeftValue() {
    return this.defaultBottomLeftValue;
  }
  public final void setDefaultBottomLeftValue(int var1) {
    this.defaultBottomLeftValue=var1;
  }
  public final int getDefaultBottomRightValue() {
    return this.defaultBottomRightValue;
  }
  public final void setDefaultBottomRightValue(int var1) {
    this.defaultBottomRightValue=var1;
  }
  @NotNull
  public final SubCellData[][] getSubCells() {
    return this.subCells;
  }
  public final void reset() {
    this.subCells[0][0].set(this.resetColor,this.defaultTopLeftValue);
    this.subCells[1][0].set(this.resetColor,this.defaultTopRightValue);
    this.subCells[0][1].set(this.resetColor,this.defaultBottomLeftValue);
    this.subCells[1][1].set(this.resetColor,this.defaultBottomRightValue);
  }
  @NotNull
  public final SubCellGlyph copy() {
    SubCellGlyph output=new SubCellGlyph(this.subCells[0][0].copy(),this.subCells[0][1].copy(),this.subCells[1][0].copy(),this.subCells[1][1].copy());
    output.resetColor=this.resetColor;
    output.defaultTopLeftValue=this.defaultTopLeftValue;
    output.defaultTopRightValue=this.defaultTopRightValue;
    output.defaultBottomLeftValue=this.defaultBottomLeftValue;
    output.defaultBottomRightValue=this.defaultBottomRightValue;
    return output;
  }
  public SubCellGlyph() {
    this.resetColor=Color.CLEAR.toFloatBits();
    this.defaultTopLeftValue=257;
    this.defaultTopRightValue=258;
    this.defaultBottomLeftValue=260;
    this.defaultBottomRightValue=259;
    byte var1=2;
    SubCellData[][] var2=new SubCellData[var1][];
    for(int var3=0;var3<var1;++var3) {
      int var5=0;
      byte var6=2;
      SubCellData[] var7=new SubCellData[var6];
      for(int var8=0;var8<var6;++var8) {
        int var12=0;
        SubCellData var13=new SubCellData(this.resetColor,this.defaultTopLeftValue);
        var7[var8]=var13;
      }
      var2[var3]=var7;
    }
    this.subCells=(SubCellData[][])var2;
  }
  public SubCellGlyph(@NotNull SubCellData topLeft,@NotNull SubCellData topRight,@NotNull SubCellData bottomLeft,@NotNull SubCellData bottomRight) {
    // Intrinsics.checkParameterIsNotNull(topLeft,"topLeft");
    // Intrinsics.checkParameterIsNotNull(topRight,"topRight");
    // Intrinsics.checkParameterIsNotNull(bottomLeft,"bottomLeft");
    // Intrinsics.checkParameterIsNotNull(bottomRight,"bottomRight");
    this();
    this.subCells[0][0]=topLeft;
    this.subCells[1][0]=topRight;
    this.subCells[0][1]=bottomLeft;
    this.subCells[1][1]=bottomRight;
  }
  public final class SubCellData{
    private float color;
    private int value;
    public final void set(float color,int value) {
      this.color=color;
      this.value=value;
    }
    @NotNull
    public final SubCellData copy() {
      return SubCellGlyph.this.new SubCellData(this.color,this.value);
    }
    public final float getColor() {
      return this.color;
    }
    public final void setColor(float var1) {
      this.color=var1;
    }
    public final int getValue() {
      return this.value;
    }
    public final void setValue(int var1) {
      this.value=var1;
    }
    public SubCellData(float color,int value) {
      this.color=color;
      this.value=value;
    }
    // $FF: synthetic method
    public SubCellData(float var2,int var3,int var4) {
      if((var4&1)!=0) {
        var2=Color.CLEAR.toFloatBits();
      }
      if((var4&2)!=0) {
        var3=0;
      }
      // this(var2,var3);
    }
  }
}
