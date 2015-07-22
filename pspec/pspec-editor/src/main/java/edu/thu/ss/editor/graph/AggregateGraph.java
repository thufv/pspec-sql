package edu.thu.ss.editor.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.visual.AggregateItem;
import prefuse.visual.AggregateTable;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

public class AggregateGraph extends Display {

	public static final String GRAPH = "graph";
	public static final String NODES = "graph.nodes";
	public static final String EDGES = "graph.edges";
	public static final String AGGR = "aggregates";

	private Visualization visualization;
	private Graph graph;
	private VisualGraph visualGraph;
	private AggregateTable aggregateTable;

	private Set<Set<Integer>> aggregateGroups = new HashSet<>();

	public AggregateGraph(Visualization visualization) {
		super(visualization);
		this.visualization = visualization;
	}

	public void run() {
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
		visualGraph = visualization.addGraph("graph", graph);

		visualization.setInteractive(EDGES, null, false);
		visualization.setValue(NODES, null, VisualItem.SHAPE, new Integer(Constants.SHAPE_ELLIPSE));

		aggregateTable = visualization.addAggregates(AGGR);
		aggregateTable.addColumn(VisualItem.POLYGON, float[].class);
		aggregateTable.addColumn("id", int.class);
		
		
	}

	public void initDataGroups(List<ExpandedRule> rules) {
		graph = new Graph();
		graph.addColumn("id", java.lang.String.class);
		graph.addColumn("name", java.lang.String.class);
		graph.addColumn("info", java.lang.String.class);
		for (int i = 0; i < rules.size(); i++) {
			Node node = graph.addNode();
			node.set("id", i);
			node.set("name", rules.get(i).getRuleId());
			node.set("info", rules.get(i).toString());
		}
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