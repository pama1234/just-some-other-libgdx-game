package pama1234.gdx.game.duel.util.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pama1234.app.game.server.duel.util.ObjectPool;
import pama1234.gdx.game.duel.ClientGameSystem;
import pama1234.gdx.game.duel.Duel;

public final class ParticleSet{
  public final Duel p;
  public final ClientGameSystem core;
  public final List<Particle> particleList;
  public final LinkedList<Particle> removingParticleList;
  public final ObjectPool<Particle> particlePool;
  public final ParticleBuilder builder;
  public ParticleSet(Duel p,ClientGameSystem core,int capacity) {
    this.p=p;
    this.core=core;
    particlePool=new ObjectPool<>(capacity);
    for(int i=0;i<capacity;i++) {
      particlePool.pool.add(new Particle(this.p,core));
    }
    particleList=Collections.synchronizedList(new LinkedList<>());
    removingParticleList=new LinkedList<>();
    builder=new ParticleBuilder(this.p);
  }
  public void update() {
    particlePool.update();
    synchronized(particleList) {
      for(Particle eachParticle:particleList) {
        eachParticle.update();
      }
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
    synchronized(particleList) {
      for(Particle eachParticle:particleList) eachParticle.display();
    }
    //    particleList.forEach(Particle::display);
    p.endBlend();
  }
  public Particle allocate() {
    return particlePool.allocate();
  }
}