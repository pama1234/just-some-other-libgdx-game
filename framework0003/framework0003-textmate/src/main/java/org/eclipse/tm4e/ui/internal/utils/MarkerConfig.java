package org.eclipse.tm4e.ui.internal.utils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jdt.annotation.Nullable;

public abstract class MarkerConfig{
  public static Set<MarkerConfig> getDefaults() {
    final var defaults=new HashSet<MarkerConfig>();
    // problem markers:
    defaults.add(new ProblemMarkerConfig("ATTN",ProblemSeverity.INFO));
    defaults.add(new ProblemMarkerConfig("NOTE",ProblemSeverity.INFO));
    // task markers:
    defaults.add(new TaskMarkerConfig("BUG",TaskPriority.HIGH));
    defaults.add(new TaskMarkerConfig("FIXME",TaskPriority.HIGH));
    defaults.add(new TaskMarkerConfig("HACK",TaskPriority.NORMAL));
    defaults.add(new TaskMarkerConfig("OPTIMIZE",TaskPriority.NORMAL));
    defaults.add(new TaskMarkerConfig("TODO",TaskPriority.NORMAL));
    defaults.add(new TaskMarkerConfig("XXX",TaskPriority.NORMAL));
    return defaults;
  }
  public static final class ProblemMarkerConfig extends MarkerConfig{
    public ProblemMarkerConfig(final String tag,final ProblemSeverity severity) {
      super(tag,Type.PROBLEM);
      this.severity=severity;
    }
    public final ProblemSeverity severity;
    @Override
    public boolean equals(final @Nullable Object obj) {
      if(this==obj) return true;
      if(obj instanceof ProblemMarkerConfig other) return type==other.type
        &&severity==other.severity
        &&tag.equals(other.tag);
      return false;
    }
    @Override
    public int hashCode() {
      int result=31+severity.hashCode();
      result=31*result+tag.hashCode();
      return 31*result+type.hashCode();
    }
    @Override
    public String toString() {
      return "ProblemMarkerConfig [tag="+tag+", severity="+severity+"]";
    }
  }
  public enum ProblemSeverity{
    ERROR(IMarker.SEVERITY_ERROR),
    WARNING(IMarker.SEVERITY_WARNING),
    INFO(IMarker.SEVERITY_INFO);
    ProblemSeverity(final int value) {
      this.value=value;
    }
    public final int value;
  }
  public static final class TaskMarkerConfig extends MarkerConfig{
    public TaskMarkerConfig(final String tag,final TaskPriority priority) {
      super(tag,Type.TASK);
      this.priority=priority;
    }
    public final TaskPriority priority;
    @Override
    public boolean equals(final @Nullable Object obj) {
      if(this==obj) return true;
      if(obj instanceof TaskMarkerConfig other) return type==other.type
        &&priority==other.priority
        &&tag.equals(other.tag);
      return false;
    }
    @Override
    public int hashCode() {
      int result=31+priority.hashCode();
      result=31*result+tag.hashCode();
      return 31*result+type.hashCode();
    }
    @Override
    public String toString() {
      return "TaskMarkerConfig [tag="+tag+", priority="+priority+"]";
    }
  }
  public enum TaskPriority{
    HIGH(IMarker.PRIORITY_HIGH),
    NORMAL(IMarker.PRIORITY_NORMAL),
    LOW(IMarker.PRIORITY_LOW);
    TaskPriority(final int value) {
      this.value=value;
    }
    public final int value;
  }
  public enum Type{
    PROBLEM,
    TASK;
  }
  private MarkerConfig(final String tag,final Type type) {
    this.tag=tag;
    this.type=type;
  }
  public final String tag;
  public final Type type;
  public boolean isProblem() {
    return type==Type.PROBLEM;
  }
  public boolean isTask() {
    return type==Type.TASK;
  }
  public ProblemMarkerConfig asProblemMarkerConfig() {
    return (ProblemMarkerConfig)this;
  }
  public TaskMarkerConfig asTaskMarkerConfig() {
    return (TaskMarkerConfig)this;
  }
}
