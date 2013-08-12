package org.ssanalytics.snapshotplugin.ui.standalone.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeData;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.spi.GraphExporter;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ImportController;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.plugin.renderers.ArrowRenderer;
import org.gephi.preview.plugin.renderers.EdgeLabelRenderer;
import org.gephi.preview.plugin.renderers.EdgeRenderer;
import org.gephi.preview.plugin.renderers.NodeLabelRenderer;
import org.gephi.preview.plugin.renderers.NodeRenderer;
import org.gephi.preview.spi.Renderer;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.openide.util.Lookup;
import org.ssanalytics.snapshotplugin.io.dbConnection.dao.factory.DaoFactory;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.event.IEvent;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.friend.IFriend;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.snapshotInfo.ISnapshotInfo;
import org.ssanalytics.snapshotplugin.ui.helper.renderers.AttendingRenderer;
import processing.core.PApplet;

public class GraphVisualizationController {

    private ExportController exportController;
    private GraphExporter graphExport;
    private ProjectController projectController;
    private ImportController importController;
    private Container container;
    private Workspace workspace;
    private GraphModel graphModel;
    private PreviewController previewController;
    private PreviewModel previewModel;
    private RankingController rankingController;
    private JPanel panelGraphVis;
    private PApplet applet;
    private ProcessingTarget target;
    private boolean hasGraph;

    public GraphVisualizationController() throws SQLException {
        exportController = Lookup.getDefault().lookup(ExportController.class);
        graphExport = (GraphExporter) exportController.getExporter("gexf");
        projectController = Lookup.getDefault().lookup(ProjectController.class);
        projectController.newProject();
        workspace = projectController.getCurrentWorkspace();
//		graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        importController = Lookup.getDefault().lookup(ImportController.class);
        previewController = Lookup.getDefault().lookup(PreviewController.class);
        rankingController = Lookup.getDefault().lookup(RankingController.class);

        hasGraph = false;
    }

    /*
     * add more sophisticated export functionality
     */
    public void exportCurrentGraph(String name) {
        graphExport.setExportVisible(true); // Only exports the visible (filtered) graph
        graphExport.setWorkspace(workspace);
        try {
            exportController.exportFile(new File(name), graphExport);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

//	public void setInterestGraph(String snapshotName, List<IAccount> accounts,
//			IRootAccount rootAcc) {
//		UndirectedGraph intGraph = graphModel.getUndirectedGraph();
//
//		Node rootNode = graphModel.factory().newNode(rootAcc.getAccountId());
//		rootNode.getNodeData().setLabel(rootAcc.getProfile().getName());
//		intGraph.addNode(rootNode);
//
//		NodeData intNodeData;
//		Node intNode;
//		for (IInterest interest : rootAcc.getInterests()) {
//			intNode = graphModel.factory().newNode(interest.getId());
//			intNodeData = intNode.getNodeData();
//			intNodeData.setLabel(interest.getName());
//
//			intGraph.addNode(intNode);
//
//			Edge intEdge = graphModel.factory().newEdge(rootNode, intNode);
//			intGraph.addEdge(intEdge);
//		}
//
//	}
    public void createFriendIsFriendGraph(ISnapshotInfo snapshot, JPanel target) throws Exception {

        graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        graphModel.clear();

        UndirectedGraph fifGraph = graphModel.getUndirectedGraph();
        for (IFriend friend : DaoFactory.getFriendDAO().getFriendListSpecificVersion(snapshot.getValue(), snapshot.version())) {
            Node f = graphModel.factory().newNode(friend.getId());
            System.out.println(friend.getName());
            f.getNodeData().setLabel(friend.getName());
            fifGraph.addNode(f);
        }
        for (Map.Entry<String, List<String>> entry : DaoFactory.getRootAccountDAO().getFriendIsFriend(snapshot.getValue(), snapshot.version()).entrySet()) {
            for (String edgeConnection : entry.getValue()) {
                Node n0 = fifGraph.getNode(entry.getKey());
                Node n1 = fifGraph.getNode(edgeConnection);
                Edge e = graphModel.factory().newEdge(n0, n1);
                fifGraph.addEdge(e);
            }
        }

        this.hasGraph = true;
        rankEventsGraph();
        target.add(visualizeCurrentGraph());
    }

    public void createEventsGraph(ISnapshotInfo snapshot, JPanel target) throws Exception {

        graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        graphModel.clear();

        DirectedGraph eventsGraph = graphModel.getDirectedGraph();

        // with the attribute model we can alter the variables for each node and edge
        AttributeModel attModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        attModel.getNodeTable().addColumn("attending", AttributeType.INT);
        attModel.getNodeTable().addColumn("declined", AttributeType.INT);
        attModel.getNodeTable().addColumn("unsure", AttributeType.INT);
        attModel.getNodeTable().addColumn("sum", AttributeType.INT);


        List<IProfile> listProfiles = DaoFactory.getProfileDAO().getProfileListForSnapshotLatestVersion(snapshot.getValue());

        Node nProfile;
        Edge eAttending;
        for (IProfile profile : listProfiles) {
            nProfile = graphModel.factory().newNode(profile.getId());
            nProfile.getNodeData().setLabel(profile.getName());
            eventsGraph.addNode(nProfile);
        }

        int decl = 0;
        Node nEvent;
        int i = 0;
        for (IEvent event : DaoFactory.getEventDAO().getEventListForSnapshotLatestVersion(snapshot.getValue())) {
            if (eventsGraph.getNode(event.getId()) != null) {
                nEvent = eventsGraph.getNode(event.getId());
            } else {
                nEvent = graphModel.factory().newNode(event.getId());
                nEvent.getNodeData().setLabel(event.getName());
                eventsGraph.addNode(nEvent);
            }

            if (eventsGraph.getNode(event.getAccountId()) != null && nEvent != null) {
                eAttending = graphModel.factory().newEdge(eventsGraph.getNode(event.getAccountId()), nEvent);

                NodeData nd = nEvent.getNodeData();

                if (nd.getAttributes().getValue("sum") == null) {
                    nd.getAttributes().setValue("sum", 0);
                    nd.getAttributes().setValue("attending", 0);
                    nd.getAttributes().setValue("unsure", 0);
                    nd.getAttributes().setValue("declined", 0);
                }

                nd.getAttributes().setValue("sum", ((int) nd.getAttributes().getValue("sum") + 1));
                switch (event.getRsvp_status()) {
                    case "attending":
                        eAttending.getEdgeData().setColor(0, 1, 0); // attending
                        nd.getAttributes().setValue("attending", ((int) nd.getAttributes().getValue("attending") + 1));
                        break;
                    case "declined":
                        eAttending.getEdgeData().setColor(0, 0, 1); // unsure
                        decl++;
                        nd.getAttributes().setValue("declined", ((int) nd.getAttributes().getValue("declined") + 1));
                        break;
                    default:
                        eAttending.getEdgeData().setColor(1, 0, 0); // declining
                        nd.getAttributes().setValue("unsure", ((int) nd.getAttributes().getValue("unsure") + 1));
                        break;
                }
                eventsGraph.addEdge(eAttending);
            }
            System.out.println("declined: " + decl);
//			System.out.println("Event: " + event.getName() + ", " + event.getRsvp_status());
        }

        this.hasGraph = true;
        rankEventsGraph();
        target.add(visualizeCurrentGraph());
    }

    private void rankEventsGraph() {
        Ranking<Node> degreeColorRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
        AbstractColorTransformer<Node> colorTrans = (AbstractColorTransformer<Node>) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);

        Ranking<Node> degreeSizeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.INDEGREE_RANKING);
        AbstractSizeTransformer<Node> sizeTrans = (AbstractSizeTransformer<Node>) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);


