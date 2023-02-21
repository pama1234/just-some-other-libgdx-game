package pama1234.gdx.util.listener;

import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.util.listener.ServerEntityListener;
/**
 * 一个实体，指的是可监听各类事件（键盘鼠标，绘制刷新）的对象
 */
public interface EntityListener extends ServerEntityListener,InputListener{
  /**
   * 调用绘制方法，每帧调用一次，libgdx的OpenGL默认使用双缓冲区，也就是说每次绘制都应当清除之前的绘图，否则会导致画面闪烁
   * 
   * @see pama1234.gdx.util.app.UtilScreen#render(float)
   * 
   * @see pama1234.gdx.util.app.UtilScreen#display()
   * @see pama1234.gdx.util.app.UtilScreen#doDraw()
   * 
   * @see pama1234.gdx.util.app.UtilScreen#background(int)
   * @see pama1234.gdx.util.app.UtilScreen#backgroundColor(int)
   */
  default void display() {}
  /**
   * 调用刷新方法，理论上每帧调用一次，实际不一定，“类似后端的业务逻辑”和绘制逻辑应当分开，一般来说，在update方法中不能调用和绘制相关的方法，但是display方法中可以进行update中才能进行的操作
   * 
   * @see pama1234.gdx.util.app.UtilScreen#render(float)
   * 
   * @see pama1234.gdx.util.app.UtilScreen#update()
   * @see pama1234.gdx.util.app.UtilScreen#doUpdate()
   * 
   */
  default void update() {}
  /**
   * 此实体将被清理时被调用，一般在程序结束时被调用，安卓端的行为不明确
   */
  default void dispose() {}
  /**
   * 可能会在实体被创建时调用，但是不一定
   */
  default void init() {}
  /**
   * 在安卓端切后台时会被调用，电脑端目前基本无作用（除非更改lwjgl的配置）
   */
  default void pause() {}
  /**
   * 在安卓端从后台切换到使用此框架的APP时会被调用，电脑端目前基本无作用（除非更改lwjgl的配置）
   */
  default void resume() {}
  /**
   * 在电脑端的窗口发生移动时被调用，安卓端不会被调用
   * 
   * @param x 横向值，相对于屏幕的左上角
   * @param y 纵向值，相对于屏幕的左上角
   */
  default void frameMoved(int x,int y) {}
  /**
   * 在电脑端的窗口发生移动时会被调用，安卓端大概不会被调用
   */
  default void frameResized(int w,int h) {}
  /**
   * 按键按下时会被调用一次
   */
  default void keyPressed(char key,int keyCode) {}
  /**
   * 按键松开时会被调用一次
   */
  default void keyReleased(char key,int keyCode) {}
  /**
   * 在一个字符被输入时会被调用一次（比如中文输入法向程序输入了一个中文，那么这个会被调用）
   */
  default void keyTyped(char key) {}
  /**
   * 鼠标在按下状态时移动的话会被调用
   */
  default void mouseDragged() {}
  /**
   * 鼠标在移动时每帧会被调用
   */
  default void mouseMoved() {}
  /**
   * 鼠标在按下任意键时会被调用一次
   */
  default void mousePressed(MouseInfo info) {}
  /**
   * 鼠标在松开任意键时会被调用一次
   */
  default void mouseReleased(MouseInfo info) {}
  /**
   * 鼠标滚轮滚动时会被调用
   */
  default void mouseWheel(float x,float y) {}
  /**
   * 安卓版，触摸屏有手指松开时会被调用一次
   */
  default void touchEnded(TouchInfo info) {}
  /**
   * 安卓版，触摸屏上的每帧对应每个手指会被调用
   */
  default void touchMoved(TouchInfo info) {}
  /**
   * 安卓版，触摸屏上手指按下时会被调用一次
   */
  default void touchStarted(TouchInfo info) {}
}
