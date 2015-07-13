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

	public static Font getDefaultFont() {
		return SWTResourceManager.getFont("Arial", 12, SWT.NORMAL);
	}

	public static Font getDefaultBoldFont() {
		return SWTResourceManager.getFont("Arial", 12, SWT.BOLD);
	}

	public static Color getDefaultBackground() {
		return SWTResourceManager.getColor(SWT.COLOR_WHITE);
	}

	public static Color getSelectedBackground() {
		return SWTResourceManager.getColor(168, 208, 231);
	}

	private static void setStyle(Control control) {
		control.setFont(getDefaultFont());
		control.setBackground(getDefaultBackground());
	}

	public static Vocabulary openVocabulary(String path, Shell shell, EventTable table) {
		Vocabulary vocabulary = null;
		try {
			VocabularyParser parser = new VocabularyParser();
			parser.setEventTable(table);
			parser.setForceRegister(true);
			vocabulary = parser.parse(path);
			if (parser.isError()) {
				showMessageBox(shell, "", getMessage(Vocabulary_Parse_Error_Message, path));
			}
		} catch (InvalidVocabularyException e) {
			showMessageBox(shell, "", getMessage(Vocabulary_Invalid_Document_Message, path));
		}
		return vocabulary;
	}

	public static Policy openPolicy(String path, Shell shell, EventTable table) {
		Policy policy = null;
		try {
			PolicyParser parser = new PolicyParser();
			parser.setEventTable(table);
			parser.setForceRegister(true);
			policy = parser.parse(path);
			if (parser.isError()) {
				showMessageBox(shell, "", getMessage(Policy_Parse_Error_Message, path));
			}
		} catch (InvalidPolicyException e) {
			showMessageBox(shell, "", getMessage(Policy_Invalid_Document_Message, path));
		} catch (InvalidVocabularyException e) {
			showMessageBox(shell, "", getMessage(Policy_Invalid_Vocabulary_Document_Message, path, e.uri));
		}
		return policy;
	}

	public static Label newLabel(Composite parent, String text, GridData data, boolean prompt) {
		Label label = new Label(parent, SWT.NONE);
		label.setFont(getDefaultFont());
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
		//setStyle(combo);
		if (data != null) {
			combo.setLayoutData(data);
		}
		return combo;
	}

	public static TreeItem newTreeItem(Tree parent, String text) {
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setFont(getDefaultFont());
		item.setText(0, text);
		item.setExpanded(true);
		return item;
	}

	public static TreeItem newTreeItem(TreeItem parent, String text) {
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setFont(getDefaultFont());
		item.setText(text);
		item.setExpanded(true);
		return item;
	}

	public static Composite newComposite(Composite parent) {
		Composite comp = new Composite(parent, SWT.NO_BACKGROUND);
		setStyle(comp);
		return comp;
	}

	public static Group newGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		setStyle(group);
		group.setFont(getDefaultBoldFont());
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
		group.setFont(EditorUtil.getDefaultFont());
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
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x
				/ 2, Display.getCurrent().getClientArea().height / 2 - shell.getSize().y / 2);

	}

	public static void showMessage(Shell parent, String text, Control control) {
		showMessage(parent, text, control.toDisplay(control.getLocation()));
	}

	public static void showMessage(Shell parent, String text, Point point) {
		if (previousTip != null && !previousTip.isDisposed()) {
			previousTip.dispose();
		}
		ToolTip tip = new ToolTip(parent, SWT.BALLOON | SWT.ERROR);
		tip.setMessage(text);
		tip.setLocation(point);
		tip.setVisible(true);
		tip.setAutoHide(true);

		previousTip = tip;
	}

	public static void showMessageBox(Shell parent, String title, String message) {
		MessageBox box = new MessageBox(parent, SWT.ICON_ERROR | SWT.OK);
		box.setText(title);
		box.setMessage(message);
		box.open();
	}

	public static void showMessageBox(Shell parent, String title, List<String> messages) {
		MessageBox box = new MessageBox(parent, SWT.ICON_ERROR | SWT.OK);
		box.setText(title);
		box.setMessage(PSpecUtil.format(messages, System.lineSeparator()));
		box.open();
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

		return toArrayString(operations);
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
			return toArrayString(operations);
		}

	}

	public static String[] toArrayString(Collection<?> col) {
		String[] result = new String[col.size()];
		int i = 0;
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

	public static EventTable addPolicyLogListeners(EventTable table, final List<String> messages) {
		table.add(new PSpecListener() {
			@Override
			public void onRuleRefError(RefErrorType type, Rule rule, CategoryRef<?> ref, String refid) {
				if (rule == null) {
					return;
				}
				switch (type) {
				case Data_Association_Overlap:
					messages.add(getMessage(Rule_Data_Association_Non_Overlap_Message, rule.getId()));
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
						messages.add(getMessage(User_Category_Parent_Not_Exist_Message, category.getId(), refid));
					} else {
						messages.add(getMessage(Data_Category_Parent_Not_Exist_Message, category.getId(), refid));
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

}
