/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

/**
 * 
 * <p>Interface to specify a collection of <code>Integers</code> representing a vote.</p> 
 * 
 * <p><code>Vote</code> extends {@link java.lang.Iterable} to allow simple 
 *  traversal of the entries in the vote. The method {@link java.lang.Iterable#iterator()} 
 *  must appear in the implementing class.</p>
 * 
 * <p>Note to students: the guidance below is much stronger than would be given for a 
 * real interface. You should assume that the implementing class restricts the vote entries 
 * to the number of candidates in an election, and that this is a parameter. The class 
 * {@link asgn1Election.VoteList} must implement this interface. To assist you I have 
 * gutted the class but left many signatures in place. It is up to you to complete the 
 * task, but you should certainly not expect to see a specification for the constructor 
 * of the implementing class, as you have been given here.</p>
 * 
 * @author hogan
 * 
 */
public interface Vote extends Iterable<Integer> {
	
	/**
	 * <p>Method adds the next preference to the vote structure.</p>
	 * 
	 * <p>Note that the index given need not be valid - the <code>Vote</code> type may 
	 * include informal votes, which are detected elsewhere.</p>
	 * 
	 * @param index <code>int</code> containing preference to be stored
	 * @return <code>boolean</code> status: <code>true</code> if pref added; <code>false</code> if 
	 * vote already contained <code>numCandidates</code> otherwise
	 */
	public boolean addPref(int index);

	/**
	 * Simple method to create a deep copy of the vote
	 * 
	 * @return a deep copy of this <code>Vote</code>
	 */
	public Vote copyVote();

	/**
	 * Method to find the index of the <code>pref</code>-th most preferred candidate 
	 * in this vote
	 * 
	 * @param pref <code>int</code> specifying the preference to be returned
	 * @return <code>CandidateIndex</code> of the candidate corresponding to the preference. 
	 */
	public CandidateIndex getPreference(int pref);

	/**
	 * Method to invert the vote to yield the preference order of candidates.
	 * 
	 * @return <code>Vote newVote</code> such that for all entries <code>i</code> of 
	 * <code>this</code>, <code>newVote[this[i]] = i</code>
	 */
	public Vote invertVote();
}
