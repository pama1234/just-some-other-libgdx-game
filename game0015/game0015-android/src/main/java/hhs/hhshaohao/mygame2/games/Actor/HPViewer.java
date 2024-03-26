package hhs.hhshaohao.mygame2.games.Actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import hhs.hhshaohao.mygame2.games.MyGame;

public class HPViewer extends Table{

  PlayerActor pa;

  Image b;
  Label l,l1;
  Texture f;

  public HPViewer(PlayerActor p) {
    this.pa=p;

    this.setFillParent(true);
    this.top().left();

    b=new Image(MyGame.am.get("hp.png",Texture.class));
    f=MyGame.am.get("hp.png",Texture.class);

    l=new Label("生命值",new Label.LabelStyle(MyGame.font.get(HPViewer.class,40),Color.BLUE));
    l1=new Label(
      "Null/Null",
      new Label.LabelStyle(MyGame.font.get(HPViewer.class,40),Color.GREEN));

    b.setColor(Color.BLACK);
    b.setSize(400,50);

    padLeft(50);
    padTop(20);

    add(l).row();
    add(b);
    add(l1).padLeft(20);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    l1.setText(pa.hp+"/"+pa.ohp);
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    super.draw(batch,parentAlpha);

    batch.setColor(Color.GREEN);
    batch.draw(f,b.getX(),b.getY(),b.getWidth()*(pa.hp/pa.ohp),b.getHeight());
  }
}
