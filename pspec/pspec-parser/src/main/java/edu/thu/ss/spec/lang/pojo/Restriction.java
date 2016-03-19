package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * class for restriction, contains multiple {@link Desensitization}
 * @author luochen
 *
 */
public class Restriction implements Parsable, Writable {

	private List<Desensitization> desensitizationList = new ArrayList<>();

	public Restriction() {
	}

	public List<Desensitization> getDesensitizations() {
		return desensitizationList;
	}

	public Desensitization getDesensitization(String dataRef) {
		for (Desensitization de : desensitizationList) {
			if (de.dataRefId.equals(dataRef)) {
				return de;
			}
		}
		return null;
	}

	public Desensitization getDesensitization(int i) {
		return desensitizationList.get(i);
	}

	public void setDesensitizationList(List<Desensitization> list) {
		this.desensitizationList = list;
	}

	@Override
	public Restriction clone() {
		Restriction res = new Restriction();
		for (Desensitization de : desensitizationList) {
			res.desensitizationList.add(de.clone());
		}

		return res;
	}

	@Override
	public void parse(Node resNode) {
		NodeList list = resNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Desensitize.equals(name)) {
				parseDesensitization(node);
			}
		}
	}

	public void parseDesensitization(Node deNode) {
		Set<String> dataRefIds = new HashSet<String>();
		Set<DesensitizeOperation> operations = new LinkedHashSet<>();
		NodeList list = deNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				String refid = XMLUtil.getAttrValue(node, ParserConstant.Attr_Refid);
				dataRefIds.add(refid);
			} else if (ParserConstant.Ele_Policy_Rule_Desensitize_Operation.equals(name)) {
				DesensitizeOperation op = DesensitizeOperation.parse(node);
				operations.add(op);
			}
		}
		if (dataRefIds.size() == 0) {
			Desensitization de = new Desensitization();
			de.setOperations(operations);
			this.desensitizationList.add(de);
		} else {
			for (String refId : dataRefIds) {
				Desensitization de = new Desensitization();
				de.setOperations(operations);
				de.setDataRefId(refId);
				this.desensitizationList.add(de);
			}
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(ParserConstant.Ele_Policy_Rule_Restriction);

		for (Desensitization de : desensitizationList) {
			if (de.effective()) {
				element.appendChild(de.outputElement(document));
			}
		}
		return element;
	}

	@Override
	public Element outputType(Document document, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Restriction: ");
		for (Desensitization de : desensitizationList) {
			sb.append("{");
			sb.append(de);
			sb.append("} ");
		}
		return sb.toString();
	}

}
