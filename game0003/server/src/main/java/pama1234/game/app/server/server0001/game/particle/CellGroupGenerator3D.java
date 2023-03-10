package pama1234.game.app.server.server0001.game.particle;

import pama1234.game.app.server.server0001.particle.Var;
import pama1234.math.Tools;
import pama1234.math.hash.Random2f;

public class CellGroupGenerator3D{
  public Random2f rng;
  public float seed;
  public float count;
  //---
  public int celltypeOut,amountOut,arraySizeOut;//TODO
  public CellGroupGenerator3D(float seed1,float seed2) {
    this.seed=seed1;
    rng=new Random2f(seed2);
  }
  public float random(float a,float b) {
    float out=rng.get(seed,count)*(b-a)+a;
    count+=0.02f;
    return out;
  }
  public CellGroup3D randomGenerate(int cellType,int rangeSectionSize) {
    CellGroup3D group;
    int[] colors;
    float boxR;
    boxR=256;
    // int cellType=64;
    colors=new int[cellType];
    // colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=Tools.hsbColor((float)i/colors.length*255,0xff,0xff);
    // colorMode(RGB);
    // int range=(int)(boxR*16/cellType);
    int range=rangeSectionSize/cellType;
    int arraySize=range*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/range;
    float[][][] core=new float[cellType][cellType][3];
    for(int i=0;i<core.length;i++) {
      for(int j=0;j<core[i].length;j++) {
        // core[i][j][Var.G]=random(-Var.DIST,Var.DIST)/16;
        // core[i][j][Var.G]=random(-Var.DIST,Var.DIST)/4;
        core[i][j][Var.G]=random(-Var.DIST,Var.DIST)/8;
        //        core[i][j][MultipleTypeForceUpdate.G]=random(-CellGroup.DIST,CellGroup.DIST);
        // core[i][j][Var.MIN]=random(0,Var.DIST*4);
        // core[i][j][Var.MAX]=random(Var.DIST*4,Var.DIST*8);
        // core[i][j][Var.MIN]=random(0,Var.DIST*40);
        // core[i][j][Var.MAX]=random(Var.DIST*40,Var.DIST*80);
        float trn=random(Var.DIST*20,Var.DIST*140);
        core[i][j][Var.MIN]=random(0,trn);
        core[i][j][Var.MAX]=random(trn,Var.DIST*160);
      }
    }
    group=new CellGroup3D(arraySize,boxR,type,core,colors);
    float randR=boxR;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      group.posZ[i]=random(-randR,randR);
      //---
      //      group.velX[i]=random(-32,32);
      //      group.velY[i]=random(-32,32);
    }
    return group;
  }
  public CellGroup3D generateFromMiniCore() {
    CellGroup3D group;
    int[] colors;
    float boxR;
    boxR=128;//1024 //256 //TODO
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
        core[i][j][Var.G]=miniCore[i][j]/4;
        // core[i][j][Var.G]=miniCore[i][j]*0.2f;
        core[i][j][Var.MIN]=Var.DIST*2;
        core[i][j][Var.MAX]=Var.DIST*6;
        // core[i][j][Var.MIN]=Var.DIST*0.5f;
        // core[i][j][Var.MAX]=Var.DIST*1.5f;
        // core[i][j][Var.MIN]=Var.DIST*24;
        // core[i][j][Var.MAX]=Var.DIST*72;
      }
    }
    int cellType=celltypeOut=core.length;
    colors=new int[cellType];
    // colorMode(HSB);
    for(int i=0;i<colors.length;i++) colors[i]=Tools.hsbColor((float)i/colors.length*255,0xff,0xff);
    // colorMode(RGB);
    int amount=amountOut=128;//1024 //128
    int arraySize=arraySizeOut=amount*cellType;
    int[] type=new int[arraySize];
    for(int i=0;i<type.length;i++) type[i]=i/amount;
    group=new CellGroup3D(arraySize,boxR,type,core,colors);
    float randR=boxR/2;
    for(int i=0;i<group.size;i++) {
      group.posX[i]=random(-randR,randR);
      group.posY[i]=random(-randR,randR);
      group.posZ[i]=random(-randR,randR);
      // if(group.posX[i]>0&&group.posZ[i]>0) System.out.println(group.posX[i]+" "+group.posY[i]+" "+group.posZ[i]);
    }
    return group;
  }
}