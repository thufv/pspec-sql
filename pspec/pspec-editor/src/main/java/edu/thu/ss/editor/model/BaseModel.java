package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.thu.ss.editor.model.OutputEntry.OutputType;

public abstract class BaseModel {

	protected String path;

	/**
	 * lazy initialization
	 */
	protected Map<OutputType, List<OutputEntry>> outputs;

	public BaseModel(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean hasOutput() {
		return outputs != null && !outputs.isEmpty();
	}

	public void getOutput(OutputType type, List<OutputEntry> list) {
		if (outputs == null) {
			return;
		}
		List<OutputEntry> entries = outputs.get(type);
		if (entries != null) {
			list.addAll(entries);
		}
	}

	public void getOutput(List<OutputEntry> list) {
		for (OutputType type : OutputType.values()) {
			getOutput(type, list);
		}
	}

	public void addOutput(OutputEntry entry) {
		if (outputs == null) {
			outputs = new HashMap<>();
		}
		List<OutputEntry> list = outputs.get(entry.outputType);
		if (list == null) {
			list = new ArrayList<>();
			outputs.put(entry.outputType, list);
		}
		list.add(entry);
	}

	public void clearOutput() {
		if (outputs == null) {
			return;
		}
		for (List<OutputEntry> list : outputs.values()) {
			list.clear();
		}
		outputs.clear();
	}

	public void clearOutput(OutputType type) {
		if (outputs == null) {
			return;
		}
		List<OutputEntry> list = outputs.remove(type);
		if (list != null) {
			list.clear();
		}

	}

	public boolean hasOutput(OutputType type) {
		if (outputs == null) {
			return false;
		}
		return outputs.containsKey(type);
	}

	public int countOutput(OutputType type) {
		if (outputs == null) {
			return 0;
		}
		List<OutputEntry> list = outputs.get(type);
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

}
