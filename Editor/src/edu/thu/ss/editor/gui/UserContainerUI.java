package edu.thu.ss.editor.gui;

import java.util.ArrayList;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;
import edu.thu.ss.editor.CategoryFactory.TreeContentProvider;
import edu.thu.ss.editor.CategoryFactory.TreeLabelProvider;
import edu.thu.ss.editor.CategoryFactory.UserCategoryFactory;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public class UserContainerUI extends Composite {
	
	protected static Shell shell;
	
	public static ArrayList<UserCategory> userCategoryList;
	private Text text_containerID;
	private Text text__shortDes;
	private Text text__longDes;
	private Text text__baseId;
	private Label lal_empty;
	private Label lbl_categoryIDContent;
	private Label lbl_parenIDContent;
	private Label lbl_shortDesDownContent;
	private Label lbl_longDesDowncontent;
	private static Tree tree;
	private static Composite composite_rightDown;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public UserContainerUI(Shell shell_, Composite parent, int style) {
		super(parent, SWT.NONE);
		shell = shell_;
		setLayout(new BorderLayout(0, 0));
		
		ToolBar toolBar_userContainer = new ToolBar(this, SWT.FLAT | SWT.RIGHT | SWT.SHADOW_OUT);
		toolBar_userContainer.setEnabled(false);
		toolBar_userContainer.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		toolBar_userContainer.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		toolBar_userContainer.setLayoutData(BorderLayout.NORTH);
		
		ToolItem tltm__userContainer = new ToolItem(toolBar_userContainer, SWT.NONE);
		tltm__userContainer.setEnabled(false);
		tltm__userContainer.setText("UserContainer");
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		sashForm.setSashWidth(4);
		sashForm.setLayoutData(BorderLayout.CENTER);
		
		Composite composite_left = new Composite(sashForm, SWT.NONE);
		composite_left.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_left.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TreeViewer treeViewer = new TreeViewer(composite_left, SWT.BORDER|SWT.H_SCROLL);
		treeViewer.setExpandPreCheckFilters(false);
		tree = treeViewer.getTree();
		tree.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		tree.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		
		userCategoryList = UserCategoryFactory.createTree();
		
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setInput(userCategoryList);
		
		//===============================================================
		Composite composite_right = new Composite(sashForm, SWT.NONE);
		composite_right.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_right.setLayout(new BorderLayout(0, 0));
		
		SashForm sashForm_right = new SashForm(composite_right, SWT.VERTICAL);
		sashForm_right.setLayoutData(BorderLayout.CENTER);
		
		Composite composite_rightUp = new Composite(sashForm_right, SWT.BORDER);
		composite_rightUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_rightUp.setLayout(new GridLayout(3, false));
		
		new Label(composite_rightUp, SWT.NONE);
		new Label(composite_rightUp, SWT.NONE);
		new Label(composite_rightUp, SWT.NONE);
		
		lal_empty = new Label(composite_rightUp, SWT.NONE);
		lal_empty.setText("    ");
		lal_empty.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lbl_containerID = new Label(composite_rightUp, SWT.NONE);
		lbl_containerID.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_containerID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_containerID.setText("*User Container ID\uFF1A");
		
		text_containerID = new Text(composite_rightUp, SWT.BORDER);
		GridData gd_text_containerID = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_containerID.widthHint = 420;
		text_containerID.setLayoutData(gd_text_containerID);
		new Label(composite_rightUp, SWT.NONE);
		
		Label lbl_baseId = new Label(composite_rightUp, SWT.NONE);
		lbl_baseId.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_baseId.setText("  Base ID\uFF1A");
		lbl_baseId.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		text__baseId = new Text(composite_rightUp, SWT.BORDER);
		text__baseId.setText("");
		GridData gd_text__baseId = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text__baseId.widthHint = 420;
		text__baseId.setLayoutData(gd_text__baseId);
		new Label(composite_rightUp, SWT.NONE);
		
		Label lbl_shortDes = new Label(composite_rightUp, SWT.NONE);
		lbl_shortDes.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_shortDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_shortDes.setText("  Short Description:");
		
		text__shortDes = new Text(composite_rightUp, SWT.BORDER);
		text__shortDes.setText("");
		GridData gd_text__shortDes = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text__shortDes.widthHint = 420;
		text__shortDes.setLayoutData(gd_text__shortDes);
		new Label(composite_rightUp, SWT.NONE);
		
		Label lbl_longDes = new Label(composite_rightUp, SWT.NONE);
		lbl_longDes.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lbl_longDes.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_longDes.setText("  Long Description:");
		lbl_longDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		text__longDes = new Text(composite_rightUp, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text__longDes = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text__longDes.widthHint = 360;
		gd_text__longDes.heightHint = 74;
		text__longDes.setLayoutData(gd_text__longDes);
		
		
		//===============================================================
		composite_rightDown = new Composite(sashForm_right, SWT.BORDER);
		composite_rightDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_rightDown.setLayout(new GridLayout(3, false));
		
		Label lbl_empty2 = new Label(composite_rightDown, SWT.NONE);
		lbl_empty2.setText("    ");
		lbl_empty2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lbl_categoryID = new Label(composite_rightDown, SWT.NONE);
		lbl_categoryID.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lbl_categoryID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_categoryID.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_categoryID.setText("*User Category ID : ");
		
		lbl_categoryIDContent = new Label(composite_rightDown, SWT.NONE);
		lbl_categoryIDContent.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_categoryIDContent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_categoryIDContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_rightDown, SWT.NONE);
		
		Label lbl_parenID = new Label(composite_rightDown, SWT.NONE);
		lbl_parenID.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lbl_parenID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_parenID.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_parenID.setText(" Parent ID               : ");
		
		lbl_parenIDContent = new Label(composite_rightDown, SWT.NONE);
		lbl_parenIDContent.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_parenIDContent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_parenIDContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_rightDown, SWT.NONE);
		
		Label lbl_shortDesDown = new Label(composite_rightDown, SWT.NONE);
		lbl_shortDesDown.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lbl_shortDesDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_shortDesDown.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_shortDesDown.setText(" Short Description : ");
		
		lbl_shortDesDownContent = new Label(composite_rightDown, SWT.NONE);
		lbl_shortDesDownContent.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_shortDesDownContent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_shortDesDownContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_rightDown, SWT.NONE);
		
		Label lbl_longDesDown = new Label(composite_rightDown, SWT.NONE);
		lbl_longDesDown.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		lbl_longDesDown.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_longDesDown.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_longDesDown.setText(" Long Description : ");
		
		lbl_longDesDowncontent = new Label(composite_rightDown, SWT.NONE);
		lbl_longDesDowncontent.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_longDesDowncontent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_longDesDowncontent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_rightDown, SWT.NONE);
		new Label(composite_rightDown, SWT.NONE);
		new Label(composite_rightDown, SWT.NONE);
		sashForm.setWeights(new int[] {402, 453});
		
		//===============================================================		
		 tree.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent e) {}
			public void mouseDown(MouseEvent e) {}
			public void mouseUp(MouseEvent e) {
				if(e.button == 1){
					 Point point = new Point(e.x,e.y);  
		             TreeItem item = tree.getItem(point); 
		             if(item!=null){
		            	 lbl_categoryIDContent.setText("  " + item.getText());
		            	 lbl_parenIDContent.setText("  this is the parent id of " + item.getText());
		            	 lbl_shortDesDownContent.setText("  this is the short description of " + item.getText());
		            	 lbl_longDesDowncontent.setText("  this is the long description of " + item.getText());
		            	 composite_rightDown.layout();
		             }  
				}else if(e.button == 3){
					tree.setMenu(createPopupMenu());
				}
			} 
	      }); 
	}
	
	/*
	 * tree popup Menu
	 */
	public static Menu createPopupMenu() {
		Menu popMenu = new Menu(shell, SWT.POP_UP);
		MenuItem addItem = new MenuItem(popMenu, SWT.PUSH);
		addItem.setText("Add Child");
		MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
		deleteItem.setText("Delete");
		MenuItem EditItem = new MenuItem(popMenu, SWT.PUSH);
		EditItem.setText("Edit");
		addItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				TreeItem[] treeItem = tree.getSelection();
				TreeItem category = treeItem[0];
				UserCategory userCategory = new UserCategory();
				if(!category.getText().equals("Root"))
					userCategory.setParentId(category.getText());
				new UserCategoryUI(shell , userCategory).open();
			}
		});
		deleteItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// TODO Auto-generated method stub
			}
		});
		EditItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//TreeItem[] treeItem = tree.getSelection();
				//String categoryID = treeItem[0].getText();
				// TODO Auto-generated method stub
				UserCategory userCategory = new UserCategory();
				new UserCategoryUI(shell ,userCategory).open();
			}
		});
		return popMenu;
	}
	
	
	
}