package com.example.elasticsearch.controller;

import com.example.elasticsearch.controller.dto.TitleDTO;
import com.example.elasticsearch.service.impl.TitleServiceImpl;
import com.example.elasticsearch.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
@RestController
@RequestMapping(value = "/title")
@Api(tags = "ES索引 [title]  CURD 使用 easy-es 框架方式实现 ~ 关联数据库")
@Slf4j
public class TitleController {
	
	private final TitleServiceImpl service;
	
	public TitleController(TitleServiceImpl service) {
		this.service = service;
	}
	
	@ApiOperation(value = "添加文章")
	@PostMapping(value = "/insert")
	public ResponseUtil<?> insert(@RequestBody TitleDTO dto) {
		return service.insert(dto);
	}
	
	@ApiOperation(value = "修改文章")
	@PostMapping(value = "/update")
	public ResponseUtil<?> update(@RequestBody TitleDTO dto) {
		return service.update(dto);
	}
	
	@ApiOperation(value = "删除文章")
	@DeleteMapping(value = "/delete")
	public ResponseUtil<?> delete(@RequestParam Integer id) {
		return service.delete(id);
	}
	
	@ApiOperation(value = "分页查询文章")
	@GetMapping(value = "/pageQuery")
	public ResponseUtil<?> pageQuery(TitleDTO dto) {
		return service.pageQuery(dto);
	}
	
	
}
