package pama1234.gdx.terminal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import java.nio.ByteBuffer;

public final class KTerminalRenderer implements Disposable{
  private Texture glyphTexture;
  private TextureRegion[] glyphs;
  private Texture backgroundTexture;
  private TextureRegion backgroundTextureRegion;
  private float glyphMaxSize;
  private int glyphWidth;
  private int glyphHeight;
  private float scaledGlyphHeight;
  private float scaledGlyphWidth;
  @NotNull
  private final SpriteBatch batch;
  @Nullable
  private String tilesetFile;
  private int columns;
  private int rows;
  public final int getGlyphWidth() {
    return this.glyphWidth;
  }
  public final int getGlyphHeight() {
    return this.glyphHeight;
  }
  private final void init(String tilesetFile, float scale) {
      if (tilesetFile == null) {
         this.columns = 16;
         this.rows = 17;
      }

      String var10000 = tilesetFile;
      if (tilesetFile == null) {
         var10000 = "defaultKTerminalFontSheet.png";
      }

      String tempTilesetFile = var10000;
      Pixmap pixmap = new Pixmap(Gdx.files.internal(tempTilesetFile));
      this.glyphWidth = pixmap.getWidth() / this.columns;
      this.glyphHeight = pixmap.getHeight() / this.rows;
      this.scaledGlyphWidth = (float)this.glyphWidth * scale;
      this.scaledGlyphHeight = (float)this.glyphHeight * scale;
      this.glyphMaxSize = (float)Math.sqrt((double)(this.scaledGlyphWidth * this.scaledGlyphWidth + this.scaledGlyphHeight * this.scaledGlyphHeight));
      Pixmap resultPixmap = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Format.RGBA8888);
      Pixmap whitePixmap = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Format.RGBA8888);
      whitePixmap.setColor(Color.WHITE);
      whitePixmap.fill();
      this.backgroundTexture = new Texture(whitePixmap);
      TextureRegion var10001 = new TextureRegion;
      Texture var10003 = this.backgroundTexture;
      if (var10003 == null) {
        //  // Intrinsics.throwUninitializedPropertyAccessException("backgroundTexture");
      }

      var10001.<init>(var10003);
      this.backgroundTextureRegion = var10001;
      whitePixmap.dispose();
      ByteBuffer var9 = pixmap.getPixels();
      // Intrinsics.checkExpressionValueIsNotNull(var9, "pixmap.pixels");
      ByteBuffer buffer = var9;
      var9 = resultPixmap.getPixels();
      // Intrinsics.checkExpressionValueIsNotNull(var9, "resultPixmap.pixels");
      ByteBuffer resultBuffer = var9;
      this.fillBuffers(buffer, resultBuffer, pixmap);
      pixmap.dispose();
      this.glyphTexture = new Texture(resultPixmap);
      resultPixmap.dispose();
      this.glyphs = this.getGlyphs();
   }
  // $FF: synthetic method
  static void init$default(KTerminalRenderer var0,String var1,float var2,int var3,Object var4) {
    if((var3&2)!=0) {
      var2=1.0F;
    }
    var0.init(var1,var2);
  }
  public final void set(@NotNull String tilesetFile,float scale) {
    // Intrinsics.checkParameterIsNotNull(tilesetFile,"tilesetFile");
    this.dispose();
    this.init(tilesetFile,scale);
  }
  // $FF: synthetic method
  public static void set$default(KTerminalRenderer var0,String var1,float var2,int var3,Object var4) {
    if((var3&2)!=0) {
      var2=1.0F;
    }
    var0.set(var1,var2);
  }
  private final void fillBuffers(ByteBuffer buffer,ByteBuffer resultBuffer,Pixmap pixmap) {
    buffer.rewind();
    resultBuffer.rewind();
    boolean start=true;
    byte rBackground=0;
    byte gBackground=0;
    byte bBackground=0;
    byte aBackground=1;
    while(true) {
      while(buffer.hasRemaining()) {
        byte r=buffer.get();
        byte g=buffer.get();
        byte b=buffer.get();
        byte a=-1;
        if(pixmap.getFormat()==Format.RGBA8888) {
          a=buffer.get();
        }
        if(start) {
          start=false;
          rBackground=r;
          gBackground=g;
          bBackground=b;
          aBackground=a;
        }
        if(r==rBackground&&g==gBackground&&b==bBackground&&a==aBackground) {
          resultBuffer.put((byte)0);
          resultBuffer.put((byte)0);
          resultBuffer.put((byte)0);
          resultBuffer.put((byte)0);
        }else {
          resultBuffer.put(r);
          resultBuffer.put(g);
          resultBuffer.put(b);
          resultBuffer.put(a);
        }
      }
      buffer.rewind();
      resultBuffer.rewind();
      return;
    }
  }
  private final TextureRegion[] getGlyphs() {
      TextureRegion[] glyphOutput = new TextureRegion[this.columns * this.rows];
      int counter = 0;
      int i = 0;

      for(int var4 = this.rows; i < var4; ++i) {
         int j = 0;

         for(int var6 = this.columns; j < var6; ++j) {
            TextureRegion var10002 = new TextureRegion;
            Texture var10004 = this.glyphTexture;
            if (var10004 == null) {
               // Intrinsics.throwUninitializedPropertyAccessException("glyphTexture");
            }

            var10002.<init>(var10004, j * this.glyphWidth, i * this.glyphHeight, this.glyphWidth, this.glyphHeight);
            glyphOutput[counter] = var10002;
            ++counter;
         }
      }

      return glyphOutput;
   }
  public final void render(final float x, final float y, @NotNull final KTerminalData kTerminalData) {
      // Intrinsics.checkParameterIsNotNull(kTerminalData, "kTerminalData");
      float originalColor = this.batch.getPackedColor();
      var $fun$draw$1 = new Function7() {
         // $FF: synthetic method
         // $FF: bridge method
         public Object invoke(Object var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7) {
            this.invoke((TextureRegion)var1, ((Number)var2).floatValue(), (KTerminalGlyph)var3, ((Number)var4).intValue(), ((Number)var5).intValue(), ((Number)var6).floatValue(), ((Number)var7).floatValue());
            return Unit.INSTANCE;
         }

         public final void invoke(@Nullable TextureRegion textureRegion, float glyphColor, @NotNull KTerminalGlyph glyph, int posX, int posY, float scaleX, float scaleY) {
            // Intrinsics.checkParameterIsNotNull(glyph, "glyph");
            KTerminalRenderer.this.getBatch().setPackedColor(glyphColor);
            KTerminalRenderer.this.getBatch().draw(textureRegion, x + (float)posX * KTerminalRenderer.this.scaledGlyphWidth + glyph.getOffsetX() * KTerminalRenderer.this.scaledGlyphWidth, y + (float)(kTerminalData.getHeight() - posY - 1) * KTerminalRenderer.this.scaledGlyphHeight + -glyph.getOffsetY() * KTerminalRenderer.this.scaledGlyphHeight, KTerminalRenderer.this.scaledGlyphWidth / (float)2, KTerminalRenderer.this.scaledGlyphHeight / (float)2, KTerminalRenderer.this.scaledGlyphWidth, KTerminalRenderer.this.scaledGlyphHeight, scaleX, scaleY, (-glyph.getRotation() + (float)90) % (float)360, true);
         }
      };
      int j = 0;

      int var7;
      int i;
      int var9;
      float scaleY;
      for(var7 = kTerminalData.getHeight(); j < var7; ++j) {
         i = 0;

         for(var9 = kTerminalData.getWidth(); i < var9; ++i) {
            boolean var11 = kTerminalData.getTerminal()[i][j].isSubCellEnabled();
            float var10000;
            if (var11) {
               var10000 = Color.CLEAR.toFloatBits();
            } else {
               if (var11) {
                  throw new NoWhenBranchMatchedException();
               }

               var10000 = kTerminalData.getTerminal()[i][j].getBackgroundColor();
            }

            float drawColor = var10000;
            KTerminalGlyph glyph = kTerminalData.getTerminal()[i][j];
            scaleY = glyph.isFlippedY() ? -glyph.getScale() : glyph.getScale();
            float scaleY = glyph.isFlippedX() ? -glyph.getScale() : glyph.getScale();
            TextureRegion var10001 = new TextureRegion;
            Texture var10003 = this.backgroundTexture;
            if (var10003 == null) {
               // Intrinsics.throwUninitializedPropertyAccessException("backgroundTexture");
            }

            var10001.<init>(var10003);
            $fun$draw$1.invoke(var10001, drawColor, glyph, i, j, scaleY, scaleY);
         }
      }

      j = 0;

      for(var7 = kTerminalData.getHeight(); j < var7; ++j) {
         i = 0;

         for(var9 = kTerminalData.getWidth(); i < var9; ++i) {
            KTerminalGlyph glyph = kTerminalData.getTerminal()[i][j];
            float scaleX = glyph.isFlippedY() ? -glyph.getScale() : glyph.getScale();
            scaleY = glyph.isFlippedX() ? -glyph.getScale() : glyph.getScale();
            if (glyph.getValue() >= this.columns * this.rows) {
               throw (Throwable)(new IllegalArgumentException("glyph value [" + glyph.getValue() + "] exceeds found glyph count [" + (this.columns * this.rows - 1) + ']'));
            }

            TextureRegion[] var21;
            if (glyph.isSubCellEnabled()) {
               SubCellGlyph.SubCellData topLeft = glyph.getSubCellGlyph().getSubCells()[0][0];
               SubCellGlyph.SubCellData topRight = glyph.getSubCellGlyph().getSubCells()[1][0];
               SubCellGlyph.SubCellData bottomLeft = glyph.getSubCellGlyph().getSubCells()[0][1];
               SubCellGlyph.SubCellData bottomRight = glyph.getSubCellGlyph().getSubCells()[1][1];
               var21 = this.glyphs;
               if (var21 == null) {
                  // Intrinsics.throwUninitializedPropertyAccessException("glyphs");
               }

               $fun$draw$1.invoke(var21[topLeft.getValue()], topLeft.getColor(), glyph, i, j, scaleX, scaleY);
               var21 = this.glyphs;
               if (var21 == null) {
                  // Intrinsics.throwUninitializedPropertyAccessException("glyphs");
               }

               $fun$draw$1.invoke(var21[topRight.getValue()], topRight.getColor(), glyph, i, j, scaleX, scaleY);
               var21 = this.glyphs;
               if (var21 == null) {
                  // Intrinsics.throwUninitializedPropertyAccessException("glyphs");
               }

               $fun$draw$1.invoke(var21[bottomLeft.getValue()], bottomLeft.getColor(), glyph, i, j, scaleX, scaleY);
               var21 = this.glyphs;
               if (var21 == null) {
                  // Intrinsics.throwUninitializedPropertyAccessException("glyphs");
               }

               $fun$draw$1.invoke(var21[bottomRight.getValue()], bottomRight.getColor(), glyph, i, j, scaleX, scaleY);
            }

            var21 = this.glyphs;
            if (var21 == null) {
               // Intrinsics.throwUninitializedPropertyAccessException("glyphs");
            }

            $fun$draw$1.invoke(var21[glyph.getValue()], kTerminalData.getTerminal()[i][j].getForegroundColor(), glyph, i, j, scaleX, scaleY);
         }
      }

      this.batch.setPackedColor(originalColor);
   }
  public void dispose() {
    Texture var10000=this.glyphTexture;
    if(var10000==null) {
      // Intrinsics.throwUninitializedPropertyAccessException("glyphTexture");
    }
    var10000.dispose();
    var10000=this.backgroundTexture;
    if(var10000==null) {
      // Intrinsics.throwUninitializedPropertyAccessException("backgroundTexture");
    }
    var10000.dispose();
  }
  @NotNull
  public final SpriteBatch getBatch() {
    return this.batch;
  }
  @Nullable
  public final String getTilesetFile() {
    return this.tilesetFile;
  }
  public final void setTilesetFile(@Nullable String var1) {
    this.tilesetFile=var1;
  }
  public final int getColumns() {
    return this.columns;
  }
  public final void setColumns(int var1) {
    this.columns=var1;
  }
  public final int getRows() {
    return this.rows;
  }
  public final void setRows(int var1) {
    this.rows=var1;
  }
  public KTerminalRenderer(@NotNull SpriteBatch batch,@Nullable String tilesetFile,int columns,int rows,float scale) {
    // Intrinsics.checkParameterIsNotNull(batch,"batch");
    super();
    this.batch=batch;
    this.tilesetFile=tilesetFile;
    this.columns=columns;
    this.rows=rows;
    this.init(this.tilesetFile,scale);
  }
  // $FF: synthetic method
  public KTerminalRenderer(SpriteBatch var1,String var2,int var3,int var4,float var5,int var6,DefaultConstructorMarker var7) {
    if((var6&2)!=0) {
      var2=null;
    }
    if((var6&4)!=0) {
      var3=16;
    }
    if((var6&8)!=0) {
      var4=16;
    }
    if((var6&16)!=0) {
      var5=1.0F;
    }
    this(var1,var2,var3,var4,var5);
  }
  // $FF: synthetic method
  public static final void access$setScaledGlyphWidth$p(KTerminalRenderer $this,float var1) {
    $this.scaledGlyphWidth=var1;
  }
  // $FF: synthetic method
  public static final void access$setScaledGlyphHeight$p(KTerminalRenderer $this,float var1) {
    $this.scaledGlyphHeight=var1;
  }
}
