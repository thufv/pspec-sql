package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

/**
 * 
 */
public class EditorModel {
	private List<Vocabulary> vocabularies = new ArrayList<>();

	private List<Policy> policies = new ArrayList<>();

	public List<Policy> getPolicies() {
		return policies;
	}

	public List<Vocabulary> getVocabularies() {
		return vocabularies;
	}

}
