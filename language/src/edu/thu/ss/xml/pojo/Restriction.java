package edu.thu.ss.xml.pojo;

import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class Restriction {
	public enum RestrictType {
		desensitize, aggregate
	}

	protected RestrictType type;

	protected Set<ReferringObject> dataRefs;

	protected List<DataCategory> datas;

	public void parse(Node resNode) {
		NodeList list = resNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				this.type = RestrictType.desensitize;
				parseDataRefs(node);
			} else if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				this.type = RestrictType.aggregate;
				parseDataRefs(node);
			}
		}
	}

	public void parseDataRefs(Node refNode) {
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

}
