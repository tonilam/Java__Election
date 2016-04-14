package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.CandidateIndex;

public class CandidateIndexTests {
	
	private static class Sample {
		protected static final int SAMLL = 1;
		protected static final int MEDIUM = 5;
		protected static final int LARGE = 16;
		protected static final int DEFAULT = MEDIUM;
	}
	
	@Before @Test /* Initialize attributes' value */
	public void setUp() {
	}
	
	@Test /** Purpose of test:
	 		* 
	 		*/
	public void isAbleToDetermineRange() {
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MaxCandidates-1));
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MaxCandidates));
		assertEquals(false, CandidateIndex.inRange(CandidateIndex.MaxCandidates+1));
		
		assertEquals(false, CandidateIndex.inRange(CandidateIndex.MinCandidates-1));
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MinCandidates));
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MinCandidates+1));
	}
	
	@Test /** Purpose of test:
	 		* 
	 		*/
	public void correctComparision() {
		CandidateIndex tester1, tester2;
		
		/* Test case 1: tester1 is smaller than tester2 */
		tester1 = new CandidateIndex(Sample.MEDIUM);
		tester2 = new CandidateIndex(Sample.LARGE);
		assertEquals(-1,tester1.compareTo(tester2));

		/* Test case 2: tester1 is equal to tester2 */
		tester1 = new CandidateIndex(Sample.DEFAULT);
		tester2 = new CandidateIndex(Sample.DEFAULT);
		assertEquals(0,tester1.compareTo(tester2));

		/* Test case 3: tester1 is greater than tester2 */
		tester1 = new CandidateIndex(Sample.MEDIUM);
		tester2 = new CandidateIndex(Sample.SAMLL);
		assertEquals(1,tester1.compareTo(tester2));
	}

	@Test /** Purpose of test:
	 		* 
	 		*/
	public void verifyCopy() {
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		CandidateIndex testerCopy = tester.copy();
		
		// assert if the copy is equal to the original
		int resultEqual = 0;
		assertEquals(resultEqual, tester.compareTo(testerCopy));

		// assert the copy change should not affect the original
		int resultLessThanInput = -1;
		testerCopy.setValue(Sample.DEFAULT + Sample.DEFAULT);   // double the value for the copy
		assertEquals(resultLessThanInput, tester.compareTo(testerCopy));

		// assert the copy change should not affect the original, another way to change the value
		CandidateIndex testerCopy2 = tester.copy();
		testerCopy2.incrementIndex();
		assertEquals(resultLessThanInput, tester.compareTo(testerCopy2));
	}
	
	@Test /** Purpose of test:
	 		* 
	 		*/
	public void verifyIncrementIndex() {
		int counterIncrement = Sample.DEFAULT;
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		
		// check if the increment is called one time
		tester.incrementIndex();
		++counterIncrement;
		assertEquals(Integer.toString(counterIncrement), tester.toString());
		
		// check if the increment is called many time
		int stepToCheck = 1000;
		for (int i = 0; i < stepToCheck; ++i) {
			tester.incrementIndex();
			++counterIncrement;
		}
		assertEquals(Integer.toString(counterIncrement), tester.toString());
	}
	
	@Test /** Purpose of test:
			* 
			*/
	public void verifySetterForValue() {
		int counterValue;
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		
		// Test sample 1
		counterValue = Sample.SAMLL;
		tester.setValue(counterValue);
		assertEquals(Integer.toString(counterValue), tester.toString());
		
		// Test sample 2
		counterValue = Sample.MEDIUM;
		tester.setValue(counterValue);
		assertEquals(Integer.toString(counterValue), tester.toString());

		// Test sample 3
		counterValue = Sample.LARGE;
		tester.setValue(counterValue);
		assertEquals(Integer.toString(counterValue), tester.toString());
	}
}
