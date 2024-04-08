package hhs.hhshaohao.mygame2.games.State;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

import hhs.hhshaohao.mygame2.games.Actor.BasicRoleActor;

public enum BasicRoleState implements State<BasicRoleActor>{
  WALK() {

    @Override
    public void enter(BasicRoleActor p1) {}

    @Override
    public void update(BasicRoleActor p1) {}

    @Override
    public void exit(BasicRoleActor p1) {}

    @Override
    public boolean onMessage(BasicRoleActor p1,Telegram p2) {
      return false;
    }
  },
  FIND() {

    @Override
    public void enter(BasicRoleActor p1) {}

    @Override
    public void update(BasicRoleActor p1) {}

    @Override
    public void exit(BasicRoleActor p1) {}

    @Override
    public boolean onMessage(BasicRoleActor p1,Telegram p2) {
      return false;
    }
  }
}
