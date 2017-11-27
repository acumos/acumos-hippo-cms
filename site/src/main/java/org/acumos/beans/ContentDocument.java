package org.acumos.beans;

import java.util.Calendar;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoHtmlAdapter;

@XmlRootElement(name = "contentdocument")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:contentdocument")
@Node(jcrType = "acumoscms:contentdocument")
public class ContentDocument extends BaseDocument {
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:introduction")
    public String getIntroduction() {
        return getProperty("acumoscms:introduction");
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:title")
    public String getTitle() {
        return getProperty("acumoscms:title");
    }

    @XmlJavaTypeAdapter(HippoHtmlAdapter.class)
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:content")
    public HippoHtml getContent() {
        return getHippoHtml("acumoscms:content");
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:publicationdate")
    public Calendar getPublicationDate() {
        return getProperty("acumoscms:publicationdate");
    }
}
