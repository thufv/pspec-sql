package edu.thu.ss.editor.model;

public class OutputEntry {
	public static enum OutputType {
		warning,
		error
	};

	public static enum MessageType {
		Vocabulary,
		User_Category,
		User_Category_Duplicate,
		Data_Category,
		Data_Category_Duplicate,
		Rule_Ref,
		Rule_Restriction
	}

	private OutputEntry(String description, OutputType type, BaseModel location, BaseModel model,
			OutputListener listener, MessageType messageType, Object[] data) {
		this.description = description;
		this.outputType = type;
		this.listener = listener;
		this.location = location;
		this.model = model;
		this.messageType = messageType;
		this.data = data;
	}

	public static OutputEntry newInstance(String description, OutputType outputType, BaseModel model,
			OutputListener listener, MessageType messageType, Object... data) {
		return new OutputEntry(description, outputType, model, model, listener, messageType, data);
	}

	public static OutputEntry newInstance(String description, OutputType outputType,
			BaseModel location, BaseModel model, OutputListener listener, MessageType messageType,
			Object... data) {
		return new OutputEntry(description, outputType, location, model, listener, messageType, data);
	}

	public String description;
	public OutputType outputType;
	public OutputListener listener;
	public BaseModel location;
	public BaseModel model;
	public Object[] data;

	public final MessageType messageType;
}
