/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

import java.util.HashSet;
import java.util.Iterator;

import asgn1Util.Strings;

/**
 * 
 * Subclass of <code>Election</code>, specialised to preferential, but not optional
 * preferential voting.
 * 
 * @author hogan
 * 
 */
public class PrefElection extends Election {
	
	// A string to store the count status on each preference distribution
	private String countStatus;
	
	// A set to store the eliminated candidates' index
	private HashSet<CandidateIndex> eliminatedCandidates;
	
	/**
	 * Simple Constructor for <code>PrefElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public PrefElection(String name) {
		super(name);
		if (isValidType(SimpleElection.PrefVoting)){
			this.type = SimpleElection.PrefVoting;
		}
		eliminatedCandidates = new HashSet<CandidateIndex>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {
		String resultHeader = showResultHeader();
		Candidate winner = clearWinner(vc.getFormalCount()/2);
		return resultHeader + countStatus + reportCountStatus() + reportWinner(winner);

	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		// verifying election type
		if (type == Election.PrefVoting) {
			return isFormalPreferentialElectionVote(v);
		} else {
			System.out.println("Vote type not supported!");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        String str = this.name + " - Preferential Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///


	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int winVotes) {
		if (numCandidates > 0){
			countStatus = "Counting primary votes; " + numCandidates + " alternatives available\n";
			vc.countPrimaryVotes(cds);
			for (Candidate incumbent : cds.values()) {
				if (incumbent.getVoteCount() > winVotes) {
					return incumbent;
				}
			}
			// No majority winner, then process follow:
			boolean foundClearWinner = false;
			while (!foundClearWinner) {
				CandidateIndex elim = selectLowestCandidate();
				countStatus += reportCountStatus() + prefDistMessage(cds.get(elim)) + "\n";
				vc.countPrefVotes(cds, elim);
				eliminatedCandidates.add(elim);
				cds.remove(elim);
				for (java.util.Map.Entry<CandidateIndex, Candidate> entry: cds.entrySet()) {
					if (entry.getValue().getVoteCount() > winVotes) {
						foundClearWinner = true;
						return entry.getValue();
					}
				}
			}
			return null;
		}
		return null;
	}

	/**
	 * Helper method to create a preference distribution message for display 
	 * 
	 * @param c <code>Candidate</code> to be eliminated
	 * @return <code>String</code> containing preference distribution message 
	 */
	private String prefDistMessage(Candidate c) {
		String str = "\nPreferences required: distributing " + c.getName()
				+ ": " + c.getVoteCount() + " votes";
		return str;
	}

	/**
	 * Helper method to create a string reporting the count progress
	 * 
	 * @return <code>String</code> containing count status  
	 */
	private String reportCountStatus() {
		String str = "\nPreferential election: " + this.name + "\n\n"
				+ candidateVoteSummary() + "\n";
		String inf = "Informal";
		String voteStr = "" + this.vc.getInformalCount();
		int length = ElectionManager.DisplayFieldWidth - inf.length()
				- voteStr.length();
		str += inf + Strings.createPadding(' ', length) + voteStr + "\n\n";

		String cast = "Votes Cast";
		voteStr = "" + this.numVotes;
		length = ElectionManager.DisplayFieldWidth - cast.length()
				- voteStr.length();
		str += cast + Strings.createPadding(' ', length) + voteStr + "\n\n";
		return str;
	}

	/**
	 * Helper method to select candidate with fewest votes
	 * 
	 * @return <code>CandidateIndex</code> of candidate with fewest votes
	 */
	private CandidateIndex selectLowestCandidate() {
		CandidateIndex lowestIndex = cds.firstKey();
		Candidate lowestCand = cds.get(lowestIndex);
		for (CandidateIndex candIndex : cds.navigableKeySet()) {
			Candidate scanningCand = cds.get(candIndex);
			
			if (!eliminatedCandidates.contains(candIndex)) {
				if (scanningCand.getVoteCount() < lowestCand.getVoteCount()) {
					lowestIndex = candIndex;
					lowestCand = cds.get(lowestIndex);
				}
			}
		}
		return lowestIndex;
	}
	
	/**
	 * Helper function to determine a vote is formal or not based on preferential election rules.
	 * @param v
	 * @return true if is formal
	 * 		   false if informal
	 */
	private boolean isFormalPreferentialElectionVote(Vote v) {
		HashSet<Integer> exploredPref = new HashSet<>(numCandidates);
		Iterator<Integer> voteIterator = v.iterator();
		int thisPref;
		
		while (voteIterator.hasNext()){
			thisPref = voteIterator.next();
			
			if (!(exploredPref.add(thisPref))){
				// immediately return the vote is informal once there exits duplicated preference
				return false;
			}
		
			if (!CandidateIndex.inRange(thisPref) || (thisPref > numCandidates)) {
				// immediately return the vote is informal once the preference in out of range
				return false;
			}
		}
		
		// all element in the vote is added and no invalid preference found, then said to be formal vote
		return true;
	}
}