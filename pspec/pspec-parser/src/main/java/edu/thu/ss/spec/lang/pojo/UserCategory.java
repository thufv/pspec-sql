package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.thu.ss.spec.lang.parser.ParserConstant;

/**
 * class for user category
 * @author luochen
 *
 */
public class UserCategory extends Category<UserCategory> {

	public UserCategory() {
	}

	public UserCategory(String id, String containerId) {
		this.id = id;
		this.containerId = containerId;
	}

	@Override
	public String toString() {
		return containerId + ":" + id;
	}

	@Override
	public Element outputElement(Document document) {
		return super.outputType(document, ParserConstant.Ele_Vocabulary_User_Category);
	}

}
