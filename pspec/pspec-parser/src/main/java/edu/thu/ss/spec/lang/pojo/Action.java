package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.Map;

public class Action extends HierarchicalObject<Action> {

	private static Map<String, Action> actions = new HashMap<>();

	public static final Action All;

	public static final Action Output;

	public static final Action Condition;

	public static final String Action_All = "all";
	public static final String Action_Output = "output";
	public static final String Action_Condition = "condition";

	static {
		All = new Action(Action_All);
		Output = new Action(Action_Output);
		Condition = new Action(Action_Condition);
		All.buildRelation(Output, Condition);
		actions.put(Action_All, All);
		actions.put(Action_Output, Output);
		actions.put(Action_Condition, Condition);

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
