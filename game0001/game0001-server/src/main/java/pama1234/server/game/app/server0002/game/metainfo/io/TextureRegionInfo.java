package pama1234.server.game.app.server0002.game.metainfo.io;

/**
 * 方块和物品等“游戏元素的定义”的贴图将会单独存储在png文件中，因此游戏元素定义中如果需要引用贴图中的某一个方框区域，就使用这个类
 */
public class TextureRegionInfo{

  /** png文件的名字 */
  public String assetId;
  /** 方框区域 */
  public int x,y,w,h;

  /** for snakeYAML */
  public TextureRegionInfo() {}
  public TextureRegionInfo(String assetId,int x,int y,int w,int h) {
    this.assetId=assetId;
    this.x=x;
    this.y=y;
    this.w=w;
    this.h=h;
  }

}
