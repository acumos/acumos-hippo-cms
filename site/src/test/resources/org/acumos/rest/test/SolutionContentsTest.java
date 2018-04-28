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

package org.acumos.rest.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.ws.rs.core.MultivaluedMap;

import org.acumos.beans.SolutionDescription;
import org.acumos.model.SolutionDescriptionContent;
import org.acumos.rest.SolutionContents;
import org.apache.cxf.attachment.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.InputStreamDataSource;
import org.hippoecm.hst.configuration.channel.ChannelInfo;
import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.configuration.hosting.VirtualHost;
import org.hippoecm.hst.configuration.site.HstSite;
import org.hippoecm.hst.content.beans.ContentNodeBinder;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.ObjectBeanPersistenceException;
import org.hippoecm.hst.content.beans.manager.workflow.QualifiedWorkflowCallbackHandler;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.HstSiteMapMatcher;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.mock.core.component.MockHstResponse;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.hippoecm.hst.site.request.ResolvedMountImpl;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.Workflow;
import org.junit.Before;
import org.junit.Test;
import org.onehippo.cms7.services.hst.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests ProtoRecordGenerator
 */
public class SolutionContentsTest {

	private static Logger logger = LoggerFactory.getLogger(SolutionContentsTest.class);

	MockHstRequestContext requestContext = null;

	MockHstResponse response = null;

	MockHstRequest request = null;

	ResolvedMount resolvedMountMock = null;

	SolutionContents solutionContents = null;

	@Before
	public void before() throws Exception {
		requestContext = new MockHstRequestContext();
		solutionContents = new SolutionContents();
		request = new MockHstRequest();
		response = new MockHstResponse();
		resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);
		((MockHstRequestContext) requestContext).setSession(requestContext.getSession());
	}

	private WorkflowPersistenceManager wpm = new WorkflowPersistenceManager() {

		@Override
		public String createAndReturn(String absPath, String nodeTypeName, String name, boolean autoCreateFolders)
				throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void update(Object content) throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public void update(Object content, ContentNodeBinder customContentNodeBinder)
				throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public void remove(Object content) throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public void save() throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public void refresh() throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public void refresh(boolean keepChanges) throws ObjectBeanPersistenceException {
			// TODO Auto-generated method stub

		}

		@Override
		public Object getObject(String path) throws ObjectBeanManagerException {
			// TODO Auto-generated method stub
			SolutionDescription solutionDescription = new SolutionDescription();
			solutionDescription.setDescription("test");
			solutionDescription.setNode(null);
			return solutionDescription;
		}

		@Override
		public Object getObjectByUuid(String uuid) throws ObjectBeanManagerException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Session getSession() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setWorkflowCallbackHandler(
				QualifiedWorkflowCallbackHandler<? extends Workflow> workflowCallbackHandler) {
			// TODO Auto-generated method stub

		}

		@Override
		public Workflow getWorkflow(String category, Node node) throws RepositoryException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Workflow getWorkflow(String category, Document document) throws RepositoryException {
			// TODO Auto-generated method stub
			return null;
		}

	};

	private Mount mountMock = new Mount() {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getAlias() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isMapped() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Mount getParent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getMountPoint() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasNoChannelInfo() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getContentPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCanonicalContentPath() {
			// TODO Auto-generated method stub
			return "default";
		}

		@Override
		public String getMountPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Mount> getChildMounts() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Mount getChildMount(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public VirtualHost getVirtualHost() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HstSite getHstSite() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isContextPathInUrl() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isPortInUrl() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSite() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getPort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String onlyForContextPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContextPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getHomePage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPageNotFound() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getScheme() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSchemeAgnostic() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean containsMultipleSchemes() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getSchemeNotMatchingResponseCode() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isPreview() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isOfType(String type) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getTypes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isVersionInPreviewHeader() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getNamedPipeline() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLocale() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HstSiteMapMatcher getHstSiteMapMatcher() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAuthenticated() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Set<String> getRoles() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<String> getUsers() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSubjectBasedSession() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSessionStateful() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getFormLoginPage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProperty(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getPropertyNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, String> getMountProperties() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getParameter(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, String> getParameters() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getIdentifier() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getChannelPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends ChannelInfo> T getChannelInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Channel getChannel() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getDefaultSiteMapItemHandlerIds() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isCacheable() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getDefaultResourceBundleId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getDefaultResourceBundleIds() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCmsLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getCmsLocations() {
			// TODO Auto-generated method stub
			return null;
		}

	};

	@Test
	public void testSolutionContents() throws Exception {
		try {
			String workspace = "public";
			SolutionDescriptionContent description = new SolutionDescriptionContent();
			description.setRevisionId("1234");
			description.setSoluctionId("8888");
			description.setDescription("Sample Solution");
			solutionContents.createSolDescription(workspace, description, requestContext, wpm);
			solutionContents.createSolDescription(workspace, description, requestContext, null);
		} catch (Exception ex) {
			logger.error("testProtoRecordGenerator failed: ", ex);
			throw ex;
		}
		//
		// Method md = null;
		// md.setAccessible(true);
		// md.invoke(solutionContents, null,null,requestContext,null);
	}

	@Test
	public void testCreateSolDescription() throws Exception {
		try {
			String workspace = "public";
			solutionContents.createSolDescription(requestContext, "1234", "8888", workspace);
		} catch (Exception ex) {
			logger.error("testCreateSolDescription failed: ", ex);
			throw ex;
		}
	}

	@Test
	public void testGetSolTermsCondition() throws Exception {
		try {
			solutionContents.getSolTermsCondition(requestContext);
		} catch (Exception ex) {
			logger.error("testCreateSolDescription failed: ", ex);
			throw ex;
		}

	}

	@Test
	public void testGetSolModelerResourceContent() throws Exception {
		try {
			solutionContents.getSolModelerResourceContent(requestContext, "myModelName");
		} catch (Exception ex) {
			logger.error("testGetSolModelerResourceContent failed: ", ex);
			throw ex;
		}
	}

	@Test
	public void testGetSolDescriptionContent() throws Exception {
		try {
			solutionContents.getSolDescriptionContent(requestContext, "contentPath", "contentName");
		} catch (Exception ex) {
			logger.error("testGetSolDescriptionContent failed: ", ex);
			throw ex;
		}
	}

	@Test
	public void testGetSolutionImageName() throws Exception {
		try {
			solutionContents.getSolutionImageName(response, requestContext, "");
			solutionContents.getSolutionImageName(response, requestContext, "solutionId123");
		} catch (Exception ex) {
			logger.error("testGetSolDescriptionContent failed: ", ex);
			throw ex;
		}
	}

	@Test
	public void testSaveAssets() throws Exception {
		try {
			Attachment attachment = new Attachment("id123", "inputStream", new Object());
			solutionContents.saveAssets(response, requestContext, "solutionId", "revisionId", "path", attachment);
		} catch (Exception ex) {
			logger.error("testGetSolDescriptionContent failed: ", ex);
			throw ex;
		}
	}
}
