package com.cfc.util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 可用Properties文件配置的Contants基类
 * <p/>
 * 子类可如下编写
 * 
 * <pre>
 * public class Constants extends ConfigurableContants {
 * 	static {
 * 		init(&quot;system.properties&quot;);
 * 	}
 * 
 * 	public final static String ERROR_BUNDLE_KEY = getProperty(&quot;constant.error_bundle_key&quot;, &quot;default&quot;);
 * 
 * }
 * </pre>
 * 
 */
public class ConfigurableConstants {

	protected static Properties p = new Properties();

	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableConstants.class.getResourceAsStream(propertyFileName);
			if (in != null) {
				p.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

}
