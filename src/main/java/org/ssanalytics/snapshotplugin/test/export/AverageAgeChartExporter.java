/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.export;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.Plots;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.openide.util.Exceptions;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.io.htmlExport.AbstractDataExporter;

/**
 *
 * @author christian
 */
public class AverageAgeChartExporter extends AbstractDataExporter {

    private String url;
    private String chartName = "Age Chart";
    private int chartWidth = 600;
    private int chartHeight = 400;

    public AverageAgeChartExporter(String snapshotName) {
        try {
            generateChart(snapshotName);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    protected Template getTemplate() {
        try {
            return config.getTemplate(this.templatePath + "ChartTemplate.html");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    protected SimpleHash getDataForTemplate() {

        SimpleHash map = new SimpleHash();

        map.put("chartName", chartName);
        map.put("chartUrl", url);
        map.put("chartWidth", chartWidth);
        map.put("chartHeight", chartHeight);

        return map;
    }

    private void generateChart(String snapshotName) throws Exception {
        List<IProfile> profiles = DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion(snapshotName);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        List<Integer> ages = new LinkedList<>();
        Calendar now = Calendar.getInstance();
        int yearNow = now.get(Calendar.YEAR);
        for (IProfile profile : profiles) {
            if (profile.getBirthday() != null) {
                try {
                    Date birthday = formatter.parse(profile.getBirthday());
                    Calendar birth = Calendar.getInstance();
                    birth.setTime(birthday);
                    ages.add(yearNow - birth.get(Calendar.YEAR));
                } catch (ParseException ex) {
                }
            }
        }

        List<Integer> ageCount = new ArrayList<>(Collections.nCopies(Collections.max(ages), 0));
        for (int item : ages) {
            int agesCounted = ageCount.get(item - 1);
            ageCount.set(item - 1, ++agesCounted);
        }

        ArrayList<Double> ageNorm = new ArrayList<>(Collections.nCopies(Collections.max(ages), 0.0));
        System.out.println(ageNorm.size());
        for (int i = 0; i < ageNorm.size(); ++i) {
            double normVal = ((float) ageCount.get(i)) / Collections.max(ageCount);
            System.out.println(ageCount.get(i) + "/" + Collections.max(ageCount) + " = " + normVal);
            ageNorm.set(i, normVal * 100);
        }


        BarChartPlot team1 = Plots.newBarChartPlot(Data.newData(ageNorm), Color.BLUEVIOLET, "Team A");
//        BarChartPlot team2 = Plots.newBarChartPlot(Data.newData(8, 35, 11, 5), Color.ORANGERED, "Team B");
//        BarChartPlot team3 = Plots.newBarChartPlot(Data.newData(10, 20, 30, 30), Color.LIMEGREEN, "Team C");

        BarChart chart = GCharts.newBarChart(team1);

        AxisStyle axStyle = AxisStyle.newAxisStyle(Color.BLACK, 9, AxisTextAlignment.CENTER);
        AxisLabels age = AxisLabelsFactory.newAxisLabels("Age", 50.0);
        age.setAxisStyle(axStyle);
        AxisLabels count = AxisLabelsFactory.newAxisLabels("Count", 50.0);
        count.setAxisStyle(axStyle);

        //change age count
        chart.addXAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(1, ageCount.size()));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(1, Collections.max(ageCount)));
        chart.addXAxisLabels(age);

        chart.addYAxisLabels(count);

        chart.setSize(chartWidth, chartHeight);
        chart.setBarWidth((int) ((float) chartWidth / Collections.max(ages)));
        chart.setSpaceWithinGroupsOfBars(0);
        chart.setDataStacked(true);

        url = chart.toURLString();
    }
}
