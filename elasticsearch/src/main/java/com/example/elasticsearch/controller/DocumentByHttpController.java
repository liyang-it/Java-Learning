package com.example.elasticsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.elasticsearch.config.EsConnection;
import com.example.elasticsearch.entity.es.Document;
import com.example.elasticsearch.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <h2>document控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月11日 16:29
 */
@RestController
@RequestMapping(value = "/document/http")
@Api(tags = "ES索引 [document]  CURD 使用 http接口方式实现 ~ 无关联数据库")
@Slf4j
public class DocumentByHttpController {
	
	private final EsConnection esConnection;
	
	private final ConnectionPool connectionPool;
	
	@SuppressWarnings("all")
	public DocumentByHttpController(EsConnection esConnection) {
		this.esConnection = esConnection;
		this.connectionPool = new ConnectionPool(20, 5L, TimeUnit.MINUTES);
	}
	
	private OkHttpClient getHttpClient() {
		return new OkHttpClient.Builder().connectionPool(connectionPool).build();
	}
	
	/**
	 * 执行http请求
	 *
	 * @param url           请求地址
	 * @param requestType   请求类型 get、post...
	 * @param doYouNeedData 是否需要数据, true 需要 , false 不需要
	 * @param contentType   Content-Type 头,例如 application/json
	 * @param jsonData      请求数据
	 * @return 请求结果
	 */
	private String execute(String url, String requestType, boolean doYouNeedData, String contentType, String jsonData) throws IOException {
		OkHttpClient client = getHttpClient();
		
		RequestBody requestBody = null;
		
		if (doYouNeedData) {
			requestBody = RequestBody.create(jsonData, MediaType.parse(contentType));
		} else {
			requestBody = RequestBody.create(new byte[0], null);
		}
		
		Request request = null;
		
		
		if ("get".equalsIgnoreCase(requestType)) {
			request = new Request.Builder().url(url).get().build();
			
			
		} else if ("post".equalsIgnoreCase(requestType)) {
			
			request = new Request.Builder().url(url).post(requestBody).build();
			
		} else if ("put".equalsIgnoreCase(requestType)) {
			
			request = new Request.Builder().url(url).put(requestBody).build();
			
		} else if ("delete".equalsIgnoreCase(requestType)) {
			
			request = new Request.Builder().url(url).delete(requestBody).build();
			
		}
		
		Response response = client.newCall(request).execute();
		
		return response.body().string();
	}
	
	@ApiOperation(value = "获取ES所有索引")
	@GetMapping("getAllIndex")
	public String getAllIndex() throws IOException {
		String url = String.format("http://%s%s", esConnection.getAddress(), "/_cat/indices?v");
		return execute(url, "get", false, null, null);
	}
	
	@ApiOperation(value = "创建 document 索引")
	@GetMapping("/createIndex")
	public String createIndex() throws IOException {
		String url = String.format("http://%s%s", esConnection.getAddress(), "/document");
		// 1.初始化-> 创建索引(相当于mysql中的表)
		
		// 创建 索引不需要参数
		String result = execute(url, "put", false, null, null);
		log.info("使用 http 创建 [document]索引 Url: {}, 结果: {}", url, result);
		return result;
	}
	
	@ApiOperation(value = "添加数据")
	@GetMapping("/insert")
	public String insert(@RequestParam @ApiParam(name = "title", value = "标题") String title, @RequestParam @ApiParam(name = "content", value = "内容") String content) throws IOException {
		// 2.初始化-> 新增数据
		
		Document document = new Document();
		document.setTitle(title);
		document.setContent(content);
		
		// 添加数据也可以自己生成ID，在/_doc/你自己生成的id
		
		String url = String.format("http://%s%s", esConnection.getAddress(), "/document/_doc");
		
		String result = execute(url, "post", true, "application/json", document.toString());
		log.info("使用 http 添加数据 Url: {}, 结果: {}", url, result);
		return result;
		
	}
	
	@ApiOperation(value = "修改数据")
	@GetMapping("/update")
	public String update(@RequestParam @ApiParam(name = "id", value = "添加数据，ES返回的唯一 _id字段数据") String id, @RequestParam @ApiParam(name = "title", value = "标题") String title, @RequestParam @ApiParam(name = "content", value = "内容") String content) throws IOException {
		
		// 如果 _id 数据不存在，就是创建
		String url = String.format("http://%s%s%s", esConnection.getAddress(), "/document/_doc/", id);
		
		Document document = new Document();
		document.setTitle(title);
		document.setContent(content);
		
		String result = execute(url, "post", true, "application/json", document.toString());
		log.info("使用 http 修改数据 Url: {}, 结果: {}", url, result);
		return result;
	}
	
	@ApiOperation(value = "删除数据")
	@GetMapping("/delete")
	public String delete(@RequestParam @ApiParam(name = "id", value = "添加数据，ES返回的唯一 _id字段数据") String id) throws IOException {
		
		String url = String.format("http://%s%s%s", esConnection.getAddress(), "/document/_doc/", id);
		
		String result = execute(url, "delete", false, null, null);
		log.info("使用 http 删除数据 Url: {}, 结果: {}", url, result);
		return result;
	}
	
	
	@ApiOperation(value = "列表查询数据")
	@GetMapping("/listQuery")
	public JSONObject listQuery(@RequestParam(defaultValue = "") @ApiParam(name = "q", value = "可查询 title、content字段数据") String q) throws IOException {
		
		String url = String.format("http://%s%s", esConnection.getAddress(), "/document/_doc/_search");
		
		// 因为Elasticsearch查询每次只返回10条数据, 先使用空条件查询，得到数据总条数
		String emptyQuery = execute(url, "get", false, null, null);
		
		
		JSONObject emptyQueryJson = JSONObject.parseObject(emptyQuery);
		
		JSONObject emptyHits = emptyQueryJson.getJSONObject("hits");
		if (emptyHits == null) {
			return emptyQueryJson;
		}
		
		Integer total = emptyHits.getJSONObject("total").getInteger("value");
		
		log.info("列表查询,获取 数据总条数: {}", total);
		
		String queryJsonParam;
		
		if (null != q && q.length() != 0) {
			queryJsonParam = "{\"size\": " + total + ",\"query\": {\"multi_match\": {\"query\": \"" + q + "\",\"fields\": [\"content\", \"title\"]}}}";
		} else {
			// 查询条件为空
			queryJsonParam = "{\"size\": " + total + ",\"query\": {\"match_all\": {}}}";
		}
		
		
		// 拿到数据总条数，在进行具体的条件查询
		log.info("根据条件查询 Url: {}, json参数: {}", url, queryJsonParam);
		
		String result = execute(url, "post", true, "application/json;charset=UTF-8", queryJsonParam);
		
		return JSONObject.parseObject(result);
	}
	
	@ApiOperation(value = "分页查询数据")
	@GetMapping("/pageQuery")
	public ResponseUtil<?> pageQuery(@RequestParam(defaultValue = "") @ApiParam(name = "q", value = "可查询 title、content字段数据") String q, @RequestParam(defaultValue = "1") @ApiParam(name = "page", value = "当前页") Integer page, @RequestParam(defaultValue = "10") @ApiParam(name = "size", value = "显示条数") Integer limit) {
		
		return ResponseUtil.success("http分页查询不做示例了,在http列表查询的基础上计算好size(显示条数)、form(从第几条开始查询) 和MySQL的limit一样");
	}
}
