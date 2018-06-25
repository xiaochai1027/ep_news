package com.cfc.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KCSqlUtil {

    private static Logger Log = LoggerFactory.getLogger(KCSqlUtil.class);
    
    /**
     * 遍历list, 获取每个map元素的key对应的value, 返回所有value的列表
     * @param list
     * @param key
     * @param <T>
     * @return
     */
    public static <T> List<T> ListMap2List(List<Map<String, Object>> list
            , String key) {
        List<T> idList = new ArrayList<>();
        for (Map<String, Object> m : list) {
            idList.add((T) m.get(key));
        }
        
        return idList;
    }
    
    /**
     * (?,?..)
     * @param count
     * @return
     */
    public static String genSqlIn(int count) {
        StringBuilder sqlIn = new StringBuilder("(");
        
        for (int i = 0; i < count; i++) {
            sqlIn.append("?,");
        }
        
        sqlIn.deleteCharAt(sqlIn.length() - 1);
        sqlIn.append(")");
        
        return sqlIn.toString();
    }
    
    public static void mergeListMap(List<Map<String, Object>> desc
            , List<Map<String, Object>> src, String descKey
            , String srcKey) {
        for (Map<String, Object> d : desc) {
            Object o1 = d.get(descKey);
            for (Map<String, Object> s : src) {
                Object o2 = s.get(srcKey);
                
                if (o1 != null && o2 != null
                        && o1.equals(o2)) {
                    src.addAll(desc);
                }
            }
        }
    }
    
    public static Map<String, Object> rs2Map(ResultSet rs) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(md.getColumnLabel(i), rs.getObject(i));
            }
        } catch (Exception e) {
            Log.error("isError",e);
        }
        
        return map;
    }
}
