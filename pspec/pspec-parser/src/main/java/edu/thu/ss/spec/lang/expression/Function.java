package edu.thu.ss.spec.lang.expression;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.util.XMLUtil;

public class Function extends Expression<DataCategory>{
	public enum FunctionTypes {
		add, subtract, multiply, divide, remainder
	}
	
	private FunctionTypes FunctionType;
	private Expression<DataCategory> left;
	private Expression<DataCategory> right;
	private String symbol;
	
	public Function() {
		super.ExpressionType = ExpressionTypes.function;
	}
	
	public Expression<DataCategory> getLeftExpression() {
		return left;
	}
	
	public Expression<DataCategory> getRightExpression() {
		return right;
	}
	
	public FunctionTypes getFunctionType() {
		return FunctionType;
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
	public void parse(Node fNode) {
		symbol = XMLUtil.getAttrValue(fNode, ParserConstant.Attr_Function_Name);
		switch (symbol) {
		case "+":
			FunctionType = FunctionTypes.add;
			break;
		case "-":
			FunctionType = FunctionTypes.subtract;
			break;
		case "*":
			FunctionType = FunctionTypes.multiply;
			break;
		case "/":
			FunctionType = FunctionTypes.divide;
			break;
		case "%":
			FunctionType = FunctionTypes.remainder;
			break;
		default:
			return;
		}
		
		NodeList list = fNode.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (ParserConstant.Ele_Policy_Rule_Function.equals(name)) {
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
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (left == null || right == null) {
			sb.append("Function Error");
		}
		else {
			sb.append("(");
			sb.append(left);
			sb.append(symbol);
			sb.append(right);
			sb.append(")");
		}
		return sb.toString();
	}
}
