package edu.thu.ss.spec.meta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.thu.ss.spec.util.SetUtil;

public class StructType extends BaseType {

	public static class FieldType {
		public String name;
		public BaseType type;

		public FieldType(String name, BaseType type) {
			this.name = name;
			this.type = type;
		}

		public BaseType getType() {
			return type;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
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
			FieldType other = (FieldType) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

	}

	protected Map<String, FieldType> fields = new LinkedHashMap<>();

	public Map<String, FieldType> getFields() {
		return fields;
	}

	@Override
	public PrimitiveType[] toPrimitives() {
		if (primitives == null) {
			List<PrimitiveType> types = new ArrayList<>();

			for (FieldType field : fields.values()) {
				PrimitiveType[] fieldPrimitives = field.type.toPrimitives();
				for (PrimitiveType primitive : fieldPrimitives) {
					types.add(primitive);
				}
			}
			primitives = types.toArray(new PrimitiveType[types.size()]);
		}
		return primitives;
	}

	public FieldType getField(String name) {
		return fields.get(name);
	}

	public BaseType getFieldType(String name) {
		FieldType field = getField(name);
		if (field == null) {
			return null;
		} else {
			return field.getType();
		}
	}

	public void addField(FieldType field) {
		fields.put(field.name, field);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
		StructType other = (StructType) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	@Override
	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(SetUtil.spaces(l));
		sb.append("Struct Type \n");
		for (FieldType field : fields.values()) {
			sb.append(SetUtil.spaces(l));
			sb.append("Field :");
			sb.append(field.name);
			sb.append("\t");
			sb.append(field.type.toString(l + 1));
			sb.append("\n");
		}
		return sb.toString();
	}
}
