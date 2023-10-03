package com.innowhere.relproxy.impl.gproxy;

import com.innowhere.relproxy.gproxy.GProxyConfig;
import com.innowhere.relproxy.impl.gproxy.core.GProxyImpl;

/**
 *
 * @author jmarranz
 */
public class GProxyDefaultImpl extends GProxyImpl{
  public static GProxyConfig createGProxyConfig() {
    return new GProxyConfigImpl();
  }

  public static void initStatic(GProxyConfigImpl config) {
    if(!config.isEnabled()) return;

    checkSingletonNull(SINGLETON);
    SINGLETON=new GProxyDefaultImpl();
    SINGLETON.init(config);
  }

  public static <T> T createStatic(T obj,Class<T> clasz) {
    if(SINGLETON==null) return obj; // No se ha llamado al init o enabled = false

    return SINGLETON.create(obj,clasz);
  }

  public static Object createStatic(Object obj,Class<?>[] classes) {
    if(SINGLETON==null) return obj; // No se ha llamado al init o enabled = false

    return SINGLETON.create(obj,classes);
  }
}
