package com.dakai.readfile.dbclient.param;

import com.dakai.readfile.dbclient.entity.DbType;
import com.dakai.readfile.dbclient.iter.IDbConfig;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DbCreateParam implements IDbConfig {

	private DbType type;

	private String host;

	private int port;

	private String name;

	private String userName;

	private String password;

	private String token;

}
