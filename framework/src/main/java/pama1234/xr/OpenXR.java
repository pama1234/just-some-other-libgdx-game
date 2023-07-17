package pama1234.xr;

public interface OpenXR{
  public void xrInitialize();
  public void xrShutdown();
  public void xrCreateInstance();
  public void xrDestroyInstance();
  public void xrGetInstanceProcAddr();
  public void xrCreateSession();
  public void xrDestroySession();
  public void xrBeginSession();
  public void xrEndSession();
  public void xrWaitFrame();
  public void xrBeginFrame();
  public void xrEndFrame();
}