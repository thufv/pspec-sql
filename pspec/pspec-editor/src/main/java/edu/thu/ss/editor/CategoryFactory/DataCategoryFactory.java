package edu.thu.ss.editor.CategoryFactory;

import java.util.ArrayList;

import edu.thu.ss.spec.lang.pojo.DataCategory;

public class DataCategoryFactory {
	public static ArrayList<DataCategory> createTree(){
	
		DataCategory dc0 = new DataCategory("Root","containerId");
		DataCategory dc1 = new DataCategory("DataCategory1","containerId");
		DataCategory dc2 = new DataCategory("DataCategory2","containerId");
		DataCategory dc3 = new DataCategory("DataCategory3","containerId");
		DataCategory dc4 = new DataCategory("DataCategory4","containerId");
		DataCategory dc5 = new DataCategory("DataCategory5","containerId");
		DataCategory dc6 = new DataCategory("DataCategory6","containerId");
		DataCategory dc7 = new DataCategory("DataCategory7","containerId");
		DataCategory dc8 = new DataCategory("DataCategory8","containerId");
		DataCategory dc9 = new DataCategory("DataCategory9","containerId");
		DataCategory dc10 = new DataCategory("DataCategory10","containerId");
		DataCategory dc11 = new DataCategory("DataCategory11","containerId");
		DataCategory dc12 = new DataCategory("DataCategory12","containerId");
		DataCategory dc13 = new DataCategory("DataCategory13","containerId");
		DataCategory dc14 = new DataCategory("DataCategory14","containerId");
		DataCategory dc15 = new DataCategory("DataCategory15","containerId");
		DataCategory dc16 = new DataCategory("DataCategory16","containerId");
		DataCategory dc17 = new DataCategory("DataCategory17","containerId");
		DataCategory dc18 = new DataCategory("DataCategory18","containerId");
		DataCategory dc19 = new DataCategory("DataCategory19","containerId");
		DataCategory dc20 = new DataCategory("DataCategory20","containerId");
		DataCategory dc21 = new DataCategory("DataCategory21","containerId");
		DataCategory dc22 = new DataCategory("DataCategory22","containerId");
		DataCategory dc23 = new DataCategory("DataCategory23","containerId");
		DataCategory dc24 = new DataCategory("DataCategory24","containerId");
		
		ArrayList<DataCategory> list0 = new ArrayList<DataCategory>();
		ArrayList<DataCategory> list1 = new ArrayList<DataCategory>();
		ArrayList<DataCategory> list2 = new ArrayList<DataCategory>();
		ArrayList<DataCategory> list3 = new ArrayList<DataCategory>();
		ArrayList<DataCategory> list4 = new ArrayList<DataCategory>();
		ArrayList<DataCategory> list5 = new ArrayList<DataCategory>();
		ArrayList<DataCategory> list6 = new ArrayList<DataCategory>();
		
		list4.add(dc13);
		list4.add(dc14);
		list4.add(dc15);
		list4.add(dc16);
		list5.add(dc17);
		list5.add(dc18);
		list5.add(dc19);
		list5.add(dc20);
		list6.add(dc21);
		list6.add(dc22);
		list6.add(dc23);
		list6.add(dc24);
		
		dc4.setChildren(list4);
		dc7.setChildren(list5);
		dc10.setChildren(list6);
		
		list2.add(dc4);
		list2.add(dc5);
		list2.add(dc6);
		list2.add(dc7);
		list2.add(dc8);
		list2.add(dc9);
		list3.add(dc10);
		list3.add(dc11);
		list3.add(dc12);
		
		dc2.setChildren(list2);
		dc3.setChildren(list3);
		
		list1.add(dc1);
		list1.add(dc2);
		list1.add(dc3);
		
		dc0.setChildren(list1);
		list0.add(dc0);
		
		return list0;
	}
}
