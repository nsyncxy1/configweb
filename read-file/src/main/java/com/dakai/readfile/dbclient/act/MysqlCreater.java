package com.dakai.readfile.dbclient.act;

import com.dakai.readfile.dbclient.entity.DbPool;
import com.dakai.readfile.dbclient.err.PoolException;
import com.dakai.readfile.dbclient.iter.IDbConfig;
import com.dakai.readfile.dbclient.iter.IDbCreate;
import com.dakai.readfile.dbclient.iter.IDbPool;
import com.dakai.readfile.dbclient.iter.IMyDataSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * mysql 数据库连接池创建
 * @author jayu
 *
 */
@Component("mysqlCreater")
public class MysqlCreater implements IDbCreate {

	private static final Logger log = LoggerFactory.getLogger(MysqlCreater.class);

	@Override
	public IDbPool create(IDbConfig config) throws PoolException {
		log.debug("mysql create start");
		DbPool pool = new DbPool();
		pool.setType(config.getType());
		IMyDataSources ds = new MySqlDataSource();
		ds.init(config);
		pool.setPool(ds);
		log.debug("mysql create end");
		return pool;
	}

}
