package pama1234.server.game.life.particle.net.message;

import pama1234.Tools;
import pama1234.server.game.life.particle.core.MetaInfo;
import pama1234.server.game.life.particle.core.MetaInfoUnit;

/**
 * 房间的基本信息（也就是不会发生瞬时变化的信息）
 */
public class RoomInfo{
  /** 区域大小 */
  public int areaWidth,areaHeight;
  /** 多少种粒子 */
  public int coreSize;

  public MetaInfoUnit[][] rules;
  public MetaInfo[] info;

  public RoomInfo initDefault() {
    // areaWidth=1600;
    // areaHeight=360;
    areaWidth=480;
    areaHeight=480;
    coreSize=12;
    info=new MetaInfo[coreSize];
    for(int i=0;i<info.length;i++) info[i]=new MetaInfo(Tools.hsbColorInt(255f/coreSize*i,255,255));
    rules=new MetaInfoUnit[coreSize][coreSize];
    for(int i=0;i<rules.length;i++) {
      for(int j=0;j<rules[i].length;j++) rules[i][j]=new MetaInfoUnit(0);
      MetaInfoUnit[] fs=rules[i];
      fs[Tools.moveInRange(i-1,0,fs.length)].g=1;
      fs[Tools.moveInRange(i+1,0,fs.length)].g=1;
      fs[Tools.moveInRange(i+2,0,fs.length)].g=-1;
      fs[Tools.moveInRange(i+3,0,fs.length)].g=-1;
    }
    for(MetaInfoUnit[] fs:rules) {
      for(int i=0;i<fs.length;i++) {
        fs[i].g*=0.5f;
      }
    }
    return this;
  }

  public void set(int areaSize,int coreSize,float[][] rulesArray,int[] colorArray) {
    this.areaWidth=areaSize;
    this.coreSize=coreSize;
    rules=new MetaInfoUnit[coreSize][coreSize];
    for(int i=0;i<rules.length;i++) {
      for(int j=0;j<rules[i].length;j++) rules[i][j]=new MetaInfoUnit(rulesArray[i][j]);
    }
    info=new MetaInfo[coreSize];
    for(int i=0;i<info.length;i++) info[i]=new MetaInfo(colorArray[i]);
  }
}