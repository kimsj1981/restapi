package com.sjkim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.service.CardPointService;
import com.sjkim.vo.CardPointVo;

@RestController
@RequestMapping("api/cardpoints")
public class CardPointRestController {

	@Autowired
	private CardPointService cardPointService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Integer postCardPoint(@RequestBody PostCardPointDto postCardPointDto) {
		return cardPointService.addCardPoint(postCardPointDto);
	}

	@GetMapping
	public List<CardPointVo> getCardPoints(@RequestParam(required = false, defaultValue = "0") Integer startRowNum,
			@RequestParam(required = false, defaultValue = "10") Integer endRowNum) {
		GetCardPointDto getCardPointDto = new GetCardPointDto();
		getCardPointDto.setStartRowNum(startRowNum);
		getCardPointDto.setEndRowNum(endRowNum);
		return cardPointService.findCardPointList(getCardPointDto);
	}

	@GetMapping(value = "{cardFraction}")
	public CardPointVo getCardPointByCardFraction(@PathVariable String cardFraction) {
		return cardPointService.findCardPointByCardFraction(cardFraction);
	}

	@PutMapping(value = "{cardFraction}")
	public Integer putCardPoint(@PathVariable String cardFraction, @RequestBody PutCardPointDto putCardPointDto) {
		putCardPointDto.setCardFraction(cardFraction);
		return cardPointService.modifyCardPoint(putCardPointDto);
	}

	@DeleteMapping(value = "{cardFraction}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public Integer deleteCardPoint(@PathVariable String cardFraction) {
		return cardPointService.removeCardPoint(cardFraction);
	}
}
