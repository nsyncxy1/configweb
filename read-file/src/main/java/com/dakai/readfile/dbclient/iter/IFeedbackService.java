package com.dakai.readfile.dbclient.iter;


import com.dakai.readfile.dbclient.entity.FeedbackIssue;
import com.dakai.readfile.dbclient.param.IssueParam;

import java.util.List;
import java.util.Map;

public interface IFeedbackService {

	Map<String, Object> publish(IssueParam param);

	List<FeedbackIssue> getList();

	int modifyStatus(String id, String status);

	FeedbackIssue getFeedbackById(String id);

}
