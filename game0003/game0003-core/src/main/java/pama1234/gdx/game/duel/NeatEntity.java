package pama1234.gdx.game.duel;

import static pama1234.app.game.server.duel.util.Const.CANVAS_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.duel.util.ai.nnet.ClientFisheyeVision;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter.NetworkGroupParam;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.DisplayEntityListener;
import pama1234.math.Tools;
import pama1234.math.UtilMath;

public class NeatEntity extends Entity<Duel> implements DisplayEntityListener{
  public Game pg;
  public NeatEntity(Duel p,Game pg) {
    super(p);
    this.pg=pg;
  }
  @Override
  public void init() {
    p.param=new NetworkGroupParam(32);
    p.neatCenter=new NeatCenter(p.param);
    //---
    p.graphics=new Graphics(p,CANVAS_SIZE,CANVAS_SIZE);
    String polarVisionVert=Gdx.files.internal("shader/main0005/vision-polar.vert").readString(),
      polarVisionFrag=Gdx.files.internal("shader/main0005/vision-polar.frag").readString();
    String cartesianVisionVert=Gdx.files.internal("shader/main0005/example.vert").readString(),
      cartesianVisionFrag=Gdx.files.internal("shader/main0005/vision-cartesian.frag").readString();
    p.cartesianShader=new ShaderProgram(cartesianVisionVert,cartesianVisionFrag);
    int ts=p.param.canvasSize;
    p.player_a=new ClientFisheyeVision(p,
      new ShaderProgram(polarVisionVert,polarVisionFrag),
      new Graphics(p,ts,ts));
    p.player_b=new ClientFisheyeVision(p,
      new ShaderProgram(polarVisionVert,polarVisionFrag),
      new Graphics(p,ts,ts));
  }
  @Override
  public void update() {
    if(pg.system.stateIndex==ClientGameSystem.play) {
      p.time++;
      pg.system.myGroup.player.engine.setScore(1,pg.system.currentState.getScore(pg.system.myGroup.id));
      pg.system.otherGroup.player.engine.setScore(1,pg.system.currentState.getScore(pg.system.otherGroup.id));
      if(p.time>p.timeLimit) {
        p.timeLimit=p.timeLimitConst;
        pg.newGame(true,false);
      }
    }
    p.player_a.update(pg.system.myGroup.player);
    p.player_b.update(pg.system.otherGroup.player);
  }
  @Override
  public void display() {
    p.textScale(UtilMath.ceil(UtilMath.max(1,p.pus/3f)));
    float ts=p.textScale()*p.textSize();
    p.text(Tools.getFloatString(p.time,5,0)+"ms -> "+Tools.getFloatString(p.timeLimit,5,0)+"ms",0,0);
    p.text("real time score",0,ts);
    p.text("a - "+Tools.getFloatString(pg.system.myGroup.player.engine.getScore(1)),0,ts*2);
    p.text("b - "+Tools.getFloatString(pg.system.otherGroup.player.engine.getScore(1)),0,ts*3);
    p.text("final score",0,ts*4);
    p.text("a - "+Tools.getFloatString(pg.system.myGroup.player.engine.getScore(0)),0,ts*5);
    p.text("b - "+Tools.getFloatString(pg.system.otherGroup.player.engine.getScore(0)),0,ts*6);
  }
  @Override
  public void displayCam() {
    p.graphics.begin();
    p.background(255);
    pg.system.display();
    p.graphics.end();
    p.player_a.render();
    p.player_b.render();
    //---
    p.cartesianShader.bind();
    p.cartesianShader.setUniformf("u_dist",p.player_a.camX/CANVAS_SIZE,p.player_a.camY/CANVAS_SIZE);
    p.image(p.player_a.graphics.texture,-656,0,CANVAS_SIZE,CANVAS_SIZE,p.cartesianShader);
    //---
    p.image(p.graphics.texture,0,0,CANVAS_SIZE,CANVAS_SIZE);
    //---
    p.cartesianShader.bind();
    p.cartesianShader.setUniformf("u_dist",p.player_b.camX/CANVAS_SIZE,p.player_b.camY/CANVAS_SIZE);
    p.image(p.player_b.graphics.texture,656,0,CANVAS_SIZE,CANVAS_SIZE,p.cartesianShader);
    //---
    p.image(p.player_a.graphics.texture,-656,-656,CANVAS_SIZE,CANVAS_SIZE);
    p.image(p.player_b.graphics.texture,656,-656,CANVAS_SIZE,CANVAS_SIZE);
    p.clearMatrix();
  }
}
