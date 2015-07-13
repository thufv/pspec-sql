package edu.thu.ss.spec.lang.parser.event;

import java.util.ArrayList;
import java.util.List;

import edu.thu.ss.spec.lang.parser.event.PSpecListener.RefErrorType;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.RestrictionErrorType;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.VocabularyErrorType;
import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;

public class EventTable {
	private List<PSpecListener> listeners = new ArrayList<>();

	public void add(PSpecListener listener) {
		listeners.add(listener);
	}

	public void remove(PSpecListener listener) {
		listeners.remove(listener);
	}

	public void onVocabularyError(VocabularyErrorType type, Category<?> category, String refid) {
		for (PSpecListener listener : listeners) {
			listener.onVocabularyError(type, category, refid);
		}
	}

	public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {
		for (PSpecListener listener : listeners) {
			listener.onRuleRefError(type, rule, ref, refid);
		}
	}

	public void onRestrictionError(RestrictionErrorType type, Rule rule, Restriction res) {
		for (PSpecListener listener : listeners) {
			listener.onRestrictionError(type, rule, res);
		}

	}

}
