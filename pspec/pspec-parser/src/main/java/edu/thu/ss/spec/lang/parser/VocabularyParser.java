package edu.thu.ss.spec.lang.parser;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.thu.ss.spec.lang.analyzer.VocabularyAnalyzer;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.VocabularyErrorType;
import edu.thu.ss.spec.lang.pojo.DataContainer;
import edu.thu.ss.spec.lang.pojo.Info;
import edu.thu.ss.spec.lang.pojo.UserContainer;
import edu.thu.ss.spec.lang.pojo.Vocabulary;
import edu.thu.ss.spec.manager.VocabularyManager;
import edu.thu.ss.spec.util.XMLUtil;

/**
 * parses and analyzes vocabulary
 * 
 * @author luochen
 * 
 */
public class VocabularyParser extends BaseParser implements ParserConstant {

	private static Logger logger = LoggerFactory.getLogger(VocabularyParser.class);

	/**
	 * parsed vocabulary in current instance
	 */
	protected Map<URI, Vocabulary> vocabularies = new HashMap<>();

	protected Vocabulary vocabulary = new Vocabulary();

	protected Map<String, UserContainer> userContainers = new HashMap<>();

	protected Map<String, DataContainer> dataContainers = new HashMap<>();

	protected VocabularyAnalyzer analyzer;

	public Vocabulary parse(String path) throws InvalidVocabularyException {

		uri = XMLUtil.toUri(path);
		if (VocabularyManager.containsVocab(uri)) {
			return VocabularyManager.getVocab(uri);
		}

		loadVocabularies(uri);

		parseContainers();

		// semantic analysis
		analyzer = new VocabularyAnalyzer(table);
		error = analyzer.analyze(vocabulary.getUserContainer(), false) || error;
		error = analyzer.analyze(vocabulary.getDataContainer(), false) || error;

		if (forceRegister || !error) {
			registerVocabularies();
		}
		return vocabulary;
	}

	private void registerVocabularies() {
		for (Vocabulary vocab : vocabularies.values()) {
			if (!vocab.isResolved()) {
				vocab.setResolved(true);
				vocab.setRootNode(null);
				VocabularyManager.add(vocab);
			}
		}
	}

	/**
	 * load all referred {@link Vocabulary} (chain)
	 * 
	 * @param uri
	 * @throws Exception
	 */
	private void loadVocabularies(URI uri) throws InvalidVocabularyException {
		Vocabulary previous = null;
		URI currentUri = uri;
		boolean stop = false;
		while (currentUri != null && !stop) {
			Vocabulary vocabulary = VocabularyManager.getVocab(currentUri);
			if (vocabulary == null) {
				vocabulary = new Vocabulary();
				Document document = null;
				try {
					document = XMLUtil.parseDocument(currentUri, Privacy_Schema_Location);
				} catch (Exception e) {
					throw new InvalidVocabularyException(uri, e);
				}
				Node rootNode = document.getElementsByTagName(ParserConstant.Ele_Vocabulary).item(0);
				if (rootNode == null) {
					throw new InvalidVocabularyException(uri, null);
				}
				vocabulary.setRootNode(rootNode);
				vocabulary.setPath(currentUri);

				parseInfo(vocabulary);
			}
			vocabularies.put(currentUri, vocabulary);
			if (previous != null) {
				previous.setBaseVocabulary(vocabulary);
			}

			previous = vocabulary;
			currentUri = vocabulary.getBase();
			//check cycle reference
			if (currentUri != null && vocabularies.get(currentUri) != null) {
				//fix problem
				vocabulary.setBase(null);
				logger.error("Cycle reference of vocabularies detected: " + currentUri);
				table.onVocabularyError(VocabularyErrorType.Cycle_Reference, null, null);
				error = true;
				break;
			}

		}
		vocabulary = vocabularies.get(uri);

	}

	/**
	 * parse {@link Info} from {@link Vocabulary}
	 * 
	 * @param vocabulary
	 * @throws Exception
	 */
	private void parseInfo(Vocabulary vocabulary) {
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

	private void parseContainers() {
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

}
