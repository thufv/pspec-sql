package edu.thu.ss.editor.util;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.model.BaseModel;
import edu.thu.ss.editor.model.OutputEntry;
import edu.thu.ss.editor.model.OutputEntry.FixListener;
import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.model.RuleModel;
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.view.OutputView;
import edu.thu.ss.editor.view.RuleView;
import edu.thu.ss.spec.lang.analyzer.rule.RuleSimplifier.SimplificationLog;
import edu.thu.ss.spec.lang.parser.InvalidPolicyException;
import edu.thu.ss.spec.lang.parser.InvalidVocabularyException;
import edu.thu.ss.spec.lang.parser.PolicyParser;
import edu.thu.ss.spec.lang.parser.VocabularyParser;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.PSpecUtil;

public class EditorUtil {

	public static final String Image_Logo = "img/logo.png";

	public static final String Image_Vocabular_Item = "img/vocab.png";

	public static final String Image_Policy_Item = "img/policy.png";

	public static final String Image_Save = "img/save.gif";
	public static final String Image_Save_As = "img/saveas.gif";

	public static final String Image_Add_Button = "img/add_button.png";

	public static final String Image_Add_Rule = "img/add_rule.png";
	public static final String Image_Delete_Rule = "img/delete_rule.png";
	public static final String Image_Edit_Rule = "img/edit_rule.png";

	public static final String Image_Delete_Button = "img/delete_button.png";

	public static final String Image_Point = "img/point.png";
	public static final String Image_Bar = "img/bar.png";

	public static final String Image_Rule = "img/rule.png";

	public static final String Image_Error = "img/error.gif";
	public static final String Image_Warning = "img/warning.gif";

	public static final String Table_Editor = "table.editor";
	public static final String View = "view";

	private static ToolTip previousTip;

	public static enum OSType {
		Mac, Windows, Other
	}

	public static OSType getOSType() {
		String name = "os.name";
		String os = System.getProperty(name).toLowerCase();
		if (os.startsWith("windows")) {
			return OSType.Windows;
		} else if (os.startsWith("mac")) {
			return OSType.Mac;
		} else {
			return OSType.Other;
		}
	}

	public static boolean isWindows() {
		return getOSType().equals(OSType.Windows);
	}

	public static boolean isMac() {
		return getOSType().equals(OSType.Mac);

	}

	public static GridData labelData() {
		GridData data = new GridData();
		data.horizontalAlignment = SWT.BEGINNING;
		data.verticalAlignment = SWT.BEGINNING;
		data.widthHint = 150;
		return data;
	}

	public static final GridData textData() {
		GridData data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		return data;
	}

	public static Point getScreenSize() {
		Display display = Display.getCurrent();
		Rectangle rec = display.getMonitors()[0].getClientArea();
		return new Point(rec.width, rec.height);
	}

	public static void setDefaultFont(Control control) {
		// do nothing

	}

	public static void setDefaultBoldFont(Control control) {
		Font font = control.getFont();

		control.setFont(SWTResourceManager.getBoldFont(font));

	}

	public static Color getDefaultBackground() {
		return SWTResourceManager.getColor(SWT.COLOR_WHITE);
	}

	public static Color getWhiteBackground() {
		return SWTResourceManager.getColor(SWT.COLOR_WHITE);
	}

	public static Color getSelectedBackground() {
		return SWTResourceManager.getColor(168, 208, 231);
	}

	public static Color getErrorForeground() {
		return SWTResourceManager.getColor(SWT.COLOR_RED);
	}

	private static void setStyle(Control control) {
		setDefaultFont(control);

		control.setBackground(getDefaultBackground());
	}

	public static enum ParseResult {
		Success, Invalid_Vocabulary, Invalid_Policy, Error
	}

	public static ParseResult openVocabulary(VocabularyModel model, Shell shell, boolean output) {
		Vocabulary vocabulary = null;
		try {
			VocabularyParser parser = new VocabularyParser();
			if (output) {
				parser.setEventTable(newOutputTable(model, null));
			}
			parser.setForceRegister(true);
			vocabulary = parser.parse(model.getPath());
			model.init(vocabulary);
			if (parser.isError()) {
				return ParseResult.Error;
			}
		} catch (InvalidVocabularyException e) {
			return ParseResult.Invalid_Vocabulary;
		}
		return ParseResult.Success;
	}

