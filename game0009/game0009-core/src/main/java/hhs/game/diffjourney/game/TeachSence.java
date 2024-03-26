package hhs.game.diffjourney.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import hhs.game.diffjourney.attacks.ShotAttack;
import hhs.game.diffjourney.entities.Enemy1;
import hhs.game.diffjourney.entities.Mushroom;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.entities.enemies.MultipleEnemyGenerator;
import hhs.game.diffjourney.map.Map;
import hhs.game.diffjourney.screens.GameScreen;
import hhs.game.diffjourney.ui.MyDialogBox;
import hhs.game.diffjourney.ui.UiList;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.tools.CameraTool;
import hhs.gdx.hsgame.tools.StringTool;
import hhs.gdx.hsgame.ui.DesktopController;
import hhs.gdx.hsgame.ui.Controller;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.DesktopMouseControler;
import hhs.gdx.hsgame.ui.NumericalFrame;
import hhs.gdx.hsgame.util.ToDoList;
import squidpony.squidgrid.mapping.FlowingCaveGenerator;
import squidpony.squidmath.RNG;

public class TeachSence extends GameScreen{
  Protagonist pro;
  Enemy1 first;
  Map map;
  MyDialogBox move=null;
  ToDoList todo;
  RNG rseed=new RNG(1);
  Pool<Mushroom> pool=Pools.get(Mushroom.class,5);
  public TeachSence() {
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    pro=new Protagonist() {
      float st=0;
      @Override
      public void control(float delta,Vector2 knob) {
        super.control(delta,knob);
        if((st+=delta)<3) return;
        if(move==null) {
          move=new MyDialogBox(PixmapBuilder.getRectangle(200,100,ColorTool.松绿));
          move.setSequence(
            StringTool.linkedList(
              new String[] {"很好，看来你已经懂得如何移动人物了","接下来让我们加上一些敌人","试着使用右摇杆或鼠标指向要攻击的方向吧"}));
          move.setFont(UiList.font);
          move.setScale(5);
          move.setBounds(0,0,Resource.width,Resource.height/3);
          stage.addActor(move);
        }
        // TODO: Implement this method
      }
    };
    addEntity(pro);
    FlowingCaveGenerator gen=new FlowingCaveGenerator(50,200);
    addEntity(map=new Map(gen.generate(),1,camera));
    pro.setMap(map.map);
    pro.setCurr(map);
    if(Gdx.app.getType()==ApplicationType.Desktop) {
      var ctrl=new DesktopController(pro);
      input.addProcessor(ctrl);
      addEntity(ctrl);
    }else {
      stage.addActor(
        new Controller(
          pro,
          TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
          TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色))));
    }
    todo=new ToDoList();
    todo.addToDo(()->pool.peak!=0&&pool.getFree()>4,
      ()-> {
        MyDialogBox end=new MyDialogBox(PixmapBuilder.getRectangle(200,100,ColorTool.松绿));
        end.setSequence(
          StringTool.linkedList(
            new String[] {"好了，你已经学会这个游戏的基础操作了","接下来就由你自由发挥吧"}));
        end.setFont(UiList.font);
        end.setScale(5);
        end.setBounds(0,0,Resource.width,Resource.height/3);
        stage.addActor(end);
      });
    todo.addToDo(
      ()->move!=null&&move.isRemoved,
      ()-> {
        TestSence.randomPos(rseed,pro.pos,map.map,50,25);
        map.getCollisions().update(pro.info,pro.pos.x,pro.pos.y);
        ShotAttack attack=new ShotAttack(map.getCollisions());
        attack.setPosUpdate((d)->pro.pos);
        attack.zindex=101;
        addEntity(attack);
        if(Gdx.app.getType()==ApplicationType.Desktop) {
          var attackControler=new DesktopMouseControler(attack);
          input.addProcessor(attackControler);
          entities.add(attackControler);
        }else {
          Controller control=new Controller(
            attack,
            TextureTool.ttr(PixmapBuilder.getRectangle(200,200,ColorTool.宝石蓝)),
            TextureTool.ttr(PixmapBuilder.getCircle(50,ColorTool.奶酪色)));
          control.setX(Resource.width/3*2);
          stage.addActor(control);
        }
        d.addTrace(()->pool.getFree()+" "+pool.peak);
        rseed=new RNG(1);
        for(int i=0;i<5;i++) {
          Enemy1 e=MultipleEnemyGenerator.getEnemy1();
          e.set(map,pro);
          TestSence.randomPos(rseed,e.pos,map.map,50,25);
          e.size.set(pro.size).scl(2);
          addEntity(e);
          e.zindex=1;
          e.setInfo();
          // d.addTrace(() -> e.machine.currState.toString());
        }
      });
    addEntity(todo);
    NumericalFrame nf=new NumericalFrame();
    nf.setSize(400,200);
    nf.setPosition(0,Resource.height);
    nf.add(()->"血量："+pro.data.hp,()->pro.data.hp/100f,Resource.font.newFont(64,Color.BLUE));
    stage.addActor(nf);
    MyDialogBox dbox=new MyDialogBox(PixmapBuilder.getRectangle(200,100,ColorTool.松绿));
    dbox.setSequence(
      StringTool.linkedList(
        new String[] {"您好，看来你是第一次来到这里","您可能需要一些帮助","首先，你可以使用摇杆操作人物或者使用wasd和上下左右方向键操作人物","先来试试吧"}));
    dbox.setFont(UiList.font);
    dbox.setScale(5);
    dbox.setBounds(0,0,Resource.width,Resource.height/3);
    stage.addActor(dbox);
  }
  @Override
  public void render(float delta) {
    CameraTool.smoothMove(camera,pro.pos.x+pro.size.x/2,pro.pos.y+pro.size.y/2);
    super.render(delta);
  }
}
