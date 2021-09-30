package com.dakai.readfile.dbclient.entity;

import com.dakai.readfile.dbclient.iter.IDbPool;
import lombok.Data;

@Data
public class DbPool implements IDbPool {

	private DbType type;

	private Object pool;

}
