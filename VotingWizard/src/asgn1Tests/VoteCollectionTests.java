package asgn1Tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import asgn1Election.CandidateIndex;
import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.ElectionManager;
import asgn1Election.Vote;
import asgn1Election.VoteCollection;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

/**
 * This class is used to test the function in class VoteCollection
 * @author Toni Lam
 */
public class VoteCollectionTests {

	static Random rand;
	static final int numCandidates = 5;
	
	@Before @Test /* Initialize attributes' value */
	public void setUp() {
		rand = new Random();
	}

	/**
	 * Purpose of test:
	 * Check if the constructor can throw an exception if the VoteCollection size is out of range,
	 * in this case: size 0
	 * precondition: input size = 0
	 * postcondition: ElectionException thrown
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testVoteCollectionOutOfRangeMinNumCandidate() throws ElectionException {
		@SuppressWarnings("unused")
		VoteCollection tester_vc = new VoteCollection(CandidateIndex.MinCandidates - 1);
	}

	/**
	 * Purpose of test:
	 * Check if the constructor can throw an exception if the VoteCollection size is out of range,
	 * in this case: size > max candidates
	 * precondition: input size = CandidateIndex.MaxCandidates + 1
	 * postcondition: ElectionException thrown
	 * @throws ElectionException 
	 */
	@Test (expected = ElectionException.class)
	public void testVoteCollectionOutOfRangeMaxNumCandidate() throws ElectionException {
		@SuppressWarnings("unused")
		VoteCollection tester_vc = new VoteCollection(CandidateIndex.MaxCandidates + 1);
	}

	/** Purpose of test:
	 * check if the includeFormalVote() can add data and change the formalCount counter
	 * precondition: true
	 * postcondition: getFormalCount() return the i+1 value on each loop and also at the end of the test,
	 * which formal count should equals to the test size
	 * @throws ElectionException 
	 */
	@Test
	public void testIncludeFormalVote() throws ElectionException {
		final int numCandidates = 3;
		final int testSize = 100000;
		VoteCollection tester = new VoteCollection(numCandidates);
		
		for (int i = 0; i < testSize; ++i) {
			tester.includeFormalVote(generateUniqueRandomVote());
			assertEquals(i+1, tester.getFormalCount());
		}
		assertEquals(testSize, tester.getFormalCount());
	}

	/** Purpose of test:
	 * check if the updateInformalVote() work properly.
	 * precondition: true
	 * postcondition: verification passes on each step and also at the end of the test
	 * @throws ElectionException 
	 */
	@Test
	public void testUpdateInformalVote() throws ElectionException {
		final int numCandidates = 3;
		final int testSize = 100000;
		int countInformal = 0;
		VoteCollection tester = new VoteCollection(numCandidates);
		
		for (int i = 0; i < testSize; ++i) {
			if (i % 2 == 0) {
				// all even number vote is treated as formal vote
				tester.includeFormalVote(generateUniqueRandomVote());
			} else {
				// all odd number vote is treated as informal vote
				tester.updateInformalCount();
				// verify the informal counter on each step
				assertEquals(++countInformal, tester.getInformalCount());
			}
		}
		// verify the informal counter at the end, it should equals to half of the testSize in this algorithm
		assertEquals(testSize/2, tester.getInformalCount());
	}

	/** Purpose of test:
	 * Check if the countPrimaryVotes() function works properly
	 * precondition: testing file not empty
	 * postcondition: findWinner() result match expected string.
	 * @throws NumbersException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCountPrimaryVotes() throws ElectionException, FileNotFoundException, IOException, NumbersException {
		String expectedString =
				"Results for election: MinMorgulValeSimple\n" +
				"Enrolment: 25\n\n" +
				"Shelob              Monster Spider Party          (MSP)\n" +
				"Gorbag              Filthy Orc Party              (FOP)\n" +
				"Shagrat             Stinking Orc Party            (SOP)\n\n\n" +
				"Counting primary votes; 3 alternatives available\n\n" +
				"Simple election: MinMorgulValeSimple\n\n" +
				"Shelob (MSP)                 8\n" +
				"Gorbag (FOP)                 8\n" +
				"Shagrat (SOP)                3\n\n" +
				"Informal                     4\n\n" +
				"Votes Cast                  23\n\n\n" +
				"Candidate Shelob (Monster Spider Party) is the winner with 8 votes...\n";
			
		ElectionManager em = new ElectionManager();
		String simpleElectionsList = ".//data//devtestdata/simple_elections.lst";
		
		em.getElectionsFromFile(simpleElectionsList);
		em.setElection(em.getElectionList().get(0));
		
		Election sElection = em.getElection();
		assertEquals(expectedString, sElection.findWinner());
	}

	/** Purpose of test:
	 * check if the emptyTheCollection can clear the voteList and reset other attributes
	 * precondition: true
	 * postcondition: both counter return 0
	 * @throws ElectionException 
	 */
	@Test
	public void testEmptyTheCollection() throws ElectionException {
		int numCandidates = 3;
		Vote voteList = new VoteList(numCandidates);
		VoteCollection vc = new VoteCollection(numCandidates);
		
		for (int i = 1; i <= numCandidates; ++i) {
			voteList.addPref(i);
		}
		
		//formal count value changed
		vc.includeFormalVote(voteList);

		//informal count value changed
		vc.updateInformalCount();
		
		vc.emptyTheCollection();
		assertEquals(0, vc.getFormalCount() + vc.getInformalCount());
	}
	
	/*********************** PRIVATE METHOD **************************/
	
	/**
	 * Helper function to generate unique random vote for a valid vote.
	 * precondition: true
	 * @return a valid vote in randomly
	 */
	private Vote generateUniqueRandomVote() {
		Vote sample = new VoteList(numCandidates);
		Set<Integer> voted = new HashSet<Integer>();
		for (int votePref = 1; votePref <= numCandidates; ++votePref) {
			int votingPref;
			do {
				votingPref = rand.nextInt(numCandidates) + 1;
			} while (voted.contains(votingPref));
			voted.add(votingPref);
			sample.addPref(votingPref);
		}
		//System.out.println(sample.toString());
		return sample;
	}
}
