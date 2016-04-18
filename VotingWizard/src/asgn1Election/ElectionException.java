/**
 * 
 * This file is part of the VotingWizard Project, written as 
 * part of the assessment for CAB302, Semester 1, 2016. 
 * 
 */
package asgn1Election;

/**
 * 
 * This class represents exceptions associated with <code>Election</code>
 * and associated classes. 
 * 
 * @author hogan
 * 
 */
@SuppressWarnings("serial")
public class ElectionException extends Exception {
	/**
	 * Constructor for <code>Exception</code>s relating to <code>Election</code>
	 * 
	 * @param msg <code>String</code> containing message for the user
	 */
	public ElectionException(String msg) {
		super("Election Exception: " + msg);
	}
}
