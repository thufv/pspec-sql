package edu.thu.ss.editor.model;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

/**
 * 
 */
public class EditorModel {

	private int nextVocabualryId = 1;

	private int nextPolicyId = 1;

	private List<VocabularyModel> vocabularies = new ArrayList<>();

	private List<PolicyModel> policies = new ArrayList<>();

	public List<VocabularyModel> getVocabularies() {
		return vocabularies;
	}

	public List<PolicyModel> getPolicies() {
		return policies;
	}

	public boolean containVocabulary(String path) {
		for (VocabularyModel model : vocabularies) {
			if (model.getPath().equals(path)) {
				return true;
			}
		}
		return false;
	}

	public boolean containPolicy(String path) {
		for (PolicyModel model : policies) {
			if (model.getPath().equals(path)) {
				return true;
			}
		}
		return false;
	}

	public VocabularyModel addVocabulary(Vocabulary vocabulary, String path) {
		VocabularyModel model = new VocabularyModel(vocabulary, path);
		vocabularies.add(model);
		return model;
	}

	public PolicyModel addPolicy(Policy policy, String path) {
		PolicyModel model = new PolicyModel(policy, path);
		policies.add(model);
		return model;
	}

	public String getNewVocabularyId() {
		return "Vocabulary" + (nextVocabualryId++);
	}

	public String getNewPolicyId() {
		return "Policy" + (nextPolicyId++);
	}

	public boolean hasOutput(OutputType type) {
		for (VocabularyModel vocabularyModel : vocabularies) {
			if (vocabularyModel.hasOutput(type)) {
				return true;
			}
		}
		for (PolicyModel policyModel : policies) {
			if (policyModel.hasOutput(type)) {
				return true;
			}
		}
		return false;
	}

	public List<OutputEntry> getOutput(OutputType type) {
		List<OutputEntry> result = new ArrayList<>();
		for (VocabularyModel vocabularyModel : vocabularies) {
			vocabularyModel.getOutput(type, result);
		}
		for (PolicyModel policyModel : policies) {
			policyModel.getOutput(type, result);
		}
		return result;
	}

	public int countOutput(OutputType type) {
		int count = 0;
		for (VocabularyModel vocabularyModel : vocabularies) {
			count += vocabularyModel.countOutput(type);
		}
		for (PolicyModel policyModel : policies) {
			count += policyModel.countOutput(type);
		}
		return count;
	}
}