	public static ParseResult openPolicy(PolicyModel model, Shell shell, boolean output) {
		Policy policy = null;
		try {
			PolicyParser parser = new PolicyParser();
			if (output) {
				parser.setEventTable(EditorUtil.newOutputTable(model, null));
			}
			parser.setForceRegister(true);
			policy = parser.parse(model.getPath());
			model.init(policy);
			if (parser.isError()) {
				return ParseResult.Error;
			}
		} catch (InvalidPolicyException e) {
			return ParseResult.Invalid_Policy;
		} catch (InvalidVocabularyException e) {
			return ParseResult.Invalid_Vocabulary;
		}
		return ParseResult.Success;
	}

	public static Label newLabel(Composite parent, String text, GridData data, boolean prompt) {
		Label label = new Label(parent, SWT.NONE);
		setDefaultFont(label);
		if (prompt) {
			label.setText(text + ":");
		} else {
			label.setText(text);
		}
		label.setLayoutData(data);
		return label;
	}

	public static Label newLabel(Composite parent, String text) {
		return newLabel(parent, text, null, true);
	}

	public static Label newLabel(Composite parent, String text, GridData data) {
		return newLabel(parent, text, data, true);
	}

	public static Text newText(Composite parent, GridData data) {
		Text text = new Text(parent, SWT.BORDER);
		setStyle(text);
		text.setLayoutData(data);
		return text;
	}

	public static Combo newCombo(Composite parent, GridData data) {
		Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		// setStyle(combo);
		if (data != null) {
			combo.setLayoutData(data);
		}
		return combo;
	}

