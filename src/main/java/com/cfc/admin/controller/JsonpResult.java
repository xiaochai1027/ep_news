package com.cfc.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.cfc.util.basedao.BaseDao;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * @author fangchen
 * @date 2018/10/8 下午6:20
 */
public class JsonpResult {

    private final static String specialname = "";

    public static void main(String[] args) throws Exception{

        getSpecial();
//        getSchool();

    }

    public static Map getSchool() throws Exception {

        List<Map> school = new ArrayList<>();
        Integer page = 0;
        while (true) {
            page++;
            List list = getSchoolList(page.toString());
            if (list == null) {
                break;
            }
            school.addAll(list);
        }

        Map<String, String> data = new HashMap<>();
        for (Map map : school) {
            String province = (String) map.get("province");
            String name = (String) map.get("schoolname");
            data.put(name, province);
        }
        System.out.println(data);
        System.out.println(school.size());
        System.out.println(data.size());
        int num = createCollege(data);
        System.out.println("putNum:" + num);
        return data;
    }

    public static Collection<String> getSpecial() throws Exception {
        List<Map> special = new ArrayList<>();
        Integer page = 0;
        while (true) {
            page++;
            List list = getSpecialname(page.toString());
            if (list == null) {
                break;
            }
            special.addAll(list);
        }

        Set<String> data = new HashSet<>();
        for (Map map : special) {
            String specialname = (String) map.get("specialname");
            data.add(specialname);
        }
        int num = createMajor(data);
//        System.out.println(data);
        System.out.println(special.size());
        System.out.println(data.size());
        System.out.println(num);
        return data;
    }


    private static List getSchoolList(String page) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //HttpGet httpGet = new HttpGet("http://test.laizi.cn:8080/114/bm/accountLogin");
            String url = "https://data-gkcx.eol.cn/soudaxue/queryschool.html?messtype=jsonp&callback=jQuery183020606668599524758_1538994246496&province=&schooltype=&page="+page+"&size=30&keyWord1=&schoolprop=&schoolflag=&schoolsort=&schoolid=&_=1538994247091";
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("referer", "https://gkcx.eol.cn/soudaxue/queryschool.html");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String str = null;
            try {
                HttpEntity entity = response.getEntity();
                //打印目标网站输出内容
//                System.out.println(EntityUtils.toString(entity));
                str = EntityUtils.toString(entity);
                int start = str.indexOf("{");
                int end = str.lastIndexOf(")");
                str = str.substring(start, end);
//                System.out.println(str);
                EntityUtils.consume(entity);
                return JSONObject.parseObject(str).getJSONArray("school");
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static List getSpecialname(String page) throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //HttpGet httpGet = new HttpGet("http://test.laizi.cn:8080/114/bm/accountLogin");
            String url = "https://data-gkcx.eol.cn/soudaxue/queryspecialty.html?messtype=jsonp&callback=jQuery18308623620798327076_1539050698091&zycengci=&zytype=&keyWord2=&page="+page+"&size=10&_=1539050698317";
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("referer", "https://gkcx.eol.cn/soudaxue/queryschool.html");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            String str = null;
            try {
                HttpEntity entity = response.getEntity();
                //打印目标网站输出内容
//                System.out.println(EntityUtils.toString(entity));
                str = EntityUtils.toString(entity);
                int start = str.indexOf("{");
                int end = str.lastIndexOf(")");
                str = str.substring(start, end);
//                System.out.println(str);
                EntityUtils.consume(entity);
                return JSONObject.parseObject(str).getJSONArray("school");
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static int createCollege(Map<String, String> map) {
        int i = 0;
        for (Map.Entry<String, String> college : map.entrySet()) {
            String c = college.getKey();
            String p = college.getValue();
            Map clo = new HashMap();
            clo.put("name", c.trim());
            clo.put("provinceName", p.trim());
            int ref = BaseDao.insertOnDuplicateKey(clo, "college");
            if (ref > 0) {
                i++;
            }
        }
        return i;
    }

    public static int createMajor(Collection<String> list) {
        int i = 0;
        for (String str : list) {
            Map clo = new HashMap();
            clo.put("name", str.trim());
            int ref = BaseDao.insertOnDuplicateKey(clo, "major");
            if (ref > 0) {
                i++;
            }
        }
        return i;
    }

}
