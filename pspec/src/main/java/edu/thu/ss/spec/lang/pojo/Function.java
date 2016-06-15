package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public class Function implements Parsable, Writable  {

	private String name;
	private String type;
	private String value;
	private String refid;
	
	private Set<DataRef> dataRefs = new HashSet<>();
	
	private Function left;
	private Function right;
	
	public Set<DataRef> getDataRefs() {
		return dataRefs;
	}
	
	public Function getLeftFunction() {
		return this.left;
	}
	
	public Function getRightFunction() {
		return this.right;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public Element outputType(Document document, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(ParserConstant.Ele_Policy_Rule_Function);
		
		if (name == null || type == null) {
			return null;
		}
		
		element.setAttribute(ParserConstant.Attr_Function_Name, name);
		element.setAttribute(ParserConstant.Attr_Function_Type, type);
		
		if (name.equals("ret")) {
			if (ParserConstant.Attr_Function_Type_Value.equals(type)) {
				element.setTextContent(value);
			}
			else if (ParserConstant.Attr_Function_Type_DataRef.equals(type)) {
				Element refEle = document.createElement(ParserConstant.Ele_Policy_Rule_DataRef);
				refEle.setAttribute(ParserConstant.Attr_Refid, refid);
				element.appendChild(refEle);
			}
		}
		else {
			if (left != null) {
				element.appendChild(left.outputElement(document));
			}
			if (right != null) {
				element.appendChild(right.outputElement(document));
			}
		}

		return element;
	}

	@Override
	public void parse(Node fNode) {
		this.name = XMLUtil.getAttrValue(fNode, ParserConstant.Attr_Function_Name);
		this.type = XMLUtil.getAttrValue(fNode, ParserConstant.Attr_Function_Type);
		
		if (ParserConstant.Attr_Function_Type_Value.equals(type)) {
			value = fNode.getTextContent();
			return;
		}

		NodeList list = fNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Function.equals(name)
					&& ParserConstant.Attr_Function_Type_Function.equals(type)) {
				Function func = new Function();
				func.parse(node);
				if (left == null) {
					left = func;
					this.dataRefs.addAll(left.getDataRefs());
				}
				else if (right == null) {
					right = func;
					this.dataRefs.addAll(right.getDataRefs());
				}
			}
			else if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)
					&& ParserConstant.Attr_Function_Type_DataRef.equals(type)) {
				this.refid = XMLUtil.getAttrValue(node, ParserConstant.Attr_Refid);
				DataRef obj = new DataRef();
				obj.setRefid(refid);
				this.dataRefs.add(obj);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (name.equals("ret")) {
			if (ParserConstant.Attr_Function_Type_Value.equals(type)) {
				sb.append(value);
			}
			else if (ParserConstant.Attr_Function_Type_DataRef.equals(type)) {
				sb.append(refid);
			}
		}
		else {
			sb.append("(");
			sb.append(left);
			sb.append(name);
			sb.append(right);
			sb.append(")");
		}
		
		return sb.toString();
	}
}