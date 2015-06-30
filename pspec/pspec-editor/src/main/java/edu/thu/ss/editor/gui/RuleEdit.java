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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.thu.ss.editor.util.EditorConstant;
import swing2swt.layout.BorderLayout;

public class RuleEdit extends Dialog {

	private int userCount = 1;
	private int dataCount = 1;
	private int restrictionCount = 1;

	protected Shell shell;
	private Dimension screenSize;
	private ScrolledComposite scrolledComposite;
	private Composite composite_Center;
	private Composite composite_UserRef;
	private Composite composite_DataRef;
	private Composite composite_Restriction;
	private Text text_UserEclud;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	public RuleEdit(Shell parent) {
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
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setSize(screenSize.width / 2, screenSize.height / 2);
		shell.setLocation(screenSize.width / 4, screenSize.height / 4);
		shell.setText("RuleEdit");
		shell.setLayout(new BorderLayout(0, 0));

		scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setAlwaysShowScrollBars(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		composite_Center = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(composite_Center);
		scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		composite_Center.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_Center.setLayoutData(BorderLayout.CENTER);
		composite_Center.setLayout(new GridLayout(1, false));

		//========================================================
		composite_UserRef = new Composite(composite_Center, SWT.NONE);
		GridData gd_composite_UserRef = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_UserRef.widthHint = screenSize.height * 2 / 3;
		composite_UserRef.setLayoutData(gd_composite_UserRef);
		composite_UserRef.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_UserRef.setLayout(new GridLayout(5, false));
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);

		Label lbl_UserRefTitle = new Label(composite_UserRef, SWT.NONE);
		lbl_UserRefTitle.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lbl_UserRefTitle.setText("User reference:");
		lbl_UserRefTitle.setFont(EditorConstant.getDefaultBoldFont());
		lbl_UserRefTitle.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);

		Button btn_UserAdd = new Button(composite_UserRef, SWT.NONE);
		btn_UserAdd.setImage(SWTResourceManager.getImage("img/add1.png"));

