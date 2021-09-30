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

@Component("postgresqlCreater")
public class PostgresqlCreater implements IDbCreate {

	private static final Logger LOG = LoggerFactory.getLogger(PostgresqlCreater.class);

	@Override
	public IDbPool create(IDbConfig config) throws PoolException {
		LOG.debug("postgresql create start");
		DbPool pool = new DbPool();
		pool.setType(config.getType());
		IMyDataSources ds = new PostgresqlDataSource();
		ds.init(config);
		pool.setPool(ds);
		LOG.debug("postgresql create end");
		return pool;
	}

}
