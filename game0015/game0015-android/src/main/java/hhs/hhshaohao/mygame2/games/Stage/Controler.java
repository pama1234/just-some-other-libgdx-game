package hhs.hhshaohao.mygame2.games.Stage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import hhs.hhshaohao.mygame2.games.Actor.Attack.BasicAttack;
import hhs.hhshaohao.mygame2.games.Actor.HPViewer;
import hhs.hhshaohao.mygame2.games.Constant;
import hhs.hhshaohao.mygame2.games.MyGame;
import hhs.hhshaohao.mygame2.games.Screen.UserScreen.UserDataScreen;
import hhs.hhshaohao.mygame2.games.ViewStage;

import static hhs.hhshaohao.mygame2.games.Tool.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hhs.hhshaohao.mygame2.games.ViewActor.DisappearLabel;

public class Controler extends ViewStage{

  Touchpad pad,attack;

  WorldStage ws;
  public Table table;
  TextButton t1;
  DisappearLabel l;

  Label cut;

  Body body;

  float speed;

  BasicAttack att;

  public Controler(final WorldStage ws,float speed) {
    body=ws.pa.body;
    this.speed=speed;
    this.ws=ws;

    Touchpad.TouchpadStyle style=new Touchpad.TouchpadStyle(
      ttd(MyGame.am.get("touchpad0.png",Texture.class)),
      ttd(MyGame.am.get("touchpad1.png",Texture.class)));

    pad=new Touchpad(20,style);
    pad.setPosition(Constant.w/10,Constant.h/10);
    pad.setWidth(Constant.h/4);
    pad.setHeight(Constant.h/4);

    Touchpad.TouchpadStyle style1=new Touchpad.TouchpadStyle();
    style1.background=ttd(MyGame.am.get("touchpad0.png",Texture.class));
    style1.knob=ttd(MyGame.am.get("attack.png",Texture.class));
    style1.knob.setMinWidth(style.background.getMinWidth()/2);
    style1.knob.setMinHeight(style.background.getMinHeight()/2);
    attack=new Touchpad(20,style1);
    attack.setPosition(Constant.w-Constant.w/4,50);
    attack.sizeBy(Constant.h/8);

    addActor(pad);
    addActor(attack);

    addActor(new HPViewer(ws.pa));

    att=ws.getAttack();
    ws.addActor(att);

    t1=new TextButton("人物信息",MyGame.res.textButton);
    t1.addListener(
      new InputListener() {

        @Override
        public boolean touchDown(
          InputEvent event,float x,float y,int pointer,int button) {
          return true;
        }

        @Override
        public void touchUp(
          InputEvent event,float x,float y,int pointer,int button) {
          MyGame.res.game.setScreen(
            new UserDataScreen(ws.pa,MyGame.res.game.mainScreen));
        }
      });

    t1.setPosition(Constant.w-t1.getWidth(),Constant.h-t1.getHeight());
    addActor(t1);

    table=new Table();
    table.setFillParent(true);
    table.center().right();

    //cut = new Label("已割:" + ws.kill+"敌怪",MyGame.res.label);
    table.add(cut);

    addActor(table);

    l=new DisappearLabel("",MyGame.res.label,2);
    addActor(l);
  }

  public void pushText(String str) {
    l.setText(str);
    l.setPosition(Constant.w/2-l.getWidth()/2,5*Constant.h/6);
    l.reset();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    //cut.setText("已割:" + ws.kill+"敌怪");
    if(pad.isTouched()) {
      body.setLinearVelocity(
        speed*pad.getKnobPercentX()*delta,speed*pad.getKnobPercentY()*delta);
    }else {
      body.setLinearVelocity(0,0);
    }
    if(attack.isTouched()) {
      att.touch(attack,delta);
    }else {
      att.up(delta);
    }
  }

  @Override
  public void draw() {
    super.draw();
  }
}
