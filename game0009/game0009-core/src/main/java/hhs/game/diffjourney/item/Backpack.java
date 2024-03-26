package hhs.game.diffjourney.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import hhs.gdx.hsgame.tools.LazyBitmapFont;
import hhs.gdx.hsgame.tools.PixmapBuilder;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.tools.TextureTool;
import java.util.Map.Entry;

public class Backpack extends ScrollPane{
  Preferences data;
  public static final float w=Resource.u*8,h=w/2;
  public static final int lw=8;
  public Table main;
  public Backpack(String data) {
    this(Gdx.app.getPreferences(data));
  }
  public Backpack(Preferences data) {
    super(new Table());
    main=(Table)getActor();
    this.data=data;
    ScrollPane.ScrollPaneStyle style=new ScrollPane.ScrollPaneStyle(
      TextureTool.ttd(PixmapBuilder.getRectangle(50,50,Color.BLACK)),
      null,
      null,
      null,
      null);
    setStyle(style);
    setSize(w,h);
    setPosition((Resource.width-w)/2,(Resource.height-h)/2);
    //main.setFillParent(true);
    main.top().left();
    update();
    setActor(main);
  }
  public void update() {
    int index=1;
    //clear();
    for(int i=0;i<99;++i) for(Entry<String,?> val:data.get().entrySet()) {
      if(index++>lw) {
        index=2;
        main.row();
      }
      try {
        main.add(new ItemUi((Class<? extends ItemData>)Class.forName(val.getKey())));
      }catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
  public static class ItemUi extends Widget{
    public static final LazyBitmapFont font=Resource.font.newFont((int)Resource.u/4,Color.WHITE);
    public ItemData currItem;
    public ItemUi(Class<? extends ItemData> item) {
      try {
        currItem=item.getConstructor().newInstance();
      }catch(Exception e) {
        e.printStackTrace();
      }
      invalidateHierarchy();
      setSize(getPrefWidth(),getPrefHeight());
    }
    @Override
    public void act(float arg0) {
      super.act(arg0);
    }
    @Override
    public void draw(Batch arg0,float arg1) {
      super.draw(arg0,arg1);
      arg0.draw(currItem.getShowTexture(),getX(),getY()+Resource.u/4,getWidth(),getHeight()-Resource.u/4);
      font.draw(arg0,currItem.getShowName(),getX(),getY()+Resource.u/4);
    }
    @Override
    public float getPrefWidth() {
      return Resource.u;
    }
    @Override
    public float getPrefHeight() {
      return Resource.u+Resource.u/4;
    }
  }
}
