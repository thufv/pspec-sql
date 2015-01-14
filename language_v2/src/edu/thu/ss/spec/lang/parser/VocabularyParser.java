package edu.thu.ss.spec.lang.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.global.CategoryManager;
import edu.thu.ss.spec.lang.pojo.Category;
import edu.thu.ss.spec.lang.pojo.CategoryContainer;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.util.ParsingException;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * parses and analyzes vocabulary
 * 
 * @author luochen
 * 
 */
public class VocabularyParser implements ParserConstant {

	/**
	 * parsed vocabulary in current instance
	 */
	protected Map<URI, Vocabulary> vocabularies;

	protected Map<String, UserContainer> userContainers;

	protected Map<String, DataContainer> dataContainers;

	protected boolean error = false;

	private static Logger logger = LoggerFactory.getLogger(VocabularyParser.class);

	public Vocabulary parse(String path, String user, String data) throws Exception {
		init();
		loadVocabularies(XMLUtil.toUri(path));

		parseUsers();
		parseDatas();
		if (error) {
			throw new ParsingException("Fail to parse vocabularies, see error messages above");
		}

		// semantic analysis
		resolveReference(userContainers);
		resolveReference(dataContainers);

		Map<String, UserContainer> mergedUsers = collectUsers(user);
		Map<String, DataContainer> mergedDatas = collectDatas(data);
		if (error) {
			throw new ParsingException("Fail to parse vocabularies, see error messages above");
		}

		inheritOperations(mergedDatas.values(), mergedDatas.get(data));
		registerVocabularies();

		Vocabulary vocabulary = new Vocabulary();
		vocabulary.setUserContainers(mergedUsers);
		vocabulary.setDataContainers(mergedDatas);
		return vocabulary;
	}

	private void init() {
		this.vocabularies = new HashMap<>();
		this.userContainers = new HashMap<>();
		this.dataContainers = new HashMap<>();
		this.error = false;
	}

	private Map<String, UserContainer> collectUsers(String user) {
		UserContainer current = userContainers.get(user);
		if (current == null) {
			logger.error("Fail to locate user category container: {}", user);
			error = true;
			return null;
		}
		Map<String, UserContainer> containers = new HashMap<>();
		while (current != null) {
			containers.put(current.getId(), current);
			current = current.getBaseContainer();
		}
		return containers;
	}

	private Map<String, DataContainer> collectDatas(String data) {
		DataContainer current = dataContainers.get(data);
		if (current == null) {
			logger.error("Fail to locate data category container: {}", data);
			error = true;
			return null;
		}
		Map<String, DataContainer> containers = new HashMap<>();
		while (current != null) {
			containers.put(current.getId(), current);
			current = current.getBaseContainer();
		}
		return containers;
	}

	private void registerVocabularies() {
		for (Vocabulary vocab : vocabularies.values()) {
			if (!vocab.isResolved()) {
				vocab.setResolved(true);
				vocab.setRootNode(null);
				CategoryManager.add(vocab);
			}
		}

	}

	/**
	 * load all referred {@link Vocabulary} (chain)
	 * 
	 * @param uri
	 * @throws Exception
	 */
	private void loadVocabularies(URI uri) throws Exception {
		while (uri != null) {
			Vocabulary vocabulary = CategoryManager.getParsedVocab().get(uri);
			// check vocabulary is parsed before
			if (CategoryManager.containsVocab(uri)) {
				vocabularies.put(uri, vocabulary);
			} else {
				vocabulary = new Vocabulary();
				Document document = XMLUtil.parseDocument(uri, Privacy_Schema_Location);
				Node rootNode = document.getElementsByTagName(ParserConstant.Ele_Vocabulary).item(0);
				vocabulary.setRootNode(rootNode);
				vocabulary.setPath(uri);
				parseInfo(vocabulary);
				if (vocabularies.get(uri) != null) {
					throw new ParsingException("Cycle reference of vocabularies detected: " + uri);
				}
				vocabularies.put(uri, vocabulary);
			}
			uri = vocabulary.getBase();
		}
	}

