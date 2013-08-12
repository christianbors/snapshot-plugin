/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.test;

/**
 *
 * @author chw
 */
public class MemoryTest {
    
    public static void main(String args[]){
        
        System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory()/1024/1024);
        System.out.println("FeeMemory: " + Runtime.getRuntime().freeMemory()/1024/1024);
        System.out.println("TotalMemory: " + Runtime.getRuntime().totalMemory()/1024/1024);
        
    }
    
}
