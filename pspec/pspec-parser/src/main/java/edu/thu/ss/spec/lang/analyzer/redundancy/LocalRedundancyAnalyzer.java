package edu.thu.ss.spec.lang.analyzer.redundancy;

import edu.thu.ss.spec.lang.analyzer.LocalExpander;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.UserRef;
import edu.thu.ss.spec.util.InclusionUtil;

/**
 * performs redundancy analysis in a local manner
 * depend on{@link LocalExpander}
 * @author luochen
 *
 */
public class LocalRedundancyAnalyzer extends BaseRedundancyAnalyzer {

	public LocalRedundancyAnalyzer() {
	}

	public LocalRedundancyAnalyzer(EventTable table) {
		super(table);
		this.instance = InclusionUtil.instance;
	}

	@Override
	protected void commit() {
		for (SimplificationLog log : logs) {
			ExpandedRule rule = log.rule;
			if (log.userRefs != null) {
				for (UserRef ref : log.userRefs) {
					rule.getUsers().removeAll(ref.getMaterialized());
				}
			} else if (log.dataRef != null) {
				rule.getDataRef().getMaterialized().removeAll(log.dataRef.getMaterialized());
			}
		}
	}
}
