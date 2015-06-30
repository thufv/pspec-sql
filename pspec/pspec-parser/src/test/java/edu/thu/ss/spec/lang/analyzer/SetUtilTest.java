package edu.thu.ss.spec.lang.analyzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.thu.ss.spec.util.SetUtil;
import edu.thu.ss.spec.util.SetUtil.SetRelation;

public class SetUtilTest {

	public void testRelation() {
		Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(1, 2));
		Set<Integer> set3 = new HashSet<>(Arrays.asList(2, 3));
		Set<Integer> set4 = new HashSet<>(Arrays.asList(3));

		assertEquals(SetRelation.contain, SetUtil.relation(set1, set2));
		assertEquals(SetRelation.intersect, SetUtil.relation(set2, set3));
		assertEquals(SetRelation.disjoint, SetUtil.relation(set2, set4));
	}

	public void testMergeList() {
		List<Set<Integer>> list = new ArrayList<>();

		SetUtil.mergeOperations(list, null);
		assertEquals(1, list.size());
		assertEquals(null, list.get(0));

		SetUtil.mergeOperations(list, new HashSet<>(Arrays.asList(1, 2, 3)));
		assertEquals(1, list.size());
		assertEquals(new HashSet<>(Arrays.asList(1, 2, 3)), list.get(0));

		SetUtil.mergeOperations(list, null);
		assertEquals(1, list.size());
		assertEquals(new HashSet<>(Arrays.asList(1, 2, 3)), list.get(0));

		SetUtil.mergeOperations(list, new HashSet<>(Arrays.asList(2, 3, 4)));
		assertEquals(2, list.size());
		assertEquals(new HashSet<>(Arrays.asList(1, 2, 3)), list.get(0));
		assertEquals(new HashSet<>(Arrays.asList(2, 3, 4)), list.get(1));

		SetUtil.mergeOperations(list, new HashSet<>(Arrays.asList(2, 3)));
		assertEquals(1, list.size());
		assertEquals(new HashSet<>(Arrays.asList(2, 3)), list.get(0));
	}

	@Test
	public void testUri() throws URISyntaxException {
		String path = "res/meta.xsd";
		File file = new File(path);
		if(file.exists()){
			path = file.getAbsolutePath();
		}
		URI uri = new URI(path).normalize();

		System.out.println(uri);
	}

}
