package hhs.hhshaohao.mygame2.games.Actor.ToolActor;

import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;

import hhs.hhshaohao.mygame2.games.MyGame;

public class Text extends Actor implements Pool.Poolable{

  @Override
  public void reset() {
    ap=1;
  }

  public LazyBitmapFont font=MyGame.font.get(Text.class,30);

  public String str;

  public float ap=1,sp=1f;
  Pool<Text> pool;

  public Text(Pool<Text> pa,String str,float x,float y) {
    this.str=str;
    pool=pa;
    setPosition(x,y);

    font.getData().setScale(0.2f);
    font.setUseIntegerPositions(false);
    font.setColor(Color.RED);

    addAction(Actions.moveBy(0,MathUtils.random(5,10),1));
  }

  public void setText(String newText) {
    str=newText;
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    // font.setPosition(getX(), getY());
    ap-=sp*Gdx.graphics.getDeltaTime();
    font.setColor(font.getColor().r,font.getColor().b,font.getColor().b,ap);
    if(ap<=0) {
      pool.free(this);
      this.remove();
    }else {
      font.draw(batch,str,getX(),getY());
    }
  }
}
