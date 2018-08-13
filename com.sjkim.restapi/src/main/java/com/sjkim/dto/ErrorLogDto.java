package com.sjkim.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.sjkim.base.AbstractDto;

public class ErrorLogDto extends AbstractDto {

	private static final long serialVersionUID = 7506020571751936044L;

	private BigDecimal errSq;

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
