package hhs.game.ball.balance;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import hhs.game.ball.balance.FallGuys;
import hhs.game.lost.games.Constant;
import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;

public class LoadResScreen implements Screen{

  Pixmap map;
  Texture text;
  SpriteBatch batch;

  String load="加载中...";
  float charWidth;

  AssetManager manager;
  Runnable run;

  LazyBitmapFont font;

  public LoadResScreen(SpriteBatch batch) {
    this.batch=batch;
    map=new Pixmap(Constant.width,Constant.height/10,Pixmap.Format.RGB565);
    map.setColor(Color.BLUE);
    map.fill();

    text=new Texture(map);

    font=FallGuys.font.get(64,Color.WHITE);
    charWidth=font.getWidth(load);
  }

  public void set(AssetManager m,Runnable r) {
    manager=m;
    run=r;
  }

  @Override
  public void show() {}

  @Override
  public void render(float p1) {
    if(manager!=null) {
      if(manager.update()) {
        run.run();
      }
      batch.begin();
      font.draw(batch,load,Constant.width/2-charWidth/2,Constant.height/2);
      batch.draw(text,0,0,manager.getProgress()*Constant.width,text.getHeight());
      batch.end();
    }
  }

  @Override
  public void resize(int p1,int p2) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {}
}