		Button btn_UserDel = new Button(composite_UserRef, SWT.NONE);
		btn_UserDel.setImage(SWTResourceManager.getImage("img/delete1.png"));
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);
		new Label(composite_UserRef, SWT.NONE);

		userPart();
		btn_UserAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				userPart();
				userCount++;
				scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				composite_Center.layout();
				composite_UserRef.layout();
			}
		});
		btn_UserDel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (userCount >= 2) {
					Control[] rightCs = composite_UserRef.getChildren();
					for (int i = 1; i < 9; i++) {
						rightCs[rightCs.length - i].dispose();
					}
					userCount--;
					scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					composite_Center.layout();
					composite_UserRef.layout();
				}
			}
		});
		//========================================================
		composite_DataRef = new Composite(composite_Center, SWT.NONE);
		GridData gd_composite_DataRef = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_DataRef.widthHint = screenSize.height * 2 / 3;
		composite_DataRef.setLayoutData(gd_composite_DataRef);
		composite_DataRef.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_DataRef.setLayout(new GridLayout(7, false));

		Label label_1 = new Label(composite_DataRef, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		label_1.setText("Data reference:");
		label_1.setFont(EditorConstant.getDefaultBoldFont());
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);

		Button button_add2 = new Button(composite_DataRef, SWT.NONE);
		button_add2.setImage(SWTResourceManager.getImage("img/add1.png"));

		Button button_de2 = new Button(composite_DataRef, SWT.NONE);
		button_de2.setImage(SWTResourceManager.getImage("img/delete1.png"));
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);
		new Label(composite_DataRef, SWT.NONE);

		dataPart();
		button_add2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				dataPart();
				dataCount++;
				scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				composite_Center.layout();
				composite_DataRef.layout();
			}
		});
		button_de2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (dataCount >= 2) {
					Control[] rightCs = composite_DataRef.getChildren();
					for (int i = 1; i < 13; i++) {
						rightCs[rightCs.length - i].dispose();
					}
					dataCount--;
					scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					composite_Center.layout();
					composite_DataRef.layout();
				}
			}
		});

		//========================================================
		composite_Restriction = new Composite(composite_Center, SWT.NONE);
		GridData gd_composite_Restriction = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_Restriction.widthHint = screenSize.height * 2 / 3;
		composite_Restriction.setLayoutData(gd_composite_Restriction);
		composite_Restriction.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_Restriction.setLayout(new GridLayout(5, false));

		Label lblRestriction = new Label(composite_Restriction, SWT.NONE);
		lblRestriction.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblRestriction.setText("Restriction:       ");
		lblRestriction.setFont(EditorConstant.getDefaultBoldFont());
		lblRestriction.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		new Label(composite_Restriction, SWT.NONE);
		new Label(composite_Restriction, SWT.NONE);
		new Label(composite_Restriction, SWT.NONE);

		Button button_add3 = new Button(composite_Restriction, SWT.NONE);
		button_add3.setImage(SWTResourceManager.getImage("img/add1.png"));

		Button button_de3 = new Button(composite_Restriction, SWT.NONE);
		button_de3.setImage(SWTResourceManager.getImage("img/delete1.png"));
		new Label(composite_Restriction, SWT.NONE);
		new Label(composite_Restriction, SWT.NONE);
		new Label(composite_Restriction, SWT.NONE);

		restrictionPart();
		button_add3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				restrictionPart();
				restrictionCount++;
				scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				composite_Center.layout();
				composite_Restriction.layout();
			}
		});
		button_de3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (restrictionCount >= 2) {
					Control[] rightCs = composite_Restriction.getChildren();
					for (int i = 1; i < 11; i++) {
						rightCs[rightCs.length - i].dispose();
					}
					restrictionCount--;
					scrolledComposite.setMinSize(composite_Center.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					composite_Center.layout();
					composite_Restriction.layout();
				}
			}
		});

		//========================================================
		Composite composite_Bottom = new Composite(shell, SWT.NONE);
		composite_Bottom.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_Bottom.setLayoutData(BorderLayout.SOUTH);
		composite_Bottom.setLayout(new FormLayout());

		Button btn_OK = new Button(composite_Bottom, SWT.NONE);
		FormData fd_btn_OK = new FormData();
		btn_OK.setLayoutData(fd_btn_OK);
		btn_OK.setText("OK");

		Button btn_Cancel = new Button(composite_Bottom, SWT.NONE);
		fd_btn_OK.top = new FormAttachment(btn_Cancel, 0, SWT.TOP);
		fd_btn_OK.right = new FormAttachment(btn_Cancel, -6);
		FormData fd_btn_Cancel = new FormData();
		fd_btn_Cancel.top = new FormAttachment(0, 3);
		fd_btn_Cancel.right = new FormAttachment(100, -29);
		btn_Cancel.setLayoutData(fd_btn_Cancel);
		btn_Cancel.setText("Cancel");
		btn_Cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	private void userPart() {
		Button btn_UserChek = new Button(composite_UserRef, SWT.CHECK);

		Label lbl_UserRefID = new Label(composite_UserRef, SWT.NONE);
		lbl_UserRefID.setText("refid:");
		lbl_UserRefID.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lbl_UserRefID.setFont(EditorConstant.getDefaultFont());
		lbl_UserRefID.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Combo combo_UserRefID = new Combo(composite_UserRef, SWT.NONE);
		GridData gd_combo_UserRefID = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_combo_UserRefID.widthHint = 621;
		combo_UserRefID.setLayoutData(gd_combo_UserRefID);
		new Label(composite_UserRef, SWT.NONE);

		Label lbl_UserEclud = new Label(composite_UserRef, SWT.NONE);
		lbl_UserEclud.setText("exclude:");
		lbl_UserEclud.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lbl_UserEclud.setFont(EditorConstant.getDefaultFont());
		lbl_UserEclud.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		text_UserEclud = new Text(composite_UserRef, SWT.BORDER);
		text_UserEclud.setEditable(false);
		text_UserEclud.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btn_UserDele = new Button(composite_UserRef, SWT.NONE);
		btn_UserDele.setText("delete");

		Combo combo_UserEclud = new Combo(composite_UserRef, SWT.NONE);
		combo_UserEclud.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}

	private void dataPart() {
		Button button_che2 = new Button(composite_DataRef, SWT.CHECK);

		Label label_2 = new Label(composite_DataRef, SWT.NONE);
		label_2.setText("refid:");
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		label_2.setFont(EditorConstant.getDefaultFont());
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Combo combo = new Combo(composite_DataRef, SWT.NONE);
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_combo.widthHint = screenSize.height / 3;
		combo.setLayoutData(gd_combo);

		Label label_4 = new Label(composite_DataRef, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setText("action:");
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		label_4.setFont(EditorConstant.getDefaultFont());
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Combo combo_2 = new Combo(composite_DataRef, SWT.NONE);
		combo_2.setItems(new String[] { "all", "projection", "condition" });
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_DataRef, SWT.NONE);

		Label label_3 = new Label(composite_DataRef, SWT.NONE);
		label_3.setText("exclude:");
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		label_3.setFont(EditorConstant.getDefaultFont());
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		text_1 = new Text(composite_DataRef, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_3 = new Button(composite_DataRef, SWT.NONE);
		button_3.setText("delete");

		Combo combo_1 = new Combo(composite_DataRef, SWT.NONE);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	}

	private void restrictionPart() {
		Button button_2 = new Button(composite_Restriction, SWT.CHECK);

		Label label_3 = new Label(composite_Restriction, SWT.NONE);
		label_3.setText("refid:");
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		label_3.setFont(EditorConstant.getDefaultFont());
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		text_2 = new Text(composite_Restriction, SWT.BORDER);
		text_2.setEditable(false);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_3 = new Button(composite_Restriction, SWT.NONE);
		button_3.setText("delete");

		Combo combo = new Combo(composite_Restriction, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_Restriction, SWT.NONE);

		Label label_4 = new Label(composite_Restriction, SWT.NONE);
		label_4.setText("operation:");
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		label_4.setFont(EditorConstant.getDefaultFont());
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		text_3 = new Text(composite_Restriction, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_4 = new Button(composite_Restriction, SWT.NONE);
		button_4.setText("add");
		new Label(composite_Restriction, SWT.NONE);
	}
}
