package edu.thu.ss.editor.model;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.IdentifiedObject;

public class CategoryLabelProvider extends LabelProvider implements IColorProvider {

	private CategoryContainer<?> container;

	public CategoryLabelProvider(CategoryContainer<?> container) {
		this.container = container;
	}

	public String getText(Object element) {
		return ((IdentifiedObject) element).getId();
	}

	@Override
	public Color getForeground(Object element) {
		IdentifiedObject obj = (IdentifiedObject) element;
		if (container.directContains(obj.getId())) {
			return SWTResourceManager.getColor(SWT.COLOR_BLACK);
		} else {
			return SWTResourceManager.getColor(SWT.COLOR_GRAY);
		}
	}

	@Override
	public Color getBackground(Object element) {
		return SWTResourceManager.getColor(SWT.COLOR_WHITE);
	}

}