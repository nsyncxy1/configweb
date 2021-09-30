package com.dakai.readfile.dbclient.act;

import com.dakai.readfile.dbclient.err.PoolException;
import com.dakai.readfile.dbclient.iter.IDbConfig;
import com.dakai.readfile.dbclient.iter.IDbCreate;
import com.dakai.readfile.dbclient.iter.IDbPool;
import com.dakai.readfile.dbclient.utils.ApplicationContextUtils;
import com.dakai.readfile.dbclient.utils.DbTypeUtils;
import com.dakai.readfile.webshell.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认数据库连接池管理工具
 * @author jayu
 *
 */

@Component
public class DefaultDBPoolsManager extends AbstractDBPoolsManager {

	private Map<String, IDbPool> urlReposity = new ConcurrentHashMap<String, IDbPool>();

	private static final Logger log = LoggerFactory.getLogger(DefaultDBPoolsManager.class);

	// 参数检查map
	private static final Map<String, String> paramCheckMap = new HashMap<String, String>();

	static {
		paramCheckMap.put("redisuserName", "no");  // 不对redis的username进行检查
		paramCheckMap.put("mongodbuserName", "no");  // 不对mongodb的username进行检查
		paramCheckMap.put("mongodbpassword", "no");  // 不对mongodb的password进行检查
		paramCheckMap.put("redispassword", "no");    // 不对redis的密码进行检查
	}

	@Override
	protected IDbPool createPool(IDbConfig config) throws PoolException {
		checkParam(config);
		String urlId = getUrlId(config);
		if(urlReposity.containsKey(urlId)) {
			//验证密码
			checkPassword(config, urlId);
			log.debug("仓库中已经包括数据库连接池: id: " + urlId);
			return urlReposity.get(urlId);
		}
//		ApplicationContext ac = ApplicationContextUtils.getAc();
		ApplicationContext ac = SpringUtils.getContext();
		if(null == ac) {
			throw new PoolException("applicationContext为空");
		}
		log.debug("type: " + config.getType().getCreaterName());
		IDbCreate creater = ac.getBean(config.getType().getCreaterName(), IDbCreate.class);
		if(null == creater) {
			throw new PoolException("没有找到对应的数据库连接池创建者");
		}
		IDbPool pool = creater.create(config);
		if(null != pool) {
			urlReposity.put(urlId, pool);
		}
		return pool;
	}

	//检查参数
	private void checkParam(IDbConfig config) throws PoolException {
		log.debug("开始检查参数");
		StringBuilder sb = new StringBuilder();
		if(null == config.getType()) {
			sb.append("数据库类型dbType不能为空，");
		}else if(!DbTypeUtils.containValue(config.getType())){
			sb.append("数据库类型dbType值不存在，");
		}
		if(null == config.getHost()) {
			sb.append("数据库地址不能为空，");
		}
		if(0 == config.getPort()) {
			sb.append("端口port不能为空，");
		}
		if(needCheck(config.getType().getName(), "name")) {
			if(null == config.getName()) {
				sb.append("数据库名name不能为空，");
			}
		}
		if(needCheck(config.getType().getName(), "userName")) {
			if(null == config.getUserName()) {
				sb.append("用户名不能为空，");
			}
		}
		if(needCheck(config.getType().getName(), "password")) {
			if(null == config.getPassword()) {
				sb.append("密码不能为空");
			}
		}
		if(sb.length() > 0) {
			throw new PoolException(sb.toString());
		}
	}

	// 是否需要对改类型的改属性进行检验
	private boolean needCheck(String name, String type) {
		if(paramCheckMap.containsKey(name + type)) {
			return false;
		}
		return true;
	}

}
