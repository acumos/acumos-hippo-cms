package org.acumos.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:account")
@Node(jcrType = "acumoscms:account")
public class Account extends HippoCompound {
    public static final String TYPE = "acumoscms:type";
    public static final String LINK = "acumoscms:link";

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:type")
    public String getType() {
        return getProperty(TYPE);
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:link")
    public String getLink() {
        return getProperty(LINK);
    }
}
