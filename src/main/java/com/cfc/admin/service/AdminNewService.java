package com.cfc.admin.service;

import com.cfc.commons.Tables;
import com.cfc.util.KCSqlUtil;
import com.cfc.util.KCUtil;
import com.cfc.util.basedao.BaseDao;
import com.cfc.util.basedao.DBUtil;
import com.cfc.util.model.PageModel;

import java.util.List;
import java.util.Map;

/**
 * author fangchen
 * date  2018/6/25 下午4:54
 */
public class AdminNewService {


    public static List<Map<String,Object>> list(PageModel pageModel){
       return DBUtil.queryForListMap("select * from `new` order by ctime desc limit ?,?", pageModel.getStart(), pageModel.getCount());
    }

    public static Integer count(){
        return DBUtil.queryForInt("select count(*) from `new`");
    }

    public static List<Map<String,Object>> listBykey(PageModel pageModel,String key){
        return DBUtil.queryForListMap("select * from `new` where  title like ? order by ctime desc limit ?,?","%"+key+"%", pageModel.getStart(), pageModel.getCount());
    }

    public static Integer countBykey(String key){
        return DBUtil.queryForInt("select count(*) from `new` where  title like ?",  "%"+key+"%");
    }


    public static Map<String, Object> get(Integer id) {
        return DBUtil.queryForMap("select * from `new` where id = ?", id);
    }

    public static String createNew(Map<String, Object> params) {
        Integer id = null;
        if (params.get("id") != null) {
            id = Integer.parseInt(params.get("id") + "");
        }
        if (id == null) {
            BaseDao.insert2(params, Tables.NEW);
        } else {
            BaseDao.update2(params, Tables.NEW, " id = ? ", id);
        }
        return "success";
    }

    public static String delete(Integer id) {
        BaseDao.delete(Tables.NEW, " id = ? ", id);
        return "success";
    }


}
