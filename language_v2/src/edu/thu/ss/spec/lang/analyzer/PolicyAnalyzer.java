package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.pojo.Policy;

public interface PolicyAnalyzer {

	public boolean analyze(Policy policy);

	public boolean stopOnError();

	public String errorMsg();

}
