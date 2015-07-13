package edu.thu.ss.editor.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import edu.thu.ss.editor.model.BaseModel;

public abstract class EditorView<T extends BaseModel> extends Composite {
	protected Shell shell;
	protected T model;
	protected OutputView outputView;

	public EditorView(Shell shell, Composite parent, T model, OutputView outputView) {
		super(parent, SWT.NONE);
		this.shell = shell;
		this.model = model;
		this.outputView = outputView;
	}

	public void refresh() {
		//TODO
	}

}
