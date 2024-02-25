
package edu.frontrange.csc240.a7;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import software.haddon.util.ObjectCounter;

/**
 * A section is a sub-division of a course. A course may have multiple sections
 * when there are too many students in the course for them all to attend at the
 * same place at the same time with the same Instructor.
 *
 * @author		Dr. Bruce K. Haddon, Instructor
 * @author              Luc Gremillion, S02597411
 * @version		3.0, 2022-03-13, CSC-240 Assignment 7, per the Assignment
 *				Instructions
 */
public class Section
{
/**
 * The maximum number of students permitted into a section.
 */
public static final int MAXIMUM_STUDENTS_PER_SECTION = 32;

/**
 * Valid length for a sectionNumber string.
 */
public static final int SECTION_NUMBER_LENGTH = 3;

/**
 * Default section number.
 */
private static final String DEFAULT_SECTION_NUMBER = "";

/**
 * Constant for building Strings with newline characters within them.
 */
private static final String LINE_SEPARATOR = System.getProperty("line.separator");

/**
 * Required object counter field.
 */
final ObjectCounter counter = new ObjectCounter(this);

/**
 * The date at which the section is finished.
 */
private FRCCDate endDate;

/**
 * The end time for the meeting of the section.
 */
private FRCCTime endTime;

/**
 * The list of students in the class. This declaration uses the Java 7 facility
 * of not repeating the generic type if that type can be inferred by the
 * compiler.
 */
private final List<Student> roster = new ArrayList<>();

/**
 * The three-character designation of the section (called a
 * &ldquo;section number&rdquo;).
 */
private String sectionNumber;

/**
 * The date on which the section starts to meet.
 */
private FRCCDate startDate;

/**
 * The time of day at which the section meets.
 */
private FRCCTime startTime;

/**
 * The course of which this is a section.
 */
private final Course thisCourse;

/**
 * Constructor.
 *
 * @param course		the course of which this is a section
 * @param sectionNumber	the section number (within the course) of this section
 * @throws SectionException if an error is found in the program
 */
public Section(Course course, String sectionNumber) throws SectionException
{
	/* No checking needed as a course is defined and checked by another class. */
	this.thisCourse = course;
        try{
            this.sectionNumber = DEFAULT_SECTION_NUMBER;
            if( isValidSectionNumber(sectionNumber) )
		this.sectionNumber = sectionNumber;
        }catch(ValidationException ex){
            throw new SectionException("SectionException has found an error: " + ex);
        }
	
}

/**
 * Add a student to the course.
 *
 * @param student		the student object to be added. If the course is full,
 *						the student is not added
 * @throws IllegalArgumentException if the roster size is null
 * @throws IllegalStateException if the roster size gets oversized
 */
public void addStudent(Student student)  throws IllegalArgumentException, IllegalStateException
{
	if( student == null )
            throw new IllegalArgumentException("Student value is null");//change
        if( roster.size() < MAXIMUM_STUDENTS_PER_SECTION )
			roster.add(student);
//        if( roster.size() > MAXIMUM_STUDENTS_PER_SECTION )
//            throw new IllegalArgumentException("Student value is max");
        else
            throw new IllegalStateException("Student value is at the max size");
        
        
        
}

/**
 * Get details about the current state of this section, including the course of
 * which it is part, the dates it starts and ends, times, etc., and the current
 * count of the enrollment.
 *
 * @return				the section details
 */
public String getDetails()
{
	return String.join(LINE_SEPARATOR,
			"Section: " + this.toString(),
			"Course: " + thisCourse.getDetails(),
			"Dates: " + startDate + " to " + endDate,
			"Times: " + startTime + " to " + endTime,
			"Enrollment: " + roster.size());
}

/**
 * Create a string that represents the information about the students in the
 * course.
 *
 * @return				a string that represents the information about the students
 *						in the course
 */
public String getRoster()
{
	/* The following commented-out code is the obvious way to do this, using
	   String concatenation (and this is acceptable). However, the recommended
	   Java approach to this kind of operation is to use a StringJoiner (new
	   class in Java 8), as this uses less garbage collection resources. */
//	String result = "";
//	for( Student student : roster )
//	{
//		result += ( result.isEmpty() ? "" : LINE_SEPARATOR) + student;
//	}
//	return result;

	StringJoiner stringJoiner = new StringJoiner(LINE_SEPARATOR);
	for( var student : roster )
		stringJoiner.add(student.toString());
	return stringJoiner.toString();
}

/**
 * Get a count of the number of students registered (on the roster) for this course.
 *
 * @return				a count of the number of students registered for this course
 */
public int getRosterCount()
{
	return roster.size();
}

/**
 * Get the section number for this course.
 *
 * @return				the section number for this course
 */
public String getSectionNumber()
{
	return sectionNumber;
}

/**
 * If any Student is pursuing the indicated degree, return true, otherwise false.
 *
 * @param degree		a degree that a Student may be pursuing
 * @return				true if a Student is pursuing the indicated degree
 */
public boolean isDegree(Degree degree)
{
	/* Search the Students in the roster for the given degree. If at least one
	   Student is pursuing that degree, return true. */
	for( Student student : roster )
		if( student.getDegree() == degree ) return true;
	return false;
}

/**
 * Set the start and end dates for the section.
 *
 * @param startDate		the start date
 * @param endDate		the end date
 * @throws AssertionError if either startDate or endDate have incorrect value
 */
public void setDates(FRCCDate startDate, FRCCDate endDate) throws AssertionError
{
	if(startDate == null)
            throw new AssertionError("Invalid Start Date: " + startDate);
	this.startDate = startDate;
        
        if(endDate == null)
            throw new AssertionError("Invalid End Date: " + endDate);
	this.endDate = endDate;
        
}

/**
 * Set the start time and the end time for the meetings of the section.
 *
 * @param startTime		the start time for meetings of the section
 * @param endTime		the end time for the meetings of the section
 * @throws AssertionError if either startTime or endTime have incorrect value
 */
public void setTimes(FRCCTime startTime, FRCCTime endTime) throws AssertionError
{
	if(startTime == null)
            throw new AssertionError("Invalid Start Time: " + startTime);
	this.startTime = startTime;
        if(endTime == null)
            throw new AssertionError("Invalid End Time: " + endTime);
	this.endTime = endTime;
}

/**
 * Section number (prefixed)
 *
 * @return				Section number (prefixed)
 */
@Override
public String toString()
{
	return thisCourse.toString() + "-" + sectionNumber;
}

/**
 * Validate the sectionNumber string. It must not be null or blank, and
 * be of the correct length.
 *
 * @param sectionNumber	the sectionNumber string
 * @return				true if the string if valid, otherwise false
 * @throws ValidationException if the section number is wrong
 */
private static boolean isValidSectionNumber(String sectionNumber) throws ValidationException
{
	if(sectionNumber != null && !sectionNumber.isBlank() &&
			sectionNumber.length() == SECTION_NUMBER_LENGTH) 
            return true;
        throw new ValidationException("Invalid Section Number: " + sectionNumber);
}
}
