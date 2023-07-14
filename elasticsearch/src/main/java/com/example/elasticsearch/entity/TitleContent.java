package com.example.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 文章内容表
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
@TableName("t_title_content")
@ApiModel(value = "TitleContent对象", description = "文章内容表")
public class TitleContent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	@ApiModelProperty("文章ID")
	private Integer titleId;
	
	@ApiModelProperty("文章内容")
	private String content;
	
	public TitleContent(Integer titleId, String content) {
		this.titleId = titleId;
		this.content = content;
	}
	
	public TitleContent() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getTitleId() {
		return titleId;
	}
	
	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "EsTitleContent{" + "id = " + id + ", titleId = " + titleId + ", content = " + content + "}";
	}
}