        colorTrans.setColors(new Color[]{new Color(0xFEF0D9), new Color(0xB30000)});
        sizeTrans.setMaxSize(20);
        sizeTrans.setMinSize(5);
        rankingController.transform(degreeColorRanking, colorTrans);
//		rankingController.transform(degreeSizeRanking, sizeTrans);

        previewModel = previewController.getModel();




        previewModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
        previewModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
//		previewModel.getProperties().putValue(GlowRenderer.ENABLE_NODE_GLOW, true);
        previewModel.getProperties().putValue(AttendingRenderer.ENABLE_ATTENDING_VIEW, true);
//		previewModel.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10f);
//		previewModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.GRAY);
        previewController.refreshPreview();
    }

    private JPanel visualizeCurrentGraph() {

        previewModel = previewController.getModel();

        target = (ProcessingTarget) previewController
                .getRenderTarget(RenderTarget.PROCESSING_TARGET);
        applet = target.getApplet();
        applet.init();

        // Refresh the preview and reset the zoom
        Renderer rArr[] = {Lookup.getDefault().lookup(NodeLabelRenderer.class),
            Lookup.getDefault().lookup(EdgeLabelRenderer.class),
            Lookup.getDefault().lookup(AttendingRenderer.class),
            Lookup.getDefault().lookup(EdgeRenderer.class),
            Lookup.getDefault().lookup(ArrowRenderer.class)};
        previewController.render(target, rArr);
        target.zoomPlus();
//		target.resetZoom();
        target.refresh();

        JPanel jp = new JPanel(new BorderLayout());
        jp.add(applet, BorderLayout.CENTER);
        jp.setVisible(true);

        return jp;
    }

    public boolean hasGraph() {
        return hasGraph;
    }

    /**
     * TODO implement initiated graph panel in which we can initially add/remove
     * nodes etc.
     */
    public void clearCurrentGraph() {
        this.hasGraph = false;
        this.graphModel.clear();
    }

    public void showLabels(boolean visible) {
        System.out.println("Show labels: " + visible);
        previewModel = previewController.getModel();
        previewModel.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, visible);
        previewController.refreshPreview();

        target.refresh();
    }

    public NodeRenderer[] getNodeStyles() {
        List<NodeRenderer> listRenderers = new ArrayList<NodeRenderer>();
        for (Renderer r : previewController.getRegisteredRenderers()) {
            if (r instanceof NodeRenderer) {
                listRenderers.add((NodeRenderer) r);
            }
        }
        return listRenderers.toArray(new NodeRenderer[0]);
    }

    public Renderer[] getRenderers() {
        return previewController.getRegisteredRenderers();
    }
}
