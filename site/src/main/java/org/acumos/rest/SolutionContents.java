package org.acumos.rest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.jcr.Binary;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.acumos.common.JSONTags;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.hippoecm.hst.content.annotations.Persistable;
import org.hippoecm.hst.content.beans.ObjectBeanPersistenceException;
import org.hippoecm.hst.content.beans.manager.workflow.WorkflowPersistenceManager;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.repository.api.HippoNode;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.acumos.beans.*;
import org.acumos.common.JsonResponse;
import org.acumos.model.*;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
@Path("/Solution/")
public class SolutionContents extends BaseRestResource {
	
	
	private static final Logger log = LoggerFactory.getLogger(SolutionContents.class);
	
	public static final String publicWorkspacePath = "public";
	public static final String orgWorkspacePath = "org";
	
	
    @Persistable
    @POST
    @Path("/description/{workspace}")
    public SolutionDescriptionContent createSolDescription(@Context HttpServletRequest request, @Context HttpServletResponse response, @Context UriInfo uriInfo, @PathParam("workspace") String workspace, SolutionDescriptionContent description) {
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	
    	if(description.getSoluctionId() == null || description.getSoluctionId().isEmpty()) {
    		//return Response.status(Response.Status.BAD_REQUEST).build();
    		log.info("Solution Id Not Present. Cannot createthe description contnent");
    	}
    	
    	if(description.getRevisionId() == null || description.getRevisionId().isEmpty()) {
    		//return Response.status(Response.Status.BAD_REQUEST).build();
    		log.info("Revision Id Not Present. Cannot createthe description contnent");
    	}
    	
    	
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
        String commentsFolderPath = siteCanonicalBasePath + "/solution/";
        String descNodeName = description.getSoluctionId();
        
        if(StringUtils.isNotEmpty(workspace) && workspace.equals("public")) {
        	commentsFolderPath = commentsFolderPath + "solution-description/" + description.getSoluctionId() + "/" + description.getRevisionId() + "/" + publicWorkspacePath + "/";
    	} else {
    		commentsFolderPath = commentsFolderPath + "solution-description/" + description.getSoluctionId() + "/" + description.getRevisionId() + "/" + orgWorkspacePath + "/";
    	}
        
        log.info("Path : " + commentsFolderPath);
        
        SolutionDescription descriptionBean = null;
    	WorkflowPersistenceManager wpm = null;
    	try {
    		wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
    		log.debug("Get Description Content with Solition Id : " + description.getSoluctionId() + " And Revision Id : " + description.getRevisionId());
    		
    		descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + descNodeName + "/" + descNodeName);
    		
    		/*
    		 * No need to publish the Item if we can retrieve it without publishing 
    		 * wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                public void processWorkflow(DocumentWorkflow wf) throws Exception {
                    wf.publish();
                }
            });*/
    		
			if(descriptionBean == null) {
				// create solution description node
				log.debug("Create Description Content with Solition Id : " + description.getSoluctionId() + " And Revision Id : " + description.getRevisionId());
	            wpm.createAndReturn(commentsFolderPath, "acumoscms:solutionDescription", descNodeName, true);
	            descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + descNodeName + "/" + descNodeName);
			} else {
				log.debug("Found Description Bean : " + descriptionBean.getCanonicalPath());
			}

