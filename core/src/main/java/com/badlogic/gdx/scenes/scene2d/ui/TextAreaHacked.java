package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Timer.Task;

public class TextAreaHacked extends TextArea{
  public TextAreaHacked(String text,TextFieldStyle style) {
    super(text,style);
  }
  public IntArray getLinesBreak() {//TODO hahaha
    return linesBreak;
  }
  public boolean focused() {
    return focused;
  }
  public void focused(boolean in) {
    focused=in;
  }
  public Task getKeyRepeatTask() {
    return keyRepeatTask;
  }
  public void cursorOn(boolean in) {
    cursorOn=in;
  }
  public boolean cursorOn() {
    return cursorOn;
  }
  public Task getBlinkTask() {
    return blinkTask;
  }
  public float blinkTime() {
    return blinkTime;
  }
}