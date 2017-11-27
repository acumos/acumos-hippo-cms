package org.acumos.beans;

import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.onehippo.cms7.essentials.components.model.AuthorEntry;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImage;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoHtmlAdapter;

@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:author")
@Node(jcrType = "acumoscms:author")
public class Author extends HippoDocument implements AuthorEntry {
    public static final String ROLE = "acumoscms:role";
    public static final String ACCOUNTS = "acumoscms:accounts";
    public static final String FULL_NAME = "acumoscms:fullname";
    public static final String IMAGE = "acumoscms:image";
    public static final String CONTENT = "acumoscms:content";

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:fullname")
    public String getFullName() {
        return getProperty(FULL_NAME);
    }

    @XmlJavaTypeAdapter(HippoHtmlAdapter.class)
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:role")
    public String getRole() {
        return getProperty(ROLE);
    }

    @HippoEssentialsGenerated(internalName = "acumoscms:image")
    public HippoGalleryImage getImage() {
        return getLinkedBean(IMAGE, HippoGalleryImage.class);
    }

    @HippoEssentialsGenerated(internalName = "acumoscms:accounts")
    public List<Account> getAccounts() {
        return getChildBeansByName(ACCOUNTS, Account.class);
    }
}
