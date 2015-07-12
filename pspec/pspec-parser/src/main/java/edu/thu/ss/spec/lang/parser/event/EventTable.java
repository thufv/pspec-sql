package edu.thu.ss.spec.lang.parser.event;

import java.util.ArrayList;
import java.util.List;

public class EventTable<E extends ParseEvent> {
	private List<Integer> types = new ArrayList<>();
	private List<ParseListener<E>> listeners = new ArrayList<>();

	public void hook(int type, ParseListener<E> listener) {
		types.add(type);
		listeners.add(listener);
	}

	public void sendEvent(E event) {
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i) == event.type) {
				listeners.get(i).handleEvent(event);
			}
		}
	}

	public void unhook(int type, ParseListener<E> listener) {
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i) == type && listeners.get(i).equals(listener)) {
				types.remove(i);
				listeners.remove(i);
				return;
			}
		}
	}
}
