package pama1234.gdx.game.state.state0002;

import pama1234.gdx.game.duel.Duel;
import pama1234.gdx.game.duel.State0002Util.StateEntity0002;
import pama1234.gdx.game.ui.util.TextField;

public class Settings extends StateEntity0002{
  public TextField[] screenTextFields;
  public Settings(Duel p,int id) {
    super(p,id);
  }
}