package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.ApproximateConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.EnhancedStrongConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.NormalConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.ScopeRelation;
import static edu.thu.ss.editor.util.MessagesUtil.StrongConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import java.awt.Frame;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import edu.thu.ss.editor.graph.AggregateDragControl;
import edu.thu.ss.editor.graph.AggregateGraph;
import edu.thu.ss.editor.graph.AggregateLayout;
import edu.thu.ss.editor.model.OutputEntry;
import edu.thu.ss.editor.model.OutputEntry.MessageType;
import edu.thu.ss.editor.model.OutputEntry.OutputType;
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.analyzer.RuleExpander;
import edu.thu.ss.spec.lang.analyzer.redundancy.ScopeAnalyzer;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;
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
import prefuse.controls.ToolTipControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.PolygonRenderer;
import prefuse.render.Renderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;

public class GraphView extends EditorView<PolicyModel, Policy> {

	private Graph graph;
	private Visualization visualization;
	private prefuse.Display display;

	private Panel panel;
	private Button scopeRelation;
	private Button normalConsistency;
	private Button approximateConsistency;
	private Button strongConsistency;
	private Button enhancedStrongConsistency;

	private boolean initialize = false;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public GraphView(final Shell shell, Composite parent, PolicyModel model, OutputView outputView) {
		super(shell, parent, model, outputView);
		this.shell = shell;
		this.model = model;

		this.setBackground(EditorUtil.getDefaultBackground());
		this.setLayout(new GridLayout(1, false));
		initializeGraph(this);

		SashForm contentForm = new SashForm(this, SWT.NONE);
		GridData contentData = new GridData();
		contentData.horizontalAlignment = SWT.FILL;
		contentData.verticalAlignment = SWT.FILL;
		contentData.grabExcessVerticalSpace = true;
		contentData.grabExcessHorizontalSpace = true;

		contentForm.setLayoutData(contentData);
		Composite composite = new Composite(contentForm, SWT.BORDER | SWT.NO_BACKGROUND | SWT.EMBEDDED);

		Frame frame = SWT_AWT.new_Frame(composite);
		panel = new Panel();
		frame.add(panel);
		showRulesRelation();

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
		panel.removeAll();
		showRulesRelation();
		Rectangle rec = GraphView.this.getClientArea();
		display.setSize(rec.width, rec.height);
	}

