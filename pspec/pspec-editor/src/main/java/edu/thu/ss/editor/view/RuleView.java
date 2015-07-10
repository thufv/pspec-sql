package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
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
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.PSpecUtil;

public class RuleView extends Composite {

	private Shell shell;

	private ToolItem add;
	private ToolItem delete;

	private ExpandBar ruleBar;

	private ExpandItem selectedItem;

	private Policy policy;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public RuleView(Shell shell, Composite parent, int style, Policy policy) {
		super(parent, style);
		this.shell = shell;
		this.policy = policy;
		this.setBackground(EditorUtil.getDefaultBackground());

		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Policy_Rules));
		content.setLayout(new GridLayout(1, false));
		initializeContent(content);
	}

	private void initializeContent(Composite parent) {
		initializeToolbar(parent);

		ruleBar = new ExpandBar(parent, SWT.SINGLE | SWT.V_SCROLL);
		GridData barData = new GridData(SWT.FILL, SWT.FILL, true, true);
		ruleBar.setLayoutData(barData);
		ruleBar.setFont(EditorUtil.getDefaultFont());
		ruleBar.setBackground(EditorUtil.getDefaultBackground());

		initializeRules();

	}

	private void initializeToolbar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);

		add = new ToolItem(toolBar, SWT.NONE);
		add.setToolTipText(MessagesUtil.Add);
		add.setImage(SWTResourceManager.getImage(EditorUtil.Image_Add_Rule));
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Rule rule = new Rule();
				int ret = new RuleDialog(shell, rule, policy).open();
				if (ret == SWT.OK) {
					policy.getRules().add(rule);
					initializeRuleItem(rule);
				}
			}
		});

		delete = new ToolItem(toolBar, SWT.NONE);
		delete.setEnabled(false);
		delete.setToolTipText(MessagesUtil.Delete);
		delete.setImage(SWTResourceManager.getImage(EditorUtil.Image_Delete_Rule));
		delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				assert (selectedItem != null);
				Rule rule = (Rule) selectedItem.getData();
				policy.getRules().remove(rule);

				selectedItem.getControl().dispose();
				selectedItem.dispose();
				selectedItem = null;
				delete.setEnabled(false);
			}

		});
	}

	private void initializeRules() {
		Rule rule = new Rule();
		rule.setId("rule1");
		UserRef user1 = new UserRef();
		user1.setRefid("manager");
		rule.getUserRefs().add(user1);
		UserRef user2 = new UserRef();
		user2.setRefid("ceo");
		rule.getUserRefs().add(user2);

		DataRef data1 = new DataRef();
		data1.setRefid("pii");
		data1.setAction(Action.All);
		rule.getDataRefs().add(data1);
		DataRef data2 = new DataRef();
		data2.setRefid("financial");
		data2.setAction(Action.Projection);
		rule.getDataRefs().add(data2);

		Restriction res = new Restriction();
		Desensitization de = new Desensitization();
		de.getOperations().add(DesensitizeOperation.get("sum"));
		de.getOperations().add(DesensitizeOperation.get("avg"));
		res.getDesensitizations().add(de);

		rule.getRestrictions().add(res);

		initializeRuleItem(rule);

		rule = new Rule();
		rule.getRestrictions().add(res);
		initializeRuleItem(rule);

		for (Rule r : policy.getRules()) {
			initializeRuleItem(r);
		}

	}

	private void initializeRuleItem(Rule rule) {
		final ExpandItem item = EditorUtil.newExpandItem(ruleBar, rule.getId());
		item.setImage(SWTResourceManager.getImage(EditorUtil.Image_Rule));
		item.setData(rule);

		initializeRuleItemContent(item, rule);

	}

	private void updateRuleItem(ExpandItem item) {
		Rule rule = (Rule) item.getData();
		int index = EditorUtil.indexOf(ruleBar.getItems(), item);
		item.getControl().dispose();
		item.dispose();

		item = EditorUtil.newExpandItem(ruleBar, rule.getId(), index);
		item.setImage(SWTResourceManager.getImage(EditorUtil.Image_Rule));
		item.setData(rule);

		initializeRuleItemContent(item, rule);
		item.getControl().setBackground(EditorUtil.getSelectedBackground());
		item.setExpanded(true);
		selectedItem = item;
	}

	private void initializeRuleItemContent(final ExpandItem item, Rule rule) {
		Composite ruleComposite = newItemComposite(ruleBar, 2);
		item.setControl(ruleComposite);
		ruleComposite.setLayout(new GridLayout());

		initializeRuleUser(rule, ruleComposite);
		initializeRuleData(rule, ruleComposite);
		initializeRuleRestrictions(rule, ruleComposite);

		ruleComposite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (selectedItem != null) {
					selectedItem.getControl().setBackground(EditorUtil.getDefaultBackground());
				}
				selectedItem = item;
				selectedItem.getControl().setBackground(EditorUtil.getSelectedBackground());
				delete.setEnabled(true);
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Rule rule = (Rule) item.getData();
				int ret = new RuleDialog(shell, rule, policy).open();
				if (ret == SWT.OK) {
					updateRuleItem(item);
				}
			}
		});

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
				sb.append(PSpecUtil.format(ref.getExcludeRefs(), ", "));
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
				sb.append(PSpecUtil.format(ref.getExcludeRefs(), ", "));
			}
			newInfoLabel(dataComposite, sb.toString());
			newBarLabel(dataComposite);
			newInfoLabel(dataComposite, ref.getAction().getId());
		}

	}

	private void initializeRuleRestrictions(Rule rule, Composite parent) {
		if (rule.getRestriction().isForbid()) {
			Label forbidLabel = EditorUtil.newLabel(parent, getMessage(Forbid), null, false);
			forbidLabel.setFont(EditorUtil.getDefaultFont());
		} else {
			for (Restriction res : rule.getRestrictions()) {
				initializeRestriction(res, rule, parent);
			}
		}
	}

	private void initializeRestriction(Restriction restriction, Rule rule, Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Restriction));

		Composite restrictionComposite = newRuleComposite(parent, 4);
		for (Desensitization de : restriction.getDesensitizations()) {
			if (!de.effective()) {
				continue;
			}
			newPointLabel(restrictionComposite);
			StringBuilder sb = new StringBuilder();
			sb.append(getMessage(Desensitize));
			if (!de.getDataRefId().isEmpty()) {
				sb.append("(");
				sb.append(de.getDataRefId());
				sb.append(")");
			}
			newInfoLabel(restrictionComposite, sb.toString());

			newBarLabel(restrictionComposite);

			newInfoLabel(restrictionComposite, PSpecUtil.format(de.getOperations(), ", "));

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

	private Composite newItemComposite(Composite parent, int column) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(EditorUtil.getDefaultBackground());
		GridLayout layout = new GridLayout(column, false);
		layout.marginHeight = 0;
		composite.setLayout(layout);
		return composite;
	}
}