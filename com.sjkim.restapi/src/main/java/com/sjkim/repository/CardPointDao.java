package com.sjkim.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sjkim.dto.GetCardPointDto;
import com.sjkim.dto.PostCardPointDto;
import com.sjkim.dto.PutCardPointDto;
import com.sjkim.vo.CardPointVo;

@Mapper
@Repository
public interface CardPointDao {

	int insertCardPoint(PostCardPointDto postCardPointDto);

	List<CardPointVo> selectCardPointAll(GetCardPointDto getCardPointDto);

	CardPointVo selectCardPointByCardFraction(String cardFraction);

	int updateCardPoint(PutCardPointDto putCardPointDto);

	int deleteCardPoint(String cardFraction);
}
