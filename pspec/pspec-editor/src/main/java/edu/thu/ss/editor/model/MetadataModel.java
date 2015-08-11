package edu.thu.ss.editor.model;

import edu.thu.ss.editor.hive.HiveConnection;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistry;

public class MetadataModel extends BaseModel {

	private XMLMetaRegistry registry;

	private HiveConnection connection;

	public MetadataModel(XMLMetaRegistry registry, String path) {
		super(path);
		this.registry = registry;
	}

	public MetadataModel(String path) {
		super(path);
	}

	public String getPath() {
		return path;
	}

	public void init(XMLMetaRegistry registry) {
		this.registry = registry;
	}

	public void connect(String host, String port, String username, String password) {
		//connection = new HiveConnection(host, port, username, password);
		//TODO test in local mode
		connection = new HiveConnection();
		connection.resolve(registry);
	}

	public XMLMetaRegistry getRegistry() {
		// TODO Auto-generated method stub
		return registry;
	}

}
