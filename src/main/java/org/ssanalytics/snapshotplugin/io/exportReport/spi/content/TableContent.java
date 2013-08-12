/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.spi.content;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ContentGenerator;

/**
 *
 * @author christian
 */
public class TableContent implements Content {

    private Map<String, String> table;
    private List<String> listTable;
    private String caption;

    public TableContent(Map<String, String> table, String caption) {
        this.table = table;
        this.caption = caption;
        this.listTable = new LinkedList<>();
    }

    public TableContent(List<String> listTable, String caption) {
        this.listTable = listTable;
        this.caption = caption;
        this.table = new LinkedHashMap<>();
    }

    public TableContent(Map<String, String> table) {
        this(table, "");
    }

    public TableContent(List<String> listTable) {
        this(listTable, "");
    }

    public Map<String, String> getTable() {
        return table;
    }

    public List<String> getTableList() {
        return listTable;
    }

    public String getCaption() {
        return caption;
    }

//    @Override
//    public Class<? extends Content> getContentClass() {
//        return TableContent.class;
//    }
    @Override
    public void sendContent(ContentGenerator generator) {
        generator.generateContent(this);
    }
}
