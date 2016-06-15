package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;

public class PolicyModel extends BaseModel {
	protected Policy policy;

	protected List<RuleModel> ruleModels = new ArrayList<>();

	public PolicyModel(Policy policy, String path) {
		super(path);
		this.policy = policy;
	}

	public PolicyModel(String path) {
		super(path);
	}

	public Policy getPolicy() {
		return policy;
	}

	public void init(Policy policy) {
		this.policy = policy;
		for (RuleModel ruleModel : ruleModels) {
			ruleModel.init();
		}
	}

	public List<RuleModel> getRuleModels() {
		return ruleModels;
	}

	public void addRuleModel(RuleModel ruleModel) {
		this.ruleModels.add(ruleModel);
		this.policy.getRules().add(ruleModel.getRule());
	}

	public void removeRuleModel(RuleModel ruleModel) {
		this.ruleModels.remove(ruleModel);
		this.policy.getRules().remove(ruleModel.getRule());

	}

	public RuleModel getRuleModel(Rule rule) {
		if (rule == null) {
			return null;
		}
		for (RuleModel ruleModel : ruleModels) {
			if (ruleModel.getRule().equals(rule)) {
				return ruleModel;
			}
		}
		return null;
	}

	@Override
	public void clearOutput() {
		super.clearOutput();
		for (RuleModel ruleModel : ruleModels) {
			ruleModel.clearOutput();
		}
	}

	@Override
	public void clearOutput(OutputType type) {
		super.clearOutput(type);
		for (RuleModel ruleModel : ruleModels) {
			ruleModel.clearOutput(type);
		}
	}

	@Override
	public void clearOutput(OutputType outputType, MessageType messageType) {
		super.clearOutput(outputType, messageType);
		for (RuleModel ruleModel : ruleModels) {
			ruleModel.clearOutput(outputType, messageType);
		}
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

	@Override
	public boolean hasOutput() {
		if (super.hasOutput()) {
			return true;
		}
		for (RuleModel ruleModel : ruleModels) {
			if (ruleModel.hasOutput()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasOutput(OutputType type) {
		if (super.hasOutput(type)) {
			return true;
		}
		for (RuleModel ruleModel : ruleModels) {
			if (ruleModel.hasOutput(type)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasOutput(OutputType outputType, MessageType messageType) {
		if (super.hasOutput(outputType, messageType)) {
			return true;
		}
		for (RuleModel ruleModel : ruleModels) {
			if (ruleModel.hasOutput(outputType, messageType)) {
				return true;
			}
		}
		return false;
	}

	public void initRuleModels() {
		for (RuleModel ruleModel : ruleModels) {
			ruleModel.init();
		}
	}

	/**
	 * 
	 * @param expanded
	 * @return rule is removed
	 */
	public boolean removeExpandedRule(ExpandedRule expanded) {
		boolean removed = false;
		RuleModel ruleModel = getRuleModel(expanded.getRule());
		Rule rule = ruleModel.getRule();
		if (expanded.isSingle()) {
			if (rule.getDataRefs().size() == 1) {
				removeRuleModel(ruleModel);
				removed = true;
			} else {
				ruleModel.simplifyDataRef(expanded.getDataRef());
			}
		} else {
			removeRuleModel(ruleModel);
			removed = true;
		}
		return removed;
	}

	public void retainOutput(OutputType outputType, MessageType messageType) {
		super.retainOutput(outputType, messageType);
		for (RuleModel ruleModel : ruleModels) {
			ruleModel.retainOutput(outputType, messageType);
		}
	}
}
