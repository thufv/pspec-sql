package edu.thu.ss.spec.meta;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.util.PSpecUtil;

public class ConditionalColumn extends DBObject {
	protected Map<JoinCondition, BaseType> types = new HashMap<>();

	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append(PSpecUtil.spaces(l));
		sb.append("Column: ");
		sb.append(name);
		sb.append("\n");
		for (JoinCondition join : types.keySet()) {
			sb.append(PSpecUtil.spaces(l + 1));
			sb.append("Join: ");
			sb.append(join);
			sb.append(types.get(join).toString(l + 1));
			sb.append("\n");
		}
		return sb.toString();
	}

	public Map<JoinCondition, BaseType> getTypes() {
		return types;
	}

	public Collection<JoinCondition> getConditions() {
		return types.keySet();
	}

	public BaseType getType(JoinCondition join) {
		return types.get(join);
	}

	public void addType(JoinCondition join, BaseType type) {
		types.put(join, type);
	}
}
