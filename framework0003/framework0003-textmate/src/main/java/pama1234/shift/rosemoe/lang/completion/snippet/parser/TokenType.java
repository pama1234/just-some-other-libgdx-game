package pama1234.shift.rosemoe.lang.completion.snippet.parser;

public enum TokenType{
  Dollar('$'),
  Colon(':'),
  Comma(','),
  CurlyOpen('{'),
  CurlyClose('}'),
  Backslash('\\'),
  Forwardslash('/'),
  Pipe('|'),
  Int,
  VariableName,
  Format,
  Plus('+'),
  Dash('-'),
  QuestionMark('?'),
  Backtick('`'),
  EOF;
  private final char target;
  TokenType() {
    this('\0');
  }
  TokenType(char targetChar) {
    target=targetChar;
  }
  public char getTargetCharacter() {
    return target;
  }
}
