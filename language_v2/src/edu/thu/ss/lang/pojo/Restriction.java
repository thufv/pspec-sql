package edu.thu.ss.lang.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;

public class Restriction implements Parsable {

	private Set<Desensitization> desensitizations;

	private boolean forbid = false;

	public void parse(Node resNode) {
		NodeList list = resNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				Desensitization d = new Desensitization();
				d.parse(node);
				if (desensitizations == null) {
					desensitizations = new HashSet<>();
				}
				desensitizations.add(d);
			} else if (ParserConstant.Ele_Policy_Rule_Forbid.equals(name)) {
				forbid = true;
			}
		}
	}

	public boolean isForbid() {
		return forbid;
	}

	public Set<Desensitization> getDesensitizations() {
		return desensitizations;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Restriction: ");
		if (forbid) {
			sb.append("forbid");
		} else {
			for (Desensitization de : desensitizations) {
				sb.append("{");
				sb.append(de);
				sb.append("} ");
			}
		}
		return sb.toString();
	}
}
