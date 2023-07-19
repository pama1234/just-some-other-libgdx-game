package hhs.game.diffjourney.game;

import com.badlogic.gdx.input.GestureDetector;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.map.LobbyMap;
import hhs.game.diffjourney.map.Region;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.ui.Controler;

public class Lobby extends BasicScreen{
  Character c;
  Region r;
  public Lobby() {
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    entities.add(r=new LobbyMap(camera));
    entities.add(c=new Character());
    c.setMap(r.map);
    c.setCurr(r);
    stage.addActor(
      new Controler(
        c,
        TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
        TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
    setClearColor(ColorTool.东方既白);
    d.addTrace(()->"camera zoom:"+camera.zoom);
  }
  @Override
  public void render(float arg0) {
    CameraTool.smoothMove(camera,c.pos.x+c.size.x/2,c.pos.y+c.size.y/2);
    super.render(arg0);
    // TODO: Implement this method
  }
}