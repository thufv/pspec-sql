package edu.thu.ss.spec.lang.pojo;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * class for desensitize operation
 * @author luochen
 *
 */
public class DesensitizeOperation {

	private static int globalId = 0;
	private static Map<String, DesensitizeOperation> operations = new HashMap<>();

	public static DesensitizeOperation parse(Node opNode) {
		String name = opNode.getTextContent();
		return get(name);
	}

	public static DesensitizeOperation get(String name) {
		name = name.toLowerCase();
		DesensitizeOperation op = operations.get(name);
		if (op == null) {
			op = new DesensitizeOperation(name);
			operations.put(name, op);
		}
		return op;
	}

	protected final String name;
	protected final int id;

	private DesensitizeOperation(String name) {
		id = (globalId++);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DesensitizeOperation other = (DesensitizeOperation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
