package pama1234.gdx.game.duel.util.graphics;

import java.util.ArrayList;

import pama1234.app.game.server.duel.util.ObjectPool;
import pama1234.gdx.game.duel.Duel;

public final class ParticleSet{
  public final Duel duel;
  public final ArrayList<Particle> particleList;
  public final ArrayList<Particle> removingParticleList;
  public final ObjectPool<Particle> particlePool;
  public final ParticleBuilder builder;
  public ParticleSet(Duel duel,int capacity) {
    this.duel=duel;
    particlePool=new ObjectPool<Particle>(capacity);
    for(int i=0;i<capacity;i++) {
      particlePool.pool.add(new Particle(this.duel));
    }
    particleList=new ArrayList<Particle>(capacity);
    removingParticleList=new ArrayList<Particle>(capacity);
    builder=new ParticleBuilder(this.duel);
  }
  public void update() {
    particlePool.update();
    for(Particle eachParticle:particleList) {
      eachParticle.update();
    }
    if(removingParticleList.size()>=1) {
      for(Particle eachInstance:removingParticleList) {
        particlePool.deallocate(eachInstance);
      }
      particleList.removeAll(removingParticleList);
      removingParticleList.clear();
    }
  }
  public void display() {
    duel.beginBlend();
    for(Particle eachParticle:particleList) eachParticle.display();
    duel.endBlend();
  }
  public Particle allocate() {
    return particlePool.allocate();
  }
}