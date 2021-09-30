package com.dakai.readfile.dbclient.act;

import com.dakai.readfile.dbclient.entity.DbPool;
import com.dakai.readfile.dbclient.err.PoolException;
import com.dakai.readfile.dbclient.iter.IDbConfig;
import com.dakai.readfile.dbclient.iter.IDbCreate;
import com.dakai.readfile.dbclient.iter.IDbPool;
import com.dakai.readfile.dbclient.iter.IMyDataSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * oracle 数据库连接创造器
 * @author jayu
 *
 */
//@Component("oracleCreater")
public class OracleCreater implements IDbCreate {

	private static final Logger LOG = LoggerFactory.getLogger(OracleCreater.class);

	@Override
	public IDbPool create(IDbConfig config) throws PoolException {
		LOG.debug("oracle create start");
		DbPool pool = new DbPool();
		pool.setType(config.getType());
		IMyDataSources ds = new MyOracleDataSource();
		ds.init(config);
		pool.setPool(ds);
		LOG.debug("oracle create end");
		return pool;
	}

}
