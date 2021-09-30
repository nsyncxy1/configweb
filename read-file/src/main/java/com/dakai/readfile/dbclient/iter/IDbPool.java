package com.dakai.readfile.dbclient.iter;


import com.dakai.readfile.dbclient.entity.DbType;

public interface IDbPool {

	DbType getType();

	Object getPool();

}
