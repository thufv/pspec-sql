package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.model.RuleModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.MessagesUtil;
import edu.thu.ss.spec.lang.pojo.CategoryRef;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.Desensitization;
import edu.thu.ss.spec.lang.pojo.Restriction;
import edu.thu.ss.spec.lang.pojo.Rule;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.PSpecUtil;

public class RuleView extends EditorView<PolicyModel, Rule> {

	private ToolItem add;
	private ToolItem delete;

	private ExpandBar ruleBar;

	private ExpandItem selectedItem;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public RuleView(Shell shell, Composite parent, PolicyModel model, OutputView outputView) {
		super(shell, parent, model, outputView);
		this.setBackground(EditorUtil.getDefaultBackground());

		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Policy_Rules));
		content.setLayout(new GridLayout(1, false));
		initializeContent(content);
	}

	@Override
	public void select(Rule object) {
		ExpandItem[] items = ruleBar.getItems();
		for (ExpandItem item : items) {
			RuleModel model = (RuleModel) item.getData();
			if (model.getRule().equals(object)) {
				item.setExpanded(true);
				selectItem(item);
			}
		}

	}

	private void selectItem(ExpandItem item) {
		if (selectedItem != null) {
			selectedItem.getControl().setBackground(EditorUtil.getDefaultBackground());
		}
		selectedItem = item;
		selectedItem.getControl().setBackground(EditorUtil.getSelectedBackground());
		delete.setEnabled(true);
	}

	private void initializeContent(Composite parent) {
		initializeToolbar(parent);

		ruleBar = new ExpandBar(parent, SWT.V_SCROLL);
		GridData barData = new GridData(SWT.FILL, SWT.FILL, true, true);
		ruleBar.setLayoutData(barData);
		ruleBar.setFont(EditorUtil.getDefaultFont());
		ruleBar.setBackground(EditorUtil.getDefaultBackground());
		initializeRules();

		if (model.getPolicy().getRules().size() == 0 && ruleBar.getVerticalBar() != null) {
			ruleBar.getVerticalBar().setVisible(false);
		}
	}

	private void initializeToolbar(Composite parent) {
		ToolBar toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);

		add = new ToolItem(toolBar, SWT.NONE);
		add.setToolTipText(MessagesUtil.Add);
		add.setImage(SWTResourceManager.getImage(EditorUtil.Image_Add_Rule));
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (model.getPolicy().getVocabulary() == null) {
					EditorUtil.showMessageBox(shell, "", getMessage(Policy_No_Vocabulary_Message));
					return;
				}

				RuleModel ruleModel = new RuleModel(new Rule());
				ruleModel.init();
				int ret = new RuleDialog(shell, ruleModel, model).open();
				if (ret == SWT.OK) {
					initializeRuleItem(ruleModel);
					model.addRuleModel(ruleModel);
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
				RuleModel ruleModel = (RuleModel) selectedItem.getData();
				model.removeRuleModel(ruleModel);
				if (ruleModel.hasOutput()) {
					//refresh
					outputView.refresh();
				}

				selectedItem.getControl().dispose();
				selectedItem.dispose();
				selectedItem = null;
				delete.setEnabled(false);
			}

		});
	}

	private void initializeRules() {
		for (RuleModel ruleModel : model.getRuleModels()) {
			initializeRuleItem(ruleModel);
		}

	}

	private void initializeRuleItem(RuleModel ruleModel) {
		final ExpandItem item = EditorUtil.newExpandItem(ruleBar, ruleModel.getRule().getId());
		item.setImage(SWTResourceManager.getImage(EditorUtil.Image_Rule));
		item.setData(ruleModel);

		initializeRuleItemContent(item, ruleModel);

	}

	private void updateRuleItem(ExpandItem item) {
		RuleModel ruleModel = (RuleModel) item.getData();
		int index = EditorUtil.indexOf(ruleBar.getItems(), item);
		item.getControl().dispose();
		item.dispose();

		item = EditorUtil.newExpandItem(ruleBar, ruleModel.getRule().getId(), index);
		item.setImage(SWTResourceManager.getImage(EditorUtil.Image_Rule));
		item.setData(ruleModel);

		initializeRuleItemContent(item, ruleModel);
		item.getControl().setBackground(EditorUtil.getSelectedBackground());
		item.setExpanded(true);
		selectedItem = item;
	}

	private void initializeRuleItemContent(final ExpandItem item, RuleModel ruleModel) {
		Composite ruleComposite = newItemComposite(ruleBar, 2);
		item.setControl(ruleComposite);
		ruleComposite.setLayout(new GridLayout());

		initializeRuleUser(ruleModel, ruleComposite);
		initializeRuleData(ruleModel, ruleComposite);
		initializeRuleRestrictions(ruleModel, ruleComposite);

		ruleComposite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				selectItem(item);
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				RuleModel ruleModel = (RuleModel) item.getData();
				int ret = new RuleDialog(shell, ruleModel, model).open();
				if (ret == SWT.OK) {
					updateRuleItem(item);
					if (ruleModel.hasOutput()) {
						ruleModel.clearOutput();
						outputView.refresh();
					}
				} else {
					ruleModel.init();
				}
			}
		});

		Point size = ruleComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		item.setHeight(size.y);
	}

	private void initializeRuleUser(RuleModel ruleModel, Composite parent) {
		EditorUtil.newLabel(parent, getMessage(User_Ref));

		Composite userComposite = newRuleComposite(parent, 2);

		for (UserRef ref : ruleModel.getUserRefs()) {
			newPointLabel(userComposite);
			StringBuilder sb = new StringBuilder();
			sb.append(ref.getRefid());
			sb.append(" ");
			if (ref.getExcludeRefs().size() > 0) {
				sb.append("exclude(");
				sb.append(PSpecUtil.format(ref.getExcludeRefs(), ", "));
			}
			newInfoLabel(userComposite, sb.toString(), ref);
		}
	}

	private void initializeRuleData(RuleModel ruleModel, Composite parent) {

		if (ruleModel.getRule().isSingle()) {
			EditorUtil.newLabel(parent, getMessage(Data_Ref));
		} else {
			EditorUtil.newLabel(parent, getMessage(Data_Association));
		}

		Composite dataComposite = newRuleComposite(parent, 4);

		for (DataRef ref : ruleModel.getDataRefs()) {
			newPointLabel(dataComposite);

			StringBuilder sb = new StringBuilder();
			sb.append(ref.getRefid());
			sb.append(" ");
			if (ref.getExcludeRefs().size() > 0) {
				sb.append("exclude(");
				sb.append(PSpecUtil.format(ref.getExcludeRefs(), ", "));
			}
			newInfoLabel(dataComposite, sb.toString(), ref);
			newBarLabel(dataComposite);
			newInfoLabel(dataComposite, ref.getAction().getId(), null);
		}

	}

	private void initializeRuleRestrictions(RuleModel ruleModel, Composite parent) {
		if (ruleModel.isForbid()) {
			Label forbidLabel = EditorUtil.newLabel(parent, getMessage(Forbid), null, false);
			forbidLabel.setFont(EditorUtil.getDefaultFont());
		} else {
			for (Restriction res : ruleModel.getRestrictions()) {
				initializeRestriction(res, ruleModel, parent);
			}
		}
	}

	private void initializeRestriction(Restriction restriction, RuleModel ruleModel, Composite parent) {
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
			newInfoLabel(restrictionComposite, sb.toString(), de.getDataRef());

			newBarLabel(restrictionComposite);

			newInfoLabel(restrictionComposite, PSpecUtil.format(de.getOperations(), ", "), null);

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

	private Label newInfoLabel(Composite parent, String text, CategoryRef<?> ref) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(text);
		label.setFont(EditorUtil.getDefaultFont());
		if (ref != null && ref.isError()) {
			label.setForeground(EditorUtil.getErrorForeground());
		} else {
			label.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		}
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

	@Override
	public void refresh() {
		selectedItem = null;
		for (ExpandItem item : ruleBar.getItems()) {
			item.getControl().dispose();
			item.dispose();
		}
		initializeRules();
	}
}