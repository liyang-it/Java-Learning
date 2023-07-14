package com.example.elasticsearch.service.es;

import com.example.elasticsearch.controller.dto.TitleDTO;
import com.example.elasticsearch.entity.Title;
import com.example.elasticsearch.entity.TitleContent;
import com.example.elasticsearch.entity.es.EsTitle;
import com.example.elasticsearch.mapper.es.EsTitleMapper;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <h2>es文章服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月13日 16:35
 */
@Service
public class EsTitleService {
	
	private final EsTitleMapper mapper;
	
	@SuppressWarnings("all")
	public EsTitleService(EsTitleMapper mapper) {
		this.mapper = mapper;
	}
	
	@Async
	public void save(Title title, TitleContent content) {
		EsTitle esTitle = new EsTitle();
		esTitle.setContentId(content.getId());
		esTitle.setContent(content.getContent());
		esTitle.setId(title.getId());
		esTitle.setTitle(title.getTitle());
		esTitle.setAbout(title.getAbout());
		esTitle.setAuthor(title.getAuthor());
		esTitle.setCreateTime(title.getCreateTime());
		mapper.insert(esTitle);
	}
	
	@Async
	public void update(Title title, TitleContent content) {
		EsTitle esTitle = new EsTitle();
		
		esTitle.setContent(content.getContent());
		
		// ID存在就是修改，不存在就是删除
		esTitle.setId(title.getId());
		
		esTitle.setTitle(title.getTitle());
		
		esTitle.setAbout(title.getAbout());
		
		esTitle.setAuthor(title.getAuthor());
		
		esTitle.setUpdateTime(title.getUpdateTime());
		
		mapper.updateById(esTitle);
	}
	
	@Async
	public void delete(Integer id) {
		mapper.deleteById(id);
	}
	
	
	public EsPageInfo<EsTitle> pageQuery(TitleDTO dto) {
		if (dto.getPage() == null) {
			dto.setPage(1);
		}
		if (dto.getLimit() == null) {
			dto.setLimit(50);
		}
		
		LambdaEsQueryWrapper<EsTitle> wrapper = new LambdaEsQueryWrapper<>();
		
		if (dto.getTitle() != null && dto.getTitle().length() != 0) {
			wrapper.match(EsTitle::getTitle, dto.getTitle());
		}
		
		if (dto.getAbout() != null && dto.getAbout().length() != 0) {
			wrapper.match(EsTitle::getAbout, dto.getAbout());
		}
		
		if (dto.getContent() != null && dto.getContent().length() != 0) {
			wrapper.match(EsTitle::getContent, dto.getContent());
		}
		
		if (dto.getAuthor() != null && dto.getAuthor().length() != 0) {
			wrapper.match(EsTitle::getAuthor, dto.getAuthor());
		}
		wrapper.orderByDesc(EsTitle::getId);
		return mapper.pageQuery(wrapper, dto.getPage(), dto.getLimit());
	}
	
}
