package edu.thu.ss.spec.z3;

import org.slf4j.Logger;

import com.microsoft.z3.Context;
import com.microsoft.z3.Symbol;
import com.microsoft.z3.Z3Exception;


public abstract class Z3Consistency {
	protected Logger logger;

	protected Context context = null;
	protected Symbol[] allSymbols = null;
	protected int[] varIndex = null;
	protected int[] dummyIndex = null;
	
	protected void init(int size) {
		try {
			context = new Context();
			allSymbols = new Symbol[size];
			varIndex = new int[size];
			dummyIndex = new int[size];
			for (int i = 0; i < size; i++) {
				allSymbols[i] = context.mkSymbol("p" + i);
				dummyIndex[i] = i;
			}
		} catch (Z3Exception e) {
			logger.error("Fail to initialize Z3Context.", e);
		}
	}
	
}
