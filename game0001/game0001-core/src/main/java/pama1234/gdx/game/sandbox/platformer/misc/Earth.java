package pama1234.gdx.game.sandbox.platformer.misc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import pama1234.gdx.util.app.UtilScreen;
import pama1234.gdx.util.app.UtilScreen3D;
import pama1234.gdx.util.entity.Entity;
import pama1234.math.UtilMath;
import pama1234.math.vec.Vec3f;

public class Earth extends Entity<UtilScreen3D>{
  public Vec3f pos;
  public Color darkGreen=UtilScreen.color(47,87,83);
  public Color green=UtilScreen.color(99,171,63);
  public float dist;
  public int divisions=16; // 初始细分数
  public float radius=10f; // 球体半径
  public ModelBuilder modelBuilder;
  public Model sphere;
  public ModelInstance sphereInstance;
  public Earth(UtilScreen3D p) {
    super(p);
    pos=new Vec3f(0,0,0);
    modelBuilder=new ModelBuilder();
    // 创建球体模型
    sphere=modelBuilder.createSphere(radius,radius,radius,divisions,divisions,
      new Material(ColorAttribute.createDiffuse(green)),
      VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal|VertexAttributes.Usage.TextureCoordinates);
    // 创建球体模型实例
    sphereInstance=new ModelInstance(sphere);
  }
  @Override
  public void update() {
    dist=p.cam.point.pos.dist(pos.x,pos.y,pos.z);
    if(dist>radius) {
      // 渲染循环中调整细分数和渲染球体
      float cameraDistance=dist/(radius*2);
      int newDivisions=UtilMath.constrain((int)(divisions/cameraDistance),8,128); // 根据距离计算新的细分数
      System.out.println(newDivisions);
      if(newDivisions!=divisions) {
        // 细分数发生变化，重新生成球体并更新模型实例
        divisions=newDivisions;
        sphere=modelBuilder.createSphere(radius,radius,radius,divisions,divisions,
          new Material(ColorAttribute.createDiffuse(green)),
          // ColorAttribute.createSpecular(darkGreen)),
          VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal|VertexAttributes.Usage.TextureCoordinates);
        sphereInstance=new ModelInstance(sphere);
      }
    }
  }
  @Override
  public void display() {
    p.modelBatch.begin(p.cam.camera);
    p.modelBatch.render(sphereInstance);
    p.modelBatch.end();
  }
}
