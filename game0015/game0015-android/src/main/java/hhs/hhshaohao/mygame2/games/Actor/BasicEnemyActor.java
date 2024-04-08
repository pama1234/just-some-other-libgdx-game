package hhs.hhshaohao.mygame2.games.Actor;

import hhs.hhshaohao.mygame2.Tools.LazyBitmapFont;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.Stage.WorldStage;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Pool;

public abstract class BasicEnemyActor extends BasicRoleActor implements Pool.Poolable{

  public Texture texture;
  public float hp,ohp;

  public Vector2 pos;

  public BloodStyle s=new BloodStyle();

  public LazyBitmapFont font;
  Texture text=MyGame.am.get("hp.png",Texture.class);

  protected float time=0;

  public WorldStage ws;

  public WorldStage getWorldStage() {
    return ws;
  }

  public BasicEnemyActor(WorldStage world,Texture text,Vector2 pos,float hp,Shape shape) {
    super(world.world,text,pos,shape,Constant.role,Constant.player|Constant.wall);
    ws=world;
    texture=text;
    ohp=this.hp=hp;
    this.pos=pos;

    font=MyGame.font.get(BasicEnemyActor.class,s.fontSize);
    font.getData().setScale(s.size/(float)s.fontSize);
    font.setUseIntegerPositions(false);
    font.setColor(s.color);
  }

  public Texture getTexture() {
    return texture;
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    cheakHurt(delta);
    if(isWalk(body)) {
      walk(body,delta);
    }else {
      find(body,delta);
    }
  }

  public void cheakHurt(float t) {
    if(ws.getAttack().cheakHurt(polygon)&&(time+=t)>ws.getAttack().wait) {
      ws.getAttack().hurt(this,getStage());
      time=0;
    }

    if(hp<=0) {
      body.getWorld().destroyBody(body);
      getStage().getRoot().removeActor(this);
    }
  }

  @Override
  public void draw(Batch batch,float parentAlpha) {
    Camera cam=getStage().getCamera();
    if(cam.position.x-cam.viewportWidth/2<getX()+getWidth()
      &&getX()<cam.position.x+cam.viewportWidth/2
      &&cam.position.y-cam.viewportHeight/2<getY()+getHeight()
      &&getY()<cam.position.y+cam.viewportHeight/2) {
      super.draw(batch,parentAlpha);
      font.draw(batch,"生命值："+hp,getX(),getY()+getHeight()+s.size);
      batch.setColor(Color.RED);
      batch.draw(text,getX(),getY()-getHeight()/4,getWidth(),getHeight()/4);
      batch.setColor(Color.GREEN);
      batch.draw(
        text,getX(),getY()-getHeight()/4,getWidth()*(hp/ohp),getHeight()/4);
      batch.setColor(Color.WHITE);

    }
  }

  public BloodStyle getBloodSlotStyle() {
    return s;
  }

  public class BloodStyle{
    public int size=5,fontSize=30;
    public Color color=Color.GREEN;
    public GlyphLayout layout=new GlyphLayout();

    public BloodStyle() {}
  }
}
