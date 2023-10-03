package pama1234.gdx.textmate;

/**
 * A content provider mediates between the viewer's model and the viewer itself.
 *
 * @see org.eclipse.jface.viewers.ContentViewer#setContentProvider(IContentProvider)
 */
public interface IContentProvider{
  /**
   * Disposes of this content provider. This is called by the viewer when it is disposed.
   * <p>
   * The viewer should not be updated during this call, as it is in the process of being disposed.
   * </p>
   * <p>
   * The default implementation does nothing.
   * </p>
   */
  default void dispose() {}
  /**
   * Notifies this content provider that the given viewer's input has been switched to a different
   * element.
   * <p>
   * A typical use for this method is registering the content provider as a listener to changes on
   * the new input (using model-specific means), and deregistering the viewer from the old input.
   * In response to these change notifications, the content provider should update the viewer (see
   * the add, remove, update and refresh methods on the viewers).
   * </p>
   * <p>
   * The viewer should not be updated during this call, as it might be in the process of being
   * disposed.
   * </p>
   * <p>
   * The default implementation does nothing.
   * </p>
   *
   * @param viewer   the viewer
   * @param oldInput the old input element, or <code>null</code> if the viewer did not previously
   *                 have an input
   * @param newInput the new input element, or <code>null</code> if the viewer does not have an
   *                 input
   */
  default void inputChanged(Viewer viewer,Object oldInput,Object newInput) {}
}
