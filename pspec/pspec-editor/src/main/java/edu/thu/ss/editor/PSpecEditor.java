package edu.thu.ss.editor;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.model.BaseModel;
import edu.thu.ss.editor.model.EditorModel;
import edu.thu.ss.editor.model.OutputEntry;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.OutputListener;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.model.RuleModel;
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.EditorUtil.ParseResult;
import edu.thu.ss.editor.util.MessagesUtil;
import edu.thu.ss.editor.view.DataContainerView;
import edu.thu.ss.editor.view.EditorView;
import edu.thu.ss.editor.view.OutputView;
import edu.thu.ss.editor.view.PolicyView;
import edu.thu.ss.editor.view.RuleView;
import edu.thu.ss.editor.view.UserContainerView;
import edu.thu.ss.editor.view.VocabularyView;
import edu.thu.ss.spec.lang.parser.PolicyWriter;
import edu.thu.ss.spec.lang.parser.VocabularyWriter;
import edu.thu.ss.spec.lang.parser.WritingException;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.manager.PolicyManager;
import edu.thu.ss.spec.manager.VocabularyManager;

public class PSpecEditor {

	private static PSpecEditor instance;

	public static PSpecEditor getInstance() {
		if (instance == null) {
			instance = new PSpecEditor();
		}
		return instance;
	}

	private Shell shell;

	private Display display;

	private EditorModel editorModel;

	private TreeItem vocabularyItems;

	private TreeItem policyItems;

	private Composite contentComposite;

	private Tree editorTree;

	private Map<String, MenuItem> menus = new HashMap<>();

	private Map<String, ToolItem> toolItems = new HashMap<>();

	private BaseModel editingModel;

	private OutputView outputView;

	private EditorView<?, ?> currentView;

	private OutputListener defaultOutputListener;

