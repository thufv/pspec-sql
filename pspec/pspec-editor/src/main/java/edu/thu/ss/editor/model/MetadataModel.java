package edu.thu.ss.editor.model;

import edu.thu.ss.editor.PSpecEditor;
import edu.thu.ss.editor.hive.HiveConnection;
import edu.thu.ss.editor.view.MetadataView;
import edu.thu.ss.spec.meta.xml.XMLMetaRegistry;

public class MetadataModel extends BaseModel {

	private XMLMetaRegistry registry;

	private HiveConnection connection;
	
	class ConnectThread extends Thread {
		private MetadataModel model;

		public ConnectThread(MetadataModel model) {
			this.model = model;
		}

		public void run() {
			if (connection != null) {
				PSpecEditor.getInstance().getDisplay().syncExec(new Runnable() {
					@Override
					public void run() {
						MetadataView view = PSpecEditor.getInstance().getMetadataView(model);
						view.updataSchemaInfo();
					}
				});
				return;
			}
			connection = new HiveConnection();
			connection.resolve(registry);
			PSpecEditor.getInstance().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					MetadataView view = PSpecEditor.getInstance().getMetadataView(model);
					view.updataSchemaInfo();
				}
			});
		}
	}

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
		Thread connect = new ConnectThread(this);
		connect.start();
	}

	public XMLMetaRegistry getRegistry() {
		return registry;
	}

}
