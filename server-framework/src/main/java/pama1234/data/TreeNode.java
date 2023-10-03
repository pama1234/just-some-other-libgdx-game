package pama1234.data;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T>{
  public T data;
  public LinkedList<TreeNode<T>> children=new LinkedList<>();
  public TreeNode<T> parent;
  public int depth;
  public TreeNode(T data) {
    this.data=data;
  }
  public void addChild(TreeNode<T> in) {
    in.setParent(this);
    children.add(in);
  }
  /**
   * 含义模糊，应当修改
   * 
   * @param in
   */
  @Deprecated
  public void addChild(T in) {
    TreeNode<T> newChild=new TreeNode<>(in);
    addChild(newChild);
  }
  public void addChildren(List<TreeNode<T>> children) {
    for(TreeNode<T> e:children) e.setParent(this);
    children.addAll(children);
  }
  public LinkedList<TreeNode<T>> getChildren() {
    return children;
  }
  private void setParent(TreeNode<T> parent) {
    this.parent=parent;
    updateDepth();
  }
  public TreeNode<T> getParent() {
    return parent;
  }
  public void updateDepth() {
    depth=parent.depth+1;
    for(TreeNode<T> e:children) e.updateDepth();
  }
}