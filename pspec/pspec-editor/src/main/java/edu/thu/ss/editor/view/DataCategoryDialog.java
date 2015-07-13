package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;

public class DataCategoryDialog extends EditorDialog {

	private DataCategory dataCategory;
	private DataContainer dataContainer;
	private Text dataId;

	public DataCategoryDialog(Shell parent, DataCategory dataCategory, DataContainer dataContainer) {
		super(parent);
		this.dataCategory = dataCategory;
		this.dataContainer = dataContainer;

		dialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		dialog.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		dialog.setText(getMessage(Data_Category));

		dialog.setLayout(new GridLayout(2, false));
		initializeContent(dialog);

		dialog.setSize(dialog.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		dialog.setMinimumSize(dialog.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		EditorUtil.centerLocation(dialog);
	}

	protected void initializeContent(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Data_Category_ID), EditorUtil.labelData());
		dataId = EditorUtil.newText(parent, EditorUtil.textData());
		dataId.setText(dataCategory.getId());
		GridData dataIdData = (GridData) dataId.getLayoutData();
		dataIdData.widthHint = 200;

		Composite buttons = new Composite(parent, SWT.RIGHT_TO_LEFT);
		buttons.setBackground(EditorUtil.getDefaultBackground());
		GridData buttonsData = new GridData(SWT.END, SWT.CENTER, true, false, 2, 1);
		buttons.setLayoutData(buttonsData);
		buttons.setLayout(new RowLayout());
		ok = EditorUtil.newButton(buttons, getMessage(OK));

		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				//check empty
				String text = dataId.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessageBox(dialog, "", getMessage(Data_Category_ID_Empty_Message));
					return;
				}
				//check duplicate
				if (dataContainer.get(text) != null) {
					EditorUtil.showMessageBox(dialog, "", getMessage(Data_Category_ID_Unique_Message, text));
					return;
				}
				dataCategory.setId(text);
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
