package hhs.gdx.hsgame.screens;

import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.entities.EntityLayers;

public class LayersScreen extends BasicScreen{
  public EntityLayers layers=new EntityLayers();
  public LayersScreen() {
    addEntity(layers);
    layers.finlod();
  }
  @Override
  public void addEntity(Entity entity) {
    if(entity instanceof EntityLayers.Stackable) {
      layers.addEntity((BasicEntity)entity);
    }else super.addEntity(entity);
  }
  @Override
  public void removeEntity(Entity e) {
    if(e instanceof EntityLayers.Stackable se) {
      layers.removeEntity((BasicEntity)e);
    }else super.removeEntity(e);
  }
}
