package edu.thu.ss.spec.lang.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.parser.ParserConstant;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class Or extends Predicate {

	public Or() {
		symbol = "Or";
	}
	
	@Override
	public boolean isBinary() {
		return true;
	}

	@Override
	public Set<DataCategory> getDataSet() {
		Set<DataCategory> dataSet = null;
		for (Expression<DataCategory> expr : expressions) {
			Set<DataCategory> set = expr.getDataSet();
			if (dataSet == null) {
				dataSet = set;
			} else {
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
				expressions.add(expr);
			}
		}
	}
	
	@Override
	public List<Expression<DataCategory>> split() {
		List<Expression<DataCategory>> list = new ArrayList<>();
		list.add(this);
		return list;
	}
}
