package edu.thu.ss.spec.lang.expression;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public abstract class Predicate extends Expression<DataCategory> {
	
	protected List<Expression<DataCategory>> expressions = new ArrayList<>();
	protected Expression<DataCategory> expression;
	protected String symbol;
	
	public Predicate() {
	}

	public void addExpression(Expression<DataCategory> expression) {
		this.expressions.add(expression);
	}

	public void setExpression(Expression<DataCategory> expression) {
		this.expression = expression;
	}

	public void setExpressions(List<Expression<DataCategory>> expressions) {
		this.expressions = expressions;
	}
	
	public List<Expression<DataCategory>> getExpressions() {
		return expressions;
	}

	public Expression<DataCategory> getExpression() {
		return expression;
	}

	public abstract boolean isBinary();

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
				expressions.add(expr);
			} else if (ParserConstant.Ele_Policy_Rule_Or.equals(name)) {
				expr = new Or();
				expr.parse(node);
				expressions.add(expr);
			} else if (ParserConstant.Ele_Policy_Rule_Not.equals(name)) {
				expr = new Not();
				expr.parse(node);
				expressions.add(expr);
			} else if (ParserConstant.Ele_Policy_Rule_Comparison.equals(name)) {
				expr = Comparison.parseComparison(node);
				expr.parse(node);
				expressions.add(expr);
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
		if (expressions == null) {
			sb.append("Predicate Error");
		} else {
			sb.append("(");
			sb.append(expressions.get(0));
			for (Expression<DataCategory> expr : expressions.subList(1, expressions.size())) {
				sb.append(" " + symbol + " ");
				sb.append(expr);
			}
			sb.append(")");
		}
		return sb.toString();
	}
	
	@Override
	public List<Expression<DataCategory>> split() {
		List<Expression<DataCategory>> list = new ArrayList<>();
		for (Expression<DataCategory> expr : expressions) {
			list.addAll(expr.split());
		}
		return list;
	}
}
