package hhs.gdx.hsgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.utils.Array;

import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.tools.LoopThread;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.Debug;
import hhs.gdx.hsgame.util.thread.ThreadCenter;
import pama1234.gdx.util.app.UtilScreen2D;
import pama1234.math.UtilMath;

public abstract class BasicScreen extends UtilScreen2D{
  LoopThread updateThread;
  public Array<Entity> removes;
  public Array<Entity> entities;
  public ThreadCenter threads;
  public Stage stage;
  public InputMultiplexer input;
  public Color clearColor=Color.WHITE;
  public float timeAcceleration=1;
  SpriteBatch batch;
  public ShapeRenderer shape;
  public OrthographicCamera camera;
  /**
   * debug
   */
  public Debug d;
  public boolean debug=false;
  boolean stageFlag=false;
  // public boolean pixelPerfect;
  public BasicScreen() {
    Resource.screens.put(getClass(),this);
    input=new InputMultiplexer();
    stage=new Stage(new ScalingViewport(Scaling.fit,Resource.width,Resource.height,new OrthographicCamera()));
    stageFlag=true;
    input.addProcessor(stage);
    BitmapFont f=new BitmapFont();
    f.getData().setScale(4,4);
    d=new Debug(new Label.LabelStyle(f,Color.GOLD));
    d.setZIndex(100);
    stage.addActor(d);
    batch=Resource.batch;
    shape=Resource.shape;
    shape.setAutoShapeType(true);
    camera=new OrthographicCamera(Resource.width,Resource.height);
    camera.position.set(Resource.width/2,Resource.height/2,0);
    Resource.cacheRender.setView(camera);
    entities=new Array<>(500);
    removes=new Array<>();
    threads=new ThreadCenter();
    updateThread=new LoopThread("BasicScreen-LoopThread") {
      @Override
      public void loop() {
        for(Entity e:entities) {
          if(!e.update) continue;
          e.update(1/frameRate);
        }
      }
    };
    updateThread.frameRate(60);
  }
  @Override
  public void render(float d) {
    float delta=d*timeAcceleration;
    super.render(delta);
    if(!removes.isEmpty()) {
      entities.removeAll(removes,true);
      removes.clear();
    }
    ScreenUtils.clear(clearColor);
    camera.update();
    Resource.cacheRender.draw(delta);
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for(Entity e:entities) {
      if(!e.hide&&e.update) {
        e.UpdateAndRender(batch,delta);
      }else {
        if(e.update) e.update(delta);
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
    stage.act(delta);
    stage.draw();
  }
  @Override
  public void hide() {
    super.hide();
    updateThread.interrupt();
  }
  @Override
  public void show() {
    super.show();
    //threads.start();
    // updateThread.start();
    Gdx.input.setInputProcessor(input);
  }
  @Override
  public void dispose() {
    // super.dispose();
    stop=true;
    if(fontBatch!=null) fontBatch.dispose();
    if(font!=null) font.dispose();
    if(center!=null) center.dispose();
    if(serverCenter!=null) serverCenter.dispose();

    if(stage!=null) stage.dispose();
    for(Entity e:entities) {
      e.dispose();
    }
    entities.clear();
  }
  @Override
  public void resize(int width,int height) {
    super.resize(width,height);
    if(isAndroid) Resource.u=UtilMath.min(width,height)/8f;
    else Resource.u=UtilMath.min(width,height)/16f;
    ScalingViewport tv=(ScalingViewport)stage.getViewport();
    tv.setScreenSize(width,height);
    stage.setViewport(tv);
  }
  public void addEntity(Entity e) {
    e.screen=this;
    if(e instanceof BasicEntity be) be.setCam(camera);
    entities.add(e);
  }
  public void removeEntity(Entity e) {
    removes.add(e);
  }
  public void sortEntity() {
    entities.sort((a,b)->a.zindex-b.zindex);
  }
  //------------------------------------------------------------------
  public Array<Entity> getEntities() {
    return this.entities;
  }
  public void setEntities(Array<Entity> entities) {
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
  @Override
  public void setup() {}
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
  public float getTimeAcceleration() {
    return this.timeAcceleration;
  }
  public void setTimeAcceleration(float timeAcceleration) {
    this.timeAcceleration=timeAcceleration;
  }
}
