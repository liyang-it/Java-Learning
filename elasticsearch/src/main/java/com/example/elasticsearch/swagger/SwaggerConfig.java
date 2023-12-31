package com.example.elasticsearch.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <h2>swagger配置</h2>
 * <p>
 * &#064;EnableSwagger2  开启Swagger使用(项目注释文档)
 * &#064;Configuration  标明是配置类
 * &#064;EnableWebMvc  解决SpringBoot2.6版本以后兼容Swagger2
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月03日 14:56
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				//用于生成API信息
				.apiInfo(apiInfo())
				//select()函数返回一个ApiSelectorBuilder实例,用来控制接口被swagger做成文档
				.select()
				//用于指定扫描哪个包下的接口
				.apis(RequestHandlerSelectors.basePackage("com.example.elasticsearch.controller"))
				//选择所有的API,如果你想只为部分API生成文档，可以配置这里
				.paths(PathSelectors.any()).build();
	}
	
	/**
	 * 用于定义API主界面的信息，比如可以声明所有的API的总标题、描述、版本
	 *
	 * @return {@link ApiInfo}
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//用来自定义API的标题
				.title("Elastic Search示例")
				//用来描述整体的API
				.description("SpringBoot整合 Elasticsearch")
				//创建人信息
				.contact(new Contact("lizr", "http://localhost:8080/es/swagger-ui.html", "xxxxxxxx@163.com"))
				//用于定义服务的域名
				//.termsOfServiceUrl("")
				//可以用来定义版本
				.version("1.0").build();
	}
	
	
}
