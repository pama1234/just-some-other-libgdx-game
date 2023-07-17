package android.view;

@Deprecated
public class KeyEvent{
  public static final int META_SHIFT_ON=0x1;
  public static final int META_ALT_ON=0x02;
  public static final int META_CTRL_ON=0x1000;
  public int getKeyCode() {
    return 0;
  }
  public int getAction() {
    return 0;
  }
  public int getRepeatCount() {
    return 0;
  }
  public int getMetaState() {
    return 0;
  }
  public int getModifiers() {
    return 0;
  }
  public long getDownTime() {
    return 0;
  }
  public long getEventTime() {
    return 0;
  }
}
