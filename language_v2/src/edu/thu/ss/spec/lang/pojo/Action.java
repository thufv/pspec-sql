package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.Map;

public class Action extends HierarchicalObject<Action> {

	public static Map<String, Action> actions = new HashMap<>();

	public static Action all;

	public static final String Action_All = "all";
	public static final String Action_Projection = "projection";
	public static final String Action_Condition = "condition";

	/**
	 * Unused
	 */
	public static final String Action_Equal_Test = "equal-test";
	public static final String Action_Range_Test = "range-test";
	public static final String Action_Bounded_Range_Test = "bounded-range-test";
	public static final String Action_Unbounded_Range_Test = "unbounded-range-test";

	static {
		all = new Action(Action_All);
		Action project = new Action(Action_Projection);
		Action condition = new Action(Action_Condition);
		all.buildRelation(project, condition);
		actions.put(Action_All, all);
		actions.put(Action_Projection, project);
		actions.put(Action_Condition, condition);
		/*
		Action equal_test = new Action(Action_Equal_Test);
		Action range_test = new Action(Action_Range_Test);
		Action bounded_range_test = new Action(Action_Bounded_Range_Test);
		Action unbounded_range_test = new Action(Action_Unbounded_Range_Test);
		
		test.buildRelation(equal_test, range_test);
		range_test.buildRelation(bounded_range_test, unbounded_range_test);
		
		
		actions.put(Action_Equal_Test, equal_test);
		actions.put(Action_Range_Test, range_test);
		actions.put(Action_Bounded_Range_Test, bounded_range_test);
		actions.put(Action_Unbounded_Range_Test, unbounded_range_test);
		*/

	}

	protected Action(String id) {
		this.id = id;

	}

	@Override
	public String toString() {
		return id;
	}

}
