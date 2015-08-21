package edu.thu.ss.spec.lang.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class Not extends Predicate {

	public Not() {
		symbol = "Not";
	}

	@Override
	public boolean isBinary() {
		return false;
	}

	@Override
	public Set<DataCategory> getDataSet() {
		return expression.getDataSet();
	}

	@Override
	public void parse(Node pNode) {
		NodeList list = pNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (name == null) {
				continue;
			}

			Expression<DataCategory> expr = null;
			if (ParserConstant.Ele_Policy_Rule_And.equals(name)) {
				expr = new And();
				expr.parse(node);
				expression = expr;
				return;
			} else if (ParserConstant.Ele_Policy_Rule_Or.equals(name)) {
				expr = new Or();
				expr.parse(node);
				expression = expr;
				return;
			} else if (ParserConstant.Ele_Policy_Rule_Not.equals(name)) {
				expr = new Not();
				expr.parse(node);
				expression = expr;
				return;
			} else if (ParserConstant.Ele_Policy_Rule_Comparison.equals(name)) {
				expr = Comparison.parseComparison(node);
				expression = expr;
				return;
			}
		}
	}

	@Override
	public List<Expression<DataCategory>> split() {
		List<Expression<DataCategory>> list = new ArrayList<>();
		if (expression instanceof And) {
			Or or = new Or();
			for (Expression<DataCategory> expr : ((And) expression).getExpressions()) {
				Not not = new Not();
				not.setExpression(expr);
				or.addExpression(not);
			}
			list.add(or);
		} else if (expression instanceof Or) {
			for (Expression<DataCategory> expr : ((Or) expression).getExpressions()) {
				Not not = new Not();
				not.setExpression(expr);
				list.add(not);
			}
		} else if (expression instanceof Not) {
			list = ((Not) expression).getExpression().split();
		} else if (expression instanceof Comparison) {
			list.add(this);
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("Not ");
		sb.append(expression);
		sb.append("]");
		return sb.toString();
	}
}