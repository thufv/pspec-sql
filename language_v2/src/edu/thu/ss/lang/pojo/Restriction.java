package edu.thu.ss.lang.pojo;

import java.util.HashSet;
import java.util.Set;

public class Restriction {

	protected Set<Desensitization> desensitizations;

	protected boolean forbid;

	public Restriction(boolean forbid) {
		this.forbid = forbid;
	}

	public Restriction(Set<Desensitization> des) {
		this.desensitizations = des;
	}

	public Restriction(Desensitization de) {
		this.desensitizations = new HashSet<>();
		this.desensitizations.add(de);
	}

	public Set<Desensitization> getDesensitizations() {
		return desensitizations;
	}

	public Desensitization getDesensitization() {
		return desensitizations.iterator().next();
	}

	public boolean isForbid() {
		return forbid;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Restriction: ");
		if (forbid) {
			sb.append("forbid");
		} else {
			for (Desensitization de : desensitizations) {
				sb.append("{");
				sb.append(de);
				sb.append("} ");
			}
		}
		return sb.toString();
	}
}
