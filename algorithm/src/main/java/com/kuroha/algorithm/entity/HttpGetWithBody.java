package com.kuroha.algorithm.entity;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.io.Serializable;
import java.net.URI;

/**
 * @author kuroha
 * @date 2019-04-03 16:37:01
 */
public class HttpGetWithBody extends HttpEntityEnclosingRequestBase implements Serializable {

	public static final String METHOD_NAME = "GET";
	private static final long serialVersionUID = 4081513480555490414L;

	public HttpGetWithBody() {
	}

	public HttpGetWithBody(URI uri) {
		this.setURI(uri);
	}

	public HttpGetWithBody(String uri) {
		this.setURI(URI.create(uri));
	}

	@Override
	public String getMethod() {
		return "GET";
	}
}
