package asgn1Tests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import asgn1Election.CandidateIndex;

/**
 * This class is used to test the function in class CandidateIndex 
 * 
 * @author Toni Lam
 */
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
	 		* check if the inRange function can determine the value is in the range of candidate index
	 		* precondition: CandidateIndex constrains MinCandidates & MaxCandidates is defined.
	 		* postcondition: all calls of the test case return true
	 		*/
	public void testInRange() {
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MaxCandidates-1));
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MaxCandidates));
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MinCandidates));
		assertEquals(true, CandidateIndex.inRange(CandidateIndex.MinCandidates+1));
	}

	@Test /** Purpose of test:
			* check if the inRange function can determine the value is out of range of candidate index
			* precondition: CandidateIndex constrains MinCandidates & MaxCandidates is defined.
			* postcondition: all calls of the test case return false
			*/
	public void testOutOfRange() {
		assertEquals(false, CandidateIndex.inRange(CandidateIndex.MaxCandidates+1));
		assertEquals(false, CandidateIndex.inRange(CandidateIndex.MinCandidates-1));
	}
	
	@Test /** Purpose of test:
	 		* check comparison of the tester1's index is smaller than the tester2's index
	 		* precondition: tester1 is smaller than tester2
	 		* postcondition: compareTo() return -1
	 		*/
	public void testCompareToLessThan() {
		CandidateIndex tester1, tester2;
		tester1 = new CandidateIndex(Sample.MEDIUM);
		tester2 = new CandidateIndex(Sample.LARGE);
		assertEquals(-1,tester1.compareTo(tester2));
	}

	@Test /** Purpose of test:
			* check comparison of the tester1's index is equal to the tester2's index
			* precondition: tester1 is equal to tester2
			* postcondition: compareTo() return 0
			*/
	public void testCompareToEquals() {
		CandidateIndex tester1, tester2;
		tester1 = new CandidateIndex(Sample.DEFAULT);
		tester2 = new CandidateIndex(Sample.DEFAULT);
		assertEquals(0,tester1.compareTo(tester2));
	}
	
	@Test /** Purpose of test:
			* check comparison of the tester1's index is larger than the tester2's index
			* precondition: tester1 is larger than tester2
			* postcondition: compareTo() return 1
			*/
	public void testCompareToLargerThan() {
		CandidateIndex tester1, tester2;
		tester1 = new CandidateIndex(Sample.MEDIUM);
		tester2 = new CandidateIndex(Sample.SAMLL);
		assertEquals(1,tester1.compareTo(tester2));
	}

	
	
	@Test /** Purpose of test:
	 		* Check if the candidate return identical copy
	 		* precondition: Sample is declared correctly.
	 		* postcondition: two copy are the same
			*/
	public void testIdenticalCopy() {
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		CandidateIndex testerCopy = tester.copy();
		
		// assert if the copy is equal to the original
		int resultEqual = 0;
		assertEquals(resultEqual, tester.compareTo(testerCopy));
	}

	@Test /** Purpose of test:
			* Check if the candidate copy works independently from the original one
			* precondition: Sample is declared correctly.
			* postcondition: changes in copy doesn't change the original one
			*/
	public void testIdependentCopy() {
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		CandidateIndex testerCopy = tester.copy();
		
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
	 		* check the incrementIndex will add the index by one
	 		* precondition: Sample is declared correctly.
	 		* postcondition: 
	 		*/
	public void testSingleIncrementIndex() {
		int counterIncrement = Sample.DEFAULT;
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		
		tester.incrementIndex();
		++counterIncrement;
		assertEquals(Integer.toString(counterIncrement), tester.toString());
	}

	@Test /** Purpose of test:
			* check the incrementIndex will add the index by the number of steps (e.g. 1000)
			* precondition: Sample is declared correctly.
			* postcondition: final index should be equal to initialized index + step.
			*/
	public void testMultiIncrementIndex() {
		int counterIncrement = Sample.DEFAULT;
		CandidateIndex tester = new CandidateIndex(Sample.DEFAULT);
		int stepToCheck = 1000;
		
		for (int i = 0; i < stepToCheck; ++i) {
			tester.incrementIndex();
			++counterIncrement;
		}
		assertEquals(Integer.toString(counterIncrement), tester.toString());
	}
}
