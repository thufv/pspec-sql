package edu.thu.ss.spec.lang.expression;

import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class BinaryFunction extends Function {
	protected Expression<DataCategory> left;
	protected Expression<DataCategory> right;
	protected String symbol;

	public Expression<DataCategory> getLeftExpression() {
		return left;
	}

	public Expression<DataCategory> getRightExpression() {
		return right;
	}

	@Override
	public Set<DataCategory> getDataSet() {
		Set<DataCategory> set1 = left.getDataSet();
		Set<DataCategory> set2 = right.getDataSet();
		set1.addAll(set2);
		return set1;
	}

	@Override
	public void parse(Node fNode) {
		NodeList list = fNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Function.equals(name)) {
				if (left == null) {
					left = Function.parseFunction(node);
				} else if (right == null) {
					left = Function.parseFunction(node);
				}
			} else if (ParserConstant.Ele_Policy_Rule_Term.equals(name)) {
				if (left == null) {
					left = new Term();
					left.parse(node);
				} else if (right == null) {
					right = new Term();
					right.parse(node);
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (left == null || right == null) {
			sb.append("Arithmetic Error");
		} else {
			sb.append("[");
			sb.append(left);
			sb.append(" ");
			sb.append(symbol);
			sb.append(" ");
			sb.append(right);
			sb.append("]");
		}
		return sb.toString();
	}
}
