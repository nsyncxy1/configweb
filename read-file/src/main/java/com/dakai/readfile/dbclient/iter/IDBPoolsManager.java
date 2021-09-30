package com.dakai.readfile.dbclient.iter;


import com.dakai.readfile.dbclient.err.PoolException;

import java.util.Map;

/**
 * 数据库连接池管理对象
 * @author yujie
 *
 */
public interface IDBPoolsManager {

	IDbPool getDbPool(String token) throws PoolException;

	//返回包括token
	Map<String, Object> setDbPool(IDbConfig config) throws PoolException;

	boolean removeDbPool(String token) throws PoolException;

}
