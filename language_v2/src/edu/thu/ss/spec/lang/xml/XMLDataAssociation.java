package edu.thu.ss.spec.lang.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.Parsable;

public class XMLDataAssociation implements Parsable {
	protected Set<XMLDataCategoryRef> dataRefs = new HashSet<>();

	public Set<XMLDataCategoryRef> getDataRefs() {
		return dataRefs;
	}

	public void parse(Node refNode) {
		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				XMLDataCategoryRef obj = new XMLDataCategoryRef();
				obj.parse(node);
				dataRefs.add(obj);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Data Association: {");
		int count = 0;
		for (XMLDataCategoryRef ref : dataRefs) {
			sb.append(ref);
			if (count++ < dataRefs.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public XMLDataCategoryRef get(String refid) {
		for (XMLDataCategoryRef ref : dataRefs) {
			if (ref.getRefid().equals(refid)) {
				return ref;
			}
		}
		return null;
	}
}
