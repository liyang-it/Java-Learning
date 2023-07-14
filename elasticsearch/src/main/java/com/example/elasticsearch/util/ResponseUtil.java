package com.example.elasticsearch.util;

/**
 * <h2>请求结果</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月13日 9:51
 */
public class ResponseUtil<T> {
	private int code;
	private String msg;
	private T data;
	
	private ResponseUtil() {
	}
	
	private ResponseUtil(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public static ResponseUtil<?> success(Object data) {
		return new ResponseUtil<>(200, "成功", data);
	}
	
	public static ResponseUtil<?> error(String msg) {
		return new ResponseUtil<>(500, msg, null);
	}
	
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
