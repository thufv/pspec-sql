package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.model.OutputEntry;
import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.OutputModel;
import edu.thu.ss.editor.util.EditorUtil;

public class OutputView extends Composite {

	private Shell shell;
	private OutputModel model = new OutputModel();
	private TreeViewer viewer;

	private Label outputLabel;

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public OutputView(final Shell shell, Composite parent, int style) {
		super(parent, style);
		this.shell = shell;

		this.setBackground(EditorUtil.getDefaultBackground());
		this.setLayout(EditorUtil.newNoMarginGridLayout(1, false));

		initializeContent(this);
	}

	private void initializeContent(Composite parent) {
		outputLabel = EditorUtil.newLabel(parent, getOutputText());

		initializeViewer(parent);

	}

	private void initializeViewer(Composite parent) {
		Composite treeComposite = EditorUtil.newComposite(parent);
		treeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		TreeColumnLayout treeLayout = new TreeColumnLayout();
		treeComposite.setLayout(treeLayout);

		viewer = new TreeViewer(treeComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.setLabelProvider(new OutputLabelProvider());
		viewer.setContentProvider(new OutputContentProvider());
		viewer.setInput(model);

		Tree tree = viewer.getTree();
		tree.setFont(EditorUtil.getDefaultFont());
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		TreeColumn[] columns = new TreeColumn[2];
		String[] titles = new String[] { getMessage(Description), getMessage(Type), };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TreeColumn(tree, SWT.NONE);
			columns[i].setText(titles[i]);
			//			columns[i].setResizable(false);
		}
		treeLayout.setColumnData(columns[0], new ColumnWeightData(5, columns[0].getWidth()));
		treeLayout.setColumnData(columns[1], new ColumnWeightData(1, columns[1].getWidth()));

		tree.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = viewer.getTree().getItem(new Point(e.x, e.y));
				if (item == null) {
					return;
				}
				Object data = item.getData();
				if (data instanceof List<?>) {
					viewer.setExpandedState(data, !viewer.getExpandedState(data));
				} else {
					OutputEntry entry = (OutputEntry) data;
					if (entry.listener != null) {
						entry.listener.handleEvent(e);
					}
				}
			}
		});
	}

	private String getOutputText() {
		return getMessage(Output_Message, String.valueOf(model.getErrors().size()),
				String.valueOf(model.getWarnings().size()));

	}

	/**
	 * must be called explicitly
	 */
	public void refresh() {
		viewer.refresh();
		outputLabel.setText(getOutputText());
	}

	public void clear() {
		model.clear();
	}

	public void addWarning(String description, OutputType type, Listener listener) {
		model.getWarnings().add(new OutputEntry(description, type, listener, MessageType.warning));
	}

	public void addError(String description, OutputType type, Listener listener) {
		model.getErrors().add(new OutputEntry(description, type, listener, MessageType.error));
	}

	private class OutputLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0) {
				if (element instanceof List<?>) {
					List<?> list = (List<?>) element;
					return getColumnImage((OutputEntry) list.get(0));
				} else {
					return getColumnImage((OutputEntry) element);
				}
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof List<?>) {
				if (columnIndex == 0) {
					List<?> list = (List<?>) element;
					OutputEntry entry = (OutputEntry) list.get(0);
					switch (entry.messageType) {
					case error:
						return getMessage(Error);
					case warning:
						return getMessage(Warning);
					}
				}
			} else {
				OutputEntry entry = (OutputEntry) element;
				switch (columnIndex) {
				case 0:
					return entry.description;
				case 1:
					//TODO
					if (entry.outputType != null) {
						return entry.outputType.toString();
					}
				}
			}

			return "";
		}

		private Image getColumnImage(OutputEntry entry) {
			switch (entry.messageType) {
			case error:
				return SWTResourceManager.getImage(EditorUtil.Image_Error);
			case warning:
				return SWTResourceManager.getImage(EditorUtil.Image_Warning);
			default:
				return null;
			}
		}
	}

	private class OutputContentProvider implements IStructuredContentProvider, ITreeContentProvider {

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof List<?>) {
				List<?> list = (List<?>) parentElement;
				return list.toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof List<?>;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			OutputModel model = (OutputModel) inputElement;
			List<List<OutputEntry>> list = new ArrayList<>();
			if (!model.getErrors().isEmpty()) {
				list.add(model.getErrors());
			}
			if (!model.getWarnings().isEmpty()) {
				list.add(model.getWarnings());
			}
			return list.toArray();
		}

	}
}
