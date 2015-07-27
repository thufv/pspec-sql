package edu.thu.ss.editor.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.controls.PanControl;
import prefuse.controls.ToolTipControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.visual.AggregateItem;
import prefuse.visual.AggregateTable;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

public class AggregateGraph extends Display {

	public static final String GRAPH = "graph";
	public static final String NODES = "graph.nodes";
	public static final String EDGES = "graph.edges";
	public static final String AGGR = "aggregates";

	private VisualGraph visualGraph;
	private AggregateTable aggregateTable;

	private Set<Set<Integer>> aggregateGroups = new HashSet<>();

	public AggregateGraph(Visualization visualization, VisualGraph visualGraph) {
		super(visualization);
		this.visualGraph = visualGraph;

		visualization.setInteractive(EDGES, null, false);
		visualization.setValue(NODES, null, VisualItem.SHAPE, new Integer(Constants.SHAPE_ELLIPSE));

		aggregateTable = visualization.addAggregates(AGGR);
		aggregateTable.addColumn(VisualItem.POLYGON, float[].class);
		aggregateTable.addColumn("id", int.class);
		
		setHighQuality(true);
		addControlListener(new PanControl());
		addControlListener(new ZoomControl());
		addControlListener(new WheelZoomControl());
		addControlListener(new ToolTipControl("info"));
		addControlListener(new AggregateDragControl());
	}

	public void setVisualGraph(VisualGraph visualGraph) {
		this.visualGraph = visualGraph;
	}
	
	public void addAggregateGroups(Set<Integer> group) {
		if (aggregateGroups.contains(group)) {
			return;
		}

		int groupIndex = aggregateGroups.size();
		aggregateGroups.add(group);
		Iterator nodes = visualGraph.nodes();

		AggregateItem aggregateItem = (AggregateItem) aggregateTable.addItem();
		aggregateItem.setInt("id", groupIndex);

		for (int nodeIndex = 0; nodeIndex < visualGraph.getNodeCount(); nodeIndex++) {
			VisualItem item = (VisualItem) nodes.next();
			String temp = item.getString("id");
			if (group.contains(new Integer(item.getString("id")))) {
				aggregateItem.addItem((VisualItem) item);
			}

		}
	}

	public void removeAggregateGroups() {
		if (aggregateTable == null) {
			return;
		}
		aggregateTable.clear();
		aggregateGroups.clear();
	}

	public void test() {
		JFrame frame = demo();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public JFrame demo() {
		JFrame frame = new JFrame("p r e f u s e  |  a g g r e g a t e d");
		frame.getContentPane().add(this);
		frame.pack();
		return frame;
	}

} // end of class AggregateGraph