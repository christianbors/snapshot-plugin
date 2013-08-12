/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sharedHelpers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author chw
 */
public class WeekDayHelper {
        
    private static WeekDayHelper instance = null;

    protected WeekDayHelper() {
        super();
    }

    public static WeekDayHelper getInstance() {
        if (instance == null) {
            instance = new WeekDayHelper();
        }
        return instance;
    }
    
    
    public Date getLastSunday(Long timestamp){
    
        if((timestamp == null) || (timestamp == 0))
            return null;
        
        Date dt = new Date(timestamp);
        long weekday = dt.getDay();
        long numberOfMSInADay = 1000*60*60*24;
        dt = new Date(timestamp - (numberOfMSInADay * weekday));
        
        dt.setHours(0);
        dt.setMinutes(0);
        dt.setSeconds(0);
        
        return dt;        
    }
    
    
}
