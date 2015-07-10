package edu.thu.ss.editor;

public class Main {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PSpecEditor editor = new PSpecEditor();
			editor.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
