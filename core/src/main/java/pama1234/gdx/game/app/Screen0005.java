package pama1234.gdx.game.app;

// import static pama1234.math.UtilMath.sq;
import pama1234.gdx.game.ui.Button;
import pama1234.gdx.game.ui.TextButtonGenerator;
import pama1234.gdx.util.listener.EntityListener;

public class Screen0005 extends ScreenCore3D{
  @Override
  public void setup() {
    // System.out.println(bu);
    noStroke();
    buttons=TextButtonGenerator.genButtons_0002(this);
    for(Button e:buttons) centerScreen.add.add(e);
    centerScreen.add.add(new EntityListener() {
      @Override
      public void display() {
        fill(0);
        // rect(width/8*5,0,width/4,frameCount<height?sq(frameCount):height);
        rect(width/8*5,0,width/4,height);
      }
    });
    // System.out.println(Hiero.class);
  }
  @Override
  public void update() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    // super.frameResized();
  }
}
