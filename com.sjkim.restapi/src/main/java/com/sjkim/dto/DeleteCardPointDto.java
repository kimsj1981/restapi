package com.sjkim.dto;

import com.sjkim.base.AbstractDto;

public class DeleteCardPointDto extends AbstractDto {

	private static final long serialVersionUID = 7284842841222212808L;

	private String cardFraction;

	public DeleteCardPointDto() {
	}

	public DeleteCardPointDto(String cardFraction) {
		this.cardFraction = cardFraction;
	}

	public String getCardFraction() {
		return cardFraction;
	}

	public void setCardFraction(String cardFraction) {
		this.cardFraction = cardFraction;
	}
}
