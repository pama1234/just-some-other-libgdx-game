package hhs.hhshaohao.mygame2.games;

import hhs.hhshaohao.mygame2.Tools.AsyncManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import hhs.hhshaohao.mygame2.games.MyRes.FontRes;
import hhs.hhshaohao.mygame2.games.Screen.DeadScreen;
import hhs.hhshaohao.mygame2.games.Screen.LoadRes;
import hhs.hhshaohao.mygame2.games.Screen.MainScreen;
import hhs.hhshaohao.mygame2.games.Screen.UserScreen.InitScreen;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;

public class MyGame extends Game{

  public static final int WIDTH=1920;
  public static final int HEIGHT=1080;
  public static Viewport viewport;

  public static SpriteBatch batch;
  public static ShapeDrawer debug;
  public static Box2DDebugRenderer boxdebug;

  // 资源管理
  public static AssetManager am;
  public static AssetManager out;
  public static AsyncManager async;
  public static PublicRes res;
  public static FontRes font;

  // 存档
  public static Preferences archive;

  // debug和游戏特效
  public Stage gameView;
  public Image full;
  public static Label fps;

  public MainScreen mainScreen;
  public DeadScreen dead;
  public LoadRes loadRes;

  @Override
  public void create() {
    batch=new SpriteBatch();
    boxdebug=new Box2DDebugRenderer();

    async=new AsyncManager();

    viewport=new com.badlogic.gdx.utils.viewport.StretchViewport(WIDTH,HEIGHT);
    viewport.apply();
    viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    archive=Gdx.app.getPreferences(Constant.archive);

    debug=new ShapeDrawer(batch);
    debug.setTextureRegion(new TextureRegion(new Texture("null.png")));

    gameView=new ViewStage();

    full=new Image(new Texture("null.png"));
    full.setPosition(0,0);
    full.setSize(Constant.w,Constant.h);

    load();

    fps=new Label("",new Label.LabelStyle(font.get(1,50,Color.BLUE),Color.BLUE));
    fps.setPosition(0,100);

    gameView.addActor(full);
    gameView.addActor(fps);

    loadRes=new LoadRes(
      this,
      new Runnable() {

        @Override
        public void run() {
          res=new PublicRes(MyGame.this);
          mainScreen=new MainScreen(MyGame.this);
          dead=new DeadScreen(MyGame.this,null);

          if((archive.contains("name"))) {
            setScreen(mainScreen);
          }else {
            setScreen(new InitScreen());
          }
        }
      });
    setScreen(loadRes);
  }

  private void load() {
    am=new AssetManager(new InternalFileHandleResolver());
    out=new AssetManager(new AbsoluteFileHandleResolver());

    font=new FontRes();

    am.load("blue0.png",Texture.class);
    am.load("null.png",Texture.class);
    am.load("hp.png",Texture.class);
    am.load("blue1.png",Texture.class);
    am.load("attack.png",Texture.class);

    am.load("touchpad0.png",Texture.class);
    am.load("touchpad1.png",Texture.class);
  }

  @Override
  public void render() {
    async.update();
    gameView.act();
    viewport.apply();
    fps.setText(
      "fps:"+Gdx.graphics.getFramesPerSecond()+"\n"+"heap:"+(Gdx.app.getNativeHeap()+Gdx.app.getJavaHeap())/1024/1024+"MB");

    Gdx.gl.glClearColor(1,1,1,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.render();

    gameView.draw();
  }

  @Override
  public void setScreen(Screen screen) {
    full.setColor(Color.BLACK);
    super.setScreen(screen);
    full.addAction(Actions.color(Color.CLEAR,0.5f));
  }

  public void dead(Screen s) {
    dead.setReScreen(s);
    setScreen(dead);
  }

  @Override
  public void dispose() {
    if(am!=null) am.dispose();
    if(font!=null) font.dispose();
    if(batch!=null) batch.dispose();
    if(gameView!=null) gameView.dispose();
    if(mainScreen!=null) mainScreen.dispose();
    if(dead!=null) dead.dispose();
    if(loadRes!=null) loadRes.dispose();
    if(boxdebug!=null) boxdebug.dispose();
    if(async!=null) async.dispose();
  }

  @Override
  public void resize(int width,int height) {
    viewport.update(width,height);
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}
}
