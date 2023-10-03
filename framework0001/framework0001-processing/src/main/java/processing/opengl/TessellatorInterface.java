package processing.opengl;

interface TessellatorInterface{
  void setCallback(int flag);
  void setWindingRule(int rule);
  void setProperty(int property,int value);
  void beginPolygon();
  void beginPolygon(Object data);
  void endPolygon();
  void beginContour();
  void endContour();
  void addVertex(double[] v);
  void addVertex(double[] v,int n,Object data);
}