package pama1234.gdx.game.ui.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.state.state0001.game.KryoUtil;

public class InfoUtil{
  public static final String[] errorText=new String[] {"未获取线上信息"};
  public static FileHandle dataLocation=Gdx.files.local("/data/cache/net/info");
  public static FileHandle infoDataLocation=Gdx.files.local("/data/cache/net/info/data.bin");
  public static FileHandle infoSourceLocation=Gdx.files.local("/data/cache/net/info/source.bin");
  public static InfoData info;
  public static InfoSource[] sources;
  public static void clearCache() {
    info.clear();
  }
  public static void loadCache() {
    sources=KryoUtil.load(Screen0011.kryo,infoSourceLocation,InfoSource[].class);
    info=KryoUtil.load(Screen0011.kryo,infoDataLocation,InfoData.class);
    if(sources==null) sources=new InfoSource[] {
      new InfoSource("https://zhuanlan.zhihu.com/p/606753465",
        "空想世界文字内容开始","空想世界文字内容结束","空想世界文字版本号",0)
    };
    if(info==null) info=new InfoData();
  }
  public static void saveCache() {
    dataLocation.mkdirs();
    KryoUtil.save(Screen0011.kryo,infoSourceLocation,sources);
    KryoUtil.save(Screen0011.kryo,infoDataLocation,info);
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
    "粒子不会撞墙啦！",
    "规则是很经典的贪吃蛇！",
    "将来会做成可爱的联机游戏！",
    "aparapi可能无法工作",
    "本程序需要java17运行",
    "采用并行计算！修了很久很久的算法哦",
    "经过测试，在1050Ti上可流畅运行1024*12个粒子",
    "采用异步计算（和渲染！！用户视角不易卡顿",
    "透明贴图渲染会有问题，请在阅读后关闭提示",
    "以下功能请通过测试得出用法",
    "WASD和空格和shift移动视角",
    "安卓版T打开全部设置",
    "I关闭参数信息",
    "滚轮调整视角速度",
    "右键或alt或esc更改鼠标功能",
    "RF调整位置倍数",
    "Z暂停时间，XC控制鼠标灵敏度",
    "NM调整视野范围，谨慎操作",
    "H关闭提示，改粒子数据需要改动源代码才行",
    "pama1234出品，谢谢观看",
    "————在很久之后，他终于把半自制的3D框架写好啦"
  },
    info0003=new String[] {
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