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

	public void simplify(List<UserRef> userRefs, List<DataRef> dataRefs,
			List<Restriction> restrictions) {
		for (UserRef user : userRefs) {
			simplifyUserRef(user);
		}
		for (DataRef data : dataRefs) {
			simplifyDataRef(data);
		}
		for (Restriction res : restrictions) {
			simplifyRestriction(res);
		}
	}

	public void simplifyUserRef(UserRef ref) {
		for (int i = 0; i < rule.getUserRefs().size(); i++) {
			if (rule.getUserRefs().get(i).equals(ref)) {
				rule.getUserRefs().remove(i);
				userRefs.remove(i);
				return;
			}
		}
	}

	public void simplifyDataRef(DataRef ref) {
		for (int i = 0; i < rule.getRawDataRefs().size(); i++) {
			if (rule.getRawDataRefs().get(i).equals(ref)) {
				rule.getRawDataRefs().remove(i);
				dataRefs.remove(i);
				return;
			}
		}
	}

	public void simplifyRestriction(Restriction res) {
		for (int i = 0; i < rule.getRestrictions().size(); i++) {
			if (rule.getRestrictions().get(i).equals(res)) {
				rule.getRestrictions().remove(i);
				restrictions.remove(i);
			}
		}
	}

}