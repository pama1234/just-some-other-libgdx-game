package pama1234.server.game.life.particle.core;

/** 矩阵形式存储的数据单元 */
public class MetaInfoUnit{
  /** 作用力 */
  public float g;
  /** 影响的最大和最小范围 */
  public float min=CellServer.dist*3,max=CellServer.dist*8;
  /** 计算分数的常量 */
  public float scoreR=CellServer.dist*5,scoreG=0;

  @Deprecated //kryo only
  public MetaInfoUnit() {}
  public MetaInfoUnit(float g) {
    this.g=g;
  }
  public MetaInfoUnit(float g,float scoreG) {
    this.g=g;
    this.scoreG=scoreG;
  }
  public MetaInfoUnit(float g,float min,float max,float scoreR,float scoreG) {
    this.g=g;
    this.min=min;
    this.max=max;
    this.scoreR=scoreR;
    this.scoreG=scoreG;
  }
}
