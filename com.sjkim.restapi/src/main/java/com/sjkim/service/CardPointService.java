package com.sjkim.service;

import java.util.List;

import com.sjkim.dto.DeleteCardPointDto;
import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.vo.CardPointVo;

public interface CardPointService {

	public Integer addCardPoint(PostCardPointDto postCardPointDto);

	public List<CardPointVo> findCardPointList(GetCardPointDto getCardPointDto);

	public CardPointVo findCardPointByCardFraction(String cardFraction);

	public Integer modifyCardPoint(PutCardPointDto putCardPointDto);

	public Integer removeCardPoint(DeleteCardPointDto deleteCardPointDto);
}
