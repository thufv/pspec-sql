package edu.thu.ss.xml.pojo;

import java.util.HashMap;
import java.util.Map;

public class Action extends HierarchicalObject {

	protected static Map<String, Action> actions;

	protected static Action root;

	public static final String Action_All = "all";
	public static final String Action_Select = "select";
	public static final String Action_Test = "test";
	public static final String Action_Equal_Test = "equal-test";
	public static final String Action_Range_Test = "range-test";
	public static final String Action_Bounded_Range_Test = "bounded-range-test";
	public static final String Action_Unbounded_Range_Test = "unbounded-range-test";

	static {
		root = new Action(Action_All);
		Action select = new Action(Action_Select);
		Action test = new Action(Action_Test);
		Action equal_test = new Action(Action_Equal_Test);
		Action range_test = new Action(Action_Range_Test);
		Action bounded_range_test = new Action(Action_Bounded_Range_Test);
		Action unbounded_range_test = new Action(Action_Unbounded_Range_Test);

		root.buildRelation(select, test);
		test.buildRelation(equal_test, range_test);
		range_test.buildRelation(bounded_range_test, unbounded_range_test);

		actions = new HashMap<>();
		actions.put(Action_All, root);
		actions.put(Action_Select, select);
		actions.put(Action_Test, test);
		actions.put(Action_Equal_Test, equal_test);
		actions.put(Action_Range_Test, range_test);
		actions.put(Action_Bounded_Range_Test, bounded_range_test);
		actions.put(Action_Unbounded_Range_Test, unbounded_range_test);
	}

	protected Action(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}

}
