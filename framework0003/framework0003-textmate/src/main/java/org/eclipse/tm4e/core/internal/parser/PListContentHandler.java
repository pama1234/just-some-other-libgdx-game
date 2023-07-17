package org.eclipse.tm4e.core.internal.parser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.common.collect.Iterators;

final class PListContentHandler<T> extends DefaultHandler{
  // private static final Logger LOGGER=System.getLogger(PListContentHandler.class.getName());
  private static final class PListPathImpl implements PListPath{
    final LinkedList<String> keys=new LinkedList<>();
    final List<Integer> keysDepths=new ArrayList<>();
    int depth=0;
    void add(final String key) {
      trim();
      keysDepths.add(depth);
      keys.add(key);
    }
    void trim() {
      for(int i=keysDepths.size()-1;i>=0;i--) {
        if(keysDepths.get(i)>=depth) {
          keysDepths.remove(i);
          keys.remove(i);
        }
      }
    }
    @Override
    public String get(final int index) {
      return keys.get(index);
    }
    @Override
    public String first() {
      return keys.getFirst();
    }
    @Override
    public String last() {
      return keys.getLast();
    }
    @Override
    public Iterator<String> iterator() {
      return Iterators.unmodifiableIterator(keys.iterator());
    }
    @Override
    public int size() {
      return keys.size();
    }
    @Override
    public String toString() {
      return String.join("/",keys.toArray(String[]::new));
    }
  }
  private final class PListObject{
    @Nullable
    final PListObject parent;
    final Object values;
    PListObject(@Nullable final PListObject parent,final Object values) {
      this.parent=parent;
      this.values=values;
    }
    @SuppressWarnings("unchecked")
    void addValue(final Object value) {
      if(values instanceof final PropertySettable propertySettable) {
        propertySettable.setProperty(path.last(),value);
      }else if(values instanceof final List list) {
        list.add(value);
      }
    }
  }
  @Nullable
  private PListObject currObject;
  @Nullable
  private T result;
  private final PropertySettable.Factory<PListPath> objectFactory;
  private final PListPathImpl path=new PListPathImpl();
  private final StringBuilder text=new StringBuilder();
  PListContentHandler(final PropertySettable.Factory<PListPath> objectFactory) {
    this.objectFactory=objectFactory;
  }
  @SuppressWarnings("unchecked")
  @Override
  public void startElement(@Nullable final String uri,@Nullable final String localName,@Nullable final String qName,
    @Nullable final Attributes attributes) throws SAXException {
    assert localName!=null;
    path.depth++;
    switch(localName) {
      case "dict":
        if(result==null) {
          result=(T)objectFactory.create(path);
          currObject=new PListObject(currObject,result);
        }else {
          currObject=new PListObject(currObject,objectFactory.create(path));
        }
        break;
      case "array":
        if(result==null) {
          result=(T)new ArrayList<>();
          currObject=new PListObject(currObject,result);
        }else {
          currObject=new PListObject(currObject,new ArrayList<>());
        }
        break;
    }
    text.setLength(0);
  }
  @Override
  public void endElement(@Nullable final String uri,@Nullable final String localName,@Nullable final String qName) throws SAXException {
    assert localName!=null;
    final var currObject=this.currObject;
    if(currObject==null) {
      throw new SAXException("Root <plist><dict> or <plist><array> element not found!");
    }
    path.trim();
    path.depth--;
    switch(localName) {
      case "key":
        if(!(currObject.values instanceof PropertySettable)) {
          System.out.println("<key> tag can only be used inside an open <dict> element");
          break;
        }
        path.add(text.toString());
        break;
      case "array","dict":
        final var parent=currObject.parent;
        if(parent!=null) {
          parent.addValue(currObject.values);
          this.currObject=parent;
        }
        break;
      case "data","string":
        currObject.addValue(text.toString());
        break;
      case "date": // e.g. <date>2007-10-25T12:36:35Z</date>
        try {
          currObject.addValue(ZonedDateTime.parse(text.toString()));
        }catch(final DateTimeParseException ex) {
          System.out.println("Failed to parse date '"+text+"'. "+ex);
        }
        break;
      case "integer":
        try {
          currObject.addValue(Integer.parseInt(text.toString()));
        }catch(final NumberFormatException ex) {
          System.out.println("Failed to parse integer '"+text+"'. "+ex);
        }
        break;
      case "real":
        try {
          currObject.addValue(Float.parseFloat(text.toString()));
        }catch(final NumberFormatException ex) {
          System.out.println("Failed to parse real as float '"+text+"'. "+ex);
        }
        break;
      case "true":
        currObject.addValue(Boolean.TRUE);
        break;
      case "false":
        currObject.addValue(Boolean.FALSE);
        break;
      case "plist":
        break;
      default:
        System.out.println("Invalid tag name: "+localName);
    }
  }
  @Override
  public void characters(final char @Nullable [] ch,final int start,final int length) throws SAXException {
    text.append(ch,start,length);
  }
  void characters(final String chars) {
    text.append(chars);
  }
  public T getResult() {
    assert result!=null;
    return result;
  }
}
