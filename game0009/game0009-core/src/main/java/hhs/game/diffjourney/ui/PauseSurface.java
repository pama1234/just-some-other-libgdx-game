package hhs.game.diffjourney.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.ListenerBuilder;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;

public class PauseSurface extends Table{
  BasicScreen screen;
  float fontScale=8,time=0,animTime=.4f;
  public boolean show=false,ok=false;
  Vector2 correctPos=new Vector2();
  PixelFontButton c;
  public PauseSurface(BasicScreen b) {
    setBackground(TextureTool.ttd(PixmapBuilder.getRectangle(1,1,ColorTool.明月珰)));
    screen=b;
    center();
    c=newButton("继续游戏");
    c.addListener(
      ListenerBuilder.touch(
        ()-> {
          screen.setTimeAcceleration(1);
          show=false;
        }));
    add(UiList.getBack()).padTop(50);
    validate();
    setSize(getPrefWidth(),getPrefHeight());
    setPosition(Resource.width/2-getPrefWidth()/2,-getPrefHeight());
    correctPos.set(getX(),Resource.height/2-getPrefHeight()/2);
  }
  @Override
  public void act(float arg0) {
    if(show) {
      setY(correctPos.y);
    }else {
      setY(-getPrefHeight());
    }
    if(show) super.act(arg0);
  }
  @Override
  public void draw(Batch arg0,float arg1) {
    if(show) super.draw(arg0,arg1);
    // TODO: Implement this method
  }
  public PixelFontButton newButton(String str) {
    PixelFontButton b=new PixelFontButton(str);
    b.setScale(fontScale);
    add(b).width(b.getPrefWidth()).padBottom(50).row();
    return b;
  }
  public BasicScreen getScreen() {
    return this.screen;
  }
  public void setScreen(BasicScreen screen) {
    this.screen=screen;
  }
}
