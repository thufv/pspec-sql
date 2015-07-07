package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.model.CategoryContentProvider;
import edu.thu.ss.editor.model.CategoryLabelProvider;
import edu.thu.ss.editor.model.DataCategoryFactory;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class DataContainerView extends Composite {

	public static ArrayList<DataCategory> dataCategoryList;

	private Tree categoryTree;

	private Text containerId;
	private Text shortDescription;
	private Text longDescription;
	private Text baseId;

	private Text dataId;
	private Text dataParantId;
	private Text dataShortDescription;
	private Text dataLongDescription;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public DataContainerView(Shell shell, Composite parent, int style) {
		super(parent, SWT.NONE);
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

		initializeCategoryTree(contentForm, shell);

		Group categoryGroup = EditorUtil.newGroup(contentForm, null);
		initializeCategory(categoryGroup);

	}

	private void initializeInfo(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		EditorUtil.newLabel(parent, getMessage(Data_Container_ID), EditorUtil.labelData());
		containerId = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Base_ID), EditorUtil.labelData());
		baseId = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		longDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 20));

	}

	private void initializeCategoryTree(Composite parent, final Shell shell) {
		TreeViewer categoryViewer = new TreeViewer(parent, SWT.BORDER | SWT.H_SCROLL);
		categoryTree = categoryViewer.getTree();

		dataCategoryList = DataCategoryFactory.createTree();

		categoryViewer.setLabelProvider(new CategoryLabelProvider());
		categoryViewer.setContentProvider(new CategoryContentProvider());
		categoryViewer.setInput(dataCategoryList);

		categoryTree.addMouseListener(new MouseAdapter() {

			public void mouseUp(MouseEvent e) {
				if (e.button == 1) {
					Point point = new Point(e.x, e.y);
					TreeItem item = categoryTree.getItem(point);
					if (item != null) {
						dataId.setText(item.getText());
						//TODO change data category display
					}
				} else if (e.button == 3) {
					categoryTree.setMenu(createTreePopup(shell));
				}
			}
		});
	}

	private void initializeCategory(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		EditorUtil.newLabel(parent, getMessage(Data_Category_ID), EditorUtil.labelData());
		dataId = EditorUtil.newText(parent, EditorUtil.textData());
		dataId.setEnabled(false);

		EditorUtil.newLabel(parent, getMessage(Data_Category_Parent_ID), EditorUtil.labelData());
		dataParantId = EditorUtil.newText(parent, EditorUtil.textData());
		dataParantId.setEnabled(false);

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		dataShortDescription = EditorUtil.newText(parent, EditorUtil.textData());
		dataShortDescription.setEnabled(false);

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		dataLongDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		dataLongDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 20));
		dataLongDescription.setEnabled(false);

	}

	public Menu createTreePopup(final Shell shell) {
		Menu popMenu = new Menu(shell);
		MenuItem addItem = new MenuItem(popMenu, SWT.PUSH);
		addItem.setText("Add Child");
		MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
		deleteItem.setText("Delete");
		MenuItem editItem = new MenuItem(popMenu, SWT.PUSH);
		editItem.setText("Edit");

		addItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TreeItem[] treeItem = categoryTree.getSelection();
				TreeItem parent = treeItem[0];
				DataCategory dataCategory = new DataCategory();
				dataCategory.setParentId(parent.getText());
				new DataCategoryDialog(shell, dataCategory).open();
			}
		});
		deleteItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

			}
		});
		editItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				DataCategory dataCategory = new DataCategory();
				new DataCategoryDialog(shell, dataCategory).open();
			}
		});
		return popMenu;
	}

}