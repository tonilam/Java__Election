/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.TreeMap;

/**
 * 
 * <p>Interface to specify a collection of <code>asgn1Election.Vote</code> objects and 
 * the operations permissible.</p> 
 * 
 * <p>Note to students: the guidance below is much stronger than would be given for a 
 * real interface. The class {@link asgn1Election.VoteCollection} must implement this 
 * interface. To assist you I have gutted the class but left many signatures and 
 * helper methods in place. It is up to you to complete the task, but you should 
 * certainly not expect to see a specification for the constructor of the implementing
 * class, as you have been given here.</p>
 * 
 * @author hogan
 * 
 */
public interface Collection {

	/**
	 * <p>Method to distribute preferences from the candidate with index <code>elim</code>, 
	 * the candidate with the fewest votes after the count of primaries and previous 
	 * distribution of preferences</p>
	 * 
	 * <p>The result of this method is that each of these votes will be distributed to 
	 * the next highest active preference. This method is obviously specific to the 
	 * preferential election.</p>
	 * 
	 * @param cds <code>TreeMap</code> containing the candidates still active in 
	 * this election. 
	 * @param elim <code>CandidateIndex</code> referring to the candidate with the 
	 * lowest number of votes; this candidate's votes will be distributed
	 */
	public void countPrefVotes(TreeMap<CandidateIndex, Candidate> cds,CandidateIndex elim);

	/**
	 * Method to count the <emphasis>primary</emphasis> or first preference votes 
	 * for each candidate in the election. This method is used in the simple election 
	 * and in the preferential election prior to the distribution of preferences. 
	 * 
	 * @param cds <code>TreeMap</code> set of candidates. Number of votes for each 
	 * candidate is increased by the number of votes from this <code>Collection</code>
	 * in which the candidate was the first choice.
	 */
	public void countPrimaryVotes(TreeMap<CandidateIndex, Candidate> cds);

	/**
	 * Simple method to empty the contents of the Collection 
	 */
	public void emptyTheCollection();

	/**
	 * Simple Getter to return the count of formal votes
	 * Note that <emphasis>only formal votes</emphasis> are stored in the collection
	 * 
	 * @return <code>int</code> containing count of formal votes
	 */
	public int getFormalCount();

	/**
	 * Simple Getter to return the count of votes informal under the present 
	 * election type. Note that <emphasis>only formal votes</emphasis> are 
	 *  stored in the collection
	 * 
	 * @return <code>int</code> containing count of informal votes
	 */
	public int getInformalCount();

	/**
	 * Method to allow a valid, formal vote to be added to the Collection. 
	 * Precondition: <code>isFormal(v)</code>; not tested in the method
	 * 
	 * @param v <code>Vote</code> to be added to the voteList
	 */
	public void includeFormalVote(Vote v);

	/**
	 * Simple method to record the presence of an informal vote by incrementing 
	 * the count. Note that informal votes are <emphasis>not</emphasis> stored 
	 * in the vote Collection. 
	 */
	public void updateInformalCount();
}
