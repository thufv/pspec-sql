package edu.thu.ss.editor.model;

import org.eclipse.jface.viewers.LabelProvider;

import edu.thu.ss.spec.lang.pojo.IdentifiedObject;

public class CategoryLabelProvider extends LabelProvider {

	public String getText(Object element) {
		return ((IdentifiedObject) element).getId();
	}
}