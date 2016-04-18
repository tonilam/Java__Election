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
import java.util.List;
import java.util.Set;

/**
 * 
 * <p>Implementing class for the {@link asgn1Election.Vote} interface. <code>Vote</code> 
 * should be implemented as some sort of <code>List</code>, with 
 * <code>ArrayList<Integer></code> the default choice.</p>
 * 
 * @author hogan
 * 
 */
public class VoteList implements Vote {
	/** Holds the information that comprises a single vote */
	private List<Integer> vote;

	/** Number of candidates in the election */
	private int numCandidates;

	/**
	 * <p>Simple Constructor for the <code>VoteList</code> class. <code>numCandidates</code> 
	 * is known to be in range through check on <code>VoteCollection</code>. 
	 * 
	 * @param numCandidates <code>int</code> number of candidates competing for 
	 * this seat. 
	 */
	public VoteList(int numCandidates) {
		this.numCandidates = numCandidates;
		vote = new ArrayList<Integer>(numCandidates);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#addPref(asgn1Election.CandidateIndex)
	 */
	@Override
	public boolean addPref(int index) {
		if (vote.size() < numCandidates) {
			return vote.add(index);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#copyVote()
	 */
	@Override
	public Vote copyVote() {
		Vote deepCopy = new VoteList(this.numCandidates);
		for (int item : vote) {
			deepCopy.addPref(item);
		}
		
		return deepCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#getPreference(int)
	 */
	@Override
	public CandidateIndex getPreference(int cand) {
		return new CandidateIndex(vote.indexOf(cand));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Vote#invertVote()
	 */
	@Override
	public Vote invertVote() {
		final int firstPreferenceIndex = 0;
		final int lastPreferenceIndex = this.vote.size() - 1;
		List<Integer> sortedVote = new ArrayList<Integer>(this.vote);
		Vote invertedVote = new VoteList(numCandidates);;

		/* find out the smallest element in the remain list and 
		 * replace the current preferrenceVote with the smallest element
		 */
		for (int key1 = firstPreferenceIndex; key1 <= lastPreferenceIndex; ++key1) {
			int minKey = key1;
			// find the smallest element in the array (from next(key1) to the end)
			// and compare with key1
			final int currentStartingIndexToCompare = key1 + 1;
			final int firstLoopStartingIndexToCompare = firstPreferenceIndex + 2;
			for (int key2 = currentStartingIndexToCompare; key2 <= lastPreferenceIndex; ++key2) {
				if (sortedVote.get(minKey) > sortedVote.get(key2)) {
					minKey = key2;
				}
				if ((currentStartingIndexToCompare > firstLoopStartingIndexToCompare) && (minKey == key1)) {
					//if  no swap in the first loop, escape form keep looping
					break;
				}
			}
			
			if (minKey != key1) {
				int newMinValue = sortedVote.get(minKey);
				sortedVote.set(minKey, sortedVote.get(key1));
				sortedVote.set(key1, newMinValue);
			}
		}

		// use set to handle duplicated preference
		Set<Integer> explored = new HashSet<Integer>(this.vote.size());
		for (int preference : sortedVote) {
			int incumbentVoteId = this.vote.indexOf(preference) + 1;
			while (explored.contains(incumbentVoteId) && incumbentVoteId <= this.vote.size()) {
				List<Integer> sublist = this.vote.subList(incumbentVoteId, this.vote.size());
				incumbentVoteId = incumbentVoteId + sublist.indexOf(preference) + 1;
			}
			invertedVote.addPref(incumbentVoteId);
			explored.add(incumbentVoteId);
		}
		return invertedVote;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Integer> iterator() {
		return vote.iterator();
	}

	/*
	 * (non-Javadoc
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "";
		for (Integer index : this.vote) {
			str += index.intValue() + " ";
		}
		return str;
	}
}
