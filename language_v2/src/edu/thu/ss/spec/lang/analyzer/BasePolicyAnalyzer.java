package edu.thu.ss.spec.lang.analyzer;

/**
 * a default implementation for {@link PolicyAnalyzer}
 * @author luochen
 *
 */
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
