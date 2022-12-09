package pama1234.gdx.game.app.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.SkylineStrategy;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter;
import com.badlogic.gdx.tools.bmfont.BitmapFontWriter.FontInfo;

import pama1234.gdx.util.element.RangeChar;

public class FontGenerator extends ScreenAdapter{
  String dir="D:/gdx project/project-0011/fontOut/";
  int width=1024,height=1152;
  @Override
  public void show() {
    int c=0;
    int size=4096;
    try {
      for(int i=0;i<Character.MAX_VALUE;i+=size) {
        saveFont(dir+"/"+c+"/","unifont-"+c,c,i,i+size);
        c++;
      }
    }catch(RuntimeException e) {}
    c=15;
    for(int i=(1<<12)*15;i<Character.MAX_VALUE;i+=size) {
      saveFont(dir+"/"+c+"/","unifont-"+c,c,i,i+size);
      c++;
    }
    Gdx.app.exit();
  }
  public void saveFont(String dir,String name,int i,int from,int to) {
    FontInfo info;
    FreeTypeFontGenerator generator;
    FreeTypeFontParameter param;
    info=new FontInfo("unifont",16);
    info.charset="utf-8";
    generator=new FreeTypeFontGenerator(Gdx.files.absolute("D:/swl/2022-12/unifont-15.0.01.ttf"));
    param=new FreeTypeFontParameter();
    param.size=16;
    param.renderCount=1;
    param.padTop=1;
    param.padRight=1;
    param.padBottom=1;
    param.padLeft=1;
    param.spaceX=-2;
    param.spaceY=-2;
    param.characters=new StringBuffer(new RangeChar(from,to)).toString();
    param.packer=new PixmapPacker(width,height,Format.Alpha,0,false,new SkylineStrategy());
    FreeTypeBitmapFontData data=generator.generateData(param);
    String[] out=BitmapFontWriter.writePixmaps(param.packer.getPages(),Gdx.files.absolute(dir),name);
    BitmapFontWriter.writeFont(data,out,Gdx.files.absolute(dir+name+".fnt"),info,width,height);
    // Hiero;
  }
  public static void main(String[] args) {
    new Lwjgl3Application(new Game() {
      @Override
      public void create() {
        setScreen(new FontGenerator());
      }
      @Override
      public void dispose() {
        super.dispose();
        screen.dispose();
      }
    },getDefaultConfiguration());
  }
  public static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
    Lwjgl3ApplicationConfiguration configuration=new Lwjgl3ApplicationConfiguration();
    configuration.setTitle("just-another-particle-system");
    configuration.useVsync(true);
    configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
    configuration.setWindowedMode(640,480);
    configuration.setWindowIcon("icon/icon128.png","icon/icon64.png","icon/icon32.png","icon/icon16.png");
    return configuration;
  }
}