package edu.thu.ss.spec.lang.analyzer;

import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Policy;

/**
 * a default implementation for {@link IPolicyAnalyzer}
 * 
 * @author luochen
 * 
 */
public abstract class BasePolicyAnalyzer<T extends AnalyzerStat> implements IPolicyAnalyzer<T> {

	protected EventTable table;
	protected T stat;

	public BasePolicyAnalyzer() {
		this(EventTable.getDummy());
	}

	public BasePolicyAnalyzer(EventTable table) {
		this.table = table;
	}

	@Override
	public boolean analyze(Policy policy, T stat) throws Exception {
		this.stat = stat;
		return analyze(policy);
	}

	@Override
	public String errorMsg() {
		return "";
	}

	@Override
	public boolean stopOnError() {
		return false;
	}
}
