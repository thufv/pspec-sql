package edu.thu.ss.editor.model;

import org.eclipse.swt.widgets.Event;

public interface OutputListener {

	public void handleEvent(OutputEntry entry, Event event);
}
