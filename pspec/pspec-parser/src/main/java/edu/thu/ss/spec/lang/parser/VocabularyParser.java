package edu.thu.ss.spec.lang.parser;

import java.net.URI;
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

	protected Vocabulary vocabulary;

	protected Map<String, UserContainer> userContainers;

	protected Map<String, DataContainer> dataContainers;

	protected boolean error = false;

	private static Logger logger = LoggerFactory.getLogger(VocabularyParser.class);

	public Vocabulary parse(String path) throws Exception {
		init();

		URI uri = XMLUtil.toUri(path);
		if (CategoryManager.containsVocab(uri)) {
			return CategoryManager.getVocab(uri);
		}

		loadVocabularies(uri);
		parseContainers();
		if (error) {
			throw new ParsingException("Fail to parse vocabularies, see error messages above");
		}

		// semantic analysis
		buildReference(vocabulary.getUserContainer());
		buildReference(vocabulary.getDataContainer());

		if (error) {
			throw new ParsingException("Fail to parse vocabularies, see error messages above");
		}

		registerVocabularies();

		return vocabulary;
	}

	private void init() {
		this.vocabularies = new HashMap<>();
		this.userContainers = new HashMap<>();
		this.dataContainers = new HashMap<>();
		this.error = false;
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
		Vocabulary previous = null;
		boolean stop = false;
		URI currentUri = uri;
		while (currentUri != null) {
			Vocabulary vocabulary = CategoryManager.getVocab(currentUri);
			if (vocabulary == null) {
				vocabulary = new Vocabulary();
				Document document = XMLUtil.parseDocument(currentUri, Privacy_Schema_Location);
				Node rootNode = document.getElementsByTagName(ParserConstant.Ele_Vocabulary).item(0);
				vocabulary.setRootNode(rootNode);
				vocabulary.setPath(currentUri);
				parseInfo(vocabulary);
				if (vocabularies.get(currentUri) != null) {
					throw new ParsingException("Cycle reference of vocabularies detected: " + currentUri);
				}
				vocabularies.put(currentUri, vocabulary);
			} else {
				stop = true;
			}
			if (previous != null) {
				previous.getUserContainer().setBaseContainer(vocabulary.getUserContainer());
				previous.getDataContainer().setBaseContainer(vocabulary.getDataContainer());
			}
			if (stop) {
				break;
			}
			previous = vocabulary;
			currentUri = vocabulary.getBase();
		}
		vocabulary = vocabularies.get(uri);
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
			if (Ele_Vocabulary_Info.equals(name)) {
				vocabulary.getInfo().parse(node);
				return;
			}
		}
	}

	private void parseContainers() throws Exception {
		for (Vocabulary vocabulary : vocabularies.values()) {
			if (!vocabulary.isResolved()) {
				Node root = vocabulary.getRootNode();
				NodeList list = root.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					String name = node.getLocalName();
					if (Ele_Vocabulary_User_Category_Container.equals(name)) {
						vocabulary.getUserContainer().parse(node);
					} else if (Ele_Vocabulary_Data_Category_Container.equals(name)) {
						vocabulary.getDataContainer().parse(node);
					}
				}
			}
		}
	}

	/**
	 * Resolve all references (container and category) in containers
	 * 
	 * @param containers
	 * @throws ParsingException
	 */
	@SuppressWarnings({ "rawtypes" })
	private <T extends CategoryContainer> void buildReference(T container) throws ParsingException {
		resolveReference(container);
		checkDuplicates(container);
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
	private <T extends CategoryContainer> void resolveReference(CategoryContainer container)
			throws ParsingException {
		if (container.isResolved()) {
			return;
		}
		CategoryContainer baseContainer = container.getBaseContainer();
		if (baseContainer != null) {
			resolveReference(baseContainer);
		}
		// resolve parent reference of all categories
		for (Object obj : container.getCategories()) {
			Category category = (Category) obj;
			String parentId = category.getParentId();
			if (!parentId.isEmpty()) {
				Category parent = container.get(parentId);
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
