package edu.thu.ss.lang.analyzer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.thu.ss.lang.util.SetUtil;
import edu.thu.ss.lang.util.SetUtil.SetRelation;

public class SetUtilTest {

	@Test
	public void testRelation() {
		Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(1, 2));
		Set<Integer> set3 = new HashSet<>(Arrays.asList(2, 3));
		Set<Integer> set4 = new HashSet<>(Arrays.asList(3));

		assertEquals(SetRelation.contain, SetUtil.relation(set1, set2));
		assertEquals(SetRelation.intersect, SetUtil.relation(set2, set3));
		assertEquals(SetRelation.disjoint, SetUtil.relation(set2, set4));

	}

}
