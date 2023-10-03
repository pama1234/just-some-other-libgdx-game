package hhs.game.diffjourney.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.map.LobbyMap;
import hhs.game.diffjourney.map.Region;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.ui.Controller;
import hhs.gdx.hsgame.ui.DesktopController;

public class Lobby extends BasicScreen{
  Protagonist c;
  /** region */
  Region r;
  public Lobby() {
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    entities.add(r=new LobbyMap(camera));
    entities.add(c=new Protagonist());
    c.setMap(r.map);
    c.setCurr(r);
    // 添加左下角的手柄
    if(Gdx.app.getType()==ApplicationType.Desktop) {
      var ctrl=new DesktopController(c);
      input.addProcessor(ctrl);
      addEntity(ctrl);
    }else {
      stage.addActor(
        new Controller(
          c,
          TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
          TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
    }
    setClearColor(ColorTool.东方既白);
    d.addTrace(()->"camera zoom:"+camera.zoom);
  }
  @Override
  public void render(float delta) {
    CameraTool.smoothMove(camera,c.pos.x+c.size.x/2,c.pos.y+c.size.y/2);
    super.render(delta);
    // TODO: Implement this method
  }
  @Override
  public void setup() {}
  @Override
  public void update() {
    // System.out.println(mouse.ox);
  }
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
