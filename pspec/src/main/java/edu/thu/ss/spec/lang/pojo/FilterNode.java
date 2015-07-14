package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public class FilterNode implements Parsable, Writable {
	
	private List<FilterNode> flist = new ArrayList<>();
	private List<Predicate> plist = new ArrayList<>();
	private Set<DataRef> dataRefs = new HashSet<>();
	private String NodeType= null;
	private boolean value = true;
	
	public FilterNode(String nType) {
		this.NodeType = nType;
	}

	public Set<DataRef> getDataRefs() {
		return dataRefs;
	}
	
	public List<FilterNode> getFilterNodes() {
		return this.flist;
	}
	
	public List<Predicate> getPredicates() {
		return this.plist;
	}
	
	public String getNodeType() {
		return this.NodeType;
	}
	
	public boolean getValue() {
		return this.value;
	}
	@Override
	public void parse(Node filNode) {
		String value = XMLUtil.getAttrValue(filNode, ParserConstant.Attr_Policy_Filter_Value);
		if (value != null && value.equals("false")) {
			this.value = false;
		}
		
		NodeList list = filNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Predicate.equals(name)) {
				Predicate pre = new Predicate();
				pre.parse(node);
				this.dataRefs.addAll(pre.getDataRefs());
				plist.add(pre);
			}
			else if (ParserConstant.Ele_Policy_Rule_And.equals(name)
					|| ParserConstant.Ele_Policy_Rule_Or.equals(name)){
				FilterNode fNode = new FilterNode(name);
				fNode.parse(node);
				this.dataRefs.addAll(fNode.getDataRefs());
				this.flist.add(fNode);
			}
		}
}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (plist.size() != 0) {
			for (Predicate pre : plist) {
				sb.append("{");
				sb.append(pre);
				sb.append("}");
				sb.append(this.NodeType);
			}
		}
		if (flist.size() != 0){
			for (FilterNode pre : flist) {
				if (pre.value == false) {
					sb.append("!");
				}
				sb.append("{");
				sb.append(pre);
				sb.append("}");
				sb.append(this.NodeType);
			}
		}
		if (sb.substring(sb.length() - this.NodeType.length(), sb.length()).equals(this.NodeType)) {
			sb.delete(sb.length() - this.NodeType.length(), sb.length());
		}
		sb.substring(sb.length());
		return sb.toString();
	}

	@Override
	public Element outputType(Document document, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(this.NodeType);
		
		for (FilterNode fNode : this.flist) {
			element.appendChild(fNode.outputElement(document));
		}
		
		for (Predicate pre : this.plist) {
			element.appendChild(pre.outputElement(document));
		}

		return element;
	}
	
}
