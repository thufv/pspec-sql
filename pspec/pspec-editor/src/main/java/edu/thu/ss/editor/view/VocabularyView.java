package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;

public class VocabularyView extends Composite {

	private Text name;
	private Text email;
	private Text country;
	private Text vocabularyID;
	private Text location;
	private Text organization;
	private Text address;
	private Text longDescription;
	private Text shortDescription;

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public VocabularyView(final Shell shell, Composite parent, int style) {
		super(parent, style);
		this.setBackground(EditorUtil.getDefaultBackground());
		this.setBackgroundMode(SWT.INHERIT_FORCE);

		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Vocabulary_Info));
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
		EditorUtil.newLabel(parent, getMessage(Vocabulary_ID), EditorUtil.labelData());
		vocabularyID = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Short_Description), EditorUtil.labelData());
		shortDescription = EditorUtil.newText(parent, EditorUtil.textData());

		EditorUtil.newLabel(parent, getMessage(Long_Description), EditorUtil.labelData());
		longDescription = new Text(parent, SWT.BORDER | SWT.V_SCROLL);
		longDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
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
