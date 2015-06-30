package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.SetUtil;

/**
 * class for data association
 * @author luochen
 *
 */
public class DataAssociation implements Parsable, Writable {

	protected List<DataRef> dataRefs = new ArrayList<>();

	public List<DataRef> getDataRefs() {
		return dataRefs;
	}

	public int getDimension() {
		return dataRefs.size();
	}

	public DataRef get(int i) {
		return dataRefs.get(i);
	}

	@Override
	public void parse(Node refNode) {
		NodeList list = refNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				DataRef obj = new DataRef();
				obj.parse(node);
				dataRefs.add(obj);
			}
		}
	}

	@Override
	public Element outputElement(Document document) {
		Element element = document.createElement(ParserConstant.Ele_Policy_Rule_DataAsscoation);
		for (DataRef ref : dataRefs) {
			Element refEle = ref.outputElement(document);
			element.appendChild(refEle);
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
		sb.append("Data Association: {");
		sb.append(SetUtil.format(dataRefs, ","));
		sb.append("}");
		return sb.toString();
	}

	public int getIndex(String refid) {
		for (int i = 0; i < dataRefs.size(); i++) {
			if (dataRefs.get(i).getRefid().equals(refid)) {
				return i;
			}
		}
		return -1;
	}

	public DataRef get(String refid) {
		for (DataRef ref : dataRefs) {
			if (ref.getRefid().equals(refid)) {
				return ref;
			}
		}
		return null;
	}
}
