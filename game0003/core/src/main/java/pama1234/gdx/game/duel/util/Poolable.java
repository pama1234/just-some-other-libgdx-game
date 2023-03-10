package pama1234.gdx.game.duel.util;

public interface Poolable<T extends Poolable<?>>{
  public boolean isAllocated();
  public void setAllocated(boolean indicator);
  public ObjectPool<T> getBelongingPool();
  public void setBelongingPool(ObjectPool<?> pool);
  public int getAllocationIdentifier(); // -1 : not allocated
  public void setAllocationIdentifier(int id);
  public void initialize();
}