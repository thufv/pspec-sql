package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.Vocabulary_Info;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Policy;

public class MetadataView extends EditorView<PolicyModel, Policy>{

	private TreeItem editorItem;
	private EventTable table;
	
	public MetadataView(Shell shell, Composite parent, PolicyModel model, OutputView outputView,
			TreeItem editorItem) {
		super(shell, parent, model, outputView);
		this.shell = shell;
		this.model = model;
		this.editorItem = editorItem;
		this.table = EditorUtil.newOutputTable(model, null);
		
		this.setBackground(EditorUtil.getDefaultBackground());
		this.setBackgroundMode(SWT.INHERIT_FORCE);

		this.setLayout(new FillLayout());

		Group content = EditorUtil.newGroup(this, getMessage(Vocabulary_Info));
		content.setLayout(new GridLayout(1, false));

		initializeContent(content);
	}

	private void initializeContent(Group content) {
		// TODO Auto-generated method stub
		
	}

}
