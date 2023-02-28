package pama1234.util.wrapper;

import pama1234.util.entity.ServerEntity;

public class ServerLayeredEntityCenter extends ServerEntity{
  public ServerEntityCenter<?>[] list;
  public boolean reverseDisplay;
  public ServerLayeredEntityCenter(int size) {
    list=new ServerEntityCenter<?>[size];
  }
  @Override
  public void init() {
    for(ServerEntityCenter<?> i:list) i.init();
  }
  @Override
  public void update() {
    for(ServerEntityCenter<?> i:list) i.update();
  }
  @Override
  public void display() {
    if(reverseDisplay) for(int i=list.length-1;i>=0;i--) list[i].display();
    else for(ServerEntityCenter<?> i:list) i.display();
  }
  @Override
  public void pause() {
    for(ServerEntityCenter<?> i:list) i.pause();
  }
  @Override
  public void resume() {
    for(ServerEntityCenter<?> i:list) i.resume();
  }
  @Override
  public void dispose() {
    for(ServerEntityCenter<?> i:list) i.dispose();
  }
}
