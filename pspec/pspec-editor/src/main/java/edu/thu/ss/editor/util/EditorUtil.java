package edu.thu.ss.editor.util;

import static edu.thu.ss.editor.util.MessagesUtil.Issuer;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditorUtil {

	public static final String Image_Logo = "img/logo.png";

	public static final String Image_Vocabular_Item = "img/vocab.png";

	public static final String Image_Policy_Item = "img/policy.png";

	public static final String Image_Add_Button = "img/add_button.png";

	public static final String Image_Add_Rule = "img/add_rule.png";
	public static final String Image_Delete_Rule = "img/delete_rule.png";
	public static final String Image_Edit_Rule = "img/edit_rule.png";

	public static final String Image_Delete_Button = "img/delete_button.png";

	public static final String Image_Point = "img/point.png";
	public static final String Image_Bar = "img/bar.png";

	public static final String Image_Rule = "img/rule.png";

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

	public static Font getDefaultFont() {
		return SWTResourceManager.getFont("Arial", 12, SWT.NORMAL);
	}

	public static Font getDefaultBoldFont() {
		return SWTResourceManager.getFont("Arial", 12, SWT.BOLD);
	}

	public static Color getDefaultBackground() {
		return SWTResourceManager.getColor(SWT.COLOR_WHITE);
	}

	private static void setStyle(Control control) {
		control.setFont(getDefaultFont());
		control.setBackground(getDefaultBackground());
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
		Combo combo = new Combo(parent, SWT.DROP_DOWN);
		setStyle(combo);
		if (data != null) {
			combo.setLayoutData(data);
		}
		return combo;
	}

	public static TreeItem newTreeItem(Tree parent, String text) {
		TreeItem item = new TreeItem(parent, SWT.NONE);
		item.setFont(getDefaultFont());
		item.setText(text);
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
}
