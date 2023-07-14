package com.example.elasticsearch.document;

import com.example.elasticsearch.ElasticsearchApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <h2>document索引测试</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月11日 15:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticsearchApplication.class)
public class TestDocument {
	
	@Test
	public void createIndex() {
	
	}
}
