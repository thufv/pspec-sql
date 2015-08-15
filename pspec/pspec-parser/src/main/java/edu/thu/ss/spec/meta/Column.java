package edu.thu.ss.spec.meta;

import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.Writable;
import edu.thu.ss.spec.meta.xml.MetaParserConstant;
import edu.thu.ss.spec.util.PSpecUtil;

public class Column extends DBObject implements Writable {

	protected BaseType type;

	protected boolean joinable = false;

	protected Integer multiplicity = null;

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
		column.setAttribute(MetaParserConstant.Attr_Name, name);
		//TODO output should be based on type
		if (type == null) {
			return null;
		} else if (type instanceof PrimitiveType) {
			PrimitiveType primitiveType = (PrimitiveType) type;
			DataCategory dataCategory = primitiveType.getDataCategory();
			if (dataCategory == null) {
				return null;
			} else {
				column.setAttribute(MetaParserConstant.Attr_Data_Category, dataCategory.getId());
				return column;
			}
		} else if (type instanceof CompositeType) {
			Element composite = document.createElement(MetaParserConstant.Ele_Composite);
			CompositeType compositeType = (CompositeType) type;
			if (compositeType.getAllTypes().entrySet().size() == 0) {
				return null;
			}
			for (Entry<String, BaseType> entry : compositeType.getAllTypes().entrySet()) {
				Element extract = document.createElement(MetaParserConstant.Ele_Composite_Extract);
				PrimitiveType subtype = (PrimitiveType) entry.getValue();
				extract.setAttribute(MetaParserConstant.Attr_Composite_Extract_Name, entry.getKey());
				extract.setAttribute(MetaParserConstant.Attr_Data_Category, subtype.getDataCategory().getId());
				composite.appendChild(extract);
			}
			column.appendChild(composite);
			return column;
		}
		return null;
	}
}
