package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.Basic_Info;
import static edu.thu.ss.editor.util.MessagesUtil.Connect;
import static edu.thu.ss.editor.util.MessagesUtil.Connection;
import static edu.thu.ss.editor.util.MessagesUtil.Extraction;
import static edu.thu.ss.editor.util.MessagesUtil.Label;
import static edu.thu.ss.editor.util.MessagesUtil.Location;
import static edu.thu.ss.editor.util.MessagesUtil.MetaData_Extraction_Unique_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Add_Label;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Column;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Database;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Delete_Label;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Host;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Host_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_ID;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_ID_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Info;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Password;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Password_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Port;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Port_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Table;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Username;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Username_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Open;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Invalid_Document_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Location;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Parse_Error_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Vocabulary_Contains_Error_Message;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.model.MetadataModel;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.EditorUtil.ParseResult;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.MetadataLabelType;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.Table;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistry;

public class MetadataView extends EditorView<MetadataModel, XMLMetaRegistry> {

	private Text metadataID;
	private Text location;
	private Text policyLocation;
	private Text username;
	private Text password;
	private Text host;
	private Text port;

	private TreeItem editorItem;
	private EventTable table;
	private Tree labelTree;

	private Combo databaseCombo;
	private Combo tableCombo;

	private String currentDatabase;
	private String currentTable;

