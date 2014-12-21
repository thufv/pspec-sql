package edu.thu.ss.spec.lang.pojo;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.util.SetUtil;

/**
 * class for desensitization, requires some data categories must be desensitized with
 * given operations.
 * @author luochen
 *
 */
public class Desensitization {

	protected String dataRefId;

	protected DataRef dataRef;

	/**
	 * materialized data categories referred in {@link #dataRef}
	 */
	protected Set<DataCategory> datas;

	/**
	 * required {@link DesensitizeOperation}s must be supported by all {@link DataCategory} in {@link #datas} 
	 */
	protected Set<DesensitizeOperation> operations;

	public Desensitization clone() {
		Desensitization de = new Desensitization();
		de.dataRefId = dataRefId;
		de.dataRef = dataRef;
		if (this.datas != null) {
			de.datas = new HashSet<>(this.datas);
		}
		if (this.operations != null) {
			de.operations = new LinkedHashSet<>(this.operations);
		}
		return de;
	}

	public void setOperations(Set<DesensitizeOperation> operations) {
		this.operations = operations;
	}

	public void setDataRef(DataRef dataRef) {
		this.dataRef = dataRef;
	}

	public DataRef getDataRef() {
		return dataRef;
	}

	public void setDataRefId(String dataRefId) {
		this.dataRefId = dataRefId;
	}

	public String getDataRefId() {
		return dataRefId;
	}

	public boolean isDefaultOperation() {
		return operations == null;
	}

	public Set<DesensitizeOperation> getOperations() {
		return operations;
	}

	public Set<DataCategory> getDatas() {
		return datas;
	}

	public void materialize() {
		this.datas = new HashSet<>();
		this.datas.addAll(dataRef.getMaterialized());
	}

	public void materialize(Set<DataCategory> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("operation: ");
		if (operations != null) {
			sb.append(SetUtil.format(operations, " "));
		} else {
			sb.append("default");
		}
		return sb.toString();
	}

}
