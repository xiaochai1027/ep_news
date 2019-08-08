package com.cfc.admin.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Fangchen.chai on 2017/3/31.
 */
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        String num = page.getHtml().xpath("//div[@class='coupon-info']").xpath("//dl").xpath("//dd").xpath("//span[@class='rest']").get();
        String src = page.getHtml().xpath("//div[@class='s']").$("img","src").get();
        System.out.println("--------------------------------------");
        System.out.println(page.toString());
        System.out.println("*************************");
        System.out.println("num="+num);
        System.out.println(src);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://yz.chsi.com.cn/sch/search.do?start=860").thread(5).run();

    }
}
