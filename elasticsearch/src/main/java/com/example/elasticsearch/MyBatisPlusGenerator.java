package com.example.elasticsearch;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>MyBatisPlus代码生成</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月13日 11:34
 */
public class MyBatisPlusGenerator {
	public static void main(String[] args) {
		
		// 要生成的表列表
		List<String> includeTables = new LinkedList<>();
		includeTables.add("t_title");
		includeTables.add("t_title_content");
		
		
		FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/elasticsearch-demo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai" + "&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false" + "&rewriteBatchedStatements=true", "root", "123456").globalConfig(builder -> {
					builder.author("Evan") // 设置作者
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.outputDir("E:\\mybatis-out"); // 指定输出目录
				}).packageConfig(builder -> {
					builder.parent("com.mh") // 设置父包名
							.moduleName("jishi") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\mybatis-out")); // 设置mapperXml生成路径
				}).strategyConfig(builder -> {
					builder.addInclude(includeTables) // 设置需要生成的表名
							.addTablePrefix("t_", "c_"); // 设置过滤表前缀
				}).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}
