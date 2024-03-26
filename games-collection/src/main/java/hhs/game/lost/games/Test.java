package hhs.game.lost.games;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;

/**
 * @Date 2023/08/07 13:01
 */
public class Test implements ApplicationListener{
  ModelBatch batch; //用于渲染模型
  PerspectiveCamera camera; //透视相机
  ModelInstance instance;
  @Override
  public void create() {
    batch=new ModelBatch();//初始化
    //初始化相机
    camera=new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    camera.near=1;//设置相机最近可以看到的距离
    camera.far=300;//设置相机最远可以看到的距离
    camera.position.set(20,20,20);//设置相机位置x,y,z
    camera.lookAt(0,0,0);//设置相机看向的位置
    camera.update();//更新相机
    ModelBuilder builder=new ModelBuilder();
    ColorAttribute attribute=ColorAttribute.createDiffuse(Color.BLUE);
    Material material=new Material(attribute);
    Model m=builder.createBox(10,10,10,material,VertexAttributes.Usage.Position);
    instance=new ModelInstance(m);
  }
  @Override
  public void dispose() {}

  @Override
  public void pause() {}
  @Override
  public void render() {
    Gdx.gl.glClearColor(0,0,0,0);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
    camera.update(); //相机更新
    batch.begin(camera);
    //渲染一个实例
    batch.render(instance);
    batch.end();
  }
  @Override
  public void resize(int p,int p1) {}
  @Override
  public void resume() {}
}
