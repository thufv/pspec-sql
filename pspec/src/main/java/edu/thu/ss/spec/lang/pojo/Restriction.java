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

	private List<Desensitization> list = new ArrayList<>();

	private Desensitization[] desensitizations;

	private Filter filter;
	
	private boolean forbid = false;
	private boolean isfilter = false;
	
	public void setForbid(boolean forbid) {
		this.forbid = forbid;
	}

	public void setIsFilter(boolean filter) {
		this.isfilter= filter;
	}
	
	public boolean isForbid() {
		return forbid;
	}

	public boolean isFilter() {
		return isfilter;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	public List<Desensitization> getList() {
		return list;
	}

	public void setDesensitizations(Desensitization[] desensitizations) {
		this.desensitizations = desensitizations;
	}

	public Desensitization[] getDesensitizations() {
		return desensitizations;
	}

	public Desensitization getDesensitization() {
		return desensitizations[0];
	}

	@Override
	public Restriction clone() {
		Restriction res = new Restriction();
		res.forbid = this.forbid;
		res.isfilter = this.isfilter;
		res.filter = this.filter;
		if (this.desensitizations != null) {
			res.desensitizations = new Desensitization[desensitizations.length];
			for (int i = 0; i < res.desensitizations.length; i++) {
				if (desensitizations[i] != null) {
					res.desensitizations[i] = desensitizations[i].clone();
				}
			}
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
			} else if (ParserConstant.Ele_Policy_Rule_Forbid.equals(name)) {
				forbid = true;
			} else if (ParserConstant.Ele_Policy_Rule_Filter.equals(name)) {
				isfilter = true;
				filter = new Filter();
				filter.parse(node);
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
			this.list.add(de);
		} else {
			for (String refId : dataRefIds) {
				Desensitization de = new Desensitization();
				de.setOperations(operations);
				de.setDataRefId(refId);
				this.list.add(de);
			}
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(ParserConstant.Ele_Policy_Rule_Restriction);

		if (this.forbid) {
			Element forbidEle = document.createElement(ParserConstant.Ele_Policy_Rule_Forbid);
			element.appendChild(forbidEle);
		} else {
			if (this.isfilter) {
				element.appendChild(filter.outputElement(document));
			}
			for (Desensitization de : desensitizations) {
				if (de != null) {
					element.appendChild(de.outputElement(document));
				}
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
		if (forbid) {
			sb.append("forbid");
		} 
		else {
			if (isfilter) {
				sb.append(filter);
			}
			if (desensitizations != null) {
				for (Desensitization de : desensitizations) {
					sb.append("{");
					sb.append(de);
					sb.append("} ");
				}
			}
		}
		return sb.toString();
	}
}
