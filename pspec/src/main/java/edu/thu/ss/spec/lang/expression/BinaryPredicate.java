package edu.thu.ss.spec.lang.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;

public class BinaryPredicate extends Expression<DataCategory> {
	public enum binaryPredicateTypes {
		and, or, not
	}
	
	private binaryPredicateTypes binaryPredicateType;
	private List<Expression<DataCategory>> expressions = new ArrayList<>();
	
	public BinaryPredicate(binaryPredicateTypes type) {
		super.ExpressionType = ExpressionTypes.binaryPredicate;
		binaryPredicateType = type;
	}

	public binaryPredicateTypes getPredicateType() {
		return binaryPredicateType;
	}
	
	public List<Expression<DataCategory>> getExpressionList() {
		return expressions;
	}
	
	@Override
	public Set<DataCategory> getDataSet() {
		if (dataSet != null) {
			return dataSet;
		}
		Set<DataCategory> dataSet = null;
		for (Expression<DataCategory> expr : expressions) {
			Set<DataCategory> set = expr.getDataSet();
			if (dataSet == null) {
				dataSet = set;
			}
			else {
				dataSet.addAll(set);
			}
		}
		return dataSet;
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
			
			Expression<DataRef> expr = null;
			if (ParserConstant.Ele_Policy_Rule_And.equals(name)) {
				expr = new BinaryPredicate(binaryPredicateTypes.and);
				expr.parse(node);
				expressions.add(expr);
			}
			else if (ParserConstant.Ele_Policy_Rule_Or.equals(name)) {
				expr = new BinaryPredicate(binaryPredicateTypes.or);
				expr.parse(node);
				expressions.add(expr);
			}
			else if (ParserConstant.Ele_Policy_Rule_Not.equals(name)) {
				expr = new BinaryPredicate(binaryPredicateTypes.not);
				expr.parse(node);
				expressions.add(expr);
			}
			else if (ParserConstant.Ele_Policy_Rule_Comparison.equals(name)) {
				expr = new BinaryComparison();
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (expressions == null) {
			sb.append("Predicate Error");
		}
		else {
			sb.append("(");
			sb.append(expressions.get(0));
			for (Expression<DataRef> expr : expressions.subList(1, expressions.size())) {
				sb.append(" " + binaryPredicateType + " ");
				sb.append(expr);
			}
			sb.append(")");
		}
		return sb.toString();
	}
}
