package edu.thu.ss.spec.lang.expr;

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
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.UserRef;

public class RuleGenerator {

	UserContainer users;

	DataContainer datas;

	List<UserCategory> userList;

	List<DataCategory> dataList;

	double desensitizeRatio = 0.5;

	double desensitzeOperationRatio = 0.5;

	int minDim = 1;

	int maxDim = 5;

	int minRes = 0;

	int maxRes = 5;

	int retryThresh = 5 * maxDim;

	private static Random rand = new Random(System.currentTimeMillis());

	private static Action[] actions = { Action.All, Action.Output, Action.Condition };

	public void setDatas(DataContainer datas) {
		this.datas = datas;
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

	public List<Rule> generate(int num) {
		List<Rule> result = new ArrayList<>();
		flatten();
		for (int i = 0; i < num; i++) {
			Rule rule = generateRule(i);
			result.add(rule);
		}
		return result;
	}

	private Rule generateRule(int i) {
		String ruleId = "rule" + i;
		Rule rule = new Rule();
		rule.setId(ruleId);

		generateUserRef(rule);
		generateDataAssociation(rule);
		generateRestrictions(rule);
		return rule;
	}

	private void generateUserRef(Rule rule) {
		int i = rand.nextInt(userList.size());
		UserCategory user = userList.get(i);
		UserRef ref = new UserRef();
		ref.setUser(user);
		ref.setRefid(user.getId());
		rule.setUserRef(ref);
	}

	private void generateDataAssociation(Rule rule) {
		int len = rand.nextInt(maxDim - minDim + 1) + minDim;

		DataAssociation association = null;
		while (association == null) {
			association = generateDataAssociation(len);
		}
		rule.setDataAssociation(association);
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

	private DataAssociation generateDataAssociation(int len) {
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

	private void generateRestrictions(Rule rule) {

		int resNum = rand.nextInt(maxRes - minRes + 1) + minRes;
		List<Restriction> list = new ArrayList<>(resNum);

		for (int i = 0; i < resNum; i++) {
			list.add(generateRestriction(rule));
		}
		rule.setRestrictions(list);

	}

	private Restriction generateRestriction(Rule rule) {
		Restriction res = new Restriction();
		int deCount = (int) Math.ceil(desensitizeRatio * rule.getDimension());
		if (deCount == 0) {
			deCount = 1;
		} else {
			deCount = rand.nextInt(deCount) + 1;
		}

		List<Integer> list = randomList(rule.getDimension());

		for (int i = 0; i < deCount; i++) {
			DataRef ref = rule.getDataAssociation().get(list.get(i));
			res.getDesensitizations().add(generateDesensitization(ref));
		}

		return res;
	}

	private Desensitization generateDesensitization(DataRef ref) {
		Desensitization de = new Desensitization();
		de.setDataRef(ref);

		DataCategory data = ref.getCategory();
		List<DesensitizeOperation> ops = new ArrayList<>(data.getAllOperations());
		Collections.shuffle(ops, rand);

		int count = rand.nextInt((int) Math.ceil((desensitzeOperationRatio * ops.size()))) + 1;

		Set<DesensitizeOperation> deOps = new HashSet<>();

		for (int i = 0; i < count; i++) {
			deOps.add(ops.get(i));
		}
		de.setOperations(deOps);

		return de;

	}

	private List<Integer> randomList(int n) {
		List<Integer> list = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		Collections.shuffle(list, rand);
		return list;
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
