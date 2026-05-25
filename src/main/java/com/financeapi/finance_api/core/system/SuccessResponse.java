package com.financeapi.finance_api.core.system;

import lombok.Getter;

@Getter
public class SuccessResponse<T> {
	private final T content;
	private final String status;
	private final String message;
	private final String nextActionUrl;

	//[Custom]Custom Response no content
	public  SuccessResponse(String status, String message, String nextActiontUrl) {
		this.content = null;
		this.status = status;
		this.message = message;
		this.nextActionUrl = nextActiontUrl;
	}

	//[Custom]Custom Response
	public  SuccessResponse(T content, String status, String message, String nextActiontUrl) {
		this.content = content;
		this.status = status;
		this.message = message;
		this.nextActionUrl = nextActiontUrl;
	}

	//[Generic]Generic Response no content
	public SuccessResponse(SuccessDetails details) {
		this.content = null;
		this.status = details.getStatus();
		this.message = details.getMessage();
		this.nextActionUrl = details.getNextActionUrl();
	}

	//[Generic]Generic Response
	public SuccessResponse(T content, SuccessDetails details) {
		this.content = content;
		this.status = details.getStatus();
		this.message = details.getMessage();
		this.nextActionUrl = details.getNextActionUrl();
	}

	//[Generic Response]
	public SuccessResponse<T> withCustomMessage(String message) {
		return new SuccessResponse<T>(this.content, this.status, message, this.nextActionUrl);
	}

	//[Generic Response]
	public SuccessResponse<T> extendMessage(String message) {
		String newMessage = String.format("%s %s", this.getMessage(), message);
		return new SuccessResponse<T>(this.content, this.status, newMessage, this.nextActionUrl);
	}

	//[Generic Response]
	public SuccessResponse<T> withCustomStatus(String status) {
		return new SuccessResponse<T>(this.content, status, this.message, this.nextActionUrl);
	}

	//[Generic Response]
	public SuccessResponse<T> withCustomUrl(String url) {
		return new SuccessResponse<T>(this.content, this.status, this.message, url);
	}

	//[Generic Response]
	public SuccessResponse<T> extendUrl(String string) {
		String newUrl = String.format("%s/%s", this.getNextActionUrl(), string);
		return new SuccessResponse<T>(this.content, this.status, this.message, newUrl);
	}
}
