package pama1234.gdx.game.love.state0055;

import pama1234.gdx.game.app.app0002.Screen0055;
import pama1234.gdx.game.love.state0055.State0055Util.StateEntity0055;
import pama1234.gdx.game.util.ui.ColorUtil;
import pama1234.math.physics.ReversedPathPoint;
import pama1234.util.wrapper.Center;

public class BulletTest extends StateEntity0055{
  //  ReversedPathPoint test=new ReversedPathPoint(0,0,500,500);

  Center<ReversedPathPoint> testC=new Center<>();

  public BulletTest(Screen0055 p) {
    super(p);
  }

  @Override
  public void from(StateEntity0055 in) {

    p.doFill();
    p.noStroke();

    p.fill(ColorUtil.clas);
    p.stroke(ColorUtil.keyword);
    //    p.fill(Color.BLUE);

    //    p.cam3d.point.des.set(0,0,-1024);

    //    for(int i=0;i<50;i++) {
    //      ReversedPathPoint e=new ReversedPathPoint(0,0,p.random(-500,500),p.random(-500,500));
    //      //      p.println(e.des);
    //      testC.add.add(e);
    //    }
  }

  @Override
  public void update() {
    if(p.frameCount%10==0) if(testC.list.size()<50) {
      ReversedPathPoint e=new ReversedPathPoint(0,0,0,0);
      resetPoint(e);
      //      ReversedPathPoint e=new ReversedPathPoint(0,0,p.random(-500,500),p.random(-500,500));
      //      p.println(e.des);
      testC.add.add(e);
    }

    //    test.update();
    testC.refresh();
    //    if(p.frameCount%3==0)
    testC.list.forEach(e->e.update(0.1f));

    testC.list.forEach(test-> {
      if(test.stop) {
        //        testC.remove.add(test);
        resetPoint(test);
      }
    });
  }

  public void resetPoint(ReversedPathPoint test) {
    //    test.set(0,0,p.random(-500,500),p.random(-500,500));
    test.set(p.random(-20,20),p.random(-20,20),p.random(-500,500),p.random(-500,500));
    test.reset();
  }

  @Override
  public void displayCam() {
    testC.list.forEach(test-> {

      p.doStroke();
      p.strokeWeight(2);
      //      p.stroke(ColorUtil.keyword);
      p.sline(test.pos.x,test.pos.y,test.px,test.py);

      p.noStroke();

      p.fill(ColorUtil.clas);
//      p.scircle(test.pos.x,test.pos.y,1);
      p.scircle(test.px,test.py,1);

      p.fill(ColorUtil.data);
      p.scircle(test.des.x,test.des.y,1);

      //      p.imageBatch.begin();
      //      p.shapeDrawer.setColor(ColorUtil.keyword);
      //      p.shapeDrawer.setDefaultLineWidth(p.strokeWeight);
      //      p.shapeDrawer.line(test.pos.x,test.pos.y,test.px,test.py);
      //      p.imageBatch.end();
    });
    //
    //    p.textScale(1/2f);
    //    p.textColor(204);
    //    p.text(" x: "+Tools.getFloatString(p.mouse.x,5,2)+" y: "+Tools.getFloatString(p.mouse.y,5,2),p.mouse.x,p.mouse.y);
    //    p.text("ox: "+Tools.getIntString(p.mouse.ox,5)+"   oy: "+Tools.getIntString(p.mouse.oy,5),p.mouse.x,p.mouse.y+8);
    //    p.textScale(1);

  }

  @Override
  public void display() {

    //    p.text(test.pos.x+" "+test.pos.y);

  }
}
