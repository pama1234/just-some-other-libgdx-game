package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.duel.util.input.UiGenerator;
import pama1234.gdx.game.ui.util.TextArea;
import pama1234.gdx.game.ui.util.TextField;

public class Settings extends StateEntity0002{
  public TextArea[] textFields;
  public Settings(Duel p,int id) {
    super(p,id);
    init();
  }
  @Override
  public void init() {
    textFields=UiGenerator.genUi_0001(p);
  }
  @Override
  public void from(StateEntity0002 in) {
    for(TextField e:textFields) p.camStage.addActor(e);
  }
  @Override
  public void to(StateEntity0002 in) {
    for(TextField e:textFields) e.remove();
  }
}
