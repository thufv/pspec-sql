package edu.thu.ss.lang.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;
import edu.thu.ss.lang.pojo.DataActionPair;
import edu.thu.ss.lang.pojo.Desensitization;
import edu.thu.ss.lang.pojo.Parsable;
import edu.thu.ss.lang.pojo.Restriction;

public class XMLRestriction implements Parsable {

	private Set<XMLDesensitization> desensitizations;

	private boolean forbid = false;

	public void parse(Node resNode) {
		NodeList list = resNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				XMLDesensitization d = new XMLDesensitization();
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

	public Restriction toRestriction(DataActionPair[] pairs) {
		Restriction res = null;
		if (forbid) {
			res = new Restriction(true);
		} else {
			Set<Desensitization> set = new HashSet<>();
			for (XMLDesensitization de : desensitizations) {
				set.add(de.toDesensitization(pairs));
			}
			res = new Restriction(set);
		}

		return res;
	}

	public void setForbid(boolean forbid) {
		this.forbid = forbid;
	}

	public boolean isForbid() {
		return forbid;
	}

	public Set<XMLDesensitization> getDesensitizations() {
		return desensitizations;
	}

	public XMLDesensitization getDesensitization() {
		return desensitizations.iterator().next();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Restriction: ");
		if (forbid) {
			sb.append("forbid");
		} else {
			for (XMLDesensitization de : desensitizations) {
				sb.append("{");
				sb.append(de);
				sb.append("} ");
			}
		}
		return sb.toString();
	}
}
