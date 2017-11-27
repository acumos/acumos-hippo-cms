package org.acumos.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoHtmlAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoGalleryImageAdapter;

@XmlRootElement(name = "banner")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:bannerdocument")
@Node(jcrType = "acumoscms:bannerdocument")
public class Banner extends BaseDocument {
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

    @XmlJavaTypeAdapter(HippoGalleryImageAdapter.class)
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean("acumoscms:image", HippoGalleryImageSet.class);
    }

    @HippoEssentialsGenerated(internalName = "acumoscms:link")
    public HippoBean getLink() {
        return getLinkedBean("acumoscms:link", HippoBean.class);
    }
}
