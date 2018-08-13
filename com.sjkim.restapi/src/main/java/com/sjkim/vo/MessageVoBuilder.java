package com.sjkim.vo;

public class MessageVoBuilder {

	private Integer httpStatus;

	private Integer rowCount;

	private Object contents;

	public MessageVoBuilder setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}

	public MessageVoBuilder setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
		return this;
	}

	public MessageVoBuilder setContents(Object contents) {
		this.contents = contents;
		return this;
	}

	public MessageVo build() {
		MessageVo messageVo = new MessageVo(httpStatus, rowCount, contents);
		return messageVo;
	}
}
