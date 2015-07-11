package edu.thu.ss.editor.model;

import edu.thu.ss.spec.lang.pojo.Policy;

public class PolicyModel extends BaseModel {
	protected Policy policy;

	public PolicyModel(Policy policy, String path) {
		super(path);
		this.policy = policy;
	}

	public Policy getPolicy() {
		return policy;
	}
}
