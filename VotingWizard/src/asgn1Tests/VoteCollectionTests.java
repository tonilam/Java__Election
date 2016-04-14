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
		final int testSize = 10;
		try {
			VoteCollection tester = new VoteCollection(numCandidates);
			System.out.println("\nGenerating random unique samples...");
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
		final int testSize = 10;
		try {
			VoteCollection tester = new VoteCollection(numCandidates);
			System.out.println("\nGenerating random samples...");
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
	
	private Vote generateRandomVote() {
		Vote sample = new VoteList(numCandidates);
		for (int votePref = 1; votePref <= numCandidates; ++votePref) {
			sample.addPref(rand.nextInt(numCandidates) + 1);
		}
		System.out.println(sample.toString());
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
		System.out.println(sample.toString());
		return sample;
	}
}
