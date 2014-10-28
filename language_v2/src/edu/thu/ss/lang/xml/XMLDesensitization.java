package edu.thu.ss.lang.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.lang.parser.ParserConstant;
import edu.thu.ss.lang.pojo.Desensitization;
import edu.thu.ss.lang.pojo.DesensitizeOperation;
import edu.thu.ss.lang.pojo.Parsable;

public class XMLDesensitization extends Desensitization implements Parsable {
	protected Set<XMLObjectRef> objRefs = new HashSet<>();
	protected Set<XMLDataCategoryRef> dataRefs = new HashSet<>();

	public Set<XMLObjectRef> getObjRefs() {
		return objRefs;
	}

	public Set<XMLDataCategoryRef> getDataRefs() {
		return dataRefs;
	}

	public Desensitization toDesensitization() {
		Desensitization de = new Desensitization();
		if (operations != null) {
			de.setOperations(new HashSet<>(operations));
		}
		if (this.datas != null) {
			de.setDatas(new HashSet<>(datas));
		}
		return de;
	}

	public void materialize() {
		this.datas = new HashSet<>();
		for (XMLDataCategoryRef ref : dataRefs) {
			this.datas.addAll(ref.getMaterialized());
		}
	}

	public void parse(Node deNode) {
		NodeList list = deNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				XMLObjectRef ref = new XMLObjectRef();
				ref.parse(node);
				objRefs.add(ref);
			} else if (ParserConstant.Ele_Policy_Rule_Desensitize_UDF.equals(name)) {
				DesensitizeOperation op = new DesensitizeOperation();
				op.parse(node);
				if (this.operations == null) {
					this.operations = new HashSet<>();
				}
				operations.add(op);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (dataRefs.size() > 0) {
			sb.append("data category: ");
			for (XMLDataCategoryRef ref : dataRefs) {
				sb.append(ref.getRefid());
				sb.append(' ');
			}
			sb.append('\t');
		}
		if (operations.size() > 0) {
			sb.append("operation: ");
			for (DesensitizeOperation op : operations) {
				sb.append(op.getUdf());
				sb.append(' ');
			}
		}
		return sb.toString();
	}
}
