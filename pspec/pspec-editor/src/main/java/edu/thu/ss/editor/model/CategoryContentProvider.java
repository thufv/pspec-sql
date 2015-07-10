package edu.thu.ss.editor.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.HierarchicalObject;

public class CategoryContentProvider<T extends Category<T>> implements IStructuredContentProvider,
		ITreeContentProvider {

	private static Object[] empty = new Object[0];
	private CategoryContainer<T> container;

	public CategoryContentProvider(CategoryContainer<T> container) {
		this.container = container;
	}

	public Object[] getElements(Object inputElement) {
		CategoryContainer<?> container = (CategoryContainer<?>) inputElement;
		return container.materializeRoots().toArray();
	}

	public Object[] getChildren(Object parentElement) {
		T node = (T) parentElement;
		List<T> list = container.getChildren(node);
		if (list == null) {
			return empty;
		}
		return list.toArray();
	}

	@SuppressWarnings("rawtypes")
	public boolean hasChildren(Object element) {
		HierarchicalObject node = (HierarchicalObject) element;
		@SuppressWarnings("unchecked")
		List<HierarchicalObject> list = node.getChildren();
		return !(list == null || list.isEmpty());
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@SuppressWarnings("rawtypes")
	public Object getParent(Object element) {
		HierarchicalObject node = (HierarchicalObject) element;
		return node.getParent();
	}
}