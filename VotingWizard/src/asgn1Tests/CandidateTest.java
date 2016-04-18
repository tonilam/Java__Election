package asgn1Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.Candidate;
import asgn1Election.ElectionException;

/**
 * This class is used to test the function in class Candidate 
 * 
 * @author Toni Lam
 */
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
	
	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the name is empty
	 * precondition: 1. Sample is declared correctly.
	 *               2. name is an empty string and passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testConstructorEmptyName() throws ElectionException {
		String emptyName = "";
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(emptyName, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
	}

	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the name is null
	 * precondition: 1. Sample is declared correctly.
	 *               2. name is null and passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testConstructorNullName() throws ElectionException {
		String nullName = new String();
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(nullName, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
	}

	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the party is empty
	 * precondition: 1. Sample is declared correctly.
	 *               2. party is an empty string and passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testConstructorEmptyParty() throws ElectionException {
		String emptyParty = "";
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(Sample.NAME, emptyParty, Sample.ABBREV, Sample.VOTE_COUNT);
	}

	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the party is null
	 * precondition: 1. Sample is declared correctly.
	 *               2. party is null and passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testCestConstructorNullParty() throws ElectionException {
		String nullParty = new String();
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(Sample.NAME, nullParty, Sample.ABBREV, Sample.VOTE_COUNT);
	}
	
	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the abbrev is empty
	 * precondition: 1. Sample is declared correctly.
	 *               2. abbrev is an empty string and passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testConstructorEmptyAbbrev() throws ElectionException {
		String emptyAbbrev = "";
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, emptyAbbrev, Sample.VOTE_COUNT);
	}

	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the abbrev is null
	 * precondition: 1. Sample is declared correctly.
	 *               2. abbrev is null and passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void TestConstructorNullAbbrev() throws ElectionException {
		String nullAbbrev = new String();
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, nullAbbrev, Sample.VOTE_COUNT);
	}
	
	/** Purpose of the test:
	 * Check if the candidate constructor has invalid parameter.
	 * In this case, the voteCount is negative
	 * precondition: 1. Sample is declared correctly.
	 *               2. negative vote count is passes to the constructor
	 * postcondition: election exception thrown inside the test 
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void TestConstructorNegativeVoteCount() throws ElectionException {
		int negativeVoteCount = -1;
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, negativeVoteCount);
	}

	/** Purpose of the test:
	 * Check if the candidate constructor works in boundary case.
	 * In this case, the voteCount is zero
	 * precondition: 1. Sample is declared correctly.
	 *               2. zero vote count is passes to the constructor
	 * postcondition: no exception thrown 
	 */
	@Test
	public void testConstructorZeroVoteCount() throws ElectionException {
		int zeroVoteCount = 0;
		@SuppressWarnings("unused")
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, zeroVoteCount);
	}

	/** Purpose of the test:
	 * Check if the candidate return correct Candidate listing
	 * precondition: Sample is declared correctly.
	 * postcondition: candidateListing return the same value as expected.
	 */
	@Test
	public void testCandidateListing() throws ElectionException {
		String expectedString = Sample.NAME + "                " +
								Sample.PARTY + "                         " +
								"(" + Sample.ABBREV + ")\n";
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
		assertEquals(expectedString, tester.candidateListing());
	}
	
	/** Purpose of the test:
	 * Check if the candidate return identical copy
	 * precondition: Sample is declared correctly.
	 * postcondition: two copy are the same
	 */
	@Test
	public void testIdenticalCopy() throws ElectionException {
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
		Candidate testerCpoy = tester.copy();
		assertTrue(compareCandidate(tester, testerCpoy));
	}

	/** Purpose of the test:
	 * Check if the copy is working independent from the original one
	 * precondition: Sample is declared correctly.
	 * postcondition: changes in copy doesn't change the original one
	 */
	@Test
	public void testIndependentCopy() throws ElectionException {
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
		Candidate testerCpoy = tester.copy();
		testerCpoy.incrementVoteCount();
		assertFalse(compareCandidate(tester, testerCpoy));
	}
	
	/** Purpose of the test:
	 * Check if the candidate return correct vote increment
	 * precondition: Sample is declared correctly.
	 * postcondition: the candidate vote count is increased by 1
	 */
	@Test
	public void testSingleIncrementCount() throws ElectionException {
		int counterIncrement = Sample.VOTE_COUNT;
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
		tester.incrementVoteCount();
		++counterIncrement;
		assertEquals(counterIncrement, tester.getVoteCount());
	}
	
	/** Purpose of the test:
	 * Check if the candidate return correct vote increment
	 * precondition: Sample is declared correctly.
	 * postcondition: the candidate vote count is increased by the number of step (e.g. 1000)
	 */
	public void testMultiIncrementCount() throws ElectionException {
		int counterIncrement = Sample.VOTE_COUNT;
		Candidate tester = new Candidate(Sample.NAME, Sample.PARTY, Sample.ABBREV, Sample.VOTE_COUNT);
		int stepToCheck = 1000;
		
		for (int i = 0; i < stepToCheck; ++i) {
			tester.incrementVoteCount();
			++counterIncrement;
		}
		assertEquals(counterIncrement, tester.getVoteCount());
	}

	/* == END TEST SECTION ==
	 * for Candidate class
	 */
	
	/*************** Private helper Method *******************/
	/**
	 * compareCandidate(): To compare two copy storing the same value.
	 * @param copy1 not null
	 * @param copy2 not null
	 * @return true if to copy's toString are the same
	 * 		   false otherwise
	 */
	private boolean compareCandidate(Candidate copy1, Candidate copy2) {
		return copy1.toString().equals(copy2.toString());
	}
}
