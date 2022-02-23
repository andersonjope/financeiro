package br.com.jope.financeiro;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractTest {

	protected MediaType contentType = MediaType.APPLICATION_JSON;
	protected static final String ENDPOINT_AUTH = "/auth";
	protected ObjectMapper mapper = new ObjectMapper();
	
}
