package pama1234.gdx.textmate;

/**
 * An interface to content providers for structured viewers.
 *
 * @see StructuredViewer
 */
public interface IStructuredContentProvider extends IContentProvider{
  /**
   * Returns the elements to display in the viewer when its input is set to the given element.
   * These elements can be presented as rows in a table, items in a list, etc. The result is not
   * modified by the viewer.
   *
   * @param inputElement the input element
   * @return the array of elements to display in the viewer
   */
  public Object[] getElements(Object inputElement);
}
