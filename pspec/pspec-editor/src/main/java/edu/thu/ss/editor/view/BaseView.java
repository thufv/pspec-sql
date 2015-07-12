package edu.thu.ss.editor.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import edu.thu.ss.editor.model.BaseModel;

public abstract class BaseView<T extends BaseModel> extends Composite {
	protected Shell shell;
	protected T model;

	public BaseView(Shell shell, Composite parent, int style, T model) {
		super(parent, style);
		this.shell = shell;
		this.model = model;
	}

	public void refresh() {

	}

}
