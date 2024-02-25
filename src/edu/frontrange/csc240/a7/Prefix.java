
package edu.frontrange.csc240.a7;

/**
 * The course prefix is a designated mnemonic for the Department of the College
 * responsible for that subject matter area.
 *
 * @author			Dr. Bruce K. Haddon, Instructor
 * @version			2.0, 2016-08-22, CSC-240 Assignment 6, per the Assignment
 *					Instructions
 */
public enum Prefix
{
/**
 * Computer Information Systems.
 */
CIS("Computer Information Systems"),

/**
 * Computer Science.
 */
CSC("Computer Science"),

/**
 * Computer Networking.
 */
CNG("Computer Networking"),

/**
 * Computer Web-based.
 */
CWB("Computer Web-based");

/**
 * Store the title corresponding to this prefix.
 */
private final String prefixTitle;

/**
 * Constructor: sets the title of the department for which this is the
 * three-letter prefix.
 *
 * @param prefixTitle	the title of the department
 */
private Prefix(String prefixTitle)
{
	this.prefixTitle = prefixTitle;
}

/**
 * The title of the department for which this is the three-letter prefix.
 *
 * @return	the title of the department
 */
public String getTitle()
{
	return prefixTitle;
}

///**
// * Main method to conduct some tests on the degree class.
// *
// * @param args		not used.
// */
//public static void main(String[] args)
//{
//	for( Prefix prefix : Prefix.values() )
//		System.out.printf("%8s  %s%n", prefix.name(), prefix.getTitle());
//}
}
