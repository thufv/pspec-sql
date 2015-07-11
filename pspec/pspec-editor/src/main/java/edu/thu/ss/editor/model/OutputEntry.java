package edu.thu.ss.editor.model;

import org.eclipse.swt.widgets.Listener;

public class OutputEntry {
	public static enum OutputType {

	};

	public static enum MessageType {
		warning, error
	}

	public OutputEntry(String description, OutputType type, Listener listener, MessageType messageType) {
		this.description = description;
		this.outputType = type;
		this.listener = listener;
		this.messageType = messageType;
	}

	public String description;
	public OutputType outputType;
	public Listener listener;

	public final MessageType messageType;
}
