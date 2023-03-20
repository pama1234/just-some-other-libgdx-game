package pama1234.gdx.game.ui.generator;

import static pama1234.gdx.game.app.Screen0011.kryo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;

import pama1234.gdx.game.state.state0001.game.KryoUtil;

public class InfoUtil{
  public static final String[] errorText=new String[] {
    "以下是非联网状态下的古老公告信息：",
    "咳咳，很明显你是一个新玩家！",
    "你应该已经注意到了与应用图标（某二次元少女）和十分霸气的名字（空想世界 1）和与之十分不符的UI和画质",
    "并且心中也应该有了某种不祥的预感（比如：这不会只是又一个itch.io上一捞一大把的像素独立游戏吧）",
    "虽然但是，你的预感是十分准确的，咳咳，咳咳咳咳咳。。。。",
    "---以下是游戏说明---",
    "电脑端使用WASD或↑←↓→箭头按钮控制角色移动，空格控制角色跳跃，E打开或关闭背包",
    "鼠标左键破坏方块，鼠标右键替换方块或使用工作站，鼠标左键双击攻击生物（需空手或持有具备攻击能力的武器）",
    "鼠标左键拿起或放下物品栏内物品，鼠标右键或滚轮更改快捷栏",
    "安卓端请单指拖拽或双指拖拽+缩放调节视角位置，电脑端使用+和-按键",
    "工作台使用教程：最左侧的按钮，Step是每次只进行一次合成，要取走物品才进行下一次，On是连续合成，Off是关闭，",
    "然后-和+是调节合成表的，有多个相同输入物品的合成表，因此需要调节合成表才能合成特定物品（比如石剑）",
    "---以下是其他东东---",
    "“我准备使用libgdx和其他一些东东做一个游戏！”",
    "目前依然在原型测试阶段（而不是内测或公测或正式发布），欢迎加QQ群 589219461。",
    "各位可以将觉得需要实现的功能提出来，我会排序后一一实现。",
    "B站up主：“pama1234_2” 蓝色头像 id：646050693"
  };
  public static FileHandle dataLocation=Gdx.files.local("/data/cache/net/info");
  public static FileHandle infoDataLocation=Gdx.files.local("/data/cache/net/info/data.bin");
  public static FileHandle infoSourceLocation=Gdx.files.local("/data/cache/net/info/source.bin");
  public static InfoData info;
  public static InfoSource[] sources;
  public static void clearCache() {
    info.clear();
  }
  public static void loadCache() {
    sources=KryoUtil.load(kryo,infoSourceLocation,InfoSource[].class);
    info=KryoUtil.load(kryo,infoDataLocation,InfoData.class);
    if(sources==null) sources=new InfoSource[] {
      new InfoSource("https://zhuanlan.zhihu.com/p/606753465",
        "空想世界文字内容开始","空想世界文字内容结束","空想世界文字版本号",0)
    };
    if(info==null) info=new InfoData();
  }
  public static void saveCache() {
    dataLocation.mkdirs();
    KryoUtil.save(kryo,infoSourceLocation,sources);
    KryoUtil.save(kryo,infoDataLocation,info);
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