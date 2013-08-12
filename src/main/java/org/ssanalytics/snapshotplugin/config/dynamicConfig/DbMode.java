/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.config.dynamicConfig;

/**
 *
 * @author Robert
 */
public enum DbMode {

    POSTGRESQL {
        public String toString() {
            return "Postgre SQL";
        }
    },
    MONGODB {
        public String toString() {
            return "MongoDB";
        }
    },
    MYSQL {
        public String toString() {
            return "MySQL";
        }
    }    
}
