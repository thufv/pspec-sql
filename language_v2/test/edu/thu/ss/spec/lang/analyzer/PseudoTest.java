package edu.thu.ss.spec.lang.analyzer;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class PseudoTest {

	@Test
	public void test() {

		int[] policy = initPolicy();
		boolean[][] imply = initImplication();
		boolean[] removed = new boolean[10];

		for (int i = 0; i < policy.length; i++) {
			int r = policy[i];
			if (removed[r]) {
				continue;
			}
			List<Integer> list = new LinkedList<>();
			int rp = r;
			for (int j = i+1; j < policy.length; j++) {
				int rt = policy[j];
				if (removed[rt]) {
					continue;
				}
				if (rt == rp) {
					continue;
				}
				if (imply[rt][rp]) {
					list.add(rp);
					rp = rt;
				}
				if (imply[rp][rt]) {
					list.add(rt);
				}
			}
			for (int l : list) {
				System.out.println(l + "  " + rp);
				removed[l] = true;
			}
		}
	}

	private int[] initPolicy() {
		int[] policy = new int[10];
		for (int i = 0; i < policy.length; i++) {
			policy[i] = i;
		}
		return policy;
	}

	private boolean[][] initImplication() {
		boolean[][] imply = new boolean[10][10];
		imply[0][1] = true;
		imply[5][1] = true;
		imply[4][0] = true;
		imply[6][5] = true;

		return imply;
	}

}
