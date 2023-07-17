package pama1234.gdx.game.util.legacy;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.UITools;
import pama1234.gdx.util.app.UtilScreenColor;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class Info extends TextBoard{
  public static int TEXT_SIZE=16;
  public String[][] data;
  public int state;
  public float cw;
  public Color background;
  public Info(RealGame p,float x,float y) {
    this(p,x,y,new String[] {
      "粒子生命v1.3\n\n"+
        "目录：\n\n"+
        "1. 更新日志\n"+
        "2. 操作说明\n"+
        "3. 相关链接\n"+
        "pama_1234: \n"+
        "//--- [v1.2更新]\n"+
        "1. 打分机制 √\n"+
        "2. 保存游戏 √\n"+
        "3. 游戏介绍 √\n"+
        "4. 开始和设置界面 √\n"+
        "5. 联机版\n"+
        "(以及更新日志 √)\n"+
        "//--- [v1.3更新]\n"+
        "1. 移植到Libgdx\n"+
        "2. 发布到Taptap\n"+
        "  a. 取消了存档功能\n"+
        "  b. 添加了安卓UI\n",
      "操作说明：右键拖拽相机位置，滚轮调整\n"+
        "相机距离，左键选择菜单或单个粒子，\n"+
        "“wasd键”微调相机或粒子位置,“↑↓←→”\n"+
        "键用于浏览粒子数据\n\n"+
        "v1.1添加特性：空格用于暂停当前游戏，\n"+
        "e键和q键可以暂时的改变选中的粒子的颜\n"+
        "色（在取消选择时会变回原来的颜色，以\n"+
        "避免粒子系统失衡）\n\n"+
        "v1.2添加特性：添加了开始界面，加载及\n"+
        "保存游戏，空的设置界面，以及打分机制，\n"+
        "分数可以从沙盒顶上的盒子中看到，每个\n"+
        "粒子都会被系统打分，而这块计分板只会\n"+
        "显示你选中的粒子，所有粒子的分数都和\n"+
        "其身后的“虫子”的长度和宽度正相关。\n"+
        "存档工具栏的“存档”和“读档”按钮用于\n"+
        "记录当前沙盒的数据，而后面两个按钮\n"+
        "暂未实现，请注意！！你只有一个存档\n"+
        "槽，请谨慎存档，每次存档都会覆盖\n"+
        "原先的游戏记录\n\n"+
        "v1.3移植到了Libgdx的电脑和\n"+
        "安卓端，取消了存档功能",
      "这个模拟器是pama1234做的，但是原理\n"+
        "是别人想到的的，参考以下链接：\n\n"+
        "http://www.ventrella.com/Clusters/"});
  }
  public Info(RealGame p,float x,float y,String[] dataIn) {
    super(p,x,y,TEXT_SIZE*20,TEXT_SIZE*40,TEXT_SIZE);
    data=new String[dataIn.length][];
    for(int i=0;i<data.length;i++) {
      data[i]=dataIn[i].split("\n");
    }
    // beforeDraw();
    refresh();
    //---
    background=UtilScreenColor.newColorFromInt(0xff4D3C94);
  }
  @Override
  public void draw() {
    // p.fontScale(p.pus);
    // p.textSize(16);
    // System.out.println("Info.drawLayer()");
    p.background(background);
    p.textColor(255);
    UITools.border(g,0,0,g.width(),g.height());
    p.fill(p.colorFromInt(0xff006799));
    p.rect(0,0,data.length*TEXT_SIZE,TEXT_SIZE);
    p.fill(p.colorFromInt(0xff2A00FF));
    p.rect(state*TEXT_SIZE,0,TEXT_SIZE,TEXT_SIZE);
    p.fill(255);
    float ty=0;
    for(int i=0;i<data.length;i++) {
      p.text(String.valueOf(i),i*TEXT_SIZE,ty);
      UITools.border(g,i*TEXT_SIZE,0,TEXT_SIZE,TEXT_SIZE);
    }
    for(int i=0;i<data[state].length;i++) p.text(String.valueOf(data[state][i]),TEXT_SIZE/2,i*20+TEXT_SIZE*2);
    p.fill(p.colorFromInt(0xffFB612E));
    p.rect(g.width()-(cw=p.textWidth("Ctrl-C")),0,cw,TEXT_SIZE);
    p.fill(255);
    p.text("Ctrl-C",g.width()-cw,ty);
    UITools.border(g,g.width()-cw,0,cw,TEXT_SIZE);
  }
  @Override
  public void mousePressed(MouseInfo info) {
    final Vec2f pos=point.pos;
    if(info.button==Buttons.LEFT) {
      if(Tools.inBox(
        p.mouse.x,p.mouse.y,
        pos.x,pos.y,
        data.length*TEXT_SIZE-1,TEXT_SIZE)) {
        state=(int)Math.floor((Math.ceil(p.mouse.x)-pos.x)/TEXT_SIZE);
        refresh();
      }else if(Tools.inBox(
        p.mouse.x,p.mouse.y,
        pos.x+g.width()-cw,pos.y,
        cw,TEXT_SIZE)) {
          // Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data[state]),this);
        }
    }
  }
  // @Override
  // public void lostOwnership(Clipboard clipboard,Transferable contents) {
  //   System.out.println(clipboard+" "+contents);
  // }
  @Override
  public void beforeDraw() {}
}
