package edu.thu.ss.editor.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorUtil;

public class RuleInfo extends Dialog {
	
	protected Shell shell;
	private Text text_RuleID;
	private Text text__ShortDes;
	private Text text_LongDes;
	private Label lbl_empty;
	private Button btn_Desensi;
	private Button btn_Forbid;
	private Label lbl_DataCateRefType;
	private Button btn_Association;
	private Button btn_NoneAssociation;
	private Button btn_Next;
	private Button btn_Cancel;
	
	public RuleInfo(Shell parent) {
		super(parent, SWT.NONE);
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
		shell.setSize(screenSize.width/3, screenSize.height/3);
		shell.setLocation(screenSize.width/4, screenSize.height/4);
		shell.setText("RuleInfo");
		shell.setLayout(new GridLayout(8, false));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lbl_RuleID = new Label(shell, SWT.NONE);
		lbl_RuleID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_RuleID.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_RuleID.setFont(EditorUtil.getDefaultFont());
		lbl_RuleID.setText("*Rule ID:");
		
		text_RuleID = new Text(shell, SWT.BORDER);
		GridData gd_text_RuleID = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		gd_text_RuleID.widthHint = 659;
		text_RuleID.setLayoutData(gd_text_RuleID);
		new Label(shell, SWT.NONE);
		
		lbl_empty = new Label(shell, SWT.NONE);
		lbl_empty.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_empty.setText("    ");
		new Label(shell, SWT.NONE);
		
		lbl_DataCateRefType = new Label(shell, SWT.NONE);
		lbl_DataCateRefType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_DataCateRefType.setText("Data category ref Type:");
		lbl_DataCateRefType.setFont(EditorUtil.getDefaultFont());
		lbl_DataCateRefType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		btn_Association = new Button(shell, SWT.RADIO);
		btn_Association.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btn_Association.setText("Association     ");
		
		btn_NoneAssociation = new Button(shell, SWT.RADIO);
		btn_NoneAssociation.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btn_NoneAssociation.setText("None Associ");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lbl_RestrType = new Label(shell, SWT.NONE);
		lbl_RestrType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_RestrType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_RestrType.setFont(EditorUtil.getDefaultFont());
		lbl_RestrType.setText("Restriction Type:");
		
		btn_Desensi = new Button(shell, SWT.RADIO);
		btn_Desensi.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btn_Desensi.setText("Desensitize");
		
		btn_Forbid = new Button(shell, SWT.RADIO);
		btn_Forbid.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		btn_Forbid.setText("Forbid");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lbl_ShortDes = new Label(shell, SWT.NONE);
		lbl_ShortDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_ShortDes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_ShortDes.setFont(EditorUtil.getDefaultFont());
		lbl_ShortDes.setText("Short Description:");
		
		text__ShortDes = new Text(shell, SWT.BORDER);
		text__ShortDes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lbl_LongDes = new Label(shell, SWT.NONE);
		lbl_LongDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_LongDes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_LongDes.setFont(EditorUtil.getDefaultFont());
		lbl_LongDes.setText("Long Description:");
		
		text_LongDes = new Text(shell, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text_LongDes = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		gd_text_LongDes.widthHint = 676;
		gd_text_LongDes.heightHint = 135;
		text_LongDes.setLayoutData(gd_text_LongDes);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		btn_Next = new Button(shell, SWT.NONE);
		btn_Next.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btn_Next.setText("Next");
		btn_Next.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				new RuleEdit(shell).open();
			}
		});
		
		btn_Cancel = new Button(shell, SWT.NONE);
		btn_Cancel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btn_Cancel.setText("Cancel");
		btn_Cancel.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				shell.dispose();
			}
		});
		new Label(shell, SWT.NONE);
		
	}

}
	