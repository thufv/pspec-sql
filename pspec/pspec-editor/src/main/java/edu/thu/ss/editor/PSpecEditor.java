package edu.thu.ss.editor;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

public class PSpecEditor {

	private Shell shell;

	private Display display;

	private EditorModel model;

	private TreeItem vocabularyItem;

	private TreeItem policyItem;

	private Composite content;

	private Tree editorTree;

	/**
	 * Open the window.
	 */
	public void show() {
		model = new EditorModel();
		initializeShell();
		initializeMenu();
		initializeToolbar();
		initializeContent();

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
		EditorUtil.centerLocation(shell);
		shell.setText(MessagesUtil.getMessage(MessagesUtil.Editor_Name));

		shell.setLayout(new GridLayout());
	}

	private void initializeMenu() {
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

		newVocab.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Vocabulary vocabulary = new Vocabulary(model.getNewVocabularyId());
				addVocabulary(vocabulary);
			}
		});

		newPolicy.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Policy policy = new Policy(model.getNewPolicyId());
				addPolicy(policy);
			}
		});

	}

	private void initializeToolbar() {
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);

		ToolItem openVocabulary = EditorUtil.newToolItem(toolBar, getMessage(Open_Vocabulary));

		ToolItem openPolicy = EditorUtil.newToolItem(toolBar, getMessage(Open_Policy));

		ToolItem save = EditorUtil.newToolItem(toolBar, getMessage(Save));

	}

	private void initializeContent() {
		Composite composite = EditorUtil.newComposite(shell);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		SashForm mainForm = new SashForm(composite, SWT.NONE);

		editorTree = new Tree(mainForm, SWT.BORDER | SWT.V_SCROLL);
		vocabularyItem = EditorUtil.newTreeItem(editorTree, getMessage(Vocabulary));
		vocabularyItem.setImage(SWTResourceManager.getImage(EditorUtil.Image_Vocabular_Item));

		policyItem = EditorUtil.newTreeItem(editorTree, getMessage(Policy));
		policyItem.setImage(SWTResourceManager.getImage(EditorUtil.Image_Policy_Item));

		Vocabulary vocabulary = new Vocabulary(model.getNewVocabularyId());
		addVocabulary(vocabulary);

		Policy policy = new Policy(model.getNewPolicyId());
		addPolicy(policy);

		EditorUtil.processTree(editorTree);

		editorTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				//TODO view switch
				for (Control sub : content.getChildren()) {
					sub.dispose();
				}
				if (item.getParentItem() == vocabularyItem) {
					new VocabularyView(shell, content, SWT.NONE, (Vocabulary) item.getData(), item);
				} else if (item.getParentItem() == policyItem) {
					new PolicyView(shell, content, SWT.NONE, (Policy) item.getData(), item);
				} else if (item.getText().equals(getMessage(User_Container))) {
					Vocabulary vocabulary = (Vocabulary) item.getData();
					new UserContainerView(shell, content, SWT.NONE, vocabulary);
				} else if (item.getText().equals(getMessage(Data_Container))) {
					Vocabulary vocabulary = (Vocabulary) item.getData();
					new DataContainerView(shell, content, SWT.NONE, vocabulary);
				} else if (item.getText().equals(getMessage(Rule))) {
					new RuleView(shell, content, SWT.NONE, (Policy) item.getData());
				}
				content.layout();
			}
		});

		final SashForm contentForm = new SashForm(mainForm, SWT.VERTICAL);

		content = new Composite(contentForm, SWT.BORDER);
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
	}

	private void addVocabulary(Vocabulary vocabulary) {
		model.getVocabularies().add(vocabulary);

		TreeItem item = EditorUtil.newTreeItem(vocabularyItem, vocabulary.getInfo().getId());
		item.setData(vocabulary);

		TreeItem userItem = EditorUtil.newTreeItem(item, getMessage(User_Container));
		userItem.setData(vocabulary);
		TreeItem dataItem = EditorUtil.newTreeItem(item, getMessage(Data_Container));
		dataItem.setData(vocabulary);

	}

	private void addPolicy(Policy policy) {
		model.getPolicies().add(policy);

		TreeItem item = EditorUtil.newTreeItem(policyItem, policy.getInfo().getId());
		item.setData(policy);

		TreeItem ruleItem = EditorUtil.newTreeItem(item, getMessage(Rule));
		ruleItem.setData(policy);

	}
}
