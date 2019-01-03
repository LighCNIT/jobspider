package com.superoffer.jobspider;


import com.dao.impl.SpiderDaoImpl;
import com.model.SpiderInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Auther: Ligh
 * @Date: 2018/12/9 14:39
 * @Description:
 */
public class ZhiLianProcessor implements PageProcessor {

    public static String reg = "^[1-9]\\d*$";
    public static String URL_LIST = "https://sou.zhaopin.com";

    public static String URL_POST = "https://jobs.zhaopin.com/\\w+.htm";

//    @Autowired
//    private SpiderDao spiderDao;


    private Site site = Site
            .me()
            .setSleepTime(3000)
            .setRetryTimes(3)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {

        SpiderInfo spider = new SpiderInfo();
        System.out.println("url:"+page.getUrl());
        System.out.println("url_list:"+URL_LIST);
        System.out.println(page.getUrl().regex(URL_LIST).match());

        //列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@class='contentpile__content__wrapper__item clearfix']/a").links().regex(URL_POST).all());
            page.putField("url",page.getHtml().xpath("//div[@class='contentpile__content__wrapper__item clearfix']/a").links()); // URL
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());

            spider.setUrl(page.getHtml().xpath("//div[@class='contentpile__content__wrapper__item clearfix']/a").links().toString());
        } else { // 详情页
            page.putField("post", page.getHtml().xpath("//h1[@class='1 info=h3']")); // 岗位
            page.putField("salary", page.getHtml().xpath("//div[@class='1 info-money']//strong")); // 薪资
            page.putField("company", page.getHtml().xpath("//div[@class='company 1']")); // 公司
            page.putField("JD", page.getHtml().xpath("//div[@class='pos-ul']//p"));// 岗位职责
            page.putField("jobAddress",
                    page.getHtml().xpath("//div[@class='pos-common work-add cl']//p//span[@class='icon-address']"));//工作地址

            spider.setPost(page.getHtml().xpath("//h1[@class='1 info=h3']").toString());
            spider.setSalary(page.getHtml().xpath("//div[@class='1 info-money']//strong").toString());
            spider.setCompany(page.getHtml().xpath("//div[@class='company 1']").toString());
            spider.setJD(page.getHtml().xpath("//div[@class='pos-ul']//p").toString());
            spider.setJobAddress(page.getHtml().xpath("//div[@class='pos-common work-add cl']//p//span[@class='icon-address']").toString());
        }
//       new SpiderDaoImpl().addSpider(spider);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZhiLianProcessor()).addUrl("https://sou.zhaopin.com/?p=6&jl=765&sf=10001&st=15000&kw=java&kt=3")
                .thread(10)
                .run();
    }
}
