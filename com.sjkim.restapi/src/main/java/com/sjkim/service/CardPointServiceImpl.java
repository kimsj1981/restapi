package com.sjkim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjkim.annotation.Loggable;
import com.sjkim.common.LogLevel;
import com.sjkim.common.LogType;
import com.sjkim.dto.DeleteCardPointDto;
import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.exception.CardPointNotFoundException;
import com.sjkim.repository.CardPointDao;
import com.sjkim.vo.CardPointVo;

@Service("cardPointService")
public class CardPointServiceImpl implements CardPointService {

	@Autowired
	private CardPointDao cardPointDao;

	@Override
	@Transactional
	@Loggable(logLevel = LogLevel.ERROR, logType = LogType.SELLER)
	public Integer addCardPoint(PostCardPointDto postCardPointDto) {
		return cardPointDao.insertCardPoint(postCardPointDto);
	}

	@Override
	@Transactional(readOnly = true)
	@Loggable(logLevel = LogLevel.ERROR, logType = LogType.SELLER)
	public List<CardPointVo> findCardPointList(GetCardPointDto getCardPointDto) {
		return cardPointDao.selectCardPointAll(getCardPointDto);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "CardPointVo", key = "'findCardPointByCardFraction' + #cardFraction")
	@Loggable(logLevel = LogLevel.ERROR, logType = LogType.SELLER)
	public CardPointVo findCardPointByCardFraction(String cardFraction) {
		return cardPointDao.selectCardPointByCardFraction(cardFraction);
	}

	@Override
	@Transactional
	@Loggable(logLevel = LogLevel.ERROR, logType = LogType.SELLER)
	public Integer modifyCardPoint(PutCardPointDto putCardPointDto) {		
		int updateCount = cardPointDao.updateCardPoint(putCardPointDto);
		if (updateCount == 0) {
			throw new CardPointNotFoundException("일치하는 카드 포인트 적립율 정보를 찾을 수 없음");
		}
		return updateCount;
	}

	@Override
	@Transactional
	@CacheEvict(value = "CardPointVo", key = "'findCardPointByCardFraction' + #cardFraction")
	@Loggable(logLevel = LogLevel.ERROR, logType = LogType.SELLER)
	public Integer removeCardPoint(DeleteCardPointDto deleteCardPointDto) {
		int deleteCount = cardPointDao.deleteCardPoint(deleteCardPointDto);
		if (deleteCount == 0) {
			throw new CardPointNotFoundException("일치하는 카드 포인트 적립율 정보를 찾을 수 없음");
		}
		return deleteCount;
	}
}
