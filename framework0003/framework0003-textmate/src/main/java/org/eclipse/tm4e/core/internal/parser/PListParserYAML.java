package org.eclipse.tm4e.core.internal.parser;

import static org.eclipse.tm4e.core.internal.utils.NullSafetyHelper.castNonNull;

import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.SAXException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

public final class PListParserYAML<T> implements PListParser<T>{
  private final PropertySettable.Factory<PListPath> objectFactory;
  public PListParserYAML(final PropertySettable.Factory<PListPath> objectFactory) {
    this.objectFactory=objectFactory;
  }
  @SuppressWarnings("unchecked")
  private void addListToPList(final PListContentHandler<T> pList,final List<Object> list) throws SAXException {
    pList.startElement(null,"array",null,null);
    for(final Object item:list) {
      if(item instanceof final List items) {
        addListToPList(pList,items);
      }else if(item instanceof final Map map) {
        addMapToPList(pList,map);
      }else {
        addStringToPList(pList,item.toString());
      }
    }
    pList.endElement(null,"array",null);
  }
  @SuppressWarnings("unchecked")
  private void addMapToPList(final PListContentHandler<T> pList,final Map<String,Object> map) throws SAXException {
    pList.startElement(null,"dict",null,null);
    for(final Entry<String,Object> entry:map.entrySet()) {
      pList.startElement(null,"key",null,null);
      pList.characters(entry.getKey());
      pList.endElement(null,"key",null);
      if(entry.getValue() instanceof final List list) {
        addListToPList(pList,list);
      }else if(entry.getValue() instanceof final Map valueMap) {
        addMapToPList(pList,valueMap);
      }else {
        addStringToPList(pList,castNonNull(entry.getValue()).toString());
      }
    }
    pList.endElement(null,"dict",null);
  }
  private void addStringToPList(final PListContentHandler<T> pList,final String value) throws SAXException {
    pList.startElement(null,"string",null,null);
    pList.characters(value);
    pList.endElement(null,"string",null);
  }
  @Override
  public T parse(final Reader contents) throws SAXException,YAMLException {
    final var pList=new PListContentHandler<T>(objectFactory);
    pList.startElement(null,"plist",null,null);
    addMapToPList(pList,new Yaml().loadAs(contents,Map.class));
    pList.endElement(null,"plist",null);
    return pList.getResult();
  }
}
