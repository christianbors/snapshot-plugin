/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.expandedArchiveReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author chw
 */
public class AbstractFileParser {
    
    
    protected String removeUntil(String source, String search){
        int pos = source.indexOf(search);
        if(pos > -1)
            return source.substring(pos + search.length());
        else
            return source;
    }
    
    public String readFile(File source){
        
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(source));
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();
            
            while (line != null){
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            
            br.close();
            return sb.toString();            
        }catch(IOException iox){
        }
        
        return null;
    }
}
