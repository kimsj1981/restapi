package com.sjkim.vo;

import java.math.BigDecimal;

import com.sjkim.base.AbstractVo;

public class CardPointVo extends AbstractVo {

	private static final long serialVersionUID = -4375464431822448860L;

	private String cardFraction;

	private BigDecimal appPercent;

	public CardPointVo() {
	}

	public CardPointVo(String cardFraction, BigDecimal appPercent) {
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
