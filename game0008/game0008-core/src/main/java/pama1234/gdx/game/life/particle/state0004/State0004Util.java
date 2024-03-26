package pama1234.gdx.game.life.particle.state0004;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.game.Game;
import pama1234.gdx.game.life.particle.state0004.net.GameClient;
import pama1234.gdx.game.life.particle.state0004.net.GameServer;
import pama1234.gdx.game.life.particle.state0004.net.LobbyClient;
import pama1234.gdx.game.life.particle.state0004.net.LobbyServer;
import pama1234.gdx.game.life.particle.state0004.net.NetGameMenu;
import pama1234.gdx.util.listener.StateChanger;
import pama1234.gdx.util.listener.StateEntityListener;
import pama1234.gdx.util.wrapper.StateCenter;
import pama1234.gdx.util.wrapper.StateEntityBase;

import java.util.LinkedList;

public class State0004Util{
  public static void loadState0004(Screen0045 in,StateCenter0004 center) {

    // TODO 自动分配ID
    center.list.add(center.game=new Game(in));
    center.list.add(center.gameClient=new GameClient(in));
    center.list.add(center.gameServer=new GameServer(in));

    center.list.add(center.startMenu=new StartMenu(in));
    center.list.add(center.settings=new Settings(in));
    center.list.add(center.info=new Info(in));
    center.list.add(center.tutorial=new Tutorial(in));
    center.list.add(center.hoverMenu=new HoverMenu(in));

    center.list.add(center.netGameMenu=new NetGameMenu(in));
    center.list.add(center.lobbyServer=new LobbyServer(in));
    center.list.add(center.lobbyClient=new LobbyClient(in));

    for(int i=0;i<center.list.size();i++) {
      center.list.get(i).id=i;
    }

    center.defaultState=center.startMenu;
  }
  public static void loadState0004Test(Screen0045 in,DebugStateEntitys debug) {
    // debug.list.add(debug.editorTest=new EditorTest(in,-1));
  }
  public static abstract class StateEntity0004 extends StateEntityBase<Screen0045,StateEntityListener0004,StateEntity0004> implements StateEntityListener0004{
    public StateEntity0004(Screen0045 p) {
      super(p);
    }
    public StateEntity0004(Screen0045 p,int id) {
      super(p,id);
    }
  }
  public interface StateEntityListener0004 extends StateEntityListener<StateEntity0004>{
  }
  public interface StateChanger0004 extends StateChanger<StateEntity0004>{
  }
  public static class StateCenter0004 extends StateCenter<Screen0045,StateEntity0004>{
    public LinkedList<StateEntity0004> stack=new LinkedList<>();
    public StateEntity0004 defaultState;
    public boolean stackNotEmpty;

    public DebugStateEntitys debug;
    public StartMenu startMenu;
    public Settings settings;
    public Info info;
    public Tutorial tutorial;
    public HoverMenu hoverMenu;

    public NetGameMenu netGameMenu;
    public LobbyServer lobbyServer;
    public LobbyClient lobbyClient;

    public Game game;
    public GameServer gameServer;
    public GameClient gameClient;
    public StateCenter0004(Screen0045 p) {
      super(p);
    }
    public StateCenter0004(Screen0045 p,StateEntity0004 in) {
      super(p,in);
    }
    public StateCenter0004(Screen0045 p,StateEntity0004[] in) {
      super(p,in);
    }
  }
  public static class DebugStateEntitys extends StateCenter<Screen0045,StateEntity0004>{
    // public EditorTest editorTest;
    public DebugStateEntitys(Screen0045 p) {
      super(p);
    }
    public DebugStateEntitys(Screen0045 p,StateEntity0004 in) {
      super(p,in);
    }
    public DebugStateEntitys(Screen0045 p,StateEntity0004[] in) {
      super(p,in);
    }
  }
}
