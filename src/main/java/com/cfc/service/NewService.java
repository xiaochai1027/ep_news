package com.cfc.service;

import com.cfc.util.basedao.DBUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author fangchen
 * date  2018/7/5 上午10:42
 */
public class NewService {

    public static Map<Integer, Map<String, Object>> homePage(){
        List<Map<String, Object>> news = DBUtil.queryForListMap("select * from `new` order by index desc group by type ");
        Map<Integer, Map<String, Object>> map = new HashMap<>();
        news.parallelStream().forEach(x -> {
            map.put((Integer)x.get("type"), x);
        });
        return map;
    }

}
