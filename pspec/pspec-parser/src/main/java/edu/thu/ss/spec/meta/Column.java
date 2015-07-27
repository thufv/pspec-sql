package edu.thu.ss.spec.meta;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.Writable;
import edu.thu.ss.spec.meta.xml.MetaParserConstant;
import edu.thu.ss.spec.util.PSpecUtil;

public class Column extends DBObject implements Writable {

  protected BaseType type;

  protected boolean joinable = false;

  protected Integer multiplicity = null;
  
  protected Map<String, String> extraction = new HashMap<String, String>();
  
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
  
  public void addExtraction(String extractionName, String label) {
  	extraction.remove(extractionName);
  	extraction.put(extractionName, label);
  }
  
  public Map<String, String> getExtraction() {
  	return extraction;
  }

  public String toString(int l) {
    StringBuilder sb = new StringBuilder();
    sb.append(PSpecUtil.spaces(l));
    sb.append("Column: ");
    sb.append(name);
    sb.append(type.toString(l + 1));
    if (sb.charAt(sb.length() - 1) != '\n') {
      sb.append('\n');
    }
    return sb.toString();
  }

	@Override
	public Element outputType(Document document, String name) {
		return null;
	}

	@Override
	public Element outputElement(Document document) {
		Element column = document.createElement(MetaParserConstant.Ele_Column);
		boolean isLabel = false;
		for (String extractionName : extraction.keySet()) {
			if (extractionName.equals("Default")) {
				column.setAttribute(MetaParserConstant.Attr_Data_Category, extraction.get(extractionName));
				isLabel = true;
			} else {
				Element extractOp= document.createElement(MetaParserConstant.Ele_Composite_Extract);
				extractOp.setAttribute(MetaParserConstant.Attr_Data_Category, extraction.get(extractionName));
				extractOp.setAttribute(MetaParserConstant.Attr_Name, extractionName);
				column.appendChild(extractOp);
				isLabel = true;
			}
		}
		column.setAttribute(MetaParserConstant.Attr_Name, name);
		if (isLabel) {
			return column;
		} else {
			return null;
		}
		
	}
}
