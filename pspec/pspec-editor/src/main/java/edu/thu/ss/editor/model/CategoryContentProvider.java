package edu.thu.ss.editor.model;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.thu.ss.spec.lang.pojo.HierarchicalObject;

public class CategoryContentProvider implements IStructuredContentProvider,ITreeContentProvider {
	
	@SuppressWarnings("rawtypes")
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			@SuppressWarnings("unchecked")
			List<HierarchicalObject> input = (List<HierarchicalObject>) inputElement;
			return input.toArray();
		}
		return new Object[0];
	}

	@SuppressWarnings("rawtypes")
	public Object[] getChildren(Object parentElement) {
		HierarchicalObject node = (HierarchicalObject) parentElement;
		@SuppressWarnings("unchecked")
		List<HierarchicalObject> list = node.getChildren();
		if (list == null) {
			return new Object[0];
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

	public Object getParent(Object element) {
		return null;
	}
}