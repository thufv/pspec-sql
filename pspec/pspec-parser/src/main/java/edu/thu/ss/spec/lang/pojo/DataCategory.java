package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.PSpecUtil;

/**
 * class for data category
 * @author luochen
 *
 */
public class DataCategory extends Category<DataCategory> {

	public DataCategory() {
	}

	public DataCategory(String id, String containerId) {
		this.id = id;
		this.containerId = containerId;
	}

	/**
	 * all supported {@link DesensitizeOperation} for the data category
	 */
	protected Set<DesensitizeOperation> ops = new LinkedHashSet<>();
	protected Map<String, DesensitizeOperation> opIndex = new HashMap<>();

	public Set<DesensitizeOperation> getOperations() {
		return ops;
	}

	public Set<DesensitizeOperation> getAllOperations() {
		Set<DesensitizeOperation> set = new LinkedHashSet<>();
		getAllOperations(set);
		return set;
	}

	private void getAllOperations(Set<DesensitizeOperation> set) {
		if (parent != null) {
			parent.getAllOperations(set);
		}
		for (DesensitizeOperation op : ops) {
			set.add(op);
		}
	}

	public DesensitizeOperation getOperation(String op) {
		op = op.toLowerCase();
		DesensitizeOperation operation = opIndex.get(op);
		if (operation == null && parent != null) {
			operation = parent.getOperation(op);
		}
		return operation;
	}

	public DesensitizeOperation directGetOperation(String op) {
		return opIndex.get(op.toLowerCase());
	}

	public void addOperation(DesensitizeOperation op) {
		ops.add(op);
		opIndex.put(op.name, op);
	}

	@Override
	public void parse(Node dataNode) {
		super.parse(dataNode);

		NodeList list = dataNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_Ops.equals(name)) {
				parseOperations(node);
			}
		}
	}

	private void parseOperations(Node deNode) {
		NodeList list = deNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_Op.equals(name)) {
				DesensitizeOperation op = DesensitizeOperation.parse(node);
				addOperation(op);
			}
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = super.outputType(document, ParserConstant.Ele_Vocabulary_Data_Category);
		if (this.ops != null && this.ops.size() > 0) {
			Element opsEle = document.createElement(ParserConstant.Ele_Vocabulary_Desensitize_Ops);
			element.appendChild(opsEle);
			for (DesensitizeOperation op : ops) {
				Element opEle = document.createElement(ParserConstant.Ele_Vocabulary_Desensitize_Op);
				opEle.appendChild(document.createTextNode(op.name));
				opsEle.appendChild(opEle);
			}
		}
		return element;
	}

	@Override
	public String toString() {
		return containerId + ":" + id;
	}

	@Override
	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toFullString());
		if (ops.size() > 0) {
			sb.append("\tdesensitize UDF: ");
			sb.append(PSpecUtil.format(ops, " "));
		}
		return sb.toString();
	}

	public boolean support(DesensitizeOperation op) {
		return getOperation(op.name) != null;
	}

	public void removeOperation(DesensitizeOperation desensitizeOperation) {
		ops.remove(desensitizeOperation);
		opIndex.remove(desensitizeOperation.name);
	}
}
