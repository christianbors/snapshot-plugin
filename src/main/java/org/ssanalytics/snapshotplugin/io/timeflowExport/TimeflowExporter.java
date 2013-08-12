/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.timeflowExport;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.ssanalytics.snapshotplugin.io.timeflowExport.dataCells.ITimeflowDataCell;

/**
 *
 * @author Robert
 */
public class TimeflowExporter implements ITimeflowExporter{
    
    StringBuilder sb;
    
    public TimeflowExporter(){
        this.resetExport();
    }
    
    private void newLine(){

        sb.append("\r\n");
    }
    
    private void tab(){
        sb.append("\t");
    }

    @Override
    public void startTimeLineExport(List<TimeflowHeaderField> headerFields) {
        
        sb.append("#TIMEFLOW\tformat version\t1"); 
        this.newLine();
        sb.append("#TIMEFLOW\tsource\t[source unspecified]");
        this.newLine();
        sb.append("#TIMEFLOW\tdescription\t");
        this.newLine();
        
        for(TimeflowHeaderField header : headerFields){
            sb.append("#TIMEFLOW\tfield\t");
            sb.append(header.getName());
           this.tab();
            sb.append(header.getDataType().toString());
            this.newLine();
        }
        
        sb.append("#TIMEFLOW\tend-metadata");
        this.newLine();
        
        for(TimeflowHeaderField header : headerFields){
            sb.append("#TIMEFLOW\talias\t");
            sb.append(header.getAlias());
            this.tab();
            sb.append(header.getName());
            this.newLine();
        }
        
        sb.append("#TIMEFLOW\t====== End of Header. Data below is in tab-delimited format. =====");
        this.newLine();     
        
        int i = 0;
        while (true){
            sb.append(headerFields.get(i).getName());
            ++i;
            if(i < headerFields.size())
                this.tab();
            else
                break;
        }
        this.newLine();
    }
    
    private String replaceSpecialChars(String msg){
        if(msg == null)
            return "| NULL VALUE |";
        else
            return msg.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ');
    }

    @Override
    public void addEntry(List<ITimeflowDataCell> row) {
        
        for(ITimeflowDataCell cell : row){
            sb.append(this.replaceSpecialChars(cell.getStringValue()));
            this.tab();
        }
        
        sb.deleteCharAt(sb.length() - 1);
        
        this.newLine();
    }

    @Override
    public void finishExport(String filename) throws IOException{
        FileWriter fw = new FileWriter(filename);
        this.writeToFileAndClearStringBuilder(fw);
    }

    @Override
    public void finishExport(File file) throws IOException{
        FileWriter fw = new FileWriter(file);
        this.writeToFileAndClearStringBuilder(fw);
    }

    @Override
    public void resetExport() {
        this.sb = new StringBuilder();
    }
    
    
    public String getBufferContent(){
        return this.sb.toString();
    }
    
    private void writeToFileAndClearStringBuilder(FileWriter fw) throws IOException{
        fw.write(this.sb.toString());
        fw.close();
        this.resetExport();
    }
    
}
