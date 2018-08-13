package com.sjkim.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AbstractDto extends BaseObject {

	private static final long serialVersionUID = 3473899128077453595L;

	private Integer startRowNum;

	private Integer endRowNum;

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public Integer getStartRowNum() {
		return startRowNum;
	}

	public void setStartRowNum(Integer startRowNum) {
		this.startRowNum = startRowNum;
	}

	public Integer getEndRowNum() {
		return endRowNum; 
	}

	public void setEndRowNum(Integer endRowNum) {
		this.endRowNum = endRowNum;
	}
}
