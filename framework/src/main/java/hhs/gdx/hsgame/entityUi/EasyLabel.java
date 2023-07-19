package hhs.gdx.hsgame.entityUi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.entitys.BasicEntity;
import hhs.gdx.hsgame.tools.LazyBitmapFont;

public class EasyLabel extends BasicEntity{
  public LazyBitmapFont font;
  public String text;
  public EasyLabel(LazyBitmapFont font,String text) {
    this.font=font;
    this.text=text;
  }
  @Override
  public void dispose() {}
  @Override
  public void render(SpriteBatch batch) {
    font.getData().setScale(size.y/font.fontSize,size.y/font.fontSize);
    font.drawText(
      batch,
      text,
      (int)pos.x,
      (int)pos.y,
      (int)size.x,
      (int)(font.fontSize*size.y/font.fontSize/2));
    font.getData().setScale(1,1);
  }
  public LazyBitmapFont getFont() {
    return this.font;
  }
  public void setFont(LazyBitmapFont font) {
    this.font=font;
  }
  public String getText() {
    return this.text;
  }
  public void setText(String text) {
    this.text=text;
  }
}
