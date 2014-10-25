package edu.thu.ss.xml.analyzer;

import edu.thu.ss.xml.pojo.DataAssociation;
import edu.thu.ss.xml.pojo.DataCategoryRef;
import edu.thu.ss.xml.pojo.Rule.Ruling;
import edu.thu.ss.xml.pojo.UserCategoryRef;

public class InclusionUtil {

	public static boolean includes(UserCategoryRef user1, UserCategoryRef user2) {
		return user1.getUser().ancestorOf(user2.getUser());
	}

	public static boolean includes(DataCategoryRef data1, DataCategoryRef data2) {
		return data1.getAction().ancestorOf(data2.getAction()) && data1.getData().ancestorOf(data2.getData());
	}

	public static boolean includes(DataCategoryRef data1, DataAssociation association2, Ruling ruling) {
		for (DataCategoryRef data2 : association2.getDataRefs()) {
			if (ruling.equals(Ruling.deny)) {
				if (includes(data1, data2)) {
					return true;
				}
			} else {
				if (!includes(data1, data2)) {
					return false;
				}
			}
		}
		if (ruling.equals(Ruling.deny)) {
			return false;
		} else {
			return true;
		}

	}

	public static boolean includes(DataAssociation association1, DataCategoryRef data2, Ruling ruling) {
		if (!ruling.equals(Ruling.allow)) {
			return false;
		}
		for (DataCategoryRef data1 : association1.getDataRefs()) {
			if (includes(data1, data2)) {
				return true;
			}
		}
		return false;
	}

	public static boolean includes(DataAssociation association1, DataAssociation association2, Ruling ruling) {
		if (!ruling.equals(Ruling.deny)) {
			for (DataCategoryRef data2 : association2.getDataRefs()) {
				boolean match = false;
				for (DataCategoryRef data1 : association1.getDataRefs()) {
					if (includes(data1, data2)) {
						match = true;
						break;
					}
				}
				if (!match) {
					return false;
				}
			}
		}
		if (!ruling.equals(Ruling.allow)) {
			for (DataCategoryRef data1 : association1.getDataRefs()) {
				boolean match = false;
				for (DataCategoryRef data2 : association2.getDataRefs()) {
					if (includes(data1, data2)) {
						match = true;
						break;
					}
				}
				if (!match) {
					return false;
				}
			}
		}
		return true;
	}
}
