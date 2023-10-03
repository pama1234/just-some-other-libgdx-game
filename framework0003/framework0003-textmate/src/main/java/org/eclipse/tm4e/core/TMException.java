package org.eclipse.tm4e.core;

public class TMException extends RuntimeException{
  private static final long serialVersionUID=1L;
  public TMException(final String message) {
    super(message);
  }
  public TMException(final String message,final Throwable cause) {
    super(message,cause);
  }
}
