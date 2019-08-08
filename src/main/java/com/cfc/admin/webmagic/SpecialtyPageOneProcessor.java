package com.cfc.admin.webmagic;

import com.cfc.admin.controller.JsonpResult;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fangchen
 * @date 2018/10/9 上午11:41
 */
public class SpecialtyPageOneProcessor implements PageProcessor {

    private static String url = "https://yz.chsi.com.cn/zyk/specialityCategory.do";
    private static boolean stop = false;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private static Map<String, String> school = new ConcurrentHashMap<>(1024);

    @Override
    public void process(Page page) {

        List<String> list = page.getHtml().$("li", "id").all();
        page.putField("list", list);
        List<String> lists = page.getResultItems().get("list");
        if (lists == null || lists.isEmpty()) {
            //skip this page
            page.setSkip(true);
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Request> requests = new ArrayList();
        Spider s = null;
        s = Spider.create(new SpecialtyPageTwoProcessor());
        //设置所有请求
        for (String str : list) {
            //设置POST参数
            List<NameValuePair> nvs = new ArrayList<NameValuePair>();
            nvs.add(new BasicNameValuePair("method", "subCategoryMl"));
            nvs.add(new BasicNameValuePair("key", str));
//            page.addTargetRequest(buildRequest(nvs));
            requests.add(buildRequest(nvs));
        }
        Request[] r = new Request[requests.size()];
        s.addRequest(requests.toArray(r));
        s.thread(5).run();
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {


        //设置POST参数
        List<NameValuePair> nvs = new ArrayList<NameValuePair>();
        nvs.add(new BasicNameValuePair("method", "topCategory"));
        Spider s = null;
        s = Spider.create(new SpecialtyPageOneProcessor()).addRequest(buildRequest(nvs));

        s.thread(5).run();

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
//                System.out.println(SpecialtyPageLastProcessor.getSpecialty());
                int num = JsonpResult.createMajor(SpecialtyPageLastProcessor.getSpecialty());
                System.out.println(SpecialtyPageLastProcessor.getSpecialty().size());
                System.out.println(num);

            }
        });

    }

    public static Request buildRequest(List<NameValuePair> nvs){
        //设置POST请求
        Request request = new Request(url+"?r="+Math.random());
        //只有POST请求才可以添加附加参数
        request.setMethod(HttpConstant.Method.POST);

        //转换为键值对数组
        NameValuePair[] values = nvs.toArray(new NameValuePair[] {});

        //将键值对数组添加到map中
        Map<String, Object> params = new HashMap<String, Object>();
        //key必须是：nameValuePair
        params.put("nameValuePair", values);

        //设置request参数
        request.setExtras(params);
        return request;
    }


}
