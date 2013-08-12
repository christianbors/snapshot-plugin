/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.expandedArchiveReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.implementation.PreviousName;

/**
 *
 * @author chw
 */
public class PreviousNamesReader extends AbstractFileParser {

    private PreviousNamesReader() {
        super();
    }
    private static PreviousNamesReader instance = null;

    public static PreviousNamesReader getInstance() {
        if (instance == null) {
            instance = new PreviousNamesReader();
        }
        return instance;
    }

    public List<IPreviousName> extractPrevNamesFromFile(File namesHtml) {

        String source = this.readFile(namesHtml);

        String search = "<th>Previous Names</th>";
        int pos = source.indexOf(search);
        source = source.substring(pos + search.length());


        search = "</table>";
        pos = source.indexOf(search);
        source = source.substring(0, pos);

        search = "<td>";
        pos = source.indexOf(search);

        List<IPreviousName> prevNames = new LinkedList<>();

        while (pos >= 0) {
            pos += search.length();
            source = source.substring(pos);
            search = "</td>";
            pos = source.indexOf(search);
            prevNames.add(new PreviousName(source.substring(0, pos)));

            search = "<td>";
            pos = source.indexOf(search);
        }

        return prevNames;
    }
}