	private void initializeGraph(Composite content) {
		Composite dataComposite = newComposite(content, 5);
		scopeRelation = EditorUtil.newCheck(dataComposite, getMessage(ScopeRelation));
		normalConsistency = EditorUtil.newCheck(dataComposite, getMessage(NormalConsistency));
		approximateConsistency = EditorUtil.newCheck(dataComposite, getMessage(ApproximateConsistency));
		strongConsistency = EditorUtil.newCheck(dataComposite, getMessage(StrongConsistency));
		enhancedStrongConsistency = EditorUtil.newCheck(dataComposite,
				getMessage(EnhancedStrongConsistency));

		scopeRelation.setSelection(true);

		scopeRelation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scopeRelation.setSelection(true);
			}
		});

		normalConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		approximateConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		strongConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		enhancedStrongConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

	}

	private Composite newComposite(Composite parent, int column) {
		Composite composite = EditorUtil.newComposite(parent);
		GridLayout layout = EditorUtil.newNoMarginGridLayout(column, false);
		composite.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		composite.setLayoutData(data);
		return composite;
	}

	private void showRulesRelation() {
		Policy policy = model.getPolicy();
		RuleExpander expander = new RuleExpander(null);
		expander.analyze(policy);
		List<ExpandedRule> rules = policy.getExpandedRules();

		//initialize data groups
		visualization = new Visualization();
		graph = new Graph(true);
		graph.addColumn("id", int.class);
		graph.addColumn("name", java.lang.String.class);
		graph.addColumn("info", java.lang.String.class);
		for (int i = 0; i < rules.size(); i++) {
			prefuse.data.Node node = graph.addNode();
			node.set("id", i);
			node.set("name", rules.get(i).getRuleId());
			node.set("info", rules.get(i).toString().replaceAll("\n\t", " "));
		}

		generateScopeGraph(policy);
		boolean consistency = generateConsistencyGraph();

		// set renderer
		LabelRenderer labelRenderer = new LabelRenderer("name");
		labelRenderer.setRoundedCorner(8, 8);
		DefaultRendererFactory drf = new DefaultRendererFactory(labelRenderer);

		EdgeRenderer edgeRenderer = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_CURVE,
				prefuse.Constants.EDGE_ARROW_FORWARD);
		edgeRenderer.setArrowHeadSize(8, 8);
		drf.setDefaultEdgeRenderer(edgeRenderer);

		if (consistency) {
			// draw aggregates as polygons with curved edges
			Renderer polyR = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
			((PolygonRenderer) polyR).setCurveSlack(0.15f);
			drf.add("ingroup('aggregates')", polyR);
		}

		visualization.setRendererFactory(drf);

		// set color
		ActionList color = new ActionList();

		if (consistency) {
			int[] palette = new int[] { ColorLib.rgba(255, 200, 200, 150),
					ColorLib.rgba(200, 255, 200, 150), ColorLib.rgba(200, 200, 255, 150),
					ColorLib.rgba(200, 150, 200, 255), ColorLib.rgba(200, 150, 255, 200),
					ColorLib.rgba(200, 255, 150, 200), ColorLib.rgba(255, 150, 200, 200)};
			ColorAction aFill = new DataColorAction("aggregates", "id", Constants.NOMINAL,
					VisualItem.FILLCOLOR, palette);
			color.add(aFill);

			ColorAction aStroke = new ColorAction("aggregates", VisualItem.STROKECOLOR,
					ColorLib.gray(200));
			aStroke.add("_hover", ColorLib.rgb(255, 100, 100));
			color.add(aStroke);
		}

		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
		ColorAction arrows = new ColorAction("graph.edges", VisualItem.FILLCOLOR, ColorLib.gray(200));
		color.add(text);
		color.add(edges);
		color.add(arrows);

		ColorAction nFill = new ColorAction("graph.nodes", VisualItem.FILLCOLOR);
		nFill.setDefaultColor(ColorLib.gray(255));
		nFill.add("_hover", ColorLib.gray(200));

		ColorAction nStroke = new ColorAction("graph.nodes", VisualItem.STROKECOLOR);
		nStroke.setDefaultColor(ColorLib.gray(100));
		nStroke.add("_hover", ColorLib.gray(50));
		color.add(nFill);
		color.add(nStroke);

		// set layout
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(color);
		layout.add(new RepaintAction());
		layout.add(new ForceDirectedLayout("graph"));
		if (consistency) {
			layout.add(new AggregateLayout("aggregates"));
		}

		visualization.putAction("layout", layout);

		//set control
		display.addControlListener(new PanControl()); // pan with background
		display.addControlListener(new ZoomControl()); // zoom with vertical
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new ToolTipControl("info"));
		if (consistency) {
			display.setHighQuality(true);
			display.addControlListener(new AggregateDragControl());
		} else {
			display.addControlListener(new DragControl()); // drag items around
		}

		panel.add(display);
		visualization.run("layout");
	}

	private void generateScopeGraph(Policy policy) {
		ScopeAnalyzer analyzer = new ScopeAnalyzer();
		analyzer.analyze(policy);
		List<ExpandedRule> rules = policy.getExpandedRules();
		for (int i = 0; i < rules.size(); i++) {
			String source = rules.get(i).getRuleId();
			for (int j = 0; j < rules.size(); j++) {
				if (i == j) {
					continue;
				}
				String target = rules.get(j).getRuleId();
				if (analyzer.containsEdge(source, target)) {
					graph.addEdge(i, j);
				}
			}
		}
		display = new prefuse.Display(visualization);
	}

	private boolean generateConsistencyGraph() {
		AggregateGraph aggregateGraph = new AggregateGraph(visualization);
		aggregateGraph.setGraph(graph);
		boolean consistency = false;
		if (normalConsistency.getSelection()) {
			if (generateConsistencyGraph(aggregateGraph, MessageType.Normal_Consistency)) {
				consistency = true;
			}
		}
		if (approximateConsistency.getSelection()) {
			if (generateConsistencyGraph(aggregateGraph, MessageType.Approximate_Consistency)) {
				consistency = true;
			}
		}
		if (strongConsistency.getSelection()) {
			if (generateConsistencyGraph(aggregateGraph, MessageType.Strong_Consistency)) {
				consistency = true;
			}
		}
		if (enhancedStrongConsistency.getSelection()) {
			if (generateConsistencyGraph(aggregateGraph, MessageType.Enhanced_Strong_Consistency)) {
				consistency = true;
			}
		}
		return consistency;
	}

	private boolean generateConsistencyGraph(AggregateGraph aggregateGraph, MessageType messageType) {
		Policy policy = model.getPolicy();
		RuleExpander expander = new RuleExpander(null);
		expander.analyze(policy);
		List<ExpandedRule> erules = policy.getExpandedRules();

		List<OutputEntry> list = new ArrayList<>();
		model.getOutput(OutputType.analysis, list);
		if (list.size() == 0) {
			return false;
		}
		for (OutputEntry output : list) {
			if (output.messageType.equals(messageType)) {
				ExpandedRule[] rules = (ExpandedRule[]) output.data;
				Set<Integer> group = new HashSet<>();
				for (int i = 0; i < rules.length; i++) {
					for (int j = 0; j < erules.size(); j++) {
						if (erules.get(j).getRuleId().equals(rules[i].getRuleId())) {
							group.add(j);
							break;
						}
					}
				}
				aggregateGraph.addInconsistencyGroups(group);
			}
		}
		display = aggregateGraph;
		return true;
	}
}
