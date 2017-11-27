package org.acumos.common;

import java.io.Serializable;

import org.acumos.common.JSONTags;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonResponse<T> implements Serializable {

	private static final long serialVersionUID = -2934104266393591755L;

	/**
	 * Json property status.
	 */
	@JsonProperty(value = JSONTags.TAG_RESPONSE_STATUS)
	private Boolean status;

	/**
	 * Json property statusCode.
	 */
	@JsonProperty(value = JSONTags.TAG_RESPONSE_STATUS_CODE)
	private int statusCode;
	
	/**
	 * Json property responseDetail.
	 */
	@JsonProperty(value = JSONTags.TAG_RESPONSE_DETAIL)
	private String responseDetail;

	/**
	 * Json property responseCode.
	 */
	@JsonProperty(value = JSONTags.TAG_RESPONSE_CODE)
	private String responseCode;

	/**
	 * Json property responseBody. It represents the type of generic object.
	 */
	@JsonProperty(value = JSONTags.TAG_RESPONSE_BODY)
	private T responseBody;
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getResponseDetail() {
		return responseDetail;
	}

	public void setResponseDetail(String responseDetail) {
		this.responseDetail = responseDetail;
	}

	/**
	 * Json property errorCode.
	 */
	@JsonProperty(value = JSONTags.TAG_ERROR_CODE)
	private String errorCode;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
		
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public T getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
