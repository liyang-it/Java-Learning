package com.example.elasticsearch.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.elasticsearch.entity.TitleContent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 文章内容表 Mapper 接口
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
public interface TitleContentMapper extends BaseMapper<TitleContent> {
	
	@Select("SELECT * FROM `t_title_content` where title_id = #{titleId}")
	TitleContent getByTitleId(@Param("titleId") Integer titleId);
}
