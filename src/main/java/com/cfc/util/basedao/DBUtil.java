package com.cfc.util.basedao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import static com.cfc.util.config.LogTag.LOG_SQL_COST_MASTER;


/**
 * @author yuanzhe(yuanzhe@lanxum.com)
 *
 * @date 2015年3月11日 上午11:47:31
 */
public class DBUtil {
	private static Logger Log = LoggerFactory.getLogger(DBUtil.class);
	public static <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
		List<T> list = new ArrayList<T>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		int row = 1;
		try {
            long start = System.currentTimeMillis();
			ps = BaseDao.query(sql, Arrays.asList(args));
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rowMapper.mapRow(rs, row));
				row++;
			}
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "query result count:" + (row - 1) + ", cost mill:" + (end - start));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(rs, ps);
		}
		
		return list;
	}
	
	public static String queryForString(String sql, Object... args) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		String ret = null;
		try {
			long start = System.currentTimeMillis();
			ps = BaseDao.query(sql, Arrays.asList(args));
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			}

			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "query cost mill:" + (end - start));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(rs, ps);
		}
		
		return ret;
	}
	
	public static int queryForInt(String sql, Object... args) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		int ret = -1;
		try {
			long start = System.currentTimeMillis();
			ps = BaseDao.query(sql, Arrays.asList(args));
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getInt(1);
			}
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "query cost mill:" + (end - start));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(rs, ps);
		}
		
		return ret;
	}

	public static Long queryForLong(String sql, Object... args) {

		ResultSet rs = null;
		PreparedStatement ps = null;
		Long ret = -1L;
		try {
			long start = System.currentTimeMillis();
			ps = BaseDao.query(sql, Arrays.asList(args));
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getLong(1);
			}
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "query cost mill:" + (end - start));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(rs, ps);
		}

		return ret;
	}

	private static void closeQuietly(ResultSet rs, PreparedStatement ps) {

		if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		if (ps != null) {
            try {
                ps.getConnection().close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
	}

	public static List<Map<String, Object>> queryForListMap(String sql, Object... args) {
		return query(sql, (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<String, Object>();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(md.getColumnLabel(i), rs.getObject(i));
            }
            return map;
        }, args);
	}
	
	public static Map<String, Object> queryForMap(String sql, Object... args) {
		final Map<String, Object> map = new HashMap<String, Object>();
		query(sql, (RowMapper<Void>) (rs, rowNum) -> {
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(md.getColumnLabel(i), rs.getObject(i));
            }
            return null;
        }, args);
		
		return map;
	}
	
	public interface RowMapper<T> {
		T mapRow(ResultSet rs, int rowNum) throws Exception;
	}
}
