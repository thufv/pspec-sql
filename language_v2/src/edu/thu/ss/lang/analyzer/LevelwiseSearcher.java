package edu.thu.ss.lang.analyzer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

class SearchKey {
	int[] rules;

	public SearchKey(int... rules) {
		this.rules = rules;
	}

	public int getFirst() {
		return rules[0];
	}

	public void setFirst(int value) {
		rules[0] = value;
	}

	public int getLast() {
		return rules[rules.length - 1];
	}

	public void setLast(int value) {
		rules[rules.length - 1] = value;
	}

	@Override
	public int hashCode() {
		if (rules == null)
			return 0;
		int result = 1;
		for (int element : rules) {
			result = (element >= 0) ? (31 * result + element) : result;
		}
		return result;
	}

	public boolean prefixEquals(SearchKey other) {
		if (this.rules.length != other.rules.length) {
			return false;
		}
		for (int i = 0; i < rules.length - 1; i++) {
			if (rules[i] != other.rules[i]) {
				return false;
			}
		}
		return true;
	}

	public SearchKey combine(SearchKey other) {
		int[] newRules = Arrays.copyOf(rules, rules.length + 1);
		newRules[newRules.length - 1] = other.rules[other.rules.length - 1];
		return new SearchKey(newRules);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchKey other = (SearchKey) obj;

		int[] rule1, rule2;
		if (this.rules.length == other.rules.length + 1) {
			rule1 = this.rules;
			rule2 = other.rules;
		} else if (this.rules.length == other.rules.length - 1) {
			rule1 = other.rules;
			rule2 = this.rules;
		} else {
			return false;
		}
		//rule1 is longer.
		boolean skip = false;
		for (int i = 0; i < rule1.length; i++) {
			if (rule1[i] < 0) {
				skip = true;
				continue;
			}
			if (rule1[i] != rule2[skip ? i - 1 : i]) {
				return false;
			}
		}
		return true;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i : rules) {
			sb.append(i);
			sb.append(' ');
		}
		return sb.toString();
	}
}

/**
 * Abstract class performing level-wise search on rules.
 * 
 * @author luochen
 * 
 */
public abstract class LevelwiseSearcher {

	protected int maxLevel = Integer.MAX_VALUE;

	protected int defaultSize = 10;

	protected SearchKey[] keys = new SearchKey[0];

	public void search() {
		int level = 1;
		Set<SearchKey> currentLevel = new LinkedHashSet<>(defaultSize);
		initLevel(currentLevel);
		while (currentLevel.size() > 0 && level < maxLevel) {
			beginLevel(level + 1);
			Set<SearchKey> nextLevel = new LinkedHashSet<>(defaultSize);
			processNextLevel(currentLevel, nextLevel, level);
			level++;
			currentLevel = nextLevel;
			endLevel(level);
		}
	}

	/**
	 * main interface for implementation, process the new combination.
	 * 
	 * @param key
	 * @param currentIndex
	 * @return true/false decides whether key should be kept.
	 */
	protected abstract boolean process(SearchKey key);

	protected abstract void initLevel(Set<SearchKey> currentLevel);

	protected void beginLevel(int level) {

	}

	protected void endLevel(int level) {

	}

	private void processNextLevel(Set<SearchKey> currentLevel, Set<SearchKey> nextLevel, int level) {
		int size = currentLevel.size();
		keys = currentLevel.toArray(keys);

		for (int i = 0; i < size; i++) {
			SearchKey key1 = keys[i];
			for (int j = i + 1; j < size; j++) {
				SearchKey key2 = keys[j];
				if (!key1.prefixEquals(key2)) {
					break;
				}
				SearchKey key = key1.combine(key2);
				if (!isValidKey(key, currentLevel)) {
					continue;
				}
				if (!process(key)) {
					continue;
				}
				nextLevel.add(key);
			}

		}
	}

	private boolean isValidKey(SearchKey key, Set<SearchKey> currentIndex) {
		for (int i = 0; i < key.rules.length - 2; i++) {
			int tmp = key.rules[i];
			key.rules[i] = -1;
			if (!currentIndex.contains(key)) {
				return false;
			}
			key.rules[i] = tmp;
		}
		return true;
	}

}
