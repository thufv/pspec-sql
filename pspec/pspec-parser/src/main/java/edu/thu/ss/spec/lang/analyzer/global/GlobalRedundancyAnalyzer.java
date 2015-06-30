package edu.thu.ss.spec.lang.analyzer.global;

import edu.thu.ss.spec.lang.analyzer.BaseRedundancyAnalyzer;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.UserRef;
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
	public GlobalRedundancyAnalyzer() {
		this.instance = GlobalInclusionUtil.instance;
	}

	@Override
	protected void commit() {
		for (SimplificationLog log : logs) {
			ExpandedRule rule = log.rule;
			if (log.userRefs != null) {
				for (UserRef userRef : log.userRefs) {
					if (userRef.getExcludes().size() > 0) {
						continue;
					}
					for (UserRef ruleRef : rule.getUserRefs()) {
						if (ruleRef.getCategory().ancestorOf(userRef.getCategory())) {
							ruleRef.exclude(userRef.getCategory());
						}
					}
				}
			} else if (log.dataRef != null) {
				if (!rule.isGlobal()) {
					rule.getDataRef().getMaterialized().removeAll(log.dataRef.getMaterialized());
				} else {
					if (log.dataRef.getExcludes().size() == 0) {
						rule.getDataRef().exclude(log.dataRef.getCategory());
					}
				}
			}
		}
	}
}
