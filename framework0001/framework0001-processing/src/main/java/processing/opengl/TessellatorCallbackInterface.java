package processing.opengl;

interface TessellatorCallbackInterface{
  void begin(int type);
  void end();
  void vertex(Object data);
  void combine(double[] coords,Object[] data,
    float[] weight,Object[] outData);
  void error(int errnum);
}