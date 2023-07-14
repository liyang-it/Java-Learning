package com.example.elasticsearch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
@TableName("t_title")
@ApiModel(value = "Title对象", description = "文章表")
public class Title implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	
	@ApiModelProperty("文章标题")
	private String title;
	
	@ApiModelProperty("文章简介")
	private String about;
	
	@ApiModelProperty("作者")
	private String author;
	
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
	
	@ApiModelProperty("修改时间")
	private LocalDateTime updateTime;
	
	@ApiModelProperty("数据版本")
	private Integer version;
	
	@ApiModelProperty("0 正常 1 删除")
	private Boolean deleted;
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public String toString() {
		return "EsTitle{" + "id = " + id + ", title = " + title + ", about = " + about + ", author = " + author + ", createTime = " + createTime + ", updateTime = " + updateTime + ", version = " + version + ", deleted = " + deleted + "}";
	}
}
