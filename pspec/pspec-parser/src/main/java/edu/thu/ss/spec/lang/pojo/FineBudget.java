package edu.thu.ss.spec.lang.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.Pair;
import edu.thu.ss.spec.util.XMLUtil;

public class FineBudget extends PrivacyBudget<Map<DataCategory, Double>> implements Parsable {

	public static class BudgetAllocation extends PrivacyBudget.BudgetAllocation {

		public DataRef dataRef;
		public Double budget;

		public BudgetAllocation(UserRef userRef, DataRef dataRef, Double budget) {
			super(userRef);
			this.dataRef = dataRef;
			this.budget = budget;
		}

	}

	public Map<DataCategory, Double> getBudget(UserCategory user) {
		return budgets.get(user);
	}

	public void materialize(Map<UserCategory, Map<DataCategory, Double>> materialized) {
		this.budgets = materialized;
	}

	@Override
	public void parse(Node fineNode) {
		NodeList list = fineNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_DP_Budget_Allocation.equals(name)) {
				parseAllocation(node);
			}
		}
	}

	private void parseAllocation(Node allocNode) {
		NodeList list = allocNode.getChildNodes();
		List<UserRef> users = new ArrayList<>();
		List<Pair<DataRef, Double>> datas = new ArrayList<>();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_UserRef.equals(name)) {
				UserRef ref = new UserRef();
				ref.parse(node);
				users.add(ref);
			} else if (ParserConstant.Ele_Policy_Rule_DataRef.equals(name)) {
				DataRef ref = new DataRef();
				ref.parse(node);
				Double budget = Double.valueOf(XMLUtil.getAttrValue(node,
						ParserConstant.Attr_Policy_DP_Budget));
				datas.add(new Pair<DataRef, Double>(ref, budget));
			}
		}

		for (UserRef user : users) {
			for (Pair<DataRef, Double> pair : datas) {
				this.allocations.add(new BudgetAllocation(user, pair.left, pair.right));
			}
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Fine Budget:\n");
		for (Entry<UserCategory, Map<DataCategory, Double>> e : budgets.entrySet()) {
			sb.append("User: ");
			sb.append(e.getKey().getId());
			sb.append("\n");
			for (Entry<DataCategory, Double> de : e.getValue().entrySet()) {
				sb.append("\tData: ");
				sb.append(de.getKey().getId());
				sb.append("\t Budget: ");
				sb.append(de.getValue());
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	@Override
	public boolean isGlobal() {
		return false;
	}
}
