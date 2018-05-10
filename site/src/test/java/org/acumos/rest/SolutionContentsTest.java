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

package org.acumos.rest;

import javax.activation.DataHandler;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.acumos.beans.SolutionDescription;
import org.acumos.model.SolutionDescriptionContent;
import org.acumos.rest.SolutionContents;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.easymock.Mock;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.attachment.ByteDataSource;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.AttachmentBuilder;
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
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.onehippo.repository.mock.MockNode;
import org.hippoecm.hst.site.request.ResolvedMountImpl;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.Workflow;
import org.junit.Before;
import org.junit.Test;
import org.onehippo.cms7.services.hst.Channel;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Tests ProtoRecordGenerator
 */
public class SolutionContentsTest {

	MockHstRequestContext requestContext = null;

	private static class CustomWorkFlow implements WorkflowPersistenceManager {

		@Override
		public String createAndReturn(String absPath, String nodeTypeName, String name, boolean autoCreateFolders)
				throws ObjectBeanPersistenceException {
			return null;
		}

		@Override
		public void update(Object content) throws ObjectBeanPersistenceException {
		}

		@Override
		public void update(Object content, ContentNodeBinder customContentNodeBinder)
				throws ObjectBeanPersistenceException {
		}

		@Override
		public void remove(Object content) throws ObjectBeanPersistenceException {
		}

		@Override
		public void save() throws ObjectBeanPersistenceException {
		}

		@Override
		public void refresh() throws ObjectBeanPersistenceException {
		}

		@Override
		public void refresh(boolean keepChanges) throws ObjectBeanPersistenceException {
		}

		@Override
		public Object getObject(String path) throws ObjectBeanManagerException {
			return null;
		}

		@Override
		public Object getObjectByUuid(String uuid) throws ObjectBeanManagerException {
			return null;
		}

		@Override
		public Session getSession() {
			return null;
		}

		@Override
		public void setWorkflowCallbackHandler(
				QualifiedWorkflowCallbackHandler<? extends Workflow> workflowCallbackHandler) {

		}

		@Override
		public Workflow getWorkflow(String category, Node node) throws RepositoryException {
			return null;
		}

		@Override
		public Workflow getWorkflow(String category, Document document) throws RepositoryException {
			return null;
		}

	}

	@Mock
	public Session session;

	@Before
	public void before() throws Exception {
		requestContext = new MockHstRequestContext();
		requestContext.setSession(session);
	}

	private Mount mountMock = new Mount() {

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String getAlias() {
			return null;
		}

		@Override
		public boolean isMapped() {
			return false;
		}

		@Override
		public Mount getParent() {
			return null;
		}

		@Override
		public String getMountPoint() {
			return null;
		}

		@Override
		public boolean hasNoChannelInfo() {
			return false;
		}

		@Override
		public String getContentPath() {
			return null;
		}

		@Override
		public String getCanonicalContentPath() {
			return "default";
		}

		@Override
		public String getMountPath() {
			return null;
		}

		@Override
		public List<Mount> getChildMounts() {
			return null;
		}

		@Override
		public Mount getChildMount(String name) {
			return null;
		}

		@Override
		public VirtualHost getVirtualHost() {
			return null;
		}

		@Override
		public HstSite getHstSite() {
			return null;
		}

		@Override
		public boolean isContextPathInUrl() {
			return false;
		}

		@Override
		public boolean isPortInUrl() {
			return false;
		}

		@Override
		public boolean isSite() {
			return false;
		}

		@Override
		public int getPort() {
			return 0;
		}

		@Override
		public String onlyForContextPath() {
			return null;
		}

		@Override
		public String getContextPath() {
			return null;
		}

		@Override
		public String getHomePage() {
			return null;
		}

		@Override
		public String getPageNotFound() {
			return null;
		}

		@Override
		public String getScheme() {
			return null;
		}

		@Override
		public boolean isSchemeAgnostic() {
			return false;
		}

		@Override
		public boolean containsMultipleSchemes() {
			return false;
		}

		@Override
		public int getSchemeNotMatchingResponseCode() {
			return 0;
		}

		@Override
		public boolean isPreview() {
			return false;
		}

		@Override
		public boolean isOfType(String type) {
			return false;
		}

		@Override
		public String getType() {
			return null;
		}

		@Override
		public List<String> getTypes() {
			return null;
		}

		@Override
		public boolean isVersionInPreviewHeader() {
			return false;
		}

		@Override
		public String getNamedPipeline() {
			return null;
		}

		@Override
		public String getLocale() {
			return null;
		}

		@Override
		public HstSiteMapMatcher getHstSiteMapMatcher() {
			return null;
		}

		@Override
		public boolean isAuthenticated() {
			return false;
		}

		@Override
		public Set<String> getRoles() {
			return null;
		}

		@Override
		public Set<String> getUsers() {
			return null;
		}

		@Override
		public boolean isSubjectBasedSession() {
			return false;
		}

		@Override
		public boolean isSessionStateful() {
			return false;
		}

		@Override
		public String getFormLoginPage() {
			return null;
		}

		@Override
		public String getProperty(String name) {
			return null;
		}

		@Override
		public List<String> getPropertyNames() {
			return null;
		}

		@Override
		public Map<String, String> getMountProperties() {
			return null;
		}

		@Override
		public String getParameter(String name) {
			return null;
		}

		@Override
		public Map<String, String> getParameters() {
			return null;
		}

		@Override
		public String getIdentifier() {
			return null;
		}

		@Override
		public String getChannelPath() {
			return null;
		}

		@Override
		public <T extends ChannelInfo> T getChannelInfo() {
			return null;
		}

		@Override
		public Channel getChannel() {
			return null;
		}

		@Override
		public String[] getDefaultSiteMapItemHandlerIds() {
			return null;
		}

		@Override
		public boolean isCacheable() {
			return false;
		}

		@Override
		public String getDefaultResourceBundleId() {
			return null;
		}

		@Override
		public String[] getDefaultResourceBundleIds() {
			return null;
		}

		@Override
		public String getCmsLocation() {
			return null;
		}

		@Override
		public List<String> getCmsLocations() {
			return null;
		}

	};

