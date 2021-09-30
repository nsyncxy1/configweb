package com.dakai.readfile.dbclient.data;

import com.dakai.readfile.dbclient.entity.UserData;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存在服务端的用户数据
 * @author yujie
 * 2019年4月10日 下午5:28:03
 */
@Component
public class UserCacheData {

	private Map<String, UserData> reposity = new ConcurrentHashMap<String, UserData>();

	public UserData getUserData(String token) {
		return reposity.get(token);
	}

	public void setUserData(String token, UserData data) {
		reposity.put(token, data);
	}

}
