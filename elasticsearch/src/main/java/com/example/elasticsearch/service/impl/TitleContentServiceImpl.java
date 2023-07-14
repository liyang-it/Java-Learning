package com.example.elasticsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.elasticsearch.entity.TitleContent;
import com.example.elasticsearch.mapper.TitleContentMapper;
import com.example.elasticsearch.service.ITitleContentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章内容表 服务实现类
 * </p>
 *
 * @author Evan
 * @since 2023-07-13
 */
@Service
public class TitleContentServiceImpl extends ServiceImpl<TitleContentMapper, TitleContent> implements ITitleContentService {

}
