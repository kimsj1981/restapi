package com.sjkim.vo;

import com.sjkim.base.AbstractVo;

public class MessageVo extends AbstractVo {

	private static final long serialVersionUID = -5977983490583897385L;

	private Integer httpStatus;

	private Integer rowCount;

	private Object contents;

	public MessageVo() {
	}

	public MessageVo(Integer httpStatus, Integer rowCount, Object contents) {
		this.httpStatus = httpStatus;
		this.rowCount = rowCount;
		this.contents = contents;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Object getContents() {
		return contents;
	}

	public void setContents(Object contents) {
		this.contents = contents;
	}
}
