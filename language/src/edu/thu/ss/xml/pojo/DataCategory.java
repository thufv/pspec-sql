package edu.thu.ss.xml.pojo;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.parser.ParserConstant;

public class DataCategory extends HierarchicalObject {

	protected String udf;
	protected String clazz;

	@Override
	public void parse(Node dataNode) {
		super.parse(dataNode);

		NodeList list = dataNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_Op.equals(name)) {
				parseDesensitizeOp(node);
			}
		}
	}

	private void parseDesensitizeOp(Node opNode) {
		NodeList list = opNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Vocabulary_Desensitize_UDF.equals(name)) {
				this.udf = node.getTextContent();
			} else if (ParserConstant.Ele_Vocabulary_Desensitize_Class.equals(name)) {
				this.clazz = node.getTextContent();
			}

		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((udf == null) ? 0 : udf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataCategory other = (DataCategory) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (udf == null) {
			if (other.udf != null)
				return false;
		} else if (!udf.equals(other.udf))
			return false;
		return true;
	}

}
