package com.cfc.admin.webmagic;

import com.cfc.admin.controller.JsonpResult;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fangchen
 * @date 2018/10/9 上午11:41
 */
public class SchoolPageProcessor implements PageProcessor {

    private static String url = "https://yz.chsi.com.cn/sch/search.do?start=";
    private static boolean stop = false;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    private static Map<String, String> school = new ConcurrentHashMap<>(1024);

    @Override
    public void process(Page page) {

        List<Selectable> selectables = page.getHtml().$("table tbody > tr ").nodes();
        for (Selectable s : selectables) {
            String name = s.css("a").xpath("/a/text()").get();
            if (name == null) {
                stop = true;
                break;
            }
            String province = s.css("td").all().get(1).replaceAll("<td>", "").replaceAll("</td>", "");
            System.out.println(name + "----" + province);
            school.put(name, province);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider s = null;
        Integer num = 0;
        s = Spider.create(new SchoolPageProcessor());
        while (!stop) {
            String tarUrl = url + num.toString();
            num += 20;
            s.addUrl(tarUrl);
            if (num >= 880) {
                break;
            }
        }
        s.thread(5).run();
        int result = JsonpResult.createCollege(school);
        System.out.println(school.size());
        System.out.println(result);
//        System.out.println(school);

    }
}
