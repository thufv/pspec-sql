package edu.thu.ss.spec.lang.parser;

import java.net.URI;

import edu.thu.ss.spec.lang.parser.event.EventTable;

public abstract class BaseParser {

	protected boolean error = false;

	protected boolean forceRegister = false;

	protected EventTable table = EventTable.getDummy();

	protected URI uri;

	public boolean isError() {
		return error;
	}

	public void setForceRegister(boolean forceRegister) {
		this.forceRegister = forceRegister;
	}

	public void setEventTable(EventTable table) {
		this.table = table;
	}

}
