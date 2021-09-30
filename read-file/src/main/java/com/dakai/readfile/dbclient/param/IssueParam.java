package com.dakai.readfile.dbclient.param;

import com.dakai.readfile.dbclient.iter.IToken;
import lombok.Data;

@Data
public class IssueParam implements IToken {

	private String token;

	private String title;

	private String content;

}
