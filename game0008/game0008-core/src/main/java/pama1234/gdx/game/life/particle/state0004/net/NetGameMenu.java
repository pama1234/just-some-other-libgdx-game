package pama1234.gdx.game.life.particle.state0004.net;

import pama1234.Tools;
import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.NetSectorCenter0045;
import pama1234.gdx.game.life.particle.state0004.State0004Util.StateEntity0004;
import pama1234.gdx.game.life.particle.ui.generator.UiGenerator;
import pama1234.gdx.game.ui.element.TextField;

public class NetGameMenu extends StateEntity0004{
  // public TextButtonCam<?>[] menu;
  public TextField[] configsField,clientConfigs,serverConfigs;
  public TextField localPortConfig,localTokenConfig,localRoomNumberConfig;
  public TextField remoteAddressConfig,remoteTokenConfig,remoteRoomNumberConfig,remoteRoomPwdConfig;

  public float messageLifespan;
  public String message;

  public NetSectorCenter0045 sectorCenter;
  // 给文本框确定位置用的
  public final int x=180;

  public NetGameMenu(Screen0045 p) {
    super(p);
    initContainer();

    sectorCenter=new NetSectorCenter0045(p,this);
    // sectorCenter.switchSetting(sectorCenter.clientSetting);
    sectorCenter.addAll(sectorCenter.mainSetting);

    configsField=Tools.concat(serverConfigs,clientConfigs);

    for(TextField i:configsField) {
      UiGenerator.addAndroidKeyboardUtil(p,i);
    }

    // container.centerCamAddAll(menu);
    // container.addCamTextFields(configsField);
    container.refreshAll();
  }
  public void textFieldEnter(TextField in) {}
  public void textFieldExit(TextField in) {}
  @Override
  public void displayCam() {
    super.displayCam();

    if(messageLifespan>0) {
      messageLifespan-=1/150f;
      p.textColor(204);
      p.text(message,0,-90);

      p.beginBlend();
      p.fill(255,204);
      p.rect(0,-70,160*messageLifespan,2);
    }
  }
  public void message(String string) {
    message=string;
    messageLifespan=1;
  }
  @Override
  public void from(StateEntity0004 in) {
    // p.centerCamAddAll(menu);
    // p.addCamTextFields(configsField);
    p.cam.point.set(160,23,0);

    super.from(in);
  }
  @Override
  public void to(StateEntity0004 in) {
    // p.centerCamRemoveAll(menu);
    // p.removeCamTextFields(configsField);
    sectorCenter.switchSetting(null);

    p.localServerConfig.port=Integer.valueOf(localPortConfig.getText());
    p.localServerConfig.token=localTokenConfig.getText();
    p.localServerConfig.roomNumber=Integer.valueOf(localRoomNumberConfig.getText());

    p.remoteServerConfig.address=remoteAddressConfig.getText();
    p.remoteServerConfig.token=remoteTokenConfig.getText();
    p.remoteServerConfig.roomNumber=Integer.valueOf(remoteRoomNumberConfig.getText());
    p.remoteServerConfig.roomPwd=remoteRoomPwdConfig.getText();

    super.to(in);
  }
}
