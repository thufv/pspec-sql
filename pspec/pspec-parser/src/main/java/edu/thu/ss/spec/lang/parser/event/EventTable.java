package edu.thu.ss.spec.lang.parser.event;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.spec.lang.parser.PSpec.PSpecEventType;

public class EventTable<E extends ParseEvent> {
	private List<PSpecEventType> types = new ArrayList<>();
	private List<ParseListener<E>> listeners = new ArrayList<>();

	public void hook(PSpecEventType type, ParseListener<E> listener) {
		types.add(type);
		listeners.add(listener);
	}

	public void sendEvent(E event) {
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).equals(event.type)) {
				listeners.get(i).handleEvent(event);
			}
		}
	}

	public void unhook(PSpecEventType type, ParseListener<E> listener) {
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).equals(type) && listeners.get(i).equals(listener)) {
				types.remove(i);
				listeners.remove(i);
				return;
			}
		}
	}
}
