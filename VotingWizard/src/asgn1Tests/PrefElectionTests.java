package asgn1Tests;

import static org.junit.Assert.assertEquals;
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
import asgn1Election.PrefElection;
import asgn1Election.Vote;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

/**
 * This class is used to test the function in class PrefElection
 * 
 * @author Toni Lam
 */
public class PrefElectionTests {
	
	ElectionManager eManager;
	String prefElectionsList;

	@Before @Test /* Initialize election manager */
	public void setUp() throws FileNotFoundException, IOException, ElectionException, NumbersException {
		prefElectionsList = ".//data//devtestdata//pref_elections.lst";
	}
	
	/** Purpose of the test:
	 * Try to load all elections as set in elections.lst
	 * precondition: eManager is not null and contains at least one election in the list
	 * postcondition: no error 
	 */
	@Test
	public void testLoadDefs() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		eManager = new ElectionManager();
		eManager.getElectionsFromFile(prefElectionsList);
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
		String invalidDefsList = ".//data//devtestdata//pref_elections_invalid_list.lst";
		eManager = new ElectionManager();
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
		String invalidDefsList = ".//data//devtestdata//pref_elections_invalid_electorate.lst";
		eManager = new ElectionManager();
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
		eManager = new ElectionManager();
		eManager.getElectionsFromFile(prefElectionsList);	
		
		for (Election election : eManager.getElectionList()) {		
			election.loadDefs();
			election.loadVotes();
		}
	}
	
	/** Purpose of the test:
	 * Try to load one election file and check if the data loaded is correct.
	 * precondition: the election file is not empty
	 * postcondition: all value is as expected
	 */
	@Test
	public void testLoadDefsVerifyDefsData() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String expectedSeat = "MorgulVale";
		int expectedNumCandidate = 5;
		String expectedCandidatesList = "[Shelob (MSP)                 0\n" +
										", Gorbag (FOP)                 0\n" +
										", Shagrat (SOP)                0\n" +
										", Black Rider (NP)             0\n" +
										", Mouth of Sauron (WSSP)       0\n]";
		String checkingElection = "MorgulVale";
		
		Election tester = new PrefElection(checkingElection);
		tester.loadDefs();
		
		assertEquals(expectedSeat, tester.getName());
		assertEquals(expectedNumCandidate, tester.getNumCandidates());
		assertEquals(expectedCandidatesList, tester.getCandidates().toString());
	}
	

	/** Purpose of the test:
	 * Try to load one vote file and check if the data loaded is correct.
	 * precondition: the vote file is not empty
	 * postcondition: the initial vote count shown on the result is as expected
	 */
	@Test
	public void testLoadVotesVerifyVotesData() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String
			expectedLine14 = "Shelob (MSP)                 9",
			expectedLine15 = "Gorbag (FOP)                 5",
			expectedLine16 = "Shagrat (SOP)                4",
			expectedLine17 = "Black Rider (NP)             9",
			expectedLine18 = "Mouth of Sauron (WSSP)       3";
		String checkingElection = "MorgulVale";
		Election tester = new PrefElection(checkingElection);
		
		tester.loadDefs();
		tester.loadVotes();
		String resultLines[] = tester.findWinner().trim().split("\n");
		
		assertEquals(expectedLine14, resultLines[14]);
		assertEquals(expectedLine15, resultLines[15]);
		assertEquals(expectedLine16, resultLines[16]);
		assertEquals(expectedLine17, resultLines[17]);
		assertEquals(expectedLine18, resultLines[18]);
	}
	
	/** Purpose of the test:
	 * Try to load all elections as set in elections.lst
	 * precondition: eManager is not null and contains at least one invalid election in the list
	 * postcondition: FileNotFoundException thrown
	 */
	@Test (expected = FileNotFoundException.class)
	public void testLoadVotesInvalidVotes() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidVotesList = ".//data//devtestdata//pref_elections_missing_vote.lst";
		eManager = new ElectionManager();
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
		String invalidVotesList = ".//data//devtestdata//pref_elections_defsnumberexception.lst";
		eManager = new ElectionManager();
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
	@Test (expected = NumbersException.class)
	public void testLoadVotesNumberException() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String invalidVotesList = ".//data//devtestdata//pref_elections_numberexception.lst";
		eManager = new ElectionManager();
		eManager.getElectionsFromFile(invalidVotesList);
		
		for (Election election : eManager.getElectionList()) {
			eManager.setElection(election);
		}
	}

	/** Purpose of test:
	 * check if the findWinner work properly
	 * Hints: ElectionManager.manageCount() will call findWinner for all election stored.
	 * precondition: eManager contains at least one preference election
	 * postcondition: result is as the expect string
	 * @throws FileNotFoundException
	 * @throws ElectionException
	 * @throws IOException
	 * @throws NumbersException
	 */
	@Test
	public void testFindWinner() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String expectedResult = "Results for election: MinMorgulVale\n"
				+ "Enrolment: 25\n"
				+ "\n"
				+ "Shelob              Monster Spider Party          (MSP)\n"
				+ "Gorbag              Filthy Orc Party              (FOP)\n"
				+ "Shagrat             Stinking Orc Party            (SOP)\n"
				+ "\n"
				+ "\n"
				+ "Counting primary votes; 3 alternatives available\n"
				+ "\n"
				+ "Preferential election: MinMorgulVale\n"
				+ "\n"
				+ "Shelob (MSP)                 8\n"
				+ "Gorbag (FOP)                 7\n"
				+ "Shagrat (SOP)                3\n"
				+ "\n"
				+ "Informal                     3\n"
				+ "\n"
				+ "Votes Cast                  21\n"
				+ "\n"
				+ "\n"
				+ "Preferences required: distributing Shagrat: 3 votes\n"
				+ "\n"
				+ "Preferential election: MinMorgulVale\n"
				+ "\n"
				+ "Shelob (MSP)                10\n"
				+ "Gorbag (FOP)                 8\n"
				+ "\n"
				+ "Informal                     3\n"
				+ "\n"
				+ "Votes Cast                  21\n"
				+ "\n"
				+ "\n"
				+ "Candidate Shelob (Monster Spider Party) is the winner with 10 votes...\n";
		String result;
		eManager = new ElectionManager();
		eManager.getElectionsFromFile(prefElectionsList);
		
		Election election = eManager.getElectionList().get(0);	
		eManager.setElection(election);
		result = eManager.manageCount();
		assertEquals(expectedResult, result);
	}

	/** Purpose of test:
	 * This test is used to pick up an election and evaluate each sample vote to check if inFormal()
	 * function returns the expected result
	 * precondition: the checking file is not empty
	 * postcondition: pass all test
	 * @throws FileNotFoundException
	 * @throws ElectionException
	 * @throws IOException
	 * @throws NumbersException
	 */
	@Test
	public void testIsFormal() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		Election tester;
		Vote v;
		
		eManager = new ElectionManager();
		String prefElectionsList = ".//data//devtestdata//pref_elections_formal_informal.lst";
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
