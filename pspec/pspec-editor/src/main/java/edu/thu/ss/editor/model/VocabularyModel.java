package edu.thu.ss.editor.model;

import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class VocabularyModel extends BaseModel {
	protected Vocabulary vocabulary;

	public VocabularyModel(Vocabulary vocabulary, String path) {
		super(path);
		this.vocabulary = vocabulary;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}


}
