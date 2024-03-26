package pama1234.gdx.game.life.particle.state0004.net.menu;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSector0045;
import pama1234.gdx.game.life.particle.state0004.net.NetGameMenu;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.math.geometry.RectF;

public class ClientSetting extends SettingSector0045<NetGameMenu>{
  public int tx=110;
  public int ty=-64;

  @Override
  public void createButton(Screen0045 p,NetGameMenu ps) {
    super.createButton(p,ps);
    buttonsCam.addAll(new TextButtonCam[] {
      new TextButtonCam<>(p,()->true,true)
        .rectAuto(0,ty+20)
        .textSupplier(self->self.text="进入大厅")
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.lobbyClient);
        }),
      new TextButtonCam<>(p,()->true,true)
        .rectAuto(0,ty+44)
        .textSupplier(self->self.text="加入房间")
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.gameClient);
        }),
    });

    ps.clientConfigs=new TextField[] {
      ps.remoteAddressConfig=new TextField("120.24.195.134",new CodeTextFieldStyle(p),
        // remoteAddressConfig=new TextField("127.0.0.1:12347",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->ty,()->320,()->18),()->1,"127.0.0.1"),
      ps.remoteTokenConfig=new TextField("Sugar Honey Ice Tea",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->ty+20,()->320,()->18),()->1,"public static void main"),

      ps.remoteRoomNumberConfig=new TextField("0001",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->ty+44,()->160,()->18),()->1,"0001"),
      ps.remoteRoomPwdConfig=new TextField("",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->ty+64,()->160,()->18),()->1,"password"),
    };
    camTextFields.addAll(ps.clientConfigs);
  }
  @Override
  public void displayCam() {
    p.beginBlend();
    p.fill(255,204);
    // p.rect(0,-4,600,2);
    p.rect(0,ty+40,600,2);

    p.textColor(204);
    p.text("远程服务器",0,ty);
    p.text("地址",tx,ty);
    p.text("令牌",tx,ty+20);

    p.text("房间号",tx,ty+44);
    p.text("密码",tx,ty+64);
  }
}
