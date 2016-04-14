package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.CandidateIndex;
import asgn1Election.Vote;
import asgn1Election.VoteList;

public class VoteListTests {

	@Before @Test /* Initialize attributes' value */
	public void setUp() {
	}
	
	@Test /**
			*
			*/
	public void validVote() {
		Vote tester = new VoteList(3);
		int sampleVote[] = new int[] {1, 2, 3};
		String expectedString = "1 2 3 ";
		boolean validVote = true;
		
		for (int item : sampleVote) {
			validVote = validVote && tester.addPref(item);
		}
		assertTrue(validVote);
		assertEquals(expectedString, tester.toString());
	}


	@Test /**
	 		*
	 		*/
	public void voteElementExceedLimit() {
		final int LIMIT = 3;
		
		Vote tester = new VoteList(LIMIT);
		int sampleVote[] = new int[] {1, 2, 3};
		for (int sample : sampleVote) {
			assertTrue(tester.addPref(sample));
		}

		Vote tester2 = new VoteList(LIMIT);
		int sampleVote2[] = new int[] {1, 2, 3, 4};
		int counter = 0;
		for (int sample : sampleVote2) {
			if (counter++ < LIMIT) {
				assertTrue(tester2.addPref(sample));
			} else {
				assertFalse("Scanning sample: "+sample, tester2.addPref(sample));
			}
		}
	}
	
//	@Test /** Javadoc said that the informal vote will be handled elsewhere
//	 		*
//	 		*/
//	public void duplicateVoteElement() {
//		Vote tester = new VoteList(3);
//		int sampleVote[] = new int[] {1, 3, 3};
//		String expectedString = "1 3 ";
//		boolean validVote = true;
//		
//		for (int item : sampleVote) {
//			validVote = validVote && tester.addPref(item);
//		}
//		assertFalse(validVote);
//		assertEquals(expectedString, tester.toString());
//	}

	@Test /** Purpose:
	 		* As the requirement of function vote()
	 		* doesn't handle out of range issue,
	 		* the function should accept and process
	 		* any input although the preference is out
	 		* of range.
	 		* P.S. It is a valid vote but can be either
	 		* formal or informal vote.
	 		*/
	public void voteElementOutOfRange() {
		Vote tester = new VoteList(3);
		int sampleVote[] = new int[] {1, 2, 5};
		String expectedString = "1 2 5 ";
		boolean validVote = true;
		
		for (int item : sampleVote) {
			validVote = validVote && tester.addPref(item);
		}
		assertTrue(validVote);
		assertEquals(expectedString, tester.toString());
	}
	
	@Test /** Purpose of test:
			* 
			*/
	public void verifyCopyVote() {
		int sampleVote[] = new int[] {1, 2, 5};
		
		// Make the original vote list
		Vote tester = new VoteList(3);
		for (int item : sampleVote) {
			if (!tester.addPref(item)) {
				fail("Error on addPref");
			}
		}
		
		// Make a deep copy
		Vote testerCopy = tester.copyVote();

		// assert if the copy is equal to the original
		assertEquals(tester.toString(), testerCopy.toString());
	}
	
	@Test /** Purpose of test:
			* 
			*/
	public void verifyGetPreference() {
		int sampleVote[] = new int[] {3, 1, 2};
		Vote tester = assignList(sampleVote);
		assertEquals(0, new CandidateIndex(1).compareTo(tester.getPreference(1)));
		assertEquals(0, new CandidateIndex(2).compareTo(tester.getPreference(2)));
		assertEquals(0, new CandidateIndex(0).compareTo(tester.getPreference(3)));
	}
	
	@Test /** Purpose of test:
			* 
			*/
	public void verifyNormalInvertVote() {
		int sampleVote[] = new int[] {3, 1, 5, 4, 2};
		assertEquals("1 4 0 3 2 ", assignList(sampleVote).invertVote().toString());
	}

	@Test /** Purpose of test:
			* 
			*/
	public void verifyDuplicatedInvertVote() {
		int sampleVote[] = new int[] {5, 3, 5, 3, 2};
		assertEquals("4 1 3 0 2 ", assignList(sampleVote).invertVote().toString());
	}

	@Test /** Purpose of test:
			* 
			*/
	public void verifyOutOfRangeInvertVote() {
		int sampleVote[] = new int[] {5, 100, 999, 3, 2};
		assertEquals("4 3 0 1 2 ", assignList(sampleVote).invertVote().toString());
	}

	public Vote assignList(int sampleVote[]) {
		// Make the original vote list
		Vote tester = new VoteList(sampleVote.length);
		for (int item : sampleVote) {
			if (!tester.addPref(item)) {
				fail("Error on addPref");
			}
		}
		return tester;
	}
}
