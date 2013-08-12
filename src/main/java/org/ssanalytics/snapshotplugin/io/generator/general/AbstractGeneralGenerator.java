/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.io.generator.general;

import java.util.List;
import org.ssanalytics.snapshotplugin.domainModel.crawlerData.contract.profile.IProfile;
import org.ssanalytics.snapshotplugin.io.generator.AbstractGenerator;

/**
 *
 * @author christian
 */
public abstract class AbstractGeneralGenerator extends AbstractGenerator {

    protected List<IProfile> profiles = null;

    public void setProfileList(List<IProfile> profiles) {
        this.profiles = profiles;
    }

    public boolean hasProfiles() {
        return this.profiles != null;
    }
}
