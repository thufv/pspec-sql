package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.PSpecUtil;
import edu.thu.ss.spec.util.Profiling;

/**
 * class for data association
 * @author luochen
 *
 */
public class DataAssociation implements Parsable, Writable {

	protected List<DataRef> dataRefs = new ArrayList<>();

	protected AssociatedDataAccess[] adas = null;

	//only used for building adas
	protected int adasIndex = 0;

	public List<DataRef> getDataRefs() {
		return dataRefs;
	}

	public int getDimension() {
		return dataRefs.size();
	}

	public DataRef get(int i) {
		return dataRefs.get(i);
	}

	public void setDataRefs(List<DataRef> dataRefs) {
		this.dataRefs = dataRefs;
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
		sb.append(PSpecUtil.format(dataRefs, ","));
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

	public AssociatedDataAccess[] getAssociatedDataAccesses() {
		buildAssociatedDataAccesses();
		return adas;
	}

	private void buildAssociatedDataAccesses() {
		//init
		adasIndex = 0;

		int totalNum = 1;
		for (DataRef ref : dataRefs) {
			totalNum = totalNum * ref.daNum();
		}
		adas = new AssociatedDataAccess[totalNum];

		buildAssociatedDataAccesses(new DataAccess[dataRefs.size()], 0);

	}

	private void buildAssociatedDataAccesses(DataAccess[] tmpAdas, int refIndex) {
		if (refIndex == dataRefs.size()) {
			DataAccess[] array = new DataAccess[tmpAdas.length];
			System.arraycopy(tmpAdas, 0, array, 0, tmpAdas.length);
			adas[adasIndex++] = new AssociatedDataAccess(array);
		} else {
			DataRef ref = dataRefs.get(refIndex);
			for (DataCategory data : ref.materialized) {
				if (ref.action.ancestorOf(Action.Output)) {
					tmpAdas[refIndex] = new DataAccess(Action.Output, data);
					buildAssociatedDataAccesses(tmpAdas, refIndex + 1);
				}
				if (ref.action.ancestorOf(Action.Condition)) {
					tmpAdas[refIndex] = new DataAccess(Action.Condition, data);
					buildAssociatedDataAccesses(tmpAdas, refIndex + 1);
				}
			}
		}
	}

}
