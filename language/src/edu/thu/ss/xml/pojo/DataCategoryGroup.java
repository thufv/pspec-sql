package edu.thu.ss.xml.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public abstract class DataCategoryGroup {
	protected Set<ReferringObject> dataRefs = new HashSet<>();

	protected List<DataCategory> datas = new ArrayList<>();

	public Set<ReferringObject> getDataRefs() {
		return dataRefs;
	}

	public List<DataCategory> getDatas() {
		return datas;
	}

	public void parse(Node refNode) {
		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				ReferringObject obj = new ReferringObject();
				obj.parse(node);
				dataRefs.add(obj);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (ReferringObject ref : dataRefs) {
			sb.append(ref.refid);
			if (count++ < dataRefs.size() - 1) {
				sb.append(',');
			}
		}
		return sb.toString();
	}
}
