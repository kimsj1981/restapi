package com.sjkim.dto;

import java.math.BigDecimal;

import com.sjkim.base.AbstractDto;

import io.swagger.annotations.ApiModelProperty;

public class PutCardPointDto extends AbstractDto {

	private static final long serialVersionUID = 1497705415290355968L;

	@ApiModelProperty(hidden = true)
	private String cardFraction;

	private BigDecimal appPercent;

	public PutCardPointDto() {
	}

	public PutCardPointDto(String cardFraction, BigDecimal appPercent) {
		super();
		this.cardFraction = cardFraction;
		this.appPercent = appPercent;
	}

	public String getCardFraction() {
		return cardFraction;
	}

	public void setCardFraction(String cardFraction) {
		this.cardFraction = cardFraction;
	}

	public BigDecimal getAppPercent() {
		return appPercent;
	}

	public void setAppPercent(BigDecimal appPercent) {
		this.appPercent = appPercent;
	}
}
