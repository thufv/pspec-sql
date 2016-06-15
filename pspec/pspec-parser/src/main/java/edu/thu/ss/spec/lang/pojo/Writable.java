package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Writable {

	public Element outputType(Document document, String name);

	public Element outputElement(Document document);

}
