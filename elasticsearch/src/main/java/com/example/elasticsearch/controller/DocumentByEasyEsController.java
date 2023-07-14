package com.example.elasticsearch.controller;

import com.example.elasticsearch.entity.es.Document;
import com.example.elasticsearch.mapper.es.DocumentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dromara.easyes.core.biz.EsPageInfo;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h2>document控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月11日 16:29
 */
@RestController
@RequestMapping(value = "/document/easy")
@Api(tags = "ES索引 [document]  CURD 使用 Easy-es 框架实现 ~ 无关联数据库")
public class DocumentByEasyEsController {
	
	private final DocumentMapper documentMapper;
	
	@SuppressWarnings("all")
	public DocumentByEasyEsController(DocumentMapper documentMapper) {
		this.documentMapper = documentMapper;
	}
	
	private LambdaEsQueryWrapper<Document> getQueryWrapper() {
		return new LambdaEsQueryWrapper<>();
	}
	
	@ApiOperation(value = "创建 document 索引")
	@GetMapping("/createIndex")
	public Boolean createIndex() {
		// 1.初始化-> 创建索引(相当于mysql中的表)
		return documentMapper.createIndex();
	}
	
	@ApiOperation(value = "添加数据")
	@GetMapping("/insert")
	public Integer insert(@RequestParam @ApiParam(name = "title", value = "标题") String title, @RequestParam @ApiParam(name = "content", value = "内容") String content) {
		// 2.初始化-> 新增数据
		Document document = new Document();
		document.setTitle(title);
		document.setContent(content);
		return documentMapper.insert(document);
	}
	
	@ApiOperation(value = "列表查询数据")
	@GetMapping("/listQuery")
	public List<Document> listQuery(@RequestParam(defaultValue = "") @ApiParam(name = "q", value = "可查询 title、content字段数据") String q) {
		
		LambdaEsQueryWrapper<Document> wrapper = getQueryWrapper();
		if (null != q && q.length() != 0) {
			wrapper.like(Document::getTitle, q);
			wrapper.or();
			wrapper.like(Document::getContent, q);
		}
		return documentMapper.selectList(wrapper);
	}
	
	@ApiOperation(value = "分页查询数据")
	@GetMapping("/pageQuery")
	public EsPageInfo<Document> pageQuery(@RequestParam(defaultValue = "") @ApiParam(name = "q", value = "可查询 title、content字段数据") String q, @RequestParam(defaultValue = "1") @ApiParam(name = "page", value = "当前页") Integer page, @RequestParam(defaultValue = "10") @ApiParam(name = "size", value = "显示条数") Integer limit) {
		// 3.查询出所有标题为老汉的文档列表
		LambdaEsQueryWrapper<Document> wrapper = getQueryWrapper();
		if (null != q && q.length() != 0) {
			wrapper.like(Document::getTitle, q);
			wrapper.or();
			wrapper.like(Document::getContent, q);
		}
		return documentMapper.pageQuery(wrapper, page, limit);
	}
	
	
}
