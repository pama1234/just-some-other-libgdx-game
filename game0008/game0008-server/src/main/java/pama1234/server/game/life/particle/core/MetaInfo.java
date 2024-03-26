package pama1234.server.game.life.particle.core;

/** 列表形式存储的数据 */
public class MetaInfo{
  public int color=hashCode()|0xff000000;
  public int amount=1<<6;
  @Deprecated //kryo only
  public MetaInfo() {}
  public MetaInfo(int color) {
    this.color=color;
  }
}
