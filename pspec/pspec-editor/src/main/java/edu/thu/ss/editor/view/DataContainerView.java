package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.model.CategoryContentProvider;
import edu.thu.ss.editor.model.CategoryLabelProvider;
import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.util.PSpecUtil;

public class DataContainerView extends EditorView<VocabularyModel, DataCategory> {

	private TreeViewer dataViewer;

	private Text containerId;
	private Text shortDescription;
	private Text longDescription;

	private Text dataId;
	private Combo dataParentId;
	private Text dataShortDescription;
	private Text dataLongDescription;
	private Text dataOperation;
	private List dataOperations;
	private Button addOperation;
	private Button deleteOperation;

	private DataContainer dataContainer;
	private DataCategory selectedData;
	private TreeItem selectedItem;

	private final static double dataOperationHeightRatio = (double) 1 / 8;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public DataContainerView(Shell shell, Composite parent, VocabularyModel model,
			OutputView outputView) {
		super(shell, parent, model, outputView);
		this.dataContainer = model.getVocabulary().getDataContainer();

		setBackground(EditorUtil.getDefaultBackground());
		setLayout(new GridLayout(1, false));

		Group infoGroup = EditorUtil.newGroup(this, getMessage(Data_Container));
		GridData infoData = new GridData();
		infoData.horizontalAlignment = SWT.FILL;
		infoGroup.setLayoutData(infoData);

		initializeInfo(infoGroup);

		SashForm contentForm = new SashForm(this, SWT.NONE);
		GridData contentData = new GridData();
		contentData.horizontalAlignment = SWT.FILL;
		contentData.verticalAlignment = SWT.FILL;
		contentData.grabExcessVerticalSpace = true;
		contentData.grabExcessHorizontalSpace = true;

		contentForm.setLayoutData(contentData);

		initializeDataTree(contentForm, shell);

		Group categoryGroup = EditorUtil.newGroup(contentForm, null);

		initializeDataInfo(categoryGroup);

	}

