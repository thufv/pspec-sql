package edu.thu.ss.editor.model;


public class OutputEntry {
	public static enum OutputType {
		warning,
		error,
		analysis
	};

	public static enum MessageType {
		Vocabulary,
		User_Category,
		User_Category_Duplicate,
		Data_Category,
		Data_Category_Duplicate,
		Rule_Ref,
		Rule_Restriction,
		Simplify
	}

	private OutputEntry(String description, OutputType type, BaseModel location, BaseModel model,
			FixListener fixListener, MessageType messageType, Object[] data) {
		this.description = description;
		this.outputType = type;
		this.fixListener = fixListener;
		this.location = location;
		this.model = model;
		this.messageType = messageType;
		this.data = data;
	}

	public static OutputEntry newInstance(String description, OutputType outputType, BaseModel model,
			MessageType messageType, Object... data) {
		return new OutputEntry(description, outputType, model, model, null, messageType, data);
	}

	public static OutputEntry newInstance(String description, OutputType outputType,
			BaseModel location, BaseModel model, MessageType messageType, Object... data) {
		return new OutputEntry(description, outputType, location, model, null, messageType, data);
	}

	public static OutputEntry newInstance(String description, OutputType outputType,
			BaseModel location, BaseModel model, FixListener fixListener, MessageType messageType,
			Object... data) {
		return new OutputEntry(description, outputType, location, model, fixListener, messageType, data);
	}

	public String description;
	public OutputType outputType;
	public FixListener fixListener;
	public BaseModel location;
	public BaseModel model;
	public Object[] data;

	public final MessageType messageType;

	public static interface FixListener {

		public void handleEvent(OutputEntry entry);
	}

}
