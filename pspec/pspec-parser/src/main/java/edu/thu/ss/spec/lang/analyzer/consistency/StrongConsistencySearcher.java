package edu.thu.ss.spec.lang.analyzer.consistency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.thu.ss.spec.lang.parser.event.EventTable;
import edu.thu.ss.spec.lang.parser.event.PSpecListener.AnalysisType;
import edu.thu.ss.spec.lang.pojo.Action;
import edu.thu.ss.spec.lang.pojo.DataCategory;
import edu.thu.ss.spec.lang.pojo.DataRef;
import edu.thu.ss.spec.lang.pojo.ExpandedRule;
import edu.thu.ss.spec.lang.pojo.UserCategory;
import edu.thu.ss.spec.z3.Z3StrongConsistencySolver;

public class StrongConsistencySearcher extends LevelwiseSearcher {

	private ExpandedRule seed;
	private List<ExpandedRule> candidates;
	private List<ExpandedRule> sortedRules;
	private Map<SearchKey, Set<LeafAssociation>> cache = new HashMap<>();
	private static Z3StrongConsistencySolver z3Util = null;

	private EventTable table;

	private static Logger logger = LoggerFactory.getLogger(StrongConsistencySearcher.class);

	public class Leaf {
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

	public class LeafAssociation {

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

	public StrongConsistencySearcher(EventTable table) {
		this.table = table;
		if (z3Util == null) {
			z3Util = new Z3StrongConsistencySolver();
		}
	}

	public void init(ExpandedRule seed, List<ExpandedRule> candidates) {
		this.seed = seed;
		this.candidates = candidates;
		cache.clear();
		z3Util.setSeedRule(seed);
		conflicts = 0;
	}

	@Override
	protected boolean process(SearchKey key) {
		Set<UserCategory> users = null;
		ExpandedRule[] rules = new ExpandedRule[key.index.length];
		for (int i = 0; i < key.index.length; i++) {
			ExpandedRule rule = sortedRules.get(key.index[i]);
			rules[i] = rule;
			if (users == null) {
				users = new HashSet<>(rule.getUsers());
			} else {
				users.retainAll(rule.getUsers());
				if (users.size() == 0) {
					return false;
				}
			}
		}

		assert (key.index.length > 1);
		int temp = key.getLast();
		key.setLast(-1);
		Set<LeafAssociation> list = cache.get(key);
		if (list == null) {
			logger.error("Invalid cache");
			return false;
		}
		key.setLast(temp);
		ExpandedRule rule = sortedRules.get(key.index[key.index.length - 1]);
		list = filterLeafAssociation(list, rule);
		if (list.size() == 0) {
			return false;
		}

		boolean result = z3Util.isSatisfiable(z3Util.buildExpression(list, rules));
		if (!result) {
			logger.warn("Possible conflicts when adding:" + sortedRules.get(key.getLast()).getId());
			conflicts++;
			
			ExpandedRule[] newRules = Arrays.copyOf(rules, rules.length + 1);
			newRules[newRules.length - 1] = seed;
			table.onAnalysis(AnalysisType.Enhanced_Strong_Consistency, newRules);
		} else {
			cache.put(key, list);
		}
		return result;
	}

	@Override
	protected void initLevel(Set<SearchKey> currentLevel) {
		sortedRules = new ArrayList<>(candidates);
		Collections.sort(sortedRules, new Comparator<ExpandedRule>() {
			@Override
			public int compare(ExpandedRule o1, ExpandedRule o2) {
				return Integer.compare(o1.getDimension(), o2.getDimension());
			}
		});

		int[] index = new int[sortedRules.size()];
		for (int i = 0; i < index.length; i++) {
			ExpandedRule rule = sortedRules.get(i);
			ExpandedRule[] rules = { rule };
			index[i] = sortedRules.indexOf(rule);
			Set<LeafAssociation> set = filterLeafAssociation(getLeafAssociation(seed), rule);
			if (z3Util.isSatisfiable(z3Util.buildExpression(set, rules))) {
				SearchKey key = new SearchKey(i);
				cache.put(key, set);
				currentLevel.add(key);
			} else {
				logger.warn("conflict between {} and {}", rule.getId(), seed.getId());
				conflicts++;

				ExpandedRule[] newRules = Arrays.copyOf(rules, rules.length + 1);
				newRules[newRules.length - 1] = seed;
				table.onAnalysis(AnalysisType.Strong_Consistency, newRules);
			}

		}
	}

