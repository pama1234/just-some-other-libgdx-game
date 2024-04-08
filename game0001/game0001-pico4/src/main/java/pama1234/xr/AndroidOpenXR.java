package pama1234.xr;

public class AndroidOpenXR implements OpenXR{
  // JNI bindings for OpenXR
  public native void xrInitialize();
  public native void xrShutdown();
  public native void xrCreateInstance();
  public native void xrDestroyInstance();
  public native void xrGetInstanceProcAddr();
  public native void xrCreateSession();
  public native void xrDestroySession();
  public native void xrBeginSession();
  public native void xrEndSession();
  public native void xrWaitFrame();
  public native void xrBeginFrame();
  public native void xrEndFrame();
}