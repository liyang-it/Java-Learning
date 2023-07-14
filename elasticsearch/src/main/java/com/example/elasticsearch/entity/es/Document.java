package com.example.elasticsearch.entity.es;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;

/**
 * <h2>文档实体类 ~ 不对接数据库</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月11日 15:33
 */
@Data
@IndexName("document")
public class Document {
	
	/**
	 * es中的唯一id
	 */
	@IndexId
	private String id;
	/**
	 * 文档标题
	 */
	private String title;
	/**
	 * 文档内容
	 */
	private String content;
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
