package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.Connect;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Connect_Database;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import edu.thu.ss.editor.model.MetadataModel;
import edu.thu.ss.editor.util.EditorUtil;

public class ConnectionDialog extends EditorDialog {

	private MetadataModel metadataModel;
	private String username;
	private String password;
	private String host;
	private String port;

	public ConnectionDialog(Shell parent, MetadataModel model, String username, String password,
			String host, String port) {
		super(parent);
		this.metadataModel = model;
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;

		dialog = new Shell(getParent(), SWT.TITLE | SWT.APPLICATION_MODAL);
		dialog.setText(getMessage(Connect));
		GridLayout layout = new GridLayout(1, true);
		layout.verticalSpacing = 10;
		layout.marginHeight = 20;
		dialog.setLayout(layout);

		initializeContent(dialog);
		dialog.pack();

		EditorUtil.centerLocation(dialog);

		connect();
	}

	private void initializeContent(Composite parent) {
		ProgressBar pb = EditorUtil.newProgressBar(parent, SWT.INDETERMINATE);
		Label label = EditorUtil.newLabel(parent, getMessage(Metadata_Connect_Database), null, false);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		label.setLayoutData(gridData);
	}

	private void connect() {
		metadataModel.connect(host, port, username, password);
	}

	public void close() {
		dialog.dispose();
	}
}
