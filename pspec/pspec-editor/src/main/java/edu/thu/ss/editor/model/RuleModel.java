package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserRef;

public class RuleModel extends BaseModel {
	private Rule rule;

	private List<UserRef> userRefs = new ArrayList<>();
	private List<DataRef> dataRefs = new ArrayList<>();
	//only non-forbid restrictions
	private List<Restriction> restrictions = new ArrayList<>();
	private boolean forbid = false;

	public RuleModel(Rule rule) {
		super("");
		this.rule = rule;
	}

	//must be called explicitly
	public void init() {
		userRefs.clear();
		for (UserRef ref : rule.getUserRefs()) {
			userRefs.add(ref.clone());
		}

		dataRefs.clear();
		for (DataRef ref : rule.getDataRefs()) {
			dataRefs.add(ref.clone());
		}

		restrictions.clear();
		if (rule.getRestrictions().size() == 0 || rule.getRestriction().isForbid()) {
			forbid = true;
		} else {
			for (Restriction res : rule.getRestrictions()) {
				restrictions.add(res.clone());
			}
		}
	}

	public Rule getRule() {
		return rule;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public List<UserRef> getUserRefs() {
		return userRefs;
	}

	public List<DataRef> getDataRefs() {
		return dataRefs;
	}

	public boolean isForbid() {
		return forbid;
	}

	public void setForbid(boolean forbid) {
		this.forbid = forbid;
	}

}