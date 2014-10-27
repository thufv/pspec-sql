package edu.thu.ss.lang.pojo;

import org.w3c.dom.Node;

public class DesensitizeOperation implements Parsable {
	/**
	 * loaded implicitly from metastore
	 */
	protected String clazz;
	protected String udf;

	public void parse(Node opNode) {
		this.udf = opNode.getTextContent();
	}

	public String getUdf() {
		return udf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((udf == null) ? 0 : udf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DesensitizeOperation other = (DesensitizeOperation) obj;
		if (udf == null) {
			if (other.udf != null)
				return false;
		} else if (!udf.equals(other.udf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return udf;
	}

}
