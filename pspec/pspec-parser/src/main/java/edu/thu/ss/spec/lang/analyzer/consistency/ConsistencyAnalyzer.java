package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.thu.ss.spec.lang.analyzer.BasePolicyAnalyzer;
import edu.thu.ss.spec.lang.analyzer.stat.AnalyzerStat;
import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.Policy;

public abstract class ConsistencyAnalyzer extends BasePolicyAnalyzer {
	
	static public class Leaf {
		public Action action;
		public DataCategory category;

		public Leaf(DataCategory category, Action action) {
			this.action = action;
			this.category = category;
		}

		public boolean belongTo(ExpandedRule rule) {
			if (rule.isSingle()) {
				return belongTo(rule.getDataRef());
			} else if (rule.isAssociation()) {
				List<DataRef> dataRefs = rule.getAssociation().getDataRefs();
				for (DataRef dataRef : dataRefs) {
					if (belongTo(dataRef)) {
						return true;
					}
				}
			}
			return false;
		}

		public boolean belongTo(DataRef dataRef) {
			if (!action.ancestorOf(dataRef.getAction()) && !dataRef.getAction().ancestorOf(action)) {
				return false;
			}

			Set<DataCategory> categories = dataRef.getMaterialized();
			if (categories.contains(category)) {
				return true;
			}
			return false;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Leaf other = (Leaf) obj;

			if (this.category != other.category) {
				return false;
			}

			if (this.action != other.action) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return category.getId().toString();
		}
	}

	static public class LeafAssociation {

		public int length = 0;

		public Leaf[] leafAssociation;

		public LeafAssociation(int dim) {
			leafAssociation = new Leaf[dim];
		}

		public void addLeaf(Leaf leaf) {
			leafAssociation[length++] = leaf;
		}

		public boolean belongTo(ExpandedRule rule) {
			if (rule.isSingle()) {
				DataRef dataRef = rule.getDataRef();
				for (Leaf leaf : leafAssociation) {
					if (leaf.belongTo(dataRef)) {
						return true;
					}
				}
				return false;
			} else if (rule.isAssociation()) {
				List<DataRef> dataRefs = rule.getAssociation().getDataRefs();
				boolean[] matches = new boolean[leafAssociation.length];
				for (int i = 0; i < matches.length; i++) {
					matches[i] = false;
				}
				for (DataRef dataRef : dataRefs) {
					boolean match = false;
					for (int i = 0; i < leafAssociation.length; i++) {
						Leaf leaf = leafAssociation[i];
						if (!matches[i] && leaf.belongTo(dataRef)) {
							matches[i] = true;
							match = true;
							break;
						}
					}
					if (!match) {
						return false;
					}
				}
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("LeafAssociation:");
			for (Leaf leaf : leafAssociation) {
				sb.append(" " + leaf + " ");
			}
			return sb.toString();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LeafAssociation other = (LeafAssociation) obj;

			if (this.leafAssociation.length != other.leafAssociation.length) {
				return false;
			}

			for (int i = 0; i < this.leafAssociation.length; i++) {
				if (this.leafAssociation[i] != other.leafAssociation[i]) {
					return false;
				}
			}
			return true;
		}

		public LeafAssociation clone() {
			LeafAssociation copy = new LeafAssociation(this.leafAssociation.length);
			copy.length = this.length;
			copy.leafAssociation = this.leafAssociation.clone();
			return copy;
		}
	}
	
	public ConsistencyAnalyzer(EventTable table) {
		super(table);
	}

	public abstract boolean analyze(List<ExpandedRule> rules);

	@Override
	public boolean analyze(Policy policy) {
		List<ExpandedRule> rules = policy.getExpandedRules();
		List<ExpandedRule> restrictionRules = new ArrayList<>();
		List<ExpandedRule> filterRules = new ArrayList<>();

		for (ExpandedRule rule : rules) {
			if (rule.isFilter()) {
				filterRules.add(rule);
			} else if (!rule.getRestriction().isForbid()) {
				restrictionRules.add(rule);
			}
		}

		analyze(restrictionRules);
		//analyze(filterRules);
		return false;
	}
	

	
	@Override
	public boolean analyze(Policy policy, AnalyzerStat stat, int n) {
		return analyze(policy);
	}
}
