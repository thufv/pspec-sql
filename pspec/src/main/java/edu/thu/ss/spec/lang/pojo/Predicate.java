package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public class Predicate implements Parsable, Writable {

	private boolean value = true;
	private Set<DataRef> dataRefs = new HashSet<>();
	protected String operator = null;
	protected Function left = null;
	protected Function right = null;
	
	public Set<DataRef> getDataRefs() {
		return dataRefs;
	}
	
	public Function getLeftFunction() {
		return this.left;
	}
	
	public Function getRightFunction() {
		return this.right;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	@Override
	public Element outputType(Document document, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(ParserConstant.Ele_Policy_Rule_Predicate);
		
		if (operator != null) {
			Element oprEle = document.createElement(ParserConstant.Ele_Policy_Rule_Operator);
			oprEle.setTextContent(operator);
			element.appendChild(oprEle);
		}
		if (left != null) {
			element.appendChild(left.outputElement(document));
		}
		if (right != null) {
			element.appendChild(right.outputElement(document));
		}
		
		return element;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append("Predicate: ");
		if (value == false) {
			sb.append("!");
		}
		sb.append("[");
		sb.append(left);
		sb.append(" ");
		sb.append(operator);
		sb.append(" ");
		sb.append(right);
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public void parse(Node pNode) {
		// TODO Auto-generated method stub
		String value = XMLUtil.getAttrValue(pNode, ParserConstant.Attr_Policy_Filter_Value);
		if (value != null && value.equals("false")) {
			this.value = false;
		}
		NodeList list = pNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Operator.equals(name)) {
				operator = node.getTextContent();
			}
			else if (ParserConstant.Ele_Policy_Rule_Function.equals(name)) {
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
		}
	}
}
