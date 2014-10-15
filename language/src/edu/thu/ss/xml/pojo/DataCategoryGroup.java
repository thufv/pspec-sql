package edu.thu.ss.xml.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public abstract class DataCategoryGroup {
	protected Set<DataCategoryRef> dataRefs = new HashSet<>();

	public Set<DataCategoryRef> getDataRefs() {
		return dataRefs;
	}

	public void parse(Node refNode) {
		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				DataCategoryRef obj = new DataCategoryRef();
				obj.parse(node);
				dataRefs.add(obj);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (DataCategoryRef ref : dataRefs) {
			sb.append(ref);
			if (count++ < dataRefs.size() - 1) {
				sb.append('\t');
			}
		}
		return sb.toString();
	}
}
