package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.List;

public class OutputModel {

	private List<OutputEntry> warnings = new ArrayList<>();

	private List<OutputEntry> errors = new ArrayList<>();

	public List<OutputEntry> getWarnings() {
		return warnings;
	}

	public List<OutputEntry> getErrors() {
		return errors;
	}

	public void clear() {
		warnings.clear();
		errors.clear();
	}
}
