package edu.thu.ss.spec.lang.analyzer;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.PSpec.PSpecEventType;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.VocabularyErrorType;
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

	private EventTable table;

	public VocabularyAnalyzer(EventTable table) {
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
					table
							.onVocabularyError(VocabularyErrorType.Category_Parent_Not_Exist, category, parentId);
					error = true;
				}
			}
		}
		container.setResolved(true);
		return error;
	}

	public <T extends Category<T>> boolean checkDuplicateCategory(CategoryContainer<T> container) {
		boolean error = false;
		Set<String> categories = new HashSet<>();
		//check parent first

		CategoryContainer<T> current = container;
		while (current != null) {
			for (T category : current.getCategories()) {
				if (categories.contains(category.getId())) {
					logger.error("Duplicate category: {} is detected, please fix.", category.getId());
					table.onVocabularyError(VocabularyErrorType.Category_Duplicate, category, null);
					error = true;
				}
				categories.add(category.getId());
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
						table.onVocabularyError(VocabularyErrorType.Category_Cycle_Reference, category, null);
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
