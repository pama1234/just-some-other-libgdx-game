package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import pama1234.gdx.util.app.ScreenCore2D;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.listener.DisplayEntityListener;

public class Screen0019 extends ScreenCore2D{
  public class TestEntity extends Entity<Screen0019> implements DisplayEntityListener{
    public TestEntity(Screen0019 p) {
      super(p);
    }
    @Override
    public void update() {
      System.out.println("Screen0019.TestEntity.update()");
    }
    @Override
    public void display() {
      System.out.println("Screen0019.TestEntity.display()");
    }
    @Override
    public void displayCam() {
      System.out.println("Screen0019.TestEntity.displayCam()");
    }
  }
  @Override
  public void setup() {
    // execute0001();
    // center.add.add(new TestEntity(this));
    // centerScreen.add.add(new TestEntity(this));
    // centerCam.add.add(new TestEntity(this));
  }
  public void execute0001() {
    FileHandle unicode=Gdx.files.local("data/all-unicode.txt");
    unicode.writeString("",false);
    StringBuilder sb=new StringBuilder();
    for(int i=0;i<65536;i++) {
      // char ic=(char)i;
      // if(Character.isDefined(ic))
      // sb.append(ic);
      sb.append(Integer.toHexString(i));
      sb.append('\n');
      if(i%4096==0) {
        unicode.writeString(sb.toString(),true);
        sb.setLength(0);
      }
    }
    Gdx.app.exit();
  }
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
}