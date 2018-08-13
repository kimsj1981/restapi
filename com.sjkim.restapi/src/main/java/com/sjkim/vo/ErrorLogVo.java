package com.sjkim.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sjkim.base.AbstractVo;

public class ErrorLogVo extends AbstractVo {

	private static final long serialVersionUID = -3671975051832638734L;

	private BigDecimal errSq;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
	private Date errDt;

	private String errCd;

	public BigDecimal getErrSq() {
		return errSq;
	}

	public void setErrSq(BigDecimal errSq) {
		this.errSq = errSq;
	}

	public Date getErrDt() {
		return errDt;
	}

	public void setErrDt(Date errDt) {
		this.errDt = errDt;
	}

	public String getErrCd() {
		return errCd;
	}

	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
}
