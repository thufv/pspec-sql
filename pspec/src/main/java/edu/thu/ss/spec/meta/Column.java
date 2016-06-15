package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.meta.xml.MetaParserConstant;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.XMLUtil;

public class Column extends DBObject {

  protected BaseType type;

  protected boolean joinable = false;

  protected Integer multiplicity = null;

  protected Map<String, String> valueMap = null;
  
  public Integer getMultiplicity() {
    return multiplicity;
  }

  public boolean isJoinable() {
    return joinable;
  }

  public void setJoinable(boolean joinable) {
    this.joinable = joinable;
  }

  public void setMultiplicity(Integer multiplicity) {
    this.multiplicity = multiplicity;
  }

  public void setType(BaseType type) {
    this.type = type;
  }

  public BaseType getType() {
    return type;
  }

  public void parseValueMap(Node columnNode) {
	NodeList list = columnNode.getChildNodes();
	for (int i = 0; i < list.getLength(); i++) {
		Node node = list.item(i);
		String name = node.getLocalName();
		if (MetaParserConstant.Ele_Column_Value_Map.equals(name)) {
			String key = XMLUtil.getAttrValue(node, MetaParserConstant.Attr_Column_Map_Key);
			String value = XMLUtil.getAttrValue(node, MetaParserConstant.Attr_Column_Map_Value);
			if (this.valueMap == null) {
				this.valueMap = new HashMap<>();
			}
			this.valueMap.put(key, value);
		}
	}
  }
  public String toString(int l) {
    StringBuilder sb = new StringBuilder();
    sb.append(SetUtil.spaces(l));
    sb.append("Column: ");
    sb.append(name);
    sb.append(type.toString(l + 1));
    
    if (valueMap != null) {
		sb.append(" map: ");
		Iterator iter = valueMap.entrySet().iterator();
	    while (iter.hasNext()) {
	      Map.Entry entry = (Map.Entry) iter.next();
	      String key = (String) entry.getKey();
	      String val = (String) entry.getValue();
	      sb.append("<"+key+","+val+">");
	    }
    }
    
    if (sb.charAt(sb.length() - 1) != '\n') {
      sb.append('\n');
    }
    return sb.toString();
  }
}