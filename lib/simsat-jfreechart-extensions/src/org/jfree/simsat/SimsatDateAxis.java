/* ========================================================================
 * SIMSAT JFreeChart Extensions : SIMSAT extensions to JFreeChart, a free chart library for the Java(tm) platform
 * ========================================================================
*
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * -------------
 * DateAxis.java
 * -------------
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Jonathan Nash;
 *                   David Li;
 *                   Michael Rauch;
 *                   Bill Kelemen;
 *                   Pawel Pabis;
 *                   Chris Boek;
 *
 * Changes (from 23-Jun-2001)
 * --------------------------
 * 23-Jun-2001 : Modified to work with null data source (DG);
 * 18-Sep-2001 : Updated header (DG);
 * 27-Nov-2001 : Changed constructors from public to protected, updated Javadoc
 *               comments (DG);
 * 16-Jan-2002 : Added an optional crosshair, based on the implementation by
 *               Jonathan Nash (DG);
 * 26-Feb-2002 : Updated import statements (DG);
 * 22-Apr-2002 : Added a setRange() method (DG);
 * 25-Jun-2002 : Removed redundant local variable (DG);
 * 25-Jul-2002 : Changed order of parameters in ValueAxis constructor (DG);
 * 21-Aug-2002 : The setTickUnit() method now turns off auto-tick unit
 *               selection (fix for bug id 528885) (DG);
 * 05-Sep-2002 : Updated the constructors to reflect changes in the Axis
 *               class (DG);
 * 18-Sep-2002 : Fixed errors reported by Checkstyle (DG);
 * 25-Sep-2002 : Added new setRange() methods, and deprecated
 *               setAxisRange() (DG);
 * 04-Oct-2002 : Changed auto tick selection to parallel number axis
 *               classes (DG);
 * 24-Oct-2002 : Added a date format override (DG);
 * 08-Nov-2002 : Moved to new package com.jrefinery.chart.axis (DG);
 * 14-Jan-2003 : Changed autoRangeMinimumSize from Number --> double, moved
 *               crosshair settings to the plot (DG);
 * 15-Jan-2003 : Removed anchor date (DG);
 * 20-Jan-2003 : Removed unnecessary constructors (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 02-May-2003 : Added additional units to createStandardDateTickUnits()
 *               method, as suggested by mhilpert in bug report 723187 (DG);
 * 13-May-2003 : Merged HorizontalDateAxis and VerticalDateAxis (DG);
 * 24-May-2003 : Added support for underlying timeline for
 *               SegmentedTimeline (BK);
 * 16-Jul-2003 : Applied patch from Pawel Pabis to fix overlapping dates (DG);
 * 22-Jul-2003 : Applied patch from Pawel Pabis for monthly ticks (DG);
 * 25-Jul-2003 : Fixed bug 777561 and 777586 (DG);
 * 13-Aug-2003 : Implemented Cloneable and added equals() method (DG);
 * 02-Sep-2003 : Fixes for bug report 790506 (DG);
 * 04-Sep-2003 : Fixed tick label alignment when axis appears at the top (DG);
 * 10-Sep-2003 : Fixes for segmented timeline (DG);
 * 17-Sep-2003 : Fixed a layout bug when multiple domain axes are used (DG);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 07-Nov-2003 : Modified to use new tick classes (DG);
 * 12-Nov-2003 : Modified tick labelling to use roll unit from DateTickUnit
 *               when a calculated tick value is hidden (which can occur in
 *               segmented date axes) (DG);
 * 24-Nov-2003 : Fixed some problems with the auto tick unit selection, and
 *               fixed bug 846277 (labels missing for inverted axis) (DG);
 * 30-Dec-2003 : Fixed bug in refreshTicksHorizontal() when start of time unit
 *               (ex. 1st of month) was hidden, causing infinite loop (BK);
 * 13-Jan-2004 : Fixed bug in previousStandardDate() method (fix by Richard
 *               Wardle) (DG);
 * 21-Jan-2004 : Renamed translateJava2DToValue --> java2DToValue, and
 *               translateValueToJava2D --> valueToJava2D (DG);
 * 12-Mar-2004 : Fixed bug where date format override is ignored for vertical
 *               axis (DG);
 * 16-Mar-2004 : Added plotState to draw() method (DG);
 * 07-Apr-2004 : Changed string width calculation (DG);
 * 21-Apr-2004 : Fixed bug in estimateMaximumTickLabelWidth() method (bug id
 *               939148) (DG);
 * 11-Jan-2005 : Removed deprecated methods in preparation for 1.0.0
 *               release (DG);
 * 13-Jan-2005 : Fixed bug (see
 *               http://www.jfree.org/forum/viewtopic.php?t=11330) (DG);
 * 21-Apr-2005 : Replaced Insets with RectangleInsets, removed redundant
 *               argument from selectAutoTickUnit() (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 10-Feb-2006 : Added some API doc comments in respect of bug 821046 (DG);
 * 19-Apr-2006 : Fixed bug 1472942 in equals() method (DG);
 * 25-Sep-2006 : Fixed bug 1564977 missing tick labels (DG);
 * 15-Jan-2007 : Added get/setTimeZone() suggested by 'skunk' (DG);
 * 18-Jan-2007 : Fixed bug 1638678, time zone for calendar in
 *               previousStandardDate() (DG);
 * 04-Apr-2007 : Use time zone in date calculations (CB);
 * 19-Apr-2007 : Fix exceptions in setMinimum/MaximumDate() (DG);
 * 03-May-2007 : Fixed minor bugs in previousStandardDate(), with new JUnit
 *               tests (DG);
 * 21-Nov-2007 : Fixed warnings from FindBugs (DG);
 *
 * ------------- SIMSAT JFREECHART EXTENSIONS (European Space Agency) ---------
 *  <date>      <ref>    <person>       <description>
 *  25-01-2005  none     ghamilton      Created.
 *  09-08-2005  SSLNXMMI-461 gevison    Changes for Code Review.
 */

