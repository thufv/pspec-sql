package edu.thu.ss.editor.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class VocabularyModel extends BaseModel {
	protected Vocabulary vocabulary;
	protected Set<String> errors = new HashSet<>();

	public VocabularyModel(Vocabulary vocabulary, String path) {
		super(path);
		this.vocabulary = vocabulary;
	}

	public VocabularyModel(String path) {
		super(path);
	}

	public void init(Vocabulary vocabulary) {
		this.vocabulary = vocabulary;
	}

	@Override
	public void clearOutput() {
		super.clearOutput();
		errors.clear();
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

	public Set<String> getErrors() {
		return errors;
	}

	public boolean clearOutputByCategory(String id, MessageType messageType) {
		boolean found = false;
		if (outputs == null) {
			return found;
		}
		for (OutputType type : OutputType.values()) {
			List<OutputEntry> list = outputs.get(type);
			if (list == null) {
				continue;
			}
			Iterator<OutputEntry> it = list.iterator();
			while (it.hasNext()) {
				OutputEntry entry = it.next();
				if (entry.messageType.equals(messageType) && entry.data.length > 0
						&& id.equals(entry.data[0])) {
					found = true;
					it.remove();
				}
			}
		}
		return found;

	}
}
