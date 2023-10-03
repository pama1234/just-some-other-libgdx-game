package pama1234.shift.rosemoe.util;

public enum CharCode{
  Tab(9),
  Space(32),
  DoubleQuote(34),
  DollarSign(36),
  SingleQuote(39),
  OpenParen(40),
  CloseParen(41),
  Dash(45),
  Period(46),
  Slash(47),
  Colon(58),
  LessThan(60),
  GreaterThan(62),
  OpenSquareBracket(91),
  Backslash(92),
  CloseSquareBracket(93),
  Underline(95),
  OpenCurlyBrace(123),
  CloseCurlyBrace(125);
  private final int code;
  CharCode(int code) {
    this.code=code;
  }
  public int getCode() {
    return code;
  }
}
