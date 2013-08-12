/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test.chart.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetUtilities;

/**
 *
 * @author christian
 */
public class ChartIntegrationTest {
    
    public static void main(String args[]) {
        ChartFactory.createBarChart("Test BarChart", "X", "Y", createDataset(), PlotOrientation.HORIZONTAL, true, true, false);
    }

    private static CategoryDataset createDataset() {
        final double[][] data = new double[][] {{4.0, 3.0, -2.0, 3.0, 6.0}};
        return DatasetUtilities.createCategoryDataset(
            "Series ",
            "Category ",
            data
        );
    }
}