	private void initializeInfo(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		EditorUtil.newLabel(parent, getMessage(Data_Container_ID), EditorUtil.labelData());
		containerId = EditorUtil.newText(parent, EditorUtil.textData());
		containerId.setText(dataContainer.getId());

		containerId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = containerId.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(Data_Container_ID_Not_Empty_Message),
							containerId);
					containerId.setText(dataContainer.getId());
					containerId.selectAll();
					return;
				}
				dataContainer.setId(containerId.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());
		shortDescription.setText(dataContainer.getShortDescription());
		shortDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dataContainer.setShortDescription(shortDescription.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		longDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 20));
		longDescription.setText(dataContainer.getLongDescription());
		longDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				dataContainer.setLongDescription(longDescription.getText().trim());

			}
		});
	}

	private void initializeDataTree(Composite parent, final Shell shell) {
		dataViewer = new TreeViewer(parent, SWT.BORDER | SWT.H_SCROLL);

		final Tree dataTree = dataViewer.getTree();
		EditorUtil.processTreeViewer(dataViewer);

		dataViewer.setLabelProvider(new CategoryLabelProvider<DataCategory>(dataContainer, model
				.getErrors()));
		dataViewer.setContentProvider(new CategoryContentProvider<DataCategory>(dataContainer));
		dataViewer.setInput(dataContainer);

		dataTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedItem = (TreeItem) e.item;
				if (selectedItem == null) {
					return;
				}
				selectedData = (DataCategory) selectedItem.getData();
				dataId.setText(selectedData.getId());

				dataParentId.setItems(EditorUtil.getCategoryItems(dataContainer));
				EditorUtil.setSelectedItem(dataParentId, selectedData.getParentId());

				dataShortDescription.setText(selectedData.getShortDescription());
				dataLongDescription.setText(selectedData.getLongDescription());

				dataOperation.setText("");
				dataOperations.removeAll();
				for (DesensitizeOperation op : selectedData.getOperations()) {
					dataOperations.add(op.getName());
				}

				if (dataContainer.directContains(selectedData)) {
					enableDataInfo();
				} else {
					disableDataInfo(false);
				}

				//clear message
				if (model.clearOutputByCategory(selectedData.getId(), MessageType.Data_Category)) {
					if (!dataContainer.duplicate(selectedData.getId())) {
						model.getErrors().remove(selectedData.getId());
						refresh(selectedData.getId());
					}
					outputView.refresh(OutputType.warning);
				}
			}
		});

		dataTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					Menu menu = createTreePopup(dataViewer.getTree());
					EditorUtil.showPopupMenu(menu, shell, e);
				}
			}
		});
	}

	private void initializeDataInfo(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		EditorUtil.newLabel(parent, getMessage(Data_Category_ID), EditorUtil.labelData());
		dataId = EditorUtil.newText(parent, EditorUtil.textData());
		dataId.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//check empty
				String text = dataId.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(Data_Category_ID_Not_Empty_Message), dataId);
					dataId.setText(selectedData.getId());
					dataId.selectAll();
					return;
				}
				//check duplicate
				boolean duplicate = false;
				if (!text.equals(selectedData.getId())) {
					duplicate = dataContainer.contains(text);
				} else {
					duplicate = dataContainer.duplicate(selectedData.getId());
				}
				if (duplicate) {
					EditorUtil.showMessage(shell, getMessage(Data_Category_ID_Unique_Message, text), dataId);
					dataId.setText(selectedData.getId());
					dataId.selectAll();
					return;
				}
				String oldId = selectedData.getId();
				EditorUtil.updateItem(dataParentId, selectedData.getId(), text);
				dataContainer.update(text, selectedData);
				if (model.getErrors().contains(oldId) && !dataContainer.duplicate(oldId)) {
					//problem fixed
					model.clearOutputByCategory(oldId, MessageType.Data_Category_Duplicate);
					model.getErrors().remove(oldId);
					refresh(oldId);
					outputView.refresh(OutputType.error);
				}

				dataViewer.refresh(selectedData);
			}
		});

		EditorUtil.newLabel(parent, getMessage(Data_Category_Parent_ID), EditorUtil.labelData());
		dataParentId = EditorUtil.newCombo(parent, EditorUtil.textData());
		dataParentId.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = dataParentId.getText().trim();
				if (text.equals(selectedData.getId())) {
					EditorUtil
							.showMessage(shell, getMessage(Data_Category_Parent_Same_Message), dataParentId);
					EditorUtil.setSelectedItem(dataParentId, selectedData.getParentId());
					return;
				}
				if (text.equals(selectedData.getParentId())) {
					return;
				}
				//check cycle reference
				DataCategory oldParent = selectedData.getParent();
				DataCategory newParent = dataContainer.get(text);
				if (PSpecUtil.checkCategoryCycleReference(selectedData, newParent, null)) {
					EditorUtil.showMessage(shell,
							getMessage(Data_Category_Parent_Cycle_Message, selectedData.getId()), dataParentId);
					EditorUtil.setSelectedItem(dataParentId, selectedData.getParentId());
					return;
				}

				//set new parent
				dataContainer.setParent(selectedData, newParent);

				dataParentId.setItems(EditorUtil.getCategoryItems(dataContainer));
				EditorUtil.setSelectedItem(dataParentId, selectedData.getParentId());
				refreshViewer(oldParent);
				refreshViewer(newParent);
			}
		});

		EditorUtil.newLabel(parent, getMessage(Desensitize_Operation), EditorUtil.labelData());
		final Composite operationComposite = new Composite(parent, SWT.NO_BACKGROUND);

		operationComposite.setLayout(EditorUtil.newNoMarginGridLayout(3, false));

		dataOperation = EditorUtil.newText(operationComposite, EditorUtil.textData());

		addOperation = new Button(operationComposite, SWT.PUSH);
		addOperation.setText("+");
		addOperation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String op = dataOperation.getText().trim();
				if (op.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(Desensitize_Operation_Empty_Message),
							dataOperation);
					return;
				}
				if (selectedData.directGetOperation(op) != null) {
					EditorUtil.showMessage(shell, getMessage(Desensitize_Operation_Unique_Message, op),
							dataOperation);
					return;
				}

				dataOperation.setText("");
				dataOperations.add(op);

				selectedData.addOperation(DesensitizeOperation.get(op));
			}
		});

		deleteOperation = new Button(operationComposite, SWT.PUSH);
		deleteOperation.setText("-");
		deleteOperation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] selected = dataOperations.getSelection();
				if (selected.length == 0) {
					return;
				}
				for (String op : selected) {
					selectedData.removeOperation(DesensitizeOperation.get(op));
				}
				dataOperations.remove(dataOperations.getSelectionIndices());

			}
		});

		dataOperations = new List(operationComposite, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		GridData listData = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		listData.heightHint = (int) (shell.getSize().y * dataOperationHeightRatio);
		dataOperations.setLayoutData(listData);

		GridData compositeData = new GridData();
		compositeData.horizontalAlignment = SWT.FILL;
		compositeData.heightHint = operationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		operationComposite.setLayoutData(compositeData);

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		dataShortDescription = EditorUtil.newText(parent, EditorUtil.textData());
		dataShortDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				selectedData.setShortDescription(dataShortDescription.getText());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		dataLongDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		dataLongDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dataLongDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				selectedData.setLongDescription(dataLongDescription.getText());

			}
		});

		disableDataInfo(true);

	}

	private Menu createTreePopup(final Control control) {
		final TreeItem selected = dataViewer.getTree().getSelectionCount() > 0 ? dataViewer.getTree()
				.getSelection()[0] : null;
		Menu popMenu = new Menu(control);
		MenuItem addItem = new MenuItem(popMenu, SWT.PUSH);
		addItem.setText(getMessage(Add));

		addItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DataCategory data = new DataCategory();
				TreeItem parentItem = null;
				if (selected != null) {
					parentItem = selected.getParentItem();
				}
				if (parentItem != null) {
					data.setParentId(parentItem.getText());
				}
				int ret = new DataCategoryDialog(shell, data, dataContainer).open();
				if (ret == SWT.OK) {
					if (parentItem != null) {
						DataCategory parent = dataContainer.get(parentItem.getText());
						parent.buildRelation(data);
					}
					dataContainer.add(data);

					if (parentItem != null) {
						dataViewer.refresh(dataContainer.get(parentItem.getText()));
					} else {
						dataViewer.refresh();
					}
				}
			}
		});

		MenuItem addChildItem = new MenuItem(popMenu, SWT.PUSH);
		addChildItem.setEnabled(selected != null);
		addChildItem.setText(getMessage(Add_Child));

		addChildItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DataCategory data = new DataCategory();
				data.setParentId(selected.getText());
				int ret = new DataCategoryDialog(shell, data, dataContainer).open();
				if (ret == SWT.OK) {
					DataCategory parent = dataContainer.get(selected.getText());
					parent.buildRelation(data);
					dataContainer.add(data);

					dataViewer.refresh(parent);
				}
			}
		});

		boolean editable = (selected != null && dataContainer.directContains((DataCategory) selected
				.getData()));
		MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
		deleteItem.setEnabled(editable);
		deleteItem.setText(getMessage(Delete));
		deleteItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DataCategory data = dataContainer.get(selected.getText());
				TreeItem parentItem = selected.getParentItem();
				DataCategory parent = null;
				if (parentItem != null) {
					parent = dataContainer.get(parentItem.getText());
				}
				MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				mb.setText(getMessage(Delete_Data));
				mb.setMessage(getMessage(Delete_Data_Message));
				int ret = mb.open();

				if (ret == SWT.YES) {
					//remove all
					dataContainer.cascadeRemove(data);
					if (parent != null) {
						parent.removeRelation(data);
						dataViewer.refresh(parent);
					} else {
						dataViewer.refresh();
					}
					disableDataInfo(true);

				} else if (ret == SWT.NO) {
					dataContainer.remove(data);
					if (parent != null) {
						int index = parent.removeRelation(data);
						if (data.getChildren() != null) {
							parent.buildRelation(index, data.getChildren());
						}
						dataViewer.refresh(parent);
					} else {
						if (data.getChildren() != null) {
							for (DataCategory child : data.getChildren()) {
								child.setParent(null);
							}
							dataContainer.getRoot().addAll(data.getChildren());
						}
						dataViewer.refresh();
					}
					disableDataInfo(true);

				}
			}
		});

		return popMenu;
	}

	private void refresh(String dataId) {
		DataCategory data = dataContainer.get(dataId);
		if (data != null) {
			dataViewer.refresh(data);
		}
	}

	@Override
	public void refresh() {
		dataViewer.refresh();
	}

	private void refreshViewer(DataCategory data) {
		if (data == null) {
			dataViewer.refresh();
		} else {
			dataViewer.refresh(data);
		}
	}

	private void disableDataInfo(boolean clear) {
		if (clear) {
			dataId.setText("");
			dataParentId.removeAll();
			dataShortDescription.setText("");
			dataLongDescription.setText("");
			dataOperation.setText("");
			dataOperations.removeAll();
		}

		dataId.setEnabled(false);
		dataParentId.setEnabled(false);
		dataShortDescription.setEnabled(false);
		dataLongDescription.setEnabled(false);
		dataOperation.setEnabled(false);
		dataOperations.setEnabled(false);
		addOperation.setEnabled(false);
		deleteOperation.setEnabled(false);
	}

	private void enableDataInfo() {
		dataId.setEnabled(true);
		dataParentId.setEnabled(true);
		dataShortDescription.setEnabled(true);
		dataLongDescription.setEnabled(true);
		dataOperation.setEnabled(true);
		dataOperations.setEnabled(true);
		addOperation.setEnabled(true);
		deleteOperation.setEnabled(true);
	}
}