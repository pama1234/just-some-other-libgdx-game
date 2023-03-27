package pama1234.util.function;

@FunctionalInterface
public interface ExecuteFunction{
  public static final ExecuteFunction doNothing=()-> {};
  public void execute();
}