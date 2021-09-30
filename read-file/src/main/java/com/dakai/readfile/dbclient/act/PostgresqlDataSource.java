package com.dakai.readfile.dbclient.act;

import com.dakai.readfile.dbclient.iter.IDbConfig;

public class PostgresqlDataSource extends AbstractMyDataSources {

	public static final String driver = "org.postgresql.Driver";

	@Override
	protected String getUrl(IDbConfig config) {
		return "jdbc:postgresql://" + config.getHost() + ":"
					+ config.getPort() + "/" + config.getName();
	}

	@Override
	protected String getDriver() {
		return driver;
	}

}
