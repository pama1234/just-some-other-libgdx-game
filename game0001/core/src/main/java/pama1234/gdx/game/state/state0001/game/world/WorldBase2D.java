package pama1234.gdx.game.state.state0001.game.world;

import pama1234.data.TreeNode;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.Game;
import pama1234.gdx.game.state.state0001.StateGenerator0001.StateEntityListener0001;
import pama1234.gdx.game.state.state0001.game.metainfo.MetaWorld;

public class WorldBase2D<M extends MetaWorld<?,?,?,?>>extends World<Screen0011,Game> implements StateEntityListener0001{
  public TreeNode<WorldBase2D<?>> node;
  //---
  public int typeId;
  public M type;
  //---
  public WorldBase2D(Screen0011 p,Game pg,int size,M type) {
    super(p,pg,size);
    node=new TreeNode<>(this);
    this.type=type;
  }
  public float random(float max) {
    return p.random(max);
  }
  public float random(float min,float max) {
    return p.random(min,max);
  }
}