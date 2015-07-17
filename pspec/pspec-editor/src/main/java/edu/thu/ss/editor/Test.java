package edu.thu.ss.editor;

/******************************************************************************
 * All Right Reserved. 
 * Copyright (c) 1998, 2004 Jackwind Li Guojie
 * 
 * Created on 2004-4-9 14:11:34 by JACK
 * $Id$
 * 
 *****************************************************************************/

import java.awt.Container;
import java.awt.Frame;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.plaf.PanelUI;
import javax.swing.text.LabelView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import prefuse.Constants;
import prefuse.Display;
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

public class Test {

	public static void main(String[] args) {

		// load the socialnet.xml file. it is assumed that the file can be
		// found at the root of the java classpath
		org.eclipse.swt.widgets.Display display1 = org.eclipse.swt.widgets.Display.getDefault();
		Shell shell = new Shell(display1);

		Graph graph = null;
		try {
			graph = new GraphMLReader().readGraph("/socialnet.xml");
		} catch (DataIOException e) {
			e.printStackTrace();
			System.err.println("Error loading graph. Exiting...");
			System.exit(1);
		}

		// -- 2. the visualization --------------------------------------------

		// add the graph to the visualization as the data group "graph"
		// nodes and edges are accessible as "graph.nodes" and "graph.edges"
		Visualization vis = new Visualization();
		vis.add("graph", graph);
		vis.setInteractive("graph.edges", null, false);

		// -- 3. the renderers and renderer factory ---------------------------

		// draw the "name" label for NodeItems
		LabelRenderer r = new LabelRenderer("name");
		r.setRoundedCorner(8, 8); // round the corners

		// create a new default renderer factory
		// return our name label renderer as the default for all non-EdgeItems
		// includes straight line edges for EdgeItems by default
		vis.setRendererFactory(new DefaultRendererFactory(r));

		// -- 4. the processing actions ---------------------------------------

		// create our nominal color palette
		// pink for females, baby blue for males
		int[] palette = new int[] { ColorLib.rgb(255, 180, 180), ColorLib.rgb(190, 190, 255) };
		// map nominal data values to colors using our provided palette
		DataColorAction fill = new DataColorAction("graph.nodes", "gender", Constants.NOMINAL,
				VisualItem.FILLCOLOR, palette);
		// use black for node text
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));
		// use light grey for edges
		ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));

		// create an action list containing all color assignments
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);

		// create an action list with an animated layout
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(new ForceDirectedLayout("graph"));
		layout.add(new RepaintAction());

		// add the actions to the visualization
		vis.putAction("color", color);
		vis.putAction("layout", layout);

		// -- 5. the display and interactive controls -------------------------

		Display display = new Display(vis);
		display.setSize(800, 600); // set display size
		// drag individual items around
		display.addControlListener(new DragControl());
		// pan with left-click drag on background
		display.addControlListener(new PanControl());
		// zoom with right-click drag
		display.addControlListener(new ZoomControl());

		// -- 6. launch the visualization -------------------------------------

		display.setVisible(true);
		//获得容器  
		vis.run("color");
		vis.run("layout");
		shell.setSize(new Point(800, 600));
		shell.setLayout(new FillLayout());
		shell.setText("Prefuse in SWT");
		// 新建swt组件  
		Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
		// 注入组件，并返回为frame  
		Frame frame = SWT_AWT.new_Frame(composite);
		// 加载容器  
		Panel panel = new Panel();
		frame.add(panel);
		panel.add(display);
		// 打开shell  
		shell.open();

		while (!shell.isDisposed()) {
			if (!display1.readAndDispatch())
				display1.sleep();
		}
	}

}
