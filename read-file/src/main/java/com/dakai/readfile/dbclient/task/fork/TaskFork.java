package com.dakai.readfile.dbclient.task.fork;


import com.dakai.readfile.dbclient.parser.RowData;
import com.dakai.readfile.dbclient.task.BatchInsertTask;
import com.dakai.readfile.dbclient.task.TaskResult;

import java.util.List;

public interface TaskFork {

	List<BatchInsertTask> fork(List<RowData> list, final TaskResult finalResult);

}
