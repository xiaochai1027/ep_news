package com.cfc.util.basedao;


import com.cfc.util.Pair;
import com.cfc.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.cfc.util.config.LogTag.LOG_SQL_COST_MASTER;


/**
 * @author yuanzhe(yuanzhe@lanxum.com)
 * @date 2015年3月9日 下午5:38:32
 */
public class BaseDao {
    private static final Logger Log = LoggerFactory.getLogger(BaseDao.class);
    private static Map<String, Method> sMethodCache = new ConcurrentHashMap<String, Method>();
    private static Map<String, Field[]> sFieldsCache = new ConcurrentHashMap<String, Field[]>();

    @Deprecated
    public static int update(Object obj, String tableName, String where, Object... whereArgs) {
        int affective = -1;
        if (obj == null) {
            return affective;
        }

        try {
            long start = System.currentTimeMillis();
            Pair<String, List<Object>> pair = genUpdateSql(obj, tableName, where, whereArgs);
            affective = exec(null, pair.first, pair.second, false);

            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "update cost mill:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        }

        return affective;
    }

    public static int update2(Map<String, Object> obj
            , String tableName, String where, Object... whereArgs) {
        int affective = -1;
        if (obj == null || obj.isEmpty()) {
            return affective;
        }

        try {
            long start = System.currentTimeMillis();

            Pair<String, List<Object>> pair = genUpdateSql2(obj, tableName, where, whereArgs);
            affective = exec(null, pair.first, pair.second, false);

            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "update cost mill:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        }

        return affective;
    }

    /**
     * obj是一个bean，里面的字段与数据库一一对应,忽略id字段,值为null的忽略
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Deprecated
    public static int insert(Object obj, String tableName) {
        ArrayList list = new ArrayList();
        list.add(obj);
        return insert(list, tableName, obj.getClass());
    }

    /**
     * obj是一个bean，里面的字段与数据库一一对应,忽略id字段,值为null的忽略
     */
    @Deprecated
    public static <T> int insert(List<T> list, String tableName, Class<T> clazz) {
        if (list.isEmpty()) {
            return -1;
        }

        int retId = -1;

        try {
            long start = System.currentTimeMillis();
            Pair<String, List<Object>> pair = genInsertSql(list, tableName, clazz);
            retId = exec(null, pair.first, pair.second, list.size() == 1);

            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "insert cost mill:" + (end - start));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            Log.error("isError",e);
        }
        return retId;
    }

    public static int insertOnDuplicateKey(Map<String, Object> obj, String tableName) {
        List<Map<String, Object>> list = Arrays.asList(obj);
        if (list.isEmpty()) {
            return -1;
        }

        int retId = -1;

        try {

            long start = System.currentTimeMillis();

            Pair<String, List<Object>> pair = genInsertSql2(list, tableName);

            StringBuilder sb = new StringBuilder(pair.first);
            sb.append(" ON DUPLICATE KEY UPDATE ");

            for (String k : obj.keySet()) {
                sb.append(k).append("=?,");
                pair.second.add(obj.get(k));
            }
            sb.deleteCharAt(sb.length() - 1);
            retId = exec(null, sb.toString(), pair.second, list.size() == 1);

            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "insert cost mill:" + (end - start));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            Log.error("isError",e);
        }
        return retId;
    }

    public static int insert2(Map<String, Object> obj, String tableName) {
        return insert2(Arrays.asList(obj), tableName);
    }

