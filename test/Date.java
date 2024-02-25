
package edu.frontrange.csc240.a7;

import java.util.regex.Pattern;

/**
 * Project specific holder for dates.
 *
 * @author		Dr. Bruce K. Haddon, Instructor
 * @version		3.0, 2021-03-16, CSC-240 Assignment 11
 */
public class Date implements Comparable<Date>
{
/**
 * Days for each month (except February).
 * <p>
 * Note there is no zeroth month, and the first entry in this array corresponds to
 * the month numbered 1.
 */
private static final int DAYS_PER_MONTH[] =
{
	31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
};

/**
 * The month of February is the second month.
 */
private static final int FEBRUARY = 2;

/**
 * February days in a year that is a leap year.
 */
private static final int FEB_DAYS_LEAP_YEAR = 29;

/**
 * February days in a year that is not a leap year.
 */
private static final int FEB_DAYS_USUALLY = 28;

/**
 * Formatting format for American date format (interpreted as month/day/year).
 */
private static final String FORMAT_AMERICAN = "%1$d/%2$d/%3$d";

/**
 * Formatting format for ISO8601 date format (interpreted as year-month-day).
 */
private static final String FORMAT_ISO8601 = "%3$04d-%1$02d-%2$02d";

/**
 * The major cycle of years on which a year is a leap year.
 */
private static final int MAJOR_LEAP_CYCLE = 400;

/**
 * The minor cycle of years on which a year is a leap year (except for the cycle of
 * years that are skipped).
 */
private static final int MINOR_LEAP_CYCLE = 4;

/**
 * The cycle of years where a leap year is skipped out of the minor cycle.
 */
private static final int MINOR_LEAP_SKIP = 100;

/**
 * The number of months in a standard Gregorian year.
 */
private static final int MONTHS_IN_YEAR = 12;

/**
 * For the factory method, corresponding pattern to match the American format
 * (interpreted as month/day/year).
 */
private static final Pattern PATTERN_AMERICAN =
		Pattern.compile("(\\d{1,2})/(\\d{1,2})/(\\d{4})");

/**
 * For the factory method, corresponding pattern to match ISO8601 date format
 * (interpreted as year-month-day).
 */
private static final Pattern PATTERN_ISO8601 =
		Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

static
{
	assert DAYS_PER_MONTH.length == MONTHS_IN_YEAR :
			"Incorrect initialization of daysPerMonth array";
}

/**
 * Flag to show date string in ISO8601 format (or not)
 */
private boolean ISO8601;

/**
 * The day of the month (range is month and year dependent),
 */
private final int day;

/**
 * The month of the year (1 to MONTHS_IN_YEAR).
 */
private final int month;

/**
 * Year (full year number, including century).
 */
private final int year;

/**
 * The given date is verified. If unusable day or month numbers are provided, an
 * Exception is thrown.
 *
 * @param month			the month of the year (1 - MONTHS_IN_YEAR)
 * @param day			the day of the month (range is month and year dependent)
 * @param year			year (full year number, including century)
 * @param form			true if the displayed form is to be ISO8601
 * @throws IllegalArgumentException if any argument is outside a permitted range
 * @see	#checkMonth(int)
 * @see	#checkDay(int, int, int)
 */
public Date(int month, int day, int year, boolean... form)
		throws IllegalArgumentException
{
	checkMonth(month);						// validate month
	this.month = month;
	this.year = year;						// could validate year (but do not)
	checkDay(this.year, this.month, day);	// validate day
	this.day = day;

	if( form != null && form.length > 0 )
		setISO8601(form[0]);
}

/**
 * {@inheritDoc}
 */
@Override
public boolean equals(Object obj)
{
	return this == obj ||
			obj instanceof Date &&
					this.compareTo((Date) obj) == 0;
}

/**
 * Compare two dates: this date to a given other (that) date.
 *
 * @param that			the date to which the comparison is being made
 * @return				negative value if this date is before that date, positive
 *						value if this date is after that date, otherwise zero.
 */
@SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "NestedAssignment"})
@Override
public int compareTo(Date that)
{
	int result;
	if( (result = Integer.compare(this.year, that.year)) != 0 ) return result;
	if( (result = Integer.compare(this.month, that.month)) != 0 ) return result;
	return Integer.compare(this.day, that.day);
}

/**
 * {@inheritDoc}
 */
@Override
public int hashCode()
{
	return ((41 + this.day) * 37 + this.month) * 31 + this.year;
}

/**
 * Set how {@code toString} will show the date.
 *
 * @param ISO8601		true if ISO8601 format is desired, false otherwise.
 * @return				this Date object
 */
public final Date setISO8601(boolean ISO8601)
{
	this.ISO8601 = ISO8601;
	return this;
}

/**
 * Show string form in the selected format.
 *
 * @return				date of the form m/d/year or year-mm-dd.
 */
@Override
public String toString()
{
	return String.format(ISO8601 ? FORMAT_ISO8601 : FORMAT_AMERICAN,
			month, day, year);
}

/**
 * Factory method for creating dates from a String form. The form is expected to be
 * either ISO8601 format, or the standard American format. This method is using
 * regular expressions to recognize the form of the given string, a topic that will
 * be taken up in a later lesson.
 *
 * @param form			the string representing the date
 * @return				the corresponding Date object if the form is acceptable
 * @throws IllegalArgumentException if the form is not recognized
 */
