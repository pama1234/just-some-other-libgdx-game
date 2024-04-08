package hhs.game.diffjourney.test;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.ui.Controller;
import hhs.gdx.hsgame.ui.DesktopController;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import squidpony.squidmath.RNG;

public class TestScreen extends BasicScreen{
  RNG rSeed;
  Character1 c;
  MyRandomMap tm;
  public TestScreen() {
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    rSeed=new RNG();
    addEntity(c=new Character1());
    if(Gdx.app.getType()==ApplicationType.Desktop) {
      var ctrl=new DesktopController(c);
      input.addProcessor(ctrl);
      entities.add(ctrl);
    }else {
      stage.addActor(
        new Controller(c,TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
          TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
    }
    addEntity(tm=new MyRandomMap(camera));
  }
  @Override
  public void render(float arg0) {
    CameraTool.smoothMove(camera,c.pos.x+c.size.x/2,c.pos.y+c.size.y/2);
    super.render(arg0);
    // TODO: Implement this method
  }
  @Override
  public void setup() {}
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}
