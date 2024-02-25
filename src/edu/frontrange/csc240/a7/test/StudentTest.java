
package edu.frontrange.csc240.a7.test;

import edu.frontrange.csc240.a7.Degree;
import edu.frontrange.csc240.a7.FRCCDate;
import edu.frontrange.csc240.a7.Student;
import edu.frontrange.csc240.a7.StudentException;
import java.time.LocalDate;
import java.util.Random;
import software.haddon.util.ObjectCounter;

import static java.lang.Integer.max;
import static java.lang.System.out;

/**
 * A test program for ensuring that the Student class is functional.
 * This is a very brief test, and does not test in depth.
 *
 * @author		Dr. Bruce K. Haddon, Instructor
 * @version		4.1, 2021-07-20, CSC-240 Assignment 7, per the Assignment
 *				Instructions
 */
public class StudentTest
{
/**
 * Source of random numbers.
 */
private static final Random RANDOM = new Random();

/**
 * A collection of "students" to use in this test program, but public so that the
 * same collection may be used in the serialization demonstration programs.
 */
@SuppressWarnings("PublicField")
public Student[] testStudents;

/**
 * Run the actual tests.
 *
 * @throws StudentException if a error occurs in a Student constructor
 */
@SuppressWarnings( {"BroadCatchBlock", "TooBroadCatch", "UnusedAssignment"} )
public void run() throws StudentException
{
	/* Initialize the {@code testStudents} variable) in a try statement since there
	   needs to be a catch for any possible {@code StudentException} that may
       be thrown. */
	try
	{
		testStudents = new Student[]
		{
			new Student("S00001234", "Mdffthp", "Fjspw",
					new FRCCDate(12, 10, randomYear(10)).setISO8601(true), Degree.AASCIS),
			new Student("S00005678", "Mkdwqk", "Fjspw",
					new FRCCDate(2, 15, randomYear(6)), Degree.AS),
			new Student("S00009999", "Mkxtr", "Fjspw",
					new FRCCDate(4, 10, randomYear(11)), Degree.AA)
		};
	} catch( StudentException ex )
	{
		System.out.println("Failure to initialize");
		System.exit(-1);
	}

	/* System.err set the same as System.out. */
	System.setErr(out);

	/* Headings. */
	out.println("** Testing Student Class");
	out.println();
	out.println("** Test Student Values:");
	out.println();

	/* Good students. */
	for( Student testStudent : testStudents )
	{
		out.println(testStudent.getDetails());
		out.println();
	}

	Student testStudentX;
	try
	{
		/* Create a student that is incorrect (the student number is not long
		   enough, and will cause a failure. */
		out.println("** The next student is expected to cause a failure.");
		testStudentX = new Student("S0000678", "Jjfbn", "Fjspw");
		out.println("** \""+ testStudentX.getDetails() + "\"");
		out.println();
	}
	catch( StudentException ex )
	{
		out.println("** Expected exception caught: the message is:");
		out.println("** " + ex.getMessage());
		out.println("** Cause of the exception is:");
		Throwable cause = ex.getCause();
		out.println( cause == null ? "<No cause given>" :
				cause.getClass().getName() + "/\"" + cause.getMessage() + "\"");
		out.println();
	}

	Student testStudentY;
	try
	{
		/* Create a student that is correct, and then set the birthdate to null,
		   and this should cause an assertion failure. */
		out.println("** The next student is expected to cause a failure.");
		testStudentY = new Student("S00003456", "Yrsst", "Klppwc");
		out.println("** \""+ testStudentY.getDetails() + "\"");
		out.println("** Set the birthdate with a null value");
		testStudentY.setBirthDate(null);
		out.println();
	}
	catch( StudentException ex )
	{
		out.println("**?? Unexpected exception caught: the message is:");
		out.println("**?? " + ex.getMessage());
		out.println();
	}
	catch( AssertionError ae )
	{
		out.println("** Expected  AssertionError caught: the message is:");
		out.println("** " + ae.getMessage());
		out.println();
	}

	out.println("** Count of Student instances should be 5, and is: " +
									ObjectCounter.getCounter(Student.class));

	/* These following statements are purely for test purposes. Calls to System.gc()
	   and System.runFinalization() are not recommended for any ordinary coding
	   style. The use here is to simply show that the Student objects when
	   discarded (the references set to null) may actually be collected. */
	testStudentX = null;
	testStudentY = null;
	System.gc();
	System.gc();
	System.runFinalization();
	System.gc();
	out.println("** Count of Student instances should be 3, and is: " +
									ObjectCounter.getCounter(Student.class));

	testStudents = null;
	System.gc();
	System.gc();
	System.runFinalization();
	System.gc();

	out.println("** Count of Student instances should be 0, and is: " +
									ObjectCounter.getCounter(Student.class));
}

/**
 * Program entry point.
 *
 * <p>
 * Execute: </p>
 * <pre>java edu.frontrange.csc240.a11.example1.test.StudentTest</pre>
 *
 * @param args			unused
 * @throws StudentException if a error occurs in a Student constructor
 */
public static void main(String[] args) throws StudentException
{
	new StudentTest().run();
}

/**
 * Select as year for use as data.
 *
 * @param range			0 if result is to be close to current year, otherwise a
 *						range around 25 year in	the past
 * @return				the selected year
 */
private static int randomYear(int range)
{
	LocalDate now = LocalDate.now();
	int medianAge = range == 0 ? 0 : 25;
	range = max(1, range);
	LocalDate random = now.minusYears(medianAge + RANDOM.nextInt(range) - range/2 + 1);
	return random.getYear();
}
}

