package edu.thu.ss.spec.meta;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.thu.ss.spec.util.SetUtil;

public class CompositeType extends BaseType {

	public static class ExtractOperation {
		public String name;
		public PrimitiveType type;

		public ExtractOperation(String name, PrimitiveType type) {
			super();
			this.name = name;
			this.type = type;
		}

		public PrimitiveType getType() {
			return type;
		}

		public String getName() {
			return name;
		}

	}

	protected Map<String, ExtractOperation> extracts = new LinkedHashMap<>();

	public void addExtractOperation(ExtractOperation operation) {
		extracts.put(operation.name, operation);
	}

	public ExtractOperation getExtractOperation(String name) {
		return extracts.get(name);
	}

	@Override
	public PrimitiveType[] toPrimitives() {
		if (primitives == null) {
			primitives = new PrimitiveType[extracts.size()];
			int i = 0;
			for (ExtractOperation extract : extracts.values()) {
				primitives[i++] = extract.type;
			}
		}
		return primitives;
	}

	@Override
	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(SetUtil.spaces(l));
		sb.append("Compositive Type \n");
		for (ExtractOperation extract : extracts.values()) {
			sb.append(SetUtil.spaces(l));
			sb.append("Extract Operation :");
			sb.append(extract.name);
			sb.append("\t");
			sb.append(extract.type.toString(l + 1));
			sb.append("\n");
		}
		return sb.toString();
	}
}
