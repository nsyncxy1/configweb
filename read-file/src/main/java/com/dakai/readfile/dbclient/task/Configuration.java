package com.dakai.readfile.dbclient.task;

import com.dakai.readfile.dbclient.parser.SourceParser;
import com.dakai.readfile.dbclient.parser.TypeHandler;
import com.dakai.readfile.dbclient.parser.excel.ExcelParser;
import com.dakai.readfile.dbclient.parser.type.*;
import com.dakai.readfile.dbclient.task.fork.DefaultTaskFork;
import com.dakai.readfile.dbclient.task.fork.TaskFork;
import com.dakai.readfile.dbclient.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Configuration {

	@Value("${insert.batch.size}")
	private int batchSize = 400;
	@Value("${insert.thread.size}")
	private int threadSize = 4;

	private static Map<String, TypeHandler> typeHandlers = new HashMap<>();
	static {
		typeHandlers.put("string", new StringTypeHandler());
		typeHandlers.put("int", new IntTypeHandler());
		typeHandlers.put("date", new DateTypeHandler());
		typeHandlers.put("number", new NumberTypeHandler());
		typeHandlers.put("year", new YearTypeHandler());
	}

	public TaskFork newTaskFork(DataSource dataSource, String sql) {
		return new DefaultTaskFork()
				.batchSize(batchSize)
				.dataSource(dataSource)
				.sql(sql);
	}

	public int batchSize() {
		return batchSize;
	}

	public ExecutorService newExecutor() {
		int size = 4;
		if(threadSize > 0) {
			size = threadSize;
		}
		size = size > 16 ? 16 : size;
		return Executors.newFixedThreadPool(size);
	}

	public SourceParser newParser(String sourceType) {
		SourceParser parser = null;
		if(!StringUtil.isBlank(sourceType)) {
			switch (sourceType) {
				case "excel":
					parser = new ExcelParser();
					break;
			}
		}
		return parser;
	}

	public Map<Integer, TypeHandler> typeHandlers(Map<Integer, String> type){
		Map<Integer, TypeHandler> map = new HashMap<>();
		if(type != null) {
			type.forEach((k, v) -> {
				String t = v.toLowerCase();
				if(typeHandlers.containsKey(t)) {
					map.put(k, typeHandlers.get(t));
				}
			});
		}
		return map;
	}

}
