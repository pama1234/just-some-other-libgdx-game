package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class InfoUtil{
  public static final String[] errorText=new String[] {
  };
  public static FileHandle dataLocation=Gdx.files.local("/data/cache/net/info");
  public static FileHandle infoDataLocation=Gdx.files.local("/data/cache/net/info/data.bin");
  public static FileHandle infoSourceLocation=Gdx.files.local("/data/cache/net/info/source.bin");
  public static InfoData info;
  public static InfoSource[] sources;
  public static void clearCache() {
    info.clear();
  }
  public static void loadOnline() {
    HttpRequestBuilder requestBuilder=new HttpRequestBuilder();
    for(InfoSource e:sources) {
      Gdx.net.sendHttpRequest(
        requestBuilder.newRequest().method(HttpMethods.GET).url(e.url).build(),new InfoResponseListener(e));
    }
  }
  public static class InfoResponseListener implements HttpResponseListener{
    public InfoSource source;
    public InfoResponseListener(InfoSource source) {
      this.source=source;
    }
    @Override
    public void handleHttpResponse(HttpResponse httpResponse) {
      updateInfo(source,httpResponse.getResultAsString(),info);
    }
    @Override
    public void failed(Throwable t) {}
    @Override
    public void cancelled() {}
  }
  public static void updateInfo(InfoSource source,String s,InfoData in) {
    String a=getInfo(s,source.start,source.end,source.pointer);
    int tp=a.indexOf(source.versionDeclare);
    int tp_2=a.indexOf('\n',tp+source.versionDeclare.length());
    String substring=a.substring(tp+source.versionDeclare.length(),tp_2).trim().replaceFirst("^0+(?!$)","");
    String info=a.substring(tp_2).trim();
    int version=Integer.parseInt(substring);
    // System.out.println("版本号："+version);
    // System.out.println(info);
    if(in.version<version) in.set(version,info);
  }
  public static String getInfo(String s,String start,String end,int pointer) {
    int index=s.indexOf(start);
    while(index>=0) {
      int temp=index;
      int indexEnd=s.indexOf(end,index+1);
      index=s.indexOf(start,index+1);
      if(indexEnd<index||index==-1) {
        if(pointer==0) return s.substring(temp+start.length(),indexEnd);
        else pointer--;
      }
    }
    return null;
  }
  public static String[] info0001=new String[] {
  },info0003=new String[] {
    "欢迎！以下是公告",
    "请单指或双指拖拽或使用鼠标右键和滚轮调节视角位置。",
    "这是一个空白的游戏框架，可以作为各位写游戏的模板。",
    "目前仍在开发阶段，欢迎加QQ群 589219461。"
  };
  public static class InfoSource{
    public String url;
    public String start,end,versionDeclare;
    public int pointer;
    @Deprecated
    public InfoSource() {}//kryo
    public InfoSource(String url,String start,String end,String versionDeclare,int pointer) {
      this.url=url;
      this.start=start;
      this.end=end;
      this.versionDeclare=versionDeclare;
      this.pointer=pointer;
    }
  }
  public static class InfoData{
    public String[] data=errorText;
    public int version=-1;
    public InfoData() {}
    public void clear() {
      version=-1;
      data=errorText;
    }
    public void set(int version,String info) {
      this.version=version;
      this.data=info.split("\n");
    }
    public InfoData(String data,int version) {
      this(data.split("\n"),version);
    }
    public InfoData(String[] data,int version) {
      this.data=data;
      this.version=version;
    }
  }
}