package edu.frontrange.csc240.a7;
/**
 * This class acts as a wrapper to the super class and using the 
 * super class's methods, while using the same constructors
 *
 * @author		Luc Gremillion, S02597411
 * @version             3/13/2022, CSC-240 Assignment 7	
 */
@SuppressWarnings("serial")
public class SectionException extends Exception{
/**
 * Creates an exception with no inputs to return
 * no information about the exception.
 *
 * @see	Exception
 */    
public SectionException(){
	super();
}
/**
 * Creates an exception with the throw-able cause to return
 * a message when needed about why the cause had a certain error
 *
 * @param cause             Throw-able cause
 * 
 * @see	Exception
 */ 
public SectionException(Throwable cause){
	super(cause);
}
/**
 * Creates an exception with the message received from another 
 * program and to return the message when needed
 *			
 * @param message               Detailed Message about the error
 * 
 * @see	Exception
 */
public SectionException(String message){
	super(message);
}
/**
 * Creates an exception with the throw-able cause and with the message received from another 
 * program to return a message of what error was thrown and what the message from the program that threw the error said
 *
 * @param message               Detailed Message about the error
 * @param cause                 Throw-able cause
 * 
 * @see	Exception
 */
public SectionException(String message, Throwable cause){
	super(message, cause);
}  
  
}
