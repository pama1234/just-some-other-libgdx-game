package hhs.gdx.hsgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.gdx.hsgame.tools.Resource;

public class Debug extends Table{
  public Label.LabelStyle style;
  GLProfiler profiler=new GLProfiler(Gdx.graphics);;
  StringBuilder sb=new StringBuilder();
  int lc,ldc;
  public Debug(Label.LabelStyle style) {
    this.style=style;
    setBounds(0,0,Resource.width/2,Resource.height);
    bottom().left();
    addTrace(()->"FPS:"+Gdx.graphics.getFramesPerSecond());
    addTrace(()->"Used memory:"+(Gdx.app.getJavaHeap()+Gdx.app.getNativeHeap())/1048567+"MB");
  }
  @Override
  public void act(float arg0) {
    super.act(arg0);
  }
  public void addDebugText() {
    profiler.enable();
    addTrace(
      ()-> {
        sb.setLength(0);
        float d=Gdx.graphics.getDeltaTime();
        int c=profiler.getCalls();
        int dc=profiler.getDrawCalls();
        sb.append("GL Calls: "+(c-lc)/d+"\n");
        sb.append("Draw calls: "+(dc-ldc)/d+"\n");
        sb.append("Shader switches: "+profiler.getShaderSwitches()+"\n");
        sb.append("Texture bindings: "+profiler.getTextureBindings()+"\n");
        sb.append("Vertex calls: "+profiler.getVertexCount().total+"\n");
        long memory=(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())
          /(1024*1024);
        sb.append("Used memory: "+memory+"MB\n");
        sb.append("FPS:"+Gdx.graphics.getFramesPerSecond());
        lc=c;
        ldc=dc;
        return sb.toString();
      });
  }
  public void addTrace(change c) {
    row();
    add(new UpdateLabel(c.getText(),style,c)).left();
  }
  public void addViewer(String str) {
    row();
    add(new Label(str,style));
  }
  public void addTrace(change... cs) {
    for(change c:cs) {
      addTrace(c);
    }
  }
  public interface change{
    public abstract String getText();
  }
  public static class UpdateLabel extends Label{
    change c;
    public UpdateLabel(String str,Label.LabelStyle style,change c) {
      super(str,style);
      this.c=c;
    }
    @Override
    public void act(float delta) {
      setText(c.getText());
      super.act(delta);
    }
  }
}
