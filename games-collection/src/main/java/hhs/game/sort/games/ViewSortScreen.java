package hhs.game.sort.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import hhs.game.sort.CameraControl;
import hhs.game.sort.MyInputListener;
import hhs.game.sort.SortingAlgorithm;

public class ViewSortScreen implements Screen{

  MyGame game;
  SpriteBatch batch;
  OrthographicCamera camera;

  TextButton exit,restart;
  Stage st;
  InputMultiplexer inputs;

  ViewableArray array;

  Thread sort;
  SortingAlgorithm sa;

  public void setWaitTime(int wait) {
    sa.st=wait;
  }
  public ViewSortScreen(MyGame g,final SortingAlgorithm sort,int size) {
    game=g;
    batch=g.batch;
    inputs=new InputMultiplexer();

    array=new ViewableArray(g,size);
    sa=sort;
    sort.set(array);
    this.sort=new Thread(sort);

    camera=new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    camera.position.set(array.arr.length*5,array.maxHeight/2+Gdx.graphics.getHeight()/8,0);
    camera.zoom=array.sc;

    inputs.addProcessor(st=new Stage());
    inputs.addProcessor(new CameraControl(camera));

    exit=new TextButton("返回",game.text);
    exit.addListener(new MyInputListener() {
      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        ViewSortScreen.this.sort.interrupt();
        while(ViewSortScreen.this.sort.isAlive()) {}
        game.setScreen(game.cs);
      }
    });
    exit.setPosition(0,Gdx.graphics.getHeight()-exit.getHeight());

    restart=new TextButton("重新开始",game.text);
    restart.addListener(new MyInputListener() {
      @Override
      public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
        ViewSortScreen.this.sort.interrupt();
        while(ViewSortScreen.this.sort.isAlive()) {}
        array.reset();
        ViewSortScreen.this.sort=new Thread(sort);
        ViewSortScreen.this.sort.start();
      }
    });
    restart.setPosition(Gdx.graphics.getWidth()-restart.getWidth(),Gdx.graphics.getHeight()-restart.getHeight());

    st.addActor(exit);
    st.addActor(restart);
  }

  @Override
  public void dispose() {}

  @Override
  public void hide() {}

  @Override
  public void pause() {}

  @Override
  public void render(float p) {
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    array.viewArray(batch,p);
    batch.end();

    if(!sort.isAlive()) {

    }

    st.act();
    st.draw();
  }

  @Override
  public void resize(int p,int p1) {}

  @Override
  public void resume() {}

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputs);
    sort.start();
  }

}
