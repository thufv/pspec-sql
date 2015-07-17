package edu.thu.ss.editor.view;

import java.awt.Frame;
import java.awt.Panel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

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
import edu.thu.ss.editor.model.PolicyModel;
import edu.thu.ss.editor.util.EditorUtil;
import edu.thu.ss.spec.lang.pojo.Policy;

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

		this.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle rec = GraphView.this.getClientArea();
				if (!initialize) {
					display.setSize(rec.width, rec.height);
					initialize = true;
				}
			}
		});
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

}
