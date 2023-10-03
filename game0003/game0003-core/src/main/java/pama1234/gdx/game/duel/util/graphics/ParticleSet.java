package pama1234.gdx.game.duel.util.graphics;

import java.util.ArrayList;

import pama1234.app.game.server.duel.util.ObjectPool;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;

public final class ParticleSet{
  public final Duel p;
  public final ClientGameSystem core;
  public final ArrayList<Particle> particleList;
  public final ArrayList<Particle> removingParticleList;
  public final ObjectPool<Particle> particlePool;
  public final ParticleBuilder builder;
  public ParticleSet(Duel p,ClientGameSystem core,int capacity) {
    this.p=p;
    this.core=core;
    particlePool=new ObjectPool<Particle>(capacity);
    for(int i=0;i<capacity;i++) {
      particlePool.pool.add(new Particle(this.p,core));
    }
    particleList=new ArrayList<Particle>(capacity);
    removingParticleList=new ArrayList<Particle>(capacity);
    builder=new ParticleBuilder(this.p);
  }
  public void update() {
    particlePool.update();
    for(Particle eachParticle:particleList) {
      eachParticle.update();
    }
    if(!removingParticleList.isEmpty()) {
      for(Particle eachInstance:removingParticleList) {
        particlePool.deallocate(eachInstance);
      }
      particleList.removeAll(removingParticleList);
      removingParticleList.clear();
    }
  }
  public void display() {
    p.beginBlend();
    for(Particle eachParticle:particleList) eachParticle.display();
    p.endBlend();
  }
  public Particle allocate() {
    return particlePool.allocate();
  }
}