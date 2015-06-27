package edu.thu.ss.editor;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;
import edu.thu.ss.editor.gui.DataContainerUI;
import edu.thu.ss.editor.gui.Rules;
import edu.thu.ss.editor.gui.UserContainerUI;
import edu.thu.ss.editor.gui.VocabInfo;

public class Main {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage("img/logo.png"));
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment(); 
		//get maximum window bounds
		Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
		shell.setSize(maximumWindowBounds.width, maximumWindowBounds.height);
		shell.setLocation(0, 0);
		shell.setText("PSpecEditor");
		shell.setLayout(new BorderLayout(0, 0));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntm_File = new MenuItem(menu, SWT.CASCADE);
		mntm_File.setText("File");
		
		Menu menu_1 = new Menu(mntm_File);
		mntm_File.setMenu(menu_1);
		
		MenuItem mntmNew = new MenuItem(menu_1, SWT.CASCADE);
		mntmNew.setText("New");
		
		Menu menu_2 = new Menu(mntmNew);
		mntmNew.setMenu(menu_2);
		
		MenuItem mntmNewVacab = new MenuItem(menu_2, SWT.NONE);
		mntmNewVacab.setText("New Vacab");
		
		MenuItem mntmNewPolicy = new MenuItem(menu_2, SWT.NONE);
		mntmNewPolicy.setText("New Policy");
		
		MenuItem mntmOpenFile = new MenuItem(menu_1, SWT.NONE);
		mntmOpenFile.setText("Open File");
		
		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText("Save");
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		
		MenuItem mntm_Edit = new MenuItem(menu, SWT.CASCADE);
		mntm_Edit.setText("Edit");
		
		Menu menu_3 = new Menu(mntm_Edit);
		mntm_Edit.setMenu(menu_3);
		
		MenuItem mntmCleanUp = new MenuItem(menu_3, SWT.NONE);
		mntmCleanUp.setText("Clean Up");
		
		MenuItem mntm_Help = new MenuItem(menu, SWT.CASCADE);
		mntm_Help.setText("Help");
		
		Menu menu_4 = new Menu(mntm_Help);
		mntm_Help.setMenu(menu_4);
		
		MenuItem mntmAbout = new MenuItem(menu_4, SWT.NONE);
		mntmAbout.setText("About");
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT );
		toolBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		toolBar.setLayoutData(BorderLayout.NORTH);
		
		ToolItem tltm_Open = new ToolItem(toolBar, SWT.NONE);
		tltm_Open.setToolTipText("Open File");
		tltm_Open.setImage(SWTResourceManager.getImage("img/test.png"));
		
		ToolItem tltm_Save = new ToolItem(toolBar, SWT.NONE);
		tltm_Save.setToolTipText("Save File");
		tltm_Save.setImage(SWTResourceManager.getImage("img/test.png"));
		
		ToolItem tltm_CleanUp = new ToolItem(toolBar, SWT.NONE);
		tltm_CleanUp.setToolTipText("Clean Up");
		tltm_CleanUp.setImage(SWTResourceManager.getImage("img/test.png"));
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		sashForm.setSashWidth(4);
		sashForm.setLayoutData(BorderLayout.CENTER);
		
		Composite composite_left = new Composite(sashForm, SWT.BORDER);
		composite_left.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_left.setLayout(new FillLayout());
		
		Tree tree = new Tree(composite_left, SWT.NONE);
		tree.setBackground(SWTResourceManager.getColor(240, 248, 255));
		TreeItem treeItem_Vacab = new TreeItem(tree, SWT.NONE);  
		treeItem_Vacab.setImage(SWTResourceManager.getImage("img/V.png"));
		treeItem_Vacab.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		treeItem_Vacab.setText("Vacabulary");  
		TreeItem treeItem_UserContainer = new TreeItem(treeItem_Vacab, SWT.NONE);  
		treeItem_UserContainer.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		treeItem_UserContainer.setText("User Container"); 
		treeItem_UserContainer.setExpanded(true);
		TreeItem treeItem_DataContainer = new TreeItem(treeItem_Vacab, SWT.NONE);  
		treeItem_DataContainer.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		treeItem_DataContainer.setText("Data Container");  
		treeItem_DataContainer.setExpanded(true);
		treeItem_Vacab.setExpanded(true);
		
		TreeItem treeItem_Rule = new TreeItem(tree, SWT.NONE);  
		treeItem_Rule.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		treeItem_Rule.setImage(SWTResourceManager.getImage("img/R.png"));
		treeItem_Rule.setText("Policy");  
		treeItem_Rule.setExpanded(true);
		
		
		Composite composite_right = new Composite(sashForm, SWT.NONE);
		composite_right.setLayout(new FillLayout());
		
		final SashForm sashForm_right = new SashForm(composite_right, SWT.VERTICAL);
		sashForm_right.setSashWidth(4);
		
//		ScrolledComposite sc1 = new ScrolledComposite(sashForm_1, SWT.V_SCROLL); 
//		final Composite composite3 = new Composite(sc1, SWT.BORDER);
//		composite3.setSize(composite3.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		final Composite composite_up = new Composite(sashForm_right, SWT.BORDER);
		composite_up.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_up.setLayout(new FillLayout());
		Composite composite_down = new Composite(sashForm_right, SWT.BORDER);
		composite_down.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_down.setLayout(new BorderLayout(0, 0));
		
		ToolBar toolBar_down = new ToolBar(composite_down, SWT.FLAT | SWT.RIGHT);
		toolBar_down.setEnabled(false);
		toolBar_down.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		toolBar_down.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		toolBar_down.setLayoutData(BorderLayout.NORTH);
		
		ToolItem tltm_resultOutput = new ToolItem(toolBar_down, SWT.NONE);
		tltm_resultOutput.setText("Result Output");
		sashForm_right.setWeights(new int[] {808, 124});
		sashForm.setWeights(new int[] {267, 1382});
		
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem selectedTI = (TreeItem) e.item;
				Control[] rightCs = composite_up.getChildren();
				for (int i = 0; i < rightCs.length; i++) {
					rightCs[i].dispose();
				}
				String selStr = selectedTI.getText();
				if(selStr.equalsIgnoreCase("Vacabulary")){
					new VocabInfo(shell, composite_up, SWT.NONE);
					composite_up.layout();
				}else if(selStr.equalsIgnoreCase("User Container")){
					new UserContainerUI(shell, composite_up, SWT.NONE);
					composite_up.layout();
				}else if(selStr.equalsIgnoreCase("Data Container")){
					new DataContainerUI(shell, composite_up, SWT.NONE);
					composite_up.layout();
				}else if(selStr.equalsIgnoreCase("Policy")){
					new Rules(shell, composite_up, SWT.NONE);
					composite_up.layout();
				}
			}
		});		
		
		
		
	}

}
