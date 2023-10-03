package pama1234.gdx.game.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.decals.Decal;

import pama1234.game.app.server.server0001.game.particle.aparapi.CellGroup3D;
import pama1234.gdx.game.util.ParticleScreen3D;
import pama1234.gdx.util.element.Graphics;
import pama1234.gdx.util.entity.Entity;

public class ParticleWorldBase3D extends Entity<ParticleScreen3D>{
  public static class GraphicsData{
    public Graphics g;
    public TextureRegion tr;
    public GraphicsData(Graphics g,TextureRegion tr) {
      this.g=g;
      this.tr=tr;
    }
  }
  public static class DecalData{//TODO
    public Decal decal;
    public int layer;
    public DecalData(Decal g,int layer) {
      this.decal=g;
      this.layer=layer;
    }
  }
  public CellGroup3D group;
  public ParticleWorldBase3D(ParticleScreen3D p) {
    super(p);
  }
}
