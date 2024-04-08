package pama1234.gdx.game.sandbox.platformer.item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.game.sandbox.platformer.player.ctrl.PlayerControllerFull;
import pama1234.math.UtilMath;
import pama1234.math.physics.PathVar;

/**
 * <pre>
 * 玩家的物品存储器，包括物品栏，背包，和手持物品等
 * 这个类用于放置绘制相关的内容
 * 
 * 在绘制屏幕上的物品时，物品等图形的尺寸需要乘以p.bu*0.5f
 * </pre>
 * 
 * @see InventoryCore
 * @see PlayerControllerFull 用于将键盘鼠标等输入信息转换为物品栏的操作
 */
public class Inventory extends InventoryCore{
  public Inventory() {}
  public Inventory(LivingEntity pe,int size,int hotSlotSize) {
    this.pe=pe;
    this.hotSlotSize=hotSlotSize;
    data=new ItemSlot[size];
    for(int i=0;i<data.length;i++) data[i]=new ItemSlot();
    innerInit();
  }
  public void innerInit() {
    hotSlots=new DisplaySlot[hotSlotSize];
    hotSlotKeyData=new boolean[hotSlotSize];
    for(int i=0;i<hotSlots.length;i++) hotSlots[i]=new DisplaySlot(data[i]);
    handSlot=new DisplaySlot(data[data.length-1]);
    backpackSlots=new DisplaySlot[data.length-hotSlotSize-1];
    for(int i=0;i<backpackSlots.length;i++) backpackSlots[i]=new DisplaySlot(data[i+hotSlots.length]);
    displaySlots=new DisplaySlot[][] {hotSlots,backpackSlots};
    r=new PathVar(rSize=UtilMath.min(pe.type.attr.w,pe.type.attr.h));

    this.p=pe.p;
  }
  /**
   * 绘制屏幕视角中的图形
   * 
   * @see MainPlayer#displayScreen()
   */
  public void displayScreen() {
    if(p.isAndroid) androidDisplayScreen();
    else desktopDisplayScreen();
  }
  /** 用于绘制手机端的物品存储器UI */
  public void androidDisplayScreen() {
    float tts=p.textScale();
    p.textScale(tts/3f);
    switch(displayState) {
      case noDisplay: {}
        break;
      // 绘制手持物品和被选中的物品
      case displayHoldItem: {
        // displaySlotItem(pe.p,handSlot);

        // float tx=p.mouse.x-pe.cx();
        // float ty=p.mouse.y-pe.cy();
        // float rad=UtilMath.atan2(ty,tx);
        // if(tx<0) rad-=UtilMath.PI;

        // displaySlotItem(pe.p,selectSlot(),rad);
      }
        break;
      // 绘制物品栏
      case displayHotSlot: {
        drawSelectRectScreen();
        displayAllHotSlotScreen();
      }
        break;
      // 绘制物品栏和背包
      case displayFullInventory: {
        drawSelectRectScreen();
        displayAllHotSlotScreen();
        displayBackpackSlotScreen();
        //        displaySlotWhenNotNull(handSlot);
        drawMouseHoldScreen(handSlot);
      }
        break;
    }
    p.textScale(tts);
  }
  /** TODO 用于绘制电脑端的物品存储器UI */
  public void desktopDisplayScreen() {}
  /**
   * 绘制相机视角中的图形
   * 
   * @see MainPlayer#display()
   */
  public void display() {
    if(p.isAndroid) androidDisplayCam();
    else desktopDisplayCam();
  }
  /** TODO 用于绘制手机端的物品存储器UI */
  public void androidDisplayCam() {
    p.textScale(0.5f);
    switch(displayState) {
      case noDisplay: {}
        break;
      // 绘制手持物品和被选中的物品
      case displayHoldItem: {
        displaySlotItem(pe.p,handSlot);

        float tx=p.mouse.x-pe.cx();
        float ty=p.mouse.y-pe.cy();
        float rad=UtilMath.atan2(ty,tx);
        if(tx<0) rad-=UtilMath.PI;

        displaySlotItem(pe.p,selectSlot(),rad);
      }
        break;
      // 绘制物品栏
      case displayHotSlot: {
        // drawSelectRect();
        // displayAllHotSlot();
      }
        break;
      // 绘制物品栏和背包
      case displayFullInventory: {
        // drawSelectRect();
        // displayAllHotSlot();
        // displayBackpackSlot();
        // displaySlotWhenNotNull(handSlot);
        //        drawMouseHold(handSlot);
      }
        break;
    }
    p.textScale(1);
  }
  /** 用于绘制电脑端的物品存储器UI */
  public void desktopDisplayCam() {
    p.textScale(0.5f);
    switch(displayState) {
      case noDisplay: {}
        break;
      // 绘制手持物品和被选中的物品
      case displayHoldItem: {
        displaySlotItem(pe.p,handSlot);

        float tx=p.mouse.x-pe.cx();
        float ty=p.mouse.y-pe.cy();
        float rad=UtilMath.atan2(ty,tx);
        if(tx<0) rad-=UtilMath.PI;

        displaySlotItem(pe.p,selectSlot(),rad);
      }
        break;
      // 绘制物品栏
      case displayHotSlot: {
        drawSelectRect();
        displayAllHotSlot();
      }
        break;
      // 绘制物品栏和背包
      case displayFullInventory: {
        drawSelectRect();
        displayAllHotSlot();
        displayBackpackSlot();
        displaySlotWhenNotNull(handSlot);
        drawMouseHold(handSlot);
      }
        break;
    }
    p.textScale(1);
  }
  public void displayBackpackSlot() {
    Screen0011 p=pe.p;
    for(int i=0;i<backpackSlots.length;i++) displaySlot(p,backpackSlots[i]);
    p.noTint();
  }
  public void displayBackpackSlotScreen() {
    Screen0011 p=pe.p;
    for(int i=0;i<backpackSlots.length;i++) displaySlotScreen(p,backpackSlots[i]);
    p.noTint();
  }
  public void displayAllHotSlot() {
    Screen0011 p=pe.p;
    for(int i=0;i<hotSlots.length;i++) displaySlot(p,hotSlots[i]);
    p.noTint();
  }
  /** 绘制整个快捷物品栏（单行，使用滚轮或鼠标右键移动选择框） */
  public void displayAllHotSlotScreen() {
    Screen0011 p=pe.p;
    for(int i=0;i<hotSlots.length;i++) displaySlotScreen(p,hotSlots[i]);
    p.noTint();
  }
  /** 将这个物品居中的绘制在鼠标上 */
  public void drawMouseHold(DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
      p.image(tr,p.mouse.x-ths.w2/2f,p.mouse.y-ths.h2/2f,ths.w2,ths.h2);
      displayItemCount(p,ti,p.mouse.x-ths.w1/2f,p.mouse.y-ths.h1/2f);
    }
  }
  /** 将这个物品居中的绘制在鼠标上，物品的大小以窗口大小为参考标准（而不是世界） */
  public void drawMouseHoldScreen(DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
      int ox=p.mouse.ox;
      int oy=p.mouse.oy;
      float w2=ths.w2;
      float h2=ths.h2;
      p.image(tr,ox-w2/2f,oy-h2/2f,w2,h2);
      float w1=ths.w1;
      float h1=ths.h1;
      displayItemCount(p,ti,ox-w1/2f,oy-h1/2f);
    }
  }
  public void displaySlotWhenNotNull(DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) {
      drawSlotBackground(p,ths);
      drawSlotItem(p,ths,ti);
    }
  }
  public static void displaySlot(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    drawSlotBackground(p,ths);
    if(ti!=null) drawSlotItem(p,ths,ti);
  }
  public static void displaySlotScreen(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    drawSlotBackgroundScreen(p,ths);
    if(ti!=null) drawSlotItemScreen(p,ths,ti);
  }
  public static void drawSlotBackground(Screen0011 p,DisplaySlot ths) {
    p.tint(255,127);
    p.image(ImageAsset.items[0][0],ths.x1,ths.y1);
    p.noTint();
  }
  public static void drawSlotBackgroundScreen(Screen0011 p,DisplaySlot ths) {
    p.tint(255,127);
    p.image(ImageAsset.items[0][0],ths.x1,ths.y1,p.bu*0.5f,p.bu*0.5f);
    p.noTint();
  }
  public static void drawSlotItem(Screen0011 p,DisplaySlot ths,Item ti) {
    TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
    p.image(tr,ths.x1+ths.w3(),ths.y1+ths.h3(),ths.w2,ths.h2);
    displayItemCount(p,ti,ths.x1,ths.y1);
  }
  public static void drawSlotItemScreen(Screen0011 p,DisplaySlot ths,Item ti) {
    TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
    p.image(tr,ths.x1+ths.w3(),ths.y1+ths.h3(),ths.w2,ths.h2);
    displayItemCount(p,ti,ths.x1,ths.y1);
  }
  public static void drawSlotItem(Screen0011 p,DisplaySlot ths,Item ti,float rad) {
    TextureRegion tr=ti.type.rttr.tiles[ti.displayType()];
    float tx=ths.x1+ths.w3();
    float ty=ths.y1+ths.h3();
    float tw=ths.w2;
    float th=ths.h2;

    p.pushMatrix();
    p.translate(tx+tw/2f,ty+th/2f);
    p.rotate(rad);
    p.image(tr,-tw/2f,-th/2f,tw,th);
    p.popMatrix();

    displayItemCount(p,ti,ths.x1,ths.y1);
  }
  /**
   * 输入物品框，拆分出物品并绘制
   * 
   * @param p
   * @param ths
   */
  public static void displaySlotItem(Screen0011 p,DisplaySlot ths) {
    Item ti=ths.data.item;
    if(ti!=null) drawSlotItem(p,ths,ti);
  }
  public static void displaySlotItem(Screen0011 p,DisplaySlot ths,float rad) {
    Item ti=ths.data.item;
    if(ti!=null) drawSlotItem(p,ths,ti,rad);
  }
  public static void displayItemCount(Screen0011 p,Item ti,float x,float y) {
    p.textColor(255,191);
    p.text(Integer.toString(ti.count),x,y);
  }
  public void drawSelectRect() {
    DisplaySlot ths=selectSlotPos>=hotSlots.length?backpackSlots[selectSlotPos-hotSlots.length]:selectSlot();
    TextureRegion tr=ImageAsset.items[0][1];
    p.tint(255,191);
    p.image(tr,ths.x1,ths.y1);
  }
  public void drawSelectRectScreen() {
    DisplaySlot ths=selectSlotPos>=hotSlots.length?backpackSlots[selectSlotPos-hotSlots.length]:selectSlot();
    TextureRegion tr=ImageAsset.items[0][1];
    p.tint(255,191);
    p.image(tr,ths.x1,ths.y1,p.bu*0.5f,p.bu*0.5f);
  }
}