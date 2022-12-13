package pama1234.gdx.game.app.app0002;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.ui.CodeTextFieldStyle;
import pama1234.gdx.game.ui.util.NormalOnscreenKeyboard;
import pama1234.gdx.game.ui.util.TextField;
import pama1234.gdx.game.util.RectF;
import pama1234.gdx.util.app.ScreenCore3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.info.MouseInfo;
import pama1234.gdx.util.listener.EntityListener;
import pama1234.math.UtilMath;

public class Screen0005 extends ScreenCore3D{
  public TextField textField;
  //---
  public Graphics gbackground;
  public ShaderProgram shader;
  public float[] idata=new float[7];
  @Override
  public void setup() {
    noStroke();
    // background=false;
    gbackground=new Graphics(this,width,height);
    ShaderProgram.pedantic=false;
    shader=new ShaderProgram(
      // Gdx.files.internal("shader/main0001/atmosphericScattering.vert"),
      imageBatch.getShader().getVertexShaderSource(),
      Gdx.files.internal("shader/temp.frag").readString());
      // Gdx.files.internal("shader/main0001/atmosphericScattering.frag").readString());
    // shader.bind();
    System.out.println(shader.getLog());
    font.load(0);
    textField=new TextField(shader.getLog().replace('\n',' '),new CodeTextFieldStyle(this),
      new RectF(()->u,()->u,()->width-u*2,()->pu),()->pus);
    textField.setOnscreenKeyboard(new NormalOnscreenKeyboard());
    stage.addActor(textField);
    // String[] ts=shader.getLog().split("\n");
    centerScreen.add.add(new EntityListener() {
      @Override
      public void display() {
        imageBatch.begin();
        imageBatch.setShader(shader);
        shaderUpdate();
        imageBatch.draw(gbackground.texture,0,0,width,height);
        imageBatch.end();
        imageBatch.setShader(null);
        // fill(255);
        // for(int i=0;i<ts.length;i++) text(ts[i],0,i*pu);
      }
    });
  }
  @Override
  public void update() {
    shaderUpdate();
  }
  public void shaderUpdate() {
    shader.setUniformf("iTime",frameCount/30f);
    // idata[0]=mouse.x;
    // idata[1]=mouse.y;
    // shader.setUniform4fv("iMouse",idata,0,4);
    // idata[0]=(cam3d.viewDir.x()-UtilMath.PI)*2;
    // idata[1]=(cam3d.viewDir.y()+UtilMath.PI)*2;
    idata[0]=(cam3d.viewDir.x()-UtilMath.PI);
    idata[1]=(cam3d.viewDir.y()+UtilMath.PI);
    // idata[0]=mouse.x/width;
    // idata[1]=mouse.y/height;
    shader.setUniform2fv("iCam",idata,0,2);
  }
  @Override
  public void mousePressed(MouseInfo info) {}
  @Override
  public void displayWithCam() {}
  @Override
  public void display() {}
  @Override
  public void frameResized() {
    gbackground.dispose();
    gbackground=new Graphics(this,width,height);
    imageBatch.begin();
    imageBatch.setShader(shader);
    idata[4]=width;
    idata[5]=height;
    idata[6]=1;
    shader.setUniform3fv("iResolution",idata,4,3);
    imageBatch.end();
  }
}
