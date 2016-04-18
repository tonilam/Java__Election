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
	String prefElectionsList;

	@Before @Test /* Initialize election manager */
	public void setUp() throws FileNotFoundException, IOException, ElectionException, NumbersException {
		prefElectionsList = ".//data//devtestdata/pref_elections.lst";
		eManager = new ElectionManager();
		eManager.getElectionsFromFile(prefElectionsList);
	}
	
	/** Purpose of the test:
	 * Try to load all elections as set in elections.lst
	 * precondition: eManager is not null and contains at least one election in the list
	 * postcondition: no error 
	 */
	@Test
	public void testLoadDefs() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
		}
	}

	/** Purpose of the test:
	 * Try to load all elections as set in a invalid elections list
	 * precondition: eManager is not null and contains at least one invalid election in the list
	 * postcondition: FileNotFoundException thrown
	 */
	@Test (expected = FileNotFoundException.class)
	public void testLoadDefsInvalidDefs() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidDefsList = ".//data//devtestdata/pref_elections_invalid_list.lst";
		eManager.getElectionsFromFile(invalidDefsList);	
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
		}
	}
	
	/** Purpose of the test:
	 * Try to load all elections as set in a elections list that contains invalid electorate file
	 * precondition: eManager is not null and contains at least one invalid election in the list
	 * postcondition: ElectionException thrown
	 */
	@Test (expected = ElectionException.class)
	public void testLoadDefsInvalidElectorate() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidDefsList = ".//data//devtestdata/pref_elections_invalid_electorate.lst";
		eManager.getElectionsFromFile(invalidDefsList);	
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
		}
	}

	/** Purpose of the test:
	 * Try to load all elections as set in elections.lst
	 * precondition: eManager is not null and contains at least one election in the list
	 * postcondition: no error 
	 */
	@Test
	public void testLoadVotes() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		eManager.getElectionsFromFile(prefElectionsList);	
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
			election.loadVotes();
		}
	}

	/** Purpose of the test:
	 * Try to load all elections as set in elections.lst
	 * precondition: eManager is not null and contains at least one invalid election in the list
	 * postcondition: FileNotFoundException thrown
	 */
	@Test (expected = FileNotFoundException.class)
	public void testLoadVotesInvalidVotes() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidVotesList = ".//data//devtestdata/pref_elections_missing_vote.lst";
		eManager.getElectionsFromFile(invalidVotesList);	
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
			election.loadVotes();
		}
	}
	
	/** Purpose of the test:
	 * Try to load all elections as set in a elections list that contains invalid numCandidate setting
	 * precondition: eManager is not null and contains at least one invalid election in the list
	 * postcondition: NumbersException thrown
	 */
	@Test (expected = NumbersException.class)
	public void testLoadDefsNumberException() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidVotesList = ".//data//devtestdata/pref_elections_defsnumberexception.lst";
		eManager.getElectionsFromFile(invalidVotesList);	
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
		}
	}
	
	/** Purpose of the test:
	 * Try to load all elections as set in a elections list that contains invalid votes
	 * precondition: eManager is not null and contains at least one invalid election in the list
	 * postcondition: NumbersException thrown
	 */
	@Test
	public void testLoadVotesNumberException() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidVotesList = ".//data//devtestdata/pref_elections_numberexception.lst";
		eManager.getElectionsFromFile(invalidVotesList);	
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
			election.loadVotes();
		}
	}

	/** Purpose of test:
	 * check if the findWinner work properly
	 * Hints: ElectionManager.manageCount() will call findWinner for all election stored.
	 * precondition: eManager contains at least one preference election
	 * postcondition: each result of manageCount() is not empty
	 * @throws FileNotFoundException
	 * @throws ElectionException
	 * @throws IOException
	 * @throws NumbersException
	 */
	@Test
	public void testFindWinner() throws FileNotFoundException, ElectionException, IOException, NumbersException {
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