    /**
     * INSERT INTO #tableName#(map1.keys) VALUES(map1.values),(map2.values),(map3.values)...
     *
     * @param list
     * @param tableName
     * @return
     */
    public static int insert2(List<Map<String, Object>> list, String tableName) {
        if (list.isEmpty()) {
            return -1;
        }

        int retId = -1;

        try {
            long start = System.currentTimeMillis();

            Pair<String, List<Object>> pair = genInsertSql2(list, tableName);
            retId = exec(null, pair.first, pair.second, list.size() == 1);

            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "insert cost mill:" + (end - start));
        } catch (RuntimeException e) {
               throw e;

        } catch (Exception e) {
            Log.error("isError",e);
        }
        return retId;
    }


    public static int insert3(List<Map<String, Object>> list, String tableName, boolean insertId) {
        if (list.isEmpty()) {
            return -1;
        }

        int retId = -1;
        try {
            long start = System.currentTimeMillis();

            Pair<String, List<Object>> pair = genInsertSql3(list, tableName, insertId);
            retId = exec(null, pair.first, pair.second, list.size() == 1);


            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "insert cost mill:" + (end - start));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            Log.error("isError",e);
        }
        return retId;
    }


    public static <T> List<T> queryList(String sql, Class<T> clazz, Object... args) {
        return queryList(sql, Arrays.asList(args), clazz);
    }

    public static <T> List<T> queryList(String sql, List<Object> args, Class<T> clazz) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<T> list = new ArrayList<T>();
        try {

            long start = System.currentTimeMillis();
            ps = query(sql, args);
            rs = ps.executeQuery();

            String key = clazz.getName();
            Field[] fields = sFieldsCache.get(key);

            if (fields == null) {
                fields = clazz.getDeclaredFields();
                sFieldsCache.put(key, fields);
            }
            while (rs.next()) {
                T obj = clazz.newInstance();

                for (Field f : fields) {
                    try {
                        Object val = rs.getObject(f.getName());
                        setValue(obj, f.getName(), val, f.getType());
                    } catch (SQLException ignored) {

                    }
                }

                list.add(obj);
            }
            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "query cost mill:" + (end - start));
        } catch (Exception e) {
            Log.warn(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                Log.warn(e.getMessage());
            }

            try {
                if (ps != null) {
                    ps.getConnection().close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return list;
    }


    public static <T> T query(String sql, Class<T> clazz, Object... args) {
        return query(sql, Arrays.asList(args), clazz);
    }

    public static <T> T query(String sql, List<Object> args, Class<T> clazz) {
        Log.info("query sql:" + sql);
        Log.info("query args:" + args);
        ResultSet rs = null;
        PreparedStatement ps = null;
        T obj = null;
        try {
            long start = System.currentTimeMillis();
            ps = query(sql, args);
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    try {
                        setValue(obj, f.getName(), rs.getObject(f.getName()), f.getType());
                    } catch (SQLException ignored) {
                    }
                }
            }
            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "query cost mill:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                Log.error("isError",e);
            }
            try {
                if (ps != null) {
                    ps.getConnection().close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return obj;
    }

    /**
     * 此方法中不可关闭rs
     *
     * @param sql
     * @param args
     * @return
     */
    public static PreparedStatement query(String sql, List<Object> args) {
        return query(false, sql, args);
    }

    /**
     * 此方法中不可关闭rs
     *
     * @param sql
     * @param args
     * @return
     */
    public static PreparedStatement query(boolean slave, String sql, List<Object> args) {
        Log.info("query sql:" + sql);
        Log.info("query args:" + args);
        PreparedStatement ps = null;
        try {
            Connection conn = DBPool.getInstance().getConnection();
            ps = genPs(conn, sql, args);
        } catch (Exception e) {
            Log.error("isError",e);
        }
        return ps;
    }


    public static int delete(String table, String where, Object... args) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM `").append(table).append("` WHERE ").append(where);
        int row = -1;
        try {
            long start = System.currentTimeMillis();
            row = exec(null, sql.toString(), Arrays.asList(args), false);
            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "delete cost mill:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        }

        return row;
    }

    public static int deleteList(String table, String columnName, Object... args) {
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
            row = exec(null, sql.toString(), Arrays.asList(args), false);
            long end = System.currentTimeMillis();
            Log.info(LOG_SQL_COST_MASTER + "delete cost mill:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        }

        return row;
    }

    /**
     * 外部传入的conn就不关闭，自己生成的就关闭
     *
     * @param conn
     * @param sql
     * @param args
     * @param lastInsertId
     * @return
     */
    public static int exec(Connection conn, String sql, List<Object> args, boolean lastInsertId) {
        long start = System.currentTimeMillis();
        Log.info("sql:" + sql);
        Log.info("args:" + args);
        int affective = -1;
        PreparedStatement ps = null;
        Connection connection = conn;
        try {
            if (connection == null) {
                connection = DBPool.getInstance().getConnection();
            }

            ps = genPs(connection, sql, args);
            affective = ps.executeUpdate();

            if (lastInsertId) {
                ResultSet rs = connection.createStatement().executeQuery("SELECT LAST_INSERT_ID() AS id");
                if (rs.next()) {
                    affective = rs.getInt("id");
                }
            }
            long end = System.currentTimeMillis();
            Log.info("exec mills:" + (end - start));
        } catch (Exception e) {
            Log.error("isError",e);
        } finally {
            //表示外部传入的
            if (conn == null && connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    Log.error("isError",e);
                }
            }
        }

        return affective;
    }

    public static PreparedStatement genPs(Connection conn, String sql, List<Object> args) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            if (args == null || args.isEmpty()) {
                return ps;
            }

            for (int i = 0; i < args.size(); i++) {
                Object obj = args.get(i);
                ps.setObject(i + 1, obj);
            }
        } catch (Exception e) {
            Log.warn(e.getMessage());
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e2) {
                    Log.error("isError",e2);
                }
            }
        }

        return ps;
    }


    public static Pair<String, List<Object>> genInsertSql2(List<Map<String, Object>> list, String tableName) throws Exception {
        return genInsertSql3(list, tableName, false);
    }

    public static Pair<String, List<Object>> genInsertSql3(List<Map<String, Object>> list, String tableName, boolean insertId) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO `").append(tableName).append("`(");
        Set<String> keySet = list.get(0).keySet();
        //生成INSERT INTO table(XXX,XXX)
        for (String fieldName : keySet) {

            if (!insertId) {
                if ("id".equals(fieldName)
                        || "serialVersionUID".equals(fieldName)) {
                    continue;
                }
            }


            sql.append("`").append(fieldName).append("`").append(",");
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(" )");

        //生成VALUES和args
        StringBuilder values = new StringBuilder("VALUES");
        List<Object> argList = new ArrayList<Object>();

        for (Map<String, Object> m : list) {
            values.append("(");

            Set<String> tmpKeySet = m.keySet();
            for (String fieldName : tmpKeySet) {
                if (!insertId) {
                    if ("id".equals(fieldName)
                            || "serialVersionUID".equals(fieldName)) {
                        continue;
                    }

                }
                Object arg = m.get(fieldName);
                if (arg == null && "ctime".equals(fieldName)) {
                    //生成ctime的值
                    arg = System.currentTimeMillis();
                    m.put(fieldName, arg);
                }

                argList.add(arg);

                values.append("?").append(",");
            }
            values.deleteCharAt(values.length() - 1);
            values.append(")").append(",");
        }

        values.deleteCharAt(values.length() - 1);
        sql.append(" ").append(values);
        String sqlStr = sql.toString();
        return new Pair<>(sqlStr, argList);
    }

    @Deprecated
    public static <T> Pair<String, List<Object>> genInsertSql(List<T> list, String tableName, Class clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder sql = new StringBuilder("INSERT INTO `").append(tableName).append("`(");


        //生成INSERT INTO table(XXX,XXX)
        for (Field field : fields) {

            String fieldName = field.getName();
            if ("id".equals(fieldName)
                    || "serialVersionUID".equals(fieldName)) {
                continue;
            }

            sql.append("`").append(fieldName).append("`").append(",");
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(" )");

        //生成VALUES和args
        StringBuilder values = new StringBuilder("VALUES");
        List<Object> argList = new ArrayList<Object>();

        for (T t : list) {
            values.append("(");
            for (Field field : fields) {

                String fieldName = field.getName();
                if ("id".equals(fieldName)
                        || "serialVersionUID".equals(fieldName)) {
                    continue;
                }

                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);

                String key = t.getClass().getName() + "." + getMethodName;
                Method method = sMethodCache.get(key);
                if (method == null) {
                    method = clazz.getMethod(getMethodName);
                    sMethodCache.put(key, method);
                }

                Object arg = method.invoke(t);

                if (arg == null && "ctime".equals(fieldName)) {
                    //生成ctime的值


                    arg = System.currentTimeMillis();
                    setValue(t, fieldName, arg, Long.class);

                }

                argList.add(arg);

                values.append("?").append(",");
            }
            values.deleteCharAt(values.length() - 1);
            values.append(")").append(",");
        }

        values.deleteCharAt(values.length() - 1);
        sql.append(" ").append(values);
        String sqlStr = sql.toString();

        return new Pair<String, List<Object>>(sqlStr, argList);
    }

    @Deprecated
    public static Pair<String, List<Object>> genUpdateSql(Object obj, String tableName, String where, Object... whereArgs) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sql = new StringBuilder("UPDATE ").append(
                tableName).append(" SET ");

        List<Object> argList = new ArrayList<Object>();

        boolean empty = true;
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("id".equals(fieldName)
                    || "serialVersionUID".equals(fieldName)) {
                continue;
            }

            String getMethodName = "get"
                    + fieldName.substring(0, 1).toUpperCase()
                    + fieldName.substring(1);

            Method method = obj.getClass().getMethod(getMethodName);
            Object arg = method.invoke(obj);


            if (arg == null) {
                continue;
            }

            empty = false;

            argList.add(arg);

            sql.append("`").append(fieldName).append("`").append("=?,");

        }

        if (empty) {
            Log.warn("obj is empty");
            return null;
        }

        sql.deleteCharAt(sql.length() - 1);

        if (!TextUtils.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }
        argList.addAll(Arrays.asList(whereArgs));
        String sqlStr = sql.toString();

        return new Pair<>(sqlStr, argList);
    }

    public static Pair<String, List<Object>> genUpdateSql2(Map<String, Object> obj, String tableName, String where, Object... whereArgs) throws Exception {
        StringBuilder sql = new StringBuilder("UPDATE `").append(
                tableName).append("` SET ");

        if (obj.isEmpty()) {
            Log.warn("obj is empty");
            return null;
        }

        List<Object> argList = new ArrayList<Object>();

        for (String field : obj.keySet()) {

            Object arg = obj.get(field);

            argList.add(arg);

            sql.append("`").append(field).append("`").append("=?,");
        }

        sql.deleteCharAt(sql.length() - 1);

        if (!TextUtils.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }
        argList.addAll(Arrays.asList(whereArgs));
        String sqlStr = sql.toString();

        return new Pair<String, List<Object>>(sqlStr, argList);
    }

    public static Pair<String, List<Object>> genUpdateColumnSql(String columnName, String columnValue, String tableName,
                                                                String where, Object... whereArgs) throws Exception {
        if (TextUtils.isEmpty(columnName)) {
            Log.warn("columnName is empty");
            return null;
        }
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        columnValue = columnValue == null ? "null" : columnValue;
        sql.append("`").append(columnName).append("`").append("=").append(columnValue);

        if (!TextUtils.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }
        List<Object> argList = new ArrayList<Object>();
        argList.addAll(Arrays.asList(whereArgs));
        String sqlStr = sql.toString();

        return new Pair<>(sqlStr, argList);
    }

    private static void setValue(Object obj, String fieldName, Object value, Class... methodArgs) throws Exception {
        String setMethodName = "set"
                + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);

        String key = obj.getClass().getName() + "." + setMethodName;
        Method setMethod = sMethodCache.get(key);
        if (setMethod == null) {
            setMethod = obj.getClass().getMethod(setMethodName, methodArgs);
            sMethodCache.put(key, setMethod);
        }

        setMethod.invoke(obj, value);
    }


    public static void main(String[] args) {

    }


}
