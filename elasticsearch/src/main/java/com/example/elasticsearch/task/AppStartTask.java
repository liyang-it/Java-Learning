package com.example.elasticsearch.task;

import com.example.elasticsearch.entity.es.EsTitle;
import com.example.elasticsearch.mapper.es.EsTitleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <h2>程序启动任务</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月13日 11:59
 */
@Component
@Slf4j
public class AppStartTask implements ApplicationRunner {
	
	private final EsTitleMapper esTitleMapper;
	
	@SuppressWarnings("all")
	public AppStartTask(EsTitleMapper esTitleMapper) {
		this.esTitleMapper = esTitleMapper;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initTitleIndex();
	}
	
	/**
	 * 初始化 "es_title" 文章索引, 相关文档: <a href="https://www.easy-es.cn/pages/e8b9ad/#%E5%88%9B%E5%BB%BA%E7%B4%A2%E5%BC%95">...</a>
	 */
	private void initTitleIndex() {
		
		String indexName = EsTitle.INDEX_NAME;
		
		log.info("系统启动,开始执行 创建初始化 [{}] 索引", indexName);
		
		boolean existsIndex = esTitleMapper.existsIndex(indexName);
		
		if (existsIndex) {
			log.info("[{}] 索引 存在,不需要创建", indexName);
		} else {
			log.info("索引不存在,创建 {}", esTitleMapper.createIndex(indexName));
		}
	}
	
	
}
