package android.text;

@Deprecated
public class Editable{
  public static class Factory{
    private static Editable.Factory sInstance=new Editable.Factory();
    public static Editable.Factory getInstance() {
      return sInstance;
    }
    public Editable newEditable(CharSequence source) {
      // return new SpannableStringBuilder(source);
      return null;
    }
  }
}
