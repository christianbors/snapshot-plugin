/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.jsonSnapshotInsert.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author chw
 */
public class AccountCounter {
    
    public int countAccountsInJsonFile(String file) throws ParseException, FileNotFoundException, IOException{
        JSONParser jp = new JSONParser();
        Reader r = new FileReader(file);
        
        JSONObject job = (JSONObject) jp.parse(r);
        
        return job.size();        
    }    
}
