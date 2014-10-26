package edu.thu.ss.xml.analyzer;

public abstract class BasePolicyAnalyzer implements PolicyAnalyzer {

	@Override
	public String errorMsg() {
		return "";
	}

	@Override
	public boolean stopOnError() {
		return false;
	}

}
