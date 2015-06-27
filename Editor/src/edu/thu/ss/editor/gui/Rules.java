package edu.thu.ss.editor.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.layout.FillLayout;

public class Rules extends Composite {

	protected Shell shell;
	private Button btn_add;
	private Button btn_del;
	private Button btn_edit;
	private Label lbl_title;
	private Composite composite;
	private ExpandBar expandBar;
	private Label lbl_empty;
	private Label lbl_ImgPoint;
	private Label lbl_cut;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public Rules(Shell shell_, Composite parent, int style) {
		super(parent, SWT.NONE);
		shell = shell_;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(6, false));
		
		lbl_empty = new Label(this, SWT.NONE);
		lbl_empty.setText("        ");
		lbl_empty.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_empty.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		lbl_empty.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		lbl_title = new Label(this, SWT.NONE);
		lbl_title.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_title.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_title.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		lbl_title.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		lbl_title.setText("Rules Edit");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		btn_add = new Button(this, SWT.NONE);
		btn_add.setImage(SWTResourceManager.getImage("img/add.png"));
		btn_add.setSize(30, 30);
		btn_add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new RuleInfo(shell).open();
			}
		});

		btn_del = new Button(this, SWT.NONE);
		btn_del.setImage(SWTResourceManager.getImage("img/delete.png"));
		btn_del.setSize(30, 30);
		btn_del.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});

		btn_edit = new Button(this, SWT.NONE);
		btn_edit.setImage(SWTResourceManager.getImage("img/edit.png"));
		btn_edit.setSize(30, 30);
		btn_edit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				new RuleEdit(shell).open();
			}
		});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		composite = new Composite(this, SWT.BORDER);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1);
		gd_composite.widthHint = screenSize.width * 2 / 3;
		gd_composite.heightHint = screenSize.height / 2;
		composite.setLayoutData(gd_composite);

		expandBar = new ExpandBar(composite, SWT.V_SCROLL);
		expandBar.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.BOLD));
		expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		//****************************************************************
		creatRuleOverview(false);
		creatRuleOverview(true);

	}
	
	private void creatRuleOverview(boolean reatricType){
		ExpandItem item = new ExpandItem(expandBar, SWT.NONE);
		item.setImage(SWTResourceManager.getImage("img/rule.png"));
		item.setExpanded(true);
		item.setText("Rule1");
		item.setHeight(250);
		
		Composite composite_info = new Composite(expandBar, SWT.NONE);
		item.setControl(composite_info);
		composite_info.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		composite_info.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_info.setLayout(new GridLayout(7, false));

		Label lbl_UserRef = new Label(composite_info, SWT.NONE);
		lbl_UserRef.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lbl_UserRef.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_UserRef.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.BOLD));
		lbl_UserRef.setText("User reference:");

		Label label_empty = new Label(composite_info, SWT.NONE);
		label_empty.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_empty.setText("    ");

		//user ref
		for(int i = 0; i < 2; i ++){
			lbl_ImgPoint = new Label(composite_info, SWT.NONE);
			lbl_ImgPoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_ImgPoint.setImage(SWTResourceManager.getImage("img\\point.png"));

			Label lbl_UserRefContent = new Label(composite_info, SWT.NONE);
			lbl_UserRefContent.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
			lbl_UserRefContent.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			lbl_UserRefContent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
			lbl_UserRefContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_UserRefContent.setText("analyst1  (  analyst11 , analyst12 , analyst13  )");
			new Label(composite_info, SWT.NONE);
		}

		Label lbl_DataRef = new Label(composite_info, SWT.NONE);
		lbl_DataRef.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
		lbl_DataRef.setText("Data reference(association):");
		lbl_DataRef.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.BOLD));
		lbl_DataRef.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		//data ref
		for(int i = 0; i < 2; i ++){
			new Label(composite_info, SWT.NONE);
			lbl_ImgPoint = new Label(composite_info, SWT.NONE);
			lbl_ImgPoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			lbl_ImgPoint.setImage(SWTResourceManager.getImage("img\\point.png"));

			Label lbl_DataRefContent = new Label(composite_info, SWT.NONE);
			lbl_DataRefContent.setText("city  (  city1 , city2  )  ");
			lbl_DataRefContent.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			lbl_DataRefContent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
			lbl_DataRefContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

			lbl_cut = new Label(composite_info, SWT.NONE);
			lbl_cut.setText(" ");
			lbl_cut.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			lbl_cut.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
			lbl_cut.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));

			Label lbl_Action = new Label(composite_info, SWT.NONE);
			lbl_Action.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
			lbl_Action.setText("projection");
			lbl_Action.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			lbl_Action.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
			lbl_Action.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			
		}
		
		//Forbid
		if(reatricType){
			Label lblRestrictionforbid = new Label(composite_info, SWT.NONE);
			lblRestrictionforbid.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1));
			lblRestrictionforbid.setText("Forbid");
			lblRestrictionforbid.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.BOLD));
			lblRestrictionforbid.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		}else{
			Label lblRestrictionassociation = new Label(composite_info, SWT.NONE);
			lblRestrictionassociation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 1));
			lblRestrictionassociation.setText("Restriction:");
			lblRestrictionassociation.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.BOLD));
			lblRestrictionassociation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			new Label(composite_info, SWT.NONE);
			//Restriction
			for(int i = 0; i < 2; i ++){
				lbl_ImgPoint = new Label(composite_info, SWT.NONE);
				lbl_ImgPoint.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				lbl_ImgPoint.setImage(SWTResourceManager.getImage("img\\point.png"));

				Label lbl_Desens = new Label(composite_info, SWT.NONE);
				lbl_Desens.setText("desensitize\r\n");
				lbl_Desens.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				lbl_Desens.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
				lbl_Desens.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

				lbl_cut = new Label(composite_info, SWT.NONE);
				lbl_cut.setText(" ");
				lbl_cut.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				lbl_cut.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
				lbl_cut.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));

				Label lbl_refData = new Label(composite_info, SWT.NONE);
				lbl_refData.setText("city");
				lbl_refData.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				lbl_refData.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
				lbl_refData.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

				lbl_cut = new Label(composite_info, SWT.NONE);
				lbl_cut.setText(" ");
				lbl_cut.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				lbl_cut.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
				lbl_cut.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
				
				Label lbl_operat = new Label(composite_info, SWT.NONE);
				lbl_operat.setText("sum,avg\r\n");
				lbl_operat.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				lbl_operat.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 7, SWT.NORMAL));
				lbl_operat.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				new Label(composite_info, SWT.NONE);
			}
		}
	}
}