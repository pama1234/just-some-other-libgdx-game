package pama1234.util.neat.raimannma.architecture;

import java.util.Set;

public class ConnectionData{
  public static Set<ConnectionData> pool;
  public float weight;//      0
  public float gain;//        1
  public int toIndex;//       2
  public int gateNodeIndex;// 3
  public ConnectionData(float weight,float gain,int toIndex,int gateNodeIndex) {
    this.weight=weight;
    this.gain=gain;
    this.toIndex=toIndex;
    this.gateNodeIndex=gateNodeIndex;
  }
}