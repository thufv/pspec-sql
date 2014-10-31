package edu.thu.ss.lang.util;

import java.util.BitSet;
import java.util.Set;

import edu.thu.ss.lang.pojo.DataCategory;
import edu.thu.ss.lang.pojo.DataCategoryContainer;
import edu.thu.ss.lang.pojo.HierarchicalObject;
import edu.thu.ss.lang.pojo.UserCategory;
import edu.thu.ss.lang.pojo.UserCategoryContainer;

public class CategoryManager {

	private static UserCategoryContainer users;
	private static DataCategoryContainer datas;
	public static int Num_Users;
	public static int Num_Datas;
	private static boolean inited = false;

	public static void init(UserCategoryContainer userContainer, DataCategoryContainer dataContainer) {
		users = userContainer;
		datas = dataContainer;
		inited = true;

		Num_Users = users.getCategories().size();
		Num_Datas = datas.getCategories().size();
	}

	public static UserCategory getUser(int label) {
		assert (inited);
		return users.getCategory(label);
	}

	public static DataCategory getData(int label) {
		assert (inited);
		return datas.getCategory(label);
	}

	public static void printUsers(StringBuilder sb, BitSet users) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i)) {
				sb.append(getUser(i).getId());
				sb.append(' ');
			}
		}
	}

	public static void printDatas(StringBuilder sb, BitSet datas) {
		for (int i = 0; i < datas.size(); i++) {
			if (datas.get(i)) {
				sb.append(getData(i).getId());
				sb.append(' ');
			}
		}
	}

	public static <T extends HierarchicalObject<T>> BitSet toBits(Set<T> set, int total) {
		BitSet result = new BitSet(total);
		for (T t : set) {
			result.set(t.getLabel());
		}
		return result;
	}

	public static BitSet dataToBits(Set<DataCategory> set) {
		return toBits(set, CategoryManager.Num_Datas);
	}

	public static BitSet userToBits(Set<UserCategory> set) {
		return toBits(set, CategoryManager.Num_Datas);
	}
}
