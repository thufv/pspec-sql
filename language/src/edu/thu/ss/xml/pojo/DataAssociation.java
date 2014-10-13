package edu.thu.ss.xml.pojo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class DataAssociation {
	protected List<ReferringObject> dataRefs;
	protected List<DataCategory> datas;

	public DataAssociation() {
		dataRefs = new ArrayList<>();
		datas = new ArrayList<>();
	}

	public void parse(Node associationNode) {
		NodeList list = associationNode.getChildNodes();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataRefs == null) ? 0 : dataRefs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataAssociation other = (DataAssociation) obj;
		if (dataRefs == null) {
			if (other.dataRefs != null)
				return false;
		} else if (!dataRefs.equals(other.dataRefs))
			return false;
		return true;
	}

}
