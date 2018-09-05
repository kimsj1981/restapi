package com.sjkim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
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

import com.sjkim.activemq.JmsSender;
import com.sjkim.config.PropertyMessage;
import com.sjkim.dto.DeleteCardPointDto;
import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.service.CardPointService;
import com.sjkim.serviceexecutor.ServiceJob;
import com.sjkim.vo.CardPointVo;

@RestController
@RequestMapping("api/cardpoints")
public class CardPointRestController {
	
	@Autowired
	PropertyMessage propertyMessage;

	@Autowired
	private CardPointService cardPointService;

	@Autowired
	private JmsSender jsmSender;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Integer postCardPoint(@RequestBody PostCardPointDto postCardPointDto) {
		if (propertyMessage.isQueueEnabled()) {
			ServiceJob serviceJob = new ServiceJob();
			serviceJob.setServiceName(ClassUtils.getUserClass(cardPointService).getName());
			serviceJob.setMethodName("addCardPoint");
			serviceJob.setDto(postCardPointDto);
			serviceJob.setInvokeBeanId("cardPointService");
			jsmSender.send(serviceJob);
			return Integer.valueOf(1);
		} else {
			return cardPointService.addCardPoint(postCardPointDto);
		}
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
		if (propertyMessage.isQueueEnabled()) {
			ServiceJob serviceJob = new ServiceJob();
			serviceJob.setServiceName(ClassUtils.getUserClass(cardPointService).getName());
			serviceJob.setMethodName("modifyCardPoint");
			serviceJob.setDto(putCardPointDto);
			serviceJob.setInvokeBeanId("cardPointService");
			jsmSender.send(serviceJob);
			return Integer.valueOf(1);
		} else {
			return cardPointService.modifyCardPoint(putCardPointDto);
		}
	}

	@DeleteMapping(value = "{cardFraction}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public Integer deleteCardPoint(@PathVariable String cardFraction) {
		DeleteCardPointDto deleteCardPointDto = new DeleteCardPointDto();
		deleteCardPointDto.setCardFraction(cardFraction);
		if (propertyMessage.isQueueEnabled()) {
			ServiceJob serviceJob = new ServiceJob();
			serviceJob.setServiceName(ClassUtils.getUserClass(cardPointService).getName());
			serviceJob.setMethodName("removeCardPoint");
			serviceJob.setDto(deleteCardPointDto);
			serviceJob.setInvokeBeanId("cardPointService");
			jsmSender.send(serviceJob);
			return Integer.valueOf(1);
		} else {
			return cardPointService.removeCardPoint(deleteCardPointDto);
		}
	}
}
