package edu.thu.ss.editor.model;

import java.util.Set;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.IdentifiedObject;

public class CategoryLabelProvider<T extends Category<T>> extends LabelProvider implements
		IColorProvider {

	private CategoryContainer<T> container;

	private Set<String> errors;

	public CategoryLabelProvider(CategoryContainer<T> container, Set<String> errors) {
		this.container = container;
		this.errors = errors;
	}

	public String getText(Object element) {
		return ((IdentifiedObject) element).getId();
	}

	@Override
	public Color getForeground(Object element) {
		T category = (T) element;
		if (container.directContains(category)) {
			if (errors.contains(category.getId())) {
				return SWTResourceManager.getColor(SWT.COLOR_RED);
			} else {
				return SWTResourceManager.getColor(SWT.COLOR_BLACK);
			}
		} else {
			if (errors.contains(category.getId())) {
				return SWTResourceManager.getColor(203, 157, 157);
			} else {
				return SWTResourceManager.getColor(SWT.COLOR_GRAY);
			}
		}
	}

	@Override
	public Color getBackground(Object element) {
		return SWTResourceManager.getColor(SWT.COLOR_WHITE);
	}

}