package hhs.game.diffjourney.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import hhs.game.diffjourney.attacks.ShotAttack;
import hhs.game.diffjourney.map.InfiniteMap;
import hhs.game.diffjourney.map.InfiniteRegion;
import com.badlogic.gdx.math.Vector2;
import hhs.game.diffjourney.entities.Protagonist;
import hhs.game.diffjourney.map.Region;
import hhs.gdx.hsgame.ui.DesktopMouseControler;
import squidpony.squidmath.ClassicNoise;
import squidpony.squidmath.RNG;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.input.GestureDetector;
import hhs.gdx.hsgame.tools.CameraControlGesturer;
import hhs.gdx.hsgame.ui.DesktopController;
import hhs.gdx.hsgame.ui.Controller;
import hhs.gdx.hsgame.tools.TextureTool;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.ui.NumericalFrame;
import com.badlogic.gdx.graphics.Color;
import hhs.game.diffjourney.screens.GameScreen;

public class UnLimitMapTestSence extends GameScreen{
  Protagonist c;
  Region r;
  // Music play = Resource.asset.get("music/play.mp3");
  RNG rseed=new RNG(MathUtils.random(21000000));
  InfiniteMap map;
  ClassicNoise noise;
  public UnLimitMapTestSence() {
    // play.setLooping(true);
    input.addProcessor(new GestureDetector(new CameraControlGesturer(camera)));
    c=new Protagonist();
    addEntity(map=new InfiniteMap(camera,c));
    map.addRegion();
    addEntity(c);
    // 为了方便看效果将速度调快了点
    c.setMap(((InfiniteRegion)map.regions.values().toArray()[0]).map);
    c.setCurr(map); //取消这个注释以增加碰撞效果
    ShotAttack attack=new ShotAttack(map.getCollisions());
    attack.setPosUpdate((d)->c.pos);
    addEntity(attack);
    d.addTrace(()->attack.pool.peak+"");
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
    d.addDebugText();
    d.addTrace(()->"camera zoom:"+camera.zoom);
    NumericalFrame nf=new NumericalFrame();
    nf.setSize(400,200);
    nf.setPosition(0,Resource.height);
    nf.add(()->"血量："+c.data.hp,()->c.data.hp/(float)c.data.maxHp,Resource.font.newFont(64,Color.BLUE));
    stage.addActor(nf);
    d.addTrace(()->c.pos.x+" "+c.pos.y);
    // d.setZIndex(Integer.MAX_VALUE);
    noise=new ClassicNoise();
  }
  float fract(float f) {
    if(f>1) {
      return f-MathUtils.floor(f);
    }else return f;
  }
  float hash12(Vector2 p) {
    Vector2 p2=new Vector2(1,1).scl(55.1876653f).scl(p).scl(10.1321513f);
    return fract((p2.x+p2.y)*p2.x*p2.y);
  }

  Vector2 floor(Vector2 p) {
    return new Vector2(MathUtils.floor(p.x),MathUtils.floor(p.y));
  }
  Vector2 fract(Vector2 f) {
    return new Vector2(fract(f.x),fract(f.y));
  }
  float mix(float a,float b,double c) {
    return MathUtils.lerp(a,b,(float)c);
  }
  float noise(Vector2 p) {
    Vector2 i=floor(p);
    Vector2 f=fract(p);
    Vector2 u=new Vector2(1,1).scl(f).scl(f).scl(new Vector2(3-2*f.x,3-2*f.y));
    Vector2 du=new Vector2(6,6).scl(u).scl(new Vector2(1,1).sub(u));
    float a=hash12(i);
    float b=hash12(new Vector2(1.0f,0.f).add(i));
    float c=hash12(new Vector2(.0f,1.f).add(i));
    float d=hash12(new Vector2(1.0f,1.f).add(i));

    return mix(a,b,u.x)+(c-a)*u.y*(1-u.x)+(d-b)*u.x*u.y;
  }
}