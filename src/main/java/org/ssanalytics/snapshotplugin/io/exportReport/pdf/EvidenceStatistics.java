/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.pdf;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.ssanalytics.snapshotplugin.io.dbConnection.independentDataFetcher.EvidenceFetcher;

/**
 *
 * @author christian
 */
public class EvidenceStatistics {

    private static EvidenceStatistics instance = null;

    public static EvidenceStatistics getInstance() {
        if (instance == null) {
            instance = new EvidenceStatistics();
        }
        return instance;
    }

    public JFreeChart getEvidenceStatisticsForLastYear(String snapshotName) throws Exception {

        Calendar cal = Calendar.getInstance();
        TimePeriodValuesCollection collection = new TimePeriodValuesCollection();
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
        
        TimePeriodValues valuesBooks = new TimePeriodValues("Books");
        TimePeriodValues valuesActivities = new TimePeriodValues("Activities");
        TimePeriodValues valuesLikes = new TimePeriodValues("Likes");
        TimePeriodValues valuesInterests = new TimePeriodValues("Interests");
        TimePeriodValues valuesMovies = new TimePeriodValues("Movies");
        TimePeriodValues valuesTelevision = new TimePeriodValues("Television");
        TimePeriodValues valuesMusic = new TimePeriodValues("Music");

        DateAxis xAxis = new DateAxis("Timespan");
        DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 1);
        DateFormat format = new SimpleDateFormat("MM/yy");
        xAxis.setDateFormatOverride(format);
        xAxis.setTickUnit(unit);
        
        ValueAxis yAxis = new NumberAxis("Evidence");

        Date oldMonth;
        Date newMonth;
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) -1);
        while (cal.getTime().before(new Date(System.currentTimeMillis()))) {
            oldMonth = cal.getTime();

            if (cal.get(Calendar.MONTH) == 11) {
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
                cal.set(Calendar.MONTH, 0);
            } else {
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
            }
            newMonth = cal.getTime();

            Map<String, Integer> map = EvidenceFetcher.getInstance().getEvidenceCountForTimespanForSnapsthotLatestVersion(snapshotName, oldMonth.getTime(), newMonth.getTime());

            valuesBooks.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("books"));
            valuesActivities.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("activities"));
            valuesMusic.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("music"));
            valuesMovies.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("movies"));
            valuesInterests.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("interests"));
            valuesLikes.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("likes"));
            valuesTelevision.add(new SimpleTimePeriod(oldMonth, newMonth), map.get("television"));
//            series0.add(unit.getUnit(), map.get("books"));
        }
        collection.addSeries(valuesBooks);
        collection.addSeries(valuesActivities);
        collection.addSeries(valuesMusic);
        collection.addSeries(valuesMovies);
        collection.addSeries(valuesInterests);
//        collection.addSeries(valuesLikes);
        collection.addSeries(valuesTelevision);
        
        XYPlot plot = new XYPlot(collection, xAxis, yAxis, renderer);
        JFreeChart chart = new JFreeChart("Evidence Chart", plot);
//        JFreeChart evidenceLineChart = ChartFactory.createXYLineChart("Evidence Statistics",
//                "Time",
//                "Evidence",
//                seriesCollection,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false);

        return chart;
    }
}
