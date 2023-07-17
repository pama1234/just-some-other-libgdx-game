package pama1234.gdx.textmate;

import pama1234.shift.eclipse.IMemento;

/**
 * Objects implementing this interface are capable of saving their state in an {@link IMemento}.
 *
 * @since 3.1
 */
public interface IPersistable{
  /**
   * Saves the state of the object in the given memento.
   *
   * @param memento the storage area for object's state
   */
  void saveState(IMemento memento);
}
