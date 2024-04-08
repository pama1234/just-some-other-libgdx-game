package pama1234.game.app.net;

import pama1234.Tools;

public class RoomInfo{
  public int areaSize;
  public int coreSize;
  public float[][] rules;
  public int[] colorArray;

  public RoomInfo initDefault() {
    areaSize=240;
    coreSize=12;
    colorArray=new int[coreSize];
    for(int i=0;i<colorArray.length;i++) colorArray[i]=Tools.hsbColorInt(255f/coreSize*i,255,255);
    rules=new float[coreSize][coreSize];
    for(int i=0;i<rules.length;i++) {
      // for(int j=0;j<rules[i].length;j++);
      float[] fs=rules[i];
      fs[Tools.moveInRange(i-1,0,fs.length)]=1;
      fs[Tools.moveInRange(i+1,0,fs.length)]=1;
      fs[Tools.moveInRange(i+2,0,fs.length)]=-1;
      fs[Tools.moveInRange(i+3,0,fs.length)]=-1;
    }
    return this;
  }
}