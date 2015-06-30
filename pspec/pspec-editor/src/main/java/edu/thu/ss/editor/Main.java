package edu.thu.ss.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import edu.thu.ss.editor.util.EditorConstant;

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
		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				Display.getDefault().dispose();
				System.exit(0);
			}
		});

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
		int defaultWidth = 1024;
		int defaultHeight = 768;
		shell.setSize(defaultWidth, defaultHeight);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x
				/ 2, Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);
		shell.setText("PSpec Editor");
		shell.setLayout(new BorderLayout(0, 0));
		//TODO add triggers for menu
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem fileItems = new MenuItem(menu, SWT.CASCADE);
		fileItems.setText("File");

		Menu menu_1 = new Menu(fileItems);
		fileItems.setMenu(menu_1);

		MenuItem newItems = new MenuItem(menu_1, SWT.CASCADE);
		newItems.setText("New");

		Menu menu_2 = new Menu(newItems);
		newItems.setMenu(menu_2);

		MenuItem newVocab = new MenuItem(menu_2, SWT.NONE);
		newVocab.setText("New Vacabulary");

		MenuItem newPolicy = new MenuItem(menu_2, SWT.NONE);
		newPolicy.setText("New Policy");

		MenuItem openFile = new MenuItem(menu_1, SWT.NONE);
		openFile.setText("Open File");

		MenuItem save = new MenuItem(menu_1, SWT.NONE);
		save.setText("Save");

		MenuItem exit = new MenuItem(menu_1, SWT.NONE);
		exit.setText("Exit");

		MenuItem edit = new MenuItem(menu, SWT.CASCADE);
		edit.setText("Edit");

		Menu menu_3 = new Menu(edit);
		edit.setMenu(menu_3);

		MenuItem mntmCleanUp = new MenuItem(menu_3, SWT.NONE);
		mntmCleanUp.setText("Clean Up");

		MenuItem help = new MenuItem(menu, SWT.CASCADE);
		help.setText("Help");

		Menu menu_4 = new Menu(help);
		help.setMenu(menu_4);

		MenuItem about = new MenuItem(menu_4, SWT.NONE);
		about.setText("About");
		/*
				ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
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
		*/
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		sashForm.setSashWidth(4);
		sashForm.setLayoutData(BorderLayout.CENTER);

		Composite leftForm = new Composite(sashForm, SWT.BORDER);
		leftForm.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		leftForm.setLayout(new FillLayout());

		Tree tree = new Tree(leftForm, SWT.NONE);
		tree.setBackground(SWTResourceManager.getColor(240, 248, 255));
		TreeItem treeItem_Vacab = new TreeItem(tree, SWT.NONE);
		treeItem_Vacab.setImage(SWTResourceManager.getImage("img/V.png"));
		treeItem_Vacab.setFont(EditorConstant.getDefaultFont());
		treeItem_Vacab.setText("Vacabulary");
		TreeItem treeItem_UserContainer = new TreeItem(treeItem_Vacab, SWT.NONE);
		treeItem_UserContainer.setFont(EditorConstant.getDefaultFont());
		treeItem_UserContainer.setText("User Container");
		treeItem_UserContainer.setExpanded(true);
		TreeItem treeItem_DataContainer = new TreeItem(treeItem_Vacab, SWT.NONE);
		treeItem_DataContainer.setFont(EditorConstant.getDefaultFont());
		treeItem_DataContainer.setText("Data Container");
		treeItem_DataContainer.setExpanded(true);
		treeItem_Vacab.setExpanded(true);

		TreeItem treeItem_Rule = new TreeItem(tree, SWT.NONE);
		treeItem_Rule.setFont(EditorConstant.getDefaultFont());
		treeItem_Rule.setImage(SWTResourceManager.getImage("img/R.png"));
		treeItem_Rule.setText("Policy");
		treeItem_Rule.setExpanded(true);

		Composite rightComposite = new Composite(sashForm, SWT.NONE);
		rightComposite.setLayout(new FillLayout());

		final SashForm rightForm = new SashForm(rightComposite, SWT.VERTICAL);
		rightForm.setSashWidth(4);

		//		ScrolledComposite sc1 = new ScrolledComposite(sashForm_1, SWT.V_SCROLL); 
		//		final Composite composite3 = new Composite(sc1, SWT.BORDER);
		//		composite3.setSize(composite3.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		final Composite composite_up = new Composite(rightForm, SWT.BORDER);
		composite_up.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_up.setLayout(new FillLayout());
		Composite composite_down = new Composite(rightForm, SWT.BORDER);
		composite_down.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_down.setLayout(new BorderLayout(0, 0));

		ToolBar toolBar_down = new ToolBar(composite_down, SWT.FLAT | SWT.RIGHT);
		toolBar_down.setEnabled(false);
		toolBar_down.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		toolBar_down.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		toolBar_down.setLayoutData(BorderLayout.NORTH);

		ToolItem tltm_resultOutput = new ToolItem(toolBar_down, SWT.NONE);
		tltm_resultOutput.setText("Result Output");
		rightForm.setWeights(new int[] { 808, 124 });
		sashForm.setWeights(new int[] { 267, 1382 });

		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem selectedTI = (TreeItem) e.item;
				Control[] rightCs = composite_up.getChildren();
				for (int i = 0; i < rightCs.length; i++) {
					rightCs[i].dispose();
				}
				String selStr = selectedTI.getText();
				if (selStr.equalsIgnoreCase("Vacabulary")) {
					new VocabInfo(shell, composite_up, SWT.NONE);
					composite_up.layout();
				} else if (selStr.equalsIgnoreCase("User Container")) {
					new UserContainerUI(shell, composite_up, SWT.NONE);
					composite_up.layout();
				} else if (selStr.equalsIgnoreCase("Data Container")) {
					new DataContainerUI(shell, composite_up, SWT.NONE);
					composite_up.layout();
				} else if (selStr.equalsIgnoreCase("Policy")) {
					new Rules(shell, composite_up, SWT.NONE);
					composite_up.layout();
				}
			}
		});

	}

}