package org.jfree.simsat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.TickUnits;

/**
 * Overridden JFreeChart class created to workaround bug in JFreeChart. The bug
 * was a problem in method DateAxis::refreshTicksHorizontal that went into a
 * tight loop - freezing the MMI see issue SSLNXMMI-266 - when using
 * milliseconds as the tick unit. This class overrides
 * createStandardDateTickUnits to remove milliseconds as a tick unit. This class
 * should be removed when the bug is fixed.
 */
public class SimsatDateAxis extends DateAxis
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * F1 Data Format
     */
    private static final String F1_FORMAT = "HH:mm:ss.SSS";

    /**
     * F2 Data Format
     */
    private static final String F2_FORMAT = "HH:mm:ss";

    /**
     * F3 Data Format
     */
    private static final String F3_FORMAT = "HH:mm";

    /**
     * F4 Data Format
     */
    private static final String F4_FORMAT = "d-MMM, HH:mm";

    /**
     * F5 Data Format
     */
    private static final String F5_FORMAT = "d-MMM";

    /**
     * F6 Data Format
     */
    private static final String F6_FORMAT = "MMM-yyyy";

    /**
     * F7 Data Format
     */
    private static final String F7_FORMAT = "yyyy";


    /**
     * Constructor.
     * 
     * @param timeAxisLabel
     */
    public SimsatDateAxis(String timeAxisLabel)
    {
        super(timeAxisLabel);

        // reset the tick to what we want
        setStandardTickUnits(createStandardDateTickUnits(TimeZone.getDefault()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jfree.chart.axis.DateAxis#createStandardDateTickUnits(java.util.TimeZone)
     */
    public static TickUnitSource createStandardDateTickUnits(TimeZone zone)
    {
        TickUnits units = new TickUnits();

        // date formatters
        DateFormat f1 = new SimpleDateFormat(F1_FORMAT);
        DateFormat f2 = new SimpleDateFormat(F2_FORMAT);
        DateFormat f3 = new SimpleDateFormat(F3_FORMAT);
        DateFormat f4 = new SimpleDateFormat(F4_FORMAT);
        DateFormat f5 = new SimpleDateFormat(F5_FORMAT);
        DateFormat f6 = new SimpleDateFormat(F6_FORMAT);
        DateFormat f7 = new SimpleDateFormat(F7_FORMAT);

        f1.setTimeZone(zone);
        f2.setTimeZone(zone);
        f3.setTimeZone(zone);
        f4.setTimeZone(zone);
        f5.setTimeZone(zone);
        f6.setTimeZone(zone);
        f7.setTimeZone(zone);

        // milliseconds - commented out deliberately to show what is different
        // between
        // this and the base class' method
        /*
         * units.add(new DateTickUnit(DateTickUnit.MILLISECOND, 1, f1));
         * units.add(new DateTickUnit(DateTickUnit.MILLISECOND, 5,
         * DateTickUnit.MILLISECOND, 1, f1)); units.add(new
         * DateTickUnit(DateTickUnit.MILLISECOND, 10, DateTickUnit.MILLISECOND,
         * 1, f1)); units.add(new DateTickUnit(DateTickUnit.MILLISECOND, 25,
         * DateTickUnit.MILLISECOND, 5, f1)); units.add(new
         * DateTickUnit(DateTickUnit.MILLISECOND, 50, DateTickUnit.MILLISECOND,
         * 10, f1)); units.add( new DateTickUnit(DateTickUnit.MILLISECOND, 100,
         * DateTickUnit.MILLISECOND, 10, f1) ); units.add( new
         * DateTickUnit(DateTickUnit.MILLISECOND, 250, DateTickUnit.MILLISECOND,
         * 10, f1) ); units.add( new DateTickUnit(DateTickUnit.MILLISECOND, 500,
         * DateTickUnit.MILLISECOND, 50, f1) );
         */

        /*
         * Code review comment: It makes no sense to make the literal values in
         * the following code proper constants of the class or the method. The
         * values are only used here and no where else and would make more sense
         * to anyone maintaining this code for them to be left as they are.
         */

        // seconds
        units.add(new DateTickUnit(DateTickUnit.SECOND, 1, DateTickUnit.MILLISECOND, 50, f2));
        units.add(new DateTickUnit(DateTickUnit.SECOND, 5, DateTickUnit.SECOND, 1, f2));
        units.add(new DateTickUnit(DateTickUnit.SECOND, 10, DateTickUnit.SECOND, 1, f2));
        units.add(new DateTickUnit(DateTickUnit.SECOND, 30, DateTickUnit.SECOND, 5, f2));

        // minutes
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 1, DateTickUnit.SECOND, 5, f3));
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 2, DateTickUnit.SECOND, 10, f3));
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 5, DateTickUnit.MINUTE, 1, f3));
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 10, DateTickUnit.MINUTE, 1, f3));
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 15, DateTickUnit.MINUTE, 5, f3));
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 20, DateTickUnit.MINUTE, 5, f3));
        units.add(new DateTickUnit(DateTickUnit.MINUTE, 30, DateTickUnit.MINUTE, 5, f3));

        // hours
        units.add(new DateTickUnit(DateTickUnit.HOUR, 1, DateTickUnit.MINUTE, 5, f3));
        units.add(new DateTickUnit(DateTickUnit.HOUR, 2, DateTickUnit.MINUTE, 10, f3));
        units.add(new DateTickUnit(DateTickUnit.HOUR, 4, DateTickUnit.MINUTE, 30, f3));
        units.add(new DateTickUnit(DateTickUnit.HOUR, 6, DateTickUnit.HOUR, 1, f3));
        units.add(new DateTickUnit(DateTickUnit.HOUR, 12, DateTickUnit.HOUR, 1, f4));

        // days
        units.add(new DateTickUnit(DateTickUnit.DAY, 1, DateTickUnit.HOUR, 1, f5));
        units.add(new DateTickUnit(DateTickUnit.DAY, 2, DateTickUnit.HOUR, 1, f5));
        units.add(new DateTickUnit(DateTickUnit.DAY, 7, DateTickUnit.DAY, 1, f5));
        units.add(new DateTickUnit(DateTickUnit.DAY, 15, DateTickUnit.DAY, 1, f5));

        // months
        units.add(new DateTickUnit(DateTickUnit.MONTH, 1, DateTickUnit.DAY, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 2, DateTickUnit.DAY, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 3, DateTickUnit.MONTH, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 4, DateTickUnit.MONTH, 1, f6));
        units.add(new DateTickUnit(DateTickUnit.MONTH, 6, DateTickUnit.MONTH, 1, f6));

        // years
        units.add(new DateTickUnit(DateTickUnit.YEAR, 1, DateTickUnit.MONTH, 1, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 2, DateTickUnit.MONTH, 3, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 5, DateTickUnit.YEAR, 1, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 10, DateTickUnit.YEAR, 1, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 25, DateTickUnit.YEAR, 5, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 50, DateTickUnit.YEAR, 10, f7));
        units.add(new DateTickUnit(DateTickUnit.YEAR, 100, DateTickUnit.YEAR, 20, f7));

        return units;
    }
}
