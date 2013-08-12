/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.snapshot;

import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.ssanalytics.snapshotplugin.io.generator.AbstractGenerator;

/**
 *
 * @author christian
 */
public abstract class AbstractSnapshotGenerator extends AbstractGenerator {

    @Override
    public abstract void generate(ContainerLoader container);

}
