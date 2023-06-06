package pama1234.gdx.game.app.app0004;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0018 extends ScreenCore2D{
  @Override
  public void setup() {
    HttpRequestBuilder requestBuilder=new HttpRequestBuilder();
    HttpRequest httpRequest=requestBuilder.newRequest().method(HttpMethods.GET).url("https://zhuanlan.zhihu.com/p/606753465").build();
    Gdx.net.sendHttpRequest(httpRequest,new HttpResponseListener() {
      @Override
      public void handleHttpResponse(HttpResponse httpResponse) {
        String s=httpResponse.getResultAsString();
        test(s);
      }
      @Override
      public void failed(Throwable t) {}
      @Override
      public void cancelled() {}
    });
  }
  public void test(String s) {
    String a=getInfo(s,"空想世界文字内容开始","空想世界文字内容结束",0);
    String versionString="空想世界文字版本号";
    int tp=a.indexOf(versionString);
    int tp_2=a.indexOf('\n',tp+versionString.length());
    String substring=a.substring(tp+versionString.length(),tp_2).trim().replaceFirst("^0+(?!$)","");
    int version=Integer.parseInt(substring);
    System.out.println("版本号："+version);
    System.out.println(a.substring(tp_2).trim());
  }
  public String getInfo(String s,String start,String end,int pointer) {
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
  @Override
  public void update() {}
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {}
  @Override
  public void frameResized() {}
  @Override
  public void dispose() {
    super.dispose();
  }
}