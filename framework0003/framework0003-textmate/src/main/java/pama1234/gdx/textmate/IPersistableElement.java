package pama1234.gdx.textmate;

// import org.eclipse.ui.IElementFactory;
// import org.eclipse.ui.IPersistable;
/**
 * Interface for asking an object to store its state in a memento.
 * <p>
 * This interface is typically included in interfaces where persistance is required.
 * </p>
 * <p>
 * When the workbench is shutdown objects which implement this interface will be persisted. At
 * this time the <code>getFactoryId</code> method is invoked to discover the id of the element
 * factory that will be used to re-create the object from a memento. Then the
 * <code>saveState</code> method is invoked to store the element data into a newly created
 * memento. The resulting mementos are collected up and written out to a single file.
 * </p>
 * <p>
 * During workbench startup these mementos are read from the file. The factory Id for each is
 * retrieved and mapped to an <code>IElementFactory</code> which has been registered in the
 * element factory extension point. If a factory exists for the Id it will be engaged to
 * re-create the original object.
 * </p>
 *
 * @see org.eclipse.core.runtime.IAdaptable
 * @see pama1234.shift.eclipse.ui.IMemento
 * @see org.eclipse.ui.IElementFactory
 */
public interface IPersistableElement extends IPersistable{
  /**
   * Returns the id of the element factory which should be used to re-create this object.
   * <p>
   * Factory ids are declared in extensions to the standard extension point
   * <code>"org.eclipse.ui.elementFactories"</code>.
   * </p>
   *
   * @return the element factory id
   * @see IElementFactory
   */
  String getFactoryId();
}
