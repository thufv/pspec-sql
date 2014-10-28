package edu.thu.ss.xml.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class Restriction implements Parsable, Cloneable {

	private Set<Desensitization> desensitizations = new HashSet<Desensitization>();

	public void parse(Node resNode) {
		NodeList list = resNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				Desensitization d = new Desensitization();
				d.parse(node);
				desensitizations.add(d);
			}
		}
	}

	public Set<Desensitization> getDesensitizations() {
		return desensitizations;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Restriction: ");
		for (Desensitization de : desensitizations) {
			sb.append("desensitize: ");
			sb.append(de);
			sb.append('\t');
		}
		return sb.toString();
	}
}
