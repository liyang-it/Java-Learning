package com.example.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.elasticsearch.controller.dto.TitleDTO;
import com.example.elasticsearch.entity.Title;
import com.example.elasticsearch.entity.TitleContent;
import com.example.elasticsearch.mapper.TitleMapper;
import com.example.elasticsearch.service.ITitleService;
import com.example.elasticsearch.service.es.EsTitleService;
import com.example.elasticsearch.util.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements ITitleService {
	
	private final EsTitleService esTitleService;
	private final TitleContentServiceImpl contentService;
	
	@SuppressWarnings("all")
	public TitleServiceImpl(EsTitleService esTitleService, TitleContentServiceImpl contentService) {
		this.esTitleService = esTitleService;
		this.contentService = contentService;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseUtil<?> insert(TitleDTO dto) {
		dto.valid();
		// 保存文章
		Title title = new Title();
		title.setTitle(dto.getTitle());
		title.setAbout(dto.getAbout());
		title.setAuthor(dto.getAuthor());
		title.setCreateTime(LocalDateTime.now());
		title.setVersion(1);
		title.setDeleted(false);
		save(title);
		// 保存文章内容
		TitleContent content = new TitleContent(title.getId(), dto.getContent());
		contentService.save(content);
		
		// 异步保存到es,一般都是用mq异步到单独的es服务器
		esTitleService.save(title, content);
		
		
		return ResponseUtil.success(null);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseUtil<?> update(TitleDTO dto) {
		if (dto.getId() == null) {
			return ResponseUtil.error("Id不能为空");
		}
		
		dto.valid();
		Title query = getById(dto.getId());
		query.setTitle(dto.getTitle());
		query.setAbout(dto.getAbout());
		query.setAuthor(dto.getAuthor());
		query.setUpdateTime(LocalDateTime.now());
		
		// 修改文章
		updateById(query);
		
		// 更新文章内容
		TitleContent content = contentService.getBaseMapper().getByTitleId(query.getId());
		content.setContent(dto.getContent());
		contentService.updateById(content);
		
		// 异步修改到es,一般都是用mq异步到单独的es服务器
		esTitleService.update(query, content);
		
		
		return ResponseUtil.success(null);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseUtil<?> delete(Integer id) {
		if (id == null) {
			return ResponseUtil.error("ID不能为空");
		}
		Title query = getById(id);
		
		query.setDeleted(true);
		
		updateById(query);
		
		esTitleService.delete(id);
		
		return ResponseUtil.success(null);
	}
	
	@Override
	public ResponseUtil<?> pageQuery(TitleDTO dto) {
		return ResponseUtil.success(esTitleService.pageQuery(dto));
	}
	
}
