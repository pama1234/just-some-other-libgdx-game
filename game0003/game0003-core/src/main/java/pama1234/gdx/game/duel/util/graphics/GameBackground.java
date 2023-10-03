package pama1234.gdx.game.duel.util.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.duel.Duel;

public final class GameBackground{
  public final Duel duel;
  public final ArrayList<BackgroundLine> lineList=new ArrayList<BackgroundLine>();
  public final float maxAccelerationMagnitude;
  public final Color lineColor;
  public GameBackground(Duel duel,Color col) {
    this(duel,col,0.1f);
  }
  public GameBackground(Duel duel,Color col,float maxAcc) {
    this.duel=duel;
    lineColor=col;
    maxAccelerationMagnitude=maxAcc;
    for(int i=0;i<10;i++) {
      lineList.add(new HorizontalLine(this.duel));
    }
    for(int i=0;i<10;i++) {
      lineList.add(new VerticalLine(duel));
    }
  }
  public void update() {
    for(BackgroundLine eachLine:lineList) eachLine.update(duel.random(-maxAccelerationMagnitude,maxAccelerationMagnitude));
  }
  public void display() {
    duel.stroke(lineColor);
    // System.out.println("GameBackground.display()");
    for(BackgroundLine eachLine:lineList) {
      // System.out.println("GameBackground.display() "+eachLine);
      eachLine.display();
    }
  }
}