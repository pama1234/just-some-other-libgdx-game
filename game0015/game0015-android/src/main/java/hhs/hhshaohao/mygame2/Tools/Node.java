package hhs.hhshaohao.mygame2.Tools;

// 节点类
public class Node{
  private int row;// 行坐标
  private int col;// 列坐标
  private Node parentNode;// 父类节点
  private int g;// 当前点到起点的移动耗费
  private int h;// 当前点到终点的移动耗费，即曼哈顿距离|r1-r2|+|c1-c2|(忽略障碍物)
  private int f;// f=g+h

  public Node(int row,int col,Node parentNode) {
    this.row=row;
    this.col=col;
    this.parentNode=parentNode;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row=row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col=col;
  }

  public Node getParentNode() {
    return parentNode;
  }

  public void setParentNode(Node parentNode) {
    this.parentNode=parentNode;
  }

  public int getG() {
    return g;
  }

  public void setG(int g) {
    this.g=g;
  }

  public int getH() {
    return h;
  }

  public void setH(int h) {
    this.h=h;
  }

  public int getF() {
    return f;
  }

  public void setF(int f) {
    this.f=f;
  }
}
