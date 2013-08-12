/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.performanceEvaluator.helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.helper.AccountCounter;

/**
 *
 * @author chw
 */
public class CountAccountsInSnapshot {
    
    private static String FILENAME = "D:/git/ssanalytics/testdata/evalshot_large.json";
    
    public static void main(String args[]) throws ParseException, FileNotFoundException, IOException{
        AccountCounter ac = new AccountCounter();
        
        int i = ac.countAccountsInJsonFile(FILENAME);
        
        System.out.println("Number of Accounts: " + i);
    }
}
