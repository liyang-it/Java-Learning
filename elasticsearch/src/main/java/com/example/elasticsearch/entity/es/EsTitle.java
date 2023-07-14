package com.example.elasticsearch.entity.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.Analyzer;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 文章表 - ES映射对象
 * 使用到了ik分词器，需要安装 <a href="https://github.com/medcl/elasticsearch-analysis-ik/tree/v7.14.0">ik分词器插件</a>
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
@IndexName("es_title")
@ApiModel(value = "Title对象", description = "文章表 - ES映射对象")
public class EsTitle implements Serializable {
	
	public static final String INDEX_NAME = "es_title";
	private static final long serialVersionUID = 1L;
	/**
	 * es中的唯一id,如果你想自定义es中的id为你提供的id,比如MySQL中的id,请将注解中的type指定为customize,如此id便支持任意数据类型),使用文章表ID代替
	 */
	@IndexId(type = IdType.CUSTOMIZE)
	private Integer id;
	
	
	/**
	 * 指定类型及存储/查询分词器
	 */
	@IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_SMART, searchAnalyzer = Analyzer.IK_MAX_WORD)
	@ApiModelProperty("文章标题")
	private String title;
	
	/**
	 * 指定类型及存储/查询分词器
	 */
	@IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_SMART, searchAnalyzer = Analyzer.IK_MAX_WORD)
	@ApiModelProperty("文章简介")
	private String about;
	
	
	/**
	 * 指定类型及存储/查询分词器
	 */
	@ApiModelProperty("文章内容ID")
	private Integer contentId;
	/**
	 * 指定类型及存储/查询分词器
	 */
	@IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_SMART, searchAnalyzer = Analyzer.IK_MAX_WORD)
	@ApiModelProperty("文章内容")
	private String content;
	
	/**
	 * 指定类型及存储/查询分词器
	 */
	@IndexField(fieldType = FieldType.TEXT, analyzer = Analyzer.IK_SMART, searchAnalyzer = Analyzer.IK_MAX_WORD)
	@ApiModelProperty("作者")
	private String author;
	
	@ApiModelProperty("创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	
	@ApiModelProperty("修改时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime;
	
	
	public Integer getContentId() {
		return contentId;
	}
	
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
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
	
	
}
