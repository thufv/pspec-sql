package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.thu.ss.editor.util.EditorUtil;

public class PolicyView extends Composite {

	private Text name;
	private Text email;
	private Text country;
	private Text policyID;
	private Text organization;
	private Text address;
	private Text longDescription;
	private Text shortDescription;
	private Combo vocabularyId;
	private Combo userContainerId;
	private Combo dataContainerId;

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public PolicyView(final Shell shell, Composite parent, int style) {
		super(parent, style);
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

		Group vocabularyGroup = EditorUtil.newInnerGroup(parent, getMessage(Vocabulary_Ref));
		initializeVocabulary(vocabularyGroup);

		Group issuerGroup = EditorUtil.newInnerGroup(parent, getMessage(Issuer));
		initializeIssuer(issuerGroup);

	}

	private void initializeBasic(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Policy_ID), EditorUtil.labelData());
		policyID = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		longDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

	}

	private void initializeVocabulary(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Vocabulary_Location), EditorUtil.labelData());
		vocabularyId = EditorUtil.newCombo(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(User_Container_ID), EditorUtil.labelData());
		userContainerId = EditorUtil.newCombo(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Data_Container_ID), EditorUtil.labelData());
		dataContainerId = EditorUtil.newCombo(parent, EditorUtil.textData());

	}

	private void initializeIssuer(Composite parent) {
		EditorUtil.newLabel(parent, getMessage(Issuer_Name), EditorUtil.labelData());
		name = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Issuer_Email), EditorUtil.labelData());
		email = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Issuer_Organization), EditorUtil.labelData());
		organization = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Issuer_Address), EditorUtil.labelData());
		address = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Issuer_Country), EditorUtil.labelData());
		country = EditorUtil.newText(parent, EditorUtil.textData());
	}
}