	@Test
	public void testSolutionContents() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		WorkflowPersistenceManager wpm = new CustomWorkFlow();

		HstRequestContext requestContext = new MockHstRequestContext();
		String workspace = "public";
		SolutionDescriptionContent description = new SolutionDescriptionContent();
		description.setRevisionId("1234");
		description.setSoluctionId("8888");
		description.setDescription("Sample Solution");

		ResolvedMount resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);

		SolutionDescriptionContent descriptionBean = solutionContents.createSolDescription(workspace, description,
				requestContext, wpm);
		assert ("Sample Solution".equals(descriptionBean.getDescription()));
	}

	@Test
	public void testUpdateSolutionDescription() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		WorkflowPersistenceManager wpm = new CustomWorkFlow() {
			@Override
			public Object getObject(String path) throws ObjectBeanManagerException {
				SolutionDescription solutionDescription = new SolutionDescription();
				solutionDescription.setDescription("test");
				solutionDescription.setNode(null);
				return solutionDescription;
			}
		};

		HstRequestContext requestContext = new MockHstRequestContext();
		String workspace = "public";
		SolutionDescriptionContent description = new SolutionDescriptionContent();
		description.setRevisionId("1234");
		description.setSoluctionId("8888");
		description.setDescription("Sample Solution");

		ResolvedMount resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);

		SolutionDescriptionContent descriptionBean = solutionContents.createSolDescription(workspace, description,
				requestContext, wpm);
		assert ("Sample Solution".equals(descriptionBean.getDescription()));
	}

	@Test
	public void getTermsAndCodition() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		WorkflowPersistenceManager wpm = new CustomWorkFlow() {
			@Override
			public Object getObject(String path) throws ObjectBeanManagerException {
				SolutionDescription solutionDescription = new SolutionDescription();
				solutionDescription.setDescription("Terms And Condition Text");
				solutionDescription.setNode(null);
				return solutionDescription;
			}
		};

		HstRequestContext requestContext = new MockHstRequestContext();
		ResolvedMount resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);
		SolutionDescriptionContent descriptionBean = solutionContents.getSolTermsCondition(requestContext, wpm);
		assert ("Terms And Condition Text".equals(descriptionBean.getDescription()));
	}

	@Test
	public void testGetSolutionDescription() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		WorkflowPersistenceManager wpm = new CustomWorkFlow() {
			@Override
			public Object getObject(String path) throws ObjectBeanManagerException {
				SolutionDescription solutionDescription = new SolutionDescription();
				solutionDescription.setDescription("Sample Solution");
				solutionDescription.setNode(null);
				return solutionDescription;
			}
		};

		HstRequestContext requestContext = new MockHstRequestContext();
		String workspace = "public";
		SolutionDescriptionContent description = new SolutionDescriptionContent();
		description.setRevisionId("1234");
		description.setSoluctionId("8888");
		description.setDescription("Sample Solution");

		ResolvedMount resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);

		SolutionDescriptionContent descriptionBean = solutionContents.getSolDescription(requestContext, "8888", "1234",
				workspace, wpm);
		assert ("Sample Solution".equals(descriptionBean.getDescription()));

	}

	@Test
	public void testGetSolDescriptionContent() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		WorkflowPersistenceManager wpm = new CustomWorkFlow() {
			@Override
			public Object getObject(String path) throws ObjectBeanManagerException {
				SolutionDescription solutionDescription = new SolutionDescription();
				solutionDescription.setDescription("This will Contain the instruction for creating java model");
				solutionDescription.setNode(null);
				return solutionDescription;
			}
		};

		HstRequestContext requestContext = new MockHstRequestContext();
		SolutionDescriptionContent description = new SolutionDescriptionContent();
		description.setRevisionId("1234");
		description.setSoluctionId("8888");
		description.setDescription("This will Contain the instruction for creating java model");

		ResolvedMount resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);

		SolutionDescriptionContent descriptionBean = solutionContents.getSolDescriptionContent(requestContext,
				"/global/modeler", "java", wpm);
		assert ("This will Contain the instruction for creating java model".equals(descriptionBean.getDescription()));

	}

	@Test
	public void testGetSolModelerResourceContent() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		WorkflowPersistenceManager wpm = new CustomWorkFlow() {
			@Override
			public Object getObject(String path) throws ObjectBeanManagerException {
				SolutionDescription solutionDescription = new SolutionDescription();
				solutionDescription.setDescription("This will Contain the instruction for creating java model");
				solutionDescription.setNode(null);
				return solutionDescription;
			}
		};

		HstRequestContext requestContext = new MockHstRequestContext();
		SolutionDescriptionContent description = new SolutionDescriptionContent();
		description.setRevisionId("1234");
		description.setSoluctionId("8888");
		description.setDescription("This will Contain the instruction for creating java model");

		ResolvedMount resolvedMountMock = new ResolvedMountImpl(mountMock, null, null, null, 0);
		((MockHstRequestContext) requestContext).setResolvedMount(resolvedMountMock);

		SolutionDescriptionContent descriptionBean = solutionContents.getSolModelerResourceContent(requestContext,
				"java", wpm);
		assert ("This will Contain the instruction for creating java model".equals(descriptionBean.getDescription()));
	}

	@Test
	public void testSaveImage() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		ClassLoader classLoader = getClass().getClassLoader();
		File f = new File(classLoader.getResource("my_company.png").getFile());
		InputStream inputStream = new FileInputStream(f);

		Attachment att = new AttachmentBuilder().object(inputStream)
				.contentDisposition(new ContentDisposition("form-data; name=\"file\"; filename=\"my_company.png\""))
				.mediaType("image/png")
				.dataHandler(new DataHandler(new ByteDataSource(IOUtils.toByteArray(inputStream)), "multipart/mixed"))
				.build();
		MockNode rootNode = MockNode.root();

		String imageNmae = solutionContents.saveimage(att, "12345", rootNode);
		assert ("my_company.png".equals(imageNmae));
	}

	@Test
	public void testSaveAssets() throws Exception {

		SolutionContents solutionContents = new SolutionContents();
		ClassLoader classLoader = getClass().getClassLoader();
		File f = new File(classLoader.getResource("my_company.png").getFile());
		InputStream inputStream = new FileInputStream(f);

		Attachment att = new AttachmentBuilder()
				.contentDisposition(new ContentDisposition("form-data; name=\"file\"; filename=\"my_company.png\""))
				.mediaType("image/png")
				.dataHandler(new DataHandler(new ByteDataSource(IOUtils.toByteArray(inputStream)), "multipart/mixed"))
				.build();
		MetadataMap<String, String> map = new MetadataMap<String, String>();
		map.add("Content-Disposition", "attachment;filename=image.jpg");
		MockNode rootNode = MockNode.root();

		String assetName = solutionContents.saveAssets("1234", "56987", "content", att, rootNode);
		assert ("my_company.png".equals(assetName));
	}

}
