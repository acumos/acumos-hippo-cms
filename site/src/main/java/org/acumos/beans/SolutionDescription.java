/*-
 * ===============LICENSE_START=======================================================
 * Acumos
 * ===================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property & Tech Mahindra. All rights reserved.
 * ===================================================================================
 * This Acumos software file is distributed by AT&T and Tech Mahindra
 * under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ===============LICENSE_END=========================================================
 */
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
