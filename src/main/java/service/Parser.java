package service;

import domain.BaseModel;
import domain.SysCity;
import mapper.SysCountyMapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    /**
     * 解析省页面，返回省数据
     */
    public List<BaseModel> parseProvince(Document document) {
        Element body = document.body();
        Elements elements = body.getElementsByClass("provincetable");
        Elements links = elements.get(0).getElementsByTag("a");
        List<BaseModel> list = new ArrayList<BaseModel>();
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            BaseModel baseModel = new BaseModel();
            baseModel.setId(linkHref);
            baseModel.setHref(linkHref);
            baseModel.setName(linkText);
            list.add(baseModel);
        }
        return list;
    }

    /**
     * 解析地级页面，返回数据
     */
    public List<BaseModel> parseCity(Document document) {
        Element body = document.body();
        Elements elements = body.getElementsByClass("citytable");
        Elements links = elements.get(0).getElementsByClass("citytr");
        List<BaseModel> list = new ArrayList<BaseModel>();
        for (Element link : links) {
            Elements tds = link.getElementsByTag("td");
            Element cityUrlElement = tds.get(1).getElementsByTag("a").get(0);
            String href = cityUrlElement.attr("href");
            String name = tds.get(1).text();
            BaseModel baseModel = new BaseModel();
            baseModel.setId(href);
            baseModel.setHref(href);
            baseModel.setName(name);
            list.add(baseModel);
        }
        return list;
    }

    /**
     * 解析县级页面，返回数据
     */
    public List<BaseModel> parseCounty(Document document) {
        List<BaseModel> list = new ArrayList<BaseModel>();
        Element body = document.body();
        Elements elements = body.getElementsByClass("countytable");
        if (elements.isEmpty()) {
            return new ArrayList<BaseModel>();
        }
        Elements links = elements.get(0).getElementsByClass("countytr");
        for (Element link : links) {
            Elements tds = link.getElementsByTag("td");
            String id = tds.get(0).text();
            String name = tds.get(1).text();
            BaseModel baseModel = new BaseModel();
            baseModel.setId(id);
            Elements aElements = tds.get(1).getElementsByTag("a");
            if (!aElements.isEmpty()) {
                String href = aElements.get(0).attr("href");
                baseModel.setHref(href);
            }
            baseModel.setName(name);
            list.add(baseModel);
        }
        return list;
    }

    /**
     * 解析乡级页面,返回数据
     */
    public List<BaseModel> parseTown(Document document) {
        List<BaseModel> list = new ArrayList<BaseModel>();
        Element body = document.body();
        Elements elements = body.getElementsByClass("towntable");
        Elements links = elements.get(0).getElementsByClass("towntr");
        for (Element link : links) {
            Elements tds = link.getElementsByTag("td");
            String id = tds.get(0).text();
            String name = tds.get(1).text();
            BaseModel baseModel = new BaseModel();
            baseModel.setId(id);
            Elements aElements = tds.get(1).getElementsByTag("a");
            if (!aElements.isEmpty()) {
                String href = aElements.get(0).attr("href");
                baseModel.setHref(href);
            }
            baseModel.setName(name);
            list.add(baseModel);
        }
        return list;
    }

    /**
     * 解析村级页面,返回数据
     */
    public List<BaseModel> parseVillage(Document document) {
        List<BaseModel> list = new ArrayList<BaseModel>();
        Element body = document.body();
        Elements elements = body.getElementsByClass("villagetable");

        Elements links = elements.get(0).getElementsByClass("villagetr");
        for (Element link : links) {
            Elements tds = link.getElementsByTag("td");
            String href = tds.get(0).text();
            String name = tds.get(2).text();
            BaseModel baseModel = new BaseModel();
            baseModel.setId(href);
            baseModel.setName(name);
            list.add(baseModel);
        }
        return list;
    }

}
