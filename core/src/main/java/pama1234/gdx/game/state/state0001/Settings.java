package pama1234.gdx.game.state.state0001;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntity0001;
import pama1234.gdx.game.ui.ButtonGenerator;
import pama1234.gdx.game.ui.util.Button;

public class Settings extends StateEntity0001{
  public Button[] buttons,buttonsCam;
  public Settings(Screen0011 p) {
    super(p);
    buttons=ButtonGenerator.genButtons_0004(p);
    // buttons=concat(ButtonGenerator.genButtons_0004(p),ButtonGenerator.genButtons_0006(p));
    buttonsCam=ButtonGenerator.genButtons_0006(p);
  }
  @Override
  public void init() {
    p.backgroundColor(0);
    p.cam.noGrab();
    for(Button e:buttons) p.centerScreen.add.add(e);
    for(Button e:buttonsCam) p.centerCam.add.add(e);
  }
  @Override
  public void dispose() {
    for(Button e:buttons) p.centerScreen.remove.add(e);
    for(Button e:buttonsCam) p.centerCam.remove.add(e);
  }
  @Override
  public void displayCam() {
    // p.text("FirstRun",0,0);
  }
  // public <T> T[] concat(T[] a,T[] b) {
  //   return Stream.concat(Arrays.stream(a),Arrays.stream(b)).toArray(size->(T[])Array.newInstance(a.getClass().getComponentType(),size));
  // }
  // public <T> T[] concat(T[] a,T[] b) {
  //   T[] both=Arrays.copyOf(a,a.length+b.length);
  //   System.arraycopy(b,0,both,a.length,b.length);
  //   return both;
  // }
}