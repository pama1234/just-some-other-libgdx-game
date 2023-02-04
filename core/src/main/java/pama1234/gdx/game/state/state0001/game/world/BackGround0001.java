package pama1234.gdx.game.state.state0001.game.world;

import com.badlogic.gdx.graphics.Texture;
import pama1234.gdx.game.state.state0001.game.player.MainPlayer;
import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.entity.Entity;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.info.TouchInfo;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.util.element.CameraController2D;

public class BackGround0001 extends Entity<Screen0011> {
    BackGroundCenter0001 pb;
    public float width,height,proportion=1f;
    MainPlayer mp;
    Texture bg;
    float x,y,tx,ty;
    CameraController2D cam;
    byte[] side[]={{-1,-1},{0,-1},{1,-1},
        {-1,0},{1,0},
        {-1,1},{0,1},{1,1}};
    public BackGround0001(Screen0011 s,BackGroundCenter0001 pb,MainPlayer player) {
        this(s,pb,player,null,5760,3240);
    }

    public BackGround0001(Screen0011 s,BackGroundCenter0001 pb,MainPlayer player,Texture bg,float w,float h) {
        super(s);
        this.pb=pb;
        width=w;
        height=h;
        mp=player;
        setBgTexture(bg);
        //li=ri=li=di=1;
        cam=p.cam2d;
        x=-width/2;
        y=height/2;
    } 
    public BackGround0001 setBgTexture(Texture t) {
        bg=t;
        return this;
    }
    @Override
    public void display() {
        pb.batch.draw(bg,x,y,width,height);
        for(int i = 0; i<8; i++) {
            tx=x;
            ty=y;
            tx+=side[i][0]*width;
            ty+=side[i][1]*height;
            pb.batch.draw(bg,tx,ty,width,height);
        }
        while(x-width>cam.x1()) {
            x-=width;
        }
        while(x+width<cam.x2()) {
            x+=width;
        }
        while(y-height>cam.y1()) {
            y-=height;
        }
        while(y+height<cam.y2()) {
            y+=height;
        }
        //x+=xp*width;
        //y+=yp*height;
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void init() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void frameMoved(int x,int y) {
    }

    @Override
    public void frameResized(int w,int h) {
    }

    @Override
    public void keyPressed(char key,int keyCode) {
    }

    @Override
    public void keyReleased(char key,int keyCode) {
    }

    @Override
    public void keyTyped(char key) {
    }

    @Override
    public void mouseDragged() {
    }

    @Override
    public void mouseMoved() {
    }

    @Override
    public void mousePressed(MouseInfo info) {
    }

    @Override
    public void mouseReleased(MouseInfo info) {
    }

    @Override
    public void mouseWheel(float x,float y) {
    }

    @Override
    public void touchEnded(TouchInfo info) {
    }

    @Override
    public void touchMoved(TouchInfo info) {
    }

    @Override
    public void touchStarted(TouchInfo info) {
    }

}
