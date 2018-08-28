package com.sjkim.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.sjkim.dto.DeleteCardPointDto;
import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.service.CardPointService;
import com.sjkim.vo.CardPointVo;

@RunWith(MockitoJUnitRunner.class)
public class CardPointRestControllerTest {
	@Mock
	private CardPointService cardPointService;

	@InjectMocks
	private CardPointRestController cardPointRestController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(cardPointRestController).build();
	}

	@Test
	public void testPostCardPoint() throws Exception {
		// given
		PostCardPointDto postCardPointDto = new PostCardPointDto("9999", new BigDecimal("0.99"));
		Gson gson = new Gson();
		String json = gson.toJson(postCardPointDto);
		// when
		ResultActions result = mockMvc
				.perform(post("/api/cardpoints").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
				.andDo(print());
		// then
		result.andExpect(status().isCreated());
		verify(cardPointService).addCardPoint(postCardPointDto);
	}

	@Test
	public void testGetCardPoints() throws Exception {
		// given
		GetCardPointDto getCardPointDto = new GetCardPointDto();
		getCardPointDto.setStartRowNum(0);
		getCardPointDto.setEndRowNum(10);
		CardPointVo cardPointVo = new CardPointVo("9999", new BigDecimal("0.99"));
		List<CardPointVo> cardPointList = new ArrayList<>();
		cardPointList.add(cardPointVo);
		when(cardPointService.findCardPointList(getCardPointDto)).thenReturn(cardPointList);
		// when
		ResultActions result = mockMvc.perform(get("/api/cardpoints")).andDo(print());
		// then
		verify(cardPointService).findCardPointList(getCardPointDto);
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].cardFraction").value("9999"));
		result.andExpect(jsonPath("$[0].appPercent").value("0.99"));
	}

	@Test
	public void testGetCardPointByCardFraction() throws Exception {
		// given
		when(cardPointService.findCardPointByCardFraction("9999"))
				.thenReturn(new CardPointVo("9999", new BigDecimal("0.99")));
		// when
		ResultActions result = mockMvc.perform(get("/api/cardpoints/{cardFraction}", "9999")).andDo(print());
		// then
		verify(cardPointService).findCardPointByCardFraction("9999");
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.cardFraction").value("9999"));
		result.andExpect(jsonPath("$.appPercent").value("0.99"));
	}

	@Test
	public void testPutCardPoint() throws Exception {
		// given
		PutCardPointDto putCardPointDto = new PutCardPointDto("9999", new BigDecimal("0"));
		Gson gson = new Gson();
		String json = gson.toJson(putCardPointDto);
		// when
		ResultActions result = mockMvc.perform(put("/api/cardpoints/{cardFraction}", "9999")
				.contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andDo(print());
		// then
		result.andExpect(status().isOk());
		verify(cardPointService).modifyCardPoint(putCardPointDto);
	}

	@Test
	public void testDeleteCardPoint() throws Exception {
		// given
		DeleteCardPointDto deleteCardPointDto = new DeleteCardPointDto("9999");
		// when
		ResultActions result = mockMvc.perform(delete("/api/cardpoints/{cardFraction}", "9999")).andDo(print());
		// then
		result.andExpect(status().isNoContent());
		verify(cardPointService).removeCardPoint(deleteCardPointDto);
	}
}