	public static ToolBar newToolBar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		if (isWindows()) {
			setStyle(toolBar);
		}
		return toolBar;
	}

	public static TreeItem newTreeItem(Tree parent, String text) {
		TreeItem item = new TreeItem(parent, SWT.NONE);

		item.setText(0, text);
		item.setExpanded(true);
		return item;
	}

	public static TreeItem newTreeItem(TreeItem parent, String text) {
		TreeItem item = new TreeItem(parent, SWT.NONE);

		item.setText(text);
		item.setExpanded(true);
		return item;
	}

	public static Composite newComposite(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE | SWT.DOUBLE_BUFFERED);
		setStyle(comp);
		comp.setBackgroundMode(SWT.INHERIT_FORCE);
		return comp;
	}

	public static Group newGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		setStyle(group);
		setDefaultBoldFont(group);
		if (text != null) {
			group.setText(text);
		}
		return group;
	}

	public static Group newInnerGroup(Composite parent, String text) {
		Group group = EditorUtil.newGroup(parent, text);
		GridData groupData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		group.setLayoutData(groupData);
		GridLayout groupLayout = new GridLayout(2, false);
		group.setLayout(groupLayout);
		setDefaultBoldFont(group);
		return group;
	}

	public static Button newButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.NONE);
		setStyle(button);
		button.setText(text);
		return button;
	}

	public static Button newRadio(Composite parent, String text) {
		Button button = new Button(parent, SWT.RADIO);
		setStyle(button);
		button.setText(text);
		return button;
	}

	public static Button newCheck(Composite parent, String text) {
		Button button = new Button(parent, SWT.CHECK);
		setStyle(button);
		button.setText(text);
		return button;
	}

	public static GridLayout newNoMarginGridLayout(int columns, boolean equals) {
		GridLayout layout = new GridLayout(columns, equals);
		layout.marginHeight = layout.marginWidth = 0;
		layout.verticalSpacing = layout.horizontalSpacing = 0;
		return layout;
	}

	public static FileDialog newOpenFileDialog(Shell shell) {
		FileDialog dlg = new FileDialog(shell, SWT.SINGLE);
		dlg.setFilterExtensions(new String[] { "XML Files" });
		dlg.setFilterExtensions(new String[] { "*.xml" });
		return dlg;
	}

	public static FileDialog newSaveFileDialog(Shell shell, String name) {
		FileDialog dlg = new FileDialog(shell, SWT.SAVE);
		dlg.setFilterExtensions(new String[] { "XML Files" });
		dlg.setFilterExtensions(new String[] { "*.xml" });
		dlg.setFileName(name);
		return dlg;
	}

	public static Point getRawPoint(SelectionEvent e) {
		return ((Control) e.widget).toDisplay(e.x, e.y);
	}

	public static void centerLocation(Shell shell) {
		shell.setLocation(
				Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2,
				Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);

	}

	public static void showMessage(Shell parent, String text, Control control) {
		showMessage(parent, text, control.toDisplay(control.getLocation()));
	}

	public static void showMessage(Shell parent, String text, Point point) {
		hideMessage();
		ToolTip tip = new ToolTip(parent, SWT.BALLOON | SWT.ERROR);
		tip.setMessage(text);
		tip.setLocation(point);
		tip.setVisible(true);
		tip.setAutoHide(true);

		previousTip = tip;
	}

	public static void hideMessage() {
		if (previousTip != null && !previousTip.isDisposed()) {
			previousTip.dispose();
		}
	}

	private static int showMessageBox(Shell parent, String title, String message, int style) {
		MessageBox box = new MessageBox(parent, SWT.ICON_ERROR | SWT.OK);
		box.setText(title);
		box.setMessage(message);
		return box.open();
	}

	public static int showErrorMessageBox(Shell parent, String title, String message) {
		return showMessageBox(parent, title, message, SWT.ICON_ERROR | SWT.OK);
	}

	public static int showInfoMessageBox(Shell parent, String title, String message) {
		return showMessageBox(parent, title, message, SWT.ICON_INFORMATION | SWT.OK);
	}

	public static int showErrorMessageBox(Shell parent, String title, List<String> messages) {
		return showMessageBox(parent, title, PSpecUtil.format(messages, System.lineSeparator()),
				SWT.ICON_ERROR | SWT.OK);
	}

	public static int showQuestionMessageBox(Shell parent, String title, String message) {
		return showMessageBox(parent, title, message, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	}

	public static void processTree(Tree tree) {
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Tree tree = (Tree) e.getSource();
				if (tree.getSelectionCount() > 0) {
					TreeItem item = (TreeItem) tree.getSelection()[0];
					item.setExpanded(!item.getExpanded());
				}
			}
		});
	}

	public static void processTreeViewer(final TreeViewer viewer) {
		viewer.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Tree tree = (Tree) e.getSource();
				if (tree.getSelectionCount() > 0) {
					TreeItem item = (TreeItem) tree.getSelection()[0];
					Object ele = item.getData();
					viewer.setExpandedState(ele, !item.getExpanded());
				}
			}
		});
	}

	public static <T extends Category<T>> String[] getCategoryItems(CategoryContainer<T> container) {
		if (container == null) {
			return new String[0];
		}
		List<T> roots = container.materializeRoots();
		List<String> result = new ArrayList<>();
		result.add("");
		for (T root : roots) {
			buildCategoryItems(root, 0, result, container);
		}
		return result.toArray(new String[result.size()]);
	}

	public static <T extends Category<T>> String[] getChildCategoryItems(T category,
			CategoryContainer<T> container) {
		if (category == null || container == null) {
			return new String[0];
		}
		List<String> result = new ArrayList<>();
		buildCategoryItems(category, 0, result, container);
		result.set(0, "");
		return result.toArray(new String[result.size()]);
	}

	private static <T extends Category<T>> void buildCategoryItems(T category, int depth,
			List<String> result, CategoryContainer<T> container) {
		StringBuilder id = new StringBuilder(depth + category.getId().length());
		for (int i = 0; i < depth; i++) {
			id.append(' ');
		}
		id.append(category.getId());
		result.add(id.toString());

		List<T> children = category.getChildren();
		if (children != null) {
			for (T child : children) {
				if (container.contains(child.getId())) {
					buildCategoryItems(child, depth + 1, result, container);
				}
			}
		}

	}

	public static void updateItem(Combo combo, String oldItem, String newItem) {
		String[] items = combo.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(oldItem)) {
				combo.setItem(i, newItem);
				return;
			}
		}
	}

	public static void setSelectedItem(Combo combo, String text) {
		for (String item : combo.getItems()) {
			if (item.trim().equals(text)) {
				combo.setText(item);
			}
		}

	}

	public static ToolItem newToolItem(ToolBar toolBar, String text) {
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		if (text != null) {
			item.setText(text);
		}
		return item;
	}

	public static ExpandItem newExpandItem(ExpandBar parent, String text) {
		ExpandItem item = new ExpandItem(parent, SWT.NONE);
		item.setText(text);
		return item;
	}

	public static ExpandItem newExpandItem(ExpandBar parent, String text, int index) {
		ExpandItem item = new ExpandItem(parent, SWT.NONE, index);
		item.setText(text);
		return item;
	}

	public static int indexOf(Object[] array, Object obj) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == obj) {
				return i;
			}
		}
		return -1;
	}

	public static void dispose(TableItem item) {
		Object data = item.getData(Table_Editor);

		if (data == null || !(data instanceof List<?>)) {
			return;
		}
		@SuppressWarnings("unchecked")
		List<TableEditor> list = (List<TableEditor>) data;
		for (TableEditor editor : list) {
			editor.getEditor().dispose();
			editor.dispose();
		}
	}

	public static String[] getActionItems() {
		String[] items = new String[] { Action.Action_All, Action.Action_Projection,
				Action.Action_Condition };
		return items;
	}

	public static String[] getOperationItems(DataRef ref, DataContainer container) {
		DataCategory data = container.get(ref.getRefid());
		if (data == null) {
			return new String[0];
		}
		Set<DesensitizeOperation> operations = data.getAllOperations();

		return toArrayString(operations, true);
	}

	public static String[] getOperationItems(List<DataRef> list, DataContainer container) {
		Set<DesensitizeOperation> operations = null;
		for (DataRef ref : list) {
			DataCategory data = container.get(ref.getRefid());
			if (data == null) {
				continue;
			}
			if (operations == null) {
				operations = new LinkedHashSet<>(data.getAllOperations());
			} else {
				operations.retainAll(data.getAllOperations());
			}
		}
		if (operations == null) {
			return new String[0];
		} else {
			return toArrayString(operations, true);
		}

	}

	public static String[] toArrayString(Collection<?> col, boolean spacing) {
		int size = col.size();
		if (spacing) {
			size++;
		}
		String[] result = new String[size];
		int i = 0;
		if (spacing) {
			result[i++] = "";
		}
		Iterator<?> it = col.iterator();
		while (it.hasNext()) {
			result[i++] = it.next().toString();
		}
		return result;
	}

	public static void exclude(Control composite) {
		composite.setVisible(false);
		GridData data = (GridData) composite.getLayoutData();
		if (data == null) {
			data = new GridData();
			composite.setLayoutData(data);
		}
		data.exclude = true;
	}

	public static void include(Control composite) {
		composite.setVisible(true);
		GridData data = (GridData) composite.getLayoutData();
		if (data == null) {
			data = new GridData();
			composite.setLayoutData(data);
		}
		data.exclude = false;
	}

	public static EventTable newLogTable(final List<String> messages) {
		EventTable table = new EventTable();
		table.add(new PSpecListener() {
			@Override
			public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {
				if (rule == null) {
					return;
				}
				switch (type) {
				case Data_Association_Overlap:
					messages.add(getMessage(Rule_Data_Association_Not_Overlap_Message, rule.getId()));
					break;
				case Category_Ref_Not_Exist:
					if (ref instanceof UserRef) {
						messages.add(getMessage(User_Category_Not_Exist_Message, ref.getRefid(), rule.getId()));
					} else {
						messages.add(getMessage(Data_Category_Not_Exist_Message, ref.getRefid(), rule.getId()));
					}
					break;
				case Category_Exclude_Invalid:
					if (ref instanceof UserRef) {
						messages.add(getMessage(User_Category_Exclude_Invalid_Message, ref.getRefid(), refid,
								rule.getId()));
					} else {
						messages.add(getMessage(Data_Category_Exclude_Invalid_Message, ref.getRefid(), refid,
								rule.getId()));
					}
					break;
				case Category_Exclude_Not_Exist:
					if (ref instanceof UserRef) {
						messages.add(getMessage(User_Category_Exclude_Not_Exist_Message, ref.getRefid(), refid,
								rule.getId()));
					} else {
						messages.add(getMessage(Data_Category_Exclude_Not_Exist_Message, ref.getRefid(), refid,
								rule.getId()));
					}
					break;
				default:
					break;
				}
			}

			@Override
			public void onRestrictionError(RestrictionErrorType type, Rule rule, Restriction res,
					String refId) {
				switch (type) {
				case Associate_Restriction_DataRef_Not_Exist:
					messages.add(getMessage(Rule_Restriction_DataRef_Not_Exist_Message, refId,
							rule.getRestrictionIndex(res), rule.getId()));
					break;
				case Associate_Restriction_Explicit_DataRef:
					messages.add(getMessage(Rule_Restriction_Explicit_DataRef_Message, rule.getId()));
					break;
				case One_Forbid:
					messages.add(getMessage(Rule_Restriction_One_Forbid_Message, rule.getId()));
					break;
				case Single_One_Restriction:
					messages.add(getMessage(Rule_Restriction_Single_One_Message, rule.getId()));
					break;
				case Single_Restriction_No_DataRef:
					messages.add(getMessage(Rule_Restriction_Single_No_DataRef_Message, rule.getId()));
					break;
				case Single_Restriction_One_Desensitize:
					messages.add(getMessage(Rule_Restriction_Single_One_Desensitize_Message, rule.getId()));
					break;
				case Unsupported_Operation:
					messages.add(getMessage(Rule_Restriction_Unsupported_Operation_Message, rule.getId(),
							rule.getRestrictionIndex(res)));
					break;
				default:
					break;
				}
			}

			@Override
			public void onVocabularyError(VocabularyErrorType type, Category<?> category, String refid) {
				switch (type) {
				case Category_Cycle_Reference:
					if (category instanceof UserCategory) {
						messages.add(getMessage(User_Category_Parent_Cycle_Message, category.getId()));
					} else {
						messages.add(getMessage(Data_Category_Parent_Cycle_Message, category.getId()));
					}
					break;
				case Category_Duplicate:
					if (category instanceof UserCategory) {
						messages.add(getMessage(User_Category_ID_Unique_Message, category.getId()));
					} else {
						messages.add(getMessage(Data_Category_ID_Unique_Message, category.getId()));
					}
					break;
				case Category_Parent_Not_Exist:
					if (category instanceof UserCategory) {
						messages
								.add(getMessage(User_Category_Parent_Not_Exist_Message, category.getId(), refid));
					} else {
						messages
								.add(getMessage(Data_Category_Parent_Not_Exist_Message, category.getId(), refid));
					}
					break;
				case Cycle_Reference:
					messages.add(getMessage(Vocabulary_Cycle_Reference_Message));
					break;
				default:
					break;
				}
			}
		});
		return table;

	}

	public static EventTable newOutputTable(final BaseModel model, FixListener listener) {
		EventTable table = new EventTable();
		addOutputListener(table, model, listener);
		return table;

	}

	public static EventTable addOutputListener(EventTable table, final BaseModel model,
			final FixListener listener) {
		table.add(new PSpecListener() {
			private String message;

			@Override
			public void onParseRule(Rule rule) {
				PolicyModel policyModel = (PolicyModel) model;
				policyModel.getRuleModels().add(new RuleModel(rule));
			}

			@Override
			public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {
				PolicyModel policyModel = (PolicyModel) model;
				RuleModel ruleModel = policyModel.getRuleModel(rule);
				if (ruleModel == null) {
					return;
				}
				switch (type) {
				case Data_Association_Overlap:
					message = getMessage(Rule_Data_Association_Not_Overlap_Message, rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
							MessageType.Rule_Ref, listener));
					break;
				case Category_Ref_Not_Exist:
					if (ref instanceof UserRef) {
						message = getMessage(User_Category_Not_Exist_Message, ref.getRefid(), rule.getId());
						ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
								MessageType.Rule_Ref, listener));
					} else {
						message = getMessage(Data_Category_Not_Exist_Message, ref.getRefid(), rule.getId());
						ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
								MessageType.Rule_Ref, listener));
					}
					break;
				case Category_Exclude_Invalid:
					if (ref instanceof UserRef) {
						message = getMessage(User_Category_Exclude_Invalid_Message, ref.getRefid(), refid,
								rule.getId());
						ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
								MessageType.Rule_Ref, listener));
					} else {
						message = getMessage(Data_Category_Exclude_Invalid_Message, ref.getRefid(), refid,
								rule.getId());
						ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
								MessageType.Rule_Ref, listener));
					}
					break;
				case Category_Exclude_Not_Exist:
					if (ref instanceof UserRef) {
						message = getMessage(User_Category_Exclude_Not_Exist_Message, ref.getRefid(), refid,
								rule.getId());
						ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
								MessageType.Rule_Ref, listener));
					} else {
						message = getMessage(Data_Category_Exclude_Not_Exist_Message, ref.getRefid(), refid,
								rule.getId());
						ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.error, model, ruleModel,
								MessageType.Rule_Ref, listener));
					}
					break;
				default:
					break;
				}
			}

			@Override
			public void onRestrictionError(RestrictionErrorType type, Rule rule, Restriction res,
					String refId) {
				PolicyModel policyModel = (PolicyModel) model;
				RuleModel ruleModel = policyModel.getRuleModel(rule);
				switch (type) {
				case Associate_Restriction_DataRef_Not_Exist:
					message = getMessage(Rule_Restriction_DataRef_Not_Exist_Message, refId,
							rule.getRestrictionIndex(res), rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				case Associate_Restriction_Explicit_DataRef:
					message = getMessage(Rule_Restriction_Explicit_DataRef_Message, rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				case One_Forbid:
					message = getMessage(Rule_Restriction_One_Forbid_Message, rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				case Single_One_Restriction:
					message = getMessage(Rule_Restriction_Single_One_Message, rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				case Single_Restriction_No_DataRef:
					message = getMessage(Rule_Restriction_Single_No_DataRef_Message, rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				case Single_Restriction_One_Desensitize:
					message = getMessage(Rule_Restriction_Single_One_Desensitize_Message, rule.getId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				case Unsupported_Operation:
					message = getMessage(Rule_Restriction_Unsupported_Operation_Message, rule.getId(),
							rule.getRestrictionIndex(res));
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.warning, model, ruleModel,
							MessageType.Rule_Restriction, listener));
					break;
				default:
					break;
				}
			}

			@Override
			public void onVocabularyError(VocabularyErrorType type, Category<?> category, String refid) {
				VocabularyModel vocabularyModel = (VocabularyModel) model;
				vocabularyModel.getErrors().add(category.getId());
				switch (type) {
				case Category_Cycle_Reference:
					if (category instanceof UserCategory) {
						message = getMessage(User_Category_Parent_Cycle_Message, category.getId());
						model.addOutput(OutputEntry.newInstance(message, OutputType.warning, model,
								MessageType.User_Category, listener));
					} else {
						message = getMessage(Data_Category_Parent_Cycle_Message, category.getId());
						model.addOutput(OutputEntry.newInstance(message, OutputType.warning, model,
								MessageType.Data_Category, listener));
					}
					break;
				case Category_Duplicate:
					if (category instanceof UserCategory) {
						message = getMessage(User_Category_Duplicate_Message, category.getId());
						model.addOutput(OutputEntry.newInstance(message, OutputType.error, model,
								MessageType.User_Category_Duplicate, category.getId(), listener));
					} else {
						message = getMessage(Data_Category_Duplicate_Message, category.getId());
						model.addOutput(OutputEntry.newInstance(message, OutputType.error, model,
								MessageType.Data_Category_Duplicate, category.getId(), listener));
					}
					break;
				case Category_Parent_Not_Exist:
					if (category instanceof UserCategory) {
						message = getMessage(User_Category_Parent_Not_Exist_Message, category.getId(), refid);
						model.addOutput(OutputEntry.newInstance(message, OutputType.warning, model,
								MessageType.User_Category, category.getId(), listener));
					} else {
						message = getMessage(Data_Category_Parent_Not_Exist_Message, category.getId(), refid);
						model.addOutput(OutputEntry.newInstance(message, OutputType.warning, model,
								MessageType.Data_Category, category.getId(), listener));
					}

					break;
				case Cycle_Reference:
					message = getMessage(Vocabulary_Cycle_Reference_Message, category.getId(), refid);
					model.addOutput(OutputEntry.newInstance(message, OutputType.warning, model,
							MessageType.Vocabulary, listener));
					break;
				default:
					break;
				}
			}

			@Override
			public void onAnalysis(AnalysisType type, ExpandedRule... rules) {
				PolicyModel policyModel = (PolicyModel) model;

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < rules.length - 1; i++) {
					ExpandedRule rule = rules[i];
					sb.append(rule.getRuleId());
					sb.append(",");
				}
				RuleModel ruleModel = null;
				switch (type) {
				case Redundancy:
					ruleModel = policyModel.getRuleModel(rules[0].getRule());
					message = getMessage(Rule_Redundancy_Message, rules[0].getRuleId(), rules[1].getRuleId());
					model.addOutput(OutputEntry.newInstance(message, OutputType.analysis, model, ruleModel,
							MessageType.Redundancy, listener, rules[0]));
					break;
				case Normal_Consistency:
					sb.append(rules[rules.length - 1].getRuleId());
					message = getMessage(Rule_Normal_Inconsistency_Message, sb.toString());
					model.addOutput(OutputEntry.newInstance(message, OutputType.analysis, model,
							MessageType.Normal_Consistency, (Object[]) rules));
					break;
				case Approximate_Consistency:
					sb.append(rules[rules.length - 1].getRuleId());
					message = getMessage(Rule_Approximate_Inconsistency_Message, sb.toString());
					model.addOutput(OutputEntry.newInstance(message, OutputType.analysis, model,
							MessageType.Approximate_Consistency, (Object[]) rules));
					break;
				case Strong_Consistency:
					sb.deleteCharAt(sb.length() - 1);
					ruleModel = policyModel.getRuleModel(rules[rules.length - 1].getRule());
					message = getMessage(Rule_Strong_Inconsistency_Message, sb.toString(),
							rules[rules.length - 1].getRuleId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.analysis, model,
							ruleModel, MessageType.Strong_Consistency, (Object[]) rules));
					break;
				case Enhanced_Strong_Consistency:
					sb.deleteCharAt(sb.length() - 1);
					ruleModel = policyModel.getRuleModel(rules[rules.length - 1].getRule());
					message = getMessage(Rule_Enhanced_Strong_Inconsistency_Message, sb.toString(),
							rules[rules.length - 1].getRuleId());
					ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.analysis, model,
							ruleModel, MessageType.Enhanced_Strong_Consistency, (Object[]) rules));
					break;
				default:
					break;
				}

			}
		});

		return table;
	}

	public static void addSimplifyOutput(PolicyModel policyModel, RuleModel ruleModel,
			SimplificationLog log, FixListener listener) {
		String message = "";
		String ruleId = ruleModel.getRule().getId();
		ruleModel.clearOutput(OutputType.analysis);
		for (UserRef userRef : log.redundantUsers) {
			message = getMessage(Rule_User_Ref_Simplify_Message, userRef.getRefid(), ruleId);
			ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.analysis, policyModel,
					ruleModel, MessageType.Simplify, listener, userRef));
		}
		for (DataRef dataRef : log.redundantDatas) {
			message = getMessage(Rule_Data_Ref_Simplify_Message, dataRef.getRefid(), ruleId);
			ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.analysis, policyModel,
					ruleModel, MessageType.Simplify, listener, dataRef));
		}
		for (Restriction res : log.redundantRestrictions) {
			message = getMessage(Rule_Restriction_Simplify_Message,
					ruleModel.getRule().getRestrictionIndex(res), ruleId);
			ruleModel.addOutput(OutputEntry.newInstance(message, OutputType.analysis, policyModel,
					ruleModel, MessageType.Simplify, listener, res));
		}
	}

	public static void showPopupMenu(Menu menu, Shell shell, MouseEvent e) {
		Point p = ((Control) e.widget).toDisplay(e.x, e.y);
		menu.setLocation(p);
		menu.setVisible(true);
		Display display = Display.getCurrent();
		while (!menu.isDisposed() && menu.isVisible()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		menu.dispose();

	}

	public static FixListener newSimplifyListener(final RuleView ruleView,
			final OutputView outputView) {
		return new FixListener() {
			@Override
			public void handleEvent(OutputEntry entry) {
				assert(entry.data.length == 1);
				RuleModel model = (RuleModel) entry.model;
				Object data = entry.data[0];
				if (data instanceof UserRef) {
					model.simplifyUserRef((UserRef) data);
				} else if (data instanceof DataRef) {
					model.simplifyDataRef((DataRef) data);
				} else if (data instanceof Restriction) {
					model.simplifyRestriction((Restriction) data);
				}
				ruleView.refreshRuleItem(model, false);
				model.removeOutput(entry);
				outputView.remove(entry);
			}
		};
	}
}
