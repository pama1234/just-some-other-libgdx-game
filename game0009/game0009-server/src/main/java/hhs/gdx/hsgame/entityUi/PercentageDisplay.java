package hhs.gdx.hsgame.entityUi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;

public class PercentageDisplay extends BasicEntity{
  public static Texture texture=PixmapBuilder.getRectangle(200,100,ColorTool.rgb(255,255,255));
  public Color front=Color.GREEN;
  public Color back=Color.RED;
  float per=1;
  Updater percentage;
  public PercentageDisplay(Updater percentage) {
    this.percentage=percentage;
  }
  public void update(float delta) {
    per=percentage.update();
  }
  public void render(SpriteBatch batch) {
    batch.setColor(back);
    batch.draw(texture,pos.x,pos.y,size.x,size.y);
    batch.setColor(front);
    batch.draw(texture,pos.x,pos.y,size.x*per,size.y);
    batch.setColor(Color.WHITE);
  }
  public void dispose() {}
  public static interface Updater{
    public abstract float update();
  }
}
