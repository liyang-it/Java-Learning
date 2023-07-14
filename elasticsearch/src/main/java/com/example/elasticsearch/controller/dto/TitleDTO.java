package com.example.elasticsearch.controller.dto;

import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <h2>文章CURD参数</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月13日 16:29
 */
@Data
public class TitleDTO {
	private Integer page;
	private Integer limit;
	
	@ApiModelProperty("文章ID")
	private Integer id;
	
	@ApiModelProperty("文章标题")
	private String title;
	
	@ApiModelProperty("文章简介")
	private String about;
	
	@ApiModelProperty("文章内容")
	private String content;
	
	@ApiModelProperty("作者")
	private String author;
	
	public void valid() {
		
		if (StringUtils.isNullOrEmpty(title)) {
			
			throw new RuntimeException("文章标题不能为空");
			
		} else if (StringUtils.isNullOrEmpty(about)) {
			
			throw new RuntimeException("文章简介不能为空");
			
		} else if (StringUtils.isNullOrEmpty(content)) {
			
			throw new RuntimeException("文章内容不能为空");
			
		} else if (StringUtils.isNullOrEmpty(author)) {
			
			throw new RuntimeException("作者不能为空");
			
		}
	}
}
