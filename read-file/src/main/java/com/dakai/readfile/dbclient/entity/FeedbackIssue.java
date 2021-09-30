package com.dakai.readfile.dbclient.entity;

import com.dakai.readfile.dbclient.iter.IToken;
import lombok.Data;

@Data
public class FeedbackIssue implements IToken {

	private String token;

	private String title;

	private String content;

	private String status;

	private String id;

	private int index;

}
