package pama1234.gdx.game.dimensional.tower.defense.util;

import pama1234.math.UtilMath;

public class Vec12f{
  public float[] data;
  public Vec12f() {
    data=new float[12];
  }
  public float mag() {
    return UtilMath.mag(data);
  }
  public float dist(Vec12f in) {
    return UtilMath.dist(data,in.data);
  }
  public void set(float in) {
    for(int i=0;i<data.length;i++) data[i]=in;
  }
  public void set(float[] in) {
    for(int i=0;i<data.length;i++) data[i]=in[i];
  }
  public void set(Vec12f in) {
    for(int i=0;i<data.length;i++) data[i]=in.data[i];
  }
  public void add(Vec12f in) {
    for(int i=0;i<data.length;i++) data[i]+=in.data[i];
  }
  public void sub(Vec12f in) {
    for(int i=0;i<data.length;i++) data[i]-=in.data[i];
  }
  @Deprecated
  public void mult(float in) {
    scale(in);
  }
  public void scale(float in) {
    for(int i=0;i<data.length;i++) data[i]*=in;
  }
  public void execute(Vec12f in,ExecuteVec executer) {
    for(int i=0;i<data.length;i++) data[i]=executer.execute(data[i],in.data[i]);
  }
  @FunctionalInterface
  public interface ExecuteVec{
    // public void execute(Vec12f a,Vec12f b);
    public float execute(float a,float b);
  }
}