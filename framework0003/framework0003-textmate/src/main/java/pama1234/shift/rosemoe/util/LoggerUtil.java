package pama1234.shift.rosemoe.util;

import java.util.Map;
import java.util.WeakHashMap;

import com.badlogic.gdx.utils.Logger;

public class LoggerUtil{
  private static final Map<String,LoggerUtil> map=new WeakHashMap<>();
  private final String name;
  private Logger log;
  private static Logger slog=new Logger("LoggerUtil");
  private LoggerUtil(String name) {
    this.name=name;
    log=new Logger(name);
  }
  public synchronized static LoggerUtil instance(String name) {
    var logger=map.get(name);
    if(logger==null) {
      logger=new LoggerUtil(name);
      map.put(name,logger);
    }
    return logger;
  }
  public void d(String msg) {
    log.debug(msg);
  }
  public void d(String msg,Object... format) {
    log.debug(String.format(msg,format));
  }
  public void i(String msg) {
    log.info(msg);
  }
  public void i(String msg,Object... format) {
    log.info(String.format(msg,format));
  }
  public void v(String msg) {
    log.info(msg);
  }
  public void v(String msg,Object... format) {
    log.info(String.format(msg,format));
  }
  public void w(String msg) {
    log.error(msg);
  }
  public void w(String msg,Object... format) {
    log.error(String.format(msg,format));
  }
  public void w(String msg,Throwable e) {
    log.error(msg,e);
  }
  public void w(String msg,Throwable e,Object... format) {
    log.error(String.format(msg,format),e);
  }
  public void e(String msg) {
    log.error(msg);
  }
  public void e(String msg,Object... format) {
    log.error(String.format(msg,format));
  }
  public void e(String msg,Throwable e) {
    log.error(msg,e);
  }
  public void e(String msg,Throwable e,Object... format) {
    log.error(String.format(msg,format),e);
  }
  public static void ds(String msg) {
    slog.debug(msg);
  }
  public static void ds(String msg,Object... format) {
    slog.debug(String.format(msg,format));
  }
  public static void is(String msg) {
    slog.info(msg);
  }
  public static void is(String msg,Object... format) {
    slog.info(String.format(msg,format));
  }
  public static void vs(String msg) {
    slog.info(msg);
  }
  public static void vs(String msg,Object... format) {
    slog.info(String.format(msg,format));
  }
  public static void ws(String msg) {
    slog.error(msg);
  }
  public static void ws(String msg,Object... format) {
    slog.error(String.format(msg,format));
  }
  public static void ws(String msg,Throwable e) {
    slog.error(msg,e);
  }
  public static void ws(String msg,Throwable e,Object... format) {
    slog.error(String.format(msg,format),e);
  }
  public static void es(String msg) {
    slog.error(msg);
  }
  public static void es(String msg,Object... format) {
    slog.error(String.format(msg,format));
  }
  public static void es(String msg,Throwable e) {
    slog.error(msg,e);
  }
  public static void es(String msg,Throwable e,Object... format) {
    slog.error(String.format(msg,format),e);
  }
}
