package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.ElectionException;

public class CandidateTest {
	
	private static class Sample {
		protected static final String NAME = "name";
		protected static final String PARTY = "party";
		protected static final String ABBREV = "abbrev";
		protected static final int VOTE_COUNT = 1;
	}

	@Before @Test /* Initialize attributes' value */
	public void setUp() {
	}
	
	/*	== TEST SECTION ==
	 *  This section will check each function of the Candidate class
	 **/
	
	@Test /* Check if the candidate is create with appropriate parameter */
	public void normalConstructor() {
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
		} catch (ElectionException e) {
			fail("Exception " + e);
		}
	}

	@Test /* Check if the candidate constructor has invalid parameter.
	 	   * In this case, the name is empty
	 	   */
	public void constructorEmptyName() {
		String emptyName = "";
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(emptyName, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}

	@Test /* Check if the candidate constructor has invalid parameter.
	 	   * In this case, the name is null
	 	   */
	public void constructorNullName() {
		String nullName = new String();
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(nullName, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}

	@Test /* Check if the candidate constructor has invalid parameter.
		   * In this case, the party is empty
		   */
	public void constructorEmptyParty() {
		String emptyParty = "";
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, emptyParty, Sample.ABBREV, Sample.VOTE_COUNT);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}

	@Test /* Check if the candidate constructor has invalid parameter.
		   * In this case, the party is null
		   */
	public void constructorNullParty() {
		String nullParty = new String();
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, nullParty, Sample.ABBREV, Sample.VOTE_COUNT);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}
	
	@Test /* Check if the candidate constructor has invalid parameter.
		   * In this case, the abbrev is empty
		   */
	public void constructorEmptyAbbrev() {
		String emptyAbbrev = "";
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, emptyAbbrev, Sample.VOTE_COUNT);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}

	@Test /* Check if the candidate constructor has invalid parameter.
		   * In this case, the abbrev is null
		   */
	public void constructorNullAbbrev() {
		String nullAbbrev = new String();
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, nullAbbrev, Sample.VOTE_COUNT);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}
	
	@Test /* Check if the candidate constructor has invalid parameter.
		   * In this case, the voteCount is negative
		   */
	public void constructorNegativeVoteCount() {
		int negativeVoteCount = -1;
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, negativeVoteCount);
			fail("Exception not caught.");
		} catch (ElectionException e) {
			assertEquals("Election Exception: " + "Cannot create candidate. Invalid parameter.", e.getMessage());
		}
	}

	@Test /* Check if the candidate constructor works in boundary case.
	   * In this case, the voteCount is zero
	   */
	public void constructorZeroVoteCount() {
		int zeroVoteCount = 0;
		try {
			@SuppressWarnings("unused")
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, zeroVoteCount);
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	@Test /* Check if the candidate return correct vote count */
	public void correctVoteCount() {
		try {
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			assertEquals(Sample.VOTE_COUNT, tester.getVoteCount());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}
	
	@Test /* Check if the candidate return correct Candidate listing */
	public void correctCandidateListing() {
		try {
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			assertEquals(Sample.NAME + "                " +
					Sample.PARTY + "                         " +
					"(" + Sample.ABBREV + ")\n", tester.candidateListing());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}
	
	@Test /* Check if the candidate return correct vote listing9[ */
	public void correctCopy() {
		try {
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			Candidate testerCpoy = tester.copy();

			// assert if the copy is equal to the original
			assertTrue(compareCandidate(tester, testerCpoy));
			
			// assert if a change in the copy should not make change to the original
			testerCpoy.incrementVoteCount();
			assertFalse(compareCandidate(tester, testerCpoy));
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}
	
	@Test /* Check if the candidate return correct vote listing9[ */
	public void correctGetterForName() {
		try {
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			assertEquals(Sample.NAME, tester.getName());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	@Test /* Check if the candidate return correct vote listing9[ */
	public void correctGetterForParty() {
		try {
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			assertEquals(Sample.PARTY, tester.getParty());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	@Test /* Check if the candidate return correct vote listing9[ */
	public void correctGetterForVoteCount() {
		try {
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			assertEquals(Sample.VOTE_COUNT, tester.getVoteCount());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	@Test /* Check if the candidate return correct vote listing9[ */
	public void correctGetterForVoteCountString() {
		try {
			String expectedString = Integer.toString(Sample.VOTE_COUNT);
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			assertEquals(expectedString, tester.getVoteCountString());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	@Test /* Check if the candidate return correct vote listing9[ */
	public void correctVoteCountIncrement() {
		try {
			int counterIncrement = Sample.VOTE_COUNT;
			Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
			
			// check if the increment is called one time
			tester.incrementVoteCount();
			++counterIncrement;
			assertEquals(counterIncrement, tester.getVoteCount());
			
			// check if the increment is called many time
			int stepToCheck = 1000;
			for (int i = 0; i < stepToCheck; ++i) {
				tester.incrementVoteCount();
				++counterIncrement;
			}
			assertEquals(counterIncrement, tester.getVoteCount());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	/* == END TEST SECTION ==
	 * for Candidate class
	 */
	
	/*************** Private Method *******************/
	private boolean compareCandidate(Candidate copy1, Candidate copy2) {
		return copy1.toString().equals(copy2.toString());
	}
}
