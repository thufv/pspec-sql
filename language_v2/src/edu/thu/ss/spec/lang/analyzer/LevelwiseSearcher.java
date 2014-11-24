package edu.thu.ss.spec.lang.analyzer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Abstract class performing level-wise search on rules.
 * 
 * @author luochen
 * 
 */
public abstract class LevelwiseSearcher {

	public static class SearchKey {
		public int[] index;

		public SearchKey(int... rules) {
			this.index = rules;
		}

		public int getFirst() {
			return index[0];
		}

		public void setFirst(int value) {
			index[0] = value;
		}

		public int getLast() {
			return index[index.length - 1];
		}

		public void setLast(int value) {
			index[index.length - 1] = value;
		}

		@Override
		public int hashCode() {
			if (index == null)
				return 0;
			int result = 1;
			for (int element : index) {
				result = (element >= 0) ? (31 * result + element) : result;
			}
			return result;
		}

		public boolean prefixEquals(SearchKey other) {
			if (this.index.length != other.index.length) {
				return false;
			}
			for (int i = 0; i < index.length - 1; i++) {
				if (index[i] != other.index[i]) {
					return false;
				}
			}
			return true;
		}

		public SearchKey combine(SearchKey other) {
			int[] newRules = Arrays.copyOf(index, index.length + 1);
			newRules[newRules.length - 1] = other.index[other.index.length - 1];
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
			if (this.index.length == other.index.length + 1) {
				rule1 = this.index;
				rule2 = other.index;
			} else if (this.index.length == other.index.length - 1) {
				rule1 = other.index;
				rule2 = this.index;
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
			for (int i : index) {
				sb.append(i);
				sb.append(' ');
			}
			return sb.toString();
		}
	}

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
		for (int i = 0; i < key.index.length - 2; i++) {
			int tmp = key.index[i];
			key.index[i] = -1;
			if (!currentIndex.contains(key)) {
				return false;
			}
			key.index[i] = tmp;
		}
		return true;
	}

}
