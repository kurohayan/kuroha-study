package com.kuroha.algorithm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页类
 * @author kuroha
 */
@Data
public class Page implements Serializable {


	private static final long serialVersionUID = 4864251580254655476L;
	/**
	 * 起始页 与 offset 二选一即可
	 */
	private int current;
	/**
	 * 每页条数
	 */
	private int limit;
	/**
	 * 起始位置 与 current 二选一即可
	 */
	private int offset;
	/**
	 * 总数
	 */
	private int count;
	/**
	 * 总页数
	 */
	private int pageNum;

	public void init(){
		if (limit <= 0) {
			limit = 10;
		}
		if (current >0) {
			offset = (current - 1) * limit;
		} else {
			current = 1;
		}
		if (offset < 0){
			offset = 0;
		}
		if (count >0) {
			if (count%limit == 0) {
				pageNum = count / limit;
			} else {
				pageNum = count / limit + 1;
			}
		}
	}
}
