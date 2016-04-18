package asgn1Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asgn1Election.ElectionException;
import asgn1Election.Vote;
import asgn1Election.VoteCollection;
import asgn1Election.VoteList;

public class VoteCollectionTests {

	static Random rand;
	static final int numCandidates = 5;
	
	@Before @Test /* Initialize attributes' value */
	public void setUp() {
		rand = new Random();
	}

	@Test /**
			*
			*/
	public void verifyIncludeFormalVote() {
		final int numCandidates = 3;
		final int testSize = 100000;
		try {
			VoteCollection tester = new VoteCollection(numCandidates);
			for (int i = 0; i < testSize; ++i) {
				tester.includeFormalVote(generateUniqueRandomVote());
				assertEquals(i+1, tester.getFormalCount());
			}
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
		
	}

	@Test /**
			*
			*/
	public void verifyEmptyTheCollection() {
		final int numCandidates = 3;
		final int testSize = 100000;
		try {
			VoteCollection tester = new VoteCollection(numCandidates);
			for (int i = 0; i < testSize; ++i) {
				tester.includeFormalVote(generateRandomVote());
				assertEquals(i+1, tester.getFormalCount());
				assertEquals(0, tester.getInformalCount());
			}
			tester.emptyTheCollection();
			assertEquals(0, tester.getFormalCount());
			assertEquals(0, tester.getInformalCount());
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
		
	}
	
	@Test /**
			*
			*/
	public void verifyUpdateInformalVote() {
		final int numCandidates = 3;
		final int testSize = 100000;
		int countFormal = 0,
			countInformal = 0;
		try {
			VoteCollection tester = new VoteCollection(numCandidates);
			for (int i = 0; i < testSize; ++i) {
				if (i % 2 == 0) {
					// all even number vote is treated as formal vote
					tester.includeFormalVote(generateUniqueRandomVote());
					assertEquals(++countFormal, tester.getFormalCount());
				} else {
					// all odd number vote is treated as informal vote
					tester.updateInformalCount();
					assertEquals(++countInformal, tester.getInformalCount());
				}
			}
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}

	@Test /**
		*
		*/
	public void verifyCountPrimaryVotes() throws ElectionException {
		final int numCandidates = 3;
		final int testSize = 100000;
		try {
			VoteCollection tester = new VoteCollection(numCandidates);
			for (int i = 0; i < testSize; ++i) {
				tester.includeFormalVote(generateUniqueRandomVote());
			}
		} catch (ElectionException e) {
			fail("This test should not have an exception" + e.getMessage());
		}
	}
	
	
	/*********************** PRIVATE METHOD **************************/
	
	private Vote generateRandomVote() {
		Vote sample = new VoteList(numCandidates);
		for (int votePref = 1; votePref <= numCandidates; ++votePref) {
			sample.addPref(rand.nextInt(numCandidates) + 1);
		}
		//System.out.println(sample.toString());
		return sample;
	}

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
