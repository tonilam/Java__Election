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
import asgn1Election.Vote;
import asgn1Election.VoteList;
import asgn1Util.NumbersException;

/**
 * This class is used to test the function in class SimpleElection
 * @author Toni Lam
 */
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
	public void testLoadDefsAndVotes() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		for (Election election : eManager.getElectionList()) {		
			eManager.setElection(election);
		}
	}

	/** Purpose of the test:
	 * Try to read a elections list which is not exist in the system.
	 * precondition: fakeFileName is not exist in the system
	 * postcondition: throw FileNotFoundException
	 */
	@Test (expected = FileNotFoundException.class)
	public void testGetElectionsFromFileInvalidFilename() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String fakeFileName = "test_not_exist";
		eManager.getElectionsFromFile(fakeFileName);
	}


	/** Purpose of the test:
	 * check if the findWinner() function return the result in expected format.
	 * precondition: testing file not empty
	 * postcondition: result from the file match the expected string.
	 * @throws FileNotFoundException
	 * @throws ElectionException
	 * @throws IOException
	 * @throws NumbersException
	 */
	@Test
	public void testFindWinner() throws FileNotFoundException, ElectionException, IOException, NumbersException {
		String expectedString = "Results for election: MinMorgulValeSimple\n"
				+ "Enrolment: 25\n"
				+ "\n"
				+ "Shelob              Monster Spider Party          (MSP)\n"
				+ "Gorbag              Filthy Orc Party              (FOP)\n"
				+ "Shagrat             Stinking Orc Party            (SOP)\n"
				+ "\n"
				+ "\n"
				+ "Counting primary votes; 3 alternatives available\n"
				+ "\n"
				+ "Simple election: MinMorgulValeSimple\n"
				+ "\n"
				+ "Shelob (MSP)                 8\n"
				+ "Gorbag (FOP)                 8\n"
				+ "Shagrat (SOP)                3\n"
				+ "\n"
				+ "Informal                     4\n"
				+ "\n"
				+ "Votes Cast                  23\n"
				+ "\n"
				+ "\n"
				+ "Candidate Shelob (Monster Spider Party) is the winner with 8 votes...\n";
		String result;
		Election election = eManager.getElectionList().get(0);		
		
		eManager.setElection(election);
		result = eManager.manageCount();
		assertEquals(expectedString, result);
	}

	/** Purpose of the test:
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
