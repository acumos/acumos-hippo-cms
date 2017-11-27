package org.acumos.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoHtmlAdapter;

@XmlRootElement(name = "faqitem")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:faqitem")
@Node(jcrType = "acumoscms:faqitem")
public class FaqItem extends BaseDocument {
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:question")
    public String getQuestion() {
        return getProperty("acumoscms:question");
    }

    @XmlJavaTypeAdapter(HippoHtmlAdapter.class)
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:answer")
    public HippoHtml getAnswer() {
        return getHippoHtml("acumoscms:answer");
    }
}
