package org.ssanalytics.snapshotplugin.ui.helper.renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeData;

import org.gephi.preview.api.Item;
import org.gephi.preview.api.PDFTarget;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperties;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.plugin.builders.NodeBuilder;
import org.gephi.preview.plugin.items.NodeItem;
import org.gephi.preview.spi.ItemBuilder;
import org.gephi.preview.spi.Renderer;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import processing.core.PGraphicsJava2D;

@ServiceProvider(service = Renderer.class, position = 400)
public class AttendingRenderer implements Renderer {

    public static final String ENABLE_ATTENDING_VIEW = "node.attending.enable";

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(AttendingRenderer.class,
                "AttendingRenderer.name");
    }

    @Override
    public PreviewProperty[] getProperties() {
        return new PreviewProperty[]{PreviewProperty.createProperty(this,
                    ENABLE_ATTENDING_VIEW, Boolean.class, "Show attending ratio",
                    "Pie chart view", PreviewProperty.CATEGORY_NODES).setValue(false)
                };
    }

    @Override
    public boolean isRendererForitem(Item item, PreviewProperties properties) {
        return item instanceof NodeItem && properties.getBooleanValue(ENABLE_ATTENDING_VIEW);
    }

    @Override
    public boolean needsItemBuilder(ItemBuilder itemBuilder, PreviewProperties properties) {
        return itemBuilder instanceof NodeBuilder && properties.getBooleanValue(ENABLE_ATTENDING_VIEW);
    }

    @Override
    public void preProcess(PreviewModel arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(Item item, RenderTarget target, PreviewProperties properties) {
        if (target instanceof ProcessingTarget) {
            renderProcessing(item, (ProcessingTarget) target, properties);
        }
        if (target instanceof PDFTarget) {
            this.renderPDF(item, (PDFTarget) target, properties);
        }
    }

    public void renderProcessing(Item item, ProcessingTarget target, PreviewProperties properties) {
        Float x = item.getData(NodeItem.X);
        Float y = item.getData(NodeItem.Y);
        Float size = item.getData(NodeItem.SIZE);
        float angleAtt = 0;
        float angleUns = 0;
        NodeData nd = ((Node) item.getSource()).getNodeData();
        if (nd.getAttributes().getValue("type") == "Event") {
            if (nd.getAttributes().getValue("sum") != null) {
                if ((int) nd.getAttributes().getValue("sum") > 2) {
                    System.out.println(nd.getAttributes().getValue("sum"));
                }
                int sum = (int) nd.getAttributes().getValue("sum");
                int att_int = (int) nd.getAttributes().getValue("attending");
                int uns_int = (int) nd.getAttributes().getValue("declined");
                if (sum > 0) {
                    float att = (float) att_int / (float) sum;
                    float uns = (float) uns_int / (float) sum;
                    //float uns = ((int) n.getNodeData().getAttributes().getValue("unsure"))/ sum;

                    angleAtt = 360 * att;
                    angleUns = 360 * uns;


                }
            }

            PGraphicsJava2D graphics = (PGraphicsJava2D) target.getGraphics();
            Arc2D.Float arc_att = new Arc2D.Float(Arc2D.PIE);
            arc_att.setFrame(x - ((size * 1.5) / 2), y - ((size * 1.5) / 2), size * 1.5, size * 1.5);
            arc_att.setAngleStart(0);
            arc_att.setAngleExtent(angleAtt);
            Arc2D.Float arc_uns = new Arc2D.Float(Arc2D.PIE);
            arc_uns.setFrame(x - ((size * 1.4) / 2), y - ((size * 1.4) / 2), size * 1.4, size * 1.4);
            arc_uns.setAngleStart(angleAtt);
            arc_uns.setAngleExtent(angleUns);
            Arc2D.Float arc_dec = new Arc2D.Float(Arc2D.PIE);
            arc_dec.setFrame(x - ((size * 1.3) / 2), y - ((size * 1.3) / 2), size * 1.3, size * 1.3);
            arc_dec.setAngleStart(0);
            arc_dec.setAngleExtent(-(360 - angleAtt - angleUns));
            Graphics2D g2 = graphics.g2;
            g2.setColor(Color.black);
            if (angleAtt > 0) {
                g2.draw(arc_att);
            }
            if (angleUns > 0) {
                g2.draw(arc_uns);
            }
            g2.draw(arc_dec);
            g2.setColor(Color.green);
            g2.fill(arc_att);
            g2.setColor(Color.blue);
            g2.fill(arc_uns);
            g2.setColor(Color.red);
            g2.fill(arc_dec);
        }

    }

    public void renderPDF(Item item, PDFTarget target, PreviewProperties properties) {
    }
}
