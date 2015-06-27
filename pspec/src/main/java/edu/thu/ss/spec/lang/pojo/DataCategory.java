package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.SetUtil;

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
	protected Set<DesensitizeOperation> ops = new HashSet<>();
	protected Map<String, DesensitizeOperation> opIndex = new HashMap<>();

	public Set<DesensitizeOperation> getOperations() {
		return ops;
	}

	public DesensitizeOperation getOperation(String op) {
		return opIndex.get(op.toLowerCase());
	}

	private void addOperation(DesensitizeOperation op) {
		ops.add(op);
		opIndex.put(op.name, op);
	}

	public void inheritDesensitizeOperation(DataContainer container) {
		if (parent != null) {
			for (DesensitizeOperation op : parent.ops) {
				this.addOperation(op);
			}
		}
		if (children != null) {
			for (DataCategory data : children) {
				if (container.contains(data.getId())) {
					data.inheritDesensitizeOperation(container);
				}
			}
		}
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
			sb.append(SetUtil.format(ops, " "));
		}
		return sb.toString();
	}
}
