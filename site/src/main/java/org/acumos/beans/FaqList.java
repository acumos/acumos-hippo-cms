package org.acumos.beans;

import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoHtmlAdapter;

@XmlRootElement(name = "faqlist")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:faqlist")
@Node(jcrType = "acumoscms:faqlist")
public class FaqList extends BaseDocument {
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:title")
    public String getTitle() {
        return getProperty("acumoscms:title");
    }

    @XmlJavaTypeAdapter(HippoHtmlAdapter.class)
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:description")
    public HippoHtml getDescription() {
        return getHippoHtml("acumoscms:description");
    }

    @HippoEssentialsGenerated(internalName = "acumoscms:faqitem")
    public List<FaqItem> getFaqItems() {
        return getLinkedBeans("acumoscms:faqitem", FaqItem.class);
    }

    /** 
     * Additional marker for the template to validate it's working with an FAQ list bean.
     */
    public boolean isFAQ() {
        return true;
    }
}
