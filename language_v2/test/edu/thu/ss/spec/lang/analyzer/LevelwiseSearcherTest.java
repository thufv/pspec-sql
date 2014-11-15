package edu.thu.ss.spec.lang.analyzer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import edu.thu.ss.spec.lang.analyzer.LevelwiseSearcher;
import edu.thu.ss.spec.lang.analyzer.SearchKey;

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
		PrintSearcher print = new PrintSearcher(5, Integer.MAX_VALUE);
		print.search();
	}

	class PrintSearcher extends LevelwiseSearcher {
		private int num;
		private int[] exclude = new int[] { 1, 2 };

		public PrintSearcher(int num, int max) {
			this.num = num;
			this.maxLevel = max;
		}

		@Override
		protected void initLevel(Set<SearchKey> currentLevel) {
			for (int i = 1; i <= num; i++) {
				SearchKey key = new SearchKey(i);
				currentLevel.add(key);
			}
		}

		@Override
		protected boolean process(SearchKey key) {
			if (Arrays.equals(key.rules, exclude)) {
				//	return true;
			}
			System.out.println(key);
			return true;
		}

	}
}
