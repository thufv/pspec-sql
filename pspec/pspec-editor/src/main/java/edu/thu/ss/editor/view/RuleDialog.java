package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserRef;

public class RuleDialog extends Dialog {

	protected Shell dialog;
	private Rule rule;

	private Text ruleId;
	private Text shortDescription;
	private Text longDiscription;

	private Button dataSingleType;
	private Button dataAssociationType;

	private Button addUser;
	private Button deleteUser;
	private Table userTable;

	private Table dataTable;
	private Button addData;
	private Button deleteData;

	private Button addRestriction;
	private Button deleteRestriction;

	private Button cancel;
	private Button ok;

	private Button restrictType;
	private Button forbidType;

	private List<UserRef> userRefs = new ArrayList<>();

	private List<Table> restrictTables = new ArrayList<>();

	private Composite restrictComposite;

	private ScrolledComposite scroll;

	private Composite scrollContent;

	public RuleDialog(Shell parent, Rule rule) {
		super(parent, SWT.NONE);
		this.rule = rule;

		dialog = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.RESIZE);
		dialog.setBackground(EditorUtil.getDefaultBackground());
		Display display = Display.getCurrent();
		dialog.setSize(display.getClientArea().width / 2, display.getClientArea().height * 2 / 3);
		dialog
				.setMinimumSize(display.getClientArea().width / 3, display.getClientArea().height * 2 / 3);

