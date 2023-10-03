package pama1234.gdx.game.duel;

import static pama1234.app.game.server.duel.util.Const.CANVAS_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.Tools;
import pama1234.gdx.game.duel.util.ai.nnet.ClientFisheyeVision;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter;
import pama1234.gdx.game.duel.util.ai.nnet.NeatCenter.NetworkGroupParam;
import pama1234.gdx.game.state.state0002.Game;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.DisplayEntityListener;
import pama1234.math.UtilMath;

public class NeatEntity extends Entity<Duel> implements DisplayEntityListener{
  public Game pg;

  public Graphics graphics;
  // public ShaderProgram polarShader;
  public ShaderProgram cartesianShader;
  public ClientFisheyeVision player_a,player_b;

  public int timeLimitConst=60*10;
  public int time,timeLimit=timeLimitConst;
  public NeatEntity(Duel p,Game pg,boolean init) {
    super(p);
    this.pg=pg;
    if(init) init();
  }
  @Override
  public void init() {
    p.param=new NetworkGroupParam(32);
    p.neatCenter=new NeatCenter(p.param);

    graphics=new Graphics(p,CANVAS_SIZE,CANVAS_SIZE);
    String polarVisionVert=Gdx.files.internal("shader/main0005/vision-polar.vert").readString(),
      polarVisionFrag=Gdx.files.internal("shader/main0005/vision-polar.frag").readString();
    String cartesianVisionVert=Gdx.files.internal("shader/main0005/example.vert").readString(),
      cartesianVisionFrag=Gdx.files.internal("shader/main0005/vision-cartesian.frag").readString();
    cartesianShader=new ShaderProgram(cartesianVisionVert,cartesianVisionFrag);
    int ts=p.param.canvasSize;
    player_a=new ClientFisheyeVision(p,
      new ShaderProgram(polarVisionVert,polarVisionFrag),
      new Graphics(p,ts,ts));
    player_b=new ClientFisheyeVision(p,
      new ShaderProgram(polarVisionVert,polarVisionFrag),
      new Graphics(p,ts,ts));
  }
  @Override
  public void update() {
    if(pg.core.stateIndex==ClientGameSystem.play) {
      time++;
      // pg.system.myGroup().player.engine.setScore(1,pg.system.currentState.getScore(pg.system.myGroup().id));
      // pg.system.otherGroup().player.engine.setScore(1,pg.system.currentState.getScore(pg.system.otherGroup().id));
      pg.core.myGroup().setAllPlayerEngineScore(1,pg.core.currentState.getScore(pg.core.myGroup().id));
      pg.core.otherGroup().setAllPlayerEngineScore(1,pg.core.currentState.getScore(pg.core.otherGroup().id));
      if(time>timeLimit) {
        timeLimit=timeLimitConst;
        pg.newGame(true,false);
      }
    }
    // player_a.update(pg.system.myGroup().player);
    // player_b.update(pg.system.otherGroup().player);
    //TODO
    player_a.update(pg.core.myGroup().playerCenter.list.getFirst());
    player_b.update(pg.core.otherGroup().playerCenter.list.getFirst());
  }
  @Override
  public void display() {
    p.textScale(UtilMath.ceil(UtilMath.max(1,p.pus/3f)));
    float ts=p.textScale()*p.textSize();
    p.text(Tools.getFloatStringDepc(time,5,0)+"ms -> "+Tools.getFloatStringDepc(timeLimit,5,0)+"ms",0,0);
    p.text("real time score",0,ts);
    p.text("a - "+Tools.getFloatString(pg.core.myGroup().playerCenter.list.getFirst().engine.getScore(1)),0,ts*2);
    p.text("b - "+Tools.getFloatString(pg.core.otherGroup().playerCenter.list.getFirst().engine.getScore(1)),0,ts*3);
    p.text("final score",0,ts*4);
    p.text("a - "+Tools.getFloatString(pg.core.myGroup().playerCenter.list.getFirst().engine.getScore(0)),0,ts*5);
    p.text("b - "+Tools.getFloatString(pg.core.otherGroup().playerCenter.list.getFirst().engine.getScore(0)),0,ts*6);
  }
  @Override
  public void displayCam() {
    graphics.begin();
    p.background(255);
    pg.core.display();
    graphics.end();
    player_a.render();
    player_b.render();

    cartesianShader.bind();
    cartesianShader.setUniformf("u_dist",player_a.camX/CANVAS_SIZE,player_a.camY/CANVAS_SIZE);
    p.image(player_a.graphics.texture,-656,0,CANVAS_SIZE,CANVAS_SIZE,cartesianShader);

    p.image(graphics.texture,0,0,CANVAS_SIZE,CANVAS_SIZE);

    cartesianShader.bind();
    cartesianShader.setUniformf("u_dist",player_b.camX/CANVAS_SIZE,player_b.camY/CANVAS_SIZE);
    p.image(player_b.graphics.texture,656,0,CANVAS_SIZE,CANVAS_SIZE,cartesianShader);

    p.image(player_a.graphics.texture,-656,-656,CANVAS_SIZE,CANVAS_SIZE);
    p.image(player_b.graphics.texture,656,-656,CANVAS_SIZE,CANVAS_SIZE);
    p.clearMatrix();
  }
}
