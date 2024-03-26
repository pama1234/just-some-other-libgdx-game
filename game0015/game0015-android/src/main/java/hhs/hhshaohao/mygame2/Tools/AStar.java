package hhs.hhshaohao.mygame2.Tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// A*算法
public class AStar{
  private int[][] map;// 地图(1可通过 0不可通过)
  private List<Node> openList;// 开启列表
  private List<Node> closeList;// 关闭列表
  private final static int COST_STRAIGHT=10;// 垂直方向或水平方向移动的路径评分
  private final static int COST_DIAGONAL=14;// 斜方向移动的路径评分
  private int row;// 行
  private int column;// 列
  private NodeFComparator mNodeFComparator;// 排序用的比较
  private Object mObjectLock;// 对像锁
  private boolean isCancel=false;// 取消操作
  private boolean isReleased=true;// 资源释放

  private static ArrayList<Node> nodeHeap;//节点堆，避免每次都new Node
  private static int nodeHeapSize=0;//节点堆长度
  private static int nodeUsed=0;//已使用的节点个数
  private static final int MALLOC_NODE_SIZE=100;//每次分配Node的个数

  public AStar(int[][] map) {
    this.map=map;
    if(map!=null&&map.length>0&&map[0].length>0) {
      this.row=map.length;
      this.column=map[0].length;
    }else {
      row=0;
      column=0;
    }
    mNodeFComparator=new NodeFComparator();
    openList=new ArrayList<>();
    closeList=new ArrayList<>();

    mObjectLock=new Object();

    isCancel=false;
    isReleased=false;
  }

  /**
   * 寻路
   * 
   * @param row1
   * @param col1
   * @param row2
   * @param col2
   * @return
   */
  public ArrayList<Node> search(int row1,int col1,int row2,int col2) {
    if(isReleased) return null;

    ArrayList<Node> result=null;
    synchronized(mObjectLock) {
      if(row1<0||row1>=row||row2<0||row2>=row||col1<0
        ||col1>=column||col2<0||col2>=column) {
        result=null;
      }else if(map[row1][col1]==0||map[row2][col2]==0) {
        result=null;
      }else {
        openList.clear();
        closeList.clear();

        Node sNode=mallocNode(row1,col1,null);
        Node eNode=mallocNode(row2,col2,null);
        openList.add(sNode);
        ArrayList<Node> roadList=search(sNode,eNode);
        if(roadList.size()==0) {
          roadList=null;
          result=null;
        }else {
          result=roadList;
        }
        nodeUsed=0;
      }
    }
    return result;
  }

  /**
   * 查找核心算法
   * 
   * @param sNode 起点
   * @param eNode 终点
   */
  private ArrayList<Node> search(Node sNode,Node eNode) {
    boolean isFind=false;
    Node node=null;
    ArrayList<Node> roadList=new ArrayList<>();
    while(!isCancel&&openList.size()>0) {
      // 取出开启列表中最低F值，即第一个存储的值的F为最低的
      node=openList.get(0);
      // 判断是否找到目标点
      if(node.getRow()==eNode.getRow()&&node.getCol()==eNode.getCol()) {
        isFind=true;
        break;
      }
      // 上
      if((node.getCol()-1)>=0) {
        checkPath(node.getRow(),node.getCol()-1,node,eNode,
          COST_STRAIGHT);
      }
      // 下
      if((node.getCol()+1)<column) {
        checkPath(node.getRow(),node.getCol()+1,node,eNode,
          COST_STRAIGHT);
      }
      // 左
      if((node.getRow()-1)>=0) {
        checkPath(node.getRow()-1,node.getCol(),node,eNode,
          COST_STRAIGHT);
      }
      // 右
      if((node.getRow()+1)<row) {
        checkPath(node.getRow()+1,node.getCol(),node,eNode,
          COST_STRAIGHT);
      }
      // 左上
      if((node.getRow()-1)>=0&&(node.getCol()-1)>=0) {
        checkPath(node.getRow()-1,node.getCol()-1,node,eNode,
          COST_DIAGONAL);
      }
      // 左下
      if((node.getRow()-1)>=0&&(node.getCol()+1)<column) {
        checkPath(node.getRow()-1,node.getCol()+1,node,eNode,
          COST_DIAGONAL);
      }
      // 右上
      if((node.getRow()+1)<row&&(node.getCol()-1)>=0) {
        checkPath(node.getRow()+1,node.getCol()-1,node,eNode,
          COST_DIAGONAL);
      }
      // 右下
      if((node.getRow()+1)<row&&(node.getCol()+1)<column) {
        checkPath(node.getRow()+1,node.getCol()+1,node,eNode,
          COST_DIAGONAL);
      }
      // 从开启列表中删除
      // 添加到关闭列表中
      closeList.add(openList.remove(0));
      // 开启列表中排序，把F值最低的放到最底端
      openList.sort(mNodeFComparator);
    }
    if(isFind) {
      getPath(roadList,node);
    }
    return roadList;
  }

