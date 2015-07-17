package edu.thu.ss.spec.lang.analyzer.redundancy;

import edu.thu.ss.spec.lang.analyzer.RuleExpander;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.util.InclusionUtil;

/**
 * performs redundancy analysis in a local manner
 * depend on{@link RuleExpander}
 * @author luochen
 *
 */
public class LocalRedundancyAnalyzer extends BaseRedundancyAnalyzer {

	public LocalRedundancyAnalyzer(EventTable table) {
		super(table);
		this.instance = InclusionUtil.instance;
	}
}
