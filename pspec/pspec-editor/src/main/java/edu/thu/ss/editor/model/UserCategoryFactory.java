package edu.thu.ss.editor.model;

import java.util.ArrayList;

import edu.thu.ss.spec.lang.pojo.UserCategory;

public class UserCategoryFactory {
	public static ArrayList<UserCategory> createTree(){
	
		UserCategory uc0 = new UserCategory("Root","containerId");
		UserCategory uc1 = new UserCategory("UserCategory1","containerId");
		UserCategory uc2 = new UserCategory("UserCategory2","containerId");
		UserCategory uc3 = new UserCategory("UserCategory3","containerId");
		UserCategory uc4 = new UserCategory("UserCategory4","containerId");
		UserCategory uc5 = new UserCategory("UserCategory5","containerId");
		UserCategory uc6 = new UserCategory("UserCategory6","containerId");
		UserCategory uc7 = new UserCategory("UserCategory7","containerId");
		UserCategory uc8 = new UserCategory("UserCategory8","containerId");
		UserCategory uc9 = new UserCategory("UserCategory9","containerId");
		UserCategory uc10 = new UserCategory("UserCategory10","containerId");
		UserCategory uc11 = new UserCategory("UserCategory11","containerId");
		UserCategory uc12 = new UserCategory("UserCategory12","containerId");
		UserCategory uc13 = new UserCategory("UserCategory13","containerId");
		UserCategory uc14 = new UserCategory("UserCategory14","containerId");
		UserCategory uc15 = new UserCategory("UserCategory15","containerId");
		UserCategory uc16 = new UserCategory("UserCategory16","containerId");
		UserCategory uc17 = new UserCategory("UserCategory17","containerId");
		UserCategory uc18 = new UserCategory("UserCategory18","containerId");
		UserCategory uc19 = new UserCategory("UserCategory19","containerId");
		UserCategory uc20 = new UserCategory("UserCategory20","containerId");
		UserCategory uc21 = new UserCategory("UserCategory21","containerId");
		UserCategory uc22 = new UserCategory("UserCategory22","containerId");
		UserCategory uc23 = new UserCategory("UserCategory23","containerId");
		UserCategory uc24 = new UserCategory("UserCategory24","containerId");
		
		ArrayList<UserCategory> list0 = new ArrayList<UserCategory>();
		ArrayList<UserCategory> list1 = new ArrayList<UserCategory>();
		ArrayList<UserCategory> list2 = new ArrayList<UserCategory>();
		ArrayList<UserCategory> list3 = new ArrayList<UserCategory>();
		ArrayList<UserCategory> list4 = new ArrayList<UserCategory>();
		ArrayList<UserCategory> list5 = new ArrayList<UserCategory>();
		ArrayList<UserCategory> list6 = new ArrayList<UserCategory>();
		
		list4.add(uc13);
		list4.add(uc14);
		list4.add(uc15);
		list4.add(uc16);
		list5.add(uc17);
		list5.add(uc18);
		list5.add(uc19);
		list5.add(uc20);
		list6.add(uc21);
		list6.add(uc22);
		list6.add(uc23);
		list6.add(uc24);
		
		uc4.setChildren(list4);
		uc7.setChildren(list5);
		uc10.setChildren(list6);
		
		list2.add(uc4);
		list2.add(uc5);
		list2.add(uc6);
		list2.add(uc7);
		list2.add(uc8);
		list2.add(uc9);
		list3.add(uc10);
		list3.add(uc11);
		list3.add(uc12);
		
		uc2.setChildren(list2);
		uc3.setChildren(list3);
		
		list1.add(uc1);
		list1.add(uc2);
		list1.add(uc3);
		
		uc0.setChildren(list1);
		list0.add(uc0);
		
		return list0;
	}
}
