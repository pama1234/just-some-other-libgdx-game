package pama1234.gdx.game.util.legacy;

import com.badlogic.gdx.Input.Buttons;

import pama1234.gdx.game.app.app0002.RealGame;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.math.Tools;
import pama1234.math.vec.Vec2f;

public class Info extends TextBoard{
  public static int TEXT_SIZE=16;
  String[][] data;
  int state;
  float cw;
  public Info(RealGame p,float x,float y) {
    this(p,x,y,new String[] {
      "(炼蛊模拟器)粒子生命v1.3\n\n"+
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
        "1. 更好的美工\n"+
        "2. 完善设置界面\n"+
        "    a. 固定按钮位置",
      "操作说明：右键拖拽相机位置，滚轮调整相机距离，左键选择菜单或单个粒子，“wasd键”微调相机或粒子位置,“↑↓←→”键用于浏览粒子数据\n\n"+
        "v1.1添加特性：空格用于暂停当前游戏，e键和q键可以暂时的改变选中的粒子的颜色（在取消选择时会变回原来的颜色，以避免粒子系统失衡）\n\n"+
        "v1.2添加特性：添加了开始界面，加载及保存游戏，空的设置界面，以及打分机制，分数可以从沙盒顶上的盒子中看到，每个粒子都会被系统打分，而这块计分板只会显示你选中的粒子，所有粒子的分数都和其身后的“虫子”的长度和宽度正相关。"+
        "存档工具栏的“存档”和“读档”按钮用于记录当前沙盒的数据，而后面两个按钮暂未实现，请注意！！你只有一个存档槽，请谨慎存档，每次存档都会覆盖原先的游戏记录\n\n"+
        "v1.3移植到了Libgdx的电脑和安卓端，取消了存档功能",
      "这个模拟器是pama1234做的，但是原理是别人想到的的，参考以下链接：\n"+
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
  }
  @Override
  public void draw() {
    // p.fontScale(p.pus);
    // p.textSize(16);
    // System.out.println("Info.drawLayer()");
    p.background(p.colorFromInt(0xff4D3C94));
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
