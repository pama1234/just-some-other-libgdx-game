package pama1234.gdx.game.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpRequestBuilder;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0018 extends ScreenCore2D{
  public String start="空想世界文字内容开始";
  public String end="空想世界文字内容结束";
  public int pointer=0;
  @Override
  public void setup() {
    HttpRequestBuilder requestBuilder=new HttpRequestBuilder();
    HttpRequest httpRequest=requestBuilder.newRequest().method(HttpMethods.GET).url("https://zhuanlan.zhihu.com/p/606753465").build();
    Gdx.net.sendHttpRequest(httpRequest,new HttpResponseListener() {
      @Override
      public void handleHttpResponse(HttpResponse httpResponse) {
        String s=httpResponse.getResultAsString();
        getInfo(s,pointer);
      }
      @Override
      public void failed(Throwable t) {}
      @Override
      public void cancelled() {}
    });
  }
  public void getInfo(String s,int pointer) {
    int index=s.indexOf(start);
    while(index>=0) {
      int temp=index;
      int indexEnd=s.indexOf(end,index+1);
      index=s.indexOf(start,index+1);
      if(indexEnd<index||index==-1) {
        if(pointer==0) {
          System.out.println(s.substring(temp+start.length(),indexEnd));
          break;
        }else pointer--;
      }
    }
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