public static Date instance(String form) throws IllegalArgumentException
{
	/* Regular expression matcher for the given date form. */
	var m_ISO8601 = PATTERN_ISO8601.matcher(form);

	/* Regular expression matcher for the given date form. */
	var m_american = PATTERN_AMERICAN.matcher(form);

	/* Depending on which format the given String form matches, create the Date
	   appropriately, and return that Date. */
	if( m_ISO8601.matches() )
		return new Date(Integer.parseInt(m_ISO8601.group(2)),
				Integer.parseInt(m_ISO8601.group(3)),
				Integer.parseInt(m_ISO8601.group(1)), true);
	else if( m_american.matches() )
		return new Date(Integer.parseInt(m_american.group(1)),
				Integer.parseInt(m_american.group(2)),
				Integer.parseInt(m_american.group(3)), false);
	else
		throw new IllegalArgumentException("Date form \"" + form +
				"\" not recogized");
}

/**
 * Factory method for creating date. The given date is verified. If unusable day or
 * month numbers are provided, null is returned.
 *
 * @param year			year (full year number, including century)
 * @param month			the month of the year (1 - MONTHS_IN_YEAR)
 * @param day			the day of the month (range is month and year dependent)
 * @param form			true if the displayed form is to be ISO8601
 * @return				the corresponding Date instance, or null if there are
 *						unacceptable argument values
 * @see	#checkMonth(int)
 * @see	#checkDay(int, int, int)
 */
public static Date instance(int year, int month, int day, boolean... form)
{
	try
	{
		return new Date(month, day, year, form);
	} catch( IllegalArgumentException ex )
	{
		return null;
	}
}

/**
 * Determines if a given year is a leap year, if (and only if) the year is the
 * number of a year within the Gregorian calendar (some date after October 15, 1582,
 * or later depending upon the country).
 *
 * @param year			the given year
 * @return				true if a leap year
 */
public static boolean isLeapYear(int year)
{
	return year % MAJOR_LEAP_CYCLE == 0 ||
			year % MINOR_LEAP_CYCLE == 0 && year % MINOR_LEAP_SKIP != 0;
}

/**
 * Utility method to confirm valid day value based on month and year.
 *
 * @param year			the given year
 * @param month			the given month
 * @param day			the day number to test
 * @throws IllegalArgumentException if any argument is outside a permitted range
 */
private static void checkDay(int year, int month, int day)
		throws IllegalArgumentException
{
	checkMonth(month);
	var daysInThisMonth = DAYS_PER_MONTH[month - 1];

	/* Set days in February, depending upon whether year is a leap year or not. */
	if( month == FEBRUARY ) daysInThisMonth = isLeapYear(year) ?
				FEB_DAYS_LEAP_YEAR : FEB_DAYS_USUALLY;

	/* Check if day in range for the currently set month. */
	if( day < 1 || day > daysInThisMonth )
		throw new IllegalArgumentException("Invalid day value \"" + day + "\"");
}

/**
 * Utility method to confirm proper month value. Values of month are permitted to be
 * 1 to MONTHS_IN_YEAR inclusive.
 *
 * @param month			a month number to test
 * @throws IllegalArgumentException is the month is not acceptable
 */
private static void checkMonth(int month)
{
	if( month < 1 || month > MONTHS_IN_YEAR ) // validate month
		throw new IllegalArgumentException("Invalid month value \"" + month + "\"");
}

///**
// * Main method to conduct some tests on the date class.
// *
// * @param args	not used.
// */
//@SuppressWarnings({"BroadCatchBlock", "TooBroadCatch", "UseSpecificCatch"})
//public static void main(String[] args)
//{
//	try
//	{
//		Date d1 = new Date(2, 29, 2008);
//		System.out.println(d1.toString());
//		System.out.println(d1.setISO8601(true).toString());
//		System.out.println(d1.setISO8601(false).toString());
//
//		Date d2 = Date.instance(d1.toString());
//		System.out.println(d2.toString());
//		Date d3 = Date.instance(d1.setISO8601(true).toString());
//		System.out.println(d3.toString());
//
//		Date d4 = new Date(5, 31, 2009);
//		System.out.println(d4.toString());
//		d4.setISO8601(true);
//		System.out.println(d4.toString());
//		d4.setISO8601(false);
//		System.out.println(d4.toString());
//
//		Date d5 = Date.instance(d4.toString());
//		System.out.println(d5.toString());
//		d4.setISO8601(true);
//		Date d6 = Date.instance(d4.toString());
//		System.out.println(d6.toString());
//	} catch( Exception ex )
//	{
//		System.out.println("Unexpected " + ex.getClass().getSimpleName() +
//				": " + ex.getMessage());
//	}
//
//	try
//	{
//		Date d7 = new Date(6, 31, 2010);
//		System.out.println(d7.toString());
//	} catch( Exception ex )
//	{
//		System.out.println(ex.getClass().getSimpleName() + ": " + ex.
//				getMessage());
//	}
//	System.out.println();
//
//	try
//	{
//		Date d8 = new Date(13, 12, 2010);
//		System.out.println(d8.toString());
//	} catch( Exception ex )
//	{
//		System.out.println(ex.getClass().getSimpleName() + ": " + ex.
//				getMessage());
//	}
//	System.out.println();
//
//	try
//	{
//		Date d9 = new Date(2, 29, 2010);
//		System.out.println(d9.toString());
//	} catch( Exception ex )
//	{
//		System.out.println(ex.getClass().getSimpleName() + ": " + ex.
//				getMessage());
//	}
//	System.out.println();
//}
}
