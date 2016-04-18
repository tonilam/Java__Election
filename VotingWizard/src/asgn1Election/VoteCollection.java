/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 
 * <p>Class to manage a collection of <code>Vote</code>s as specified by the 
 * {@link asgn1Election.Collection} interface. This implementation is based on  
 * a <code>ArrayList<E></code> data structure to enable convenient additions to the 
 * list.</p>
 * 
 * <p>The private methods {@link #getPrimaryKey(Vote) } and 
 * {@link #getPrefthKey(Vote, TreeMap, int)} are crucial to your success. Some guidance 
 * is given for these methods through comments in situ, but this is generous, and well 
 * beyond what would be provided in real practice.</p>
 * 
 * <p>As before, please note the name clash between <code>asgn1Election.Collection</code>
 * and <code>java.util.Collection</code>. 
 * 
 * @author hogan
 *
 */
public class VoteCollection implements Collection {
	/** Holds all the votes in this seat */
	private ArrayList<Vote> voteList;
	
	private HashSet<String> elimilatedCandidates;
	
	@SuppressWarnings("unused")
	private ArrayList<Candidate> candidates;

	/** Number of candidates competing for this seat */
	@SuppressWarnings("unused")
	private int numCandidates;

	/** Number of formal votes read during the election and stored in the collection */
	private int formalCount;

	/** Number of invalid votes received during the election */
	private int informalCount;

	/**
	 * Simple Constructor for the <code>VoteCollection</code> class.
	 * Most information added through mutator methods. 
	 * 
	 * @param numCandidates <code>int</code> number of candidates competing for 
	 * this seat
	 * @throws ElectionException if <code>NOT inRange(numCandidates)</code>
	 */
	public VoteCollection(int numCandidates) throws ElectionException {
		if (CandidateIndex.inRange(numCandidates)) {
			this.numCandidates = numCandidates;
			formalCount = 0;
			informalCount = 0;
			voteList = new ArrayList<Vote>();
			elimilatedCandidates = new HashSet<String>();
		} else {
			throw new ElectionException("number of candidates out of range");
		}
	}
	
	/* 
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#countPrefVotes(java.util.TreeMap, asgn1Election.CandidateIndex)
	 */
	@Override
	public void countPrefVotes(TreeMap<CandidateIndex, Candidate> cds,
			CandidateIndex elim) {
		boolean newTransfer;
		elimilatedCandidates.add(elim.toString());
		for (Vote validVote : voteList) {
			newTransfer = false;
			Iterator<Integer> votePrefth = validVote.invertVote().iterator();
			CandidateIndex firstPreference = new CandidateIndex(votePrefth.next());
			if (firstPreference.compareTo(elim) == 0) {
				newTransfer = true;
			}
			
			// transfer eliminated candidate's vote to next pref
			if (elimilatedCandidates.contains(firstPreference.toString())) {
				boolean voteTransferred = false;
				while (!voteTransferred) {
					// find out the next live pref
					if (elimilatedCandidates.contains(firstPreference.toString())) {
						firstPreference = new CandidateIndex(votePrefth.next());
						if (firstPreference.compareTo(elim) == 0) {
							newTransfer = true;
						}
					} else {
						voteTransferred = true;
					}
				}
				if (newTransfer){
					cds.get(firstPreference).incrementVoteCount();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#countPrimaryVotes(java.util.TreeMap)
	 */
	@Override
	public void countPrimaryVotes(TreeMap<CandidateIndex, Candidate> cds) {
		for (Vote validVote : voteList) {
			// only look into the fist preference of each vote and count for it.
			CandidateIndex firstPreference = new CandidateIndex(validVote.invertVote().iterator().next());
			cds.get(firstPreference).incrementVoteCount();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#emptyTheVoteList()
	 */
	@Override
	public void emptyTheCollection() {
		voteList.clear();
		formalCount = 0;
		informalCount = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#getFormalCount()
	 */
	@Override
	public int getFormalCount() {
		return formalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#getInformalCount()
	 */
	@Override
	public int getInformalCount() {
		return informalCount;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#includeFormalVote(asgn1Election.Vote)
	 */
	@Override
	public void includeFormalVote(Vote v) {
		// Precondition: isFormal(v) not tested in the method
		voteList.add(v);
		++formalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Collection#updateInformalCount()
	 */
	@Override
	public void updateInformalCount() {
		++informalCount;
	}
	
	/**
	 * 
	 * <p>Important helper method to find the candidate in the current vote 
	 * corresponding to a given preference. Ideally we seek the candidate who is 
	 * preference <emphasis>pref</emphasis>, but often some of the higher preferences
	 * for a given vote may already have been eliminated. So this method finds not 
	 * the <emphasis>pref-th</emphasis> candidate, but the pref-th 
	 * <emphasis>active</emphasis> candidate</p>
	 * 
	 * <p>You must therefore find a way to deal with the candidate set, to work out 
	 * which ones are still active and which aren't. This method is quite specific 
	 * to the preferential election and so does not get used for the simple ballot.</p>
	 * 
	 * @param v <code>Vote</code> to be examined to find the pref-th active candidate
	 * @param cds <code>TreeMap</code> set of all active candidates
	 * @param pref <code>int</code> specifies the preference we are looking for
	 * @return <code>(key = prefth preference still active) OR null</code>
	 * 
	 */
	@SuppressWarnings("unused")
	private CandidateIndex getPrefthKey(Vote v,TreeMap<CandidateIndex, Candidate> cds, int pref) {
		ArrayList<CandidateIndex> activeCandidateIndex = new ArrayList<>(cds.keySet());
		System.out.println(activeCandidateIndex);
		return activeCandidateIndex.get(0);
	}

	/**
	 * <p>Important helper method to find the first choice candidate in the current 
	 * vote. This is always undertaken prior to distribution of preferences and so it 
	 * is not necessary to test whether the candidate remains in the set.</p>
	 * 
	 * @param v <code>Vote</code> from which first pref is to be obtained
	 * @return <code>CandidateIndex</code> of the first preference candidate
	 */
	@SuppressWarnings("unused")
	private CandidateIndex getPrimaryKey(Vote v) {
		return v.invertVote().getPreference(0);
	}
}
