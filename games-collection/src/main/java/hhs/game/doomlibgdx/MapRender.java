package hhs.game.doomlibgdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MapRender extends Actor{
  char map[][];
  int x,y;
  BitmapFont font;
  float fs=10;
  public MapRender(char map[][]) {
    font=new BitmapFont();
    font.setColor(Color.WHITE);
    this.map=map;
    x=0;
    y=0;
  }
  public void up(int x,int y) {
    this.x=x;
    this.y=y;
  }

  @Override
  public void draw(Batch arg0,float arg1) {
    for(int i=0;i<map.length;i++) {
      for(int j=0;j<map[i].length;j++) {
        if(map[i][j]=='#') {
          font.draw(arg0,"#",i*fs+getX(),j*fs+getY());
        }
        if(i==x&&j==y) {
          font.draw(arg0,"Y",i*fs+getX(),j*fs+getY());
        }
      }

    }
  }

}
