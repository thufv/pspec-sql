package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;

public class PolicyModel extends BaseModel {
	protected Policy policy;

	protected List<RuleModel> ruleModels = new ArrayList<>();

	public PolicyModel(Policy policy, String path) {
		super(path);
		this.policy = policy;

		for (Rule rule : policy.getRules()) {
			ruleModels.add(new RuleModel(rule));
		}
	}

	public Policy getPolicy() {
		return policy;
	}

	@Override
	public void getOutput(OutputType type, List<OutputEntry> list) {
		super.getOutput(type, list);
		for (RuleModel rule : ruleModels) {
			rule.getOutput(type, list);
		}
	}

	@Override
	public int countOutput(OutputType type) {
		int count = super.countOutput(type);
		for (RuleModel rule : ruleModels) {
			count += rule.countOutput(type);
		}
		return count;

	}

	public List<RuleModel> getRuleModels() {
		return ruleModels;
	}

	public void addRuleModel(RuleModel ruleModel) {
		this.ruleModels.add(ruleModel);
		this.policy.getRules().add(ruleModel.getRule());
	}

}
