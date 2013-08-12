/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.dbConnection.dao.sharedHelpers;

import java.util.Map;

/**
 *
 * @author chw
 */
public class WordCountHelper {

    private static WordCountHelper instance = null;

    protected WordCountHelper() {
        super();
    }

    public static WordCountHelper getInstance() {
        if (instance == null) {
            instance = new WordCountHelper();
        }
        return instance;
    }

    public Map<String, Integer> countWordsInString(String input, Map<String, Integer> map) {
        if (input == null) {
            return map;
        }

        String message = input.replace('\n', ' ').replace('.', ' ').replace(',', ' ').replace('!', ' ').replace('?', ' ').replace(';', ' ').replace(':', ' ');
        for (String s : message.split(" ")) {

            if (s.trim().equals("")) {
                continue;
            }

            if (map.containsKey(s)) {
                Integer cnt = map.get(s);
                cnt++;
                map.put(s, cnt);
            } else {
                map.put(s, new Integer(1));
            }
        }

        return map;
    }
}
