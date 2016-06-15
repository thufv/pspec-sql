package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.Analysis;
import static edu.thu.ss.editor.util.MessagesUtil.Delete;
import static edu.thu.ss.editor.util.MessagesUtil.Description;
import static edu.thu.ss.editor.util.MessagesUtil.Fix;
import static edu.thu.ss.editor.util.MessagesUtil.Goto;
import static edu.thu.ss.editor.util.MessagesUtil.Location;
import static edu.thu.ss.editor.util.MessagesUtil.Output_Message;
import static edu.thu.ss.editor.util.MessagesUtil.Policy_Output;
import static edu.thu.ss.editor.util.MessagesUtil.Type;
import static edu.thu.ss.editor.util.MessagesUtil.Vocabulary_Output;
import static edu.thu.ss.editor.util.MessagesUtil.Metadata_Output;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.PSpecEditor;
import edu.thu.ss.editor.model.BaseModel;
import edu.thu.ss.editor.model.EditorModel;
import edu.thu.ss.editor.model.MetadataModel;
import edu.thu.ss.editor.model.OutputEntry;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.model.VocabularyModel;
import edu.thu.ss.editor.util.EditorUtil;

public class OutputView extends Composite {

	private Shell shell;

	private EditorModel model;
	private TreeViewer viewer;

	private Label outputLabel;

	private OutputContentProvider contentProvider;
	private OutputLabelProvider labelProvider;

	private Map<OutputType, Integer> outputCounts = new HashMap<>();

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public OutputView(final Shell shell, Composite parent, int style, EditorModel model) {
		super(parent, style);
		this.shell = shell;
		this.model = model;
		this.setBackground(EditorUtil.getDefaultBackground());
		this.setLayout(EditorUtil.newNoMarginGridLayout(1, false));

		initializeContent(this);
	}

	private void initializeContent(Composite parent) {
		outputLabel = EditorUtil.newLabel(parent, "");
		initializeViewer(parent);

	}

	private void initializeViewer(Composite parent) {
		Composite treeComposite = EditorUtil.newComposite(parent);
		treeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		TreeColumnLayout treeLayout = new TreeColumnLayout();
		treeComposite.setLayout(treeLayout);

		viewer = new TreeViewer(treeComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		labelProvider = new OutputLabelProvider();
		viewer.setLabelProvider(labelProvider);
		contentProvider = new OutputContentProvider();
		viewer.setContentProvider(contentProvider);
		viewer.setInput(model);

		final Tree tree = viewer.getTree();
		EditorUtil.setDefaultFont(tree);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		TreeColumn[] columns = new TreeColumn[3];
		String[] titles = new String[] { getMessage(Description), getMessage(Location),
				getMessage(Type) };
		for (int i = 0; i < columns.length; i++) {
			columns[i] = new TreeColumn(tree, SWT.NONE);
			columns[i].setText(titles[i]);
			//			columns[i].setResizable(false);
		}
		treeLayout.setColumnData(columns[0], new ColumnWeightData(5, columns[0].getWidth()));
		treeLayout.setColumnData(columns[1], new ColumnWeightData(1, columns[0].getWidth()));
		treeLayout.setColumnData(columns[2], new ColumnWeightData(1, columns[1].getWidth()));

		tree.addListener(SWT.MouseDoubleClick, new Listener() {
			@Override
			public void handleEvent(Event e) {
				TreeItem item = viewer.getTree().getItem(new Point(e.x, e.y));
				if (item == null) {
					return;
				}
				Object data = item.getData();
				if (data instanceof OutputType) {
					viewer.setExpandedState(data, !viewer.getExpandedState(data));
				} else {
					OutputEntry entry = (OutputEntry) data;
					PSpecEditor.getInstance().switchView(entry);
				}
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3) {
					TreeItem item = tree.getItem(new Point(e.x, e.y));
					if (item == null) {
						return;
					}
					Object data = item.getData();
					if (!(data instanceof OutputEntry)) {
						return;
					}
					OutputEntry entry = (OutputEntry) data;
					if (entry.outputType.equals(OutputType.analysis)) {
						Menu menu = createOutputPopup(tree, entry);
						EditorUtil.showPopupMenu(menu, shell, e);
					}
				}
			}
		});
	}

