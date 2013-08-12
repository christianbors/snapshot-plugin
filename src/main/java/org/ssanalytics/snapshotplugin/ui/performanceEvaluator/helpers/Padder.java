/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers;

/**
 *
 * @author chw
 */
public class Padder {
    
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
    
}
