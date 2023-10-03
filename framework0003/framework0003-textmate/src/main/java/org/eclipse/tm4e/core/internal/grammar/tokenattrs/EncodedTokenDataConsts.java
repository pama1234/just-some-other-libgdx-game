package org.eclipse.tm4e.core.internal.grammar.tokenattrs;

final class EncodedTokenDataConsts{
  static final int LANGUAGEID_MASK=0b00000000_00000000_00000000_11111111;
  static final int TOKEN_TYPE_MASK=0b00000000_00000000_00000011_00000000;
  static final int BALANCED_BRACKETS_MASK=0b00000000_00000000_00000100_00000000;
  static final int FONT_STYLE_MASK=0b00000000_00000000_01111000_00000000;
  static final int FOREGROUND_MASK=0b00000000_11111111_10000000_00000000;
  static final int BACKGROUND_MASK=0b11111111_00000000_00000000_00000000;
  static final int LANGUAGEID_OFFSET=0;
  static final int TOKEN_TYPE_OFFSET=8;
  static final int BALANCED_BRACKETS_OFFSET=10;
  static final int FONT_STYLE_OFFSET=11;
  static final int FOREGROUND_OFFSET=15;
  static final int BACKGROUND_OFFSET=24;
  private EncodedTokenDataConsts() {}
}
