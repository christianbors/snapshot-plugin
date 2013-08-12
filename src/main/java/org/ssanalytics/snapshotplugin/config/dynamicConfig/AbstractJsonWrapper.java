/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.config.dynamicConfig;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Robert
 */
public class AbstractJsonWrapper {

    protected JSONObject job;

    public AbstractJsonWrapper(JSONObject job) {
        this.job = job;
    }

    protected String getString(String key) {
        try {
            return (String) this.job.get(key);
        } catch (Exception ex) {
            System.out.println("Exception in AbstractjsonWrapper.getString()");

            return "";
        }
    }

    protected JSONArray getJar(String key) {
        try {
            return (JSONArray) this.job.get(key);
        } catch (ClassCastException exception) {
            //this should only happen, if f.e. the JSOAN Array "home" is "false" instead of an arary.
            System.out.println(exception.getMessage() + " (AbstractJsonWrapper.getJar())");

            return null;
        }
    }
}
