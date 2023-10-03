package org.eclipse.tm4e.core.model;

import java.util.List;

import org.eclipse.tm4e.core.internal.utils.AbstractListeners;
import org.eclipse.tm4e.core.internal.utils.StringUtils;

public class ModelTokensChangedEvent{
  public interface Listenable{
    boolean addModelTokensChangedListener(Listener listener);
    boolean removeModelTokensChangedListener(Listener listener);
  }
  public interface Listener{
    void onModelTokensChanged(ModelTokensChangedEvent event);
  }
  public static class Listeners extends AbstractListeners<Listener,ModelTokensChangedEvent>{
    @Override
    public void dispatchEvent(final ModelTokensChangedEvent event,final Listener listener) {
      listener.onModelTokensChanged(event);
    }
    public void dispatchEvent(final List<Range> ranges,final ITMModel model) {
      if(ranges.isEmpty()) return;
      dispatchEvent(new ModelTokensChangedEvent(ranges,model));
    }
  }
  public final List<Range> ranges;
  public final ITMModel model;
  public ModelTokensChangedEvent(final List<Range> ranges,final ITMModel model) {
    this.ranges=ranges;
    this.model=model;
  }
  public ModelTokensChangedEvent(final Range range,final ITMModel model) {
    this(List.of(range),model);
  }
  @Override
  public String toString() {
    return StringUtils.toString(this,sb-> {
      sb.append("ranges=").append(ranges).append(", ");
      sb.append("model=").append(model);
    });
  }
}
