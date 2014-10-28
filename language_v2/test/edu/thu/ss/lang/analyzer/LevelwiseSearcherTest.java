package edu.thu.ss.lang.analyzer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LevelwiseSearcherTest {

	@Test
	public void testKey() {
		SearchKey key1 = new SearchKey(1, 2, 3);
		SearchKey key2 = new SearchKey(2, 3, 4);

		SearchKey key3 = new SearchKey(1, 2, 3, 4);

		Map<SearchKey, Integer> map = new HashMap<>();
		map.put(key1, 1);
		map.put(key2, 2);

		key3.rules[3] = -1;
		assertEquals((Integer) 1, map.get(key3));

		key3.rules[3] = 4;
		key3.rules[0] = -1;
		assertEquals((Integer) 2, map.get(key3));
	}

	@Test
	public void testAnalyzer() {
		PrintSearcher print = new PrintSearcher(500, 3);
		print.search();
	}

	class PrintSearcher extends LevelwiseSearcher<Integer> {
		private int num;
		private int[] exclude = new int[] { 1, 2 };

		public PrintSearcher(int num, int max) {
			this.num = num;
			this.maxLevel = max;
		}

		@Override
		protected void initLevel(List<SearchKey> currentLevel, Map<SearchKey, Integer> currentIndex) {
			for (int i = 1; i <= num; i++) {
				SearchKey key = new SearchKey(i);
				currentLevel.add(key);
				currentIndex.put(key, 0);
			}
		}

		@Override
		protected Integer process(SearchKey key, Map<SearchKey, Integer> currentIndex) {
			if (Arrays.equals(key.rules, exclude)) {
				return null;
			}
			//System.out.println(key);
			return 0;
		}

	}
}
