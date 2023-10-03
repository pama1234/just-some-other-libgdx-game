package hhs.gdx.hsgame.entities;

public class GroupEntity extends EntityCenter<BasicEntity>{
  @Override
  public void add(BasicEntity be) {
    super.add(be);
    be.pos=pos;
    be.center=center;
    be.rotate=rotate;
  }
}
