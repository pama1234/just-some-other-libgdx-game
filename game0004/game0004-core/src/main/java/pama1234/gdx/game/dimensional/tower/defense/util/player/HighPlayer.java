package pama1234.gdx.game.dimensional.tower.defense.util.player;

import com.badlogic.gdx.graphics.g3d.decals.Decal;

import pama1234.gdx.game.dimensional.tower.defense.DemonDefense;
import pama1234.gdx.game.dimensional.tower.defense.util.entity.HighLife.CircleLife;
import pama1234.gdx.game.dimensional.tower.defense.util.math.physics.HighMassPoint;

public class HighPlayer extends CircleLife{
  public HighPlayer(DemonDefense p,HighMassPoint point,Decal decal) {
    super(p,point,decal);
  }
}