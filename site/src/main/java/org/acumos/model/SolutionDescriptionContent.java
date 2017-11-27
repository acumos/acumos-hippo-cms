package org.acumos.model;


import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;


public class SolutionDescriptionContent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty(value = "description")
	private String description;
	
	
	@JsonProperty(value = "solutionId")
	private String soluctionId;
	
	@JsonProperty(value = "revisionId")
	private String revisionId;
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the soluctionId
	 */
	public String getSoluctionId() {
		return soluctionId;
	}

	/**
	 * @param soluctionId the soluctionId to set
	 */
	public void setSoluctionId(String soluctionId) {
		this.soluctionId = soluctionId;
	}

	/**
	 * @return the revisionId
	 */
	public String getRevisionId() {
		return revisionId;
	}

	/**
	 * @param revisionId the revisionId to set
	 */
	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}
}
