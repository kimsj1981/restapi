package com.sjkim.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sjkim.RestapiApplication;
import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.vo.CardPointVo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RestapiApplication.class })
public class CardPointDaoTest {
	@Autowired
	CardPointDao cardPointDao;

	// fixture
	GetCardPointDto getCardPointDto = new GetCardPointDto();
	PostCardPointDto postCardPointDto = new PostCardPointDto("9999", new BigDecimal("0.99"));
	PutCardPointDto putCardPointDto = new PutCardPointDto("9999", new BigDecimal("0"));

	@Test
	@Transactional
	@Rollback(true)
	public void testInsertCardPoint() {
		// when
		cardPointDao.insertCardPoint(postCardPointDto);
		CardPointVo cardPointVo = cardPointDao.selectCardPointByCardFraction(postCardPointDto.getCardFraction());
		// then
		assertNotNull(cardPointVo);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSelectCardPointAll() {
		// given
		cardPointDao.insertCardPoint(postCardPointDto);
		// when
		List<CardPointVo> cardPointList = cardPointDao.selectCardPointAll(getCardPointDto);
		CardPointVo cardPointVo = new CardPointVo(postCardPointDto.getCardFraction(), postCardPointDto.getAppPercent());
		// then
		assertTrue(cardPointList.contains(cardPointVo));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSelectCardPointByCardFraction() {
		// given
		cardPointDao.insertCardPoint(postCardPointDto);
		// when
		CardPointVo cardPointVo = cardPointDao.selectCardPointByCardFraction(postCardPointDto.getCardFraction());
		// then
		assertEquals(postCardPointDto.getCardFraction(), cardPointVo.getCardFraction());
		assertEquals(postCardPointDto.getAppPercent(), cardPointVo.getAppPercent());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateCardPoint() {
		// given
		cardPointDao.insertCardPoint(postCardPointDto);
		// when
		cardPointDao.updateCardPoint(putCardPointDto);
		CardPointVo cardPointVo = cardPointDao.selectCardPointByCardFraction(putCardPointDto.getCardFraction());
		// then
		assertEquals(putCardPointDto.getAppPercent(), cardPointVo.getAppPercent());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCardPoint() {
		// given
		cardPointDao.insertCardPoint(postCardPointDto);
		// when
		cardPointDao.deleteCardPoint(postCardPointDto.getCardFraction());
		CardPointVo cardPointVo = cardPointDao.selectCardPointByCardFraction(postCardPointDto.getCardFraction());
		// then
		assertNull(cardPointVo);
	}
}
