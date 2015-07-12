package edu.thu.ss.spec.lang.parser.event;

import java.net.URI;

import edu.thu.ss.spec.lang.pojo.Policy;

public class PolicyEvent extends ParseEvent {
	public Policy policy;

	public PolicyEvent(int type, URI uri, Policy policy, Object... data) {
		super(type, uri, data);
		this.policy = policy;
	}

}
