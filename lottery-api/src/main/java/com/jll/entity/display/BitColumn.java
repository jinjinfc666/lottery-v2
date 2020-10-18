package com.jll.entity.display;

import java.util.List;

import com.jll.entity.PlayTypeNum;

/**
 * define a column display on front end.
 * @author root
 *
 */
public class BitColumn {
	
	private String columnName;
	
	private Integer bitIndex;
	
	/**
	 * the betting number including in the column
	 */
	private List<PlayTypeNum> playTypeNums;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public List<PlayTypeNum> getPlayTypeNums() {
		return playTypeNums;
	}

	public void setPlayTypeNums(List<PlayTypeNum> playTypeNums) {
		this.playTypeNums = playTypeNums;
	}

	public Integer getBitIndex() {
		return bitIndex;
	}

	public void setBitIndex(Integer bitIndex) {
		this.bitIndex = bitIndex;
	}
	
	
}
