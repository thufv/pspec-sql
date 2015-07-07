package edu.thu.ss.editor;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Test {

	protected static Shell shell;

	protected static Display display;

	/**
	 * Open the window.
	 */
	public static void main1(String[] args) {
		display = Display.getDefault();

		shell = new Shell();
		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout());

		Button button = new Button(composite, SWT.NONE);
		button.setText("luochen");

		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		shell.open();
		shell.layout();
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Display.getDefault().dispose();
				System.exit(0);
			}
		});

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public class MyModel {
		public int counter;

		public MyModel(int counter) {
			this.counter = counter;
		}

		@Override
		public String toString() {
			return "Item " + this.counter;
		}
	}

	public Test(Shell shell) {
		final TableViewer v = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		v.setContentProvider(new ArrayContentProvider());

		TableViewerColumn viewerColumn = new TableViewerColumn(v, SWT.NONE);
		viewerColumn.getColumn().setText("Column1");
		viewerColumn.getColumn().setWidth(300);
		viewerColumn.setLabelProvider(new ColumnLabelProvider());
		viewerColumn.setEditingSupport(new EditingSupport(v) {

			@Override
			protected void setValue(Object element, Object value) {
				((MyModel) element).counter = Integer.parseInt(value.toString());
				getViewer().update(element, null);
			}

			@Override
			protected Object getValue(Object element) {
				return ((MyModel) element).counter + "";
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor((Composite) getViewer().getControl());
			}

			@Override
			protected boolean canEdit(Object element) {
				return ((MyModel) element).counter % 2 == 0;
			}
		});

		MyModel[] model = createModel();
		v.setInput(model);
		v.getTable().setLinesVisible(true);
	}

	private MyModel[] createModel() {
		MyModel[] elements = new MyModel[10];

		for (int i = 0; i < 10; i++) {
			elements[i] = new MyModel(i);
		}

		return elements;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new Test(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();

	}

}
