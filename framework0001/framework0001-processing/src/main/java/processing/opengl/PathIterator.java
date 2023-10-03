package processing.opengl;

import processing.opengl.path.LinePath;

public class PathIterator{
  float[] floatCoords;
  int typeIdx;
  int pointIdx;
  int colorIdx;
  LinePath path;
  static final int[] curvecoords= {2,2,0};
  public PathIterator(LinePath p2df) {
    this.path=p2df;
    this.floatCoords=p2df.floatCoords;
    pointIdx=0;
    colorIdx=0;
  }
  public int currentSegment(float[] coords) {
    int type=path.pointTypes[typeIdx];
    int numCoords=curvecoords[type];
    if(numCoords>0) {
      System.arraycopy(floatCoords,pointIdx,coords,0,numCoords);
      int color=path.pointColors[colorIdx];
      coords[numCoords+0]=(color>>24)&0xFF;
      coords[numCoords+1]=(color>>16)&0xFF;
      coords[numCoords+2]=(color>>8)&0xFF;
      coords[numCoords+3]=(color>>0)&0xFF;
    }
    return type;
  }
  public int currentSegment(double[] coords) {
    int type=path.pointTypes[typeIdx];
    int numCoords=curvecoords[type];
    if(numCoords>0) {
      for(int i=0;i<numCoords;i++) {
        coords[i]=floatCoords[pointIdx+i];
      }
      int color=path.pointColors[colorIdx];
      coords[numCoords+0]=(color>>24)&0xFF;
      coords[numCoords+1]=(color>>16)&0xFF;
      coords[numCoords+2]=(color>>8)&0xFF;
      coords[numCoords+3]=(color>>0)&0xFF;
    }
    return type;
  }
  public int getWindingRule() {
    return path.getWindingRule();
  }
  public boolean isDone() {
    return (typeIdx>=path.numTypes);
  }
  public void next() {
    int type=path.pointTypes[typeIdx++];
    if(0<curvecoords[type]) {
      pointIdx+=curvecoords[type];
      colorIdx++;
    }
  }
}