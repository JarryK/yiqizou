package com.web.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Page<T> extends RowBounds {
	
	/**
	 * 页码 表示第一页
	 * */
	private int currPage;
	
	/**
	 * 每页数量
	 * */
	private int pageSize;
	
	/**
	 * 查询条件对象
	 * */ 
	private T queryObj;

	public Page(int currPage, int pageSize , T queryObj) {
		super(currPage * pageSize,pageSize);
		this.currPage = currPage;
		this.pageSize = pageSize;
		this.queryObj = queryObj;
	}
	
	/**
	 * 根据Example条件进行模糊查询
	 * Example可以参考	https://blog.csdn.net/u013521531/article/details/78811077
	 * @param mapper 用于查询的mapper
	 * @param example 模糊查询对象
	 * @return 带count和查询结果list的map对象
	 * */
	@SuppressWarnings("unchecked")
	public Map<String,Object> selectByExample(Mapper mapper, Example example){
		int count = mapper.selectCountByExample(example);
		List<Object> list = mapper.selectByExampleAndRowBounds(example, this);
		HashMap<String, Object> result = new HashMap<>();
		result.put("count", count);
		result.put("list", list);
		return result;
	}
	
}
