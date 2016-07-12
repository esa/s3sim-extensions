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
 * -----------------
 * ChartFactory.java
 * -----------------
 * (C) Copyright 2001-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Serge V. Grachov;
 *                   Joao Guilherme Del Valle;
 *                   Bill Kelemen;
 *                   Jon Iles;
 *                   Jelai Wang;
 *                   Richard Atkinson;
 *                   David Browning (for Australian Institute of Marine 
 *                       Science);
 *                   Benoit Xhenseval;
 *
 * Changes
 * -------
 * 19-Oct-2001 : Version 1, most methods transferred from JFreeChart.java (DG);
 * 22-Oct-2001 : Added methods to create stacked bar charts (DG);
 *               Renamed DataSource.java --> Dataset.java etc. (DG);
 * 31-Oct-2001 : Added 3D-effect vertical bar and stacked-bar charts, 
 *               contributed by Serge V. Grachov (DG);
 * 07-Nov-2001 : Added a flag to control whether or not a legend is added to 
 *               the chart (DG);
 * 17-Nov-2001 : For pie chart, changed dataset from CategoryDataset to 
 *               PieDataset (DG);
 * 30-Nov-2001 : Removed try/catch handlers from chart creation, as the 
 *               exception are now RuntimeExceptions, as suggested by Joao 
 *               Guilherme Del Valle (DG);
 * 06-Dec-2001 : Added createCombinableXXXXXCharts methods (BK);
 * 12-Dec-2001 : Added createCandlestickChart() method (DG);
 * 13-Dec-2001 : Updated methods for charts with new renderers (DG);
 * 08-Jan-2002 : Added import for 
 *               com.jrefinery.chart.combination.CombinedChart (DG);
 * 31-Jan-2002 : Changed the createCombinableVerticalXYBarChart() method to use
 *               renderer (DG);
 * 06-Feb-2002 : Added new method createWindPlot() (DG);
 * 23-Apr-2002 : Updates to the chart and plot constructor API (DG);
 * 21-May-2002 : Added new method createAreaChart() (JI);
 * 06-Jun-2002 : Added new method createGanttChart() (DG);
 * 11-Jun-2002 : Renamed createHorizontalStackedBarChart() 
 *               --> createStackedHorizontalBarChart() for consistency (DG);
 * 06-Aug-2002 : Updated Javadoc comments (DG);
 * 21-Aug-2002 : Added createPieChart(CategoryDataset) method (DG);
 * 02-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 09-Oct-2002 : Added methods including tooltips and URL flags (DG);
 * 06-Nov-2002 : Moved renderers into a separate package (DG);
 * 18-Nov-2002 : Changed CategoryDataset to TableDataset (DG);
 * 21-Mar-2003 : Incorporated HorizontalCategoryAxis3D, see bug id 685501 (DG);
 * 13-May-2003 : Merged some horizontal and vertical methods (DG);
 * 24-May-2003 : Added support for timeline in createHighLowChart (BK);
 * 07-Jul-2003 : Added createHistogram() method contributed by Jelai Wang (DG);
 * 27-Jul-2003 : Added createStackedAreaXYChart() method (RA);
 * 05-Aug-2003 : added new method createBoxAndWhiskerChart (DB);
 * 08-Sep-2003 : Changed ValueAxis API (DG);
 * 07-Oct-2003 : Added stepped area XY chart contributed by Matthias Rose (DG);
 * 06-Nov-2003 : Added createWaterfallChart() method (DG);
 * 20-Nov-2003 : Set rendering order for 3D bar charts to fix overlapping 
 *               problems (DG);
 * 25-Nov-2003 : Added createWaferMapChart() method (DG);
 * 23-Dec-2003 : Renamed createPie3DChart() --> createPieChart3D for 
 *               consistency (DG);
 * 20-Jan-2004 : Added createPolarChart() method (DG);
 * 28-Jan-2004 : Fixed bug (882890) with axis range in 
 *               createStackedXYAreaChart() method (DG);
 * 25-Feb-2004 : Renamed XYToolTipGenerator --> XYItemLabelGenerator (DG);
 * 11-Mar-2004 : Updated for pie chart changes (DG);
 * 27-Apr-2004 : Added new createPieChart() method contributed by Benoit 
 *               Xhenseval (see RFE 942195) (DG);
 * 11-May-2004 : Split StandardCategoryItemLabelGenerator 
 *               --> StandardCategoryToolTipGenerator and
 *               StandardCategoryLabelGenerator (DG);
 * 06-Jan-2005 : Removed deprecated methods (DG);
 * 27-Jan-2005 : Added new constructor to LineAndShapeRenderer (DG);
 * 28-Feb-2005 : Added docs to createBubbleChart() method (DG);
 * 17-Mar-2005 : Added createRingPlot() method (DG);
 * 21-Apr-2005 : Replaced Insets with RectangleInsets (DG);
 * 29-Nov-2005 : Removed signal chart (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 26-Jan-2006 : Corrected API docs for createScatterPlot() (DG);
 * 23-Aug-2006 : Modified createStackedXYAreaChart() to use 
 *               StackedXYAreaRenderer2, because StackedXYAreaRenderer doesn't
 *               handle negative values (DG);
 * 27-Sep-2006 : Update createPieChart() method for deprecated code (DG);
 * 29-Nov-2006 : Update createXYBarChart() to use a time based tool tip 
 *               generator is a DateAxis is requested (DG);
 * 17-Jan-2007 : Added createBoxAndWhiskerChart() method from patch 1603937
 *               submitted by Darren Jung (DG);
 * 10-Jul-2007 : Added new methods to create pie charts with locale for
 *               section label and tool tip formatting (DG);
 *
 * ------------- SIMSAT JFREECHART EXTENSIONS (European Space Agency) ---------
 *  <date>      <ref>    <person>       <description>
 *  25-01-2005  none     ghamilton      Created.
 *  09-08-2005  SSLNXMMI-461 gevison    Changes for Code Review.
 */

package org.jfree.simsat;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ShapeUtilities;

/**
 * Overridden JFreeChart class created to workaround bug in JFreeChart.
 * Specifically this has been created override the DateAxis class with
 * SimsatDateAxis. See issue SSLNXMMI-266.
 */
public abstract class SimsatChartFactory extends ChartFactory
{
    /**
     * Default margin
     */
    private static final double DEFAULT_MARGIN = 0.02;


    /**
     * Constructor
     */
    public SimsatChartFactory()
    {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jfree.chart.ChartFactory#createTimeSeriesChart(java.lang.String,
     *      java.lang.String, java.lang.String, org.jfree.data.XYDataset,
     *      boolean, boolean, boolean)
     */
    public static JFreeChart createTimeSeriesChart(String title,
                                                   String timeAxisLabel,
                                                   String valueAxisLabel,
                                                   XYDataset dataset,
                                                   boolean legend,
                                                   boolean tooltips,
                                                   boolean urls)
    {
        // this method is a cut and paste from ChartFactory except the next line
        // where we use SimsatDateAxis instead of DateAxis
        ValueAxis timeAxis = new SimsatDateAxis(timeAxisLabel);

        NumberAxis valueAxis = new NumberAxis(valueAxisLabel);

        XYPlot plot = null;

        XYToolTipGenerator labelGenerator = null;

        XYURLGenerator urlGenerator = null;

        JFreeChart chart = null;

        valueAxis.setAutoRangeIncludesZero(false); // override default

        timeAxis.setLowerMargin(DEFAULT_MARGIN); // reduce the default
        // margins on the time axis
        timeAxis.setUpperMargin(DEFAULT_MARGIN);

        plot = new XYPlot(dataset, timeAxis, valueAxis, null);

        if (tooltips)
        {
            labelGenerator = StandardXYToolTipGenerator.getTimeSeriesInstance();
        }

        if (urls)
        {
            urlGenerator = new StandardXYURLGenerator();
        }

        // Restored the standard rendered StandardXYItemRenderer
        // the subclass FixedStandardXYItemRenderer is not needed any longer
        plot.setRenderer(new StandardXYItemRenderer(StandardXYItemRenderer.LINES,
                                                             labelGenerator,
                                                             urlGenerator));

        chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);

        return chart;
    }


    // NOTICE: FixedStandardXYItemRenderer is a subclass of
    // StandardXYItemRenderer,
    // that has been introduced to fix SPR-419 (i.e. the difficulty in
    // triggering the tooltips
    // attached to the points of the graph displayed in a GRD).
    private static class FixedStandardXYItemRenderer extends StandardXYItemRenderer
    {
        private static final long serialVersionUID = 1L;


        public FixedStandardXYItemRenderer(int type,
                                           XYToolTipGenerator toolTipGenerator,
                                           XYURLGenerator urlGenerator)
        {
            super(type, toolTipGenerator, urlGenerator);
        }

        /**
         * Draws the visual representation of a single data item.
         * 
         * @param g2
         *            the graphics device.
         * @param state
         *            the renderer state.
         * @param dataArea
         *            the area within which the data is being drawn.
         * @param info
         *            collects information about the drawing.
         * @param plot
         *            the plot (can be used to obtain standard color information
         *            etc).
         * @param domainAxis
         *            the domain axis.
         * @param rangeAxis
         *            the range axis.
         * @param dataset
         *            the dataset.
         * @param series
         *            the series index (zero-based).
         * @param item
         *            the item index (zero-based).
         * @param crosshairState
         *            crosshair information for the plot (<code>null</code>
         *            permitted).
         * @param pass
         *            the pass index.
         */
        @Override
        public void drawItem(Graphics2D g2,
                             XYItemRendererState state,
                             Rectangle2D dataArea,
                             PlotRenderingInfo info,
                             XYPlot plot,
                             ValueAxis domainAxis,
                             ValueAxis rangeAxis,
                             XYDataset dataset,
                             int series,
                             int item,
                             CrosshairState crosshairState,
                             int pass)
        {

            // setup for collecting optional entity info...
            Shape entityArea = null;
            EntityCollection entities = null;
            if (info != null)
            {
                entities = info.getOwner().getEntityCollection();
            }

            PlotOrientation orientation = plot.getOrientation();
            Paint paint = getItemPaint(series, item);
            Stroke seriesStroke = getItemStroke(series, item);
            g2.setPaint(paint);
            g2.setStroke(seriesStroke);

            // get the data point...
            double x1 = 0.0d;
            double y1 = 0.0d;
            try
            {
                // [jsc] JFreeChart update v0.9.20 -> v1.0.10
                // now getXValue() returns a primitive type
                x1 = dataset.getXValue(series, item);
                y1 = dataset.getYValue(series, item);
            }
            catch (Exception e)
            {
                return;
            }

            final RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
            final RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
            double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
            double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);

            double transX0 = 0.0d;
            double transY0 = 0.0d;
            boolean isLineDrawn = false;

            if (getPlotLines())
            {

                if (item > 0)
                {
                    // get the previous data point...
                    // [jsc] JFreeChart update v0.9.20 -> v1.0.10
                    // now getXValue() returns a primitive type
                    double x0 = dataset.getXValue(series, item - 1);
                    double y0 = dataset.getYValue(series, item - 1);
                    boolean drawLine = true;
                    if (getPlotDiscontinuous())
                    {
                        // only draw a line if the gap between the current
                        // and previous data
                        // point is within the threshold
                        int numX = dataset.getItemCount(series);

                        // [jsc] JFreeChart update v0.9.20 -> v1.0.10
                        // now getXValue() returns a primitive type
                        double minX = dataset.getXValue(series, 0);
                        double maxX = dataset.getXValue(series, numX - 1);
                        drawLine = (x1 - x0) <= ((maxX - minX) / numX * getGapThreshold());
                    }
                    if (drawLine)
                    {
                        transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
                        transY0 = rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);

                        isLineDrawn = true;

                        // only draw if we have good values
                        if (Double.isNaN(transX0) || Double.isNaN(transY0) || Double.isNaN(transX1)
                            || Double.isNaN(transY1))
                        {
                            return;
                        }

                        if (orientation == PlotOrientation.HORIZONTAL)
                        {
                            state.workingLine.setLine(transY0, transX0, transY1, transX1);
                        }
                        else if (orientation == PlotOrientation.VERTICAL)
                        {
                            state.workingLine.setLine(transX0, transY0, transX1, transY1);
                        }

                        if (state.workingLine.intersects(dataArea))
                        {
                            g2.draw(state.workingLine);
                        }
                    }
                }
            }
            // [jsc] JFreeChart update v0.9.20 -> v1.0.10
            // getPlotShapes() -> getBaseShapesVisible()
            if (getBaseShapesVisible())
            {

                Shape shape = getItemShape(series, item);
                if (orientation == PlotOrientation.HORIZONTAL)
                {
                    // [jsc] JFreeChart update v0.9.20 -> v1.0.10
                    // createTransformedShape() ->
                    // ShapeUtilities.createTranslatedShape()
                    shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);
                }
                else if (orientation == PlotOrientation.VERTICAL)
                {
                    // [jsc] JFreeChart update v0.9.20 -> v1.0.10
                    // createTransformedShape() ->
                    // ShapeUtilities.createTranslatedShape()
                    shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
                }
                if (shape.intersects(dataArea))
                {
                    if (getItemShapeFilled(series, item))
                    {
                        g2.fill(shape);
                    }
                    else
                    {
                        g2.draw(shape);
                    }
                }
                entityArea = shape;

            }

            if (getPlotImages())
            {
                // use shape scale with transform??
                // double scale = getShapeScale(plot, series, item, transX1,
                // transY1);
                Image image = getImage(plot, series, item, transX1, transY1);
                if (image != null)
                {
                    Point hotspot = getImageHotspot(plot, series, item, transX1, transY1, image);
                    g2.drawImage(image, (int) (transX1 - hotspot.getX()), (int) (transY1 - hotspot.getY()), null);
                    entityArea = new Rectangle2D.Double(transX1 - hotspot.getX(), transY1 - hotspot.getY(), image
                            .getWidth(null), image.getHeight(null));
                }

            }

            // draw the item label if there is one...
            if (isItemLabelVisible(series, item))
            {
                drawItemLabel(g2, orientation, dataset, series, item, transX1, transY1, (y1 < 0.0));
            }

            // [jsc] JFreeChart update v0.9.20 -> v1.0.10
            // Use updateCrosshairValues(CrosshairState, double, double, int,
            // int, double, double, PlotOrientation)
            updateCrosshairValues(crosshairState, x1, y1, 0, 0, transX1, transY1, orientation);

            // add an entity for the item...
            if (entities != null)
            {
                if (entityArea == null)
                {
                    // NOTICE: Now follows the fix of StandardXYItemRenderer
                    // which aims in
                    // solving SPR-419. The original
                    // StandardXYItemRenderer.drawItem()
                    // uses fixed and -- at least for the use made in the GRDs
                    // -- too low
                    // values (namely 2.0) in place of our DEFAULT_AREA_WIDTH
                    // and
                    // DEFAULT_AREA_HEIGHT. We have replaced those fixed values
                    // with the higher
                    // one 10.0; moreover we set the entityArea so, that it
                    // contains the last
                    // point in the time series. These two modifications should
                    // ensure a sufficiently
                    // wide 'tooltip sensitive' stripe around the graph
                    // representing the time series.
                    final double DEFAULT_AREA_WIDTH = 10.0;
                    final double DEFAULT_AREA_HEIGHT = 10.0;

                    double entityAreaCenterX = 0.0;
                    double entityAreaCenterY = 0.0;
                    double entityAreaWidth = DEFAULT_AREA_WIDTH;
                    double entityAreaHeight = DEFAULT_AREA_HEIGHT;

                    if (isLineDrawn)
                    {
                        double transXDiff = Math.abs(transX1 - transX0);
                        double transYDiff = Math.abs(transY1 - transY0);

                        if (orientation == PlotOrientation.VERTICAL)
                        {
                            entityAreaCenterX = transX1;
                            entityAreaCenterY = transY1;
                            entityAreaWidth = Math.max(transXDiff, DEFAULT_AREA_WIDTH);
                            entityAreaHeight = Math.max(transYDiff, DEFAULT_AREA_HEIGHT);
                        }
                        else if (orientation == PlotOrientation.HORIZONTAL)
                        {
                            entityAreaCenterX = transY1;
                            entityAreaCenterY = transX1;
                            entityAreaWidth = Math.max(transYDiff, DEFAULT_AREA_WIDTH);
                            entityAreaHeight = Math.max(transXDiff, DEFAULT_AREA_HEIGHT);
                        }
                    }

                    entityArea = new Rectangle2D.Double(entityAreaCenterX - DEFAULT_AREA_WIDTH,
                                                        entityAreaCenterY - DEFAULT_AREA_HEIGHT,
                                                        entityAreaWidth,
                                                        entityAreaHeight);
                }
                String tip = null;
                XYToolTipGenerator generator = getToolTipGenerator(series, item);
                if (generator != null)
                {
                    try
                    {
                        tip = generator.generateToolTip(dataset, series, item);
                    }
                    catch (Throwable t)
                    {
                        // intentionally left blank
                    }
                }
                String url = null;
                if (getURLGenerator() != null)
                {
                    try
                    {
                        url = getURLGenerator().generateURL(dataset, series, item);
                    }
                    catch (Throwable t)
                    {
                        // intentionally left blank
                    }
                }
                XYItemEntity entity = new XYItemEntity(entityArea, dataset, series, item, tip, url);
                // [jsc] JFreeChart update v0.9.20 -> v1.0.10
                // addEntity() -> add()
                entities.add(entity);
            }

        }
    }

}
