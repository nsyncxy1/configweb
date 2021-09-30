package com.dakai.readfile.dbclient.entity;

/**
 * 数据库类型
 * @author yujie
 *
 */
public enum DbType {
	Oracle("oracle"),
	Mysql("mysql"),
	Redis("redis"),
	Mongodb("mongodb"),
	Postgresql("postgresql");

	private final String name;

	private DbType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// 和ISqlHandler对应的实现类相对应
	public String getHandlerName() {
		return name + "Handler";
	}

	// 和IDbCreater对应的实现类相对应
	public String getCreaterName() {
		return name + "Creater";
	}

	// 通过name 获取枚举
	public static DbType getDbTypeByName(String name) {
		DbType[] ds = DbType.values();
		for(DbType d:ds) {
			if(d.getName().equals(name)) {
				return d;
			}
		}
		return null;
	}

}

