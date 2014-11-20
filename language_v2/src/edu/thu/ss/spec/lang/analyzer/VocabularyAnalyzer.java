package edu.thu.ss.spec.lang.analyzer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.xml.XMLCategoryContainer;

public class VocabularyAnalyzer {

	public void analyze(Collection<UserContainer> users, Collection<DataContainer> datas) {
		analyze(users, new MaterializeVisitor<UserCategory>());

		analyze(datas, new MaterializeVisitor<DataCategory>());
		analyze(datas, new PropagationVisitor());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T extends XMLCategoryContainer> void analyze(Collection<T> containers, CategoryVisitor visitor) {
		for (XMLCategoryContainer container : containers) {
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

	private class MaterializeVisitor<T extends HierarchicalObject<T>> implements CategoryVisitor<T> {
		@Override
		public void visit(T category) {
			Set<T> decesdants = new HashSet<>();
			decesdants.add(category);
			category.setDecesdants(decesdants);
			if (category.getChildren() != null) {
				for (T child : category.getChildren()) {
					this.visit(child);
					decesdants.addAll(child.getDecesdants());
				}
			}
		}
	}

}
