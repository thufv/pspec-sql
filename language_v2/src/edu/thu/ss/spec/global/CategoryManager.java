package edu.thu.ss.spec.global;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

/**
 * A category manager for data/user category containers
 * 
 * @author luochen
 * 
 */
public class CategoryManager {
	private static Logger logger = LoggerFactory.getLogger(CategoryManager.class);

	private static Map<URI, Vocabulary> parsedVocab = new HashMap<>();

	private static Map<String, UserContainer> users = new HashMap<>();
	private static Map<String, DataContainer> datas = new HashMap<>();

	public static Map<URI, Vocabulary> getParsedVocab() {
		return parsedVocab;
	}

	public static Map<String, UserContainer> getUsers() {
		return users;
	}

	public static Map<String, DataContainer> getDatas() {
		return datas;
	}

	public static void add(UserContainer container) {
		users.put(container.getId(), container);
	}

	public static void add(DataContainer container) {
		datas.put(container.getId(), container);
	}

	public static void add(Vocabulary vocab) {
		parsedVocab.put(vocab.getPath(), vocab);
		for (UserContainer container : vocab.getUserContainers().values()) {
			add(container);
		}
		for (DataContainer container : vocab.getDataContainers().values()) {
			add(container);
		}
	}

	public static boolean containsVocab(String path) throws Exception {
		return parsedVocab.containsKey(new URI(path).normalize());
	}

	public static Vocabulary getVocab(String path) throws Exception {
		return parsedVocab.get(new URI(path).normalize());
	}

}