	/**
	 * Open the window.
	 */
	public void show() {
		editorModel = new EditorModel();
		initializeShell();
		initializeMenu();
		initializeToolbar();

		enableMenus(false);

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
		VocabularyManager.setCache(false);
		PolicyManager.setCache(false);

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

		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				mb.setText(getMessage(Confirm_Exit));
				mb.setMessage(getMessage(Confirm_Exit_Message));
				int ret = mb.open();
				if (ret == SWT.YES) {
					for (VocabularyModel model : editorModel.getVocabularies()) {
						save(model, false);
					}
					for (PolicyModel model : editorModel.getPolicies()) {
						save(model, false);
					}
				} else if (ret == SWT.NO) {
					//do nothing
				} else {
					event.doit = false;
				}
			}
		});

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
		menus.put(New_Vocabulary, newVocab);

		MenuItem newPolicy = new MenuItem(newMenu, SWT.NONE);
		newPolicy.setText(getMessage(New_Policy));
		menus.put(New_Policy, newPolicy);

		MenuItem openItems = new MenuItem(fileMenu, SWT.CASCADE);
		openItems.setText(getMessage(Open));

		Menu openMenu = new Menu(openItems);
		openItems.setMenu(openMenu);

		MenuItem openVocabulary = new MenuItem(openMenu, SWT.NONE);
		openVocabulary.setText(getMessage(Open_Vocabulary));
		menus.put(Open_Vocabulary, openVocabulary);

		MenuItem openPolicy = new MenuItem(openMenu, SWT.NONE);
		openPolicy.setText(getMessage(Open_Policy));
		menus.put(Open_Policy, openPolicy);

		MenuItem close = new MenuItem(fileMenu, SWT.NONE);
		close.setText(getMessage(Close));
		menus.put(Close, close);

		MenuItem save = new MenuItem(fileMenu, SWT.NONE);
		save.setText(getMessage(Save));
		save.setImage(SWTResourceManager.getImage(EditorUtil.Image_Save));
		menus.put(Save, save);

		MenuItem saveAs = new MenuItem(fileMenu, SWT.NONE);
		saveAs.setText(getMessage(Save_As));
		saveAs.setImage(SWTResourceManager.getImage(EditorUtil.Image_Save_As));
		menus.put(Save_As, saveAs);

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
		menus.put(About, about);

		newVocab.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Vocabulary vocabulary = new Vocabulary(editorModel.getNewVocabularyId());
				addVocabulary(new VocabularyModel(vocabulary, ""));
			}
		});

		newPolicy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Policy policy = new Policy(editorModel.getNewPolicyId());
				addPolicy(new PolicyModel(policy, ""));
			}
		});
		openVocabulary.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openVocabulary();
			}
		});

		openPolicy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openPolicy();
			}
		});

		close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox mb = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
				mb.setText(getMessage(Confirm_Close));
				mb.setMessage(getMessage(Confirm_Close_Message));
				int ret = mb.open();
				if (ret == SWT.YES) {
					if (!save(editingModel, false)) {
						closeModel();
					}
				} else if (ret == SWT.NO) {
					closeModel();
				}
			}
		});

		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save(editingModel, false);
			}
		});

		saveAs.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				save(editingModel, true);
			};
		});
	}

	private void initializeToolbar() {
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);

		ToolItem openVocabulary = EditorUtil.newToolItem(toolBar, getMessage(Open_Vocabulary));
		toolItems.put(Open_Vocabulary, openVocabulary);

		ToolItem openPolicy = EditorUtil.newToolItem(toolBar, getMessage(Open_Policy));
		toolItems.put(Open_Policy, openPolicy);

		ToolItem save = EditorUtil.newToolItem(toolBar, getMessage(Save));
		save.setImage(SWTResourceManager.getImage(EditorUtil.Image_Save));
		toolItems.put(Save, save);

		ToolItem saveAs = EditorUtil.newToolItem(toolBar, getMessage(Save_As));
		saveAs.setImage(SWTResourceManager.getImage(EditorUtil.Image_Save_As));
		toolItems.put(Save_As, saveAs);

		openVocabulary.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openVocabulary();
			}
		});

		openPolicy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openPolicy();
			}
		});

		save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save(editingModel, false);
			}
		});

		saveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save(editingModel, true);
			}
		});
	}

	private void initializeContent() {
		Composite composite = EditorUtil.newComposite(shell);
		composite.setLayout(new FillLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		SashForm mainForm = new SashForm(composite, SWT.NONE);

		editorTree = new Tree(mainForm, SWT.BORDER | SWT.V_SCROLL);
		vocabularyItems = EditorUtil.newTreeItem(editorTree, getMessage(Vocabulary));
		vocabularyItems.setImage(SWTResourceManager.getImage(EditorUtil.Image_Vocabular_Item));

		policyItems = EditorUtil.newTreeItem(editorTree, getMessage(Policy));
		policyItems.setImage(SWTResourceManager.getImage(EditorUtil.Image_Policy_Item));

		EditorUtil.processTree(editorTree);

		editorTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem item = (TreeItem) e.item;
				BaseModel model = (BaseModel) item.getData();
				EditorView<?, ?> view = (EditorView<?, ?>) item.getData(EditorUtil.View);
				switchView(model, view, null);
			}
		});

		/*editorTree.addListener(SWT.MouseHover, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = editorTree.getItem(new Point(event.x, event.y));
				if (item == null || item.getData() == null) {
					EditorUtil.hideMessage();
					return;
				}
				BaseModel model = (BaseModel) item.getData();
				if (!model.getPath().isEmpty()) {
					EditorUtil.showMessage(shell, model.getPath(), Display.getCurrent().getCursorLocation());
				}
			}
		});*/

		final SashForm rightForm = new SashForm(mainForm, SWT.VERTICAL);
		rightForm.setBackground(EditorUtil.getDefaultBackground());
		contentComposite = new Composite(rightForm, SWT.BORDER);
		contentComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		contentComposite.setLayout(EditorUtil.newNoMarginGridLayout(1, false));

		outputView = new OutputView(shell, rightForm, SWT.NONE, editorModel);

		mainForm.setWeights(new int[] { 1, 5 });
		rightForm.setWeights(new int[] { 5, 1 });

		Vocabulary vocabulary = new Vocabulary(editorModel.getNewVocabularyId());
		addVocabulary(new VocabularyModel(vocabulary, ""));

		Policy policy = new Policy(editorModel.getNewPolicyId());
		addPolicy(new PolicyModel(policy, ""));

		defaultOutputListener = new OutputListener() {
			@Override
			public void handleEvent(OutputEntry entry, Event event) {
				switch (entry.messageType) {
				case User_Category:
				case User_Category_Duplicate:
					switchView(entry.location, getUserContainerView((VocabularyModel) entry.location), null);
					break;
				case Data_Category_Duplicate:
				case Data_Category:
					switchView(entry.location, getDataContainerView((VocabularyModel) entry.location), null);
					break;
				default:
					RuleModel model = (RuleModel) entry.model;
					switchView(entry.location, getRuleView((PolicyModel) entry.location), model.getRule());
					break;
				}
			}
		};

	}

	protected void switchView(BaseModel model, EditorView<?, ?> view, Object obj) {
		editingModel = model;
		if (editingModel == null) {
			//hide views
			if (currentView != null) {
				EditorUtil.exclude(currentView);
				currentView = null;
				contentComposite.layout();
			}
			enableMenus(false);
			return;
		}

		if (currentView != view) {
			if (currentView != null) {
				EditorUtil.exclude(currentView);
			}
			EditorUtil.include(view);
			currentView = view;
			currentView.refresh();
			contentComposite.layout();
			enableMenus(true);
		}

		if (obj == null) {
			return;
		}
		if (obj instanceof Rule) {
			RuleView ruleView = (RuleView) currentView;
			ruleView.select((Rule) obj);
		}
	}

	public PolicyView getPolicyView(PolicyModel model) {
		for (TreeItem policyItem : policyItems.getItems()) {
			if (policyItem.getData().equals(model)) {
				return (PolicyView) policyItem.getData(EditorUtil.View);
			}
		}
		return null;
	}

	public VocabularyView getVocabularyView(VocabularyModel model) {
		for (TreeItem vocabularyItem : vocabularyItems.getItems()) {
			if (vocabularyItem.getData().equals(model)) {
				return (VocabularyView) vocabularyItem.getData(EditorUtil.View);
			}
		}
		return null;
	}

	public DataContainerView getDataContainerView(VocabularyModel model) {
		for (TreeItem vocabularyItem : vocabularyItems.getItems()) {
			if (!vocabularyItem.getData().equals(model)) {
				continue;
			}
			for (TreeItem item : vocabularyItem.getItems()) {
				EditorView<?, ?> view = (EditorView<?, ?>) item.getData(EditorUtil.View);
				if (view instanceof DataContainerView) {
					return (DataContainerView) view;
				}
			}
		}
		return null;
	}

	public UserContainerView getUserContainerView(VocabularyModel model) {
		for (TreeItem vocabularyItem : vocabularyItems.getItems()) {
			if (!vocabularyItem.getData().equals(model)) {
				continue;
			}
			for (TreeItem item : vocabularyItem.getItems()) {
				EditorView<?, ?> view = (EditorView<?, ?>) item.getData(EditorUtil.View);
				if (view instanceof UserContainerView) {
					return (UserContainerView) view;
				}
			}
		}
		return null;
	}

	public RuleView getRuleView(PolicyModel model) {
		for (TreeItem policyItem : policyItems.getItems()) {
			if (!policyItem.getData().equals(model)) {
				continue;
			}
			for (TreeItem item : policyItem.getItems()) {
				EditorView<?, ?> view = (EditorView<?, ?>) item.getData(EditorUtil.View);
				if (view instanceof RuleView) {
					return (RuleView) view;
				}
			}
		}
		return null;
	}

	public OutputListener getDefaultOutputListener() {
		return defaultOutputListener;
	}

	private void addVocabulary(VocabularyModel model) {
		Vocabulary vocabulary = model.getVocabulary();
		editorModel.getVocabularies().add(model);

		TreeItem item = EditorUtil.newTreeItem(vocabularyItems, vocabulary.getInfo().getId());
		item.setData(model);
		VocabularyView vocabularyView = new VocabularyView(shell, contentComposite, model, outputView,
				item);
		vocabularyView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		EditorUtil.exclude(vocabularyView);
		item.setData(EditorUtil.View, vocabularyView);

		TreeItem userItem = EditorUtil.newTreeItem(item, getMessage(User_Container));
		userItem.setData(model);
		UserContainerView userView = new UserContainerView(shell, contentComposite, model, outputView);
		userView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		EditorUtil.exclude(userView);
		userItem.setData(EditorUtil.View, userView);

		TreeItem dataItem = EditorUtil.newTreeItem(item, getMessage(Data_Container));
		dataItem.setData(model);
		DataContainerView dataView = new DataContainerView(shell, contentComposite, model, outputView);
		dataView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		EditorUtil.exclude(dataView);
		dataItem.setData(EditorUtil.View, dataView);
	}

	private void addPolicy(PolicyModel model) {
		editorModel.getPolicies().add(model);

		Policy policy = model.getPolicy();

		TreeItem item = EditorUtil.newTreeItem(policyItems, policy.getInfo().getId());
		item.setData(model);
		PolicyView policyView = new PolicyView(shell, contentComposite, model, outputView, item);
		policyView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		EditorUtil.exclude(policyView);
		item.setData(EditorUtil.View, policyView);

		TreeItem ruleItem = EditorUtil.newTreeItem(item, getMessage(Rule));
		ruleItem.setData(model);
		RuleView ruleView = new RuleView(shell, contentComposite, model, outputView);
		ruleView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		EditorUtil.exclude(ruleView);
		ruleItem.setData(EditorUtil.View, ruleView);
	}

	private void openVocabulary() {
		FileDialog dlg = EditorUtil.newOpenFileDialog(shell);
		String file = dlg.open();
		if (file != null) {
			if (editorModel.containVocabulary(file)) {
				EditorUtil.showMessageBox(shell, "", getMessage(Vocabulary_Opened_Message, file));
				return;
			}
			VocabularyModel vocabularyModel = new VocabularyModel(file);
			ParseResult result = EditorUtil.openVocabulary(vocabularyModel, shell, defaultOutputListener);
			if (result.equals(ParseResult.Invalid_Vocabulary)) {
				EditorUtil.showMessageBox(shell, "", getMessage(Vocabulary_Invalid_Document_Message, file));
				return;
			}
			if (result.equals(ParseResult.Error)) {
				EditorUtil.showMessageBox(shell, "", getMessage(Vocabulary_Parse_Error_Message, file));
			}
			addVocabulary(vocabularyModel);
			if (vocabularyModel.hasOutput()) {
				outputView.refresh();
			}
		}
	}

	private void openPolicy() {
		FileDialog dlg = EditorUtil.newOpenFileDialog(shell);
		String file = dlg.open();
		if (file != null) {
			if (editorModel.containPolicy(file)) {
				EditorUtil.showMessageBox(shell, "", getMessage(Policy_Opened_Message, file));
				return;
			}
			PolicyModel policyModel = new PolicyModel(file);
			ParseResult result = EditorUtil.openPolicy(policyModel, shell, defaultOutputListener);
			if (result.equals(ParseResult.Invalid_Policy)) {
				EditorUtil.showMessageBox(shell, "", getMessage(Policy_Invalid_Document_Message, file));
				return;
			}
			if (result.equals(ParseResult.Invalid_Vocabulary)) {
				EditorUtil.showMessageBox(shell, "",
						getMessage(Policy_Invalid_Vocabulary_Document_Message, file));
				return;
			}
			if (result.equals(ParseResult.Error)) {
				EditorUtil.showMessageBox(shell, "", getMessage(Policy_Parse_Error_Message, file));
			}

			addPolicy(policyModel);

			if (policyModel.hasOutput()) {
				outputView.refresh();
			}
		}
	}

	private void enableMenus(boolean enable) {
		menus.get(Close).setEnabled(enable);
		menus.get(Save).setEnabled(enable);
		menus.get(Save_As).setEnabled(enable);
		toolItems.get(Save).setEnabled(enable);
		toolItems.get(Save_As).setEnabled(enable);
	}

	private boolean save(BaseModel model, boolean rename) {
		shell.setFocus();
		if (model instanceof VocabularyModel) {
			return saveVocabulary((VocabularyModel) model, rename);
		} else if (model instanceof PolicyModel) {
			return savePolicy((PolicyModel) model, rename);
		}
		return false;
	}

	private void closeModel() {
		if (editingModel instanceof VocabularyModel) {
			for (TreeItem item : vocabularyItems.getItems()) {
				if (item.getData() == editingModel) {
					close(item);
					break;
				}
			}
		} else {
			for (TreeItem item : policyItems.getItems()) {
				if (item.getData() == editingModel) {
					close(item);
					break;
				}
			}
		}

		editingModel = null;
		currentView = null;
		switchView(null, null, null);
	}

	private void close(TreeItem item) {
		for (TreeItem sub : item.getItems()) {
			EditorView<?, ?> view = (EditorView<?, ?>) sub.getData(EditorUtil.View);
			view.dispose();
		}
		EditorView<?, ?> view = (EditorView<?, ?>) item.getData(EditorUtil.View);
		view.dispose();
		item.dispose();
	}

	private boolean saveVocabulary(VocabularyModel model, boolean rename) {
		if (model.hasOutput(OutputType.error)) {
			EditorUtil.showMessageBox(shell, "",
					getMessage(Vocabulary_Save_Error_Message, model.getVocabulary().getInfo().getId()));
			return true;
		}
		String path = model.getPath();
		String vocabularyId = model.getVocabulary().getInfo().getId();
		boolean refresh = false;
		if (path.isEmpty() || rename) {
			FileDialog dlg = EditorUtil.newSaveFileDialog(shell, vocabularyId + ".xml");
			dlg.setText(getMessage(Save_Vocabulary, vocabularyId));
			path = dlg.open();
			if (path == null) {
				return false;
			}
			refresh = true;
		}
		model.setPath(path);
		VocabularyWriter writer = new VocabularyWriter();
		try {
			writer.output(model.getVocabulary(), path);
		} catch (WritingException e) {
			e.printStackTrace();
		}
		EditorUtil.showMessageBox(shell, "",
				getMessage(Vocabulary_Save_Success_Message, vocabularyId, path));
		if (refresh) {
			VocabularyView view = getVocabularyView(model);
			view.refreshLocation();
		}
		return false;
	}

	private boolean savePolicy(PolicyModel model, boolean rename) {
		if (model.hasOutput(OutputType.error)) {
			EditorUtil.showMessageBox(shell, "",
					getMessage(Vocabulary_Save_Error_Message, model.getPolicy().getInfo().getId()));
			return true;
		}
		String path = model.getPath();
		String policyId = model.getPolicy().getInfo().getId();
		boolean refresh = false;
		if (path.isEmpty() || rename) {
			FileDialog dlg = EditorUtil.newSaveFileDialog(shell, policyId + ".xml");
			dlg.setText(getMessage(Save_Policy, policyId));
			path = dlg.open();
			if (path == null) {
				return false;
			}
			refresh = true;
		}
		model.setPath(path);
		PolicyWriter writer = new PolicyWriter();
		try {
			//TODO
			writer.output(model.getPolicy(), path);
		} catch (WritingException e) {
			e.printStackTrace();
		}
		EditorUtil.showMessageBox(shell, "", getMessage(Policy_Save_Success_Message, policyId, path));
		if (refresh) {
			PolicyView view = getPolicyView(model);
			view.refreshLocation();
		}
		return false;
	}

}
