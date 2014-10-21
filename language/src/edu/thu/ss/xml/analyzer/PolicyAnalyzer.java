package edu.thu.ss.xml.analyzer;

import edu.thu.ss.xml.pojo.Policy;

public interface PolicyAnalyzer {

	public boolean analyze(Policy policy);

	public boolean stopOnError();

	public String errorMsg();

}
