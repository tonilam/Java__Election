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
 * Subclass of <code>Election</code>, specialised to simple, first past the post voting
 * 
 * @author hogan
 * 
 */
public class SimpleElection extends Election {
	
	private String countStatus;

	/**
	 * Simple Constructor for <code>SimpleElection</code>, takes name and also sets the 
	 * election type internally. 
	 * 
	 * @param name <code>String</code> containing the Election name
	 */
	public SimpleElection(String name) {
		super(name);
		if (isValidType(SimpleElection.SimpleVoting)){
			this.type = SimpleElection.SimpleVoting;
		}
		winner = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#findWinner()
	 */
	@Override
	public String findWinner() {
		Candidate winner = clearWinner((vc.getFormalCount()+vc.getInformalCount())/2);
		return showResultHeader() + countStatus + reportCountResult() + reportWinner(winner);
	}

	/* 
	 * (non-Javadoc)
	 * @see asgn1Election.Election#isFormal(asgn1Election.Vote)
	 */
	@Override
	public boolean isFormal(Vote v) {
		// verifying election type
		if (type == Election.SimpleVoting) {
			return isFormalSimpleElectionVote(v);
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
		String str = this.name + " - Simple Voting";
		return str;
	}
	
	// Protected and Private/helper methods below///

	/*
	 * (non-Javadoc)
	 * 
	 * @see asgn1Election.Election#clearWinner(int)
	 */
	@Override
	protected Candidate clearWinner(int wVotes) {
		if (numCandidates > 0){
			countStatus = "Counting primary votes; " + numCandidates + " alternatives available\n";
			winner = cds.get(new CandidateIndex(1));
			vc.countPrimaryVotes(cds);
			for (Candidate cand : cds.values()) {
				if (cand.getVoteCount() > wVotes) {
					return cand;
				} else if (cand.getVoteCount() > winner.getVoteCount()) {
					winner = cand;
				}
			}
			return winner;
		}
		return null;
	}

	/**
	 * Helper method to create a string reporting the count result
	 * 
	 * @return <code>String</code> containing summary of the count
	 */
	private String reportCountResult() {
		String str = "\nSimple election: " + this.name + "\n\n"
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
	
	private boolean isFormalSimpleElectionVote(Vote v) {
		final int firstPreference = 1;
		HashSet<Integer> exploredPref = new HashSet<>(numCandidates);
		Iterator<Integer> voteIterator = v.iterator();
		
		while (voteIterator.hasNext()){
			int thisPref = voteIterator.next();
			if (thisPref == firstPreference) {
				if (!(exploredPref.add(thisPref))){
					/* violate simple election formal vote rule 2:
					 * There must be *only one* first preference vote i.e. 2 1 1 3 4 is informal 
					 */
					return false;
				}
			} else if ((thisPref < CandidateIndex.MinCandidates) || (thisPref > CandidateIndex.MaxCandidates) || (thisPref > numCandidates)) {
				/* violate simple election formal vote rule 3:
				 * We will be strict, and allow only preferences for valid candidates.
				 */
				return false;
			}
		}
		if (!exploredPref.contains(firstPreference)) {
			/* violate simple election formal vote rule 1:
			 * There must be a first preference vote i.e. the number 1 must appear in the vote.
			 */
			return false;
		}

		return true;
	}
}