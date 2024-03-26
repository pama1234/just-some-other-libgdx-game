package pama1234.gdx.game.life.particle.state0004;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSector0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.state0004.net.NetGameMenu;
import pama1234.gdx.game.life.particle.state0004.net.menu.ClientSetting;
import pama1234.gdx.game.life.particle.state0004.net.menu.NetMainSetting;
import pama1234.gdx.game.life.particle.state0004.net.menu.PlayerSetting;
import pama1234.gdx.game.life.particle.state0004.net.menu.ServerSetting;
import pama1234.gdx.game.life.particle.state0004.settings.menu.GameSetting;
import pama1234.gdx.game.life.particle.state0004.settings.menu.MainSetting;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.gdx.util.listener.EntityNeoListener;
import pama1234.gdx.util.ui.menu.SectorCenter;
import pama1234.gdx.util.ui.menu.SettingSector;
import pama1234.gdx.util.wrapper.EntityCenter;

public class SectorCenter0045<T extends StateEntity0004>extends SectorCenter<Screen0045,T,SettingSector0045<T>>{
  public static class NetSectorCenter0045 extends SectorCenter0045<NetGameMenu>{
    public NetMainSetting mainSetting;

    public PlayerSetting playerSetting;

    public ClientSetting clientSetting;
    public ServerSetting serverSetting;

    public NetSectorCenter0045(Screen0045 p,NetGameMenu ps) {
      super(p,ps);
      mainSetting=new NetMainSetting();
      mainSetting.createButton(p,ps);

      list.add(playerSetting=new PlayerSetting());
      list.add(clientSetting=new ClientSetting());
      list.add(serverSetting=new ServerSetting());

      init();

      for(int i=0;i<list.size();i++) {
        list.get(i).pb=mainSetting.buttonsCam.get(i);
      }
    }
  }
  public static class SettingSectorCenter0045 extends SectorCenter0045<Settings>{
    public MainSetting mainSetting;

    public GameSetting gameSetting;

    public SettingSectorCenter0045(Screen0045 p,Settings ps) {
      super(p,ps);
      mainSetting=new MainSetting();
      mainSetting.createButton(p,ps);

      list.add(gameSetting=new GameSetting());

      init();

      for(int i=0;i<list.size();i++) {
        list.get(i).pb=mainSetting.buttonsCam.get(i);
      }
    }
  }

  public SectorCenter0045(Screen0045 p,T ps) {
    super(p,ps);
  }

  @Override
  public void addAll(SettingSector0045<T> in) {
    super.addAll(in);
    ps.container.centerCamAddAll(in.centerCam);
    p.addCamTextFields(in.camTextFields);
    ps.container.centerNeoAddAll(in);
  }

  @Override
  public void removeAll(SettingSector0045<T> in) {
    super.removeAll(in);
    ps.container.centerCamRemoveAll(in.centerCam);
    p.removeCamTextFields(in.camTextFields);
    ps.container.centerNeoRemoveAll(in);
  }

  public abstract static class SettingSector0045<T extends StateEntity0004>extends SettingSector<Screen0045,T> implements EntityNeoListener{
    public Screen0045 p;

    public EntityCenter<Screen0045,EntityListener> centerCam=new EntityCenter<>(p);
    @Override
    public void createButton(Screen0045 p,T ps) {
      this.p=p;
    }
    public void load() {}
    public void save() {}
  }
}
