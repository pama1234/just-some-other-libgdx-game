package hhs.game.gongde.games;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import hhs.game.gongde.LazyBitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @Author hhshaohao
 * @Date 2023/03/11 22:23
 */
public class StaticRes{

  public static LazyBitmapFont font;
  public static Label.LabelStyle label;
  public static TextButton.TextButtonStyle textButton;

  public static void finishLoading(MyGame g) {
    font=new LazyBitmapFont(g.manager.get("auto.ttf",FreeTypeFontGenerator.class),64,Color.WHITE);
    label=new Label.LabelStyle(font,Color.WHITE);

    Pixmap up=new Pixmap(200,100,Pixmap.Format.RGB565);
    Pixmap down=new Pixmap(200,100,Pixmap.Format.RGB565);

    up.setColor(Color.WHITE);
    down.setColor(Color.GRAY);
    up.fill();
    down.fill();

    textButton=new TextButton.TextButtonStyle(ttd(new Texture(up)),ttd(new Texture(down)),null,font);
  }
  public static TextureRegionDrawable ttd(Texture t) {
    return new TextureRegionDrawable(new TextureRegion(t));
  }
}
