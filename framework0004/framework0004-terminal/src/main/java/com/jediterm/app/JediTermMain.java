// JediTermMain.java
package com.jediterm.app;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.jetbrains.annotations.NotNull;

import com.jediterm.core.compatibility.Charsets;

public final class JediTermMain{
  @NotNull
  public static final JediTermMain INSTANCE;
  // @JvmStatic
  public static final void main(@NotNull String[] arg) {
    // Intrinsics.checkNotNullParameter(arg,"arg");
    INSTANCE.configureJavaUtilLogging();
    SwingUtilities.invokeLater((Runnable)null);
  }
  private final void configureJavaUtilLogging() {
    String format="[%1$tF %1$tT] [%4\\$-7s] %5$s %n";
    LogManager var10000=LogManager.getLogManager();
    String var2="java.util.logging.SimpleFormatter.format="+format;
    Charset var3=Charsets.UTF_8;
    byte[] var10003=var2.getBytes(var3);
    // Intrinsics.checkNotNullExpressionValue(var10003,"this as java.lang.String).getBytes(charset)");
    try {
      var10000.readConfiguration((InputStream)(new ByteArrayInputStream(var10003)));
    }catch(SecurityException e) {
      e.printStackTrace();
    }catch(IOException e) {
      e.printStackTrace();
    }
    Logger rootLogger=Logger.getLogger("");
    ConsoleHandler var9=new ConsoleHandler();
    // int var5=0;
    var9.setLevel(Level.ALL);
    // Unit var7=Unit.INSTANCE;
    rootLogger.addHandler((Handler)var9);
    // Intrinsics.checkNotNullExpressionValue(rootLogger,"rootLogger");
    rootLogger.setLevel(Level.INFO);
  }
  private JediTermMain() {}
  static {
    JediTermMain var0=new JediTermMain();
    INSTANCE=var0;
  }
}