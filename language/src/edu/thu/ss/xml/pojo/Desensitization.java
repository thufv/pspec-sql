package edu.thu.ss.xml.pojo;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class Desensitization {
	protected Set<DataCategoryRef> dataRefs = new HashSet<>();
	protected Set<DesensitizeOperation> operations = new HashSet<>();

	public Set<DataCategoryRef> getDataRefs() {
		return dataRefs;
	}

	public Set<DesensitizeOperation> getOperations() {
		return operations;
	}

	public boolean isDefaultOperation() {
		return operations.size() == 0;
	}

	public boolean isForAllDataCategory() {
		return dataRefs.size() == 0;
	}

	public void parse(Node deNode) {
		NodeList list = deNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				DataCategoryRef ref = new DataCategoryRef();
				ref.parse(node);
				dataRefs.add(ref);
			} else if (ParserConstant.Ele_Policy_Rule_Desensitize_UDF.equals(name)) {
				DesensitizeOperation op = new DesensitizeOperation();
				op.parse(node);
				operations.add(op);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (dataRefs.size() > 0) {
			sb.append("data category: ");
			for (DataCategoryRef ref : dataRefs) {
				sb.append(ref.getRefid());
				sb.append(' ');
			}
			sb.append('\t');
		}
		if (operations.size() > 0) {
			sb.append("operation: ");
			for (DesensitizeOperation op : operations) {
				sb.append(op.udf);
				sb.append(' ');
			}
		}
		return sb.toString();
	}

}
