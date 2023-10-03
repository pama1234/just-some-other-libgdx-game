package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pools;

public class FontTool{
  public static int getWidth(BitmapFont font,String str) {
    GlyphLayout layout=Pools.obtain(GlyphLayout.class);
    layout.setText(font,str);
    return (int)layout.width;
  }
  // 绘制文字，超过w后自动换行
  public static void drawText(BitmapFont font,Batch batch,String text,int x,int y,int w,int lineSpacing) {
    int lastSubStrIndex=0;
    for(int i=0;i<text.length();i++) {
      // 计算当前字符串、最后一个字符、宽度
      String currentSubStr=text.substring(lastSubStrIndex,i+1); // 当前正在组建的一行字符串
      char currentLastChar=currentSubStr.charAt(currentSubStr.length()-1);
      int cW=getWidth(font,currentSubStr);
      if(cW>w||currentLastChar=='\n') {
        font.draw(batch,text.substring(lastSubStrIndex,i),x,y);
        y-=font.getCapHeight()/2+lineSpacing; // fontSize/2等于行高
        if(currentLastChar=='\n') {
          lastSubStrIndex=i+1;
        }else {
          lastSubStrIndex=i;
        }
      }
      if(i==text.length()-1) {
        font.draw(batch,text.substring(lastSubStrIndex,i+1),x,y);
      }
    }
  }
}
