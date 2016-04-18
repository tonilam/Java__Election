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
	
	@Test /** Purpose of test:
			* to check the VoteList can store all the valid vote into the list
			* precondition: number of votes to be added is less than number of candidates
			* postcondition: the VoteList stores the value as expected
			*/
	public void testAddPrefValidVote() {
		String expectedString = "1 2 3 ";
		Vote tester = new VoteList(3);
		int sampleVote[] = new int[] {1, 2, 3};
		boolean validVote = true;
		
		for (int item : sampleVote) {
			validVote = validVote && tester.addPref(item);
		}
		assertTrue(validVote);
		assertEquals(expectedString, tester.toString());
	}
	
	@Test /** Purpose of test:
			* to check the VoteList can store all the valid vote into the list
			* precondition: number of votes to be added is more than number of candidates
			* postcondition: the VoteList stores the value as expected
			*/
	public void testAddPrefInvalidVote() {
		String expectedString = "1 2 3 ";
		Vote tester = new VoteList(3);
		int sampleVote[] = new int[] {1, 2, 3, 4};
		boolean validVote = true;
		
		for (int item : sampleVote) {
			validVote = validVote && tester.addPref(item);
		}
		assertEquals(expectedString, tester.toString());
	}

	@Test /** Purpose of test:
			* to check the VoteList return false on adding more elements than it can store.
			* precondition: number of votes to be added is more than number of candidates
			* postcondition: addPref() return false
			*/
	public void testAddPrefExceedLimit() {
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
	
	@Test /** Purpose of test:
	 		* As the requirement of function vote() doesn't handle out of range issue,
	 		* the function should accept and process any input although the preference
	 		* is out of range.
	 		* P.S. It is a valid vote but can be either formal or informal vote.
	 		* precondition: true
	 		* postcondition: the toString() result is the same as expected, and the vote is counted for valid
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
			* Check if a deep copy of vote stores the same values as the original.
			* precondition: true
			* postcondition: toString() results of two object are the same
			*/
	public void testCopyVote() {
		int sampleVote[] = new int[] {1, 2, 5};
		
		// Make the original vote list
		Vote tester = assignList(sampleVote);
		
		// Make a deep copy
		Vote testerCopy = tester.copyVote();

		// assert if the copy is equal to the original
		assertEquals(tester.toString(), testerCopy.toString());
	}
	
	@Test /** Purpose of test:
			* check the getPreference() function returns the correct value
			* precondition: true
			* postcondition: the results of getPreference is same as expected.
			*/
	public void testGetPreference() {
		int sampleVote[] = new int[] {3, 1, 2};
		Vote tester = assignList(sampleVote);
		assertEquals(0, new CandidateIndex(1).compareTo(tester.getPreference(1)));
		assertEquals(0, new CandidateIndex(2).compareTo(tester.getPreference(2)));
		assertEquals(0, new CandidateIndex(0).compareTo(tester.getPreference(3)));
	}
	
	@Test /** Purpose of test:
			* check the invertVote() function returns the preference list as expected
			* in the situation that each preference is unique
			* precondition: true
			* postcondition: returned list is the same as expected. 
			*/
	public void testNormalInvertVote() {
		int sampleVote[] = new int[] {2, 6, 5, 3, 4, 7, 1, 8};
		assertEquals("7 1 4 5 3 2 6 8 ", assignList(sampleVote).invertVote().toString());
	}

	@Test /** Purpose of test:
			* check the invertVote() function returns the preference list as expected
			* if there is two candidates having the same preference
			* precondition: true
			* postcondition: returned list is the same as expected. 
			*/
	public void testDuplicatedInvertVote() {
		int sampleVote[] = new int[] {5, 3, 5, 3, 2};
		assertEquals("5 2 4 1 3 ", assignList(sampleVote).invertVote().toString());
	}

	@Test /** Purpose of test:
			* check if the invertVote() can process our of range candidate index.
			* precondition: true
			* postcondition: returned list is the same as expected. 
			*/
	public void testOutOfRangeInvertVote() {
		int sampleVote[] = new int[] {5, 100, 999, 3, 2};
		assertEquals("5 4 1 2 3 ", assignList(sampleVote).invertVote().toString());
	}

	@Test /** Purpose of test:
	 		* check if the iterator working and returns something.
	 		* precondition: true
	 		* postcondition: iterator not null 
	 		*/
	public void testIterator() {
		int sampleVote[] = new int[] {5, 100, 999, 3, 2};
		assertNotNull(assignList(sampleVote).iterator());
	}

	/**
	 * Helper function
	 * To add pref from a list of integer to the VoteList 
	 * @param sampleVote
	 * @return
	 */
	private Vote assignList(int sampleVote[]) {
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