package com.cfc.admin.webmagic;

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

import static com.cfc.admin.webmagic.SpecialtyPageOneProcessor.buildRequest;


/**
 * @author fangchen
 * @date 2018/10/9 上午11:41
 */
public class SpecialtyPageThreeProcessor implements PageProcessor {

    private static String url = "https://yz.chsi.com.cn/zyk/specialityCategory.do";
    private static boolean stop = false;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private static Map<String, String> school = new ConcurrentHashMap<>(1024);

    @Override
    public void process(Page page) {
        List<String> list = page.getHtml().$("li", "id").all();
        Spider s = null;
        s = Spider.create(new SpecialtyPageLastProcessor());
        //设置所有请求
        for (String str : list) {
            //设置POST参数
            List<NameValuePair> nvs = new ArrayList<NameValuePair>();
            nvs.add(new BasicNameValuePair("method", "subCategoryXk"));
            nvs.add(new BasicNameValuePair("key", str));
            s.addRequest(buildRequest(nvs));
        }
        s.thread(5).run();

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //设置POST请求
        Request request = new Request(url);
        //只有POST请求才可以添加附加参数
        request.setMethod(HttpConstant.Method.POST);

        //设置POST参数
        List<NameValuePair> nvs = new ArrayList<NameValuePair>();
        nvs.add(new BasicNameValuePair("method", "topCategory"));

        //转换为键值对数组
        NameValuePair[] values = nvs.toArray(new NameValuePair[] {});

        //将键值对数组添加到map中
        Map<String, Object> params = new HashMap<String, Object>();
        //key必须是：nameValuePair
        params.put("nameValuePair", values);

        //设置request参数
        request.setExtras(params);

        Spider s = null;
        s = Spider.create(new SpecialtyPageThreeProcessor()).addRequest(request);

        s.thread(5).run();

    }

    public static void getFirst(){

    }
}
