package pama1234.gdx.game.cgj.life.particle;

public class MetaInfo{
  public float g;
  public float min=Cell.dist*3,max=Cell.dist*8;
  public float scoreR=Cell.dist*5,scoreG;
  public MetaInfo(float g) {
    this.g=g;
  }
  public MetaInfo(float g,float scoreG) {
    this.g=g;
    this.scoreG=scoreG;
  }
  public MetaInfo(float g,float min,float max,float scoreR,float scoreG) {
    this.g=g;
    this.min=min;
    this.max=max;
    this.scoreR=scoreR;
    this.scoreG=scoreG;
  }
}