	public Menu createOutputPopup(Control control, final OutputEntry entry) {
		Menu popMenu = new Menu(control);
		MenuItem gotoItem = new MenuItem(popMenu, SWT.PUSH);
		gotoItem.setText(getMessage(Goto));

		gotoItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PSpecEditor.getInstance().switchView(entry);
			}
		});
		if (entry.fixListener != null) {
			MenuItem fixItem = new MenuItem(popMenu, SWT.PUSH);
			fixItem.setText(getMessage(Fix));
			fixItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					entry.fixListener.handleEvent(entry);
				}
			});
		}
		if (entry.outputType.equals(Analysis)) {
			MenuItem deleteItem = new MenuItem(popMenu, SWT.PUSH);
			deleteItem.setText(getMessage(Delete));
			deleteItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					entry.model.removeOutput(entry);
					remove(entry);
				}
			});
		}

		return popMenu;
	}

	/**
	 * must be called explicitly
	 */
	public void refresh() {
		viewer.refresh();
	}

	/**
	 * must be called explicitly
	 */
	public void refresh(OutputType type) {
		boolean refreshed = false;
		TreeItem[] items = viewer.getTree().getItems();
		for (Item item : items) {
			if (type.equals(item.getData())) {
				viewer.refresh(type);
				viewer.setExpandedState(type, true);
				if (outputCounts.get(type) == 0) {
					item.dispose();
				}
				refreshed = true;
				break;
			}
		}
		if (!refreshed) {
			viewer.refresh();
		}
		viewer.setExpandedState(type, true);
	}

	private void updateCount(OutputType type, int count) {
		outputCounts.put(type, count);
	}

	private void updateLabel() {
		StringBuilder sb = new StringBuilder();
		sb.append(getMessage(Output_Message));
		for (int i = 0; i < OutputType.values().length; i++) {
			OutputType type = OutputType.values()[i];
			Integer count = outputCounts.get(type);
			if (count == null) {
				count = 0;
			}
			sb.append(' ');
			sb.append(count);
			sb.append(' ');
			sb.append(getMessage(type.toString()));
			if (i != OutputType.values().length - 1) {
				sb.append(',');
			}
		}
		outputLabel.setText(sb.toString());
	}

	private class OutputLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0) {
				if (element instanceof OutputType) {
					return getColumnImage((OutputType) element);
				} else {
					return getColumnImage(((OutputEntry) element).outputType);
				}
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof OutputType) {
				if (columnIndex == 0) {
					return getMessage(element.toString());
				}
			} else {
				OutputEntry entry = (OutputEntry) element;
				switch (columnIndex) {
				case 0:
					return entry.description;
				case 1:
					BaseModel model = entry.location;
					if (model instanceof VocabularyModel) {
						VocabularyModel vocabularyModel = (VocabularyModel) model;
						return getMessage(Vocabulary_Output, vocabularyModel.getVocabulary().getInfo().getId());
					} else if (model instanceof MetadataModel) {
						MetadataModel metadataModel = (MetadataModel) model;
						return getMessage(Metadata_Output, metadataModel.getRegistry().getInfo().getId());
					} else {

						PolicyModel policyModel = (PolicyModel) model;
						return getMessage(Policy_Output, policyModel.getPolicy().getInfo().getId());
					}
				case 2:
					//TODO
					if (entry.messageType != null) {
						return entry.messageType.toString();
					}
				}
			}

			return "";
		}

		private Image getColumnImage(OutputType type) {
			switch (type) {
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
			if (!(parentElement instanceof OutputType)) {
				return new Object[0];
			}

			OutputType type = (OutputType) parentElement;
			List<OutputEntry> list = model.getOutput(type);
			updateCount(type, list.size());
			updateLabel();
			return list.toArray();
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof OutputType;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			EditorModel model = (EditorModel) inputElement;
			List<OutputType> types = new ArrayList<>(OutputType.values().length);

			for (OutputType type : OutputType.values()) {
				int count = model.countOutput(type);
				if (count > 0) {
					types.add(type);
				}
				updateCount(type, count);
			}
			updateLabel();

			return types.toArray();

		}
	}

	public void refresh(BaseModel model) {
		List<OutputEntry> list = new ArrayList<>();
		model.getOutput(list);
		for (OutputEntry entry : list) {
			viewer.refresh(entry);
		}
	}

	public void remove(OutputEntry entry) {
		Tree tree = viewer.getTree();
		for (TreeItem item : tree.getItems()) {
			if (item.getData().equals(entry.outputType)) {
				for (TreeItem entryItem : item.getItems()) {
					if (entryItem.getData().equals(entry)) {
						entryItem.dispose();
						if (item.getItemCount() == 0) {
							item.dispose();
						}
						updateCount(entry.outputType, outputCounts.get(entry.outputType) - 1);
						updateLabel();
						return;
					}
				}
			}
		}

	}
}
