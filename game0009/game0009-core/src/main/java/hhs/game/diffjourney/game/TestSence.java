package hhs.game.diffjourney.game;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.map.Map;
import hhs.game.diffjourney.map.Region;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.ui.Controller;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.SectionDungeonGenerator;
import squidpony.squidmath.RNG;

public class TestSence extends BasicScreen{
  Character c;
  Region r;
  Map m;
  // Music play = Resourse.asset.get("music/play.mp3");
  RNG rseed=new RNG(MathUtils.random(21000000));
  public TestSence() {
    // play.setLooping(true);
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    DungeonGenerator dg=new DungeonGenerator(500,500,rseed);
    dg.addGrass(SectionDungeonGenerator.CORRIDOR);
    dg.addWater(SectionDungeonGenerator.CAVE);
    entities.add(m=new Map(dg.generate(),5,camera));
    entities.add(c=new Character());
    c.setMap(m.map);
    // entities.add(r = new Region(400, new FlowingCaveGenerator(400, 400).generate(), 0, 0,
    // camera));
    c.setCurr(m);
    // entities.add(new debugQuad(r.quad));
    stage.addActor(
      new Controller(
        c,
        TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
        TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
    setClearColor(ColorTool.东方既白);
    d.addTrace(()->"camera zoom:"+camera.zoom);
  }
  @Override
  public void show() {
    super.show();
    // play.play();
    // TODO: Implement this method
  }
  @Override
  public void hide() {
    super.hide();
    // play.stop();
    // TODO: Implement this method
  }
  @Override
  public void render(float arg0) {
    CameraTool.smoothMove(camera,c.pos.x+c.size.x/2,c.pos.y+c.size.y/2);
    super.render(arg0);
    // TODO: Implement this method
  }
}
