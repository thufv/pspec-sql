package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public class UserCategoryDialog extends Dialog {

	protected Shell dialog;
	private UserCategory userCategory;

	private Text userId;
	private Text parentId;
	private Text shortDescription;
	private Text longDiscription;
	private Button cancel;
	private Button ok;

	public UserCategoryDialog(Shell parent, UserCategory userCategory) {
		super(parent, SWT.NONE);
		this.userCategory = userCategory;

		dialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		dialog.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Display display = Display.getCurrent();
		dialog.setSize(display.getClientArea().width / 3, display.getClientArea().height / 2);
		dialog.setMinimumSize(display.getClientArea().width / 3, display.getClientArea().height / 2);

		dialog.setText(getMessage(User_Category));
	}

	public void open() {
		initializeContent();
		dialog.open();
		dialog.layout();
		Display display = getParent().getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void initializeContent() {
		dialog.setLayout(new GridLayout(2, false));

		EditorUtil.newLabel(dialog, getMessage(User_Category_ID), EditorUtil.labelData());
		userId = EditorUtil.newText(dialog, EditorUtil.textData());

		EditorUtil.newLabel(dialog, getMessage(User_Category_Parent_ID), EditorUtil.labelData());
		parentId = EditorUtil.newText(dialog, EditorUtil.textData());

		EditorUtil.newLabel(dialog, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(dialog, EditorUtil.textData());

		EditorUtil.newLabel(dialog, getMessage(Long_Description), EditorUtil.labelData());
		longDiscription = new Text(dialog, SWT.BORDER | SWT.V_SCROLL);
		GridData longData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		longData.minimumHeight = 100;
		longDiscription.setLayoutData(longData);

		Composite buttons = new Composite(dialog, SWT.RIGHT_TO_LEFT);
		buttons.setBackground(EditorUtil.getDefaultBackground());
		GridData buttonsData = new GridData(SWT.END, SWT.CENTER, true, false, 2, 1);
		buttons.setLayoutData(buttonsData);
		buttons.setLayout(new RowLayout());
		ok = EditorUtil.newButton(buttons, getMessage(OK));

		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialog.dispose();
			}
		});

		cancel = EditorUtil.newButton(buttons, getMessage(Cancel));
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dialog.dispose();
			}
		});

	}

}