		dialog.setText(getMessage(Rule));
		dialog.setLayout(new FillLayout());
	}

	public void open() {
		scroll = new ScrolledComposite(dialog, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER
				| SWT.NO_BACKGROUND);
		scroll.setBackground(EditorUtil.getDefaultBackground());
		scrollContent = EditorUtil.newComposite(scroll);

		scrollContent.setLayout(new GridLayout(2, false));

		initializeContent(scrollContent);

		scroll.setContent(scrollContent);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		scroll.setMinSize(scrollContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		dialog.open();
		dialog.layout();
		Display display = getParent().getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void layoutScroll() {
		//scroll.setMinSize();
		scroll.setMinHeight(scrollContent.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);

	}

	protected void initializeContent(final Composite parent) {

		EditorUtil.newLabel(parent, getMessage(Rule_ID), EditorUtil.labelData());
		ruleId = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDiscription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		GridData longData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		longData.minimumHeight = 100;
		longDiscription.setLayoutData(longData);

		initializeUser(parent);
		initializeData(parent);
		initializeRestrictions(parent);

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

	protected void initializeUser(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(User_Ref), EditorUtil.labelData());

		Composite userComposite = newComposite(parent);

		newDummyLabel(userComposite);
		addUser = EditorUtil.newButton(userComposite, getMessage(Add));
		deleteUser = EditorUtil.newButton(userComposite, getMessage(Delete));

		Composite tableComposite = newTableComposite(userComposite);
		TableColumnLayout tableLayout = new TableColumnLayout();
		tableComposite.setLayout(tableLayout);

		userTable = newTable(tableComposite);

		TableColumn[] columns = new TableColumn[5];
		String[] titles = new String[] { getMessage(User_Category), getMessage(Exclude), "", "", "" };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(userTable, SWT.NONE);
			columns[i].setText(titles[i]);
			columns[i].setResizable(false);
		}
		//add a new row
		addUserRow();
		tableLayout.setColumnData(columns[0], new ColumnWeightData(1, columns[0].getWidth()));
		tableLayout.setColumnData(columns[1], new ColumnWeightData(2, columns[1].getWidth()));
		tableLayout.setColumnData(columns[2], new ColumnWeightData(1, columns[2].getWidth()));
		tableLayout.setColumnData(columns[3], new ColumnWeightData(0));
		tableLayout.setColumnData(columns[4], new ColumnWeightData(0));

		addUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addUserRow();
			}
		});

		GridData userData = (GridData) userComposite.getLayoutData();
		userData.heightHint = userComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;

	}

	protected void initializeData(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Data_Ref), EditorUtil.labelData());

		Composite dataComposite = newComposite(parent);

		Composite typeComposite = newRadioComposite(dataComposite);
		dataSingleType = EditorUtil.newRadio(typeComposite, getMessage(Single));
		dataSingleType.setSelection(true);
		dataAssociationType = EditorUtil.newRadio(typeComposite, getMessage(Association));

		addData = EditorUtil.newButton(dataComposite, getMessage(Add));
		deleteData = EditorUtil.newButton(dataComposite, getMessage(Delete));

		Composite tableComposite = newTableComposite(dataComposite);
		TableColumnLayout tableLayout = new TableColumnLayout();
		tableComposite.setLayout(tableLayout);

		dataTable = newTable(tableComposite);

		TableColumn[] columns = new TableColumn[6];
		String[] titles = new String[] { getMessage(Data_Category), getMessage(ACTION),
				getMessage(Exclude), "", "", "" };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(dataTable, SWT.NONE);
			columns[i].setText(titles[i]);
			columns[i].setResizable(false);
		}
		//add a new row
		addDataRow();
		tableLayout.setColumnData(columns[0], new ColumnWeightData(1, columns[0].getWidth()));
		tableLayout.setColumnData(columns[1], new ColumnWeightData(1, columns[1].getWidth()));
		tableLayout.setColumnData(columns[2], new ColumnWeightData(2, columns[2].getWidth()));
		tableLayout.setColumnData(columns[3], new ColumnWeightData(1, columns[2].getWidth()));
		tableLayout.setColumnData(columns[4], new ColumnWeightData(0));
		tableLayout.setColumnData(columns[5], new ColumnWeightData(0));

		addData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addDataRow();
			}
		});

		GridData dataData = (GridData) dataComposite.getLayoutData();
		dataData.heightHint = dataComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;

	}

	private void initializeRestrictions(final Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Rule_Type), EditorUtil.labelData());

		final Composite composite = newComposite(parent);

		Composite typeComposite = newRadioComposite(composite);
		forbidType = EditorUtil.newRadio(typeComposite, getMessage(Forbid));
		forbidType.setSelection(true);
		restrictType = EditorUtil.newRadio(typeComposite, getMessage(Restrict));

		addRestriction = EditorUtil.newButton(composite, getMessage(Add));
		deleteRestriction = EditorUtil.newButton(composite, getMessage(Delete));

		restrictComposite = newComposite(composite);
		GridData restrictData = (GridData) restrictComposite.getLayoutData();
		restrictData.horizontalSpan = 3;
		addRestrictTable(restrictComposite);

		exclude(addRestriction);
		exclude(deleteRestriction);
		exclude(restrictComposite);

		forbidType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exclude(addRestriction);
				exclude(deleteRestriction);
				exclude(restrictComposite);
				resize(composite);
				parent.pack();
				layoutScroll();
			}
		});

		restrictType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				include(addRestriction);
				include(deleteRestriction);
				include(restrictComposite);
				resize(composite);
				parent.pack();
				layoutScroll();
			}
		});

		addRestriction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addRestrictTable(restrictComposite);
				resize(composite);
				parent.pack();
				layoutScroll();
			}
		});

		resize(composite);
	}

	private void resize(Composite composite) {
		GridData dataData = (GridData) composite.getLayoutData();
		dataData.heightHint = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
	}

	private void exclude(Control composite) {
		composite.setVisible(false);
		GridData data = (GridData) composite.getLayoutData();
		if (data == null) {
			data = new GridData();
			composite.setLayoutData(data);
		}
		data.exclude = true;
	}

	private void include(Control composite) {
		composite.setVisible(true);
		GridData data = (GridData) composite.getLayoutData();
		if (data == null) {
			data = new GridData();
			composite.setLayoutData(data);
		}
		data.exclude = false;
	}

	private void addRestrictTable(Composite parent) {
		Composite tableComposite = newTableComposite(parent);
		TableColumnLayout tableLayout = new TableColumnLayout();
		tableComposite.setLayout(tableLayout);

		Table restrictTable = newTable(tableComposite);
		restrictTables.add(restrictTable);
		TableColumn[] columns = new TableColumn[5];
		String[] titles = new String[] { getMessage(Data_Category), getMessage(Desensitize_Operation),
				"", "", "" };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TableColumn(restrictTable, SWT.NONE);
			columns[i].setText(titles[i]);
			columns[i].setResizable(false);
		}
		//add a new row
		addRestrictRow(restrictTable);
		tableLayout.setColumnData(columns[0], new ColumnWeightData(1, columns[0].getWidth()));
		tableLayout.setColumnData(columns[1], new ColumnWeightData(2, columns[2].getWidth()));
		tableLayout.setColumnData(columns[2], new ColumnWeightData(1, columns[2].getWidth()));
		tableLayout.setColumnData(columns[3], new ColumnWeightData(0));
		tableLayout.setColumnData(columns[4], new ColumnWeightData(0));
	}

	private void addRestrictRow(Table table) {
		TableItem item = new TableItem(table, SWT.NULL);
		Combo dataCategory = EditorUtil.newCombo(table, null);
		newTableEditor(table, dataCategory, item, 0);

		item.setText(1, "test1");

		Combo operation = EditorUtil.newCombo(table, null);
		newTableEditor(table, operation, item, 2);

		Button addOperation = EditorUtil.newButton(table, "+");
		newTableEditor(table, addOperation, item, 3);

		Button deleteOperation = EditorUtil.newButton(table, "-");
		newTableEditor(table, deleteOperation, item, 4);
	}

	private void addUserRow() {
		TableItem item = new TableItem(userTable, SWT.NULL);
		Combo userCategory = EditorUtil.newCombo(userTable, null);
		newTableEditor(userTable, userCategory, item, 0);

		item.setText(1, "test1");

		Combo excludeUser = EditorUtil.newCombo(userTable, null);
		newTableEditor(userTable, excludeUser, item, 2);

		Button addExclude = EditorUtil.newButton(userTable, "+");
		newTableEditor(userTable, addExclude, item, 3);

		Button deleteExclude = EditorUtil.newButton(userTable, "-");
		newTableEditor(userTable, deleteExclude, item, 4);
	}

	private void addDataRow() {
		TableItem item = new TableItem(dataTable, SWT.NULL);
		Combo dataCategory = EditorUtil.newCombo(dataTable, null);
		newTableEditor(dataTable, dataCategory, item, 0);

		Combo action = EditorUtil.newCombo(dataTable, null);
		newTableEditor(dataTable, action, item, 1);

		item.setText(2, "test1");

		Combo excludeData = EditorUtil.newCombo(dataTable, null);
		newTableEditor(dataTable, excludeData, item, 3);

		Button addExclude = EditorUtil.newButton(dataTable, "+");
		newTableEditor(dataTable, addExclude, item, 4);

		Button deleteExclude = EditorUtil.newButton(dataTable, "-");
		newTableEditor(dataTable, deleteExclude, item, 5);

	}

	protected Composite newComposite(Composite parent) {
		Composite composite = EditorUtil.newComposite(parent);
		GridLayout layout = EditorUtil.newNoMarginGridLayout(3, false);
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		composite.setLayoutData(data);
		return composite;
	}

	protected Label newDummyLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		label.setLayoutData(data);
		return label;
	}

	protected Composite newTableComposite(Composite parent) {
		Composite composite = EditorUtil.newComposite(parent);
		GridData tableData = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		tableData.heightHint = 100;
		composite.setLayoutData(tableData);
		return composite;

	}

	protected Table newTable(Composite parent) {
		Table table = new Table(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		return table;
	}

	protected Composite newRadioComposite(Composite parent) {
		Composite composite = EditorUtil.newComposite(parent);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		composite.setLayoutData(data);
		GridLayout layout = EditorUtil.newNoMarginGridLayout(2, false);
		composite.setLayout(layout);
		return composite;
	}

	protected TableEditor newTableEditor(Table table, Control control, TableItem item, int column) {
		TableEditor editor = new TableEditor(table);
		editor.grabHorizontal = true;
		editor.setEditor(control, item, column);
		return editor;
	}
}