	public MetadataView(Shell shell, Composite parent, MetadataModel model, OutputView outputView,
			TreeItem editorItem) {
		super(shell, parent, model, outputView);
		this.shell = shell;
		this.model = model;
		this.editorItem = editorItem;
		this.table = EditorUtil.newOutputTable(model, null);

		this.setBackground(EditorUtil.getDefaultBackground());
		this.setBackgroundMode(SWT.INHERIT_FORCE);
		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Metadata_Info));
		content.setLayout(new GridLayout(1, false));
		initializeContent(content);
	}

	private void initializeContent(Composite parent) {
		Group basicGroup = EditorUtil.newInnerGroup(parent, getMessage(Basic_Info));
		initializeBasic(basicGroup);

		Group connectGroup = EditorUtil.newInnerGroup(parent, getMessage(Connection));
		GridLayout layout = new GridLayout(4, true);
		layout.horizontalSpacing = 20;
		connectGroup.setLayout(layout);
		initializeConnect(connectGroup);

		Group labelGroup = EditorUtil.newInnerGroup(parent, getMessage(Label));
		labelGroup.setLayout(new GridLayout(1, true));
		((GridData) labelGroup.getLayoutData()).grabExcessVerticalSpace = true;
		((GridData) labelGroup.getLayoutData()).verticalAlignment = SWT.FILL;
		initializeLabel(labelGroup);
	}

	private void initializeBasic(Composite parent) {
		final XMLMetaRegistry registry = model.getRegistry();
		EditorUtil.newLabel(parent, getMessage(Metadata_ID), EditorUtil.labelData());
		metadataID = EditorUtil.newText(parent, EditorUtil.textData());
		metadataID.setText(registry.getInfo().getId());
		metadataID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = metadataID.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(Metadata_ID_Not_Empty_Message), metadataID);
					metadataID.setText(registry.getInfo().getId());
					metadataID.selectAll();
					return;
				}

				if (!registry.getInfo().getId().equals(text)) {
					registry.getInfo().setId(text);
					editorItem.setText(text);
					if (model.hasOutput()) {
						outputView.refresh(model);
					}
				}
			}
		});

		EditorUtil.newLabel(parent, getMessage(Location), EditorUtil.labelData());
		location = EditorUtil.newText(parent, EditorUtil.textData());
		location.setText(model.getPath());
		location.setEnabled(false);

		EditorUtil.newLabel(parent, getMessage(Policy_Location), EditorUtil.labelData());
		Composite policyComposite = EditorUtil.newComposite(parent);
		policyComposite.setLayout(EditorUtil.newNoMarginGridLayout(2, false));
		policyComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		policyLocation = EditorUtil.newText(policyComposite, EditorUtil.textData());
		if (registry.getPolicyLocation() != null) {
			policyLocation.setText(registry.getPolicyLocation().toString());
		} else {
			policyLocation.setText("...");
		}
		policyLocation.setEnabled(false);
		GridData baseData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		policyLocation.setLayoutData(baseData);

		Button open = EditorUtil.newButton(policyComposite, getMessage(Open));
		open.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = EditorUtil.newOpenFileDialog(shell);
				String file = dlg.open();
				if (file != null) {
					PolicyModel policyModel = new PolicyModel(file);
					ParseResult result = EditorUtil.openPolicy(policyModel, shell, false);
					if (result.equals(ParseResult.Invalid_Policy)) {
						EditorUtil.showErrorMessageBox(shell, "",
								getMessage(Policy_Invalid_Document_Message, file));
						return;
					}
					if (result.equals(ParseResult.Invalid_Vocabulary)) {
						EditorUtil.showErrorMessageBox(shell, "",
								getMessage(Policy_Vocabulary_Contains_Error_Message, file));
						return;
					}
					if (result.equals(ParseResult.Error)) {
						EditorUtil.showErrorMessageBox(shell, "",
								getMessage(Policy_Parse_Error_Message, model.getRegistry().getInfo().getId()));
						return;
					}
					registry.setPolicy(policyModel.getPolicy());
					policyLocation.setText(file);

					boolean hasOutput = model.hasOutput();
					model.clearOutput();

					if (hasOutput || model.hasOutput()) {
						outputView.refresh();
					}
				}
			}
		});
	}

	private void initializeConnect(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Metadata_Host), EditorUtil.labelData());
		host = EditorUtil.newText(parent, EditorUtil.textData());
		host.setText("127.0.0.1");

		EditorUtil.newLabel(parent, getMessage(Metadata_Port), EditorUtil.labelData());
		port = EditorUtil.newText(parent, EditorUtil.textData());
		port.setText("3306");

		EditorUtil.newLabel(parent, getMessage(Metadata_Username), EditorUtil.labelData());
		username = EditorUtil.newText(parent, EditorUtil.textData());
		username.setText("root");

		EditorUtil.newLabel(parent, getMessage(Metadata_Password), EditorUtil.labelData());
		password = EditorUtil.newText(parent, EditorUtil.textData());
		password.setText("123456");

		Button connect = EditorUtil.newButton(parent, getMessage(Connect));
		connect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (host.getText().equals("")) {
					EditorUtil.showErrorMessageBox(shell, "", getMessage(Metadata_Host_Not_Empty_Message));
					return;
				}
				if (port.getText().equals("")) {
					EditorUtil.showErrorMessageBox(shell, "", getMessage(Metadata_Port_Not_Empty_Message));
					return;
				}
				if (username.getText().equals("")) {
					EditorUtil
							.showErrorMessageBox(shell, "", getMessage(Metadata_Username_Not_Empty_Message));
					return;
				}
				if (password.getText().equals("")) {
					EditorUtil
							.showErrorMessageBox(shell, "", getMessage(Metadata_Password_Not_Empty_Message));
					return;
				}

				model.connect(host.getText(), port.getText(), username.getText(), password.getText());
				initializeLabelCombo();
			}
		});
	}

	private void initializeLabel(Composite parent) {
		Group comboGroup = EditorUtil.newInnerGroup(parent, "");
		comboGroup.setLayout(new GridLayout(2, true));

		EditorUtil.newLabel(comboGroup, getMessage(Metadata_Database), EditorUtil.labelData());
		databaseCombo = EditorUtil.newCombo(comboGroup, null);
		EditorUtil.newLabel(comboGroup, getMessage(Metadata_Table), EditorUtil.labelData());
		tableCombo = EditorUtil.newCombo(comboGroup, null);

		SashForm contentForm = new SashForm(parent, SWT.NONE);
		GridData contentData = new GridData();
		contentData.horizontalAlignment = SWT.FILL;
		contentData.verticalAlignment = SWT.FILL;
		contentData.grabExcessVerticalSpace = true;
		contentData.grabExcessHorizontalSpace = true;
		contentForm.setLayoutData(contentData);

		initializeLabelTree(contentForm);
	}

	private void initializeLabelCombo() {
		final XMLMetaRegistry registry = model.getRegistry();
		Set<String> databaseNames = registry.getDatabases().keySet();
		databaseCombo.setItems(databaseNames.toArray(new String[databaseNames.size()]));

		databaseCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!saveTableLabel()) {
					return;
				}
				String databaseName = databaseCombo.getText().trim();

				if (databaseName.isEmpty()) {
					return;
				}

				labelTree.removeAll();
				Database database = registry.getDatabases().get(databaseName);
				Set<String> tableNames = database.getTables().keySet();
				tableCombo.setItems(tableNames.toArray(new String[tableNames.size()]));
			}
		});

		tableCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!saveTableLabel()) {
					tableCombo.setText(currentTable);
					return;
				}
				String databaseName = databaseCombo.getText().trim();
				String tableName = tableCombo.getText().trim();
				if (databaseName.isEmpty() || tableName.isEmpty()) {
					return;
				}
				currentDatabase = databaseName;
				currentTable = tableName;

				labelTree.removeAll();
				Database database = registry.getDatabases().get(databaseName);
				Table table = database.getTable(tableName);
				Set<String> columnNames = table.getColumns().keySet();
				for (String columnName : columnNames) {
					TreeItem item = new TreeItem(labelTree, SWT.NONE);
					item.setText(new String[] { columnName, "", "" });
					Map<String, String> extraction = table.getColumn(columnName).getExtraction();
					if (!extraction.isEmpty()) {
						for (String extractionName : extraction.keySet()) {
							String label = extraction.get(extractionName);
							TreeItem subItem = new TreeItem(item, SWT.NONE);
							subItem.setText(new String[] { columnName, label, extractionName });
						}
					}
				}
			}
		});
	}

	private void initializeLabelTree(Composite parent) {
		labelTree = new Tree(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		TreeColumn column = new TreeColumn(labelTree, SWT.CENTER);
		column.setText(getMessage(Metadata_Column));
		TreeColumn label = new TreeColumn(labelTree, SWT.CENTER);
		label.setText(getMessage(Label));
		TreeColumn extraction = new TreeColumn(labelTree, SWT.CENTER);
		extraction.setText(getMessage(Extraction));
		column.setWidth(200);
		label.setWidth(200);
		extraction.setWidth(200);
		labelTree.setHeaderVisible(true);
		labelTree.setLinesVisible(true);

		labelTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				TreeItem[] selection = labelTree.getSelection();
				if (selection.length == 0) {
					return;
				}
				TreeItem item = selection[0];
				if (e.button == 3) {
					Menu menu = createLabelPopup(labelTree, item);
					EditorUtil.showPopupMenu(menu, shell, e);
				}
			}
		});

	}

	private Menu createLabelPopup(Control control, final TreeItem item) {
		Menu popMenu = new Menu(control);

		if (item.getParentItem() == null) {
			MenuItem addItem = new MenuItem(popMenu, SWT.PUSH);
			addItem.setText(getMessage(Metadata_Add_Label));
			addItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TreeItem newItem = new TreeItem(item, SWT.NONE);
					addLabelRow(newItem);
				}
			});

		} else {
			MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
			deleteItem.setText(getMessage(Metadata_Delete_Label));
			deleteItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					item.dispose();
				}
			});
		}
		return popMenu;
	}

	private void addLabelRow(TreeItem item) {
		item.setText(new String[] { item.getParentItem().getText(0), "", "Default" });
		for (int i = 1; i < 3; i++) {
			final int column = i;
			final TreeEditor editor = new TreeEditor(labelTree);
			editor.horizontalAlignment = SWT.LEFT;
			editor.grabHorizontal = true;
			editor.minimumWidth = 50;

			labelTree.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					Control oldEditor = editor.getEditor();
					if (oldEditor != null) {
						oldEditor.dispose();
					}
					final TreeItem item = (TreeItem) event.item;
					if (item == null || item.getParentItem() == null) {
						return;
					}

					if (column == 1) {
						final Combo labelCombo = new Combo(labelTree, SWT.NONE);
						XMLMetaRegistry registry = model.getRegistry();
						Object[] dataCategories = registry.getPolicy().getDataContainer().materializeRoots()
								.toArray();
						String[] categories = new String[dataCategories.length];
						for (int i = 0; i < dataCategories.length; i++) {
							categories[i] = ((DataCategory) dataCategories[i]).getId();
						}
						labelCombo.setItems(categories);
						labelCombo.setText(item.getText(column));
						
						labelCombo.addFocusListener(new FocusAdapter() {
							@Override
							public void focusLost(FocusEvent e) {
								item.setText(1, labelCombo.getText());
							}
						});
						labelCombo.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								String text = labelCombo.getText().trim();
								labelCombo.setText(text);
								if (!text.isEmpty()) {
									EditorUtil.setSelectedItem(labelCombo, text);
								}
							}
						});
						editor.setEditor(labelCombo, item, column);
					} else if (column == 2) {
						final Text newEditor = new Text(labelTree, SWT.NONE);
						newEditor.setText(item.getText(column));
						newEditor.addFocusListener(new FocusAdapter() {
							@Override
							public void focusLost(FocusEvent e) {
								Text text = (Text) editor.getEditor();
								TreeItem parent = item.getParentItem();
								for (TreeItem sibling : parent.getItems()) {
									if (sibling.equals(item)) {
										continue;
									}
									if (sibling.getText(column).equals(text.getText())) {
										EditorUtil.showMessage(shell,
												getMessage(MetaData_Extraction_Unique_Message, text.getText()), newEditor);
										text.setText("");
										item.setText(column, "");
										return;
									}
								}
								item.setText(column, text.getText());
							}
						});
						editor.setEditor(newEditor, item, column);
					}
				}
			});
		}
	}

	public boolean saveTableLabel() {
		if (currentDatabase == null || currentTable == null) {
			return true;
		}
		TreeItem[] items = labelTree.getItems();
		boolean error = false;
		model.clearOutput(OutputType.error);
		model.clearTableLabel(currentDatabase, currentTable);
		for (int i = 0; i < items.length; i++) {
			TreeItem item = items[i];
			TreeItem[] subItems = item.getItems();
			for (int j = 0; j < subItems.length; j++) {
				TreeItem subItem = subItems[j];
				if (subItem.getText(1).isEmpty()) {
					table.onMetadataLabelError(MetadataLabelType.Label_Empty, currentDatabase + "."
							+ currentTable + "." + subItem.getText(0));
					error = true;
				} else if (subItem.getText(2).isEmpty()) {
					table.onMetadataLabelError(MetadataLabelType.Extraction_Empty, currentDatabase + "."
							+ currentTable + "." + subItem.getText(0));
					error = true;
				} else {
					String columnName = subItem.getText(0);
					String label = subItem.getText(1);
					String extractionName = subItem.getText(2);
					model.addColumnExtraction(currentDatabase, currentTable, columnName, extractionName,
							label);
				}
			}
		}
		outputView.refresh(OutputType.error);
		if (error) {
			EditorUtil.setSelectedItem(databaseCombo, currentDatabase);
			EditorUtil.setSelectedItem(tableCombo, currentTable);
		}
		return !error;
	}

	public void refreshLocation() {
		location.setText(model.getPath());
	}
}
