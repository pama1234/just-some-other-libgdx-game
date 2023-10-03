package pama1234.app.game.server.duel.util.actor;

import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;
import pama1234.util.wrapper.Center;

public final class ActorGroup{
  public Center<ActorGroup> enemyGroup=new Center<>();
  public Center<AbstractPlayerActor> playerCenter;
  public Center<AbstractArrowActor> arrowCenter=new Center<>();
  public int id;
  // TODO shit
  public int damageCount;
  public ActorGroup(int id) {
    this.id=id;
    playerCenter=new Center<>();
  }
  public void update() {
    playerCenter.refresh();
    for(AbstractPlayerActor i:playerCenter.list) i.update();
    arrowCenter.refresh();
    for(var i:arrowCenter.list) i.update();
  }
  public void act() {
    // playerCenter.refresh();
    for(AbstractPlayerActor i:playerCenter.list) i.act();
    // arrowCenter.refresh();
    for(var i:arrowCenter.list) i.act();
  }
  public void addPlayer(AbstractPlayerActor in) {
    //TODO
    removeAllPlayer();
    playerCenter.add.add(in);
    in.group=this;
  }
  public void removeAllPlayer() {
    playerCenter.remove.addAll(playerCenter.list);
  }
  public void removePlayer(AbstractPlayerActor in) {
    // player=in;
    playerCenter.remove.add(in);
    in.group=this;
  }
  public void addArrow(AbstractArrowActor newArrow) {
    arrowCenter.add.add(newArrow);
    newArrow.group=this;
  }
  public void displayPlayer() {
    for(var i:playerCenter.list) i.display();
  }
  public void displayArrows() {
    for(var i:arrowCenter.list) i.display();
  }
  public void setAllPlayerEngineScore(int scoreType,float score) {
    for(var i:playerCenter.list) {
      i.engine.setScore(scoreType,score);
    }
  }
}