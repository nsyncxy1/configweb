package com.dakai.readfile.dbclient.param;

import cn.hutool.core.codec.Base64;
import com.dakai.readfile.dbclient.iter.IDbToken;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.Base64Utils;

/**
 * 数据查询参数
 * @author yujie
 *
 */
@ToString
public class SqlDataParam implements IDbToken {

	//查询sql语句
	private String sql;

	private String token;

	//查询数据库id
	private String dbId;

	public String getSql() {
//		System.out.println("加密的--sql-->"+sql);
		if (sql != null) {
			String se1 = Base64.decodeStr(sql);
			String se2 = se1.substring(1);
			String sqlString = Base64.decodeStr(se2);
//			System.out.println("entity--sql-->"+sqlString);
			return sqlString;
		}

		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String getDbId() {
		return dbId;
	}

	public void setDbId(String dbId) {
		this.dbId = dbId;
	}
}
