package edu.thu.ss.editor;

import static edu.thu.ss.editor.util.MessagesUtil.*;

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

import edu.thu.ss.editor.model.EditorModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.MessagesUtil;
import edu.thu.ss.editor.view.DataContainerView;
import edu.thu.ss.editor.view.PolicyView;
import edu.thu.ss.editor.view.RuleView;
import edu.thu.ss.editor.view.UserContainerView;
import edu.thu.ss.editor.view.VocabularyView;

public class PSpecEditor {

	protected Shell shell;

	protected Display display;

	protected EditorModel model;

	protected TreeItem vocabularyItem;

	protected TreeItem policyItem;

	/**
	 * Open the window.
	 */
	public void show() {
		model = new EditorModel();
		initializeShell();
		initializeMenus();
		initializeContents();

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
	protected void initializeShell() {
		display = Display.getDefault();
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(EditorUtil.Image_Logo));
		int defaultWidth = 1024;
		int defaultHeight = 768;
		shell.setMinimumSize(defaultWidth, defaultHeight);
		shell.setSize(defaultWidth, defaultHeight);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x
				/ 2, Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);
		shell.setText(MessagesUtil.getMessage(MessagesUtil.Editor_Name));
		shell.setLayout(new FillLayout());

	}

	private void initializeMenus() {
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem fileItems = new MenuItem(menu, SWT.CASCADE);
		fileItems.setText(getMessage(File));

		Menu fileMenu = new Menu(fileItems);
		fileItems.setMenu(fileMenu);

		MenuItem newItems = new MenuItem(fileMenu, SWT.CASCADE);
		newItems.setText(getMessage(New));

		Menu newMenu = new Menu(newItems);
		newItems.setMenu(newMenu);

		MenuItem newVocab = new MenuItem(newMenu, SWT.NONE);
		newVocab.setText(getMessage(New_Vocabulary));

		MenuItem newPolicy = new MenuItem(newMenu, SWT.NONE);
		newPolicy.setText(getMessage(New_Policy));

		MenuItem openFile = new MenuItem(fileMenu, SWT.NONE);
		openFile.setText(getMessage(Open));

		MenuItem save = new MenuItem(fileMenu, SWT.NONE);
		save.setText(getMessage(Save));

		MenuItem exit = new MenuItem(fileMenu, SWT.NONE);
		exit.setText(getMessage(Exit));

		MenuItem edit = new MenuItem(menu, SWT.CASCADE);
		edit.setText(getMessage(Edit));

		Menu editMenu = new Menu(edit);
		edit.setMenu(editMenu);

		MenuItem help = new MenuItem(menu, SWT.CASCADE);
		help.setText(getMessage(Help));

		Menu helpMenu = new Menu(help);
		help.setMenu(helpMenu);

		MenuItem about = new MenuItem(helpMenu, SWT.NONE);
		about.setText(getMessage(About));

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
	}

	private void initializeContents() {
		SashForm mainForm = new SashForm(shell, SWT.NONE);
		//sashForm.setSashWidth(4);

		Tree editorTree = new Tree(mainForm, SWT.NONE);
		TreeItem vocabItem = EditorUtil.newTreeItem(editorTree, getMessage(Vocabulary));
		vocabItem.setImage(SWTResourceManager.getImage(EditorUtil.Image_Vocabular_Item));

		TreeItem userItem = EditorUtil.newTreeItem(vocabItem, getMessage(User_Container));

		TreeItem dataItem = EditorUtil.newTreeItem(vocabItem, getMessage(Data_Container));

		vocabItem.setExpanded(true);

		TreeItem policyItem = EditorUtil.newTreeItem(editorTree, getMessage(Policy));
		policyItem.setImage(SWTResourceManager.getImage(EditorUtil.Image_Policy_Item));

		policyItem.setExpanded(true);

		final SashForm contentForm = new SashForm(mainForm, SWT.VERTICAL);
		//rightForm.setSashWidth(4);

		final Composite content = new Composite(contentForm, SWT.BORDER);
		content.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		content.setLayout(new FillLayout());

		ToolBar outputBar = new ToolBar(contentForm, SWT.FLAT | SWT.RIGHT);
		outputBar.setEnabled(true);
		outputBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		outputBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		ToolItem outputText = new ToolItem(outputBar, SWT.NONE);
		outputText.setText(getMessage(Output));

		mainForm.setWeights(new int[] { 1, 5 });
		contentForm.setWeights(new int[] { 5, 1 });

		//TODO view switch
		editorTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				for (Control sub : content.getChildren()) {
					sub.dispose();
				}
				String name = item.getText();
				if (name.equals(getMessage(Vocabulary))) {
					new VocabularyView(shell, content, SWT.NONE);
				} else if (name.equals(getMessage(User_Container))) {
					new UserContainerView(shell, content, SWT.NONE);
				} else if (name.equals(getMessage(Data_Container))) {
					new DataContainerView(shell, content, SWT.NONE);
				} else if (name.equals(getMessage(Policy))) {
					new PolicyView(shell, content, SWT.NONE);
				} else if (name.equals(getMessage(Rule))) {
					new RuleView(shell, content, SWT.NONE);
				}
				content.layout();
			}
		});

	}

}
