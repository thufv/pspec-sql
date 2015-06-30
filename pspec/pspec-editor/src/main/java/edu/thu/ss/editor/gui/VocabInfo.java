package edu.thu.ss.editor.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorConstant;

public class VocabInfo extends Composite {
	
	protected static Shell shell;
	
	private Text text_Name;
	private Text text_Email;
	private Text text_Country;
	private Text text_vocabID;
	private Text text_Location;
	private Text text__Organiz;
	private Text text_Addr;
	private Text text_LongDes;
	private Text text_ShortDes;
	/**
	 * Create the composite
	 * @param parent
	 * @param style
	 */
	public VocabInfo(Shell shell_ , Composite parent, int style) {
		super(parent, style);
		shell = shell_;
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(5, false));
		
		Label lbl_empty = new Label(this, SWT.NONE);
		lbl_empty.setText("        ");
		lbl_empty.setFont(EditorConstant.getDefaultFont());
		lbl_empty.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lbl_Title = new Label(this, SWT.NONE);
		lbl_Title.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lbl_Title.setText("Vocabulary Info");
		lbl_Title.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		lbl_Title.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		lbl_Title.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_vocabID = new Label(this, SWT.NONE);
		lbl_vocabID.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_vocabID.setFont(EditorConstant.getDefaultFont());
		lbl_vocabID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_vocabID.setText("*Vocabulary ID\uFF1A");
		
		text_vocabID = new Text(this, SWT.BORDER);
		GridData gd_text_vocabID = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_vocabID.widthHint = 748;
		text_vocabID.setLayoutData(gd_text_vocabID);
		new Label(this, SWT.NONE);
		
		Label lbl_empty1 = new Label(this, SWT.NONE);
		lbl_empty1.setText("                                 ");
		lbl_empty1.setFont(EditorConstant.getDefaultFont());
		lbl_empty1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(this, SWT.NONE);
		
		Label lbl_Location = new Label(this, SWT.NONE);
		lbl_Location.setText("  Location\uFF1A");
		lbl_Location.setFont(EditorConstant.getDefaultFont());
		lbl_Location.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		text_Location = new Text(this, SWT.BORDER);
		text_Location.setEnabled(false);
		text_Location.setEditable(false);
		text_Location.setText("");
		text_Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Button btn_Browse = new Button(this, SWT.NONE);
		btn_Browse.setText("Browse");
		btn_Browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog  directoryDialog  = new DirectoryDialog (shell);
				directoryDialog.setText("chooser");
				directoryDialog.open();
				if(!directoryDialog.getFilterPath().equals(""))
					text_Location.setText(directoryDialog.getFilterPath());
			}
		});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_Issuer = new Label(this, SWT.NONE);
		lbl_Issuer.setFont(EditorConstant.getDefaultFont());
		lbl_Issuer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_Issuer.setText("  Issuer:");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_Name = new Label(this, SWT.NONE);
		lbl_Name.setFont(EditorConstant.getDefaultFont());
		lbl_Name.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_Name.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_Name.setText("    Name : ");
		
		text_Name = new Text(this, SWT.BORDER);
		text_Name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_Organiz = new Label(this, SWT.NONE);
		lbl_Organiz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_Organiz.setText("    Organiz : ");
		lbl_Organiz.setFont(EditorConstant.getDefaultFont());
		lbl_Organiz.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		text__Organiz = new Text(this, SWT.BORDER);
		text__Organiz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_Email = new Label(this, SWT.NONE);
		lbl_Email.setText("    E-mail : ");
		lbl_Email.setFont(EditorConstant.getDefaultFont());
		lbl_Email.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_Email.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		text_Email = new Text(this, SWT.BORDER);
		text_Email.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_Addr = new Label(this, SWT.NONE);
		lbl_Addr.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_Addr.setText("    Addr : ");
		lbl_Addr.setFont(EditorConstant.getDefaultFont());
		lbl_Addr.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		text_Addr = new Text(this, SWT.BORDER);
		text_Addr.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_Country = new Label(this, SWT.NONE);
		lbl_Country.setText("    Country : ");
		lbl_Country.setFont(EditorConstant.getDefaultFont());
		lbl_Country.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_Country.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		text_Country = new Text(this, SWT.BORDER);
		text_Country.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_ShortDes = new Label(this, SWT.NONE);
		lbl_ShortDes.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lbl_ShortDes.setFont(EditorConstant.getDefaultFont());
		lbl_ShortDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_ShortDes.setText(" Short Descrip\uFF1A");
		
		text_ShortDes = new Text(this, SWT.BORDER);
		text_ShortDes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lbl_LongDes = new Label(this, SWT.NONE);
		lbl_LongDes.setFont(EditorConstant.getDefaultFont());
		lbl_LongDes.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
		lbl_LongDes.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lbl_LongDes.setText(" Long Descrip\uFF1A");
		
		text_LongDes = new Text(this, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_text_LongDes = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text_LongDes.heightHint = 142;
		text_LongDes.setLayoutData(gd_text_LongDes);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		
	}
}
