package edu.thu.ss.spec.lang.pojo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * class for desensitization, requires some data categories must be desensitized with
 * given operations.
 * @author luochen
 *
 */
public class Desensitization implements Parsable {
	protected Set<String> dataRefIds = new HashSet<>();
	protected Set<DataRef> dataRefs = new HashSet<>();

	/**
	 * materialized data categories referred in {@link #dataRefs}
	 */
	protected Set<DataCategory> datas;

	/**
	 * required {@link DesensitizeOperation}s must be supported by all {@link DataCategory} in {@link #datas} 
	 */
	protected Set<DesensitizeOperation> operations;

	/**
	 * for single {@link DataRef}, {@link #dataIndex} is always {0}.
	 * otherwise, {@link DataAssociation} is considered as a array of {@link DataCategory},
	 * and {@link #dataIndex} contains index for applicable {@link DataCategory} in this {@link Desensitization}
	 */
	protected int[] dataIndex;

	public int[] getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int... index) {
		this.dataIndex = index;
	}

	public Desensitization clone() {
		Desensitization de = new Desensitization();
		de.dataRefIds = new HashSet<>(this.dataRefIds);
		de.dataRefs = new HashSet<>(this.dataRefs);
		if (this.datas != null) {
			de.datas = new HashSet<>(this.datas);
		}
		if (this.operations != null) {
			de.operations = new HashSet<>(this.operations);
		}
		if (this.dataIndex != null) {
			de.dataIndex = Arrays.copyOf(dataIndex, dataIndex.length);
		}
		return de;
	}

	public Set<String> getDataRefIds() {
		return dataRefIds;
	}

	public boolean isDefaultOperation() {
		return operations == null;
	}

	public Set<DesensitizeOperation> getOperations() {
		return operations;
	}

	public void setOperations(Set<DesensitizeOperation> operations) {
		this.operations = operations;
	}

	public Set<DataCategory> getDatas() {
		return datas;
	}

	public Set<DataRef> getDataRefs() {
		return dataRefs;
	}

	public void materialize() {
		this.datas = new HashSet<>();
		for (DataRef ref : dataRefs) {
			this.datas.addAll(ref.getMaterialized());
		}
	}

	public void materialize(Set<DataCategory> datas) {
		this.datas = datas;
	}

	@Override
	public void parse(Node deNode) {
		NodeList list = deNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				String refid = XMLUtil.getAttrValue(node, ParserConstant.Attr_Refid);
				this.dataRefIds.add(refid);
			} else if (ParserConstant.Ele_Policy_Rule_Desensitize_Operation.equals(name)) {
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
		sb.append("data category ref: ");
		for (int i : dataIndex) {
			sb.append(i);
			sb.append(' ');
		}
		sb.append('\t');
		sb.append("operation: ");
		if (operations != null) {
			sb.append(SetUtil.format(operations, " "));
		} else {
			sb.append("default");
		}
		return sb.toString();
	}

}
