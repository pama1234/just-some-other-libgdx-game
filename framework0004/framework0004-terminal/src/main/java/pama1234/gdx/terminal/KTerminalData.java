package pama1234.gdx.terminal;

import java.sql.Ref;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;

public final class KTerminalData{
  private int width;
  private int height;
  @NotNull
  private KTerminalGlyph[][] terminal;
  private final Cursor cursor;
  private final Cursor workingCursor;
  private float defaultForegroundColor;
  private float defaultBackgroundColor;
  @Nullable
  private final Map customColorMap;
  public static final int WRITE_LEFT_TO_RIGHT=0;
  public static final int WRITE_TOP_TO_BOTTOM=1;
  public static final int WRITE_RIGHT_TO_LEFT=2;
  public static final int WRITE_BOTTOM_TO_TOP=3;
  public static final int WRAP_NONE=-1;
  public static final int WRAP_NO_SHIFT=0;
  public static final int WRAP_POSITIVE_SHIFT=1;
  public static final int WRAP_NEGATIVE_SHIFT=2;
  public static final int NULL=0;
  public static final int SMILE=1;
  public static final int INVERTED_SMILE=2;
  public static final int HEART=3;
  public static final int DIAMOND=4;
  public static final int CLUB=5;
  public static final int SPADE=6;
  public static final int BULLET=7;
  public static final int INVERTED_BULLET=8;
  public static final int CIRCLE=9;
  public static final int INVERTED_CIRCLE=10;
  public static final int MALE=11;
  public static final int FEMALE=12;
  public static final int PAIR_EIGHTH_NOTE=13;
  public static final int EIGHTH_NOTE=14;
  public static final int SOLAR=15;
  public static final int RIGHT_TRIANGLE=16;
  public static final int LEFT_TRIANGLE=17;
  public static final int UP_DOWN_ARROW=18;
  public static final int DOUBLE_EXCLAMATION=19;
  public static final int PARAGRAPH=20;
  public static final int SECTION=21;
  public static final int LOWER_BLACK_RECTANGLE=22;
  public static final int UP_DOWN_BAR_ARROW=23;
  public static final int UP_ARROW=24;
  public static final int DOWN_ARROW=25;
  public static final int RIGHT_ARROW=26;
  public static final int LEFT_ARROW=27;
  public static final int RIGHT_ANGLE=28;
  public static final int LEFT_RIGHT_ARROW=29;
  public static final int UP_TRIANGLE=30;
  public static final int DOWN_TRIANGLE=31;
  public static final int SPACE=32;
  public static final int HOUSE=127;
  public static final int UPPER_CEDILLA_C=128;
  public static final int LOWER_UMLAUT_U=129;
  public static final int ACUTE_E=130;
  public static final int CIRCUMFLEX_A=131;
  public static final int LOWER_UMLAUT_A=132;
  public static final int GRAVE_A=133;
  public static final int LOWER_RING_A=134;
  public static final int LOWER_CEDILLA_C=135;
  public static final int CIRCUMFLEX_E=136;
  public static final int UMLAUT_E=137;
  public static final int GRAVE_E=138;
  public static final int UMLAUT_I=139;
  public static final int CIRCUMFLEX_I=140;
  public static final int GRAVE_I=141;
  public static final int UPPER_UMLAUT_A=142;
  public static final int UPPER_RING_A=143;
  public static final int UPPER_ACUTE_E=144;
  public static final int LOWER_ASH=145;
  public static final int UPPER_ASH=146;
  public static final int CIRCUMFLEX_O=147;
  public static final int LOWER_UMLAUT_O=148;
  public static final int GRAVE_O=149;
  public static final int CIRCUMFLEX_U=150;
  public static final int GRAVE_U=151;
  public static final int UMLAUT_Y=152;
  public static final int UPPER_UMLAUT_O=153;
  public static final int UPPER_UMLAUT_U=154;
  public static final int CENT=155;
  public static final int POUND=156;
  public static final int YEN=157;
  public static final int PESETA=158;
  public static final int HOOK_F=159;
  public static final int ACUTE_A=160;
  public static final int ACUTE_I=161;
  public static final int ACUTE_O=162;
  public static final int ACUTE_U=163;
  public static final int LOWER_TILDE_N=164;
  public static final int UPPER_TILDE_N=165;
  public static final int ORDINAL_FEMININE=166;
  public static final int ORDINAL_MASCULINE=167;
  public static final int INVERTED_QUESTION=168;
  public static final int INVERTED_NOT=169;
  public static final int NOT=170;
  public static final int ONE_HALF=171;
  public static final int ONE_FOURTH=172;
  public static final int INVERTED_EXCLAMATION=173;
  public static final int ANGLE_LEFT=174;
  public static final int ANGLE_RIGHT=175;
  public static final int SHADE_LIGHT=176;
  public static final int SHADE_MEDIUM=177;
  public static final int SHADE_DARK=178;
  public static final int BOX_SINGLE_VERTICAL=179;
  public static final int BOX_SINGLE_VERTICAL_LEFT=180;
  public static final int BOX_VERTICAL_SINGLE_LEFT_DOUBLE=181;
  public static final int BOX_VERTICAL_DOUBLE_LEFT_SINGLE=182;
  public static final int BOX_DOWN_DOUBLE_LEFT_SINGLE=183;
  public static final int BOX_DOWN_SINGLE_LEFT_DOUBLE=184;
  public static final int BOX_DOUBLE_VERTICAL_LEFT=185;
  public static final int BOX_DOUBLE_VERTICAL=186;
  public static final int BOX_DOUBLE_DOWN_LEFT=187;
  public static final int BOX_DOUBLE_UP_LEFT=188;
  public static final int BOX_UP_DOUBLE_LEFT_SINGLE=189;
  public static final int BOX_UP_SINGLE_LEFT_DOUBLE=190;
  public static final int BOX_SINGLE_DOWN_LEFT=191;
  public static final int BOX_SINGLE_UP_RIGHT=192;
  public static final int BOX_SINGLE_HORIZONTAL_UP=193;
  public static final int BOX_SINGLE_HORIZONTAL_DOWN=194;
  public static final int BOX_SINGLE_VERTICAL_RIGHT=195;
  public static final int BOX_SINGLE_HORIZONTAL=196;
  public static final int BOX_SINGLE_VERTICAL_HORIZONTAL=197;
  public static final int BOX_VERTICAL_SINGLE_RIGHT_DOUBLE=198;
  public static final int BOX_VERTICAL_DOUBLE_RIGHT_SINGLE=199;
  public static final int BOX_DOUBLE_UP_RIGHT=200;
  public static final int BOX_DOUBLE_DOWN_RIGHT=201;
  public static final int BOX_DOUBLE_HORIZONTAL_UP=202;
  public static final int HOX_DOUBLE_HORIZONTAL_DOWN=203;
  public static final int BOX_DOUBLE_VERTICAL_RIGHT=204;
  public static final int BOX_DOUBLE_HORIZONTAL=205;
  public static final int BOX_DOUBLE_VERTICAL_HORIZONTAL=206;
  public static final int BOX_UP_SINGLE_HORIZONTAL_DOUBLE=207;
  public static final int BOX_UP_DOUBLE_HORIZONTAL_SINGLE=208;
  public static final int BOX_DOWN_SINGLE_HORIZONTAL_DOUBLE=209;
  public static final int BOX_DOWN_DOUBLE_HORIZONTAL_SINGLE=210;
  public static final int BOX_UP_DOUBLE_RIGHT_SINGLE=211;
  public static final int BOX_UP_SINGLE_RIGHT_DOUBLE=212;
  public static final int BOX_DOWN_SINGLE_RIGHT_DOUBLE=213;
  public static final int BOX_DOWN_DOUBLE_RIGHT_SINGLE=214;
  public static final int BOX_VERTICAL_DOUBLE_HORIZONTAL_SINGLE=215;
  public static final int BOX_VERTICAL_SINGLE_HORIZONTAL_DOUBLE=216;
  public static final int BOX_SINGLE_UP_LEFT=217;
  public static final int BOX_SINGLE_DOWN_RIGHT=218;
  public static final int BLOCK_FULL=219;
  public static final int BLOCK_LOWER_HALF=220;
  public static final int BLOCK_LEFT_HALF=221;
  public static final int BLOCK_RIGHT_HALF=222;
  public static final int BLOCK_UPPER_HALF=223;
  public static final int ALPHA=224;
  public static final int SHARP_S=225;
  public static final int GAMMA=226;
  public static final int PI=227;
  public static final int UPPER_SIGMA=228;
  public static final int LOWER_SIGMA=229;
  public static final int MU=230;
  public static final int TAU=231;
  public static final int UPPER_PHI=232;
  public static final int THETA=233;
  public static final int OMEGA=234;
  public static final int DELTA=235;
  public static final int INFINITY=236;
  public static final int LOWER_PHI=237;
  public static final int EPSILON=238;
  public static final int INTERSECTION=239;
  public static final int TRIPLE_BAR=240;
  public static final int PLUS_MINUS=241;
  public static final int GREATER_EQUAL=242;
  public static final int LESSER_EQUAL=243;
  public static final int INTEGRAL_TOP=244;
  public static final int INTEGRAL_BOTTOM=245;
  public static final int DIVISION=246;
  public static final int APROXIMATE=247;
  public static final int DEGREE=248;
  public static final int SMALL_BULLET=249;
  public static final int INTERPUNCT=250;
  public static final int RADICAL=251;
  public static final int SUPERSCRIPT=252;
  public static final int SUPERSCRIPT_SQUARE=253;
  public static final int SQUARE=254;
  public static final int NBSP=255;
  @NotNull
  public static final Companion Companion=new Companion(null);
  public final int getWidth() {
    return this.width;
  }
  public final void setWidth(int value) {
    if(value>0) {
      this.width=value;
    }else {
      throw new IllegalArgumentException("Width can't be 0 or below.");
    }
  }
  public final int getHeight() {
    return this.height;
  }
  public final void setHeight(int value) {
    if(value>0) {
      this.height=value;
    }else {
      throw new IllegalArgumentException("Height can't be 0 or below.");
    }
  }
  @NotNull
  public final KTerminalGlyph[][] getTerminal() {
    return this.terminal;
  }
  public final void setTerminal(@NotNull KTerminalGlyph[][] var1) {
    // // Intrinsics.checkParameterIsNotNull(var1,"<set-?>");
    this.terminal=var1;
  }
  public final void resize(int width,int height) {
    this.setWidth(width);
    this.setHeight(height);
    KTerminalGlyph[][] var3=new KTerminalGlyph[width][];
    for(int var4=0;var4<width;++var4) {
      int var6=0;
      int var7=height;
      KTerminalGlyph[] var8=new KTerminalGlyph[height];
      for(int var9=0;var9<var7;++var9) {
        int var13=0;
        KTerminalGlyph var14=new KTerminalGlyph(0,this.defaultForegroundColor,this.defaultBackgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,504,null);
        var8[var9]=var14;
      }
      var3[var4]=var8;
    }
    this.terminal=(KTerminalGlyph[][])var3;
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
    this.cursor.set(x,y,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
    this.workingCursor.set(this.cursor);
  }
  // $FF: synthetic method
  public static void setCursor$default(KTerminalData var0,int var1,int var2,float var3,float var4,float var5,float var6,float var7,float var8,boolean var9,boolean var10,int var11,Object var12) {
    if((var11&1)!=0) {
      var1=0;
    }
    if((var11&2)!=0) {
      var2=0;
    }
    if((var11&4)!=0) {
      var3=var0.defaultForegroundColor;
    }
    if((var11&8)!=0) {
      var4=var0.defaultBackgroundColor;
    }
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
    var0.setCursor(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10);
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX) {
    setCursor$default(this,x,y,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,false,512,(Object)null);
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY) {
    setCursor$default(this,x,y,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,false,false,768,(Object)null);
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX) {
    setCursor$default(this,x,y,foregroundColor,backgroundColor,rotation,scale,offsetX,0.0F,false,false,896,(Object)null);
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale) {
    setCursor$default(this,x,y,foregroundColor,backgroundColor,rotation,scale,0.0F,0.0F,false,false,960,(Object)null);
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor,float rotation) {
    setCursor$default(this,x,y,foregroundColor,backgroundColor,rotation,0.0F,0.0F,0.0F,false,false,992,(Object)null);
  }
  public final void setCursor(int x,int y,float foregroundColor,float backgroundColor) {
    setCursor$default(this,x,y,foregroundColor,backgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,1008,(Object)null);
  }
  public final void setCursor(int x,int y,float foregroundColor) {
    setCursor$default(this,x,y,foregroundColor,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,1016,(Object)null);
  }
  public final void setCursor(int x,int y) {
    setCursor$default(this,x,y,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,1020,(Object)null);
  }
  public final void setCursor(int x) {
    setCursor$default(this,x,0,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,1022,(Object)null);
  }
  public final void setCursor() {
    setCursor$default(this,0,0,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,false,false,1023,(Object)null);
  }
  public final void resetCursor() {
    this.setCursor(0,0,this.defaultForegroundColor,this.defaultBackgroundColor,0.0F,1.0F,0.0F,0.0F,false,false);
  }
  @NotNull
  public final KTerminalData setCursorPosition(int x,int y) {
    this.cursor.setX(x);
    this.cursor.setY(y);
    this.workingCursor.set(this.cursor);
    return this;
  }
  @NotNull
  public final KTerminalData setCursorColor(float foregroundColor,float backgroundColor) {
    this.cursor.setForegroundColor(foregroundColor);
    this.cursor.setBackgroundColor(backgroundColor);
    this.workingCursor.setForegroundColor(foregroundColor);
    this.workingCursor.setBackgroundColor(backgroundColor);
    return this;
  }
  @NotNull
  public final KTerminalData setCursorColor(@NotNull Color foregroundColor,@NotNull Color backgroundColor) {
    // Intrinsics.checkParameterIsNotNull(foregroundColor,"foregroundColor");
    // Intrinsics.checkParameterIsNotNull(backgroundColor,"backgroundColor");
    return this.setCursorColor(foregroundColor.toFloatBits(),backgroundColor.toFloatBits());
  }
  @NotNull
  public final KTerminalData setCursorScale(float scale) {
    this.cursor.setScale(scale);
    this.workingCursor.setScale(scale);
    return this;
  }
  @NotNull
  public final KTerminalData setCursorRotation(float rotation) {
    this.cursor.setRotation(rotation);
    this.workingCursor.setRotation(rotation);
    return this;
  }
  @NotNull
  public final KTerminalData setCursorOffset(float x,float y) {
    this.cursor.setOffsetX(x);
    this.cursor.setOffsetY(y);
    this.workingCursor.setOffsetX(x);
    this.workingCursor.setOffsetY(y);
    return this;
  }
  @NotNull
  public final KTerminalData setCursorFlip(boolean isFlippedX,boolean isFlippedY) {
    this.cursor.setFlippedX(isFlippedX);
    this.cursor.setFlippedY(isFlippedY);
    this.workingCursor.setFlippedX(isFlippedX);
    this.workingCursor.setFlippedY(isFlippedY);
    return this;
  }
  @NotNull
  public final KTerminalData get(int x,int y) {
    return this.setCursorPosition(x,y);
  }
  @NotNull
  public final KTerminalData get(int x,int y,float offsetX,float offsetY) {
    return this.setCursorPosition(x,y).setCursorOffset(offsetX,offsetY);
  }
  @NotNull
  public final KTerminalData get(@NotNull Color foregroundColor,@NotNull Color backgroundColor) {
    // // Intrinsics.checkParameterIsNotNull(foregroundColor,"foregroundColor");
    // // Intrinsics.checkParameterIsNotNull(backgroundColor,"backgroundColor");
    return this.setCursorColor(foregroundColor,backgroundColor);
  }
  @NotNull
  public final KTerminalData get(float rotation,float scale) {
    return this.setCursorRotation(rotation).setCursorScale(scale);
  }
  @NotNull
  public final KTerminalData get(boolean isFlippedX,boolean isFlippedY) {
    return this.setCursorFlip(isFlippedX,isFlippedY);
  }
  @NotNull
  public final KTerminalData write(@NotNull KTerminalGlyph glyph) {
    // // Intrinsics.checkParameterIsNotNull(glyph,"glyph");
    this.workingCursor.setForegroundColor(glyph.getForegroundColor());
    this.workingCursor.setBackgroundColor(glyph.getBackgroundColor());
    this.workingCursor.setRotation(glyph.getRotation());
    this.workingCursor.setScale(glyph.getScale());
    this.workingCursor.setOffsetX(glyph.getOffsetX());
    this.workingCursor.setOffsetY(glyph.getOffsetY());
    this.workingCursor.setFlippedX(glyph.isFlippedX());
    this.workingCursor.setFlippedY(glyph.isFlippedY());
    return this.write(glyph.getValue());
  }
  @NotNull
  public final KTerminalData write(int value) {
    KTerminalGlyph var2=this.terminal[this.workingCursor.getX()][this.workingCursor.getY()];
    int var4=0;
    var2.setValue(value);
    var2.setForegroundColor(this.workingCursor.getForegroundColor());
    var2.setBackgroundColor(this.workingCursor.getBackgroundColor());
    var2.setRotation(this.workingCursor.getRotation());
    var2.setScale(this.workingCursor.getScale());
    var2.setOffsetX(this.workingCursor.getOffsetX());
    var2.setOffsetY(this.workingCursor.getOffsetY());
    var2.setFlippedX(this.workingCursor.isFlippedX());
    var2.setFlippedY(this.workingCursor.isFlippedY());
    this.workingCursor.set(this.cursor);
    return this;
  }
  @NotNull
  public final KTerminalData write(char var1) {
    return this.write((int)var1);
  }
  private final String recursiveTagExtractor(final String input,Map tagMap,final int position,boolean inTag,String output,int tagPosition,int offsetPosition) {
    while(true) {
      final char STARTING_CHAR='[';
      final char ENDING_CHAR=']';
      final char ESCAPE_CHAR='\\';
      boolean tagging=inTag;
      int tagPos=tagPosition;
      String outputString=output;
      int offsetPos=offsetPosition;
      var $fun$isStartingChar$1=new WithObjectFunction() {
        // $FF: synthetic method
        // $FF: bridge method
        public Object invoke(Object var1) {
          return this.invoke(((Number)var1).intValue());
        }
        public final boolean invoke(int pos) {
          return input.charAt(pos)==STARTING_CHAR;
        }
        // $FF: synthetic method
        public static boolean invoke$default(Object var0,int var1,int var2,Object var3) {
          if((var2&1)!=0) {
            var1=position;
          }
          return var0.invoke(var1);
        }
      };
      final var $fun$isEscapeChar$2=new WithObjectFunction() {
        // $FF: synthetic method
        // $FF: bridge method
        public Object invoke(Object var1) {
          return this.invoke(((Number)var1).intValue());
        }
        public final boolean invoke(int pos) {
          return input.charAt(pos)==ESCAPE_CHAR;
        }
        // $FF: synthetic method
        public static boolean invoke$default(Object var0,int var1,int var2,Object var3) {
          if((var2&1)!=0) {
            var1=position;
          }
          return var0.invoke(var1);
        }
      };
      var $fun$isEndingChar$3=new WithObjectFunction() {
        // $FF: synthetic method
        // $FF: bridge method
        public Object invoke(Object var1) {
          return this.invoke(((Number)var1).intValue());
        }
        public final boolean invoke(int pos) {
          return input.charAt(pos)==ENDING_CHAR;
        }
        // $FF: synthetic method
        public static boolean invoke$default(Object var0,int var1,int var2,Object var3) {
          if((var2&1)!=0) {
            var1=position;
          }
          return var0.invoke(var1);
        }
      };
      var $fun$isEscaped$4=new WithObjectFunction() {
        // $FF: synthetic method
        // $FF: bridge method
        public Object invoke(Object var1) {
          return this.invoke(((Number)var1).intValue());
        }
        public final boolean invoke(int pos) {
          return pos==0&&$fun$isEscapeChar$2.invoke(pos)||pos>0&&$fun$isEscapeChar$2.invoke(pos)&&!$fun$isEscapeChar$2.invoke(pos-1);
        }
        // $FF: synthetic method
        public static boolean invoke$default(Object var0,int var1,int var2,Object var3) {
          if((var2&1)!=0) {
            var1=position;
          }
          return var0.invoke(var1);
        }
      };
      if(!inTag&&null.invoke$default($fun$isStartingChar$1,0,1,(Object)null)&&!$fun$isEscaped$4.invoke(2)) {
        tagging=true;
        tagPos=offsetPosition;
      }else if(inTag&&$fun$isEndingChar$3.invoke(position-1)&&!$fun$isEscaped$4.invoke(position-1)) {
        tagging=false;
        tagPos=-1;
      }
      String inputCharString=String.valueOf(input.charAt(position));
      if(!null.invoke$default($fun$isEscaped$4,0,1,(Object)null)) {
        CharSequence var21;
        if(tagging) {
          Integer var10001=tagPos;
          var21=(CharSequence)tagMap.get(tagPos);
          tagMap.put(var10001,var21==null||var21.length()==0?inputCharString:// Intrinsics.stringPlus((String)tagMap.get(tagPos),inputCharString));
        }else if(!tagging) {
          StringBuilder var10000=(new StringBuilder()).append(output);
          var21=(CharSequence)tagMap.get(-1);
          outputString=var10000.append(var21==null||var21.length()==0?inputCharString:// Intrinsics.stringPlus((String)tagMap.get(-1),inputCharString)).toString();
          offsetPos=offsetPosition+1;
        }
      }
      if(position+1>=input.length()) {
        return outputString;
      }
      int var22=position+1;
      offsetPosition=offsetPos;
      tagPosition=tagPos;
      output=outputString;
      inTag=tagging;
      position=var22;
    }
  }
  // $FF: synthetic method
  static String recursiveTagExtractor$default(KTerminalData var0,String var1,Map var2,int var3,boolean var4,String var5,int var6,int var7,int var8,Object var9) {
    if((var8&4)!=0) {
      var3=0;
    }
    if((var8&8)!=0) {
      var4=false;
    }
    if((var8&16)!=0) {
      var5="";
    }
    if((var8&32)!=0) {
      var6=-1;
    }
    if((var8&64)!=0) {
      var7=0;
    }
    return var0.recursiveTagExtractor(var1,var2,var3,var4,var5,var6,var7);
  }
  @NotNull
  public final KTerminalData write(@NotNull String string,boolean hasMarkup,int direction,final int wrapping) {
    // Intrinsics.checkParameterIsNotNull(string,"string");
    int posX=this.cursor.getX();
    int posY=this.cursor.getY();
    final Ref.BooleanRef isWriting=new Ref.BooleanRef();
    isWriting.element=true;
    var $fun$wrap$2=new Function3() {
      // $FF: synthetic method
      // $FF: bridge method
      public Object invoke(Object var1,Object var2,Object var3) {
        return this.invoke(((Number)var1).intValue(),((Number)var2).intValue(),((Number)var3).intValue());
      }
      @NotNull
      public final Pair invoke(int writingPos,int alignPos,int max) {
        final Ref.IntRef tempAlignPos=new Ref.IntRef();
        tempAlignPos.element=alignPos;
        var $fun$adjustAlignPos$1=new Function0() {
          // $FF: synthetic method
          // $FF: bridge method
          public Object invoke() {
            this.invoke();
            return Unit.INSTANCE;
          }
          public final void invoke() {
            switch(wrapping) {
              case -1:
                isWriting.element=false;
              case 0:
              default:
                break;
              case 1:
                int var10001=tempAlignPos.element++;
                break;
              case 2:
                Ref.IntRef var10000=tempAlignPos;
                var10000.element+=-1;
            }
          }
        };
        int var10000;
        if(writingPos>=max) {
          $fun$adjustAlignPos$1.invoke();
          var10000=0;
        }else if(writingPos<0) {
          $fun$adjustAlignPos$1.invoke();
          var10000=max-1;
        }else {
          var10000=writingPos;
        }
        int tempWritingPos=var10000;
        return new Pair(tempWritingPos,tempAlignPos.element);
      }
    };
    Map tagMap=(Map)(new LinkedHashMap());
    String workingString=hasMarkup?recursiveTagExtractor$default(this,string,tagMap,0,false,(String)null,0,0,124,(Object)null):string;
    float writingColor=this.workingCursor.getForegroundColor();
    if(workingString==null) {
      throw new Exception("null cannot be cast to non-null type java.lang.String");
    }else {
      char[] var10000=workingString.toCharArray();
      // Intrinsics.checkExpressionValueIsNotNull(var10000,"(this as java.lang.String).toCharArray()");
      char[] $receiver$iv=var10000;
      int index$iv=0;
      char[] var14=$receiver$iv;
      int var15=$receiver$iv.length;
      for(int var16=0;var16<var15;++var16) {
        char item$iv=var14[var16];
        int i=index$iv++;
        int var20=0;
        if(isWriting.element) {
          this.workingCursor.set(posX,posY);
          if(hasMarkup&&tagMap.get(i)!=null) {
            String tagString=StringsKt.removeSuffix(StringsKt.removePrefix((String)MapsKt.getValue(tagMap,i),(CharSequence)"["),(CharSequence)"]");
            if(this.customColorMap!=null&&this.customColorMap.get(tagString)!=null) {
              writingColor=((Color)MapsKt.getValue(this.customColorMap,tagString)).toFloatBits();
            }else if(KTerminalColor.INSTANCE.getColorMap$kterminal().get(tagString)!=null) {
              writingColor=((Color)MapsKt.getValue(KTerminalColor.INSTANCE.getColorMap$kterminal(),tagString)).toFloatBits();
            }
          }
          this.workingCursor.setForegroundColor(writingColor);
          this.write(item$iv);
          switch(direction) {
            case 0:
              ++posX;
              break;
            case 1:
              ++posY;
              break;
            case 2:
              posX+=-1;
              break;
            case 3:
              posY+=-1;
          }
          Pair var22;
          int tempX;
          int tempY;
          switch(direction) {
            case 0:
            case 2:
              var22=$fun$wrap$2.invoke(posX,posY,this.width);
              tempY=((Number)var22.component1()).intValue();
              tempX=((Number)var22.component2()).intValue();
              posX=tempY;
              posY=tempX;
              break;
            case 1:
            case 3:
              var22=$fun$wrap$2.invoke(posY,posX,this.height);
              tempY=((Number)var22.component1()).intValue();
              tempX=((Number)var22.component2()).intValue();
              posX=tempX;
              posY=tempY;
          }
        }
      }
      return this;
    }
  }
  // $FF: synthetic method
  public static KTerminalData write$default(KTerminalData var0,String var1,boolean var2,int var3,int var4,int var5,Object var6) {
    if((var5&2)!=0) {
      var2=false;
    }
    if((var5&4)!=0) {
      var3=0;
    }
    if((var5&8)!=0) {
      var4=-1;
    }
    return var0.write(var1,var2,var3,var4);
  }
  @NotNull
  public final KTerminalData write(@NotNull String string,boolean hasMarkup,int direction) {
    return write$default(this,string,hasMarkup,direction,0,8,(Object)null);
  }
  @NotNull
  public final KTerminalData write(@NotNull String string,boolean hasMarkup) {
    return write$default(this,string,hasMarkup,0,0,12,(Object)null);
  }
  @NotNull
  public final KTerminalData write(@NotNull String string) {
    return write$default(this,string,false,0,0,14,(Object)null);
  }
  @NotNull
  public final KTerminalData writeSubCell(int subcellX,int subcellY,float color,int value) {
    var $fun$clampCoord$1=null.INSTANCE;
    int tempX=$fun$clampCoord$1.invoke(subcellX,this.width*2);
    int tempY=$fun$clampCoord$1.invoke(subcellY,this.height*2);
    KTerminalGlyph var8=this.terminal[tempX/2][tempY/2];
    int var10=0;
    var8.setSubCellEnabled(true);
    int var10000;
    switch(value) {
      case -1:
        var10000=tempX%2==0&&tempY%2==0?257:(tempX%2==1&&tempY%2==0?258:(tempX%2==0&&tempY%2==1?260:259));
        break;
      default:
        var10000=value;
    }
    int tempValue=var10000;
    var8.getSubCellGlyph().getSubCells()[tempX%2][tempY%2].set(color,tempValue);
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData writeSubCell$default(KTerminalData var0,int var1,int var2,float var3,int var4,int var5,Object var6) {
    if((var5&4)!=0) {
      var3=var0.defaultBackgroundColor;
    }
    if((var5&8)!=0) {
      var4=-1;
    }
    return var0.writeSubCell(var1,var2,var3,var4);
  }
  @NotNull
  public final KTerminalData writeSubCell(int subcellX,int subcellY,float color) {
    return writeSubCell$default(this,subcellX,subcellY,color,0,8,(Object)null);
  }
  @NotNull
  public final KTerminalData writeSubCell(int subcellX,int subcellY) {
    return writeSubCell$default(this,subcellX,subcellY,0.0F,0,12,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,int topLeftValue,int topRightValue,int bottomLeftValue,int bottomRightValue) {
    KTerminalGlyph var9=this.terminal[this.workingCursor.getX()][this.workingCursor.getY()];
    int var11=false;
    var9.setSubCellEnabled(true);
    var9.getSubCellGlyph().getSubCells()[0][0].set(topLeftColor,topLeftValue);
    var9.getSubCellGlyph().getSubCells()[1][0].set(topRightColor,topRightValue);
    var9.getSubCellGlyph().getSubCells()[0][1].set(bottomLeftColor,bottomLeftValue);
    var9.getSubCellGlyph().getSubCells()[1][1].set(bottomRightColor,bottomRightValue);
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData setSubCell$default(KTerminalData var0,float var1,float var2,float var3,float var4,int var5,int var6,int var7,int var8,int var9,Object var10) {
    if((var9&1)!=0) {
      var1=var0.defaultBackgroundColor;
    }
    if((var9&2)!=0) {
      var2=var0.defaultBackgroundColor;
    }
    if((var9&4)!=0) {
      var3=var0.defaultBackgroundColor;
    }
    if((var9&8)!=0) {
      var4=var0.defaultBackgroundColor;
    }
    if((var9&16)!=0) {
      var5=257;
    }
    if((var9&32)!=0) {
      var6=258;
    }
    if((var9&64)!=0) {
      var7=260;
    }
    if((var9&128)!=0) {
      var8=259;
    }
    return var0.setSubCell(var1,var2,var3,var4,var5,var6,var7,var8);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,int topLeftValue,int topRightValue,int bottomLeftValue) {
    return setSubCell$default(this,topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,topLeftValue,topRightValue,bottomLeftValue,0,128,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,int topLeftValue,int topRightValue) {
    return setSubCell$default(this,topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,topLeftValue,topRightValue,0,0,192,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor,int topLeftValue) {
    return setSubCell$default(this,topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,topLeftValue,0,0,0,224,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor,float bottomLeftColor,float bottomRightColor) {
    return setSubCell$default(this,topLeftColor,topRightColor,bottomLeftColor,bottomRightColor,0,0,0,0,240,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor,float bottomLeftColor) {
    return setSubCell$default(this,topLeftColor,topRightColor,bottomLeftColor,0.0F,0,0,0,0,248,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor,float topRightColor) {
    return setSubCell$default(this,topLeftColor,topRightColor,0.0F,0.0F,0,0,0,0,252,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(float topLeftColor) {
    return setSubCell$default(this,topLeftColor,0.0F,0.0F,0.0F,0,0,0,0,254,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell() {
    return setSubCell$default(this,0.0F,0.0F,0.0F,0.0F,0,0,0,0,255,(Object)null);
  }
  @NotNull
  public final KTerminalData setSubCell(@NotNull Color topLeftColor,@NotNull Color topRightColor,@NotNull Color bottomLeftColor,@NotNull Color bottomRightColor,int topLeftValue,int topRightValue,int bottomLeftValue,int bottomRightValue) {
    // Intrinsics.checkParameterIsNotNull(topLeftColor,"topLeftColor");
    // Intrinsics.checkParameterIsNotNull(topRightColor,"topRightColor");
    // Intrinsics.checkParameterIsNotNull(bottomLeftColor,"bottomLeftColor");
    // Intrinsics.checkParameterIsNotNull(bottomRightColor,"bottomRightColor");
    return this.setSubCell(topLeftColor.toFloatBits(),topRightColor.toFloatBits(),bottomLeftColor.toFloatBits(),bottomRightColor.toFloatBits(),topLeftValue,topRightValue,bottomLeftValue,bottomRightValue);
  }
  // $FF: synthetic method
  public static KTerminalData setSubCell$default(KTerminalData var0,Color var1,Color var2,Color var3,Color var4,int var5,int var6,int var7,int var8,int var9,Object var10) {
    if((var9&16)!=0) {
      var5=257;
    }
    if((var9&32)!=0) {
      var6=258;
    }
    if((var9&64)!=0) {
      var7=260;
    }
    if((var9&128)!=0) {
      var8=259;
    }
    return var0.setSubCell(var1,var2,var3,var4,var5,var6,var7,var8);
  }
  @NotNull
  public final KTerminalData setSubCell(@NotNull SubCellGlyph subCellGlyph) {
    // Intrinsics.checkParameterIsNotNull(subCellGlyph,"subCellGlyph");
    this.terminal[this.workingCursor.getX()][this.workingCursor.getY()].setSubCellEnabled(true);
    this.terminal[this.workingCursor.getX()][this.workingCursor.getY()].setSubCellGlyph(subCellGlyph);
    return this;
  }
  @NotNull
  public final KTerminalData clearSubCell() {
    this.terminal[this.workingCursor.getX()][this.workingCursor.getY()].setSubCellEnabled(false);
    this.terminal[this.workingCursor.getX()][this.workingCursor.getY()].getSubCellGlyph().reset();
    return this;
  }
  @NotNull
  public final KTerminalData clear(int width,int height) {
    this.workingCursor.setForegroundColor(this.defaultForegroundColor);
    this.workingCursor.setBackgroundColor(this.defaultBackgroundColor);
    this.workingCursor.setRotation(0.0F);
    this.workingCursor.setScale(1.0F);
    this.workingCursor.setFlippedX(false);
    this.workingCursor.setFlippedY(false);
    this.workingCursor.setOffsetX(0.0F);
    this.workingCursor.setOffsetY(0.0F);
    int startX=this.workingCursor.getX();
    int startY=this.workingCursor.getY();
    drawRect$default(this,width,height,(int)0,true,(int)0,0.0F,0.0F,112,(Object)null);
    int x=startX;
    for(int var6=startX+width;x<var6;++x) {
      int y=startY;
      for(int var8=startY+height;y<var8;++y) {
        this.workingCursor.setX(x);
        this.workingCursor.setY(y);
        this.clearSubCell();
      }
    }
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData clear$default(KTerminalData var0,int var1,int var2,int var3,Object var4) {
    if((var3&1)!=0) {
      var1=1;
    }
    if((var3&2)!=0) {
      var2=1;
    }
    return var0.clear(var1,var2);
  }
  @NotNull
  public final KTerminalData clear(int width) {
    return clear$default(this,width,0,2,(Object)null);
  }
  @NotNull
  public final KTerminalData clear() {
    return clear$default(this,0,0,3,(Object)null);
  }
  @NotNull
  public final KTerminalData clearAll() {
    this.workingCursor.set(0,0,this.defaultForegroundColor,this.defaultBackgroundColor,0.0F,1.0F,0.0F,0.0F,false,false);
    return this.clear(this.width,this.height);
  }
  private final KTerminalData fillShape(List shapeList,int value,float foregroundColor,float backgroundColor) {
    Iterable var10000=(Iterable)shapeList;
    Comparator var6=(Comparator)(new KTerminalData$fillShape$$inlined$compareBy$1());
    List shapeListSorted=CollectionsKt.sortedWith(var10000,(Comparator)(new KTerminalData$fillShape$$inlined$thenBy$1(var6)));
    int firstY=((Number)((Pair)CollectionsKt.first(shapeListSorted)).getSecond()).intValue();
    int lastY=((Number)((Pair)CollectionsKt.last(shapeListSorted)).getSecond()).intValue();
    Iterable $receiver$iv=(Iterable)shapeListSorted;
    int index$iv=0;
    Iterator var10=$receiver$iv.iterator();
    while(var10.hasNext()) {
      Object item$iv=var10.next();
      int var12=index$iv++;
      if(var12<0) {
        CollectionsKt.throwIndexOverflow();
      }
      Pair pair=(Pair)item$iv;
      int var16=false;
      if(var12+1<shapeListSorted.size()&&((Number)pair.getSecond()).intValue()!=firstY&&((Number)pair.getSecond()).intValue()!=lastY&&((Number)pair.getSecond()).intValue()==((Number)((Pair)shapeListSorted.get(var12+1)).getSecond()).intValue()&&((Number)pair.getFirst()).intValue()<=((Number)((Pair)shapeListSorted.get(var12+1)).getFirst()).intValue()-2) {
        int i=((Number)pair.getFirst()).intValue()+1;
        for(int var18=((Number)((Pair)shapeListSorted.get(var12+1)).getFirst()).intValue();i<var18;++i) {
          this.workingCursor.setX(i);
          this.workingCursor.setY(((Number)pair.getSecond()).intValue());
          this.workingCursor.setForegroundColor(foregroundColor);
          this.workingCursor.setBackgroundColor(backgroundColor);
          this.write(value);
        }
      }
    }
    return this;
  }
  private final KTerminalData drawShape(List shapeList,int value) {
    Iterable $receiver$iv=(Iterable)shapeList;
    Iterator var4=$receiver$iv.iterator();
    while(var4.hasNext()) {
      Object element$iv=var4.next();
      Pair it=(Pair)element$iv;
      int var7=false;
      this.workingCursor.setX(((Number)it.getFirst()).intValue());
      this.workingCursor.setY(((Number)it.getSecond()).intValue());
      this.write(value);
    }
    return this;
  }
  @NotNull
  public final KTerminalData drawRect(int width,int height,char var3,boolean isFilled,char fillChar,float fillForeground,float fillBackground) {
    return this.drawRect(width,height,(int)var3,isFilled,(int)fillChar,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawRect$default(KTerminalData var0,int var1,int var2,char var3,boolean var4,char var5,float var6,float var7,int var8,Object var9) {
    if((var8&4)!=0) {
      var3=' ';
    }
    if((var8&8)!=0) {
      var4=false;
    }
    if((var8&16)!=0) {
      var5=var3;
    }
    return var0.drawRect(var1,var2,var3,var4,var5,var6,var7);
  }
  @NotNull
  public final KTerminalData drawRect(int width,int height,int value,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    List plotList=KTerminalShapePlotter.INSTANCE.plotRect(this.cursor.getX(),this.cursor.getY(),width,height);
    this.drawShape(plotList,value);
    if(isFilled) {
      this.fillShape(plotList,fillValue,fillForeground,fillBackground);
    }
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData drawRect$default(KTerminalData var0,int var1,int var2,int var3,boolean var4,int var5,float var6,float var7,int var8,Object var9) {
    if((var8&8)!=0) {
      var4=false;
    }
    if((var8&16)!=0) {
      var5=var3;
    }
    if((var8&32)!=0) {
      var6=var0.cursor.getForegroundColor();
    }
    if((var8&64)!=0) {
      var7=var0.cursor.getBackgroundColor();
    }
    return var0.drawRect(var1,var2,var3,var4,var5,var6,var7);
  }
  @NotNull
  public final KTerminalData drawRect(int width,int height,int value,boolean isFilled,int fillValue,float fillForeground) {
    return drawRect$default(this,width,height,(int)value,isFilled,(int)fillValue,fillForeground,0.0F,64,(Object)null);
  }
  @NotNull
  public final KTerminalData drawRect(int width,int height,int value,boolean isFilled,int fillValue) {
    return drawRect$default(this,width,height,(int)value,isFilled,(int)fillValue,0.0F,0.0F,96,(Object)null);
  }
  @NotNull
  public final KTerminalData drawRect(int width,int height,int value,boolean isFilled) {
    return drawRect$default(this,width,height,(int)value,isFilled,(int)0,0.0F,0.0F,112,(Object)null);
  }
  @NotNull
  public final KTerminalData drawRect(int width,int height,int value) {
    return drawRect$default(this,width,height,(int)value,false,(int)0,0.0F,0.0F,120,(Object)null);
  }
  @NotNull
  public final KTerminalData drawLine(int endX,int endY,char var3) {
    return this.drawLine(endX,endY,(int)var3);
  }
  @NotNull
  public final KTerminalData drawLine(int endX,int endY,int value) {
    List linePlot=CollectionsKt.toList((Iterable)KTerminalShapePlotter.INSTANCE.plotLine(this.cursor.getX(),this.cursor.getY(),endX,endY));
    return this.drawShape(linePlot,value);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,char var3,boolean isFilled,char fillChar,float fillForeground,float fillBackground) {
    return this.drawEllipse(width,height,(int)var3,isFilled,(int)fillChar,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawEllipse$default(KTerminalData var0,int var1,int var2,char var3,boolean var4,char var5,float var6,float var7,int var8,Object var9) {
    if((var8&8)!=0) {
      var4=false;
    }
    if((var8&16)!=0) {
      var5=' ';
    }
    if((var8&32)!=0) {
      var6=var0.cursor.getForegroundColor();
    }
    if((var8&64)!=0) {
      var7=var0.cursor.getBackgroundColor();
    }
    return var0.drawEllipse(var1,var2,var3,var4,var5,var6,var7);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,char var3,boolean isFilled,char fillChar,float fillForeground) {
    return drawEllipse$default(this,width,height,(char)var3,isFilled,(char)fillChar,fillForeground,0.0F,64,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,char var3,boolean isFilled,char fillChar) {
    return drawEllipse$default(this,width,height,(char)var3,isFilled,(char)fillChar,0.0F,0.0F,96,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,char var3,boolean isFilled) {
    return drawEllipse$default(this,width,height,(char)var3,isFilled,(char)'\u0000',0.0F,0.0F,112,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,char var3) {
    return drawEllipse$default(this,width,height,(char)var3,false,(char)'\u0000',0.0F,0.0F,120,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,int value,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    List ellipsePlot=KTerminalShapePlotter.INSTANCE.plotEllipse(this.cursor.getX(),this.cursor.getY(),this.cursor.getX()+width,this.cursor.getY()+height);
    this.drawShape(ellipsePlot,value);
    if(isFilled) {
      this.fillShape(ellipsePlot,fillValue,fillForeground,fillBackground);
    }
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData drawEllipse$default(KTerminalData var0,int var1,int var2,int var3,boolean var4,int var5,float var6,float var7,int var8,Object var9) {
    if((var8&8)!=0) {
      var4=false;
    }
    if((var8&16)!=0) {
      var5=var3;
    }
    if((var8&32)!=0) {
      var6=var0.cursor.getForegroundColor();
    }
    if((var8&64)!=0) {
      var7=var0.cursor.getBackgroundColor();
    }
    return var0.drawEllipse(var1,var2,var3,var4,var5,var6,var7);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,int value,boolean isFilled,int fillValue,float fillForeground) {
    return drawEllipse$default(this,width,height,(int)value,isFilled,(int)fillValue,fillForeground,0.0F,64,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,int value,boolean isFilled,int fillValue) {
    return drawEllipse$default(this,width,height,(int)value,isFilled,(int)fillValue,0.0F,0.0F,96,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,int value,boolean isFilled) {
    return drawEllipse$default(this,width,height,(int)value,isFilled,(int)0,0.0F,0.0F,112,(Object)null);
  }
  @NotNull
  public final KTerminalData drawEllipse(int width,int height,int value) {
    return drawEllipse$default(this,width,height,(int)value,false,(int)0,0.0F,0.0F,120,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,char var2,boolean isFilled,char fillChar,float fillForeground,float fillBackground) {
    return this.drawCircle(radius,(int)var2,isFilled,(int)fillChar,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawCircle$default(KTerminalData var0,int var1,char var2,boolean var3,char var4,float var5,float var6,int var7,Object var8) {
    if((var7&4)!=0) {
      var3=false;
    }
    if((var7&8)!=0) {
      var4=var2;
    }
    if((var7&16)!=0) {
      var5=var0.cursor.getForegroundColor();
    }
    if((var7&32)!=0) {
      var6=var0.cursor.getBackgroundColor();
    }
    return var0.drawCircle(var1,var2,var3,var4,var5,var6);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,char var2,boolean isFilled,char fillChar,float fillForeground) {
    return drawCircle$default(this,radius,(char)var2,isFilled,(char)fillChar,fillForeground,0.0F,32,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,char var2,boolean isFilled,char fillChar) {
    return drawCircle$default(this,radius,(char)var2,isFilled,(char)fillChar,0.0F,0.0F,48,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,char var2,boolean isFilled) {
    return drawCircle$default(this,radius,(char)var2,isFilled,(char)'\u0000',0.0F,0.0F,56,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,char var2) {
    return drawCircle$default(this,radius,(char)var2,false,(char)'\u0000',0.0F,0.0F,60,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,int value,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    List circlePlot=KTerminalShapePlotter.INSTANCE.plotCircle(this.cursor.getX(),this.cursor.getY(),radius);
    this.drawShape(circlePlot,value);
    if(isFilled) {
      this.fillShape(circlePlot,fillValue,fillForeground,fillBackground);
    }
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData drawCircle$default(KTerminalData var0,int var1,int var2,boolean var3,int var4,float var5,float var6,int var7,Object var8) {
    if((var7&4)!=0) {
      var3=false;
    }
    if((var7&8)!=0) {
      var4=var2;
    }
    if((var7&16)!=0) {
      var5=var0.cursor.getForegroundColor();
    }
    if((var7&32)!=0) {
      var6=var0.cursor.getBackgroundColor();
    }
    return var0.drawCircle(var1,var2,var3,var4,var5,var6);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,int value,boolean isFilled,int fillValue,float fillForeground) {
    return drawCircle$default(this,radius,(int)value,isFilled,(int)fillValue,fillForeground,0.0F,32,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,int value,boolean isFilled,int fillValue) {
    return drawCircle$default(this,radius,(int)value,isFilled,(int)fillValue,0.0F,0.0F,48,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,int value,boolean isFilled) {
    return drawCircle$default(this,radius,(int)value,isFilled,(int)0,0.0F,0.0F,56,(Object)null);
  }
  @NotNull
  public final KTerminalData drawCircle(int radius,int value) {
    return drawCircle$default(this,radius,(int)value,false,(int)0,0.0F,0.0F,60,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,char var5,boolean isFilled,char fillChar,float fillForeground,float fillBackground) {
    return this.drawTriangle(leftX,leftY,rightX,rightY,(int)var5,isFilled,(int)fillChar,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawTriangle$default(KTerminalData var0,int var1,int var2,int var3,int var4,char var5,boolean var6,char var7,float var8,float var9,int var10,Object var11) {
    if((var10&32)!=0) {
      var6=false;
    }
    if((var10&64)!=0) {
      var7=var5;
    }
    if((var10&128)!=0) {
      var8=var0.cursor.getForegroundColor();
    }
    if((var10&256)!=0) {
      var9=var0.cursor.getBackgroundColor();
    }
    return var0.drawTriangle(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,char var5,boolean isFilled,char fillChar,float fillForeground) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(char)var5,isFilled,(char)fillChar,fillForeground,0.0F,256,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,char var5,boolean isFilled,char fillChar) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(char)var5,isFilled,(char)fillChar,0.0F,0.0F,384,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,char var5,boolean isFilled) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(char)var5,isFilled,(char)'\u0000',0.0F,0.0F,448,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,char var5) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(char)var5,false,(char)'\u0000',0.0F,0.0F,480,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,int value,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    List trianglePlot=KTerminalShapePlotter.INSTANCE.plotTriangle(this.cursor.getX(),this.cursor.getY(),leftX,leftY,rightX,rightY);
    this.drawShape(trianglePlot,value);
    if(isFilled) {
      this.fillShape(trianglePlot,fillValue,fillForeground,fillBackground);
    }
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData drawTriangle$default(KTerminalData var0,int var1,int var2,int var3,int var4,int var5,boolean var6,int var7,float var8,float var9,int var10,Object var11) {
    if((var10&32)!=0) {
      var6=false;
    }
    if((var10&64)!=0) {
      var7=var5;
    }
    if((var10&128)!=0) {
      var8=var0.cursor.getForegroundColor();
    }
    if((var10&256)!=0) {
      var9=var0.cursor.getBackgroundColor();
    }
    return var0.drawTriangle(var1,var2,var3,var4,var5,var6,var7,var8,var9);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,int value,boolean isFilled,int fillValue,float fillForeground) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(int)value,isFilled,(int)fillValue,fillForeground,0.0F,256,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,int value,boolean isFilled,int fillValue) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(int)value,isFilled,(int)fillValue,0.0F,0.0F,384,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,int value,boolean isFilled) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(int)value,isFilled,(int)0,0.0F,0.0F,448,(Object)null);
  }
  @NotNull
  public final KTerminalData drawTriangle(int leftX,int leftY,int rightX,int rightY,int value) {
    return drawTriangle$default(this,leftX,leftY,rightX,rightY,(int)value,false,(int)0,0.0F,0.0F,480,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,char topLeft,char topRight,char bottomLeft,char bottomRight,char horizontal,char vertical,boolean isFilled,char fillChar,float fillForeground,float fillBackground) {
    return this.drawBox(width,height,(int)topLeft,(int)topRight,(int)bottomLeft,(int)bottomRight,(int)horizontal,(int)vertical,isFilled,(int)fillChar,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawBox$default(KTerminalData var0,int var1,int var2,char var3,char var4,char var5,char var6,char var7,char var8,boolean var9,char var10,float var11,float var12,int var13,Object var14) {
    if((var13&256)!=0) {
      var9=false;
    }
    if((var13&512)!=0) {
      var10=' ';
    }
    if((var13&1024)!=0) {
      var11=var0.cursor.getForegroundColor();
    }
    if((var13&2048)!=0) {
      var12=var0.cursor.getBackgroundColor();
    }
    return var0.drawBox(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var11,var12);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,char topLeft,char topRight,char bottomLeft,char bottomRight,char horizontal,char vertical,boolean isFilled,char fillChar,float fillForeground) {
    return drawBox$default(this,width,height,(char)topLeft,(char)topRight,(char)bottomLeft,(char)bottomRight,(char)horizontal,(char)vertical,isFilled,(char)fillChar,fillForeground,0.0F,2048,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,char topLeft,char topRight,char bottomLeft,char bottomRight,char horizontal,char vertical,boolean isFilled,char fillChar) {
    return drawBox$default(this,width,height,(char)topLeft,(char)topRight,(char)bottomLeft,(char)bottomRight,(char)horizontal,(char)vertical,isFilled,(char)fillChar,0.0F,0.0F,3072,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,char topLeft,char topRight,char bottomLeft,char bottomRight,char horizontal,char vertical,boolean isFilled) {
    return drawBox$default(this,width,height,(char)topLeft,(char)topRight,(char)bottomLeft,(char)bottomRight,(char)horizontal,(char)vertical,isFilled,(char)'\u0000',0.0F,0.0F,3584,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,char topLeft,char topRight,char bottomLeft,char bottomRight,char horizontal,char vertical) {
    return drawBox$default(this,width,height,(char)topLeft,(char)topRight,(char)bottomLeft,(char)bottomRight,(char)horizontal,(char)vertical,false,(char)'\u0000',0.0F,0.0F,3840,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,int topLeft,int topRight,int bottomLeft,int bottomRight,int horizontal,int vertical,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    List plotList=KTerminalShapePlotter.INSTANCE.plotRect(this.cursor.getX(),this.cursor.getY(),width,height);
    Iterable $receiver$iv=(Iterable)plotList;
    Iterator var15=$receiver$iv.iterator();
    while(var15.hasNext()) {
      Object element$iv=var15.next();
      Pair it=(Pair)element$iv;
      int var18=false;
      this.workingCursor.setX(((Number)it.getFirst()).intValue());
      this.workingCursor.setY(((Number)it.getSecond()).intValue());
      int var19=((Number)it.getFirst()).intValue();
      int var20;
      if(var19==this.cursor.getX()) {
        var20=((Number)it.getSecond()).intValue();
        if(var20==this.cursor.getY()) {
          this.write(topLeft);
        }else if(var20==this.cursor.getY()+height-1) {
          this.write(bottomLeft);
        }else {
          this.write(vertical);
        }
      }else if(var19==this.cursor.getX()+width-1) {
        var20=((Number)it.getSecond()).intValue();
        if(var20==this.cursor.getY()) {
          this.write(topRight);
        }else if(var20==this.cursor.getY()+height-1) {
          this.write(bottomRight);
        }else {
          this.write(vertical);
        }
      }else {
        this.write(horizontal);
      }
    }
    if(isFilled) {
      this.fillShape(plotList,fillValue,fillForeground,fillBackground);
    }
    return this;
  }
  // $FF: synthetic method
  public static KTerminalData drawBox$default(KTerminalData var0,int var1,int var2,int var3,int var4,int var5,int var6,int var7,int var8,boolean var9,int var10,float var11,float var12,int var13,Object var14) {
    if((var13&256)!=0) {
      var9=false;
    }
    if((var13&512)!=0) {
      var10=0;
    }
    if((var13&1024)!=0) {
      var11=var0.cursor.getForegroundColor();
    }
    if((var13&2048)!=0) {
      var12=var0.cursor.getBackgroundColor();
    }
    return var0.drawBox(var1,var2,var3,var4,var5,var6,var7,var8,var9,var10,var11,var12);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,int topLeft,int topRight,int bottomLeft,int bottomRight,int horizontal,int vertical,boolean isFilled,int fillValue,float fillForeground) {
    return drawBox$default(this,width,height,(int)topLeft,(int)topRight,(int)bottomLeft,(int)bottomRight,(int)horizontal,(int)vertical,isFilled,(int)fillValue,fillForeground,0.0F,2048,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,int topLeft,int topRight,int bottomLeft,int bottomRight,int horizontal,int vertical,boolean isFilled,int fillValue) {
    return drawBox$default(this,width,height,(int)topLeft,(int)topRight,(int)bottomLeft,(int)bottomRight,(int)horizontal,(int)vertical,isFilled,(int)fillValue,0.0F,0.0F,3072,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,int topLeft,int topRight,int bottomLeft,int bottomRight,int horizontal,int vertical,boolean isFilled) {
    return drawBox$default(this,width,height,(int)topLeft,(int)topRight,(int)bottomLeft,(int)bottomRight,(int)horizontal,(int)vertical,isFilled,(int)0,0.0F,0.0F,3584,(Object)null);
  }
  @NotNull
  public final KTerminalData drawBox(int width,int height,int topLeft,int topRight,int bottomLeft,int bottomRight,int horizontal,int vertical) {
    return drawBox$default(this,width,height,(int)topLeft,(int)topRight,(int)bottomLeft,(int)bottomRight,(int)horizontal,(int)vertical,false,(int)0,0.0F,0.0F,3840,(Object)null);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled,char fillChar,float fillForeground,float fillBackground) {
    return this.drawDoubleBox(width,height,isFilled,(int)fillChar,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawDoubleBox$default(KTerminalData var0,int var1,int var2,boolean var3,char var4,float var5,float var6,int var7,Object var8) {
    if((var7&16)!=0) {
      var5=var0.cursor.getForegroundColor();
    }
    if((var7&32)!=0) {
      var6=var0.cursor.getBackgroundColor();
    }
    return var0.drawDoubleBox(var1,var2,var3,var4,var5,var6);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled,char fillChar,float fillForeground) {
    return drawDoubleBox$default(this,width,height,isFilled,(char)fillChar,fillForeground,0.0F,32,(Object)null);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled,char fillChar) {
    return drawDoubleBox$default(this,width,height,isFilled,(char)fillChar,0.0F,0.0F,48,(Object)null);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    return this.drawBox(width,height,(int)201,(int)187,(int)200,(int)188,(int)205,(int)186,isFilled,(int)fillValue,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawDoubleBox$default(KTerminalData var0,int var1,int var2,boolean var3,int var4,float var5,float var6,int var7,Object var8) {
    if((var7&4)!=0) {
      var3=false;
    }
    if((var7&8)!=0) {
      var4=0;
    }
    if((var7&16)!=0) {
      var5=var0.cursor.getForegroundColor();
    }
    if((var7&32)!=0) {
      var6=var0.cursor.getBackgroundColor();
    }
    return var0.drawDoubleBox(var1,var2,var3,var4,var5,var6);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled,int fillValue,float fillForeground) {
    return drawDoubleBox$default(this,width,height,isFilled,(int)fillValue,fillForeground,0.0F,32,(Object)null);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled,int fillValue) {
    return drawDoubleBox$default(this,width,height,isFilled,(int)fillValue,0.0F,0.0F,48,(Object)null);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height,boolean isFilled) {
    return drawDoubleBox$default(this,width,height,isFilled,(int)0,0.0F,0.0F,56,(Object)null);
  }
  @NotNull
  public final KTerminalData drawDoubleBox(int width,int height) {
    return drawDoubleBox$default(this,width,height,false,(int)0,0.0F,0.0F,60,(Object)null);
  }
  @NotNull
  public final KTerminalData drawSingleBox(int width,int height,boolean isFilled,char fillChar,@NotNull Color fillForeground,@NotNull Color fillBackground) {
    // Intrinsics.checkParameterIsNotNull(fillForeground,"fillForeground");
    // Intrinsics.checkParameterIsNotNull(fillBackground,"fillBackground");
    return this.drawSingleBox(width,height,isFilled,fillChar,fillForeground,fillBackground);
  }
  @NotNull
  public final KTerminalData drawSingleBox(int width,int height,boolean isFilled,int fillValue,float fillForeground,float fillBackground) {
    return this.drawBox(width,height,(int)218,(int)191,(int)192,(int)217,(int)196,(int)179,isFilled,(int)fillValue,fillForeground,fillBackground);
  }
  // $FF: synthetic method
  public static KTerminalData drawSingleBox$default(KTerminalData var0,int var1,int var2,boolean var3,int var4,float var5,float var6,int var7,Object var8) {
    if((var7&4)!=0) {
      var3=false;
    }
    if((var7&8)!=0) {
      var4=0;
    }
    if((var7&16)!=0) {
      var5=var0.cursor.getForegroundColor();
    }
    if((var7&32)!=0) {
      var6=var0.cursor.getBackgroundColor();
    }
    return var0.drawSingleBox(var1,var2,var3,var4,var5,var6);
  }
  @NotNull
  public final KTerminalData drawSingleBox(int width,int height,boolean isFilled,int fillValue,float fillForeground) {
    return drawSingleBox$default(this,width,height,isFilled,fillValue,fillForeground,0.0F,32,(Object)null);
  }
  @NotNull
  public final KTerminalData drawSingleBox(int width,int height,boolean isFilled,int fillValue) {
    return drawSingleBox$default(this,width,height,isFilled,fillValue,0.0F,0.0F,48,(Object)null);
  }
  @NotNull
  public final KTerminalData drawSingleBox(int width,int height,boolean isFilled) {
    return drawSingleBox$default(this,width,height,isFilled,0,0.0F,0.0F,56,(Object)null);
  }
  @NotNull
  public final KTerminalData drawSingleBox(int width,int height) {
    return drawSingleBox$default(this,width,height,false,0,0.0F,0.0F,60,(Object)null);
  }
  @NotNull
  public String toString() {
    String output="[width="+this.width+",height="+this.height+",defaultForegroundColor="+this.defaultForegroundColor+','+"defaultBackgroundColor="+this.defaultBackgroundColor+",cursor={x="+this.cursor.getX()+",y="+this.cursor.getY()+','+"foreground="+this.cursor.getForegroundColor()+",background="+this.cursor.getBackgroundColor()+"},terminal={\n";
    int j=0;
    for(int var3=this.height;j<var3;++j) {
      int i=0;
      for(int var5=this.width;i<var5;++i) {
        output=output+this.terminal[i][j].getChar();
      }
      output=output+"\n";
    }
    output=output+"}]";
    return output;
  }
  public final float getDefaultForegroundColor() {
    return this.defaultForegroundColor;
  }
  public final void setDefaultForegroundColor(float var1) {
    this.defaultForegroundColor=var1;
  }
  public final float getDefaultBackgroundColor() {
    return this.defaultBackgroundColor;
  }
  public final void setDefaultBackgroundColor(float var1) {
    this.defaultBackgroundColor=var1;
  }
  @Nullable
  public final Map getCustomColorMap() {
    return this.customColorMap;
  }
  public KTerminalData(int width,int height,float defaultForegroundColor,float defaultBackgroundColor,@Nullable Map customColorMap) {
    this.defaultForegroundColor=defaultForegroundColor;
    this.defaultBackgroundColor=defaultBackgroundColor;
    this.customColorMap=customColorMap;
    this.width=width;
    this.height=height;
    KTerminalGlyph[][] var6=new KTerminalGlyph[width][];
    for(int var7=0;var7<width;++var7) {
      int var9=false;
      int var10=height;
      KTerminalGlyph[] var11=new KTerminalGlyph[height];
      for(int var12=0;var12<var10;++var12) {
        int var16=false;
        KTerminalGlyph var17=new KTerminalGlyph(0,this.defaultForegroundColor,this.defaultBackgroundColor,0.0F,0.0F,0.0F,0.0F,false,false,504,null);
        var11[var12]=var17;
      }
      var6[var7]=var11;
    }
    this.terminal=(KTerminalGlyph[][])var6;
    this.cursor=new Cursor();
    this.workingCursor=new Cursor();
  }
  // $FF: synthetic method
  public KTerminalData(int var1,int var2,float var3,float var4,Map var5,int var6,DefaultConstructorMarker var7) {
    if((var6&4)!=0) {
      var3=Color.WHITE.toFloatBits();
    }
    if((var6&8)!=0) {
      var4=Color.BLACK.toFloatBits();
    }
    if((var6&16)!=0) {
      var5=null;
    }
    this(var1,var2,var3,var4,var5);
  }
  @Metadata(mv= {1,1,18},bv= {1,0,3},k=1,d1= {
    "\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002BU\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e¢\u0006\u0002\u0010\u0010J\u0018\u0010,\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0012\u0010.\u001a\u00020/2\n\u00100\u001a\u00060\u0000R\u000201J\u0016\u0010.\u001a\u00020/2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004JV\u0010.\u001a\u00020/2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eR$\u0010\b\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0018\u0010\u0013\"\u0004\b\u0019\u0010\u0015R$\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u001a\"\u0004\b\u001b\u0010\u001cR$\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u000e8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u001a\"\u0004\b\u001d\u0010\u001cR$\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010\u0015R$\u0010\f\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R$\u0010\t\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\"\u0010\u0013\"\u0004\b#\u0010\u0015R$\u0010\n\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00078F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b$\u0010\u0013\"\u0004\b%\u0010\u0015R$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010'\"\u0004\b+\u0010)¨\u00062"},d2= {
      "Lcom/halfdeadgames/kterminal/KTerminalData$Cursor;","","(Lcom/halfdeadgames/kterminal/KTerminalData;)V","x","","y","foregroundColor","","backgroundColor","rotation","scale","offsetX","offsetY","isFlippedX","","isFlippedY","(Lcom/halfdeadgames/kterminal/KTerminalData;IIFFFFFFZZ)V","value","getBackgroundColor","()F","setBackgroundColor","(F)V","cursorGlyph","Lcom/halfdeadgames/kterminal/KTerminalGlyph;","getForegroundColor","setForegroundColor","()Z","setFlippedX","(Z)V","setFlippedY","getOffsetX","setOffsetX","getOffsetY","setOffsetY","getRotation","setRotation","getScale","setScale","getX","()I","setX","(I)V","getY","setY","clampCursor","max","set","","cursor","Lcom/halfdeadgames/kterminal/KTerminalData;","kterminal"})
  public final class Cursor{
    private KTerminalGlyph cursorGlyph;
    private int x;
    private int y;
    public final float getForegroundColor() {
      return this.cursorGlyph.getForegroundColor();
    }
    public final void setForegroundColor(float value) {
      this.cursorGlyph.setForegroundColor(value);
    }
    public final float getBackgroundColor() {
      return this.cursorGlyph.getBackgroundColor();
    }
    public final void setBackgroundColor(float value) {
      this.cursorGlyph.setBackgroundColor(value);
    }
    public final float getRotation() {
      return this.cursorGlyph.getRotation();
    }
    public final void setRotation(float value) {
      this.cursorGlyph.setRotation(value);
    }
    public final float getScale() {
      return this.cursorGlyph.getScale();
    }
    public final void setScale(float value) {
      this.cursorGlyph.setScale(value);
    }
    public final float getOffsetX() {
      return this.cursorGlyph.getOffsetX();
    }
    public final void setOffsetX(float value) {
      this.cursorGlyph.setOffsetX(value);
    }
    public final float getOffsetY() {
      return this.cursorGlyph.getOffsetY();
    }
    public final void setOffsetY(float value) {
      this.cursorGlyph.setOffsetY(value);
    }
    public final boolean isFlippedX() {
      return this.cursorGlyph.isFlippedX();
    }
    public final void setFlippedX(boolean value) {
      this.cursorGlyph.setFlippedX(value);
    }
    public final boolean isFlippedY() {
      return this.cursorGlyph.isFlippedY();
    }
    public final void setFlippedY(boolean value) {
      this.cursorGlyph.setFlippedY(value);
    }
    private final int clampCursor(int value,int max) {
      int tempValue=value;
      while(tempValue<0||tempValue>=max) {
        if(tempValue<0) {
          tempValue+=max;
        }else if(tempValue>=max) {
          tempValue-=max;
        }
      }
      return tempValue;
    }
    public final int getX() {
      return this.x;
    }
    public final void setX(int value) {
      this.x=this.clampCursor(value,KTerminalData.this.getWidth());
    }
    public final int getY() {
      return this.y;
    }
    public final void setY(int value) {
      this.y=this.clampCursor(value,KTerminalData.this.getHeight());
    }
    public final void set(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
      this.setX(x);
      this.setY(y);
      this.cursorGlyph.set(0,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
    }
    public final void set(int x,int y) {
      this.setX(x);
      this.setY(y);
    }
    public final void set(@NotNull Cursor cursor) {
      // Intrinsics.checkParameterIsNotNull(cursor,"cursor");
      this.set(cursor.x,cursor.y,cursor.getForegroundColor(),cursor.getBackgroundColor(),cursor.getRotation(),cursor.getScale(),cursor.getOffsetX(),cursor.getOffsetY(),cursor.isFlippedX(),cursor.isFlippedY());
    }
    public Cursor(int x,int y,float foregroundColor,float backgroundColor,float rotation,float scale,float offsetX,float offsetY,boolean isFlippedX,boolean isFlippedY) {
      this.cursorGlyph=new KTerminalGlyph(0,foregroundColor,backgroundColor,rotation,scale,offsetX,offsetY,isFlippedX,isFlippedY);
      this.x=x;
      this.y=y;
    }
    public Cursor() {
      this(0,0,KTerminalData.this.getDefaultForegroundColor(),KTerminalData.this.getDefaultBackgroundColor(),0.0F,1.0F,0.0F,0.0F,false,false);
    }
  }
  public static final class Companion{
    private Companion() {}
    // $FF: synthetic method
    public Companion(Object $constructor_marker) {
      this();
    }
  }
  public final class KTerminalData$fillShape$$inlined$thenBy$1 implements Comparator{
    // $FF: synthetic field
    final Comparator $this_thenBy;
    public KTerminalData$fillShape$$inlined$thenBy$1(Comparator var1) {
      this.$this_thenBy=var1;
    }
    public final int compare(Object a,Object b) {
      int previousCompare=this.$this_thenBy.compare(a,b);
      int var10000;
      if(previousCompare!=0) {
        var10000=previousCompare;
      }else {
        Pair it=(Pair)a;
        int var5=0;
        Comparable var8=(Comparable)((Number)it.getFirst()).intValue();
        it=(Pair)b;
        Comparable var6=var8;
        var5=0;
        Integer var7=((Number)it.getFirst()).intValue();
        var10000=ComparisonsKt.compareValues(var6,(Comparable)var7);
      }
      return var10000;
    }
  }
  public final int compare(Object a,Object b) {
    Pair it=(Pair)a;
    int var4=0;
    Comparable var10000=(Comparable)((Number)it.getSecond()).intValue();
    it=(Pair)b;
    Comparable var5=var10000;
    var4=0;
    Integer var6=((Number)it.getSecond()).intValue();
    return ComparisonsKt.compareValues(var5,(Comparable)var6);
  }
}
// KTerminalData$fillShape$$inlined$thenBy$1.java
