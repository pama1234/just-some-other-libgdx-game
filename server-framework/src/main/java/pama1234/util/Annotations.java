package pama1234.util;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.MODULE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations{
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ClientOnly{}
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ServerOnly{}
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface ServerAndClient{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface ApiLevel{
    int level();
  }
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface DesktopOnly{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface MobileOnly{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface RedundantCache{}
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(value= {CONSTRUCTOR,FIELD,LOCAL_VARIABLE,METHOD,PACKAGE,MODULE,PARAMETER,TYPE})
  public static @interface ScreenDescription{
    String value();
  }
}
