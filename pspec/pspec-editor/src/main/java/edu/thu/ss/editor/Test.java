package edu.thu.ss.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JButton;
import javax.swing.JFrame;

/******************************************************************************
 * All Right Reserved. 
 * Copyright (c) 1998, 2004 Jackwind Li Guojie
 * 
 * Created on 2004-4-9 14:11:34 by JACK
 * $Id$
 * 
 *****************************************************************************/

public class Test {

	public static void main(String[] args) throws Exception {
		URL url = HelpSet.findHelpSet(Test.class.getClassLoader(), "help/pspec.hs");
		HelpSet helpSet = new HelpSet(null, url);

		HelpBroker helpBroker = helpSet.createHelpBroker("PSpec Editor");
		
		helpBroker.setDisplayed(true);
	}

}
