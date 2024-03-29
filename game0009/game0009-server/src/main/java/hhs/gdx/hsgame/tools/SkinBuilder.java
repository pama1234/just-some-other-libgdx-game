package hhs.gdx.hsgame.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SkinBuilder{
  BitmapFont font;
  public SkinBuilder(BitmapFont font) {
    this.font=font;
  }
  public Label.LabelStyle label(Color fcolor) {
    return new Label.LabelStyle(font,fcolor);
  }
  public TextButton.TextButtonStyle textButton(Drawable up,Drawable down,Drawable checked) {
    return new TextButton.TextButtonStyle(up,down,checked,font);
  }
}
