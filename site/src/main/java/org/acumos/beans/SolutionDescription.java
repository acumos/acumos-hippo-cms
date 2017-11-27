package org.acumos.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ContentNodeBindingException;
import org.hippoecm.hst.content.beans.Node;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "solutiondescription")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:solutionDescription")
@Node(jcrType = "acumoscms:solutionDescription")
public class SolutionDescription extends BaseDocument implements
        ContentNodeBinder {
    private String description;

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:html")
    public String getDescription() {
        return (description == null) ? (String) getProperty("acumoscms:description")
                : description;
    }

    public void setDescription(String html) {
        this.description = html;
    }

    public boolean bind(Object content, javax.jcr.Node node)
            throws ContentNodeBindingException {
        if (content instanceof SolutionDescription) {
            try {
                SolutionDescription desc = (SolutionDescription) content;
                node.setProperty("acumoscms:description",
                        desc.getDescription());
            } catch (Exception e) {
                System.out.println("Unable to bind the content to the JCR Node"
                        + e.getMessage());
                throw new ContentNodeBindingException(e);
            }
        } else {
            return false;
        }
        return true;
    }
}
