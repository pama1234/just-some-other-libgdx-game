package hhs.game.doomlibgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartGame extends ScreenAdapter{

  SpriteBatch batch; // Used to draw sprite and textures onto the screen
  ShapeRenderer sr;
  Pixmap pix;
  Texture tmp;
  MapRender mr;

  FitViewport viewport;
  OrthographicCamera camera;
  Stage st;
  int scale=3;
  float speed=7;
  boolean touch=false;

  char map[][];

  float px=11,py=3;
  float pa=0;
  float fov=3.14159265358979f/4;

  public StartGame(int map[][]) {
    this(mapTransform(map));
  }

  public StartGame(char map[][]) {
    batch=new SpriteBatch();
    st=new Stage();
    BitmapFont f=new BitmapFont();
    f.getData().scale(5);
    TextButton tb=new TextButton("walk",new TextButton.TextButtonStyle(null,null,null,f));
    tb.setPosition(200,100);
    tb.addListener(
      new InputListener() {
        @Override
        public boolean touchDown(InputEvent arg0,float arg1,float arg2,int arg3,int arg4) {
          // TODO: Implement this method
          touch=true;
          return true;
        }

        @Override
        public void touchUp(InputEvent event,float x,float y,int pointer,int button) {
          touch=false;
        }
      });
    st.addActor(tb);
    st.addActor(new DebugText());

    this.map=map;
    loop:
    for(int i=0;i<map.length;i++) {
      for(int j=0;j<map[i].length;j++) {
        if(map[i][j]!='#') {
          px=i;
          py=j;
          break loop;
        }
      }
    }

    mr=new MapRender(map);
    mr.setPosition(Gdx.graphics.getWidth()/2-mr.getWidth()/2,Gdx.graphics.getHeight()/2-mr.getHeight()/2);
    st.addActor(mr);
    /*
     * map =new int[][]
     * {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
     * {1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1},
     * {1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1},
     * {1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,0,0,0,0,1,1,1,1,1,0,0,0,0,0,1},
     * {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
     * {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
     */

    camera=new OrthographicCamera(340,120);
    camera.setToOrtho(false);

    viewport=new FitViewport(160*scale,90*scale,camera);
    viewport.apply();

    sr=new ShapeRenderer();

    pix=new Pixmap((int)viewport.getWorldWidth(),(int)viewport.getWorldHeight(),Pixmap.Format.RGBA8888);
    tmp=new Texture(pix);

    Gdx.input.setInputProcessor(st);
  }

  /* This method is being called once every frame */
  @Override
  public void render(float delta) {
    Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    Gdx.gl.glClearColor(1,1,1,1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

    camera.update();
    pix.setColor(Color.WHITE);
    pix.fill();
    //	sr.setProjectionMatrix(camera.combined);
    for(int i=0;i<viewport.getWorldWidth();i++) {
      float rayAngle=(pa-fov/2)+(float)i/viewport.getWorldWidth()*fov;
      float pd2w=RayCast(rayAngle);
      float nc=viewport.getWorldHeight()/2+viewport.getWorldHeight()/pd2w;
      float nf=viewport.getWorldHeight()-nc;
      for(int y=0;y<viewport.getWorldHeight();y++) {
        if(nf<y&&y<nc) {
          pix.setColor(Color.rgba8888(0,0,0,1-pd2w/16));
          pix.drawPixel(i,y);
        }
      }
    }
    tmp.draw(pix,0,0);
    //batch.setProjectionMatrix(camera.combined);
    batch.begin();
    batch.draw(tmp,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    batch.end();
    mr.up((int)px,(int)py);
    st.act();
    st.draw();

    pa+=Gdx.input.getDeltaX()*delta*.1f;
    if(touch) {
      if(RayCast(pa)>.1f) {
        px+=MathUtils.cos(pa)*speed*delta;
        py+=MathUtils.sin(pa)*speed*delta;
      }else {
        px-=MathUtils.cos(pa)*speed*delta;
        py-=MathUtils.sin(pa)*speed*delta;
      }
    }
  }

  public float RayCast(float angle) {
    float pd2w=0;
    float pex=MathUtils.cos(angle);
    float pey=MathUtils.sin(angle);

    boolean hitw=false;
    while(!hitw&&pd2w<16) {
      pd2w+=.01f;

      int tx=(int)(px+pd2w*pex);
      int ty=(int)(py+pd2w*pey);
      if(tx<0||tx>map[0].length||ty<0||ty>map.length) {
        hitw=true;
        pd2w=16;
      }else {
        if(map[tx][ty]=='#') {
          hitw=true;
        }
      }
    }
    return pd2w;
  }

  public static char[][] mapTransform(int map[][]) {
    char tmp[][]=new char[map.length][map[0].length];
    for(int i=0;i<map.length;i++) {
      for(int j=0;j<map[i].length;j++) {
        tmp[i][j]=map[i][j]==1?'#':' ';
      }
    }
    return tmp;
  }

  /* This method is being called whenever the application is destroyed */
  @Override
  public void dispose() {
    batch.dispose();
    pix.dispose();
  }
}