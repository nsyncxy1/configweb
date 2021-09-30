package com.dakai.readfile.dbclient.service;

import com.dakai.readfile.dbclient.entity.Result;
import com.dakai.readfile.dbclient.iter.IDBPoolsManager;
import com.dakai.readfile.dbclient.param.BatchInsertParam;
import com.dakai.readfile.dbclient.parser.RowData;
import com.dakai.readfile.dbclient.parser.SourceData;
import com.dakai.readfile.dbclient.parser.SourceParser;
import com.dakai.readfile.dbclient.task.Configuration;
import com.dakai.readfile.dbclient.task.TaskResult;
import com.dakai.readfile.dbclient.task.execute.DefaultTaskExecuteThread;
import com.dakai.readfile.dbclient.utils.IdUtils;
import com.dakai.readfile.dbclient.utils.ResultUtils;
import com.dakai.readfile.dbclient.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BatchInsertService {

	private final Logger LOG = LoggerFactory.getLogger(BatchInsertService.class);

	@Autowired
	IDBPoolsManager dbPoolManager;

	@Autowired
	private Configuration configuration;
	private SourceParser parser;
	private final Map<String, TaskResult> resultMap = new ConcurrentHashMap<>();

	public Map<String, Object> insert(BatchInsertParam param){
		Result result = ResultUtils.simpleResult();
		LOG.info("提交导入任务...");

		if(parser == null) {
			parser = configuration.newParser(param.getSourceType());
		}

		if(parser != null) {
			try {

				SourceData data = parser.parseAndCheck(param.getSourcePath(), configuration.typeHandlers(param.getRules()), param.getStartRow()-1);
				TaskResult tr = new TaskResult();
				tr.addError(data.getErrorInfoList(), data.getFailRows());
				tr.setTotal(data.getAllList().size());
				tr.setFail(data.getAbnormalList().size());
				String taskId = IdUtils.generateId();
				resultMap.put(taskId, tr);
				List<RowData> list = data.getNormalList();
				if(list.size() > 0) {
					// 提交任务（启动一个线程去执行）
					new DefaultTaskExecuteThread((DataSource)(dbPoolManager.getDbPool(getTikeByParam(param)).getPool()), param.getSql(),
							tr, configuration, list).start();
				}else {
					LOG.error("无可导入数据");
					result.setError("无可导入数据");
					if(data.getErrorInfoList().size() > 0) {
						result.setProperty("onlyError", "yes");
					}
				}
				result.setProperty("taskResult", tr);
				result.setProperty("taskId", taskId);

			} catch (Exception e) {
				LOG.error("解析出错", e);
				e.printStackTrace();
				result.setError("解析出错");
			}
		}else {
			LOG.error("文件类型不正确");
			result.setError("文件类型不正确");
		}

		LOG.info("提交任务完成， " + result.getResult().toString());
		return result.getResult();
	}

	public TaskResult processResult(String taskId) {
		TaskResult tr = resultMap.get(taskId);
		if((tr.getSuccess() + tr.getFail()) >= tr.getTotal()) {
			return tr;
		}
		TaskResult result = new TaskResult();
		result.setTotal(tr.getTotal());
		result.setSuccess(tr.getSuccess());
		result.setFail(tr.getFail());
		return result;
	}

	//获取token和dbId的组合
	private String getTikeByParam(BatchInsertParam param) {
		if(StringUtil.isBlank(param.getDbId()) || StringUtil.isBlank(param.getToken())) {
			return null;
		}
		return param.getToken() + param.getDbId();
	}

}
