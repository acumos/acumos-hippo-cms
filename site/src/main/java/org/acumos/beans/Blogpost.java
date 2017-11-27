package org.acumos.beans;

import java.util.Calendar;
import java.util.List;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.components.model.Authors;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.onehippo.cms7.essentials.components.rest.adapters.HippoHtmlAdapter;

@XmlRootElement(name = "blogpost")
@XmlAccessorType(XmlAccessType.NONE)
@HippoEssentialsGenerated(internalName = "acumoscms:blogpost")
@Node(jcrType = "acumoscms:blogpost")
public class Blogpost extends HippoDocument implements Authors {
    public static final String TITLE = "acumoscms:title";
    public static final String INTRODUCTION = "acumoscms:introduction";
    public static final String CONTENT = "acumoscms:content";
    public static final String PUBLICATION_DATE = "acumoscms:publicationdate";
    public static final String CATEGORIES = "acumoscms:categories";
    public static final String AUTHOR = "acumoscms:author";
    public static final String AUTHOR_NAMES = "acumoscms:authornames";
    public static final String LINK = "acumoscms:link";
    public static final String AUTHORS = "acumoscms:authors";
    public static final String TAGS = "hippostd:tags";

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:publicationdate")
    public Calendar getPublicationDate() {
        return getProperty(PUBLICATION_DATE);
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:authornames")
    public String[] getAuthorNames() {
        return getProperty(AUTHOR_NAMES);
    }

    @XmlElement
    public String getAuthor() {
        final String[] authorNames = getAuthorNames();
        if (authorNames != null && authorNames.length > 0) {
            return authorNames[0];
        }
        return null;
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:title")
    public String getTitle() {
        return getProperty(TITLE);
    }

    @XmlJavaTypeAdapter(HippoHtmlAdapter.class)
    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:content")
    public HippoHtml getContent() {
        return getHippoHtml(CONTENT);
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:introduction")
    public String getIntroduction() {
        return getProperty(INTRODUCTION);
    }

    @XmlElement
    @HippoEssentialsGenerated(internalName = "acumoscms:categories")
    public String[] getCategories() {
        return getProperty(CATEGORIES);
    }

    @Override
    @HippoEssentialsGenerated(internalName = "acumoscms:authors")
    public List<Author> getAuthors() {
        return getLinkedBeans(AUTHORS, Author.class);
    }
}
