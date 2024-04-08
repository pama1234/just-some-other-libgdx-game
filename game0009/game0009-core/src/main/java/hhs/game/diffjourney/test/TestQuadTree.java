package hhs.game.diffjourney.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.screens.BasicScreen;
import hhs.gdx.hsgame.tools.ColorTool;
import hhs.gdx.hsgame.tools.Resource;
import hhs.gdx.hsgame.util.QuadTree;

public class TestQuadTree extends BasicScreen{
  QuadTree<Box> quadTree;
  boolean touch=false;
  int count=0;
  public TestQuadTree() {
    debug=true;
    clearColor=ColorTool.克莱因蓝;
    quadTree=new QuadTree<>(0,0,Resource.width,Resource.height);
    addEntity(quadTree);
    d.addTrace(()->count+"");
  }
  @Override
  public void render(float delta) {
    super.render(delta);
    if(Gdx.input.isTouched()) {
      if(!touch) touch=true;
      else return;
      if(touch) {
        Box b=new Box();
        b.pos.set(Gdx.input.getX(),Resource.height-Gdx.input.getY());
        quadTree.add(b);
        addEntity(b);
        count=0;
        for(Box bo:quadTree.get(b)) {
          bo.c=Color.BLACK;
          count++;
        }
      }
    }else {
      touch=false;
    }
    // TODO: Implement this method
  }
  class Box extends BasicEntity{
    Color c=Color.WHITE;
    float time=0;
    static Texture text=Resource.asset.get("pao.png");
    public Box() {
      size.set(50,50);
    }
    @Override
    public void dispose() {}
    @Override
    public void update(float delta) {
      if(c!=Color.WHITE) {
        if((time+=delta)>1) {
          time=0;
          c=Color.WHITE;
        }
      }
    }
    @Override
    public void render(SpriteBatch batch) {
      batch.setColor(c);
      batch.draw(text,pos.x,pos.y,size.x,size.y);
      batch.setColor(Color.WHITE);
    }
    @Override
    public void debugDraw(ShapeRenderer sr) {}
  }
}
