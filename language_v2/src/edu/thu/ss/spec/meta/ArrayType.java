package edu.thu.ss.spec.meta;

import edu.thu.ss.spec.util.SetUtil;

public class ArrayType extends BaseType {

	protected BaseType itemType;

	public void setItemType(BaseType itemType) {
		this.itemType = itemType;
	}

	public BaseType getItemType() {
		return itemType;
	}

	@Override
	public PrimitiveType[] toPrimitives() {
		if (primitives == null) {
			primitives = itemType.toPrimitives();
		}
		return primitives;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
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
		ArrayType other = (ArrayType) obj;
		if (itemType == null) {
			if (other.itemType != null)
				return false;
		} else if (!itemType.equals(other.itemType))
			return false;
		return true;
	}

	@Override
	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(SetUtil.spaces(l));
		sb.append("Array Type: ");
		sb.append(itemType.toString(l));
		return sb.toString();
	}

}
