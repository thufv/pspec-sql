package edu.thu.ss.spec.lang.expression;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class Comparison extends Expression<DataCategory> {
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

	public static Comparison parseComparison(Node cNode) {
		NodeList list = cNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();

			if (ParserConstant.Ele_Policy_Rule_Operator.equals(name)) {
				String operator = node.getTextContent();
				switch (operator) {
				case "eq":
					EqualTo equalTo = new EqualTo();
					equalTo.parse(cNode);
					return equalTo;
				case "gt":
					GreaterThan greaterThan = new GreaterThan();
					greaterThan.parse(cNode);
					return greaterThan;
				case "ge":
					GreaterThanOrEqual greaterThanOrEqual = new GreaterThanOrEqual();
					greaterThanOrEqual.parse(cNode);
					return greaterThanOrEqual;
				case "lt":
					LessThan lessThan = new LessThan();
					lessThan.parse(cNode);
					return lessThan;
				case "le":
					LessThanOrEqual lessThanOrEqual = new LessThanOrEqual();
					lessThanOrEqual.parse(cNode);
					return lessThanOrEqual;
				default:
					return null;
				}
			}
		}
		return null;
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

	@Override
	public void parse(Node cNode) {
		NodeList list = cNode.getChildNodes();
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
	public Element outputType(Document document, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element outputElement(Document document) {
		return null;
	}
}
