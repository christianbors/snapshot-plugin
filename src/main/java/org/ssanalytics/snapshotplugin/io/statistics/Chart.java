/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.statistics;

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
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;

/**
 *
 * @author christian
 */
public class Chart {

    private static void createAgeBarChart(ISnapshotInfo snapshot) throws Exception {
        List<IProfile> profiles = DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion(snapshot.getValue());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        List<Integer> ages = new LinkedList<>();
        Calendar now = Calendar.getInstance();
        int yearNow = now.get(Calendar.YEAR);
        for (IProfile profile : profiles) {
            if (profile.getBirthday() != null) {
                Date birthday = formatter.parse(profile.getBirthday());
                Calendar birth = Calendar.getInstance();
                birth.setTime(birthday);
                ages.add(yearNow - birth.get(Calendar.YEAR));
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


        BarChartPlot team1 = Plots.newBarChartPlot(Data.newData(ageNorm), Color.BLUEVIOLET);

        BarChart chart = GCharts.newBarChart(team1);

        AxisStyle axStyle = AxisStyle.newAxisStyle(Color.BLACK, 9, AxisTextAlignment.CENTER);
        AxisLabels age = AxisLabelsFactory.newAxisLabels("Age", 50.0);
        age.setAxisStyle(axStyle);
        AxisLabels count = AxisLabelsFactory.newAxisLabels("Count", 50.0);
        count.setAxisStyle(axStyle);

        chart.addXAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(1, ageCount.size()));
        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(1, Collections.max(ageCount)));
        chart.addXAxisLabels(age);

        chart.addYAxisLabels(count);

        int width = 650;
        chart.setSize(width, 400);
        chart.setBarWidth((int) ((float) width / Collections.max(ages)));
        chart.setSpaceWithinGroupsOfBars(0);
        chart.setDataStacked(true);

        testUrlDisplay(chart.toURLString());
    }
    
    public static void testUrlDisplay(String urlString) throws IOException {
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
