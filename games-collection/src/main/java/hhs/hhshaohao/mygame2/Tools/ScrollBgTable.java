package hhs.hhshaohao.mygame2.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ScrollBgTable extends Table{

  private static float f2396K=(((float)Gdx.graphics.getHeight())/((float)Gdx.graphics.getWidth()));

  private static int f2402Q=200;
  private static int f2403R=200;

  private static float direction=0.7f;

  private static float mapSX=direction;
  private static float mapSY=direction;

  private static float mapX=((float)f2402Q);
  private static float mapY=((float)f2403R);

  private static int mapW=830;
  private static int mapH=((int)(((float)mapW)*f2396K));

  private static float bgSpeed=25.0f;

  private static int f2413aa=1570;
  private static int f2414ab=1400;

  private final TextureRegion worldmap;
  private final TextureRegionDrawable worldmapDrawable;

  public ScrollBgTable() {
    //        ScrollBgTable worldmapTable = new ScrollBgTable();
    //        worldmapTable.setFillParent(true);
    //        stage.addActor(worldmapTable);

    Texture bg=new Texture(Gdx.files.internal("worldmap.png"));
    worldmap=new TextureRegion(bg,(int)mapX,(int)mapY,mapW,mapH);
    worldmapDrawable=new TextureRegionDrawable(worldmap);

    mapX=(float)f2402Q;
    mapY=(float)f2403R;
    worldmap.setRegion((int)mapX,(int)mapY,mapW,mapH);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    updateBg(delta);
    worldmap.scroll(mapSX/5000.0f,mapSY/5000.0f);
    worldmapDrawable.setRegion(worldmap);
    setBackground(worldmapDrawable);
  }

  private void updateBg(float f) {
    if(1.0f/f>=4.0f) {
      mapX+=bgSpeed*f*mapSX;
      mapY+=bgSpeed*f*mapSY;
      if(mapX+((float)mapW)>((float)f2413aa)) {
        mapSX=-direction;
      }
      if(mapY+((float)mapH)>((float)f2414ab)) {
        mapSY=-direction;
      }
      if(mapX<((float)f2402Q)) {
        mapSX=direction;
      }
      if(mapY<((float)f2403R)) {
        mapSY=direction;
      }
    }
  }

}
