package com.dakai.readfile.dbclient.iter;


import com.dakai.readfile.dbclient.entity.DbType;

public interface IDbConfig extends IToken {

	DbType getType();

	String getHost();

	int getPort();

	String getName();

	String getUserName();

	String getPassword();

}
