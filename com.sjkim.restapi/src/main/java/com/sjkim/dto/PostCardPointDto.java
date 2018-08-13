package com.sjkim.dto;

import java.math.BigDecimal;

import com.sjkim.base.AbstractDto;

public class PostCardPointDto extends AbstractDto {

	private static final long serialVersionUID = -5587521111255672601L;

	private String cardFraction;

	private BigDecimal appPercent;

	public PostCardPointDto() {
	}

	public PostCardPointDto(String cardFraction, BigDecimal appPercent) {
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
