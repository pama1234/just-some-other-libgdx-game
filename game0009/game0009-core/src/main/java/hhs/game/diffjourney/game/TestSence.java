package hhs.game.diffjourney.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.attacks.ShotAttack;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.map.Map;
import hhs.game.diffjourney.map.Region;
import hhs.game.diffjourney.screens.GameScreen;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.ui.Controller;
import hhs.gdx.hsgame.ui.DesktopController;
import hhs.gdx.hsgame.ui.DesktopMouseControler;
import hhs.gdx.hsgame.ui.NumericalFrame;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;
import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.RNG;

public class TestSence extends GameScreen{
  Protagonist c;
  Region r;
  Map m;
  // Music play = Resource.asset.get("music/play.mp3");
  RNG rseed=new RNG(MathUtils.random(21000000));
  AStarSearch map;
  Pool<Enemy1> pool;
  public TestSence() {
    // play.setLooping(true);
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    FlowingCaveGenerator dg=new FlowingCaveGenerator(500,500);
    //dg.addGrass(SectionDungeonGenerator.CORRIDOR);
    //dg.addWater(SectionDungeonGenerator.CAVE);
    addEntity(m=new Map(dg.generate(),1,camera));
    addEntity(c=new Protagonist());
    c.setMap(m.map);
    m.zindex=-1;
    c.zindex=100;
    // entities.add(r = new Region(400, new FlowingCaveGenerator(400, 400).generate(), 0, 0,
    // camera));
    c.setCurr(m);
    //map=new AStarSearch(m.map,AStarSearch.SearchType.DIJKSTRA);
    pool=Pools.get(Enemy1.class);
    for(int i=0;i<50;i++) {
      Enemy1 e=pool.obtain();
      e.set(m,c);
      randomPos(rseed,e.pos,m.map,100,100);
      e.size.set(c.size).scl(2);
      addEntity(e);
      e.zindex=1;
      e.setInfo();
      // d.addTrace(() -> e.machine.currState.toString());
    }
    ShotAttack attack=new ShotAttack(m.getCollisions());
    attack.setPosUpdate((d)->c.pos);
    attack.zindex=101;
    addEntity(attack);
    d.addTrace(()->attack.pool.peak+"");
    sortEntity();
    // entities.add(new debugQuad(r.quad));
    // 添加左下角的手柄
    if(Gdx.app.getType()==ApplicationType.Desktop) {
      var ctrl=new DesktopController(c);
      input.addProcessor(ctrl);
      addEntity(ctrl);
      var attackControler=new DesktopMouseControler(attack);
      input.addProcessor(attackControler);
      entities.add(attackControler);
    }else {
      stage.addActor(
        new Controller(
          c,
          TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
          TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
      // 添加控制攻击的控制器
      Controller control=new Controller(
        attack,
        TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
        TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色)));
      control.setX(Resource.width/3*2);
      stage.addActor(control);
    }
    setClearColor(ColorTool.东方既白);
    d.addTrace(()->"camera zoom:"+camera.zoom);
    NumericalFrame nf=new NumericalFrame();
    nf.setSize(400,200);
    nf.setPosition(0,Resource.height);
    nf.add(()->"血量："+c.data.hp,()->c.data.hp/100f,Resource.font.newFont(64,Color.BLUE));
    stage.addActor(nf);
  }
  public static void randomPos(RNG rseed,Vector2 pos,char[][] map,int w,int h) {
    int a=rseed.between(0,w),b=rseed.between(0,h);
    int i,j;
    for(i=a;i<w;i++) {
      for(j=b;j<h;j++) {
        if(map[i][j]!='#') {
          pos.set(i*50,j*50);
          return;
        }
      }
    }
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
    while(entities.size<200) {
      Enemy1 e=pool.obtain();
      e.set(m,c);
      randomPos(rseed,e.pos,m.map,100,100);
      e.size.set(c.size).scl(2);
      addEntity(e);
      e.zindex=1;
      e.setInfo();
    }
    CameraTool.smoothMove(camera,c.pos.x+c.size.x/2,c.pos.y+c.size.y/2);
    super.render(arg0);
  }
}