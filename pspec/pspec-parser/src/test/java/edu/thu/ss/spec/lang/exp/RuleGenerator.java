package edu.thu.ss.spec.lang.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;

public class RuleGenerator {

	UserContainer users;

	DataContainer datas;

	List<UserCategory> userList;

	List<DataCategory> dataList;

	double forbidRatio = 0.2;

	double desensitizeRatio = 0.5;

	int minDim = 1;

	int maxDim = 5;

	// restriction must be 1 when dim = 1
	int minRes = 1;

	int maxRes = 5;

	int retryThresh = 5 * maxDim;

	private int forbidRules = 0;

	private int nonForbidRules = 0;

	private static Random rand = new Random(System.currentTimeMillis());

	private static Action[] actions = { Action.All, Action.Projection, Action.Condition };

	public void setDatas(DataContainer datas) {
		this.datas = datas;
	}

	public void setForbidRatio(double forbidRatio) {
		this.forbidRatio = forbidRatio;
	}

	public void setMinDim(int minDim) {
		this.minDim = minDim;
	}

	public void setMaxDim(int maxDim) {
		this.maxDim = maxDim;
	}

	public void setMinRes(int minRes) {
		this.minRes = minRes;
	}

	public void setMaxRes(int maxRes) {
		this.maxRes = maxRes;
	}

	public void setUsers(UserContainer users) {
		this.users = users;
	}

	private void flatten() {
		userList = new ArrayList<>();
		UserContainer baseUsers = users;
		while (baseUsers != null) {
			userList.addAll(baseUsers.getCategories());
			baseUsers = baseUsers.getBaseContainer();
		}

		dataList = new ArrayList<>();
		DataContainer baseDatas = datas;
		while (baseDatas != null) {
			dataList.addAll(baseDatas.getCategories());
			baseDatas = baseDatas.getBaseContainer();
		}
	}

	public List<ExpandedRule> generate(int num) {
		List<ExpandedRule> result = new ArrayList<>();
		flatten();
		forbidRules = (int) (num * forbidRatio);
		nonForbidRules = num - forbidRules;
		for (int i = 0; i < num; i++) {
			ExpandedRule rule = generateRule(i);
			result.add(rule);
		}
		return result;
	}

	private ExpandedRule generateRule(int i) {
		String ruleId = "rule" + i;
		ExpandedRule rule = new ExpandedRule();
		rule.setId(ruleId);

		generateUserRef(rule);
		generateDataRef(rule);
		generateRestrictions(rule);
		return rule;
	}

	private void generateUserRef(ExpandedRule rule) {
		int i = rand.nextInt(userList.size());
		UserCategory user = userList.get(i);
		UserRef ref = new UserRef();
		ref.setUser(user);
		ref.setRefid(user.getId());
		List<UserRef> list = new ArrayList<>(1);
		list.add(ref);
		rule.setUserRefs(list);
	}

	private void generateDataRef(ExpandedRule rule) {
		int len = 0;
		if (maxDim == 1) {
			len = 1;
		} else {
			len = rand.nextInt(maxDim - minDim);
			len += minDim;
		}

		if (len == 1) {
			DataRef ref = generateDataRef();
			rule.setDataRef(ref);
		} else {
			DataAssociation association = null;
			while (association == null) {
				association = generateAssociation(len);
			}
			rule.setAssociation(association);
		}
	}

	private DataRef generateDataRef() {
		int i = rand.nextInt(dataList.size());
		DataCategory data = dataList.get(i);
		i = rand.nextInt(actions.length);
		Action action = actions[i];
		DataRef ref = new DataRef();
		ref.setRefid(data.getId());
		ref.setData(data);
		ref.setAction(action);
		return ref;
	}

	private DataAssociation generateAssociation(int len) {
		DataAssociation association = new DataAssociation();
		int errorCount = 0;

		for (int i = 0; i < len;) {
			DataRef ref = generateDataRef();
			boolean error = false;
			for (DataRef ref2 : association.getDataRefs()) {
				if (ref2.getCategory().ancestorOf(ref.getCategory())
						|| ref2.getCategory().descedantOf(ref.getCategory())) {
					error = true;
					errorCount++;
					break;
				}
			}
			if (error) {
				if (errorCount > retryThresh) {
					return null;
				}
			} else {
				association.getDataRefs().add(ref);
				i++;
			}
		}
		return association;
	}

	private void generateRestrictions(ExpandedRule rule) {
		int r = rand.nextInt(forbidRules + nonForbidRules);
		if (r < forbidRules) {
			Restriction[] res = new Restriction[1];
			res[0] = new Restriction();
			res[0].setForbid(true);
			rule.setRestrictions(res);
			forbidRules--;
		} else {
			int resNum = 1;
			if (maxRes > 1) {
				resNum = rand.nextInt(maxRes - minRes) + minRes;
			}
			if (rule.isSingle()) {
				resNum = 1;
			}
			Restriction[] res = new Restriction[resNum];

			for (int i = 0; i < resNum; i++) {
				res[i] = generateRestriction(rule);
			}
			rule.setRestrictions(res);

			nonForbidRules--;

		}
	}

	private Restriction generateRestriction(ExpandedRule rule) {
		Restriction res = new Restriction();
		if (rule.isSingle()) {
			Desensitization de = generateDesensitization(rule.getDataRef());
			de.setDataRef(null);
			res.getDesensitizations().add(de);
		} else {
			int deCount = (int) Math.ceil(desensitizeRatio * rule.getDimension());
			DataAssociation association = rule.getAssociation();
			for (int i = 0; i < rule.getDimension(); i++) {
				int r = rand.nextInt(rule.getDimension() - i);
				if (r < deCount) {
					res.getDesensitizations().add(generateDesensitization(association.get(i)));
					deCount--;
				}
			}
		}

		return res;
	}

	private Desensitization generateDesensitization(DataRef ref) {
		Desensitization de = new Desensitization();
		de.setDataRef(ref);

		DataCategory data = ref.getCategory();
		List<DesensitizeOperation> ops = new ArrayList<>(data.getOperations());
		Collections.shuffle(ops, rand);

		int count = rand.nextInt(ops.size()) + 1;

		Set<DesensitizeOperation> deOps = new HashSet<>();

		for (int i = 0; i < count; i++) {
			deOps.add(ops.get(i));
		}
		de.setOperations(deOps);

		return de;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Users: ");
		sb.append(userList.size());
		sb.append("\n");

		sb.append("Datas: ");
		sb.append(dataList.size());
		sb.append("\n");

		sb.append("Forbid ratio: ");
		sb.append(forbidRatio);
		sb.append("\n");

		sb.append("Desensitize ratio: ");
		sb.append(desensitizeRatio);
		sb.append("\n");

		sb.append("Min Dim: ");
		sb.append(minDim);
		sb.append("\n");

		sb.append("Max Dim: ");
		sb.append(maxDim);
		sb.append("\n");

		sb.append("Min Res: ");
		sb.append(minRes);
		sb.append("\n");

		sb.append("Max Res: ");
		sb.append(maxRes);
		sb.append("\n");

		return sb.toString();
	}

}
