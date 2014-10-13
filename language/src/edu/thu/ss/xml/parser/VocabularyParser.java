package edu.thu.ss.xml.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.xml.pojo.DataCategoryContainer;
import edu.thu.ss.xml.pojo.Info;
import edu.thu.ss.xml.pojo.UserCategoryContainer;
import edu.thu.ss.xml.pojo.Vocabulary;

public class VocabularyParser implements ParserConstant {

	protected Map<String, Vocabulary> vocabularies;

	protected List<UserCategoryContainer> userContainers;

	protected List<DataCategoryContainer> dataContainers;

	public void parse(String path, String user, String data) throws Exception {
		init();
		loadVocabularies(path);
		uniqueIdCheck();
		parseUser(path, user);
		parseData(path, data);
	}

	private void init() {
		this.vocabularies = new HashMap<>();
		this.userContainers = new ArrayList<>();
		this.dataContainers = new ArrayList<>();
	}

	private void loadVocabularies(String path) throws Exception {
		while (path != null) {
			Vocabulary vocabulary = new Vocabulary();
			Document document = XMLUtil.parse(path, Schema_Location);
			vocabulary.setDocument(document);
			parseBase(vocabulary);
			if (getVocabulary(path) != null) {
				throw new ParsingException("Cycle reference of vocabularies detected: " + path);
			}
			putVocabulary(path, vocabulary);
			path = vocabulary.getBase();
		}
	}

	private void uniqueIdCheck() {

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
					throw new ParsingException("Fail to location UserCategories '" + user + "'");
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
					throw new ParsingException("Fail to location DataCategories '" + data + "'");
				}
			} else {
				dataContainers.add(container);
				data = container.getBase();
			}
		}
	}

	private void parseBase(Vocabulary vocabulary) {
		Document document = vocabulary.getDocument();
		Node root = document.getFirstChild();
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
		Document document = vocabulary.getDocument();
		Node root = document.getFirstChild();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Vocabulary_User_Categories.equals(name)) {
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
		Document document = vocabulary.getDocument();
		Node root = document.getFirstChild();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			String name = node.getLocalName();
			if (Ele_Vocabulary_Data_Categories.equals(name)) {
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

	private Vocabulary getVocabulary(String path) throws Exception {
		Vocabulary vocabulary = vocabularies.get(path);

		return vocabulary;
	}

	private void putVocabulary(String path, Vocabulary vocabulary) {
		vocabularies.put(path, vocabulary);
	}

}
