package com.sjkim.vo;

import java.math.BigDecimal;

import com.sjkim.base.AbstractVo;

public class ErrorLogVo extends AbstractVo {

	private static final long serialVersionUID = 7929767757896854814L;

	private BigDecimal errSq;

	private String errDt;

	private String errCd;

	public BigDecimal getErrSq() {
		return errSq;
	}

	public void setErrSq(BigDecimal errSq) {
		this.errSq = errSq;
	}

	public String getErrDt() {
		return errDt;
	}

	public void setErrDt(String errDt) {
		this.errDt = errDt;
	}
	
	public String getErrCd() {
		return errCd;
	}

	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
}
