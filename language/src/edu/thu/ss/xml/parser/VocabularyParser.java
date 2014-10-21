package edu.thu.ss.xml.parser;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.pojo.CategoryContainer;
import edu.thu.ss.xml.pojo.DataCategory;
import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.HierarchicalObject;
import edu.thu.ss.xml.pojo.Info;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.Vocabulary;

public class VocabularyParser implements ParserConstant {

	protected Map<URI, Vocabulary> vocabularies;

	protected List<UserCategoryContainer> userContainers;

	protected List<DataCategoryContainer> dataContainers;

	protected UserCategoryContainer userContainer;

	protected DataCategoryContainer dataContainer;

	protected boolean error = false;

	private static Logger logger = LoggerFactory.getLogger(VocabularyParser.class);

	public Vocabulary parse(String path, String user, String data) throws Exception {
		init();
		loadVocabularies(path);

		uniqueCategoriesIdCheck();
		// syntactic parse
		parseUser(path, user);
		parseData(path, data);

		// semantic analysis
		analyzeReference(userContainers, userContainer);
		analyzeReference(dataContainers, dataContainer);

		propogateDesensitizaOperation(dataContainer);
		if (error) {
			throw new ParsingException("Fail to parse vocabularies, see error messages above");
		}

		Vocabulary vocabulary = new Vocabulary();
		vocabulary.addDataCategories(data, dataContainer);
		vocabulary.addUserCategories(user, userContainer);
		return vocabulary;
	}

	private void init() {
		this.vocabularies = new HashMap<>();
		this.userContainers = new ArrayList<>();
		this.dataContainers = new ArrayList<>();
		this.userContainer = new UserCategoryContainer();
		this.dataContainer = new DataCategoryContainer();
		this.error = false;
	}

	private void loadVocabularies(String path) throws Exception {
		while (path != null) {
			Vocabulary vocabulary = new Vocabulary();
			Document document = XMLUtil.parseDocument(path, Schema_Location);
			Node rootNode = document.getElementsByTagName(ParserConstant.Ele_Vocabulary).item(0);
			vocabulary.setRootNode(rootNode);
			parseBase(vocabulary);
			if (getVocabulary(path) != null) {
				throw new ParsingException("Cycle reference of vocabularies detected: " + path);
			}
			putVocabulary(path, vocabulary);
			path = vocabulary.getBase();
		}
	}

	private void uniqueCategoriesIdCheck() throws ParsingException {
		uniqueIdCheck(Ele_Vocabulary_User_Category_Container);
		uniqueIdCheck(Ele_Vocabulary_Data_Category_Container);
	}

	private void uniqueIdCheck(String element) throws ParsingException {
		Set<String> ids = new HashSet<>();
		for (Vocabulary vocabulary : vocabularies.values()) {
			Node rootNode = vocabulary.getRootNode();
			NodeList list = rootNode.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				String name = node.getLocalName();
				if (element.equals(name)) {
					String id = XMLUtil.getAttrValue(node, Attr_Id);
					if (ids.contains(id)) {
						throw new ParsingException("Duplicate " + element + " id detected: " + id);
					}
					ids.add(id);
				}
			}
		}

	}

	private void parseUser(String path, String user) throws Exception {
		Vocabulary vocabulary = getVocabulary(path);
		String base = vocabulary.getBase();
		while (user != null) {
			UserCategoryContainer container = parseUserCategories(vocabulary, user);
			if (container == null) {
				if (base != null) {
					vocabulary = getVocabulary(base);
					base = vocabulary.getBase();
				} else {
					throw new ParsingException("Fail to locate UserCategories '" + user + "'");
				}
			} else {
				userContainers.add(container);
				user = container.getBase();
			}
		}
	}

	private void parseData(String path, String data) throws Exception {
		Vocabulary vocabulary = getVocabulary(path);
		String base = vocabulary.getBase();
		while (data != null) {
			DataCategoryContainer container = parseDataCategories(vocabulary, data);
			if (container == null) {
				if (base != null) {
					vocabulary = getVocabulary(base);
					base = vocabulary.getBase();
				} else {
					throw new ParsingException("Fail to locate DataCategories '" + data + "'");
				}
			} else {
				dataContainers.add(container);
				data = container.getBase();
			}
		}
	}

	private void parseBase(Vocabulary vocabulary) {
		Node root = vocabulary.getRootNode();
		NodeList list = root.getChildNodes();
		String base = XMLUtil.getAttrValue(root, Attr_Vocabulary_Base);
		vocabulary.setBase(base);
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

	private UserCategoryContainer parseUserCategories(Vocabulary vocabulary, String user) {
		Node root = vocabulary.getRootNode();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Vocabulary_User_Category_Container.equals(name)) {
				String id = XMLUtil.getAttrValue(node, Attr_Id);
				if (id.equals(user)) {
					UserCategoryContainer container = new UserCategoryContainer();
					container.parse(node);
					return container;
				}
			}
		}
		return null;
	}

	private DataCategoryContainer parseDataCategories(Vocabulary vocabulary, String data) {
		Node root = vocabulary.getRootNode();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Vocabulary_Data_Category_Container.equals(name)) {
				String id = XMLUtil.getAttrValue(node, Attr_Id);
				if (id.equals(data)) {
					DataCategoryContainer container = new DataCategoryContainer();
					container.parse(node);
					return container;
				}
			}
		}
		return null;
	}

	private void propogateDesensitizaOperation(DataCategoryContainer data) {
		List<DataCategory> roots = data.getRoot();
		for (DataCategory category : roots) {
			category.inheritDesensitizeOperation();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void analyzeReference(List list, CategoryContainer result) {
		for (int i = 0; i < list.size(); i++) {
			CategoryContainer container = (CategoryContainer) list.get(i);
			Map<String, Object> map = container.getContainer();
			for (String id : map.keySet()) {
				if (result.getContainer().containsKey(id)) {
					logger.error("Duplicate category id detected: " + id);
					error = true;
				}
				result.set(id, (HierarchicalObject) map.get(id));
			}
		}
		Map<String, Object> map = result.getContainer();
		for (String id : map.keySet()) {
			HierarchicalObject obj = (HierarchicalObject) map.get(id);
			String parentId = obj.getParentId();
			if (parentId == null) {
				result.getRoot().add(obj);
			} else {
				HierarchicalObject parent = (HierarchicalObject) map.get(parentId);
				if (parent == null) {
					logger.error("Fail to find parent for category: " + id + " parentId: " + parentId);
					error = true;
				}
				parent.buildRelation(obj);
			}
		}
		if (result.getRoot().size() == 0 && map.size() > 0) {
			logger.error("Cycle cateogory reference detected, please fix.");
			error = true;
		}
	}

	private Vocabulary getVocabulary(String path) throws Exception {
		URI uri = URI.create(path);
		uri = uri.normalize();
		Vocabulary vocabulary = vocabularies.get(uri);
		return vocabulary;
	}

	private void putVocabulary(String path, Vocabulary vocabulary) {
		URI uri = URI.create(path);
		uri = uri.normalize();
		vocabularies.put(uri, vocabulary);
	}

}
