package processing.opengl;

interface FontOutlineInterface{
  boolean isDone();
  int currentSegment(float[] coords);
  void next();
}