            // retrieve the comment content to manipulate
            descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + descNodeName + "/" + descNodeName);
            // update content properties
            if (descriptionBean == null) {
            	throw new HstComponentException("Solution Description Node does not exist with name " + descNodeName);
            }

            descriptionBean.setDescription(description.getDescription());

            // update now
            wpm.update(descriptionBean);
            wpm.save();

        } catch (Exception e) {

            if (wpm != null) {
                try {
                    wpm.refresh();
                } catch (ObjectBeanPersistenceException e1) {
                    //log.warn("Failed to refresh: ", e);
                }
            }
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    	return description;
    }



    @Persistable
    @GET
    @Path("/description/{workspace}/{solutionId}/{revisionId}")
    public SolutionDescriptionContent getSolDescription(@Context HttpServletRequest request, @Context HttpServletResponse response, @Context UriInfo uriInfo, @PathParam("solutionId") String solutionId, @PathParam("revisionId") String revisionId, @PathParam("workspace") String workspace) {
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	SolutionDescriptionContent description = new SolutionDescriptionContent();
    	
    	if(solutionId == null || solutionId.isEmpty()) {
    		return description;
    	}
    	
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
        String commentsFolderPath = siteCanonicalBasePath + "/solution";
        
        if(StringUtils.isNotEmpty(workspace) && workspace.equals("public")) {
        	commentsFolderPath = commentsFolderPath + "/" + "solution-description/" + solutionId + "/" + revisionId + "/" + publicWorkspacePath + "/";
    	} else {
    		commentsFolderPath = commentsFolderPath + "/" + "solution-description/" + solutionId + "/" + revisionId + "/" + orgWorkspacePath + "/";
    	}

        SolutionDescription descriptionBean = null;
    	WorkflowPersistenceManager wpm = null;
    	try {
    		wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
    		
    		descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + solutionId + "/" + solutionId);
    		log.info("Path : " + commentsFolderPath);
    		description.setDescription(descriptionBean.getDescription());
    		description.setSoluctionId(solutionId);
    		description.setRevisionId(revisionId);
    		/*
    		 * No need to publish the Item if we can retrieve it without publishing 
    		 * wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                public void processWorkflow(DocumentWorkflow wf) throws Exception {
                    wf.publish();
                }
            });*/


        } catch (Exception e) {

            if (wpm != null) {
                try {
                    wpm.refresh();
                } catch (ObjectBeanPersistenceException e1) {
                }
            }
        }
    	return description;
    }


    @Persistable
    @GET
    @Path("/global/termsCondition")
    public SolutionDescriptionContent getSolTermsCondition(@Context HttpServletRequest request, @Context HttpServletResponse response) {
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	SolutionDescriptionContent description = new SolutionDescriptionContent();
    	
    	
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
        String commentsFolderPath = siteCanonicalBasePath + "/global/" + "terms-condition/";

        SolutionDescription descriptionBean = null;
    	WorkflowPersistenceManager wpm = null;
    	try {
    		wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
    		
    		descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + "termsandcondition" + "/" + "termsandcondition");
    		
    		description.setDescription(descriptionBean.getDescription());
    		description.setSoluctionId("TermsAndCondition");
    		/*
    		 * No need to publish the Item if we can retrieve it without publishing 
    		 * wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                public void processWorkflow(DocumentWorkflow wf) throws Exception {
                    wf.publish();
                }
            });*/


        } catch (Exception e) {

            if (wpm != null) {
                try {
                    wpm.refresh();
                } catch (ObjectBeanPersistenceException e1) {
                }
            }
        }
    	return description;
    }
    
    
    @Persistable
    @GET
    @Path("/global/modelerresource/{modelName}")
    public SolutionDescriptionContent getSolModelerResourceContent(@Context HttpServletRequest request, @Context HttpServletResponse response, @PathParam("modelName") String modelName) {
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	SolutionDescriptionContent description = new SolutionDescriptionContent();
    	
    	
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
        String commentsFolderPath = siteCanonicalBasePath + "/global/" + "modeler-resources/";

        SolutionDescription descriptionBean = null;
    	WorkflowPersistenceManager wpm = null;
    	try {
    		wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
    		
    		descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + modelName + "/" + modelName);
    		
    		description.setDescription(descriptionBean.getDescription());
    		description.setSoluctionId(modelName);
    		/*
    		 * No need to publish the Item if we can retrieve it without publishing 
    		 * wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                public void processWorkflow(DocumentWorkflow wf) throws Exception {
                    wf.publish();
                }
            });*/


        } catch (Exception e) {

            if (wpm != null) {
                try {
                    wpm.refresh();
                } catch (ObjectBeanPersistenceException e1) {
                }
            }
        }
    	return description;
    }
    
    @Persistable
    @GET
    @Path("/solDescription")
    public SolutionDescriptionContent getSolDescriptionContent(@Context HttpServletRequest request, @Context HttpServletResponse response, @QueryParam("path") String contentPath, @QueryParam("name") String contentName) {
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	SolutionDescriptionContent description = new SolutionDescriptionContent();
    	
    	if(StringUtils.isEmpty(contentPath) || StringUtils.isEmpty(contentName))
    		return description;
    	
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
        String commentsFolderPath = siteCanonicalBasePath + "/" + contentPath + "/";

        SolutionDescription descriptionBean = null;
    	WorkflowPersistenceManager wpm = null;
    	try {
    		wpm = (WorkflowPersistenceManager) getPersistenceManager(requestContext);
    		
    		descriptionBean = (SolutionDescription) wpm.getObject(commentsFolderPath + contentName + "/" + contentName);
    		
    		description.setDescription(descriptionBean.getDescription());
    		description.setSoluctionId(contentName);
    		/*
    		 * No need to publish the Item if we can retrieve it without publishing 
    		 * wpm.setWorkflowCallbackHandler(new BaseWorkflowCallbackHandler<DocumentWorkflow>() {
                public void processWorkflow(DocumentWorkflow wf) throws Exception {
                    wf.publish();
                }
            });*/


        } catch (Exception e) {

            if (wpm != null) {
                try {
                    wpm.refresh();
                } catch (ObjectBeanPersistenceException e1) {
                }
            }
        }
    	return description;
    }

    private static final List<String> VALID_IMAGE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    
    @Persistable
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.WILDCARD})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solutionImages/{solutionId}")
    public JsonResponse<String> saveImage(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("solutionId") String solutionId, @Multipart("file") Attachment attachment
            ) throws PathNotFoundException, LoginException, RepositoryException, IOException {

    	log.debug("-- saveImage() --");
    	if(StringUtils.isEmpty(solutionId)){
    		String output = "Cannot Upload image";
            //return Response.status(500).entity(output).build();
    	}
    	
    	
    	//String name = image.getOriginalFilename();
    	String name = attachment.getContentDisposition().getParameter("filename");
    	log.debug("-- saveImage() --" + name);
    	
    	String imgExt = name.substring(name.lastIndexOf('.') + 1);
        if (!VALID_IMAGE_EXTENSIONS.contains(imgExt)) {
        	String output = "Image File Extension not Valid";
            //return Response.status(500).entity(output).build();
        }

    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
    	Node rootImagePath = requestContext.getSession().getRootNode().getNode("content/gallery/acumoscms");
    	log.debug("-- saveImage() --" + rootImagePath.getName());
    	
    	Node solutionImages;
        if (!rootImagePath.hasNode("solution")) {
        	solutionImages = rootImagePath.addNode("solution", "hippogallery:stdImageGallery");
        	solutionImages.addMixin("mix:referenceable");
            String[] foldertype = {"new-image-folder"};
            solutionImages.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:imageset"};
            solutionImages.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	solutionImages = rootImagePath.getNode("solution");
        }
        
        //Create Folder with solution Id
        Node solutionIdFolder;
        if (!solutionImages.hasNode(solutionId)) {
        	solutionIdFolder = solutionImages.addNode(solutionId, "hippogallery:stdImageGallery");
        	solutionIdFolder.addMixin("mix:referenceable");
            String[] foldertype = {"new-image-folder"};
            solutionIdFolder.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:imageset"};
            solutionIdFolder.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	solutionIdFolder = solutionImages.getNode(solutionId);
        }

        NodeIterator it = solutionIdFolder.getNodes();
        while(it.hasNext()){
        	Node handlenode = it.nextNode();
        	log.debug("-- saveImage() : Removing Node  --" + handlenode.getName());
        	handlenode.remove();
        }

        //Create thumbnail image
        Node imgHandle;
        if (solutionIdFolder.hasNode(name)) {
            imgHandle = solutionIdFolder.getNode(name);
        } else {
            imgHandle = solutionIdFolder.addNode(name, "hippo:handle");
            imgHandle.addMixin("mix:referenceable");
        }

        if (!imgHandle.hasNode(name)) {
            Node imgDoc = imgHandle.addNode(name, "hippogallery:imageset");
            imgDoc.addMixin("mix:referenceable");
            String[] availability = {"live", "preview"};
            imgDoc.setProperty("hippo:availability", availability);
            imgDoc.setProperty("hippogallery:filename", name);

            //Thumbnail node might already exist
            Node imgThumb;
            if (imgDoc.hasNode("hippogallery:thumbnail")) {
                imgThumb = imgDoc.getNode("hippogallery:thumbnail");
            } else {
                imgThumb = imgDoc.addNode("hippogallery:thumbnail", "hippogallery:image");
            }

            imgThumb.setProperty("jcr:lastModified", Calendar.getInstance());
            imgThumb.setProperty("jcr:mimeType", "image/" + imgExt);
            imgThumb.setProperty("hippogallery:height", 50L);
            imgThumb.setProperty("hippogallery:width", 300L);

            Node imgOrig = imgDoc.addNode("hippogallery:original", "hippogallery:image");
            imgOrig.setProperty("jcr:lastModified", Calendar.getInstance());
            imgOrig.setProperty("jcr:mimeType", "image/" + imgExt);
            imgOrig.setProperty("hippogallery:height", 768L);
            imgOrig.setProperty("hippogallery:width", 1024L);

            /*BufferedImage imageFile = new BufferedImage(300, 50, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imageFile.createGraphics();
            g2d.setPaint(Color.blue);
            g2d.setFont(new Font("Serif", Font.BOLD, 32));
            g2d.drawString(name, 5, 35);
            g2d.dispose();*/

            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while ((n = attachment.getObject(InputStream.class).read(buf)) >= 0)
                baos.write(buf, 0, n);
            byte[] content = baos.toByteArray();
            
            
            InputStream is = new ByteArrayInputStream(content);
            imgThumb.setProperty("jcr:data", imgThumb.getSession().getValueFactory().createBinary(is));
            is = new ByteArrayInputStream(content);
            imgOrig.setProperty("jcr:data", imgThumb.getSession().getValueFactory().createBinary(is));

        }
        //String imageUUID = imgHandle.getIdentifier();

        requestContext.getSession().save();

        String output = "Image File uploaded successfully";
        log.debug("-- saveImage() : Image File uploaded successfully");
        //return Response.status(200).entity(output).build();
        JsonResponse<String> jsonResponse = new JsonResponse<String>();
        jsonResponse.setResponseBody(name);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Image File uploaded successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }


    @Persistable
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solutionImages/{solutionId}")
    public JsonResponse<List<String>> getSolutionImageName(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("solutionId") String solutionId
    		) throws PathNotFoundException, LoginException, RepositoryException, IOException {
    	
    	List<String> solImageNameList = new ArrayList<String>();

    	if(StringUtils.isEmpty(solutionId)){
    		JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
    		jsonResponse.setResponseBody(solImageNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_FAILURE);
            jsonResponse.setResponseDetail("Illegal parameter. Solution Id Missing");
            response.setStatus(500);
            return jsonResponse;
    	}
    	//String name = image.getOriginalFilename();

    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
    	Node rootImagePath = requestContext.getSession().getRootNode().getNode("content/gallery/acumoscms");
    	log.debug("-- getSolutionImageName() : Path : " + rootImagePath.getName());

    	Node solutionImages;
        if (!rootImagePath.hasNode("solution")) {
        	JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
    		jsonResponse.setResponseBody(solImageNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
            jsonResponse.setResponseDetail("No Image Found");
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonResponse;
        } else {
        	solutionImages = rootImagePath.getNode("solution");
        }
        
        //Create Folder with solution Id
        Node solutionIdFolder;
        if (!solutionImages.hasNode(solutionId)) {
        	JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
    		jsonResponse.setResponseBody(solImageNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
            jsonResponse.setResponseDetail("No Image Found");
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonResponse;
        } else {
        	solutionIdFolder = solutionImages.getNode(solutionId);
        }
        //Create thumbnail image
        Node imgHandle;
        NodeIterator it = solutionIdFolder.getNodes();
        
        
        while(it.hasNext()){
        	Node handlenode = it.nextNode();
        	log.debug("-- getSolutionImageName() : Found Image Node Path : " + handlenode.getName());
        	String nodeName = handlenode.getName();
        	String imgExt = nodeName.substring(nodeName.lastIndexOf('.') + 1);
        	if(VALID_IMAGE_EXTENSIONS.contains(imgExt) && "hippo:handle".equals(handlenode.getPrimaryNodeType().getName()))
        		solImageNameList.add(handlenode.getName());
        	
        }
        
        if(solImageNameList.size() == 0){
        	String output = "No Images";
        	log.debug("-- getSolutionImageName() : No Image Found  ");
        	//return Response.status(404).entity(output).build();
        }
        
        JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
        jsonResponse.setResponseBody(solImageNameList);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Solutions Image fetched Successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }


    @Persistable
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.WILDCARD})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solutionAssets/{solutionId}/{revisionId}")
    public JsonResponse<String> saveAssets(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("solutionId") String solutionId, @PathParam("revisionId") String revisionId, @QueryParam("path") String path, @Multipart("file") Attachment attachment
            ) throws PathNotFoundException, LoginException, RepositoryException, IOException {
    	
    	log.debug("-- saveAssets() {}  ");
    	if(StringUtils.isEmpty(solutionId)){
    		String output = "Cannot Upload Documents";
            //return Response.status(500).entity(output).build();
    	}
    	
    	if(StringUtils.isEmpty(revisionId)){
    		String output = "Cannot Upload Documents";
            //return Response.status(500).entity(output).build();
    	}
    	
    	log.debug("-- saveAssets() {}  Upload path : " + path);
    	if(StringUtils.isEmpty(path) && !(orgWorkspacePath.equals(path) || publicWorkspacePath.equals(path))) {
    		String output = "Upload path not valid";
            //return Response.status(500).entity(output).build();
    	}
    	
    	//String name = image.getOriginalFilename();
    	String name = attachment.getContentDisposition().getParameter("filename");
    	log.debug("-- saveAssets() : Name : " + name);
    	
    	InputStream ip = attachment.getObject(InputStream.class);
    	log.debug(" File Size (in Bytes)  : "+ip.available());

    	
    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();

    	Node rootAssetPath = requestContext.getSession().getRootNode().getNode("content/assets");
    	log.debug("-- saveAssets() : Base Path : " + rootAssetPath.getName());


    	Node solutiondocs;
        if (!rootAssetPath.hasNode("solutiondocs")) {
        	solutiondocs = rootAssetPath.addNode("solutiondocs", "hippogallery:stdAssetGallery");
        	solutiondocs.addMixin("mix:versionable");
            String[] foldertype = {"new-file-folder"};
            solutiondocs.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:exampleAssetSet"};
            solutiondocs.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	solutiondocs = rootAssetPath.getNode("solutiondocs");
        }
        
        Node solution;
        if (!solutiondocs.hasNode("solution")) {
        	solution = solutiondocs.addNode("solution", "hippogallery:stdAssetGallery");
        	solution.addMixin("mix:versionable");
            String[] foldertype = {"new-file-folder"};
            solution.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:exampleAssetSet"};
            solution.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	solution = solutiondocs.getNode("solution");
        }

        //Create Folder with solution Id
        Node solutionIdFolder;
        if (!solution.hasNode(solutionId)) {
        	solutionIdFolder = solution.addNode(solutionId, "hippogallery:stdAssetGallery");
        	solutionIdFolder.addMixin("mix:versionable");
            String[] foldertype = {"new-file-folder"};
            solutionIdFolder.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:exampleAssetSet"};
            solutionIdFolder.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	solutionIdFolder = solution.getNode(solutionId);
        }

        //Create Folder with revision Id
        Node revisionIdFolder;
        if (!solutionIdFolder.hasNode(revisionId)) {
        	revisionIdFolder = solutionIdFolder.addNode(revisionId, "hippogallery:stdAssetGallery");
        	revisionIdFolder.addMixin("mix:versionable");
            String[] foldertype = {"new-file-folder"};
            revisionIdFolder.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:exampleAssetSet"};
            revisionIdFolder.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	revisionIdFolder = solutionIdFolder.getNode(revisionId);
        }
        
        Node pathNode;
        if (!revisionIdFolder.hasNode(path)) {
        	pathNode = revisionIdFolder.addNode(path, "hippogallery:stdAssetGallery");
        	pathNode.addMixin("mix:versionable");
            String[] foldertype = {"new-file-folder"};
            pathNode.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:exampleAssetSet"};
            pathNode.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	pathNode = revisionIdFolder.getNode(path);
        }

       //Count the total path attached to the node. Do not allow more that 7 files per solutionId 
        NodeIterator it = pathNode.getNodes();
        int count = 0;
        while(it.hasNext()){
            Node handlenode = it.nextNode();
            log.debug("-- saveAssets() : Overwrite : " + handlenode.getName());
            count++;
        }
        
        if (count >= 7) {
        	String output = "Only 7 Documents are allowed to be uploaded";
            //return Response.status(500).entity(output).build();
        }


        Node assetHandle;
        if (pathNode.hasNode(name)) {
        	assetHandle = pathNode.getNode(name);
        } else {
        	assetHandle = pathNode.addNode(name, "hippo:handle");
        	assetHandle.addMixin("mix:referenceable");
        }


        if (!assetHandle.hasNode(name)) {
            Node assetDoc = assetHandle.addNode(name, "hippogallery:exampleAssetSet");
            assetDoc.addMixin("mix:referenceable");
            String[] availability = {"live", "preview"};
            assetDoc.setProperty("hippo:availability", availability);
            //assetDoc.setProperty("hippogallery:asset", name);

            //Thumbnail node might already exist
            Node galleryAsset;
            if (assetDoc.hasNode("hippogallery:asset")) {
            	galleryAsset = assetDoc.getNode("hippogallery:asset");
            } else {
            	galleryAsset = assetDoc.addNode("hippogallery:asset", "hippo:resource");
            }

            galleryAsset.setProperty("jcr:lastModified", Calendar.getInstance());
            //galleryAsset.setProperty("jcr:mimeType", "image/" + imgExt);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while ((n = attachment.getObject(InputStream.class).read(buf)) >= 0)
                baos.write(buf, 0, n);
            byte[] content = baos.toByteArray();
            
            
            InputStream mimeis = new BufferedInputStream(attachment.getObject(InputStream.class));
            String mimeType = URLConnection.guessContentTypeFromStream(mimeis);

            galleryAsset.setProperty("jcr:mimeType", "application/" + mimeType);
            InputStream is = new ByteArrayInputStream(content);
            galleryAsset.setProperty("jcr:data", galleryAsset.getSession().getValueFactory().createBinary(is));

        }
        //String imageUUID = imgHandle.getIdentifier();

        requestContext.getSession().save();

        String output = "Image File uploaded successfully";
        log.debug("-- saveAssets() : Saved : " + name);
        JsonResponse<String> jsonResponse = new JsonResponse<String>();
        jsonResponse.setResponseBody(name);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Solutions fetched Successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }
    
    
    @Persistable
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solutionAssets/{solutionId}/{revisionId}")
    public JsonResponse<List<String>> getSolutionDocumentName(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("solutionId") String solutionId, @PathParam("revisionId") String revisionId, @QueryParam("path") String path
    		) throws PathNotFoundException, LoginException, RepositoryException, IOException {
    	
    	log.debug("-- getSolutionDocumentName() {} ");
    	if(StringUtils.isEmpty(solutionId)){
    		String output = "Cannot Find solution documents";
            //return Response.status(500).entity(output).build();
    	}
    	
    	log.debug("-- saveAssets() {}  Upload path : " + path);
    	if(StringUtils.isEmpty(path) && !(orgWorkspacePath.equals(path) || publicWorkspacePath.equals(path))) {
    		String output = "Upload path not valid";
            //return Response.status(500).entity(output).build();
    	}
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
    	
    	Node rootImagePath = requestContext.getSession().getRootNode().getNode("content/assets");
    	log.debug("-- getSolutionDocumentName() {} Base Path : " + rootImagePath.getName());
    	
    	Node solutionDocs = null;
        if (!rootImagePath.hasNode("solutiondocs")) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	solutionDocs = rootImagePath.getNode("solutiondocs");
        }
        
        Node solution = null;
        if (!solutionDocs.hasNode("solution")) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	solution = solutionDocs.getNode("solution");
        }
        
        
        //Create Folder with solution Id
        Node solutionIdFolder = null;
        if (!solution.hasNode(solutionId)) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	solutionIdFolder = solution.getNode(solutionId);
        }
        
        Node revisionIdFolder = null;
        if (!solutionIdFolder.hasNode(revisionId)) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	revisionIdFolder = solutionIdFolder.getNode(revisionId);
        }
        
        Node pathNode = null;
        if (!revisionIdFolder.hasNode(path)) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	pathNode = revisionIdFolder.getNode(path);
        }
        //Create thumbnail image
        NodeIterator it = pathNode.getNodes();
        List<String> solDocumentNameList = new ArrayList<String>();
        
        while(it.hasNext()){
        	Node handlenode = it.nextNode();
        	if("hippo:handle".equals(handlenode.getPrimaryNodeType().getName()))
        		log.debug("-- getSolutionDocumentName() {} Document Found with Name :" + handlenode.getName());
        		solDocumentNameList.add(handlenode.getName());
        	
        }
        
        if(solDocumentNameList.size() == 0){
        	String output = "No Documents Available";
        	//return Response.status(404).entity(output).build();
        }
        
        JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
        jsonResponse.setResponseBody(solDocumentNameList);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Solutions fetched Successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }
    
    
    
    @Persistable
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solutionAssets/{solutionId}/{revisionId}/{assetName}")
    public JsonResponse<List<String>> remoceSolutionDocument(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("solutionId") String solutionId, @PathParam("revisionId") String revisionId, @PathParam("assetName") String assetName, @QueryParam("path") String path
    		) throws PathNotFoundException, LoginException, RepositoryException, IOException {
    	
    	log.debug("-- remoceSolutionDocument() {} ");
    	if(StringUtils.isEmpty(solutionId)){
    		String output = "Cannot Find solution documents";
            //return Response.status(500).entity(output).build();
    	}
    	
    	log.debug("-- saveAssets() {}  Upload path : " + path);
    	if(StringUtils.isEmpty(path) && !(orgWorkspacePath.equals(path) || publicWorkspacePath.equals(path))) {
    		String output = "Upload path not valid";
            //return Response.status(500).entity(output).build();
    	}
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
    	
    	Node rootImagePath = requestContext.getSession().getRootNode().getNode("content/assets");
    	log.debug("-- getSolutionDocumentName() {} Base Path : " + rootImagePath.getName());
    	
    	Node solutionDocs = null;
        if (!rootImagePath.hasNode("solutiondocs")) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	solutionDocs = rootImagePath.getNode("solutiondocs");
        }
        
        Node solution = null;
        if (!solutionDocs.hasNode("solution")) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	solution = solutionDocs.getNode("solution");
        }
        
        Node solutionIdFolder = null;
        if (!solution.hasNode(solutionId)) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	solutionIdFolder = solution.getNode(solutionId);
        }
        
        Node revisionIdFolder = null;
        if (!solutionIdFolder.hasNode(revisionId)) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	revisionIdFolder = solutionIdFolder.getNode(revisionId);
        }
        
        Node pathNode = null;
        if (!revisionIdFolder.hasNode(path)) {
        	String output = "No Documents Available";
            //return Response.status(404).entity(output).build();
        } else {
        	pathNode = revisionIdFolder.getNode(path);
        }


        NodeIterator it = pathNode.getNodes();
        while(it.hasNext()){
        	Node handlenode = it.nextNode();
        	if("hippo:handle".equals(handlenode.getPrimaryNodeType().getName()))
        		log.debug("-- remoceSolutionDocument() {} Document Found with Name :" + handlenode.getName());
        		if (handlenode.getName() != null && handlenode.getName().equals(assetName)) {
        			log.debug("-- remoceSolutionDocument() {} Document deleted with Name :" + handlenode.getName());
        			handlenode.remove();
        			break;
        		}
        	
        }
        requestContext.getSession().save();
        
        
        JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Document Removed Successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }
    
    @Persistable
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/solutionAssets/cp/{solutionId}/{revisionId}/{fromRevisionId}")
    public JsonResponse<List<String>> copySolutionDocuments(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("solutionId") String solutionId, @PathParam("revisionId") String revisionId, @PathParam("fromRevisionId") String fromRevisionId, @QueryParam("path") String path
    		) throws PathNotFoundException, LoginException, RepositoryException, IOException {
    	
    	log.debug("-- getSolutionDocumentName() {} ");
    	if(StringUtils.isEmpty(solutionId)){
    		String output = "Cannot Find solution documents";
            //return Response.status(500).entity(output).build();
    	}
    	
    	log.debug("-- saveAssets() {}  Upload path : " + path);
    	if(StringUtils.isEmpty(path) && !(orgWorkspacePath.equals(path) || publicWorkspacePath.equals(path))) {
    		String output = "Upload path not valid";
            //return Response.status(500).entity(output).build();
    	}
    	
    	HstRequestContext requestContext = getRequestContext(request);
    	Session session = requestContext.getSession();
    	List<String> solDocumentNameList = new ArrayList<String>();
 
    	Node sourcePathNode = getPathNodeForAsset(session, solutionId, fromRevisionId, path);
    	Node destinationPathnode = getOrCreatePathNodeForAsset(session, solutionId, revisionId, path);

    	if(sourcePathNode == null) {
    		JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
            jsonResponse.setResponseBody(solDocumentNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
            jsonResponse.setResponseDetail("Solutions fetched Successfully");
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonResponse;
    	}
    	
    	if(destinationPathnode == null) {
    		JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
            jsonResponse.setResponseBody(solDocumentNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
            jsonResponse.setResponseDetail("Solutions fetched Successfully");
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonResponse;
    	}
    	
        Node sourceAssteHandle = null;
        //Get the next source node and pass it to create in destination. Remaining all nodes will be created in copy nodes 
        for (NodeIterator ni = sourcePathNode.getNodes(); ni.hasNext(); ) {
	        sourceAssteHandle = ni.nextNode();
	        break;
	    }
        Node pathNode = null;
        if(sourcePathNode != null) {
        	//copyNodes(sourceAssteHandle, destinationPathnode, sourceAssteHandle.getName());
        	pathNode = copyNodes(sourcePathNode, destinationPathnode, sourcePathNode.getName());
        }
        
        if(pathNode != null) {
	        NodeIterator it = pathNode.getNodes();
	        
	        while(it.hasNext()){
	        	Node handlenode = it.nextNode();
	        	if("hippo:handle".equals(handlenode.getPrimaryNodeType().getName()))
	        		log.debug("-- getSolutionDocumentName() {} Document Found with Name :" + handlenode.getName());
	        		solDocumentNameList.add(handlenode.getName());
	        	
	        }
        }
        
        session.save();
        
        JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
        jsonResponse.setResponseBody(solDocumentNameList);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Solutions fetched Successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }
    
    
    private Node getPathNodeForAsset(Session session, String solutionId, String revisionId, String path) {
    	
    	Node pathNode = null;
    	
    	if(StringUtils.isEmpty(solutionId) || StringUtils.isEmpty(revisionId)){
    		return pathNode;
    	}
    	
    	Node rootAssetNode;
    	
		try {
			rootAssetNode = session.getRootNode().getNode("content/assets");
			
			Node solutionDocs = null;
	        if (!rootAssetNode.hasNode("solutiondocs")) {
	        	return pathNode;
	        } else {
	        	solutionDocs = rootAssetNode.getNode("solutiondocs");
	        }
	        
	        Node solution = null;
	        if (!solutionDocs.hasNode("solution")) {
	        	return pathNode;
	        } else {
	        	solution = solutionDocs.getNode("solution");
	        }
	        
	        Node solutionIdFolder = null;
	        if (!solution.hasNode(solutionId)) {
	        	return pathNode;
	        } else {
	        	solutionIdFolder = solution.getNode(solutionId);
	        }
	        
	        Node revisionIdFolder = null;
	        if (!solutionIdFolder.hasNode(revisionId)) {
	        	return pathNode;
	        } else {
	        	revisionIdFolder = solutionIdFolder.getNode(revisionId);
	        }
	        
	        if (!revisionIdFolder.hasNode(path)) {
	        	return pathNode;
	        } else {
	        	pathNode = revisionIdFolder.getNode(path);
	        }
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pathNode;
		}

    	return pathNode;
    }
    
    
    
    @Persistable
    @POST
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.WILDCARD})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/globalImages/{path}")
    public JsonResponse<String> saveGlobalImage(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("path") String path, @Multipart("file") Attachment attachment
            ) throws PathNotFoundException, LoginException, RepositoryException, IOException {

    	log.debug("-- saveImage() --");
    	if(StringUtils.isEmpty(path)){
    		String output = "Cannot Upload image";
            //return Response.status(500).entity(output).build();
    	}
    	
    	
    	//String name = image.getOriginalFilename();
    	String name = attachment.getContentDisposition().getParameter("filename");
    	log.debug("-- saveImage() --" + name);
    	
    	String imgExt = name.substring(name.lastIndexOf('.') + 1);
        if (!VALID_IMAGE_EXTENSIONS.contains(imgExt)) {
        	String output = "Image File Extension not Valid";
            //return Response.status(500).entity(output).build();
        }

    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
    	Node rootImagePath = requestContext.getSession().getRootNode().getNode("content/gallery/acumoscms");
    	log.debug("-- saveImage() --" + rootImagePath.getName());
    	
    	Node globalImages;
        if (!rootImagePath.hasNode("global")) {
        	globalImages = rootImagePath.addNode("global", "hippogallery:stdImageGallery");
        	globalImages.addMixin("mix:referenceable");
            String[] foldertype = {"new-image-folder"};
            globalImages.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:imageset"};
            globalImages.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	globalImages = rootImagePath.getNode("global");
        }
        
        //Create Folder with solution Id
        Node pathFolder;
        if (!globalImages.hasNode(path)) {
        	pathFolder = globalImages.addNode(path, "hippogallery:stdImageGallery");
        	pathFolder.addMixin("mix:referenceable");
            String[] foldertype = {"new-image-folder"};
            pathFolder.setProperty("hippostd:foldertype", foldertype);
            String[] gallerytype = {"hippogallery:imageset"};
            pathFolder.setProperty("hippostd:gallerytype", gallerytype);
        } else {
        	pathFolder = globalImages.getNode(path);
        }

        NodeIterator it = pathFolder.getNodes();
        while(it.hasNext()){
        	Node handlenode = it.nextNode();
        	log.debug("-- saveImage() : Removing Node  --" + handlenode.getName());
        	handlenode.remove();
        }

        //Create thumbnail image
        Node imgHandle;
        if (pathFolder.hasNode(name)) {
            imgHandle = pathFolder.getNode(name);
        } else {
            imgHandle = pathFolder.addNode(name, "hippo:handle");
            imgHandle.addMixin("mix:referenceable");
        }

        if (!imgHandle.hasNode(name)) {
            Node imgDoc = imgHandle.addNode(name, "hippogallery:imageset");
            imgDoc.addMixin("mix:referenceable");
            String[] availability = {"live", "preview"};
            imgDoc.setProperty("hippo:availability", availability);
            imgDoc.setProperty("hippogallery:filename", name);

            //Thumbnail node might already exist
            Node imgThumb;
            if (imgDoc.hasNode("hippogallery:thumbnail")) {
                imgThumb = imgDoc.getNode("hippogallery:thumbnail");
            } else {
                imgThumb = imgDoc.addNode("hippogallery:thumbnail", "hippogallery:image");
            }

            imgThumb.setProperty("jcr:lastModified", Calendar.getInstance());
            imgThumb.setProperty("jcr:mimeType", "image/" + imgExt);
            imgThumb.setProperty("hippogallery:height", 50L);
            imgThumb.setProperty("hippogallery:width", 300L);

            Node imgOrig = imgDoc.addNode("hippogallery:original", "hippogallery:image");
            imgOrig.setProperty("jcr:lastModified", Calendar.getInstance());
            imgOrig.setProperty("jcr:mimeType", "image/" + imgExt);
            imgOrig.setProperty("hippogallery:height", 768L);
            imgOrig.setProperty("hippogallery:width", 1024L);

            /*BufferedImage imageFile = new BufferedImage(300, 50, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imageFile.createGraphics();
            g2d.setPaint(Color.blue);
            g2d.setFont(new Font("Serif", Font.BOLD, 32));
            g2d.drawString(name, 5, 35);
            g2d.dispose();*/

            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while ((n = attachment.getObject(InputStream.class).read(buf)) >= 0)
                baos.write(buf, 0, n);
            byte[] content = baos.toByteArray();
            
            
            InputStream is = new ByteArrayInputStream(content);
            imgThumb.setProperty("jcr:data", imgThumb.getSession().getValueFactory().createBinary(is));
            is = new ByteArrayInputStream(content);
            imgOrig.setProperty("jcr:data", imgThumb.getSession().getValueFactory().createBinary(is));

        }
        //String imageUUID = imgHandle.getIdentifier();

        requestContext.getSession().save();

        String output = "Image File uploaded successfully";
        log.debug("-- saveImage() : Image File uploaded successfully");
        //return Response.status(200).entity(output).build();
        JsonResponse<String> jsonResponse = new JsonResponse<String>();
        jsonResponse.setResponseBody(name);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Image File uploaded successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }
    
    @Persistable
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/global/{path}")
    public JsonResponse<List<String>> getLogoImageName(@Context HttpServletRequest request, @Context HttpServletResponse response, 
    		@PathParam("path") String path
    		) throws PathNotFoundException, LoginException, RepositoryException, IOException {
    	
    	List<String> solImageNameList = new ArrayList<String>();

    	if(StringUtils.isEmpty(path)){
    		JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
    		jsonResponse.setResponseBody(solImageNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_FAILURE);
            jsonResponse.setResponseDetail("Illegal parameter. Solution Id Missing");
            response.setStatus(500);
            return jsonResponse;
    	}
    	//String name = image.getOriginalFilename();

    	HstRequestContext requestContext = getRequestContext(request);
    	String siteCanonicalBasePath = requestContext.getResolvedMount().getMount().getCanonicalContentPath();
    	Node rootImagePath = requestContext.getSession().getRootNode().getNode("content/gallery/acumoscms");
    	log.debug("-- getLogoImageName() : Path : " + rootImagePath.getName());

    	Node logoImages;
        if (!rootImagePath.hasNode("global")) {
        	JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
    		jsonResponse.setResponseBody(solImageNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
            jsonResponse.setResponseDetail("No Image Found");
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonResponse;
        } else {
        	logoImages = rootImagePath.getNode("global");
        }
        
        //Create Folder with solution Id
        Node pathFolder;
        if (!logoImages.hasNode(path)) {
        	JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
    		jsonResponse.setResponseBody(solImageNameList);
            jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
            jsonResponse.setResponseDetail("No Image Found");
            response.setStatus(HttpServletResponse.SC_OK);
            return jsonResponse;
        } else {
        	pathFolder = logoImages.getNode(path);
        }
        //Create thumbnail image
        Node imgHandle;
        NodeIterator it = pathFolder.getNodes();
        
        
        while(it.hasNext()){
        	Node handlenode = it.nextNode();
        	log.debug("-- getSolutionImageName() : Found Image Node Path : " + handlenode.getName());
        	String nodeName = handlenode.getName();
        	String imgExt = nodeName.substring(nodeName.lastIndexOf('.') + 1);
        	if(VALID_IMAGE_EXTENSIONS.contains(imgExt) && "hippo:handle".equals(handlenode.getPrimaryNodeType().getName()))
        		solImageNameList.add(handlenode.getName());
        	
        }
        
        if(solImageNameList.size() == 0){
        	String output = "No Images";
        	log.debug("-- getSolutionImageName() : No Image Found  ");
        	//return Response.status(404).entity(output).build();
        }
        
        JsonResponse<List<String>> jsonResponse = new JsonResponse<List<String>>();
        jsonResponse.setResponseBody(solImageNameList);
        jsonResponse.setErrorCode(JSONTags.TAG_ERROR_CODE_SUCCESS);
        jsonResponse.setResponseDetail("Solutions Image fetched Successfully");
        response.setStatus(HttpServletResponse.SC_OK);

        return jsonResponse;
    }
    
private Node getOrCreatePathNodeForAsset(Session session, String solutionId, String revisionId, String path) {
    	
    	Node pathNode = null;
    	
    	if(StringUtils.isEmpty(solutionId) || StringUtils.isEmpty(revisionId)){
    		return pathNode;
    	}
    	
    	try {
	    	Node rootAssetPath = session.getRootNode().getNode("content/assets");
	    	Node solutiondocs;
	        if (!rootAssetPath.hasNode("solutiondocs")) {
	        	solutiondocs = rootAssetPath.addNode("solutiondocs", "hippogallery:stdAssetGallery");
	        	solutiondocs.addMixin("mix:versionable");
	            String[] foldertype = {"new-file-folder"};
	            solutiondocs.setProperty("hippostd:foldertype", foldertype);
	            String[] gallerytype = {"hippogallery:exampleAssetSet"};
	            solutiondocs.setProperty("hippostd:gallerytype", gallerytype);
	        } else {
	        	solutiondocs = rootAssetPath.getNode("solutiondocs");
	        }
	        
	        Node solution;
	        if (!solutiondocs.hasNode("solution")) {
	        	solution = solutiondocs.addNode("solution", "hippogallery:stdAssetGallery");
	        	solution.addMixin("mix:versionable");
	            String[] foldertype = {"new-file-folder"};
	            solution.setProperty("hippostd:foldertype", foldertype);
	            String[] gallerytype = {"hippogallery:exampleAssetSet"};
	            solution.setProperty("hippostd:gallerytype", gallerytype);
	        } else {
	        	solution = solutiondocs.getNode("solution");
	        }
	
	        //Create Folder with solution Id
	        Node solutionIdFolder;
	        if (!solution.hasNode(solutionId)) {
	        	solutionIdFolder = solution.addNode(solutionId, "hippogallery:stdAssetGallery");
	        	solutionIdFolder.addMixin("mix:versionable");
	            String[] foldertype = {"new-file-folder"};
	            solutionIdFolder.setProperty("hippostd:foldertype", foldertype);
	            String[] gallerytype = {"hippogallery:exampleAssetSet"};
	            solutionIdFolder.setProperty("hippostd:gallerytype", gallerytype);
	        } else {
	        	solutionIdFolder = solution.getNode(solutionId);
	        }
	
	        //Create Folder with revision Id
	        Node revisionIdFolder;
	        if (!solutionIdFolder.hasNode(revisionId)) {
	        	revisionIdFolder = solutionIdFolder.addNode(revisionId, "hippogallery:stdAssetGallery");
	        	revisionIdFolder.addMixin("mix:versionable");
	            String[] foldertype = {"new-file-folder"};
	            revisionIdFolder.setProperty("hippostd:foldertype", foldertype);
	            String[] gallerytype = {"hippogallery:exampleAssetSet"};
	            revisionIdFolder.setProperty("hippostd:gallerytype", gallerytype);
	        } else {
	        	revisionIdFolder = solutionIdFolder.getNode(revisionId);
	        }
	        
	        pathNode = revisionIdFolder;
	        /*if (!revisionIdFolder.hasNode(path)) {
	        	pathNode = revisionIdFolder.addNode(path, "hippogallery:stdAssetGallery");
	        	pathNode.addMixin("mix:versionable");
	            String[] foldertype = {"new-file-folder"};
	            pathNode.setProperty("hippostd:foldertype", foldertype);
	            String[] gallerytype = {"hippogallery:exampleAssetSet"};
	            pathNode.setProperty("hippostd:gallerytype", gallerytype);
	        } else {
	        	pathNode = revisionIdFolder.getNode(path);
	        }*/
	        
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return pathNode;
		}

    	return pathNode;
    }


	private Node copyNodes(Node source, Node parent, String name) throws RepositoryException {
		Node clone ;
		if(parent.hasNode(name))
			clone = parent.getNode(name);
		else
			clone = parent.addNode(name, source.getPrimaryNodeType().getName());

	    for (NodeType mixin : source.getMixinNodeTypes()) {
	        clone.addMixin(mixin.getName());
	    }
	    for (PropertyIterator pi = source.getProperties(); pi.hasNext(); ) {
	        Property prop = pi.nextProperty();
	        if (prop.getDefinition().isProtected()) {
	            continue;
	        }
	        if (prop.isMultiple()) {
	            clone.setProperty(prop.getName(), prop.getValues());
	        } else {
	            clone.setProperty(prop.getName(), prop.getValue());
	        }
	    }
	    for (NodeIterator ni = source.getNodes(); ni.hasNext(); ) {
	        Node node = ni.nextNode();
	        if (isVirtual(node)) {
	            continue;
	        }
	
	        copyNodes(node, clone, node.getName());
	    }
	    return clone;
	}
	
    private static boolean isVirtual(final Node node) throws RepositoryException {
        // skip virtual nodes
        if (node instanceof HippoNode) {
            HippoNode hn = (HippoNode) node;
            try {
                Node canonicalNode = hn.getCanonicalNode();
                if (canonicalNode == null) {
                    return true;
                }
                if (!canonicalNode.isSame(hn)) {
                    return true;
                }
            } catch (ItemNotFoundException infe) {
                return true;
            }
        }
        return false;
    }
}