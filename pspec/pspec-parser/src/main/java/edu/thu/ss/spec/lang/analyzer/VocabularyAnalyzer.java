package edu.thu.ss.spec.lang.analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.PSpec.PSpecEventType;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.VocabularyEvent;
import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.util.PSpecUtil;

/**
 * utility class for analyzing PSpec categories/rules
 * @author luochen
 *
 */
public class VocabularyAnalyzer {

	private static Logger logger = LoggerFactory.getLogger(VocabularyAnalyzer.class);

	private EventTable<VocabularyEvent> table;

	public VocabularyAnalyzer(EventTable<VocabularyEvent> table) {
		this.table = table;
	}

	public <T extends Category<T>> boolean analyze(CategoryContainer<T> container, boolean refresh) {
		boolean error = false;
		error = error || resolveReference(container, refresh);
		error = error || checkDuplicateCategory(container);
		error = error || checkCategoryCycleReference(container);
		return error;
	}

	public <T extends Category<T>> boolean resolveReference(CategoryContainer<T> container,
			boolean refresh) {
		boolean error = false;
		if (container.isResolved() && !refresh) {
			return error;
		}

		CategoryContainer<T> baseContainer = container.getBaseContainer();
		if (baseContainer != null) {
			error = error || resolveReference(baseContainer, refresh);
		}
		// resolve parent reference of all categories
		for (T category : container.getCategories()) {
			String parentId = category.getParentId();
			if (!parentId.isEmpty()) {
				T parent = container.get(parentId);
				if (parent != null) {
					parent.buildRelation(category);
				} else {
					logger.error("Fail to locate parent category: {} for category: {}.", parentId,
							category.getId());
					VocabularyEvent event = new VocabularyEvent(
							PSpecEventType.Vocabulary_Category_Parent_Not_Exist, null, null, category, parentId);
					table.sendEvent(event);
					error = true;
				}
			}
		}
		container.setResolved(true);
		return error;
	}

	public <T extends Category<T>> boolean checkDuplicateCategory(CategoryContainer<T> container) {
		boolean error = false;
		Map<String, T> categories = new HashMap<>();

		CategoryContainer<T> current = container;
		while (current != null) {
			for (T category : current.getCategories()) {
				if (categories.containsKey(category.getId())) {
					logger.error("Duplicate category: {} is detected, please fix.", category.getId());
					VocabularyEvent event = new VocabularyEvent(PSpecEventType.Vocabulary_Category_Duplicate,
							null, null, category);
					table.sendEvent(event);
					error = true;
				}
				categories.put(category.getId(), category);
			}
			current = current.getBaseContainer();
		}
		return error;
	}

	public <T extends Category<T>> boolean checkCategoryCycleReference(CategoryContainer<T> container) {
		boolean error = false;
		Set<T> checked = new HashSet<>();
		CategoryContainer<T> current = container;

		while (current != null) {
			for (T category : current.getCategories()) {
				if (!checked.contains(category)) {
					if (PSpecUtil.checkCategoryCycleReference(category, category.getParent(), checked)) {
						logger.error("Cycle reference is detected in category: {}, please fix.",
								category.getId());
						error = true;
						VocabularyEvent event = new VocabularyEvent(
								PSpecEventType.Vocabulary_Category_Cycle_Reference, null, null, category, container);
						table.sendEvent(event);
						//fix
						current.setParent(category, null);
					}
				}

			}
			current = current.getBaseContainer();
		}
		return error;
	}

}
