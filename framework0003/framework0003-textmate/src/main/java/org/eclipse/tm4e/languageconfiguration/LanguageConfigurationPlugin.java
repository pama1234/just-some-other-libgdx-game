package org.eclipse.tm4e.languageconfiguration;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.Nullable;
// import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import pama1234.gdx.textmate.annotation.DeprecatedJface;

@DeprecatedJface
public final class LanguageConfigurationPlugin{
  // extends AbstractUIPlugin{
  public static final String PLUGIN_ID="org.eclipse.tm4e.languageconfiguration"; //$NON-NLS-1$
  @Nullable
  private static volatile LanguageConfigurationPlugin plugin;
  @Nullable
  public static LanguageConfigurationPlugin getDefault() {
    return plugin;
  }
  public static void log(final IStatus status) {
    final var p=plugin;
    if(p!=null) {
      // p.getLog().log(status);
    }else {
      System.out.println(status);
    }
  }
  public static void logError(final Exception ex) {
    log(new Status(IStatus.ERROR,PLUGIN_ID,null,ex));
  }
  public static void logError(final String message,@Nullable final Exception ex) {
    log(new Status(IStatus.ERROR,PLUGIN_ID,message,ex));
  }
  // @Override
  public void start(@Nullable final BundleContext context) throws Exception {
    // super.start(context);
    plugin=this;
  }
  // @Override
  public void stop(@Nullable final BundleContext context) throws Exception {
    plugin=null;
    // super.stop(context);
  }
}
