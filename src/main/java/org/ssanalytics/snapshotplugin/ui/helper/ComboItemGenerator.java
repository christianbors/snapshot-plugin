/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.ui.helper;

import org.gephi.io.generator.spi.Generator;

/**
 *
 * @author christian
 */
public class ComboItemGenerator {

    private Generator generator;

    public ComboItemGenerator(Generator generator) {
        this.generator = generator;
    }

    public Generator getGenerator() {
        return this.generator;
    }

    @Override
    public String toString() {
        return generator.getName();
    }
}
