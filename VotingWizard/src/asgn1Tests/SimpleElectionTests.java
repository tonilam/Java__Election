package asgn1Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import asgn1Election.Election;
import asgn1Election.ElectionException;
import asgn1Election.ElectionManager;
import asgn1Election.Vote;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

public class SimpleElectionTests {
	
	ElectionManager eManager;

	@Before @Test /* Initialize election manager */
	public void setUp() throws FileNotFoundException, IOException, ElectionException, NumbersException {
		eManager = new ElectionManager();
		String simpleElectionsList = ".//data//devtestdata/simple_elections.lst";
		eManager.getElectionsFromFile(simpleElectionsList);
	}
	
	@Test /** Purpose of the test:
			* Try to load all elections as set in elections.lst
			* precondition: eManager is not null and contains at least one election in the list
			* postcondition: no error 
	 		*/
	public void isAbleToLoadData() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		for (Election election : eManager.getElectionList()) {		
			eManager.setElection(election);
		}
	}
	
	@Test (expected = Exception.class)
		  /** Purpose of the test:
			* Try to read a elections list which is not exist in the system.
			* precondition: fakeFileName is not exist in the system
			* postcondition: throw FileNotFoundException
	 		*/
	public void tryToLoadNonexistElectionsList() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String fakeFileName = "test_not_exist";
		eManager.getElectionsFromFile(fakeFileName);
		fail("java.io.FileNoyFoundExcepption should be catch before running this assertion.");
	}


	@Test
	public void canFindWinner() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String result;
		for (Election election : eManager.getElectionList()) {		
			eManager.setElection(election);
			result = eManager.manageCount();
			assertFalse(result.isEmpty());
		}
	}

	@Test
		  /**
		   * This test is used to pick up an election and evaluate each sample vote to check if inFormal()
		   * function returns the expected result
		   * @throws FileNotFoundException
		   * @throws ElectionException
		   * @throws IOException
		   * @throws NumbersException
		   */
	public void verifyIsFormal() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election tester;
		Vote v;
		
		eManager = new ElectionManager();
		String simpleElectionsList = ".//data//devtestdata/simple_elections_formal_informal.lst";
		eManager.getElectionsFromFile(simpleElectionsList);
		if (eManager.getElectionList().iterator().hasNext()) {

			/* Test for formal votes */
			tester = eManager.getElectionList().iterator().next();
			eManager.setElection(tester);
			
			int formalVotes[][], informalVotes[][];
			formalVotes = new int[][] {{1, 3, 4, 5, 2},
									   {3, 2, 1, 5, 4},
									   {1, 2, 2, 2, 2},
									   {1, 3, 4, 5, 4}};
			for (int[] thisVote : formalVotes){
				v = new VoteList(eManager.getElection().getNumCandidates());
				for (int pref : thisVote) {
					v.addPref(pref);
				}
				assertTrue(eManager.getElection().isFormal(v));
			}
			
			/* Test for informal votes */
			tester = eManager.getElectionList().iterator().next();
			eManager.setElection(tester);
			informalVotes = new int[][] {{2, 3, 4, 2, 5},	// violate rule 1: no first preference
										 {3, 2, 1, 4, 1},	// violate rule 2: duplicated first preference
										 {4, 2, 3, 7, 1},	// violate rule 3: preference is not valid
										 {2, 8, 4, 0, 5},	// violate rules 1 & 3
										 {1, 2, 4, 1, 7}};	// violate rules 2 & 3
										 
			for (int[] thisVote : informalVotes){
				v = new VoteList(eManager.getElection().getNumCandidates());
				for (int pref : thisVote) {
					v.addPref(pref);
				}
				assertFalse(eManager.getElection().isFormal(v));
			}
		} else {
			fail("No test taken.");
		}
	}
}
