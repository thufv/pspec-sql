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
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Connect_Success_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Database;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Delete_Label;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Extraction_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Host;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Host_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_ID;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_ID_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Info;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Label_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Password;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Policy_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Port;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Port_Not_Empty_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Table;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Username;
import static edu.thu.ss.editor.util.MessagesUtil.Open;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Invalid_Document_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Location;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Parse_Error_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Vocabulary_Contains_Error_Message;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.model.MetadataModel;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.EditorUtil.ParseResult;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.meta.BaseType;
import edu.thu.ss.spec.meta.Column;
import edu.thu.ss.spec.meta.CompositeType;
import edu.thu.ss.spec.meta.Database;
import edu.thu.ss.spec.meta.PrimitiveType;
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
	private ProgressBar pb;
	private Label connectLable;

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
		initializeInfo(basicGroup);

		Group connectGroup = EditorUtil.newInnerGroup(parent, getMessage(Connection));
		GridLayout layout = new GridLayout(4, true);
		layout.horizontalSpacing = 20;
		connectGroup.setLayout(layout);
		initializeConnection(connectGroup);

		Group labelGroup = EditorUtil.newInnerGroup(parent, getMessage(Label));
		labelGroup.setLayout(new GridLayout(4, true));
		((GridData) labelGroup.getLayoutData()).grabExcessVerticalSpace = true;
		((GridData) labelGroup.getLayoutData()).verticalAlignment = SWT.FILL;
		initializeLabel(labelGroup);
	}

	private void initializeInfo(Composite parent) {
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

	private void initializeConnection(Composite parent) {
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
		password = EditorUtil.newPassword(parent, EditorUtil.textData());

		pb = new ProgressBar(parent, SWT.INDETERMINATE);
		pb.setVisible(false);
		
		connectLable = new Label(parent, SWT.NONE);
		connectLable.setText("connecting...");
		connectLable.setVisible(false);
		new Label(parent, SWT.NONE).setText("");

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
				if (model.getRegistry().getPolicy() == null) {
					EditorUtil.showErrorMessageBox(shell, "", getMessage(Metadata_Policy_Not_Empty_Message));
					return;
				}
				
				//connect
				pb.setVisible(true);
				connectLable.setVisible(true);
				model.connect(host.getText(), port.getText(), username.getText(), password.getText());
			}
		});
	}

	private void initializeLabel(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Metadata_Database), EditorUtil.labelData());
		databaseCombo = EditorUtil.newCombo(parent, EditorUtil.textData());
		EditorUtil.newLabel(parent, getMessage(Metadata_Table), EditorUtil.labelData());
		tableCombo = EditorUtil.newCombo(parent, EditorUtil.textData());

		databaseCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO: changes should be in effect immediately when user edits
				String databaseName = databaseCombo.getText().trim();

				if (databaseName.isEmpty()) {
					return;
				}

				labelTree.removeAll();
				Database database = model.getRegistry().getDatabases().get(databaseName);
				Set<String> tableNames = database.getTables().keySet();
				tableCombo.setItems(tableNames.toArray(new String[tableNames.size()]));
			}
		});
		tableCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO: changes should be in effect immediately when user edits
				String databaseName = databaseCombo.getText().trim();
				String tableName = tableCombo.getText().trim();
				if (databaseName.isEmpty() || tableName.isEmpty()) {
					return;
				}
				currentDatabase = databaseName;
				currentTable = tableName;

				labelTree.removeAll();
				Database database = model.getRegistry().getDatabase(databaseName);
				Table table = database.getTable(tableName);
				Set<String> columnNames = table.getColumns().keySet();
				for (String columnName : columnNames) {
					//TODO: load existing labels from registry
					Column column = table.getColumn(columnName);
					BaseType type = column.getType();
					if (type == null) {
						column.setType(new PrimitiveType());
						addLabelRow(column, null, false);
						continue;
					}
					if (type instanceof PrimitiveType) {
						//add one simple row
						addLabelRow(column, ((PrimitiveType) type).getDataCategory(), false);
					} else if (type instanceof CompositeType) {
						//add multiple rows
						CompositeType compositeType = (CompositeType) type;
						if (((CompositeType) type).getAllTypes().size() == 0) {
							addLabelRow(column, null, false);
						} else {
							TreeItem item = addLabelRow(column, null, true);
							for (Entry<String, BaseType> entry : compositeType.getAllTypes().entrySet()) {
								PrimitiveType subtype = (PrimitiveType) entry.getValue();
								addExtractionRow(column, subtype.getDataCategory(), entry.getKey(), item);
							}
						}
					} else {
						//currently unsupported, treat as simple type
						addLabelRow(column, null, false);
					}
				}
			}
		});

		initializeLabelTree(parent);
	}

	private void initializeLabelTree(Composite parent) {
		Composite treeComposite = EditorUtil.newComposite(parent);
		GridData treeData = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeData.horizontalSpan = 4;
		treeComposite.setLayoutData(treeData);

		labelTree = new Tree(treeComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);

		TreeColumnLayout treeLayout = new TreeColumnLayout();
		treeComposite.setLayout(treeLayout);

		TreeColumn[] columns = new TreeColumn[3];
		String[] titles = new String[] { getMessage(Metadata_Column), getMessage(Label),
				getMessage(Extraction) };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TreeColumn(labelTree, SWT.NONE);
			columns[i].setText(titles[i]);
			columns[i].setResizable(false);
		}
		treeLayout.setColumnData(columns[0], new ColumnWeightData(1, columns[0].getWidth()));
		treeLayout.setColumnData(columns[1], new ColumnWeightData(1, columns[1].getWidth()));
		treeLayout.setColumnData(columns[2], new ColumnWeightData(1, columns[2].getWidth()));

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
					//TODO: add new row, and change current column type to composite type (if necessary)
					Column column = (Column) item.getData();
					@SuppressWarnings("unchecked")
					List<TreeEditor> editors = (List<TreeEditor>) item.getData(EditorUtil.Tree_Editor);
					editors.get(0).getEditor().dispose();
					BaseType type = column.getType();
					if (type == null || type instanceof PrimitiveType) {
						column.setType(new CompositeType());
					}
					addExtractionRow(column, null, null, item);
				}
			});
		} else {
			MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
			deleteItem.setText(getMessage(Metadata_Delete_Label));
			deleteItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					//TODO: change column column type to primitive type (if necessary)
					TreeItem parentItem = item.getParentItem();
					if (parentItem.getItemCount() == 1) {
						@SuppressWarnings("unchecked")
						List<TreeEditor> editors = (List<TreeEditor>) parentItem
								.getData(EditorUtil.Tree_Editor);
						Column column = (Column) parentItem.getData();
						column.setType(new PrimitiveType());

						final Combo dataCombo = EditorUtil.newCombo(labelTree, null);
						final XMLMetaRegistry registry = model.getRegistry();
						final BaseType type = column.getType();

						dataCombo.setItems(EditorUtil.getCategoryItems(registry.getPolicy().getDataContainer()));
						dataCombo.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								String text = dataCombo.getText().trim();
								//TODO save data category
								PrimitiveType primitiveType = (PrimitiveType) type;
								primitiveType.setDataCategory(registry.getPolicy().getDataCategory(text));
							}
						});
						editors.clear();
						editors.add(EditorUtil.newTreeEditor(labelTree, dataCombo, parentItem, 1));
					}
					EditorUtil.dispose(item);
					item.dispose();
				}
			});
		}
		return popMenu;
	}

	private TreeItem addLabelRow(Column column, DataCategory dataCategory, boolean extraction) {
		final TreeItem item = EditorUtil.newTreeItem(labelTree, "");
		List<TreeEditor> editors = new ArrayList<>(1);
		item.setData(column);
		item.setData(EditorUtil.Tree_Editor, editors);
		item.setText(0, column.getName());

		final XMLMetaRegistry registry = model.getRegistry();
		final BaseType type = column.getType();

		final Combo dataCombo = EditorUtil.newCombo(labelTree, null);
		dataCombo.setItems(EditorUtil.getCategoryItems(registry.getPolicy().getDataContainer()));
		if (dataCategory != null) {
			EditorUtil.setSelectedItem(dataCombo, dataCategory.getId());
		}

		dataCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = dataCombo.getText().trim();
				PrimitiveType primitiveType = (PrimitiveType) type;
				primitiveType.setDataCategory(registry.getPolicy().getDataCategory(text));
			}
		});
		editors.add(EditorUtil.newTreeEditor(labelTree, dataCombo, item, 1));

		if (extraction) {
			dataCombo.dispose();
		}

		item.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				EditorUtil.dispose(item);
			}
		});
		return item;
	}

	private TreeItem addExtractionRow(final Column column, DataCategory dataCategory,
			String extraction, TreeItem treeItem) {
		final TreeItem item = EditorUtil.newTreeItem(treeItem, "");
		List<TreeEditor> editors = new ArrayList<>(2);
		item.setData(column);
		item.setData(EditorUtil.Tree_Editor, editors);
		item.setData(EditorUtil.DataCategory, dataCategory);
		item.setData(EditorUtil.Extraction, extraction);
		item.setText(0, column.getName());

		final XMLMetaRegistry registry = model.getRegistry();
		final BaseType type = column.getType();

		final Combo dataCombo = EditorUtil.newCombo(labelTree, null);
		dataCombo.setItems(EditorUtil.getCategoryItems(registry.getPolicy().getDataContainer()));
		if (dataCategory != null) {
			EditorUtil.setSelectedItem(dataCombo, dataCategory.getId());
		}

		dataCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String dataCategoryId = dataCombo.getText().trim();
				//TODO save data category
				if (dataCategoryId != null) {
					DataCategory dataCategory = registry.getPolicy().getDataCategory(dataCategoryId);
					item.setData(EditorUtil.DataCategory, dataCategory);

					String extraction = (String) item.getData(EditorUtil.Extraction);
					if (extraction != null) {
						PrimitiveType extractType = new PrimitiveType();
						extractType.setDataCategory(dataCategory);
						CompositeType compositeType = (CompositeType) type;
						compositeType.add(extraction, extractType);
					} else {
						EditorUtil.showMessage(shell,
								getMessage(Metadata_Extraction_Not_Empty_Message, item.getText(0)), Display
										.getCurrent().getCursorLocation());
					}
				}
			}
		});
		editors.add(EditorUtil.newTreeEditor(labelTree, dataCombo, item, 1));

		final Text text = new Text(labelTree, SWT.NONE);
		if (extraction != null) {
			text.setText(extraction);
		}
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				TreeItem parent = item.getParentItem();
				for (TreeItem sibling : parent.getItems()) {
					if (sibling == item) {
						continue;
					}
					if (sibling.getText(2).equals(text.getText())) {
						if (text.getText().isEmpty()) {
							EditorUtil.showMessage(shell,
									getMessage(Metadata_Extraction_Not_Empty_Message, item.getText(0)), Display
											.getCurrent().getCursorLocation());
						} else {
							EditorUtil.showMessage(shell,
									getMessage(MetaData_Extraction_Unique_Message, item.getText(0)), Display
											.getCurrent().getCursorLocation());
						}
						//restore to original
						if (item.getData(EditorUtil.Extraction) != null) {
							item.setText(2, (String) item.getData(EditorUtil.Extraction));
						}
						return;
					}
				}
				//TODO: set new extraction
				String extraction = text.getText();
				item.setText(2, extraction);
				item.setData(EditorUtil.Extraction, extraction);
				DataCategory dataCategory = (DataCategory) item.getData(EditorUtil.DataCategory);

				if (dataCategory != null) {
					CompositeType compositeType = (CompositeType) type;
					compositeType.remove(extraction);
					PrimitiveType extractType = new PrimitiveType();
					extractType.setDataCategory(dataCategory);
					compositeType.add(extraction, extractType);
				} else {
					EditorUtil.showMessage(shell,
							getMessage(Metadata_Label_Not_Empty_Message, item.getText(0)), Display.getCurrent()
									.getCursorLocation());
				}
			}
		});
		editors.add(EditorUtil.newTreeEditor(labelTree, text, item, 2));

		item.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				EditorUtil.dispose(item);
			}
		});

		return item;
	}

	public void updataSchemaInfo() {
		pb.setVisible(false);
		connectLable.setVisible(false);
		EditorUtil.showInfoMessageBox(shell, "", getMessage(Metadata_Connect_Success_Message));
		Set<String> databaseNames = model.getRegistry().getDatabases().keySet();
		databaseCombo.setText("");
		databaseCombo.setItems(databaseNames.toArray(new String[databaseNames.size()]));
		tableCombo.setText("");
		tableCombo.setItems(new String[0]);

	}

	public void refreshLocation() {
		location.setText(model.getPath());
	}
}
