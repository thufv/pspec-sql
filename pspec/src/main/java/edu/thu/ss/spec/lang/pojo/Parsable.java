package edu.thu.ss.spec.lang.pojo;

import org.w3c.dom.Node;

/**
 * interface for all parsable xml language element class 
 * @author luochen
 *
 */
public interface Parsable {

	public void parse(Node node);

}
