package edu.thu.ss.editor.gui;

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

import edu.thu.ss.editor.util.EditorConstant;
import edu.thu.ss.spec.lang.pojo.UserCategory;

public class UserCategoryUI extends Dialog {
	
	protected Shell shell;
	private UserCategory userCategory;
	private Text text_categoryId;
	private Text text_parentID;
	private Text text_ShortDes;
	private Text text_LongDes;
	private Label lblNewLabel_4;
	private Button btn_Cancel;
	private Button btn_OK;
	
	public UserCategoryUI(Shell parent , UserCategory userCategory) {
		super(parent, SWT.NONE);
		this.userCategory = userCategory;
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
		shell.setLocation(screenSize.width/4 , screenSize.height/4);
		shell.setText("UserCategoryInfo");
		shell.setLayout(new GridLayout(6, false));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(EditorConstant.getDefaultFont());
		lblNewLabel.setText("*Category ID:");
		
		text_categoryId = new Text(shell, SWT.BORDER);
		text_categoryId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		if(userCategory.getId() != null)
			text_categoryId.setText(userCategory.getId());
		new Label(shell, SWT.NONE);
		
		lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_4.setText("    ");
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(EditorConstant.getDefaultFont());
		lblNewLabel_1.setText("Parent ID:");
		
		text_parentID = new Text(shell, SWT.BORDER);
		text_parentID.setEnabled(false);
		text_parentID.setEditable(false);
		text_parentID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		if(userCategory.getParentId() != null)
			text_parentID.setText(userCategory.getParentId());
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setFont(EditorConstant.getDefaultFont());
		lblNewLabel_2.setText("Short Description:");
		
		text_ShortDes = new Text(shell, SWT.BORDER);
		text_ShortDes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		if(userCategory.getShortDescription() != null)
			text_ShortDes.setText(userCategory.getShortDescription());
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setFont(EditorConstant.getDefaultFont());
		lblNewLabel_3.setText("Long Description:");
		
		text_LongDes = new Text(shell, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text_LongDes = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_text_LongDes.widthHint = 111;
		gd_text_LongDes.heightHint = 135;
		text_LongDes.setLayoutData(gd_text_LongDes);
		if(userCategory.getLongDescription() != null)
			text_LongDes.setText(userCategory.getLongDescription());
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		btn_OK = new Button(shell, SWT.NONE);
		btn_OK.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btn_OK.setText("Ok");
		btn_OK.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e){ 
				if(!text_categoryId.getText().equals(""))
					userCategory.setId(text_categoryId.getText());
				if(!text_ShortDes.getText().equals(""))
					userCategory.setShortDescription(text_ShortDes.getText());
				if(!text_LongDes.getText().equals(""))
					userCategory.setLongDescription(text_LongDes.getText());
				// TODO Auto-generated method stub
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
	