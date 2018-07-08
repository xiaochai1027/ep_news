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

    public static Map<String, List<Map<String,Object>>> homePage(){
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        //1荣誉榜 2热门资讯 3个人专栏 4合作专栏
        List<Map<String, Object>> honor = DBUtil.queryForListMap("select * from `new` where type = 1 order by indexs desc limit 0,4");
        List<Map<String, Object>> news = DBUtil.queryForListMap("select * from `new` where type = 2 order by indexs desc limit 0,10");
        List<Map<String, Object>> single = DBUtil.queryForListMap("select * from `new` where type = 3 order by indexs desc limit 0,10");
        List<Map<String, Object>> cp = DBUtil.queryForListMap("select * from `new` where type = 4 order by indexs desc limit 0,10");
        map.put("honor", honor);
        map.put("news", news);
        map.put("single", single);
        map.put("cp", cp);
        return map;
    }

    public static Map<String, Object> detail(Integer id) {
        Map<String,Object> news = DBUtil.queryForMap("select * from `new` where id = ?", id);
        return news;
    }

}