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

public class Filter implements Parsable, Writable {

	private List<FilterNode> list = new ArrayList<>();
	private Set<DataRef> dataRefs = new HashSet<>();
	private boolean permitNull = false;

	
	public Set<DataRef> getDataRefs() {
		return dataRefs;
	}
	
	public List<FilterNode> getFilterNodes() {
		return this.list;
	}
	
	@Override
	public Element outputType(Document document, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(ParserConstant.Ele_Policy_Rule_Filter);
		if (this.list != null) {
			for (FilterNode pre : list) {
				element.appendChild(pre.outputElement(document));
			}
		}
		return element;
	}

	@Override
	public void parse(Node filnode) {
		NodeList list = filnode.getChildNodes();
		/*String attr = XMLUtil.getAttrValue(filnode, "permitNull");
		if (attr.equals("TRUE")) {
			permitNull = true;
		}
		*/
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_And.equals(name)) {
				FilterNode fNode = new FilterNode(name);
				fNode.parse(node);
				this.dataRefs.addAll(fNode.getDataRefs());
				this.list.add(fNode);
				break;
			}
			else if (ParserConstant.Ele_Policy_Rule_Or.equals(name)) {
				FilterNode fNode = new FilterNode(name);
				fNode.parse(node);
				this.dataRefs.addAll(fNode.getDataRefs());
				this.list.add(fNode);
				break;
			}
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Filter: ");
		if (list.size() != 0) {
			for (FilterNode pre : list) {
				sb.append("{");
				sb.append(pre);
				sb.append("} ");
			}
		}

		return sb.toString();
	}
}
