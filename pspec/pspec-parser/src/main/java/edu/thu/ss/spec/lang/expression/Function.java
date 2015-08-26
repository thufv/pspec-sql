package edu.thu.ss.spec.lang.expression;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.util.XMLUtil;

public abstract class Function extends Expression<DataCategory> {
	protected Expression<DataCategory> left;
	protected Expression<DataCategory> right;
	protected String symbol;

	public Expression<DataCategory> getLeftExpression() {
		return left;
	}

	public Expression<DataCategory> getRightExpression() {
		return right;
	}

	public static Function parseFunction(Node fNode) {
		String symbol = XMLUtil.getAttrValue(fNode, ParserConstant.Attr_Function_Name);
		switch (symbol) {
		case "+":
			Add add = new Add();
			add.parse(fNode);
			return add;
		case "-":
			Subtract subtract = new Subtract();
			subtract.parse(fNode);
			return subtract;
		case "*":
			Multiply multiply = new Multiply();
			multiply.parse(fNode);
			return multiply;
		case "/":
			Divide divide = new Divide();
			divide.parse(fNode);
			return divide;
		case "%":
			Remainder remainder = new Remainder();
			remainder.parse(fNode);
			return remainder;
		default:
			return null;
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
		} else {
			sb.append("(");
			sb.append(left);
			sb.append(symbol);
			sb.append(right);
			sb.append(")");
		}
		return sb.toString();
	}
}
