package edu.thu.ss.editor.view;

import static edu.thu.ss.editor.util.MessagesUtil.ApproximateConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.EnhancedStrongConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.NormalConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.Redundancy;
import static edu.thu.ss.editor.util.MessagesUtil.StrongConsistency;
import static edu.thu.ss.editor.util.MessagesUtil.getMessage;

import java.awt.Frame;
import java.awt.Panel;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

import edu.thu.ss.editor.PSpecEditor;
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
import prefuse.util.GraphicsLib;
import prefuse.util.display.DisplayLib;
import prefuse.visual.VisualItem;

public class GraphView extends EditorView<PolicyModel, Policy> {

	private Graph graph;
	private Visualization visualization;
	private prefuse.Display display;

	private Panel panel;
	private Button redundancy;
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
					display.setSize(rec.width, rec.height * 6 / 7);
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
		display.setSize(rec.width, rec.height * 6 / 7);
	}

	private void initializeGraph(Composite content) {
		Composite typeComposite = newRadioComposite(content);
		redundancy = EditorUtil.newRadio(typeComposite, getMessage(Redundancy));
		normalConsistency = EditorUtil.newRadio(typeComposite, getMessage(NormalConsistency));
		approximateConsistency = EditorUtil.newRadio(typeComposite, getMessage(ApproximateConsistency));
		strongConsistency = EditorUtil.newRadio(typeComposite, getMessage(StrongConsistency));
		enhancedStrongConsistency = EditorUtil.newRadio(typeComposite,
				getMessage(EnhancedStrongConsistency));

		redundancy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!redundancy.getSelection()) {
					return;
				}
				refresh();
			}
		});

		normalConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!normalConsistency.getSelection()) {
					return;
				}
				refresh();
			}
		});

		approximateConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!approximateConsistency.getSelection()) {
					return;
				}
				refresh();
			}
		});

		strongConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!strongConsistency.getSelection()) {
					return;
				}
				refresh();
			}
		});

		enhancedStrongConsistency.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!enhancedStrongConsistency.getSelection()) {
					return;
				}
				refresh();
			}
		});

	}

	private Composite newRadioComposite(Composite parent) {
		Composite composite = EditorUtil.newComposite(parent);
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		composite.setLayoutData(data);
		GridLayout layout = EditorUtil.newNoMarginGridLayout(2, false);
		composite.setLayout(layout);
		return composite;
	}

	private void showRulesRelation() {
		if (hasErrorOutput()) {
			return;
		}
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
			node.set("info", "<html>" + rules.get(i).toString().replaceAll("\n\t", "<br>") + "</html>");
		}

		generateScopeGraph(policy);
		boolean aggregate = generateAggregateGraph();

		// set renderer
		LabelRenderer labelRenderer = new LabelRenderer("name");
		labelRenderer.setRoundedCorner(15, 15);
		DefaultRendererFactory drf = new DefaultRendererFactory(labelRenderer);

		EdgeRenderer edgeRenderer = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_CURVE,
				prefuse.Constants.EDGE_ARROW_FORWARD);
		edgeRenderer.setArrowHeadSize(8, 8);
		drf.setDefaultEdgeRenderer(edgeRenderer);

		if (aggregate) {
			// draw aggregates as polygons with curved edges
			Renderer polyR = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
			((PolygonRenderer) polyR).setCurveSlack(0.15f);
			drf.add("ingroup('aggregates')", polyR);
		}

		visualization.setRendererFactory(drf);

		// set color
		ActionList color = new ActionList();

		if (aggregate) {
			int[] palette = new int[] { ColorLib.rgba(255, 200, 200, 150),
					ColorLib.rgba(200, 255, 200, 150), ColorLib.rgba(200, 200, 255, 150) };
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
		nFill.setDefaultColor(ColorLib.rgb(136, 206, 250));
		nFill.add("_hover", ColorLib.gray(200));

		ColorAction nStroke = new ColorAction("graph.nodes", VisualItem.STROKECOLOR);
		nStroke.setDefaultColor(ColorLib.rgb(135, 206, 235));
		nStroke.add("_hover", ColorLib.gray(50));
		color.add(nFill);
		color.add(nStroke);

		// set layout
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(color);
		layout.add(new RepaintAction());
		layout.add(new ForceDirectedLayout("graph", true, false));
		if (aggregate) {
			layout.add(new AggregateLayout("aggregates"));
		}

		visualization.putAction("layout", layout);

		//set control
		display.addControlListener(new PanControl()); // pan with background
		display.addControlListener(new ZoomControl()); // zoom with vertical
		display.addControlListener(new WheelZoomControl());
		display.addControlListener(new ToolTipControl("info"));
		if (aggregate) {
			display.setHighQuality(true);
			display.addControlListener(new AggregateDragControl());
		} else {
			display.addControlListener(new DragControl()); // drag items around
		}
		adjustDisplayZoom();
		panel.add(display);
		visualization.run("layout");
	}

	private void adjustDisplayZoom() {
		Rectangle2D bounds = visualization.getBounds(Visualization.ALL_ITEMS);
		GraphicsLib.expand(bounds, 50 + (int) (1 / display.getScale()));
		DisplayLib.fitViewToBounds(display, bounds, (long) 1);
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
					graph.addEdge(j, i);
				}
			}
		}
		display = new prefuse.Display(visualization);
	}

	private boolean generateAggregateGraph() {
		AggregateGraph aggregateGraph = new AggregateGraph(visualization);
		aggregateGraph.setGraph(graph);
		if (normalConsistency.getSelection()) {
			return generateConsistencyGraph(aggregateGraph, MessageType.Normal_Consistency);
		} else if (approximateConsistency.getSelection()) {
			return generateConsistencyGraph(aggregateGraph, MessageType.Approximate_Consistency);
		} else if (strongConsistency.getSelection()) {
			return generateConsistencyGraph(aggregateGraph, MessageType.Strong_Consistency);
		} else if (enhancedStrongConsistency.getSelection()) {
			return generateConsistencyGraph(aggregateGraph, MessageType.Enhanced_Strong_Consistency);
		} else if (redundancy.getSelection()) {
			return generateRedundancyGraph(aggregateGraph);
		}
		return false;
	}

	private boolean generateConsistencyGraph(AggregateGraph aggregateGraph, MessageType messageType) {
		Policy policy = model.getPolicy();
		RuleExpander expander = new RuleExpander(null);
		expander.analyze(policy);
		List<ExpandedRule> erules = policy.getExpandedRules();

		List<OutputEntry> list = getAnalysisOutput(messageType);
		if (list.size() == 0) {
			if (MessageType.Normal_Consistency.equals(messageType)) {
				PSpecEditor.getInstance().analyzeNormalConsistency(model);
			} else if (MessageType.Approximate_Consistency.equals(messageType)) {
				PSpecEditor.getInstance().analyzeApproximateConsistency(model);
			} else if (MessageType.Strong_Consistency.equals(messageType)) {
				PSpecEditor.getInstance().analyzeStrongConsistency(model);
			} else if (MessageType.Enhanced_Strong_Consistency.equals(messageType)) {
				PSpecEditor.getInstance().analyzeEnhancedStrongConsistency(model);
			}
			list = getAnalysisOutput(messageType);
			if (list.size() == 0) {
				return false;
			}
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
				aggregateGraph.addAggregateGroups(group);
			}
		}
		display = aggregateGraph;
		return true;
	}

	private boolean hasErrorOutput() {
		List<OutputEntry> list = new ArrayList<>();
		model.getOutput(OutputType.error, list);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	private List<OutputEntry> getAnalysisOutput(MessageType messageType) {
		List<OutputEntry> list = new ArrayList<>();
		model.getOutput(OutputType.analysis, list);
		Iterator<OutputEntry> it = list.iterator();
		while (it.hasNext()) {
			OutputEntry item = it.next();
			if (!item.messageType.equals(messageType)) {
				it.remove();
			}
		}
		return list;
	}

	private boolean generateRedundancyGraph(AggregateGraph aggregateGraph) {
		Policy policy = model.getPolicy();
		RuleExpander expander = new RuleExpander(null);
		expander.analyze(policy);
		List<ExpandedRule> erules = policy.getExpandedRules();

		MessageType messageType = MessageType.Redundancy;
		List<OutputEntry> list = getAnalysisOutput(messageType);
		if (list.size() == 0) {
			PSpecEditor.getInstance().analyzeRedundancy(model);
			list = getAnalysisOutput(messageType);
			if (list.size() == 0) {
				return false;
			}
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
				aggregateGraph.addAggregateGroups(group);
			}
		}
		return true;
	}
}
