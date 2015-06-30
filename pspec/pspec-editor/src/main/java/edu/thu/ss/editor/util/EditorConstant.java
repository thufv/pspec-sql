package edu.thu.ss.editor.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.wb.swt.SWTResourceManager;

public class EditorConstant {

	public static Font getDefaultFont() {
		return SWTResourceManager.getFont("Arial", 10, SWT.NORMAL);
	}

	public static Font getDefaultBoldFont() {
		return SWTResourceManager.getFont("Arial", 10, SWT.BOLD);
	}
}
