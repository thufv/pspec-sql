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
import edu.thu.ss.spec.lang.pojo.UserContainer;

public class UserCategoryDialog extends EditorDialog {

	private UserCategory userCategory;
	private UserContainer userContainer;

	private Text userId;

	public UserCategoryDialog(Shell parent, UserCategory userCategory, UserContainer userContainer) {
		super(parent);
		this.userCategory = userCategory;
		this.userContainer = userContainer;

		dialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		dialog.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		dialog.setText(getMessage(User_Category));

		dialog.setLayout(new GridLayout(2, false));
		initializeContent(dialog);

		dialog.setSize(dialog.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		dialog.setMinimumSize(dialog.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		EditorUtil.centerLocation(dialog);
	}

	protected void initializeContent(Composite parent) {

		EditorUtil.newLabel(parent, getMessage(User_Category_ID), EditorUtil.labelData());
		userId = EditorUtil.newText(parent, EditorUtil.textData());
		userId.setText(userCategory.getId());
		GridData userIdData = (GridData) userId.getLayoutData();
		userIdData.widthHint = 200;

		Composite buttons = new Composite(parent, SWT.RIGHT_TO_LEFT);
		buttons.setBackground(EditorUtil.getDefaultBackground());
		GridData buttonsData = new GridData(SWT.END, SWT.CENTER, true, false, 2, 1);
		buttons.setLayoutData(buttonsData);
		buttons.setLayout(new RowLayout());
		ok = EditorUtil.newButton(buttons, getMessage(OK));

		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//check empty
				String text = userId.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessageBox(dialog, "", getMessage(User_Category_ID_Empty_Message));
					return;
				}
				//check duplicate
				if (userContainer.get(text) != null) {
					EditorUtil.showMessageBox(dialog, "", getMessage(User_Category_ID_Unique_Message, text));
					return;
				}
				userCategory.setId(text);
				retCode = SWT.OK;
				dialog.dispose();
			}
		});

		cancel = EditorUtil.newButton(buttons, getMessage(Cancel));
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				retCode = SWT.CANCEL;
				dialog.dispose();
			}
		});

	}
}
