package edu.thu.ss.spec.lang.analyzer.redundancy;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.util.GlobalInclusionUtil;

/**
 * performs global redundancy analysis
 * depend on {@link GlobalExpander}
 * @author luochen
 *
 */
public class GlobalRedundancyAnalyzer extends BaseRedundancyAnalyzer {

	/**
	 * {@link BaseRedundancyAnalyzer#instance} is set as {@link GlobalInclusionUtil#instance} 
	 */
	public GlobalRedundancyAnalyzer(EventTable table) {
		super(table);
		this.instance = GlobalInclusionUtil.instance;

	}
}
