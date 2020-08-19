package com.kuroha.algorithm.entity;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.io.Serializable;
import java.net.URI;

/**
 * @author kuroha
 * @date 2019-04-03 16:36:47
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase implements Serializable {
	public static final String METHOD_NAME = "DELETE";
	private static final long serialVersionUID = -2772038013487840425L;

	public HttpDeleteWithBody() {
	}

	public HttpDeleteWithBody(URI uri) {
		this.setURI(uri);
	}

	public HttpDeleteWithBody(String uri) {
		this.setURI(URI.create(uri));
	}

	@Override
	public String getMethod() {
		return "DELETE";
	}
}
