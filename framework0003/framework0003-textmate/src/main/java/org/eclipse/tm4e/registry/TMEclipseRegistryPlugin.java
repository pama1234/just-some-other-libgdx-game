package org.eclipse.tm4e.registry;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.registry.internal.GrammarRegistryManager;
import org.osgi.framework.BundleContext;

public class TMEclipseRegistryPlugin extends Plugin{
  public static final String PLUGIN_ID="org.eclipse.tm4e.registry";
  @Nullable
  private static volatile TMEclipseRegistryPlugin plugin;
  @Nullable
  public static TMEclipseRegistryPlugin getDefault() {
    return plugin;
  }
  public static void log(final IStatus status) {
    final var p=plugin;
    if(p!=null) {
      p.getLog().log(status);
    }else {
      System.out.println(status);
    }
  }
  public static void logError(final Exception ex) {
    log(new Status(IStatus.ERROR,PLUGIN_ID,ex.getMessage(),ex));
  }
  public static void logError(final String message,@Nullable final Exception ex) {
    log(new Status(IStatus.ERROR,PLUGIN_ID,message,ex));
  }
  @Override
  public void start(@Nullable final BundleContext bundleContext) throws Exception {
    super.start(bundleContext);
    plugin=this;
  }
  @Override
  public void stop(@Nullable final BundleContext bundleContext) throws Exception {
    plugin=null;
    super.stop(bundleContext);
  }
  public static IGrammarRegistryManager getGrammarRegistryManager() {
    return GrammarRegistryManager.getInstance();
  }
}
