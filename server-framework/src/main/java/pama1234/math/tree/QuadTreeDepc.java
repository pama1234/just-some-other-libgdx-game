package pama1234.math.tree;

import java.util.ArrayList;

import pama1234.math.geometry.RectD;
import pama1234.math.geometry.RectI;

/**
 * Created by alwex on 28/05/2015.
 * <p>
 * https://github.com/alwex/QuadTree
 */
@Deprecated
public class QuadTreeDepc<T>{
  // the current nodes
  ArrayList<QuadNode<T>> nodes;
  // current rectangle zone
  private RectI zone;
  // GLOBAL CONFIGRATION
  // if this is reached,
  // the zone is subdivised
  public static int maxItemByNode=5;
  public static int maxLevel=10;
  int level;
  // the four sub regions,
  // may be null if not needed
  QuadTreeDepc<T>[] regions;
  public static final int REGION_SELF=-1;
  public static final int REGION_NW=0;
  public static final int REGION_NE=1;
  public static final int REGION_SW=2;
  public static final int REGION_SE=3;
  public QuadTreeDepc(RectI definition,int level) {
    zone=definition;
    nodes=new ArrayList<QuadNode<T>>();
    this.level=level;
  }
  protected RectI getZone() {
    return this.zone;
  }
  private int findRegion(RectI r,boolean split) {
    int region=REGION_SELF;
    if(nodes.size()>=maxItemByNode&&this.level<maxLevel) {
      // we don't want to split if we just need to retrieve
      // the region, not inserting an element
      if(regions==null&&split) {
        // then create the subregions
        this.split();
      }
      // can be null if not splitted
      if(regions!=null) {
        if(regions[REGION_NW].getZone().contains(r)) {
          region=REGION_NW;
        }else if(regions[REGION_NE].getZone().contains(r)) {
          region=REGION_NE;
        }else if(regions[REGION_SW].getZone().contains(r)) {
          region=REGION_SW;
        }else if(regions[REGION_SE].getZone().contains(r)) {
          region=REGION_SE;
        }
      }
    }
    return region;
  }
  private void split() {
    regions=new QuadTreeDepc[4];
    float newWidth=zone.w()/2;
    float newHeight=zone.h()/2;
    int newLevel=level+1;
    regions[REGION_NW]=new QuadTreeDepc<T>(new RectD(
      zone.x(),
      zone.y()+zone.h()/2,
      newWidth,
      newHeight),newLevel);
    regions[REGION_NE]=new QuadTreeDepc<T>(new RectD(
      zone.x()+zone.w()/2,
      zone.y()+zone.h()/2,
      newWidth,
      newHeight),newLevel);
    regions[REGION_SW]=new QuadTreeDepc<T>(new RectD(
      zone.x(),
      zone.y(),
      newWidth,
      newHeight),newLevel);
    regions[REGION_SE]=new QuadTreeDepc<T>(new RectD(
      zone.x()+zone.w()/2,
      zone.y(),
      newWidth,
      newHeight),newLevel);
  }
  public void insert(RectI r,T element) {
    int region=this.findRegion(r,true);
    if(region==REGION_SELF||this.level==maxLevel) {
      nodes.add(new QuadNode<T>(r,element));
      return;
    }else {
      regions[region].insert(r,element);
    }
    if(nodes.size()>=maxItemByNode&&this.level<maxLevel) {
      // redispatch the elements
      ArrayList<QuadNode<T>> tempNodes=new ArrayList<QuadNode<T>>();
      int length=nodes.size();
      for(int i=0;i<length;i++) {
        tempNodes.add(nodes.get(i));
      }
      nodes.clear();
      for(QuadNode<T> node:tempNodes) {
        this.insert(node.r,node.element);
      }
    }
  }
  public ArrayList<T> getElements(ArrayList<T> list,RectI r) {
    int region=this.findRegion(r,false);
    int length=nodes.size();
    for(int i=0;i<length;i++) {
      list.add(nodes.get(i).element);
    }
    if(region!=REGION_SELF) {
      regions[region].getElements(list,r);
    }else {
      getAllElements(list,true);
    }
    return list;
  }
  public ArrayList<T> getAllElements(ArrayList<T> list,boolean firstCall) {
    if(regions!=null) {
      regions[REGION_NW].getAllElements(list,false);
      regions[REGION_NE].getAllElements(list,false);
      regions[REGION_SW].getAllElements(list,false);
      regions[REGION_SE].getAllElements(list,false);
    }
    if(!firstCall) {
      int length=nodes.size();
      for(int i=0;i<length;i++) {
        list.add(nodes.get(i).element);
      }
    }
    return list;
  }
  public void getAllZones(ArrayList<RectI> list) {
    list.add(this.zone);
    if(regions!=null) {
      regions[REGION_NW].getAllZones(list);
      regions[REGION_NE].getAllZones(list);
      regions[REGION_SW].getAllZones(list);
      regions[REGION_SE].getAllZones(list);
    }
  }
  /**
   * Created by aguiet on 28/05/2015.
   */
  public class QuadNode<T>{
    RectI r;
    T element;
    QuadNode(RectI r,T element) {
      this.r=r;
      this.element=element;
    }
    @Override
    public String toString() {
      return r.toString();
    }
  }
}