package edu.thu.ss.spec.lang.expression;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;
public class BinaryComparison extends Expression<DataCategory>{
	public enum binaryComparisonTypes {
		EqualTo,  LessThan, LessThanOrEqual, GreaterThan, GreaterThanOrEqual
	}
	
	private binaryComparisonTypes binaryComparisonType;
	private Expression<DataCategory> left;
	private Expression<DataCategory> right;
	private String symbol;
	
	public BinaryComparison() {
		super.ExpressionType = ExpressionTypes.binaryComparison;
	}

	public Expression<DataCategory> getLeftExpression() {
		return left;
	}
	
	public Expression<DataCategory> getRightExpression() {
		return right;
	}
	
	public binaryComparisonTypes getComparisonType() {
		return binaryComparisonType;
	}
	
	@Override
	public Set<DataCategory> getDataSet() {
		if (dataSet != null) {
			return dataSet;
		}
		Set<DataCategory> set1 = left.getDataSet();
		Set<DataCategory> set2 = right.getDataSet();
		set1.addAll(set2);
		dataSet = set1;
		return dataSet;
	}

	@Override
	public void parse(Node cNode) {
		NodeList list = cNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			
			if (ParserConstant.Ele_Policy_Rule_Operator.equals(name)) {
				String operator = node.getTextContent();
				switch (operator) {
				case "eq":
					binaryComparisonType = binaryComparisonTypes.EqualTo;
					symbol = "=";
					break;
				case "gt":
					binaryComparisonType = binaryComparisonTypes.GreaterThan;
					symbol = ">";
					break;
				case "ge":
					binaryComparisonType = binaryComparisonTypes.GreaterThanOrEqual;
					symbol = ">=";
					break;
				case "lt":
					binaryComparisonType = binaryComparisonTypes.LessThan;
					symbol = "<";
					break;
				case "le":
					binaryComparisonType = binaryComparisonTypes.LessThanOrEqual;
					symbol = "<=";
					break;
				}
			}
			else if (ParserConstant.Ele_Policy_Rule_Function.equals(name)) {
				if (left == null) {
					left = new Function();
					left.parse(node);
				}
				else if (right == null) {
					right = new Function();
					right.parse(node);
				}
			}
			else if (ParserConstant.Ele_Policy_Rule_Term.equals(name)) {
				if (left == null) {
					left = new Term();
					left.parse(node);
				}
				else if (right == null) {
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (left == null || right == null) {
			sb.append("Comparision Error");
		}
		else {
			sb.append("[");
			sb.append(left);
			sb.append(symbol);
			sb.append(right);
			sb.append("]");
		}
		return sb.toString();
	}
	
}
