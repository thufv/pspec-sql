package edu.thu.ss.lang.analyzer;


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
