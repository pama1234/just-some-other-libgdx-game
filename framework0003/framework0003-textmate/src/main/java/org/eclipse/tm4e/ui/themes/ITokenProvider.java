package org.eclipse.tm4e.ui.themes;

import org.eclipse.jdt.annotation.Nullable;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.textmate.IToken;
import pama1234.gdx.textmate.IsTextMate;
import pama1234.gdx.textmate.Token;

@IsTextMate
public interface ITokenProvider{
  IToken DEFAULT_TOKEN=new Token(null);
  IToken getToken(String type);
  @Nullable
  Color getEditorBackground();
  @Nullable
  Color getEditorForeground();
  @Nullable
  Color getEditorSelectionBackground();
  @Nullable
  Color getEditorSelectionForeground();
  @Nullable
  Color getEditorCurrentLineHighlight();
  @Nullable
  public Color getColor(final boolean isForeground,final String... styles);
}