	private Set<LeafAssociation> getLeafAssociation(ExpandedRule rule) {
		Set<LeafAssociation> set = new HashSet<>();
		if (seed.isSingle()) {
			Set<DataCategory> categories = rule.getDataRef().getMaterialized();
			for (DataCategory category : categories) {
				Leaf leaf = new Leaf(category, rule.getDataRef().getAction());
				if (leaf.belongTo(rule.getDataRef())) {
					LeafAssociation leafAssoc = new LeafAssociation(1);
					leafAssoc.addLeaf(leaf);
					set.add(leafAssoc);
				}
			}
		} else if (seed.isAssociation()) {
			LeafAssociation leafAssoc = new LeafAssociation(seed.getDimension());
			if (rule.isSingle()) {
				getLeafAssociation(rule.getDataRef(), leafAssoc, set, seed.getDimension(), 0, false);
			} else if (rule.isAssociation()) {
				boolean[] covered = new boolean[rule.getDimension()];
				Arrays.fill(covered, false);
				getLeafAssociation(rule.getAssociation().getDataRefs(), leafAssoc, set,
						seed.getDimension(), 0, covered);
			}
		}
		return set;
	}

	private void getLeafAssociation(DataRef dataRef, LeafAssociation leafAssoc,
			Set<LeafAssociation> set, int dim, int index, boolean cover) {
		if (index == dim) {
			if (cover) {
				set.add(leafAssoc);
			}
			return;
		}

		if (!cover) {
			DataRef dataRef1 = seed.getAssociation().getDataRefs().get(index);
			Set<DataCategory> categories = dataRef.getMaterialized();
			for (DataCategory category : categories) {
				Leaf leaf = new Leaf(category, dataRef1.getAction());
				if (leaf.belongTo(dataRef1)) {
					LeafAssociation temp = leafAssoc.clone();
					temp.addLeaf(leaf);
					getLeafAssociation(dataRef, temp, set, dim, index + 1, true);
				}
			}
		}

		DataRef dataRef1 = seed.getAssociation().getDataRefs().get(index);
		Set<DataCategory> categories = dataRef1.getMaterialized();
		for (DataCategory category : categories) {
			Leaf leaf = new Leaf(category, dataRef1.getAction());
			LeafAssociation temp = leafAssoc.clone();
			temp.addLeaf(leaf);
			getLeafAssociation(dataRef, temp, set, dim, index + 1, false);
		}
	}

	private void getLeafAssociation(List<DataRef> dataRefs, LeafAssociation leafAssoc,
			Set<LeafAssociation> set, int dim, int index, boolean[] covered) {
		if (index == dim) {
			for (int i = 0; i < covered.length; i++) {
				if (!covered[i]) {
					return;
				}
			}
			set.add(leafAssoc);
			return;
		} else {
			DataRef dataRef1 = seed.getAssociation().getDataRefs().get(index);
			boolean match = false;
			for (int i = 0; i < dataRefs.size(); i++) {
				if (covered[i]) {
					continue;
				}

				DataRef dataRef2 = dataRefs.get(i);
				if (!dataRef1.getAction().ancestorOf(dataRef2.getAction())
						&& !dataRef2.getAction().ancestorOf(dataRef1.getAction())) {
					continue;
				}

				Set<DataCategory> categories = dataRef2.getMaterialized();
				for (DataCategory category : categories) {
					if (dataRef1.getMaterialized().contains(category)) {
						match = true;
						covered[i] = true;
						Leaf leaf = new Leaf(category, dataRef2.getAction());
						LeafAssociation temp = leafAssoc.clone();
						temp.addLeaf(leaf);
						getLeafAssociation(dataRefs, temp, set, dim, index + 1, covered);
						covered[i] = false;
					}
				}

				if (match) {
					break;
				}
			}

			for (DataCategory category : dataRef1.getMaterialized()) {
				Leaf leaf = new Leaf(category, dataRef1.getAction());
				LeafAssociation temp = leafAssoc.clone();
				temp.addLeaf(leaf);
				getLeafAssociation(dataRefs, temp, set, dim, index + 1, covered);
			}
		}
	}

	private Set<LeafAssociation> filterLeafAssociation(Set<LeafAssociation> set, ExpandedRule rule) {
		Iterator<LeafAssociation> it = set.iterator();
		while (it.hasNext()) {
			LeafAssociation leafAssoc = (LeafAssociation) it.next();
			if (!leafAssoc.belongTo(rule)) {
				it.remove();
			}
		}
		return set;
	}

}
