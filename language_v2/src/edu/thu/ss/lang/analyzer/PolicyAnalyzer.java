package edu.thu.ss.lang.analyzer;

import edu.thu.ss.lang.pojo.Policy;

public interface PolicyAnalyzer {

	public boolean analyze(Policy policy);

	public boolean stopOnError();

	public String errorMsg();

}
