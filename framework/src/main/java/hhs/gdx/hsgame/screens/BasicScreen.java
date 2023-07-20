package hhs.gdx.hsgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import hhs.gdx.hsgame.entitys.BasicEntity;
import hhs.gdx.hsgame.entitys.Entity;
import hhs.gdx.hsgame.tools.LoopThread;
import hhs.gdx.hsgame.tools.Resourse;
import hhs.gdx.hsgame.ui.Debug;
import hhs.gdx.hsgame.util.thread.ThreadCenter;
import java.util.ArrayList;

public class BasicScreen extends ScreenAdapter{
  LoopThread updateThread;
  public ArrayList<Entity> entities;
  public ThreadCenter threads;
  public Stage stage;
  public InputMultiplexer input;
  public Color clearColor=Color.WHITE;
  SpriteBatch batch;
  ShapeRenderer shape;
  public OrthographicCamera camera;
  /**
   * debug
   */
  public Debug d;
  public boolean debug=false;
  public BasicScreen() {
    Resourse.screens.put(getClass(),this);
    input=new InputMultiplexer();
    stage=new Stage();
    input.addProcessor(stage);
    BitmapFont f=new BitmapFont();
    f.getData().setScale(4,4);
    d=new Debug(new Label.LabelStyle(f,Color.GOLD));
    d.setZIndex(100);
    stage.addActor(d);
    batch=Resourse.batch;
    shape=Resourse.shape;
    shape.setAutoShapeType(true);
    camera=new OrthographicCamera(Resourse.width,Resourse.height);
    camera.position.set(Resourse.width/2,Resourse.height/2,0);
    Resourse.cacheRender.setView(camera);
    entities=new ArrayList<>(500);
    threads=new ThreadCenter();
    updateThread=new LoopThread() {
      @Override
      public void loop() {
        for(Entity e:entities) {
          if(!e.update) continue;
          e.update(Gdx.graphics.getDeltaTime());
        }
      }
    };
  }
  @Override
  public void render(float arg0) {
    ScreenUtils.clear(clearColor);
    camera.update();
    Resourse.cacheRender.draw(arg0);
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for(Entity e:entities) {
      if(!e.hide&&e.update) {
        e.UpdateAndRender(batch,arg0);
      }else {
        if(e.update) e.update(arg0);
        if(!e.hide) e.render(batch);
      }
    }
    batch.end();
    if(debug) {
      shape.setProjectionMatrix(camera.combined);
      shape.begin();
      for(Entity e:entities) {
        if(e instanceof BasicEntity) ((BasicEntity)e).debugDraw(shape);
      }
      shape.end();
    }
    stage.act(arg0);
    stage.draw();
  }
  @Override
  public void hide() {
    updateThread.interrupt();
  }
  @Override
  public void show() {
    threads.start();
    // updateThread.start();
    Gdx.input.setInputProcessor(input);
  }
  @Override
  public void dispose() {
    stage.dispose();
    for(Entity e:entities) {
      e.dispose();
    }
  }
  @Override
  public void resize(int width,int height) {
    ScalingViewport tv=(ScalingViewport)stage.getViewport();
    tv.setScreenSize(width,height);
    stage.setViewport(tv);
  }
  public void addEntity(Entity e) {
    e.screen=this;
    entities.add(e);
  }
  public ArrayList<Entity> getEntities() {
    return this.entities;
  }
  public void setEntities(ArrayList<Entity> entities) {
    this.entities=entities;
  }
  public Stage getStage() {
    return this.stage;
  }
  public void setStage(Stage stage) {
    this.stage=stage;
  }
  public OrthographicCamera getCamera() {
    return this.camera;
  }
  public void setCamera(OrthographicCamera camera) {
    this.camera=camera;
  }
  public boolean getDebug() {
    return this.debug;
  }
  public void setDebug(boolean debug) {
    this.debug=debug;
  }
  public Color getClearColor() {
    return this.clearColor;
  }
  public void setClearColor(Color clearColor) {
    this.clearColor=clearColor;
  }
}
