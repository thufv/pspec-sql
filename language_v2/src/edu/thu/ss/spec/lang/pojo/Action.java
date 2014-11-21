package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.Map;

public class Action extends HierarchicalObject<Action> {

	private static Map<String, Action> actions = new HashMap<>();

	public static final Action All;

	public static final Action Projection;

	public static final Action Condition;

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
		All = new Action(Action_All);
		Projection = new Action(Action_Projection);
		Condition = new Action(Action_Condition);
		All.buildRelation(Projection, Condition);
		actions.put(Action_All, All);
		actions.put(Action_Projection, Projection);
		actions.put(Action_Condition, Condition);
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

	public static Action get(String id) {
		return actions.get(id);
	}

	protected Action(String id) {
		this.id = id;

	}

	@Override
	public String toString() {
		return id;
	}

}
