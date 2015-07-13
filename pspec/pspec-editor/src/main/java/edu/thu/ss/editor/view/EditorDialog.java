package edu.thu.ss.editor.view;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class EditorDialog extends Dialog {

	protected Shell dialog;
	protected Button cancel;
	protected Button ok;
	protected int retCode;

	public EditorDialog(Shell parent) {
		super(parent);
	}
	
	public int open() {
		dialog.open();
		dialog.layout();
		Display display = getParent().getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return retCode;
	}


}
