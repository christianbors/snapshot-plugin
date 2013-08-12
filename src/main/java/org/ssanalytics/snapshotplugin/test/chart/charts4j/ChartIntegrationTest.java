/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.chart.charts4j;

import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.LineChart;
import com.googlecode.charts4j.Plot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.GeographicalArea;
import com.googlecode.charts4j.MapChart;
import com.googlecode.charts4j.Plots;
import com.googlecode.charts4j.PoliticalBoundary;
import com.googlecode.charts4j.USAState;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
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
import org.ssanalytics.snapshotplugin.config.dynamicConfig.ConfigurationFileManager;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;

/**
 *
 * @author christian
 */
public class ChartIntegrationTest {

    public static void main(String args[]) throws IOException, SQLException, Exception {
        ConfigurationFileManager.CONFIG_FILE_NAME = "localPostgre.json";
//        createLineAgeChart();
        createMapChart();
    }

    private static void createLineAgeChart() throws IOException, SQLException, Exception {
        List<IProfile> profiles = DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion("bob");
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
                }
                catch(ParseException ex) {
                }
            }
        }
        
        List<Integer> ageCount = new ArrayList<>(Collections.nCopies(Collections.max(ages), 0));
        for(int item : ages) {
            int agesCounted = ageCount.get(item-1);
            ageCount.set(item-1, ++agesCounted);
        }
        
        ArrayList<Double> ageNorm = new ArrayList<>(Collections.nCopies(Collections.max(ages), 0.0));
        System.out.println(ageNorm.size());
        for(int i = 0; i < ageNorm.size(); ++i) {
            double normVal = ((float) ageCount.get(i))/ Collections.max(ageCount);
            System.out.println(ageCount.get(i) + "/" + Collections.max(ageCount) + " = " + normVal);
            ageNorm.set(i, normVal*100);
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

        int width = 650;
        chart.setSize(width, 400);
        chart.setBarWidth((int) ((float)width/Collections.max(ages)));
        chart.setSpaceWithinGroupsOfBars(0);
        chart.setDataStacked(true);

        displayUrlString(chart.toURLString());
    }

    private static void createMapChart() throws IOException {
        MapChart chart = GCharts.newMapChart(GeographicalArea.USA);

        PoliticalBoundary al = new USAState(USAState.Code.AL, 10);
        PoliticalBoundary ak = new USAState(USAState.Code.AK, 10);
        PoliticalBoundary az = new USAState(USAState.Code.AZ, 10);
        PoliticalBoundary ar = new USAState(USAState.Code.AR, 10);
        PoliticalBoundary ca = new USAState(USAState.Code.CA, 90);
        PoliticalBoundary co = new USAState(USAState.Code.CO, 50);
        PoliticalBoundary ct = new USAState(USAState.Code.CT, 90);
        PoliticalBoundary de = new USAState(USAState.Code.DE, 90);
        PoliticalBoundary fl = new USAState(USAState.Code.FL, 50);
        PoliticalBoundary ga = new USAState(USAState.Code.GA, 10);
        PoliticalBoundary hi = new USAState(USAState.Code.HI, 90);
        PoliticalBoundary id = new USAState(USAState.Code.ID, 10);
        PoliticalBoundary il = new USAState(USAState.Code.IL, 90);
        PoliticalBoundary in = new USAState(USAState.Code.IN, 50);
        PoliticalBoundary ia = new USAState(USAState.Code.IA, 50);
        PoliticalBoundary ks = new USAState(USAState.Code.KS, 10);
        PoliticalBoundary ky = new USAState(USAState.Code.KY, 10);
        PoliticalBoundary la = new USAState(USAState.Code.LA, 10);
        PoliticalBoundary me = new USAState(USAState.Code.ME, 50);
        PoliticalBoundary md = new USAState(USAState.Code.MD, 90);
        PoliticalBoundary ma = new USAState(USAState.Code.MA, 90);
        PoliticalBoundary mi = new USAState(USAState.Code.MI, 90);
        PoliticalBoundary mn = new USAState(USAState.Code.MN, 90);
        PoliticalBoundary ms = new USAState(USAState.Code.MS, 10);
        PoliticalBoundary mo = new USAState(USAState.Code.MO, 10);
        PoliticalBoundary mt = new USAState(USAState.Code.MT, 90);
        PoliticalBoundary ne = new USAState(USAState.Code.NE, 10);
        PoliticalBoundary nv = new USAState(USAState.Code.NV, 90);
        PoliticalBoundary nh = new USAState(USAState.Code.NH, 90);
        PoliticalBoundary nj = new USAState(USAState.Code.NJ, 90);
        PoliticalBoundary nm = new USAState(USAState.Code.NM, 50);
        PoliticalBoundary ny = new USAState(USAState.Code.NY, 90);
        PoliticalBoundary nc = new USAState(USAState.Code.NC, 50);
        PoliticalBoundary nd = new USAState(USAState.Code.ND, 90);
        PoliticalBoundary oh = new USAState(USAState.Code.OH, 90);
        PoliticalBoundary ok = new USAState(USAState.Code.OK, 10);
        PoliticalBoundary or = new USAState(USAState.Code.OR, 90);
        PoliticalBoundary pa = new USAState(USAState.Code.PA, 50);
        PoliticalBoundary ri = new USAState(USAState.Code.RI, 90);
        PoliticalBoundary sc = new USAState(USAState.Code.SC, 50);
        PoliticalBoundary sd = new USAState(USAState.Code.SD, 10);
        PoliticalBoundary tn = new USAState(USAState.Code.TN, 10);
        PoliticalBoundary tx = new USAState(USAState.Code.TX, 10);
        PoliticalBoundary ut = new USAState(USAState.Code.UT, 10);
        PoliticalBoundary vt = new USAState(USAState.Code.VT, 90);
        PoliticalBoundary va = new USAState(USAState.Code.VA, 50);
        PoliticalBoundary wa = new USAState(USAState.Code.WA, 90);
        PoliticalBoundary wv = new USAState(USAState.Code.WV, 10);
        PoliticalBoundary wi = new USAState(USAState.Code.WI, 90);
        PoliticalBoundary wy = new USAState(USAState.Code.WY, 10);

        chart.addPoliticalBoundaries(al, ak, az, ar, ca, co, ct, de, fl, ga, hi, id, il, in, ia, ks, ky, la, me, md, ma, mi, mn, ms, mo, mt, ne, nv, nh, nj, nm, ny, nc, nd, oh, ok, or, pa, ri, sc, sd, tn, tx, ut, vt, va, wa, wv, wi, wy);
        chart.setColorGradient(Color.WHITE, Color.RED, Color.BLUE);
        chart.setBackgroundFill(Fills.newSolidFill(Color.ALICEBLUE));

        displayUrlString(chart.toURLString());

        BufferedImage buffer = ImageIO.read(new URL(chart.toURLString()));
        System.out.println(chart.toURLString());
        ImageIO.write(buffer, "png", new File("test.png"));
    }

    private static void displayUrlString(final String urlString) throws IOException {
        JFrame frame = new JFrame();
        JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(urlString))));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
