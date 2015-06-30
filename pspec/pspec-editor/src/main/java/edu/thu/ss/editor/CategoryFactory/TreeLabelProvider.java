package edu.thu.ss.editor.CategoryFactory;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import edu.thu.ss.spec.lang.pojo.IdentifiedObject;

public class TreeLabelProvider extends LabelProvider implements ILabelProvider {
		public String getText(Object element) {
			return ((IdentifiedObject) element).getId();
		}
	}