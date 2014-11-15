package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;

public class DataCategory extends HierarchicalObject<DataCategory> implements Comparable<DataCategory> {

	protected Set<DesensitizeOperation> ops = new HashSet<>();
	protected Map<String, DesensitizeOperation> opIndex = new HashMap<>();

	public Set<DesensitizeOperation> getOperations() {
		return ops;
	}

	public DesensitizeOperation getOperation(String op) {
		return opIndex.get(op);
	}

	@Override
	public int compareTo(DataCategory o) {
		return Integer.compare(this.label, o.label);
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
				DesensitizeOperation op = new DesensitizeOperation();
				op.parse(node);
				addOperation(op);
			}
		}
	}

	private void addOperation(DesensitizeOperation op) {
		ops.add(op);
		opIndex.put(op.name, op);
	}

	public void inheritDesensitizeOperation() {
		if (parent != null) {
			this.ops.addAll(parent.ops);
		}
		if (children != null) {
			for (DataCategory data : children) {
				data.inheritDesensitizeOperation();
			}
		}
	}

	public String toString() {
		return super.toString();
	}

	public String toFullString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toFullString());
		if (ops.size() > 0) {
			sb.append("\tdesensitize UDF: ");
			for (DesensitizeOperation op : ops) {
				sb.append(op.name);
				sb.append(' ');
			}
		}
		return sb.toString();
	}
}
