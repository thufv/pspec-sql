package edu.thu.ss.spec.meta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.thu.ss.spec.util.SetUtil;

public abstract class ComplexType<K> extends BaseType {

	protected Map<K, BaseType> subtypes = new LinkedHashMap<>();

	protected BaseType defaultType;

	public Map<K, BaseType> getAllTypes() {
		return subtypes;
	}

	public BaseType getSubType(K k) {
		if (k == null) {
			return null;
		}
		BaseType result = this.subtypes.get(k);
		if (result != null) {
			return result;
		} else {
			return defaultType;
		}
	}

	public void setDefaultType(BaseType defaultType) {
		this.defaultType = defaultType;
	}

	public void add(K k, BaseType subtype) {
		this.subtypes.put(k, subtype);
	}

	@Override
	public PrimitiveType[] toPrimitives() {
		if (primitives == null) {
			List<PrimitiveType> types = new ArrayList<>();
			for (BaseType sub : subtypes.values()) {
				PrimitiveType[] entryPrimitives = sub.toPrimitives();
				for (PrimitiveType primitive : entryPrimitives) {
					types.add(primitive);
				}
			}
			if (defaultType != null) {
				PrimitiveType[] dps = defaultType.toPrimitives();
				for (PrimitiveType p : dps) {
					types.add(p);
				}
			}
			primitives = types.toArray(new PrimitiveType[types.size()]);
		}
		return primitives;
	}

	@Override
	public BaseType[] toSubTypes() {
		if (subTypesArray == null) {
			subTypesArray = new BaseType[defaultType == null ? subtypes.size() : subtypes.size() + 1];
			int i = 0;
			for (BaseType sub : subtypes.values()) {
				subTypesArray[i++] = sub;
			}
			if (defaultType != null) {
				subTypesArray[i] = defaultType;
			}
		}
		return subTypesArray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultType == null) ? 0 : defaultType.hashCode());
		result = prime * result + ((subtypes == null) ? 0 : subtypes.hashCode());
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
		ComplexType<?> other = (ComplexType<?>) obj;
		if (defaultType == null) {
			if (other.defaultType != null)
				return false;
		} else if (!defaultType.equals(other.defaultType))
			return false;
		if (subtypes == null) {
			if (other.subtypes != null)
				return false;
		} else if (!subtypes.equals(other.subtypes))
			return false;
		return true;
	}

	@Override
	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(SetUtil.spaces(l));
		sb.append(getTypeName());
		sb.append("\n");
		for (Entry<K, BaseType> e : subtypes.entrySet()) {
			sb.append(SetUtil.spaces(l));
			sb.append(getSelectorName());
			sb.append(":");
			sb.append(e.getKey());
			sb.append("\t");
			sb.append(e.getValue().toString(l + 1));
			sb.append("\n");
		}
		if (defaultType != null) {
			sb.append(SetUtil.spaces(l));
			sb.append("Default: ");
			sb.append(defaultType.toString(l + 1));
		}

		return sb.toString();
	}

	protected abstract String getTypeName();

	protected abstract String getSelectorName();

}
