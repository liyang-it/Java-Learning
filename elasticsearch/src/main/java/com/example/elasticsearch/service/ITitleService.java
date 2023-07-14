package com.example.elasticsearch.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.elasticsearch.controller.dto.TitleDTO;
import com.example.elasticsearch.entity.Title;
import com.example.elasticsearch.util.ResponseUtil;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
public interface ITitleService extends IService<Title> {
	ResponseUtil<?> insert(TitleDTO dto);
	
	ResponseUtil<?> update(TitleDTO dto);
	
	ResponseUtil<?> delete(Integer id);
	
	ResponseUtil<?> pageQuery(TitleDTO dto);
	
}
