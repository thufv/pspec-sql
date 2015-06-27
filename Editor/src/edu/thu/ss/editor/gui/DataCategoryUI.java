package edu.thu.ss.editor.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;
import edu.thu.ss.spec.lang.pojo.DataCategory;

public class DataCategoryUI extends Dialog {
	
	private int operationsCount = 1;
	
	protected Shell shell;
	private DataCategory dataCategory;
	private ScrolledComposite scrolledComposite;
	private Composite composite_Center;
	private Text text_CategoryID;
	private Text text_parentID;
	private Text text_shortDes;
	private Text text_longDes;
	private Label lbl_empty;
	private Label lbl_DesenOper;
	private Text text_DesenOper;
	private Text text_5;
	private Button btn_add;
	private Button btn_del;
	
	
	public DataCategoryUI(Shell parent , DataCategory dataCategory) {
		super(parent, SWT.NONE);
		this.dataCategory = dataCategory;
	}
	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setSize(screenSize.width/2, screenSize.height/2);
		shell.setLocation(screenSize.width/4, screenSize.height/4);
		shell.setText("DataCategoryInfo");
		shell.setLayout(new BorderLayout(0, 0));
		
		scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		composite_Center = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(composite_Center);
		scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		composite_Center.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_Center.setLayout(new GridLayout(5, false));
		
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		
		Label lbl_CategoryID = new Label(composite_Center, SWT.NONE);
		lbl_CategoryID.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_CategoryID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_CategoryID.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_CategoryID.setText("*Category ID:");
		
		text_CategoryID = new Text(composite_Center, SWT.BORDER);
		text_CategoryID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_Center, SWT.NONE);
		
		lbl_empty = new Label(composite_Center, SWT.NONE);
		lbl_empty.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_empty.setText("   ");
		new Label(composite_Center, SWT.NONE);
		
		Label lbl_parentID = new Label(composite_Center, SWT.NONE);
		lbl_parentID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_parentID.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_parentID.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_parentID.setText("Parent ID:");
		
		text_parentID = new Text(composite_Center, SWT.BORDER);
		text_parentID.setEnabled(false);
		text_parentID.setEditable(false);
		text_parentID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		if(dataCategory.getParentId() != null)
			text_parentID.setText(dataCategory.getParentId());
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		
		Label lbl_shortDes = new Label(composite_Center, SWT.NONE);
		lbl_shortDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_shortDes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_shortDes.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_shortDes.setText("Short Description:");
		
		text_shortDes = new Text(composite_Center, SWT.BORDER);
		text_shortDes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		
		Label lbl_longDes = new Label(composite_Center, SWT.NONE);
		lbl_longDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_longDes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_longDes.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lbl_longDes.setText("Long Description:");
		
		text_longDes = new Text(composite_Center, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text_longDes = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_longDes.heightHint = 135;
		text_longDes.setLayoutData(gd_text_longDes);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		new Label(composite_Center, SWT.NONE);
		
		lbl_DesenOper = new Label(composite_Center, SWT.NONE);
		lbl_DesenOper.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_DesenOper.setText("Desensitize Operations:");
		lbl_DesenOper.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		text_DesenOper = new Text(composite_Center, SWT.BORDER);
		text_DesenOper.setText("avg");
		text_DesenOper.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btn_add = new Button(composite_Center, SWT.NONE);
		btn_add.setImage(SWTResourceManager.getImage("img/add1.png"));
		btn_add.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				if(operationsCount != 1){
					new Label(composite_Center, SWT.NONE);
					new Label(composite_Center, SWT.NONE);
				}
				new Label(composite_Center, SWT.NONE);
				new Label(composite_Center, SWT.NONE);
				text_5 = new Text(composite_Center, SWT.BORDER);
				text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				operationsCount ++;
				scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				composite_Center.layout();
			}
		});
		
		btn_del = new Button(composite_Center, SWT.NONE);
		btn_del.setImage(SWTResourceManager.getImage("img/delete1.png"));
		btn_del.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				if(operationsCount >= 2){
					Control[] rightCs = composite_Center.getChildren();
					if(operationsCount == 2){
						for (int i = 1; i < 4; i++) {
							rightCs[rightCs.length - i].dispose();
						}
					}else{
						for (int i = 1; i < 6; i++) {
							rightCs[rightCs.length - i].dispose();
						}
					}
					operationsCount --;
				}
				composite_Center.layout();
			}
		});
		
		
		Composite composite_bottom = new Composite(shell, SWT.NONE);
		composite_bottom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_bottom.setLayoutData(BorderLayout.SOUTH);
		composite_bottom.setLayout(new FormLayout());

		Button btn_OK = new Button(composite_bottom, SWT.NONE);
		FormData fd_btn_OK = new FormData();
		btn_OK.setLayoutData(fd_btn_OK);
		btn_OK.setText("OK");
		btn_OK.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				if(!text_CategoryID.getText().equals(""))
					dataCategory.setId(text_CategoryID.getText());
				if(!text_shortDes.getText().equals(""))
					dataCategory.setShortDescription(text_shortDes.getText());
				if(!text_longDes.getText().equals(""))
					dataCategory.setLongDescription(text_longDes.getText());
				// TODO Auto-generated method stub
			}
		});
		
		Button btn_Cancel = new Button(composite_bottom, SWT.NONE);
		fd_btn_OK.top = new FormAttachment(btn_Cancel, 0, SWT.TOP);
		fd_btn_OK.right = new FormAttachment(btn_Cancel, -6);
		FormData fd_btn_Cancel = new FormData();
		fd_btn_Cancel.top = new FormAttachment(0, 3);
		fd_btn_Cancel.right = new FormAttachment(100, -29);
		btn_Cancel.setLayoutData(fd_btn_Cancel);
		btn_Cancel.setText("Cancel");
		btn_Cancel.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				shell.dispose();
			}
		});
	}
}
	