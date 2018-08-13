package com.sjkim.advice;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.sjkim.vo.MessageVo;
import com.sjkim.vo.MessageVoBuilder;

@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return Boolean.TRUE;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (request.getURI().getPath().contains("swagger-resources")
				|| request.getURI().getPath().contains("v2/api-docs")) {
			return body;
		}

		if (body instanceof MessageVo) {
			response.setStatusCode(HttpStatus.OK);
			return body;
		}

		MessageVoBuilder messageVoBuilder = new MessageVoBuilder();
		if (body instanceof Integer) {
			messageVoBuilder.setRowCount(0).setContents(null);
		} else if (body instanceof Collection) {
			messageVoBuilder.setRowCount(((Collection<?>) body).size()).setContents(body);
		} else {
			messageVoBuilder.setRowCount(1).setContents(body);
		}
		response.setStatusCode(HttpStatus.OK);
		return messageVoBuilder.setHttpStatus(getResponseCode(request, body)).build();
	}

	private Integer getResponseCode(ServerHttpRequest request, Object body) {
		Integer responseCode = HttpServletResponse.SC_OK;
		if (request.getMethod() == HttpMethod.POST && ((Integer) body) > 0) {
			responseCode = HttpServletResponse.SC_CREATED;
		} else if (request.getMethod() == HttpMethod.DELETE && ((Integer) body) > 0) {
			responseCode = HttpServletResponse.SC_NO_CONTENT;
		}
		return responseCode;
	}
}