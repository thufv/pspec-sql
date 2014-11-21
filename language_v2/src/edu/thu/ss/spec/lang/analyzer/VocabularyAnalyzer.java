package edu.thu.ss.spec.lang.analyzer;

import java.util.Collection;

import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.UserContainer;

public class VocabularyAnalyzer {

	public void analyze(Collection<UserContainer> users, Collection<DataContainer> datas) {
		analyze(datas, new PropagationVisitor());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends CategoryContainer> void analyze(Collection<T> containers, CategoryVisitor visitor) {
		for (CategoryContainer container : containers) {
			container.accept(visitor);
		}
	}

	private class PropagationVisitor implements CategoryVisitor<DataCategory> {

		@Override
		public void visit(DataCategory category) {
			if (category.getParent() != null) {
				category.getOperations().addAll(category.getParent().getOperations());
			}
			if (category.getChildren() != null) {
				for (DataCategory child : category.getChildren()) {
					this.visit(child);
				}
			}
		}
	}

}
