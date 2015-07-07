package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class DataCategoryDialog extends Dialog {

	protected Shell dialog;
	private DataCategory dataCategory;

	private Text dataId;
	private Text parentId;
	private Text shortDescription;
	private Text longDiscription;
	private Button cancel;
	private Button ok;
	private Text desensitizeOperation;
	private List operations;

	public DataCategoryDialog(Shell parent, DataCategory dataCategory) {
		super(parent, SWT.NONE);
		this.dataCategory = dataCategory;

		dialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		dialog.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Display display = Display.getCurrent();
		dialog.setSize(display.getClientArea().width / 3, display.getClientArea().height / 2);
		dialog.setMinimumSize(display.getClientArea().width / 3, display.getClientArea().height / 2);

		dialog.setText(getMessage(Data_Category));
		dialog.setLayout(new GridLayout(2, false));
	}

	public void open() {
		initializeContent(dialog);

		dialog.open();
		dialog.layout();
		Display display = getParent().getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void initializeContent(final Composite parent) {

		EditorUtil.newLabel(parent, getMessage(Data_Category_ID), EditorUtil.labelData());
		dataId = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Data_Category_Parent_ID), EditorUtil.labelData());
		parentId = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Desensitize_Operation), EditorUtil.labelData());

		//desensitizations
		final Composite operationComposite = new Composite(parent, SWT.NO_BACKGROUND);
		operationComposite.setSize(200, 400);

		GridLayout operationLayout = new GridLayout(3, false);
		operationLayout.marginHeight = 0;
		operationLayout.marginWidth = 0;
		operationLayout.horizontalSpacing = 0;
		operationLayout.verticalSpacing = 0;
		operationComposite.setLayout(operationLayout);

		desensitizeOperation = EditorUtil.newText(operationComposite, EditorUtil.textData());

		Button add = new Button(operationComposite, SWT.PUSH);
		add.setImage(SWTResourceManager.getImage(EditorUtil.Image_Add_Button));
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				operations.add(desensitizeOperation.getText());
			}
		});

		Button delete = new Button(operationComposite, SWT.PUSH);
		delete.setImage(SWTResourceManager.getImage(EditorUtil.Image_Delete_Button));
		delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
			}
		});

		operations = new List(operationComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		GridData listData = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		listData.heightHint = 100;
		operations.setLayoutData(listData);

		GridData compositeData = new GridData();
		compositeData.horizontalAlignment = SWT.FILL;
		compositeData.heightHint = operationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		operationComposite.setLayoutData(compositeData);

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDiscription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		GridData longData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		longData.minimumHeight = 100;
		longDiscription.setLayoutData(longData);

		Composite buttons = new Composite(parent, SWT.RIGHT_TO_LEFT);
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