	/**
	 * parse {@link Info} from {@link Vocabulary}
	 * 
	 * @param vocabulary
	 * @throws Exception
	 */
	private void parseInfo(Vocabulary vocabulary) throws Exception {
		Node root = vocabulary.getRootNode();
		NodeList list = root.getChildNodes();
		String base = XMLUtil.getAttrValue(root, Attr_Vocabulary_Base);
		if (base != null) {
			vocabulary.setBase(XMLUtil.toUri(base));
		}
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Vocabulary_Info.equals(name) && vocabulary.getInfo() == null) {
				Info info = new Info();
				info.parse(node);
				vocabulary.setInfo(info);
				return;
			}
		}
	}

	private void inheritOperations(Collection<DataContainer> containers, DataContainer target) {
		for (DataContainer container : containers) {
			for (DataCategory category : container.getRoot()) {
				category.inheritDesensitizeOperation(target);
			}
		}
	}

	private void parseUsers() throws Exception {
		for (Vocabulary vocabulary : vocabularies.values()) {
			if (vocabulary.isResolved()) {
				for (UserContainer container : vocabulary.getUserContainers().values()) {
					userContainers.put(container.getId(), container);
				}
			} else {
				Node root = vocabulary.getRootNode();
				NodeList list = root.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					String name = node.getLocalName();
					if (Ele_Vocabulary_User_Category_Container.equals(name)) {
						UserContainer container = new UserContainer();
						container.parse(node);
						addUserContainer(container, vocabulary);
					}
				}
			}
		}
	}

	private void parseDatas() throws Exception {
		for (Vocabulary vocabulary : vocabularies.values()) {
			if (vocabulary.isResolved()) {
				for (DataContainer container : vocabulary.getDataContainers().values()) {
					dataContainers.put(container.getId(), container);
				}
			} else {
				Node root = vocabulary.getRootNode();
				NodeList list = root.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					String name = node.getLocalName();
					if (Ele_Vocabulary_Data_Category_Container.equals(name)) {
						DataContainer container = new DataContainer();
						container.parse(node);
						addDataContainer(container, vocabulary);
					}
				}
			}
		}
	}

	private void addUserContainer(UserContainer container, Vocabulary vocabulary) {
		if (userContainers.containsKey(container.getId())
				|| CategoryManager.getUsers().containsKey(container.getId())) {
			logger.error("Duplicate UserCategoryContainer: {} detected, please fix.", container.getId());
			error = true;
		}
		userContainers.put(container.getId(), container);
		vocabulary.getUserContainers().put(container.getId(), container);
	}

	private void addDataContainer(DataContainer container, Vocabulary vocabulary) {
		if (dataContainers.containsKey(container.getId())
				|| CategoryManager.getDatas().containsKey(container.getId())) {
			logger.error("Duplicate UserCategoryContainer: {} detected, please fix.", container.getId());
			error = true;
		}
		dataContainers.put(container.getId(), container);
		vocabulary.getDataContainers().put(container.getId(), container);
	}

	/**
	 * resolve reference recursively
	 * 
	 * @param container
	 * @param containers
	 * @param baseIds
	 *          : used for detect cycle reference
	 * @throws ParsingException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends CategoryContainer> void resolveReference(CategoryContainer container,
			Map<String, T> containers, Set<String> baseIds) throws ParsingException {
		if (container.isResolved()) {
			return;
		}
		String base = container.getBase();
		if (base != null) {
			// resolve base container
			T baseContainer = containers.get(base);
			if (baseContainer == null) {
				logger.error("Fail to locate base category container: {} for category container: {}.",
						base, container.getId());
				error = true;
				return;
			}
			if (baseIds.contains(base)) {
				error = true;
				throw new ParsingException("Cycle reference of category container detected: "
						+ baseIds.toString());
			}
			baseIds.add(base);
			resolveReference(baseContainer, containers, baseIds);
			baseIds.remove(base);
			container.setBaseContainer(baseContainer);
		}
		// resolve parent reference of all categories
		for (Object obj : container.getCategories()) {
			Category category = (Category) obj;
			String parentId = category.getParentId();
			if (parentId == null) {
				container.getRoot().add(category);
			} else {
				Category parent = resolveParent(parentId, container);
				if (parent == null) {
					logger.error("Fail to locate parent category: {} for category: {}.", parentId,
							category.getId());
					error = true;
				} else {
					parent.buildRelation(category);
				}
			}
		}
		container.setResolved(true);
	}

	@SuppressWarnings({ "rawtypes" })
	private Category resolveParent(String parentId, CategoryContainer container) {
		CategoryContainer current = container;
		while (current != null) {
			Category result = container.get(parentId);
			if (result != null) {
				return result;
			}
			current = current.getBaseContainer();
		}
		return null;
	}

	/**
	 * Resolve all references (container and category) in containers
	 * 
	 * @param containers
	 * @throws ParsingException
	 */
	@SuppressWarnings({ "rawtypes" })
	private <T extends CategoryContainer> void resolveReference(Map<String, T> containers)
			throws ParsingException {
		for (String id : containers.keySet()) {
			CategoryContainer container = containers.get(id);
			if (container.isResolved()) {
				continue;
			}
			Set<String> baseIds = new HashSet<>();
			resolveReference(container, containers, baseIds);
		}

		for (CategoryContainer container : containers.values()) {
			if (!container.isLeaf()) {
				continue;
			}
			checkDuplicates(container);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void checkDuplicates(CategoryContainer container) {
		Map<String, Category> categories = new HashMap<>();
		List<Category> roots = new LinkedList<>();

		CategoryContainer current = container;
		while (current != null) {
			for (Object obj : current.getCategories()) {
				Category category = (Category) obj;
				if (categories.containsKey(category.getId())) {
					logger.error("Duplicate category: {} detected, please fix.", category.getId());
					error = true;
				}
				categories.put(category.getId(), category);
			}
			roots.addAll(current.getRoot());
			current = current.getBaseContainer();
		}
		int count = 0;
		for (Category root : roots) {
			count += countCategories(root);
		}
		if (count < categories.size()) {
			logger.error("Cycle reference of categories detected in container: {}, please fix.",
					container.getId());
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private int countCategories(Category category) {
		int count = 1;
		if (category.getChildren() != null) {
			for (Object child : category.getChildren()) {
				count += countCategories((Category) child);
			}
		}
		return count;
	}

}
