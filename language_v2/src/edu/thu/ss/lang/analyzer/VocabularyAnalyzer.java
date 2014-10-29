package edu.thu.ss.lang.analyzer;

import java.util.HashSet;
import java.util.Set;

import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.HierarchicalObject;
import edu.thu.ss.lang.pojo.UserCategory;
import edu.thu.ss.lang.xml.XMLDataCategoryContainer;
import edu.thu.ss.lang.xml.XMLUserCategoryContainer;

public class VocabularyAnalyzer {

	public void analyze(XMLUserCategoryContainer userContainer, XMLDataCategoryContainer dataContainer) {
		userContainer.accept(new MaterializeVisitor<UserCategory>());
		userContainer.accept(new LabelVisitor<UserCategory>());

		dataContainer.accept(new MaterializeVisitor<DataCategory>());
		dataContainer.accept(new LabelVisitor<DataCategory>());
		dataContainer.accept(new PropagationVisitor());
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

	private class LabelVisitor<T extends HierarchicalObject<T>> implements CategoryVisitor<T> {
		private int used = 0;

		@Override
		public void visit(T category) {
			category.setLabel(used++);
			if (category.getChildren() != null) {
				for (T child : category.getChildren()) {
					this.visit(child);
				}
			}
		}

	}

	public void label() {

	}

}
