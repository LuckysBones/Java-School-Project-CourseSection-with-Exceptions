
package edu.frontrange.csc240.a7;

import software.haddon.util.ObjectCounter;

/**
 * A Course is an offering in the College system. A number of Sections may be
 * associated with each course. A Course has a standard prefix (to indicate the
 * department, a number within that department, and a name. Each course is worth
 * a given number of credit hours per semester.
 *
 * @author		Dr. Bruce K. Haddon, Instructor
 * @author              Luc Gremillion, S02597411
 * @version		3.0, 2022-03-13, CSC-240 Assignment 7, per the Assignment
 *				Instructions
 */
public class Course
{
/**
 * Default credits value, used if no credits set.
 * <p>
 * (Public so that this value will be part of the API.)
 */
public static final int MISSING_CREDITS_VALUE = -1;

/**
 * Maximum permitted credits for a course.
 * <p>
 * (Public so that this value will be part of the API.)
 */
public static final int MAXIMUM_CREDITS = 5;

/**
 * Minimum permitted credits for a course.
 * <p>
 * (Public so that this value will be part of the API.)
 */
public static final int MINIMUM_CREDITS = 0;

/**
 * Valid length for a courseNum String.
 */
public static final int COURSE_NUMBER_LENGTH = 3;

/**
 * Default course name value.
 */
private static final String DEFAULT_COURSE_NAME = "";

/**
 * Default course number value.
 */
private static final String DEFAULT_COURSE_NUMBER = "";

/**
 * Required object counter field.
 */
final ObjectCounter counter = new ObjectCounter(this);

/**
 * The name given to the course.
 */
private String courseName;

/**
 * The three-character &ldquo;course number&rdquo;.
 */
private String courseNum;

/**
 * The prefix (i.e., department) for the course.
 */
private final Prefix coursePrefix;

/**
 * The number of credits allocated to this course.
 */
private int credits;

/**
 * Constructor (1): Set the course prefix (i.e., the department in which the course
 * is being taught), the number of the course within the department, and the name
 * of the course.Set the number of credits to the default to indicate that the
 number of credits has not been set.
 *
 * @param coursePrefix	the three-letter department prefix (represented by a
 *						member of the Prefix enumeration)
 * @param courseNum		the three-character course &ldquo;number&rdquo; within
 *						the department (replaced by the default number if not three
 *						characters)
 * @param courseName	the name of the course (replaced by the default name if the
 *						string is null or empty
 * @throws CourseException if a problem accrues in the program
 */
public Course(Prefix coursePrefix, String courseNum, String courseName) throws CourseException
{
    this.coursePrefix = coursePrefix;
    
    try {
        if( isValidCourseNum(courseNum) )
            this.courseNum = courseNum;
        /* If the course name is valid, set courseName to it. */
        this.courseName = DEFAULT_COURSE_NAME;
        if( isValidCourseName(courseName) )
            this.courseName = courseName;
        this.credits = MISSING_CREDITS_VALUE;
    } catch (ValidationException ex) {
        throw new CourseException("CourseException has found an error: " + ex);
    }
    
    
    
}
    



/**
 * Constructor (2): Set the course prefix (i.e., the department in which the course
 * is being taught), the number of the course within the department, and the name
 * of the course.Set the number of credits to the given value.
 *
 * @param coursePrefix	the three-letter department prefix (represented by a
 *						member of the Prefix enumeration)
 * @param courseNum		the three-character course &ldquo;number&rdquo; within
 *						the department (replaced by the default number if not three
 *						characters)
 * @param courseName	the name of the course (replaced by the default name if the
 *						string is null or empty
 * @param credits		the number of semester credits for the course, in the range
						MINIMUM_CREDITS to MAXIMUM_CREDITS; if outside this range,
						the number of credits is replaced by the value
						MISSING_CREDITS_VALUE.
 * @throws CourseException if a problem accrues in the program
 */
public Course(Prefix coursePrefix, String courseNum, String courseName, int credits) throws CourseException
{
    this(coursePrefix, courseNum, courseName);
    
    try{
        /* If the number of credits is valid, set credits to it. */
        this.credits = MISSING_CREDITS_VALUE;
        if( isValidCredits(credits) )
            this.credits = credits;
        
    } catch (ValidationException ex) {
        throw new CourseException("CourseException has found an error: " + ex);
    }
    
}

/**
 * The number of credits for the course.
 *
 * @return				the number of credits for the course (MISSING_CREDITS if no
 *						credits set or will be set later), otherwise the number of
 *						credits that have been indicated
 */
public int getCredits()
{
	return this.credits;
}

/**
 * Set the number of credits for the course.
 *
 * @param credits		the number of credits for the course. If the number is
 *						outside the permitted range, the course credit is set to
 *						the value MISSING_CREDITS.
 * @throws ValidationException if the credit value is incorrect
 */
public void setCredits(int credits) throws ValidationException
{
	this.credits = MISSING_CREDITS_VALUE;

        if( isValidCredits(credits) )
            this.credits = credits;
    
}

/**
 * Create a String detailing the name of the course, and the number of credits.
 *
 * @return				the name of the course, and the number of credits
 */
public String getDetails()
{
	return String.join(" ",
			toString(), courseName, String.valueOf(credits), "Credits");
}

/**
 * Return the course identification, which is the department identifier (Prefix)
 * follow by the Course number (separated by "-").
 *
 * @return				Course number (prefixed)
 */
@Override
public String toString()
{
	return coursePrefix.name() + "-" + courseNum;
}

/**
 * Validate the courseName string. It must not be null or blank.
 *
 * @param courseName	the courseName string
 * @return				true if the string if valid, otherwise false
 * @throws ValidationException course name is invalid
 */
private static boolean isValidCourseName(String courseName) throws ValidationException
{
	if(courseName != null && !courseName.isBlank())
            return true;
        
       throw new ValidationException("Invalid Course Name: " + courseName);
}

/**
 * Validate the courseNum string. It must be exactly three characters.
 *
 * @param courseNum		the courseNum string
 * @return				true if the string if valid, otherwise false
 * @throws ValidationException if course number is incorrect
 */
private static boolean isValidCourseNum(String courseNum) throws ValidationException
{
	if(courseNum != null && courseNum.length() == COURSE_NUMBER_LENGTH) return true;
        throw new ValidationException("Invalid Course Number: " + courseNum);
}

/**
 * Validate the value of the course credits. The value must be in the range
 * MINIMUM_CREDITS&nbsp;&ndash;&nbsp;MAXIMUM_CREDITS.
 *
 * @param credits		the credits value
 * @return				true if the value if valid, otherwise false
 * @throws ValidationException if the credit value is incorrect
 */
private static boolean isValidCredits(int credits) throws ValidationException 
{
	if(credits >= MINIMUM_CREDITS && credits <= MAXIMUM_CREDITS) return true;
        throw new ValidationException("Invalid credit value: " + credits);
}
}
