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

public class PrefElectionTests {
	
	ElectionManager eManager;

	@Before @Test /* Initialize election manager */
	public void setUp() throws FileNotFoundException, IOException, ElectionException, NumbersException {
		eManager = new ElectionManager();
		String prefElectionsList = ".//data//devtestdata/pref_elections.lst";
		eManager.getElectionsFromFile(prefElectionsList);
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


	//@Test
	public void canFindWinner() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String result;
		for (Election election : eManager.getElectionList()) {		
			eManager.setElection(election);
			result = eManager.manageCount();
			assertFalse(result.isEmpty());
		}
	}

	@Test
	public void verifySelectLowestCandidate() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		eManager = new ElectionManager();
		String prefElectionsList = ".//data//devtestdata/pref_elections_lowestcandidate.lst";
		eManager.getElectionsFromFile(prefElectionsList);
		eManager.setElection(eManager.getElectionList().iterator().next());
		
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
		String prefElectionsList = ".//data//devtestdata/pref_elections_formal_informal.lst";
		eManager.getElectionsFromFile(prefElectionsList);
		if (eManager.getElectionList().iterator().hasNext()) {

			/* Test for formal votes */
			tester = eManager.getElectionList().iterator().next();
			eManager.setElection(tester);
			
			int formalVotes[][], informalVotes[][];
			formalVotes = new int[][] {{4, 5, 2, 3, 1},
									   {3, 5, 1, 4, 2}, 
									   {1, 2, 3, 4, 5}, 
									   {5, 4, 3, 2, 1}, 
									   {1, 3, 2, 5, 4}};
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
			informalVotes = new int[][] {{1, 2, 2, 3, 4}, 
										 {4, 3, 3, 1, 2},
										 {2, 3, 4, 5, 6},
										 {5, 2, 3, 1, 7}};
										 
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
