package edu.thu.ss.spec.manager;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;

/**
 * manage global user/data container and parsed vocabularies.
 * 
 * @author luochen
 * 
 */
public class VocabularyManager {
	//private static Logger logger = LoggerFactory.getLogger(CategoryManager.class);

	/**
	 * stores vocabulary, differentiated by unique URI.
	 */
	private static Map<URI, Vocabulary> parsedVocab = new HashMap<>();

	private static Map<String, UserContainer> users = new HashMap<>();
	private static Map<String, DataContainer> datas = new HashMap<>();

	private static boolean cache = true;

	public static void setCache(boolean cache) {
		VocabularyManager.cache = cache;
	}

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
		if (cache) {
			users.put(container.getId(), container);
		}
	}

	public static void add(DataContainer container) {
		if (cache) {
			datas.put(container.getId(), container);
		}
	}

	/**
	 * add a parsed vocabulary, and also add the included user/data containers.
	 * @param vocab
	 */
	public static void add(Vocabulary vocab) {
		if (cache) {
			parsedVocab.put(vocab.getPath(), vocab);
			add(vocab.getUserContainer());
			add(vocab.getDataContainer());
		}
	}

	public static boolean containsVocab(URI path) {
		return parsedVocab.containsKey(path);
	}

	public static Vocabulary getVocab(URI path) {
		return parsedVocab.get(path);
	}

	public static void clear() {
		parsedVocab.clear();
		users.clear();
		datas.clear();

	}

}
