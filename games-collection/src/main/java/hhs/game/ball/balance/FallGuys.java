package hhs.game.ball.balance;

import java.util.LinkedList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hhs.game.lost.games.Constant;
import hhs.hhshaohao.mygame2.Tools.Font;
import hhs.hhshaohao.mygame2.Tools.MsgConsole;
import pama1234.util.Annotations.ScreenDescription;

@ScreenDescription("3D平衡球")
public class FallGuys extends Game{

  public static Color clearColor=Color.BLACK;
  public static SpriteBatch batch;
  public static ModelBatch modelBatch,shadowBatch;
  public static LoadResScreen loader;
  public static StaticRes res;
  public static LinkedList<Screen> screens=new LinkedList<>();
  public static Font font;

  Stage stage;
  Label debug;

  @Override
  public void create() {
    Bullet.init();

    Gdx.graphics.setVSync(false);
    batch=new SpriteBatch();
    modelBatch=new ModelBatch();
    //    DefaultShader.defaultDepthFunc=GL20.GL_LEQUAL;
    DefaultShader.defaultDepthFunc=GL20.GL_LEQUAL;
    shadowBatch=new ModelBatch(new DepthShaderProvider());
    font=new Font(false,Gdx.files.internal("auto.ttf"));

    res=new StaticRes(this);

    loader=new LoadResScreen(batch);
    screens.add(new StartScreen());

    stage=new Stage();
    Label.LabelStyle l;
    debug=new Label("",l=new Label.LabelStyle(font.get(50,Color.WHITE),Color.BLUE));
    debug.setPosition(0,100);
    stage.addActor(debug);

    Label c=new Label("",l) {
      @Override
      public void act(float delta) {
        super.act(delta);
        MsgConsole.update(delta);
        this.setText(MsgConsole.currentText());
        this.setColor(1.0f,1.0f,1.0f,MsgConsole.colorAlpha());
      }
    };
    c.setPosition(Constant.width/2,Constant.height/2);
    stage.addActor(c);
  }

  public void setLoader(AssetManager m,Runnable r) {
    loader.set(m,r);
    setScreen(loader);
  }

  @Override
  public void render() {
    //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(clearColor.r,clearColor.g,clearColor.b,clearColor.a);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
    super.render();
    debug.setText("fps:"+
      Gdx.graphics.getFramesPerSecond()+
      "\n"+
      "heap:"+
      ((Gdx.app.getJavaHeap()+Gdx.app.getNativeHeap())/1048576)+
      "MB");
    stage.act();
    stage.draw();
  }

  @Override
  public void dispose() {
    for(Screen s:screens) {
      s.dispose();
    }
    loader.dispose();
    res.dispose();
    batch.dispose();
    modelBatch.dispose();
    shadowBatch.dispose();
    font.dispose();
  }

  @Override
  public void resize(int width,int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void setScreen(Screen screen) {
    screens.add(screen);
    super.setScreen(screen);
  }
}