  // 查询此路是否能走通
  private boolean checkPath(int row,int col,Node parentNode,Node eNode,
    int cost) {
    Node node=mallocNode(row,col,parentNode);
    // 查找地图中是否能通过
    if(map[row][col]==0) {
      closeList.add(node);
      return false;
    }
    // 查找关闭列表中是否存在
    if(isListContains(closeList,row,col)!=-1) {
      return false;
    }
    // 查找开启列表中是否存在
    int index=-1;
    if((index=isListContains(openList,row,col))!=-1) {
      // G值是否更小，即是否更新G，F值
      if((parentNode.getG()+cost)<openList.get(index).getG()) {
        node.setParentNode(parentNode);
        countG(node,eNode,cost);
        countF(node);
        openList.set(index,node);
      }
    }else {
      // 添加到开启列表中
      node.setParentNode(parentNode);
      count(node,eNode,cost);
      openList.add(node);
    }
    return true;
  }

  // 集合中是否包含某个元素(-1：没有找到，否则返回所在的索引)
  private int isListContains(List<Node> list,int row,int col) {
    int size=list.size();
    Node node;
    for(int i=0;i<size;i++) {
      node=list.get(i);
      if(node.getRow()==row&&node.getCol()==col) {
        return i;
      }
    }
    return -1;
  }

  // 从终点往返回到起点
  private void getPath(ArrayList<Node> roadList,Node node) {
    if(node.getParentNode()!=null) {
      getPath(roadList,node.getParentNode());
    }
    roadList.add(node);
  }

  // 计算G,H,F值
  private void count(Node node,Node eNode,int cost) {
    countG(node,eNode,cost);
    countH(node,eNode);
    countF(eNode);
  }

  // 计算G值
  private void countG(Node node,Node eNode,int cost) {
    if(node.getParentNode()==null) {
      node.setG(cost);
    }else {
      node.setG(node.getParentNode().getG()+cost);
    }
  }

  // 计算H值
  private void countH(Node node,Node eNode) {
    node.setH(Math.abs(node.getRow()-eNode.getRow())
      +Math.abs(node.getCol()-eNode.getCol()));
  }

  // 计算F值
  private void countF(Node node) {
    node.setF(node.getG()+node.getH());
  }

  /**
   * 取消当前寻路
   */
  public void cancel() {
    isCancel=true;
  }

  /**
   * 重置
   */
  public void reset() {
    if(isReleased) return;

    isCancel=true;
    synchronized(mObjectLock) {
      openList.clear();
      closeList.clear();
      nodeUsed=0;
    }
    isCancel=false;
  }

  /**
   * 重设地图
   * 
   * @param map
   */
  public void setMap(int[][] map) {
    synchronized(mObjectLock) {
      this.map=null;
      this.map=map;
      if(map!=null&&map.length>0&&map[0].length>0) {
        this.row=map.length;
        this.column=map[0].length;
      }else {
        row=0;
        column=0;
      }
    }
  }

  /**
   * 销毁
   */
  public void onDestroy() {
    if(isReleased) return;

    isReleased=true;
    isCancel=true;
    synchronized(mObjectLock) {

      openList.clear();
      closeList.clear();

      openList=null;
      closeList=null;
    }
    mObjectLock=null;
    map=null;
  }

  //从堆中获取节点，避免不停地创建对象
  private static Node mallocNode(int row,int col,Node parent) {
    if(nodeUsed+1>=nodeHeapSize) {
      if(nodeHeap==null) nodeHeap=new ArrayList<>();
      for(int i=MALLOC_NODE_SIZE;i>0;i--) nodeHeap.add(new Node(0,0,null));
      nodeHeapSize=nodeHeap.size();
      System.out.println("node heap increated to "+nodeHeapSize);
    }

    Node result=nodeHeap.get(nodeUsed);
    result.setRow(row);
    result.setCol(col);
    result.setParentNode(parent);
    nodeUsed++;//下一个的索引号 
    return result;
  }

  /**
   * 归还所有用过的Node
   */
  public static void rebackAllUseNode() {
    nodeUsed=0;
  }

  /**
   * 清除节点堆
   */
  public static void clearNodeHeap() {
    if(nodeHeap!=null) {
      synchronized(nodeHeap) {
        nodeHeapSize=0;
        nodeHeap.clear();
        nodeUsed=0;
      }
      nodeHeap=null;
    }

  }

  // 节点比较类
  class NodeFComparator implements Comparator<Node>{
    @Override
    public int compare(Node o1,Node o2) {
      return o1.getF()-o2.getF();
    }
  }
}
