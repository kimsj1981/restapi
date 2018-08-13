package com.sjkim.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.exception.CardPointNotFoundException;
import com.sjkim.repository.CardPointDao;
import com.sjkim.vo.CardPointVo;

@RunWith(MockitoJUnitRunner.class)
public class CardPointServiceImplTest {
	@Mock
	private CardPointDao cardPointDao;

	@InjectMocks
	private CardPointServiceImpl cardPointService;

	// fixture
	GetCardPointDto getCardPointDto = new GetCardPointDto();
	PostCardPointDto postCardPointDto = new PostCardPointDto("9999", new BigDecimal("0.99"));
	PutCardPointDto putCardPointDto = new PutCardPointDto("9999", new BigDecimal("0"));

	@Test
	public void testAddCardPoint() {
		// when
		cardPointService.addCardPoint(postCardPointDto);
		// then
		verify(cardPointDao).insertCardPoint(postCardPointDto);
	}

	@Test
	public void testFindCardPointList() {
		// given
		CardPointVo cardPointVo = new CardPointVo("9999", new BigDecimal("0.99"));
		List<CardPointVo> expected = new ArrayList<>();
		expected.add(cardPointVo);
		when(cardPointDao.selectCardPointAll(getCardPointDto)).thenReturn(expected);
		// when
		List<CardPointVo> cardPointList = cardPointService.findCardPointList(getCardPointDto);
		// then
		assertEquals(expected, cardPointList);
	}

	@Test
	public void testFindCardPointByCardFraction() {
		// given
		String cardFraction = "9999";
		BigDecimal appPercent = new BigDecimal("0.99");
		when(cardPointDao.selectCardPointByCardFraction(cardFraction))
				.thenReturn(new CardPointVo(cardFraction, appPercent));
		// when
		CardPointVo cardPointVo = cardPointService.findCardPointByCardFraction(cardFraction);
		// then
		assertEquals(appPercent, cardPointVo.getAppPercent());
	}

	@Test
	public void testModifyCardPoint() {
		// given
		when(cardPointDao.updateCardPoint(putCardPointDto)).thenReturn(1);
		// when
		int updateCount = cardPointService.modifyCardPoint(putCardPointDto);
		// then
		assertEquals(1, updateCount);
	}

	@Test(expected = CardPointNotFoundException.class)
	public void testModifyCardPoint_CardPointNotFoundException() {
		// given
		when(cardPointDao.updateCardPoint(putCardPointDto)).thenReturn(0);
		// when
		cardPointService.modifyCardPoint(putCardPointDto);
	}

	@Test
	public void testRemoveCardPoint() {
		// given
		when(cardPointDao.deleteCardPoint("9999")).thenReturn(1);
		// when
		int deleteCount = cardPointService.removeCardPoint("9999");
		// then
		assertEquals(1, deleteCount);
	}

	@Test(expected = CardPointNotFoundException.class)
	public void testRemoveCardPoint_CardPointNotFoundException() {
		// given
		when(cardPointDao.deleteCardPoint("9999")).thenReturn(0);
		// when
		cardPointService.removeCardPoint("9999");
	}
}
