package com.cfc.util.basedao;



import com.cfc.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.cfc.util.config.LogTag.LOG_SQL_COST_MASTER;


public class TransactionUtil {
	private static Logger Log = LoggerFactory.getLogger(TransactionUtil.class);
	private Connection mConn;
	
	private final int FAIL_CODE = -1;
	
	public TransactionUtil() throws SQLException {
		mConn = DBPool.getInstance().getConnection();
		mConn.setAutoCommit(false);
	}
	
	public int update(Object obj, String tableName, String where, Object ... whereArgs) {
		try {

			long start = System.currentTimeMillis();

			Pair<String, List<Object>> pair = BaseDao.genUpdateSql(obj, tableName, where, whereArgs);
			int row = BaseDao.exec(mConn, pair.first, pair.second, false);

			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "update cost mill:" + (end - start));
			return row;
		} catch (Exception e) {
			Log.error("isError",e);
		}
		return FAIL_CODE;
	}
	
	public int update2(Map<String, Object> obj, String tableName, String where, Object ... whereArgs) {
	    try {
	    	long start = System.currentTimeMillis();
	        Pair<String, List<Object>> pair = BaseDao.genUpdateSql2(obj, tableName, where, whereArgs);
	        int row = BaseDao.exec(mConn, pair.first, pair.second, false);
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "update cost mill:" + (end - start));
	        return row;
	    } catch (Exception e) {
	        Log.error("isError",e);
	    }
	    return FAIL_CODE;
	}
	
	@Deprecated
	public int insert(Object obj, String tableName) {
		try {
			long start = System.currentTimeMillis();
			Pair<String, List<Object>> pair = BaseDao.genInsertSql(Arrays.asList(obj), tableName, obj.getClass());
			int id = BaseDao.exec(mConn, pair.first, pair.second, true);
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "insert cost mill:" + (end - start));
			return id;
		} catch (Exception e) {
			Log.error("isError",e);
		}
		return FAIL_CODE;
	}
	
	public int insert2(Map<String, Object> obj, String tableName) {
	    try {
	    	long start = System.currentTimeMillis();
	        Pair<String, List<Object>> pair = BaseDao.genInsertSql2(Arrays.asList(obj), tableName);
	        int id = BaseDao.exec(mConn, pair.first, pair.second, true);
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "insert cost mill:" + (end - start));

	        return id;
	    } catch (Exception e) {
	        Log.error("isError",e);
	    }
	    return FAIL_CODE;
	}
	
	public int delete(String tableName,String where, Object ...args ) {

		long start = System.currentTimeMillis();
		int row = BaseDao.exec(mConn, "DELETE"
				+ " FROM "
				+ tableName
				+ " WHERE "
				+ where, Arrays.asList(args), false);
		long end = System.currentTimeMillis();
		Log.info("[delete] cost mill:" + (end - start));
		return row;
	}
	
	public int exec(String sql, Object ... args) {
		return BaseDao.exec(mConn, sql, Arrays.asList(args), false);
	}
	
	public boolean commit() {
		boolean ret = true;
		try {
			mConn.commit();
		} catch (SQLException e) {
			Log.error("isError",e);
			ret = false;
		} finally {
			if (mConn != null) {
				try {
					mConn.close();
					mConn = null;
				} catch (SQLException e) {
					Log.error("isError",e);
				}
			}
		}
		
		return ret;
	}
	
	public boolean rollback() {
		boolean ret = true;
		try {
		    if (mConn != null) {
	            mConn.rollback();
		    }
		} catch (SQLException e) {
			Log.error("isError",e);
			ret = false;
		} finally {
			if (mConn != null) {
				try {
					mConn.close();
					mConn = null;
				} catch (SQLException e) {
					Log.error("isError",e);
				}
			}
		}
		
		return ret;
	}
	
	public int deleteList (String table, String columnName, Object ... args) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM `").append(table).append("` WHERE ").append(columnName).append(" IN(");
        for (int i = 0; i < args.length; i++) {
            sql.append("?,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        
        int row = -1;
        try {
        	long start = System.currentTimeMillis();
            row = exec(sql.toString(), args);
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "delete cost mill:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        }
        
        return row;
    }
	
    public int updateColumn(String columnName, String columnValue, String tableName, String where, Object ... whereArgs) {
        try {
        	long start = System.currentTimeMillis();
            Pair<String, List<Object>> pair = BaseDao.genUpdateColumnSql(columnName, columnValue, tableName, where, whereArgs);
            int row = BaseDao.exec(mConn, pair.first, pair.second, false);
			long end = System.currentTimeMillis();
			Log.info(LOG_SQL_COST_MASTER + "update cost mill:" + (end - start));
            return row;
        } catch (Exception e) {
            Log.error("isError",e);
        }
        return FAIL_CODE;
    }

	@Override
	protected void finalize() {
		try {
			super.finalize();
			if (mConn != null && !mConn.isClosed()) {
				Log.warn("connection is not closed in Thread-" + Thread.currentThread().getId());
			}
		} catch (Throwable e) {
			Log.error("isError",e);
		}
	}
	
	
}
