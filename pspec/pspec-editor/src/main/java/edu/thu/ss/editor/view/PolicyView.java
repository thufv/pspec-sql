package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.PSpecEditor;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.editor.util.EditorUtil.ParseResult;
import edu.thu.ss.spec.lang.analyzer.rule.RuleResolver;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.ContactInfo;
import edu.thu.ss.spec.lang.pojo.Policy;

public class PolicyView extends EditorView<PolicyModel, Policy> {

	private Text name;
	private Text email;
	private Text country;
	private Text policyID;
	private Text organization;
	private Text address;
	private Text longDescription;
	private Text shortDescription;

	private Text location;
	private Text vocabularyLocation;

	private TreeItem editorItem;

	private EventTable table;

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public PolicyView(final Shell shell, Composite parent, PolicyModel model, OutputView outputView,
			TreeItem editorItem) {
		super(shell, parent, model, outputView);
		this.shell = shell;
		this.model = model;
		this.editorItem = editorItem;
		this.table = EditorUtil.newOutputTable(model, PSpecEditor.getInstance()
				.getDefaultOutputListener());

		this.setBackground(EditorUtil.getDefaultBackground());

		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Policy_Info));
		content.setLayout(new GridLayout(1, false));
		initializeContent(content);
	}

	private void initializeContent(Composite parent) {
		Group basicGroup = EditorUtil.newInnerGroup(parent, getMessage(Basic_Info));
		((GridData) basicGroup.getLayoutData()).grabExcessVerticalSpace = true;
		((GridData) basicGroup.getLayoutData()).verticalAlignment = SWT.FILL;
		initializeBasic(basicGroup);

		Group issuerGroup = EditorUtil.newInnerGroup(parent, getMessage(Issuer));
		initializeIssuer(issuerGroup);

	}

	private void initializeBasic(Composite parent) {
		final Policy policy = model.getPolicy();
		EditorUtil.newLabel(parent, getMessage(Policy_ID), EditorUtil.labelData());
		policyID = EditorUtil.newText(parent, EditorUtil.textData());
		policyID.setText(policy.getInfo().getId());
		policyID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = policyID.getText().trim();
				if (text.isEmpty()) {
					EditorUtil.showMessage(shell, getMessage(Vocabulary_ID_Empty_Message), policyID);
					policyID.setText(policy.getInfo().getId());
					policyID.selectAll();
					return;
				}

				if (!policy.getInfo().getId().equals(text)) {
					policy.getInfo().setId(text);
					editorItem.setText(text);
					if (model.hasOutput()) {
						outputView.refresh(model);
					}
				}
			}
		});

		EditorUtil.newLabel(parent, getMessage(Location), EditorUtil.labelData());
		location = EditorUtil.newText(parent, EditorUtil.textData());
		location.setText(model.getPath());
		location.setEnabled(false);

		EditorUtil.newLabel(parent, getMessage(Vocabulary_Location), EditorUtil.labelData());
		Composite vocabularyComposite = EditorUtil.newComposite(parent);
		vocabularyComposite.setLayout(EditorUtil.newNoMarginGridLayout(2, false));
		vocabularyComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		vocabularyLocation = EditorUtil.newText(vocabularyComposite, EditorUtil.textData());
		if (policy.getVocabularyLocation() != null) {
			vocabularyLocation.setText(policy.getVocabularyLocation().toString());
		} else {
			vocabularyLocation.setText("...");
		}
		vocabularyLocation.setEnabled(false);
		GridData baseData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		vocabularyLocation.setLayoutData(baseData);

		Button open = EditorUtil.newButton(vocabularyComposite, getMessage(Open));
		open.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = EditorUtil.newOpenFileDialog(shell);
				String file = dlg.open();
				if (file != null) {
					VocabularyModel vocabularyModel = new VocabularyModel(file);
					ParseResult result = EditorUtil.openVocabulary(vocabularyModel, shell, null);
					if (result.equals(ParseResult.Invalid_Vocabulary)) {
						EditorUtil.showMessageBox(shell, "",
								getMessage(Vocabulary_Invalid_Document_Message, file));
						return;
					}
					if (result.equals(ParseResult.Error)) {
						EditorUtil.showMessageBox(shell, "",
								getMessage(Policy_Vocabulary_Contains_Error_Message, file));
						return;
					}
					policy.setVocabulary(vocabularyModel.getVocabulary());
					vocabularyLocation.setText(file);

					//analyze
					boolean hasOutput = model.hasOutput();
					model.clearOutput();

					RuleResolver resolver = new RuleResolver(table);
					boolean error = resolver.analyze(model.getPolicy());

					model.initRuleModels();
					RuleView ruleView = PSpecEditor.getInstance().getRuleView(model);
					ruleView.refresh();

					if (hasOutput || model.hasOutput()) {
						outputView.refresh();
					}
					if (error) {
						EditorUtil.showMessageBox(shell, "",
								getMessage(Policy_Parse_Error_Message, model.getPolicy().getInfo().getId()));
					}

				}
			}
		});

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());
		shortDescription.setText(policy.getInfo().getShortDescription());
		shortDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				policy.getInfo().setShortDescription(shortDescription.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		longDescription.setText(policy.getInfo().getLongDescription());
		longDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		longDescription.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				policy.getInfo().setLongDescription(longDescription.getText().trim());

			}
		});
	}

	private void initializeIssuer(Composite parent) {
		final ContactInfo contact = model.getPolicy().getInfo().getContact();
		EditorUtil.newLabel(parent, getMessage(Issuer_Name), EditorUtil.labelData());
		name = EditorUtil.newText(parent, EditorUtil.textData());
		name.setText(contact.getName());
		name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				contact.setName(name.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Issuer_Email), EditorUtil.labelData());
		email = EditorUtil.newText(parent, EditorUtil.textData());
		email.setText(contact.getEmail());
		email.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				contact.setEmail(email.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Issuer_Organization), EditorUtil.labelData());
		organization = EditorUtil.newText(parent, EditorUtil.textData());
		organization.setText(contact.getOrganization());
		organization.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				contact.setOrganization(organization.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Issuer_Address), EditorUtil.labelData());
		address = EditorUtil.newText(parent, EditorUtil.textData());
		address.setText(contact.getAddress());
		address.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				contact.setAddress(address.getText().trim());

			}
		});

		EditorUtil.newLabel(parent, getMessage(Issuer_Country), EditorUtil.labelData());
		country = EditorUtil.newText(parent, EditorUtil.textData());
		country.setText(contact.getCountry());
		country.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				contact.setCountry(country.getText().trim());

			}
		});
	}

	public void refreshLocation() {
		location.setText(model.getPath());
	}

}
