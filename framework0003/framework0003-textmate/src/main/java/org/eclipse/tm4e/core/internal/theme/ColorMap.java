package org.eclipse.tm4e.core.internal.theme;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tm4e.core.TMException;

public final class ColorMap{
  private boolean _isFrozen;
  private int _lastColorId=-1; // -1 and not 0 as in upstream project on purpose
  private final List<String> _id2color=new ArrayList<>();
  private final Map<String,@Nullable Integer> _color2id=new LinkedHashMap<>();
  public ColorMap() {
    this(null);
  }
  public ColorMap(@Nullable final List<String> _colorMap) {
    if(_colorMap!=null) {
      this._isFrozen=true;
      for(int i=0,len=_colorMap.size();i<len;i++) {
        this._color2id.put(_colorMap.get(i),i);
        this._id2color.add(_colorMap.get(i));
      }
    }else {
      this._isFrozen=false;
    }
  }
  public int getId(@Nullable final String _color) {
    if(_color==null) {
      return 0;
    }
    final var color=_color.toUpperCase();
    Integer value=_color2id.get(color);
    if(value!=null) {
      return value;
    }
    if(this._isFrozen) {
      throw new TMException("Missing color in color map - "+color);
    }
    value=++this._lastColorId;
    _color2id.put(color,value);
    if(value>=_id2color.size()) {
      _id2color.add(color);
    }else {
      _id2color.set(value,color);
    }
    return value;
  }
  public List<String> getColorMap() {
    return new ArrayList<>(_color2id.keySet());
  }
  @Override
  public boolean equals(@Nullable final Object obj) {
    if(this==obj) return true;
    if(obj instanceof final ColorMap other) return _lastColorId==other._lastColorId
      &&_color2id.equals(other._color2id);
    return false;
  }
  @Override
  public int hashCode() {
    return 31*(31+_lastColorId)+_color2id.hashCode();
  }
}
