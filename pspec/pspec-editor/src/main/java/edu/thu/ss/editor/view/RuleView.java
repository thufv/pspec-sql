package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.MessagesUtil;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.DesensitizeOperation;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.SetUtil;

public class RuleView extends Composite {

	private ToolItem add;
	private ToolItem delete;
	private ToolItem edit;

	private ExpandBar ruleBar;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public RuleView(Shell shell, Composite parent, int style) {
		super(parent, style);
		this.setBackground(EditorUtil.getDefaultBackground());

		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Policy_Rules));
		content.setLayout(new GridLayout(1, false));
		initializeContent(content, shell);
	}

	private void initializeContent(Composite parent, Shell shell) {
		initializeToolbar(parent, shell);

		ruleBar = new ExpandBar(parent, SWT.V_SCROLL);
		GridData barData = new GridData(SWT.FILL, SWT.FILL, true, true);
		ruleBar.setLayoutData(barData);
		ruleBar.setFont(EditorUtil.getDefaultFont());
		ruleBar.setBackground(EditorUtil.getDefaultBackground());

		initializeRules();

	}

	private void initializeToolbar(Composite parent, final Shell shell) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);

		add = new ToolItem(toolBar, SWT.NONE);
		add.setToolTipText(MessagesUtil.Add);
		add.setImage(SWTResourceManager.getImage(EditorUtil.Image_Add_Rule));
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new RuleDialog(shell, null).open();
			}
		});

		edit = new ToolItem(toolBar, SWT.NONE);
		edit.setToolTipText(MessagesUtil.Edit);
		edit.setImage(SWTResourceManager.getImage(EditorUtil.Image_Edit_Rule));
		edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new RuleEdit(shell).open();
			}

		});

		delete = new ToolItem(toolBar, SWT.NONE);
		delete.setToolTipText(MessagesUtil.Delete);
		delete.setImage(SWTResourceManager.getImage(EditorUtil.Image_Delete_Rule));

	}

	private void initializeRules() {
		Rule rule = new Rule();
		rule.setId("rule1");
		UserRef user1 = new UserRef();
		user1.setRefid("manager");
		rule.getUserRefs().add(user1);
		UserRef user2 = new UserRef();
		user2.setRefid("analyst");
		rule.getUserRefs().add(user2);

		DataRef data1 = new DataRef();
		data1.setRefid("address");
		data1.setAction(Action.All);
		rule.getDataRefs().add(data1);
		DataRef data2 = new DataRef();
		data2.setRefid("finance");
		data2.setAction(Action.Projection);
		rule.getDataRefs().add(data2);

		Restriction res = new Restriction();
		Desensitization de = new Desensitization();
		Set<DesensitizeOperation> operations = new HashSet<>();
		operations.add(DesensitizeOperation.get("sum"));
		operations.add(DesensitizeOperation.get("avg"));
		de.setOperations(operations);

		res.setDesensitizations(new Desensitization[] { de });

		rule.getRestrictions().add(res);

		initializeRule(rule);
	}

	private void initializeRule(Rule rule) {
		ExpandItem item = new ExpandItem(ruleBar, SWT.NONE);
		item.setImage(SWTResourceManager.getImage(EditorUtil.Image_Rule));
		item.setExpanded(true);
		item.setText(rule.getId());

		Composite ruleComposite = EditorUtil.newComposite(ruleBar);
		item.setControl(ruleComposite);
		ruleComposite.setLayout(new GridLayout());

		initializeRuleUser(rule, ruleComposite);
		initializeRuleData(rule, ruleComposite);
		initializeRuleRestrictions(rule, ruleComposite);

		Point size = ruleComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		item.setHeight(size.y);
	}

	private void initializeRuleUser(Rule rule, Composite parent) {
		EditorUtil.newLabel(parent, getMessage(User_Ref));

		Composite userComposite = newRuleComposite(parent, 2);

		for (UserRef ref : rule.getUserRefs()) {
			newPointLabel(userComposite);
			StringBuilder sb = new StringBuilder();
			sb.append(ref.getRefid());
			sb.append(" ");
			if (ref.getExcludeRefs().size() > 0) {
				sb.append("exclude(");
				sb.append(SetUtil.format(ref.getExcludeRefs(), ", "));
			}
			newInfoLabel(userComposite, sb.toString());
		}
	}

	private void initializeRuleData(Rule rule, Composite parent) {
		List<DataRef> refs = null;

		if (rule.isSingle()) {
			EditorUtil.newLabel(parent, getMessage(Data_Ref));
			refs = rule.getDataRefs();
		} else {
			EditorUtil.newLabel(parent, getMessage(Data_Association));
			refs = rule.getAssociation().getDataRefs();
		}

		Composite dataComposite = newRuleComposite(parent, 4);

		for (DataRef ref : refs) {
			newPointLabel(dataComposite);

			StringBuilder sb = new StringBuilder();
			sb.append(ref.getRefid());
			sb.append(" ");
			if (ref.getExcludeRefs().size() > 0) {
				sb.append("exclude(");
				sb.append(SetUtil.format(ref.getExcludeRefs(), ", "));
			}
			newInfoLabel(dataComposite, sb.toString());
			newBarLabel(dataComposite);
			newInfoLabel(dataComposite, ref.getAction().getId());
		}

	}

	private void initializeRuleRestrictions(Rule rule, Composite parent) {
		if (rule.getRestriction().isForbid()) {
			Label forbidLabel = EditorUtil.newLabel(parent, getMessage(Forbid), null, false);
			forbidLabel.setFont(EditorUtil.getDefaultBoldFont());
		} else {
			for (Restriction restriction : rule.getRestrictions()) {
				initializeRestriction(restriction, rule, parent);
			}
		}
	}

	private void initializeRestriction(Restriction restriction, Rule rule, Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Restriction));

		Composite restrictionComposite = newRuleComposite(parent, 4);
		for (Desensitization de : restriction.getDesensitizations()) {
			newPointLabel(restrictionComposite);
			StringBuilder sb = new StringBuilder();
			sb.append(getMessage(Desensitize));
			if (de.getDataRef() != null) {
				sb.append("(");
				sb.append(de.getDataRef().getRefid());
				sb.append(")");
			}
			newInfoLabel(restrictionComposite, sb.toString());

			newBarLabel(restrictionComposite);

			newInfoLabel(restrictionComposite, SetUtil.format(de.getOperations(), ", "));

		}

	}

	private Label newPointLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(EditorUtil.Image_Point));
		return label;
	}

	private Label newBarLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(" ");
		label.setFont(EditorUtil.getDefaultFont());
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		return label;
	}

	private Label newInfoLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setFont(EditorUtil.getDefaultFont());
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		GridData data = new GridData();
		data.verticalAlignment = SWT.TOP;

		label.setLayoutData(data);
		return label;

	}

	private Composite newRuleComposite(Composite parent, int column) {
		Composite composite = EditorUtil.newComposite(parent);
		GridLayout layout = new GridLayout(column, false);
		layout.marginHeight = 0;
		layout.marginWidth = 30;
		composite.setLayout(layout);
		return composite;
	}
}