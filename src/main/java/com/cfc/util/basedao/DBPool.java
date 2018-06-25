package com.cfc.util.basedao;

import com.cfc.util.Config;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBPool {

	private Logger Log = LoggerFactory.getLogger(DBPool.class);
	private static DBPool dbPool;
	private ComboPooledDataSource dataSource;

	static {
		dbPool = new DBPool();
	}

	DBPool() {
		try {
			Properties p = Config.load("/jdbc.properties");
			String username = p.getProperty("username");
			String password = p.getProperty("password");
			String jdbcUrl = p.getProperty("url");
			
			dataSource = new ComboPooledDataSource();
			dataSource.setUser(username);
			dataSource.setPassword(password);
			dataSource.setJdbcUrl(jdbcUrl);
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			// 设置初始连接池的大小！
			dataSource.setInitialPoolSize(1);
			// 设置连接池的最小值！ 
			dataSource.setMinPoolSize(1);
			// 设置连接池的最大值！ 
			dataSource.setMaxPoolSize(5000);
			// 设置连接池中的最大Statements数量！
			dataSource.setMaxStatements(0);
			// 设置连接池的最大空闲时间！
			dataSource.setMaxIdleTime(20);
			dataSource.setCheckoutTimeout(1000);
			dataSource.setMaxConnectionAge(20);
			dataSource.setStatementCacheNumDeferredCloseThreads(1);
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
	}

	public static DBPool getInstance() {
		return dbPool;
	}


	final Connection getConnection() {
		try {
		    Log.info("current conn size:" + dataSource.getNumBusyConnections());
			return dataSource.getConnection();
		} catch (SQLException e) {
			Log.info("qq无法从数据源获取连接:" + e.getErrorCode() + "rr" + e.toString());
			throw new RuntimeException("无法从数据源获取连接 ", e);
		}
	}

}
