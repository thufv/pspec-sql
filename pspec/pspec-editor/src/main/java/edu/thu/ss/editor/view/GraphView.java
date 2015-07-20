package edu.thu.ss.editor.view;

import java.awt.Frame;
import java.awt.Panel;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import edu.thu.ss.editor.graph.AdjacencyList;
import edu.thu.ss.editor.graph.Edge;
import edu.thu.ss.editor.graph.EdmondsChuLiu;
import edu.thu.ss.editor.graph.Node;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.analyzer.RuleExpander;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataAssociation;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.util.PSpecUtil;
import prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class GraphView extends EditorView<PolicyModel, Policy> {

	private Graph graph;
	private Visualization visualization;
	private prefuse.Display display;

	private boolean initialize = false;

	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public GraphView(final Shell shell, Composite parent, PolicyModel model, OutputView outputView) {
		super(shell, parent, model, outputView);
		this.shell = shell;
		this.model = model;

		this.setBackground(EditorUtil.getDefaultBackground());
		this.setLayout(new FillLayout());

		initializeGraph();
		//setScopeRelation();

		this.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle rec = GraphView.this.getClientArea();
				if (EditorUtil.isWindows() || !initialize) {
					display.setSize(rec.width, rec.height);
					initialize = true;
				}
			}
		});
	}

	@Override
	public void refresh() {
		setScopeRelation();
	}

	private void initializeGraph() {
		try {
			graph = new GraphMLReader().readGraph("/socialnet.xml");
		} catch (DataIOException e) {
			e.printStackTrace();
			System.err.println("Error loading graph. Exiting...");
			System.exit(1);
		}

		visualization = new Visualization();
		visualization.add("graph", graph);

		//set renderer
		LabelRenderer labelRenderer = new LabelRenderer("name");
		labelRenderer.setRoundedCorner(8, 8);

		visualization.setRendererFactory(new DefaultRendererFactory(labelRenderer));

		//set color
		int[] palette = new int[] { ColorLib.rgb(255, 180, 180), ColorLib.rgb(190, 190, 255) };
		DataColorAction fill = new DataColorAction("graph.nodes", "gender", Constants.NOMINAL,
				VisualItem.FILLCOLOR, palette);

		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));

		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));

		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);

		//set layout
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());

		visualization.putAction("color", color);
		visualization.putAction("layout", layout);

		display = new prefuse.Display(visualization);
		//display.setSize(800, 600); // set display size
		display.addControlListener(new DragControl()); // drag items around
		display.addControlListener(new PanControl()); // pan with background left-drag
		display.addControlListener(new ZoomControl()); // zoom with vertical right-drag

		Composite composite = new Composite(this, SWT.NO_BACKGROUND | SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(composite);
		Panel panel = new Panel();
		frame.add(panel);
		panel.add(display);

		visualization.run("color");
		visualization.run("layout");
	}

	private void setScopeRelation() {
		Policy policy = model.getPolicy();
		RuleExpander expander = new RuleExpander(null);
		expander.analyze(policy);
		List<ExpandedRule> rules = policy.getExpandedRules();

		visualization = new Visualization();
		graph = new Graph(true);
		graph.addColumn("id", int.class);
		graph.addColumn("name", java.lang.String.class);
		for (int i = 0; i < rules.size(); i++) {
			prefuse.data.Node node = graph.addNode();
			node.set("id", 0);
			node.set("name", rules.get(i).getRuleId());
			graph.addEdge(0, i);
		}

		if (rules.size() != 0) {
			AdjacencyList<Integer> list = new AdjacencyList<Integer>();
			Node<Integer> root = new Node<Integer>(rules.size());
			for (int i = 0; i < rules.size(); i++) {
				ExpandedRule rule1 = rules.get(i);
				Node<Integer> node1 = new Node<Integer>(i);
				list.addEdge(root, node1, (double) 1.0);
				for (int j = 0; j < rules.size(); j++) {
					if (i == j) {
						continue;
					}
					ExpandedRule rule2 = rules.get(j);
					Node<Integer> node2 = new Node<Integer>(j);
					if (checkRuleScope(rule1, rule2)) {
						list.addEdge(node1, node2, (double) 1.0);
					}
				}
			}

			EdmondsChuLiu<Integer> minBranchGenerator = new EdmondsChuLiu<Integer>();
			AdjacencyList<Integer> result = minBranchGenerator.getMinBranching(root, list);

			Collection<Edge<Integer>> allEdges = result.getAllEdges();
			for (Edge<Integer> edge : allEdges) {
				int source = edge.getFrom().getName();
				int target = edge.getTo().getName();
				if (source < rules.size() && target < rules.size()) {
					graph.addEdge(source, target);
				}
			}
		}

		visualization.add("graph", graph);

		//set renderer
		LabelRenderer labelRenderer = new LabelRenderer("name");
		labelRenderer.setRoundedCorner(8, 8);

		visualization.setRendererFactory(new DefaultRendererFactory(labelRenderer));

		//set color
		int[] palette = new int[] { ColorLib.rgb(255, 180, 180), ColorLib.rgb(190, 190, 255) };
		DataColorAction fill = new DataColorAction("graph.nodes", "id", Constants.NOMINAL,
				VisualItem.FILLCOLOR, palette);

		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));

		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));

		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);

		//set layout
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());

		visualization.putAction("color", color);
		visualization.putAction("layout", layout);

		display = new prefuse.Display(visualization);
		//display.setSize(800, 600); // set display size
		display.addControlListener(new DragControl()); // drag items around
		display.addControlListener(new PanControl()); // pan with background left-drag
		display.addControlListener(new ZoomControl()); // zoom with vertical right-drag

		Composite composite = new Composite(this, SWT.NO_BACKGROUND | SWT.EMBEDDED);
		Frame frame = SWT_AWT.new_Frame(composite);
		Panel panel = new Panel();
		frame.add(panel);
		panel.add(display);

		visualization.run("color");
		visualization.run("layout");
	}

	private boolean checkRuleScope(ExpandedRule target, ExpandedRule rule) {
		if (target.isSingle()) {
			return checkSingle(rule, target);
		} else {
			return checkAssociation(rule, target);
		}
	}

	private boolean checkSingle(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isAssociation()) {
			return false;
		}
		// pre-test, to filter out result early.
		DataRef ref1 = rule1.getDataRef();
		DataRef ref2 = rule2.getDataRef();

		Action action1 = ref1.getAction();
		Action action2 = ref2.getAction();
		if (!action1.ancestorOf(action2)) {
			return false;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!user1.containsAll(user2)) {
			return false;
		}

		Set<DataCategory> data1 = ref1.getMaterialized();
		Set<DataCategory> data2 = ref2.getMaterialized();
		if (!data1.containsAll(data2)) {
			return false;
		}

		return true;
	}

	private boolean checkAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		if (rule1.isSingle()) {
			return checkSingleAssociation(rule1, rule2);
		} else {
			return checkBothAssociation(rule1, rule2);
		}
	}

	private boolean checkSingleAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();

		if (!PSpecUtil.contains(user1, user2)) {
			return false;
		}

		DataRef ref1 = rule1.getDataRef();
		DataAssociation assoc2 = rule2.getAssociation();

		boolean match = false;
		List<DataRef> dataRefs = assoc2.getDataRefs();
		for (int i = 0; i < dataRefs.size(); i++) {
			DataRef ref2 = dataRefs.get(i);
			if (ref1.getAction().ancestorOf(ref2.getAction())
					&& PSpecUtil.contains(ref1.getMaterialized(), ref2.getMaterialized())) {
				match = true;
			}
		}
		if (!match) {
			return false;
		}
		return true;
	}

	private boolean checkBothAssociation(ExpandedRule rule1, ExpandedRule rule2) {
		DataAssociation assoc1 = rule1.getAssociation();
		DataAssociation assoc2 = rule2.getAssociation();

		if (assoc1.getDimension() > assoc2.getDimension()) {
			return false;
		}

		Set<UserCategory> user1 = rule1.getUsers();
		Set<UserCategory> user2 = rule2.getUsers();
		if (!PSpecUtil.contains(user1, user2)) {
			return false;
		}

		List<DataRef> dataRefs1 = assoc1.getDataRefs();
		List<DataRef> dataRefs2 = assoc2.getDataRefs();

		for (int i = 0; i < dataRefs1.size(); i++) {
			boolean match = false;
			DataRef ref1 = dataRefs1.get(i);
			for (int j = 0; j < dataRefs2.size(); j++) {
				DataRef ref2 = dataRefs2.get(j);
				if (ref1.getAction().ancestorOf(ref2.getAction())
						&& PSpecUtil.contains(ref1.getMaterialized(), ref2.getMaterialized())) {
					match = true;
				}
			}
			if (!match) {
				return false;
			}
		}

		return true;
	}

}
