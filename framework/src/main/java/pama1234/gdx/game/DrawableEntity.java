package pama1234.gdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;

public class DrawableEntity extends Entity<UtilScreen> implements Drawable{
  public String name;
  public float leftWidth,rightWidth,topHeight,bottomHeight,minWidth,minHeight;
  //---
  public DrawFunction f;
  public Batch b;//TODO
  public DrawableEntity(UtilScreen p,DrawFunction in) {
    super(p);
    f=in;
  }
  public DrawableEntity(UtilScreen p,DrawFunction in,Drawable drawable) {
    super(p);
    f=in;
    if(drawable instanceof BaseDrawable) name=((BaseDrawable)drawable).getName();
    leftWidth=drawable.getLeftWidth();
    rightWidth=drawable.getRightWidth();
    topHeight=drawable.getTopHeight();
    bottomHeight=drawable.getBottomHeight();
    minWidth=drawable.getMinWidth();
    minHeight=drawable.getMinHeight();
  }
  // public abstract void f(Batch batch,float x,float y,float width,float height);
  public void draw(Batch batch,float x,float y,float width,float height) {
    batch.end();//TODO
    f.draw(b,x,y,width,height);
    // f(b,x,y,width,height);
    batch.begin();
  }
  public float getLeftWidth() {
    return leftWidth;
  }
  public void setLeftWidth(float leftWidth) {
    this.leftWidth=leftWidth;
  }
  public float getRightWidth() {
    return rightWidth;
  }
  public void setRightWidth(float rightWidth) {
    this.rightWidth=rightWidth;
  }
  public float getTopHeight() {
    return topHeight;
  }
  public void setTopHeight(float topHeight) {
    this.topHeight=topHeight;
  }
  public float getBottomHeight() {
    return bottomHeight;
  }
  public void setBottomHeight(float bottomHeight) {
    this.bottomHeight=bottomHeight;
  }
  public void setPadding(float topHeight,float leftWidth,float bottomHeight,float rightWidth) {
    setTopHeight(topHeight);
    setLeftWidth(leftWidth);
    setBottomHeight(bottomHeight);
    setRightWidth(rightWidth);
  }
  public float getMinWidth() {
    return minWidth;
  }
  public void setMinWidth(float minWidth) {
    this.minWidth=minWidth;
  }
  public float getMinHeight() {
    return minHeight;
  }
  public void setMinHeight(float minHeight) {
    this.minHeight=minHeight;
  }
  public void setMinSize(float minWidth,float minHeight) {
    setMinWidth(minWidth);
    setMinHeight(minHeight);
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name=name;
  }
  public String toString() {
    if(name==null) return ClassReflection.getSimpleName(getClass());
    return name;
  }
  @FunctionalInterface
  public interface DrawFunction{
    public void draw(Batch batch,float x,float y,float width,float height);
  }
}
