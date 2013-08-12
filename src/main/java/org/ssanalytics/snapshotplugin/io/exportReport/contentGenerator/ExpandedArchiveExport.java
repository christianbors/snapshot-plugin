/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.exportReport.contentGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IAddressBookEntry;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IExpandedArchive;
import org.ssanalytics.snapshotplugin.domainModel.expandedArchive.contract.IPreviousName;
import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveDbImporter;
import org.ssanalytics.snapshotplugin.io.expandedArchiveReader.ExpandedArchiveReader;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.ReportContent;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.Content;
import org.ssanalytics.snapshotplugin.io.exportReport.spi.content.TextContent;

/**
 *
 * @author Christian
 */
@ServiceProvider(service = ReportContent.class)
public class ExpandedArchiveExport implements ReportContent {

    private List<Content> listExpandedArchiveInfo;
    private String archiveFilePath;
    private boolean folderChosen;
    private boolean nameExport;
    private boolean addressExport;

    @Override
    public void generate(ISnapshotInfo snapshot) {
        listExpandedArchiveInfo = new LinkedList<>();
        if (folderChosen) {
            if (nameExport || addressExport) {
                try {
                    IExpandedArchive archive = ExpandedArchiveReader.getInstance().readExpandedArchiveFromDirectory(archiveFilePath);
                    if (nameExport) {
                        List<IAddressBookEntry> addressBook = archive.getAddressBook();
                        if (!addressBook.isEmpty()) {
                            listExpandedArchiveInfo.add(new TextContent("Address Book entries\n\n"));
                            for (IAddressBookEntry entry : addressBook) {
                                listExpandedArchiveInfo.add(new TextContent("Name: " + entry.getName()));
                                listExpandedArchiveInfo.add(new TextContent(entry.getContactInfoList().toArray(new String[0])));
                                listExpandedArchiveInfo.add(new TextContent("\n"));
                            }
                            listExpandedArchiveInfo.add(new TextContent("\n"));
                        }
                    }
                    if (addressExport) {
                        List<IPreviousName> previousNames = archive.getPreviousNames();
                        if (!previousNames.isEmpty()) {
                            listExpandedArchiveInfo.add(new TextContent("Previous names\n\n"));
                            for (IPreviousName name : previousNames) {
                                listExpandedArchiveInfo.add(new TextContent(name.getPreviousName()));
                            }
                            listExpandedArchiveInfo.add(new TextContent("\n"));
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    @Override
    public Content[] getContent() {
        return listExpandedArchiveInfo.toArray(new Content[0]);
    }

    public void setAddressExport(boolean export) {
        this.addressExport = export;
    }

    public void setNameExport(boolean export) {
        this.nameExport = export;
    }

    public void setArchiveFilePath(String path) {
        if (!path.equals("Please Choose a File")) {
            this.archiveFilePath = path;
            folderChosen = true;
        }
    }
}
