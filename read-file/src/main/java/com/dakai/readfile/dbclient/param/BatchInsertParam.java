package com.dakai.readfile.dbclient.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class BatchInsertParam extends SqlDataParam {

	private String sql;
	private String sourcePath;
	private String sourceType;
	private int startRow;  //
	private Map<Integer, String> rules;

}
