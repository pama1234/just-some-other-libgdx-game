package pama1234.gdx.game.sandbox.platformer.region;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock.TilemapRenderer;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;

public class TilemapRenderer0001 implements TilemapRenderer{
  public World0001 pw;
  public SpriteBatch batch;
  public ShaderProgram shader;
  public TilemapRenderer0001(World0001 pw,SpriteBatch tilemapBatch) {
    this.pw=pw;
    this.batch=tilemapBatch;
    shader=tilemapBatch.getShader();
    initInfo();
  }
  public void initInfo() {}
  public void updateInfo() {
    batch.setProjectionMatrix(pw.p.cam.camera.combined);
  }
  @Override
  public void tint(float r,float g,float b) {
    batch.setColor(r/255f,g/255f,b/255f,1);
  }
  @Override
  public void tile(TextureRegion in,boolean next) {}
  @Override
  public void tile(TextureRegion in,float x,float y) {
    batch.draw(in,x,y,pw.settings.blockWidth+0.01f,pw.settings.blockHeight+0.01f);
  }
  @Deprecated
  @Override
  public void tile(TextureRegion in,float x,float y,float w,float h) {
    batch.draw(in,x,y,w,h);
  }
  @Override
  public void begin() {
    batch.begin();
  }
  @Override
  public void end() {
    batch.end();
  }
}