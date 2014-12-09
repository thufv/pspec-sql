package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.pojo.Policy;

/**
 * An interface for analyzing policy.
 * All implemented analyzers should be composed sequentially.
 * @author luochen
 *
 */
public interface PolicyAnalyzer {

	/**
	 * @param policy
	 * @return whether error occurred during analysis.
	 */
	public boolean analyze(Policy policy);

	/**
	 * whether stop policy parser when error occurred.
	 * @return boolean
	 */
	public boolean stopOnError();

	public String errorMsg();

}
