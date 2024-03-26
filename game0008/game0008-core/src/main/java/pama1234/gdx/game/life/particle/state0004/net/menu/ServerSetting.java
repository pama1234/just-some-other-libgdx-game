package pama1234.gdx.game.life.particle.state0004.net.menu;

import pama1234.gdx.game.life.particle.app.Screen0045;
import pama1234.gdx.game.life.particle.state0004.SectorCenter0045.SettingSector0045;
import pama1234.gdx.game.life.particle.state0004.net.NetGameMenu;
import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.element.TextButtonCam;
import pama1234.gdx.game.ui.element.TextField;
import pama1234.math.geometry.RectF;

public class ServerSetting extends SettingSector0045<NetGameMenu>{
  public int tx=110;
  public int ty=-64;

  @Override
  public void createButton(Screen0045 p,NetGameMenu ps) {
    super.createButton(p,ps);
    buttonsCam.addAll(new TextButtonCam[] {
      new TextButtonCam<>(p,()->true,true)
        .rectAuto(0,ty+20)
        .textSupplier(self->self.text="创建服务端")
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.lobbyServer);
        }),
      new TextButtonCam<>(p,()->true,true)
        .rectAuto(0,ty+40)
        .textSupplier(self->self.text="创建房间")
        .allTextButtonEvent(self-> {},self-> {},self-> {
          p.stateForward(p.stateCenter.gameServer);
        }),
    });

    ps.serverConfigs=new TextField[] {
      ps.localPortConfig=new TextField("12347",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->-64,()->320,()->18),()->1,"port"),
      ps.localTokenConfig=new TextField("Sugar Honey Ice Tea",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->-44,()->320,()->18),()->1,"public static void main"),
      ps.localRoomNumberConfig=new TextField("0001",new CodeTextFieldStyle(p),
        new RectF(()->ps.x,()->-24,()->320,()->18),()->1,"0001"),
    };
    camTextFields.addAll(ps.serverConfigs);
  }

  @Override
  public void displayCam() {

    p.beginBlend();

    p.textColor(204);
    p.text("本地服务器",0,ty);
    p.text("端口号",tx,ty);
    p.text("令牌",tx,ty+20);
    p.text("房间号",tx,ty+40);
  }
}
