package com.dakai.readfile.dbclient.iter;


import com.dakai.readfile.dbclient.err.SqlHandlerException;

import java.util.Map;

/**
 * sql处理
 * @author yujie
 * 2019年4月10日 上午9:29:52
 */
public interface ISqlHandler {

	/**
	 * sql语句的执行
	 */
	Map<String, Object> execute(Object pool, String sql, String token)
			throws SqlHandlerException;

}
