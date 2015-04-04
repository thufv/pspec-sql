package edu.thu.ss.spec.lang.pojo;

import java.util.Map.Entry;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.util.XMLUtil;

public class GlobalBudget extends PrivacyBudget<Double> implements Parsable {

	public static class BudgetAllocation extends PrivacyBudget.BudgetAllocation {

		public Double budget;

		public BudgetAllocation(UserRef user, Double budget) {
			super(user);
			this.budget = budget;

		}

	}

	@Override
	public boolean isGlobal() {
		return true;
	}

	@Override
	public void parse(Node budgetNode) {
		NodeList list = budgetNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_UserRef.equals(name)) {
				UserRef user = new UserRef();
				user.parse(node);
				Double budget = Double.valueOf(XMLUtil.getAttrValue(node,
						ParserConstant.Attr_Policy_DP_Budget));
				this.allocations.add(new BudgetAllocation(user, budget));
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Gloabl Budget:\n");
		for (Entry<UserCategory, Double> e : budgets.entrySet()) {
			sb.append("User: ");
			sb.append(e.getKey().getId());
			sb.append("\t Budget: ");
			sb.append(e.getValue());
			sb.append("\n");
		}

		return sb.toString();
	}

}
