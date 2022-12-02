package pama1234.game.app.server.particle.with2d;

import pama1234.game.app.server.particle.Var;
import pama1234.math.Tools;
import pama1234.math.hash.Random2f;

public class CellGroupGenerator2D{
  Random2f rng;
  float seed;
  float count;
  public CellGroupGenerator2D(float seed1,float seed2) {
    this.seed=seed1;
    rng=new Random2f(seed2);
  }
  public float random(float a,float b) {
    float out=rng.get(seed,count)*(b-a)+a;
    count+=0.02f;
    return out;
  }
  public CellGroup2D randomGenerate() {
    CellGroup2D group;
    int[] colors;
    float boxR;
    boxR=40;
    int cellType=64;
    colors=new int[cellType];
    // colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=Tools.hsbColor((float)i/colors.length*255,0xff,0xff);
    // colorMode(RGB);
    int range=(int)(boxR*16/cellType);
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    float[][][] core=new float[cellType][cellType][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        core[i][j][Var.G]=random(-Var.DIST,Var.DIST)/4;
        //        core[i][j][MultipleTypeForceUpdate.G]=random(-CellGroup.DIST,CellGroup.DIST);
        core[i][j][Var.MIN]=random(0,Var.DIST*4);
        core[i][j][Var.MAX]=random(Var.DIST*4,Var.DIST*8);
      }
    }
    group=new CellGroup2D(arraySize,boxR,type,core,colors);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
    return group;
  }
  public CellGroup2D GenerateFromMiniCore() {
    CellGroup2D group;
    int[] colors;
    float boxR;
    boxR=480;
    float[][] miniCore=new float[][] {
      {0,1,-1,-1,0,0,0,0,0,0,0,1},
      {1,0,1,-1,-1,0,0,0,0,0,0,0},
      {0,1,0,1,-1,-1,0,0,0,0,0,0},
      {0,0,1,0,1,-1,-1,0,0,0,0,0},
      {0,0,0,1,0,1,-1,-1,0,0,0,0},
      {0,0,0,0,1,0,1,-1,-1,0,0,0},
      {0,0,0,0,0,1,0,1,-1,-1,0,0},
      {0,0,0,0,0,0,1,0,1,-1,-1,0},
      {0,0,0,0,0,0,0,1,0,1,-1,-1},
      {-1,0,0,0,0,0,0,0,1,0,1,-1},
      {-1,-1,0,0,0,0,0,0,0,1,0,1},
      {1,-1,-1,0,0,0,0,0,0,0,1,0},};
    float[][][] core=new float[miniCore.length][miniCore[0].length][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        core[i][j][Var.G]=miniCore[i][j];
        core[i][j][Var.MIN]=Var.DIST*2;
        core[i][j][Var.MAX]=Var.DIST*6;
      }
    }
    int cellType=core.length;
    colors=new int[cellType];
    // colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=Tools.hsbColor((float)i/cellType*255,0xff,0xff);
    // colorMode(RGB);
    int range=1024;
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    group=new CellGroup2D(arraySize,boxR,type,core,colors);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
    return group;
  }
}