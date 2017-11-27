package org.acumos.beans;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "video")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:video")
@Node(jcrType = "acumoscms:video")
public class Video extends BaseDocument {
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:title")
    public String getTitle() {
        return getProperty("acumoscms:title");
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:description")
    public String getDescription() {
        return getProperty("acumoscms:description");
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:link")
    public String getLink() {
        return getProperty("acumoscms:link");
    }
}
