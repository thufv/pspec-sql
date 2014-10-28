package edu.thu.ss.lang.analyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SearchKey {
	int[] rules;

	public SearchKey(int... rules) {
		this.rules = rules;
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
public abstract class LevelwiseSearcher<T> {

	protected int maxLevel = Integer.MAX_VALUE;

	protected int defaultSize = 10;

	public void search() {
		int level = 1;
		List<SearchKey> currentLevel = new ArrayList<>(defaultSize);
		Map<SearchKey, T> currentIndex = new HashMap<>(defaultSize);
		initLevel(currentLevel, currentIndex);
		while (currentLevel.size() > 0 && level < maxLevel) {
			List<SearchKey> nextLevel = new ArrayList<>(defaultSize);
			Map<SearchKey, T> nextIndex = new HashMap<>(defaultSize);
			generateNextLevel(currentLevel, currentIndex, nextLevel, nextIndex, level);
			level++;
			currentLevel = nextLevel;
			currentIndex = nextIndex;
		}
	}

	/**
	 * main interface for implementation, process the new combination.
	 * 
	 * @param key
	 * @param currentIndex
	 * @return true/false decides whether key should be kept.
	 */
	protected abstract T process(SearchKey key, Map<SearchKey, T> currentIndex);

	protected abstract void initLevel(List<SearchKey> currentLevel, Map<SearchKey, T> currentIndex);

	private void generateNextLevel(List<SearchKey> currentLevel, Map<SearchKey, T> currentIndex,
			List<SearchKey> nextLevel, Map<SearchKey, T> nextIndex, int level) {
		int size = currentLevel.size();
		for (int i = 0; i < size; i++) {
			SearchKey key1 = currentLevel.get(i);
			for (int j = i + 1; j < size; j++) {
				SearchKey key2 = currentLevel.get(j);
				if (!key1.prefixEquals(key2)) {
					break;
				}
				SearchKey key = key1.combine(key2);
				if (!isValidKey(key, currentIndex)) {
					continue;
				}

				T t = process(key, currentIndex);
				if (t == null) {
					continue;
				}
				nextLevel.add(key);
				nextIndex.put(key, t);

			}

		}
	}

	private boolean isValidKey(SearchKey key, Map<SearchKey, T> currentIndex) {
		for (int i = 0; i < key.rules.length - 2; i++) {
			int tmp = key.rules[i];
			key.rules[i] = -1;
			if (!currentIndex.containsKey(key)) {
				return false;
			}
			key.rules[i] = tmp;
		}
		return true;
	}

}
