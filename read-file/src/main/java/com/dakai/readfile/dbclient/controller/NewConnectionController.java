package com.dakai.readfile.dbclient.controller;

import java.util.List;
import java.util.Map;

import com.dakai.readfile.dbclient.act.AbstractDBPoolsManager;
import com.dakai.readfile.dbclient.entity.DbType;
import com.dakai.readfile.dbclient.entity.Result;
import com.dakai.readfile.dbclient.err.PoolException;
import com.dakai.readfile.dbclient.iter.IDBPoolsManager;
import com.dakai.readfile.dbclient.param.DbCreateParam;
import com.dakai.readfile.dbclient.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newcon")
public class NewConnectionController {

	private final static Logger log = LoggerFactory.getLogger(NewConnectionController.class);

	@Autowired
	IDBPoolsManager dbPoolManager;

	@PostMapping("/create")
	public Map<String, Object> create(@ModelAttribute("param") DbCreateParam param){
		log.debug("接到创建数据库连接池请求");
		log.debug("参数： " + param);


		Result map = ResultUtils.simpleResult();

		try {
			Map<String, Object> returnMap = dbPoolManager.setDbPool(param);
			trySetProperty(returnMap, map, "tip");
			trySetProperty(returnMap, map, "token");
			trySetProperty(returnMap, map, "dbId");
		} catch (PoolException e) {
			map.setError(e.getMessage());
			log.error(e.getMessage() + param);
			e.printStackTrace();
		}

		log.debug("请求结束并返回结果");

		return map.getResult();
	}

	@GetMapping("/getUserUseing")
	public Map<String, List<DbCreateParam>> getUserUseing(){
		return ((AbstractDBPoolsManager)dbPoolManager).getUserUseing();
	}

	/**
	 * 设置返回结果，
	 * 如果returnMap包含名为name的属性，则把名为name的值插入map
	 * @param returnMap
	 * @param map
	 * @param name
	 */
	private void trySetProperty(Map<String, Object> returnMap, Result map, String name) {
		if(returnMap.containsKey(name)) {
			map.setProperty(name, returnMap.get(name));
		}
	}

}
