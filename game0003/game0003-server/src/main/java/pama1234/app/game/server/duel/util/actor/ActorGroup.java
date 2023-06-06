package pama1234.app.game.server.duel.util.actor;

import java.util.ArrayList;

import pama1234.app.game.server.duel.util.arrow.AbstractArrowActor;

public final class ActorGroup{
  public ActorGroup enemyGroup;
  public AbstractPlayerActor player;
  public final ArrayList<AbstractArrowActor> arrowList=new ArrayList<AbstractArrowActor>();
  public final ArrayList<AbstractArrowActor> removingArrowList=new ArrayList<AbstractArrowActor>();
  public int id;
  public int damageCount;
  public ActorGroup(int id) {
    this.id=id;
  }
  public void update() {
    player.update();
    if(removingArrowList.size()>=1) {
      arrowList.removeAll(removingArrowList);
      removingArrowList.clear();
    }
    for(AbstractArrowActor eachArrow:arrowList) eachArrow.update();
  }
  public void act() {
    player.act();
    for(AbstractArrowActor eachArrow:arrowList) eachArrow.act();
  }
  public void setPlayer(AbstractPlayerActor newPlayer) {
    player=newPlayer;
    newPlayer.group=this;
  }
  public void addArrow(AbstractArrowActor newArrow) {
    arrowList.add(newArrow);
    newArrow.group=this;
  }
  public void displayPlayer() {
    player.display();
  }
  public void displayArrows() {
    for(AbstractArrowActor eachArrow:arrowList) eachArrow.display();
  }
}