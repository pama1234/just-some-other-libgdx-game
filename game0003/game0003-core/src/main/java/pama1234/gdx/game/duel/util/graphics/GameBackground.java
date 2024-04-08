package pama1234.gdx.game.duel.util.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

import pama1234.gdx.game.duel.Duel;

public final class GameBackground{
  public final Duel p;
  public final ArrayList<BackgroundLine> lineList=new ArrayList<>();
  public final float maxAccelerationMagnitude;
  public final Color lineColor;
  public GameBackground(Duel p,Color col) {
    this(p,col,0.1f);
  }
  public GameBackground(Duel p,Color col,float maxAcc) {
    this.p=p;
    lineColor=col;
    maxAccelerationMagnitude=maxAcc;
    for(int i=0;i<10;i++) {
      lineList.add(new HorizontalLine(this.p));
    }
    for(int i=0;i<10;i++) {
      lineList.add(new VerticalLine(p));
    }
  }
  public void update() {
    for(BackgroundLine eachLine:lineList) eachLine.update(p.random(-maxAccelerationMagnitude,maxAccelerationMagnitude));
  }
  public void display() {
    p.doStroke();
    p.stroke(lineColor);
    // System.out.println("GameBackground.display()");
    for(BackgroundLine eachLine:lineList) {
      // System.out.println("GameBackground.display() "+eachLine);
      eachLine.display();
    }
    p.noStroke();
  }
}