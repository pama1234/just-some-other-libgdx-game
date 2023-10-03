package pama1234.shift.rosemoe.lang.completion.snippet.parser;

public class Token{
  public int index;
  public int length;
  public TokenType type;
  public Token(int index,int length,TokenType type) {
    this.index=index;
    this.length=length;
    this.type=type;
  }
  @Override
  public String toString() {
    return "Token{"+
      "index="+
      index+
      ", length="+
      length+
      ", type="+
      type+
      '}';
  }
}
