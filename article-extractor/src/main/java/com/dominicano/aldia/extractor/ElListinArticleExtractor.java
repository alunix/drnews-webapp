package com.dominicano.aldia.extractor;

import com.dominicano.aldia.dao.ArticleDAO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ElListinArticleExtractor extends AbstractArticleExtractor{

    public static final String NEWS_OUTLET = "ElListinDiario";

    //Not sure what findby stands for
    public static final String baseUrl = "http://www.listindiario.com/buscar?datefrom=04-05-2016&dateto=&findby=1&find=&s=12";

    public static final Map<String, String> sectionsMap = new HashMap<String, String>();

    private final Logger log = Logger.getLogger(ElListinArticleExtractor.class.getSimpleName());

    public static int CONN_TIMEOUT = 4000; //ms

    public ElListinArticleExtractor(){
        //mapping of sections to their id
        sectionsMap.put("Economía Y negocios","15");
        sectionsMap.put("Entretenimiento","18");
        sectionsMap.put("El Deporte","13");
    }

    private String getDateStr(){
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH)+1; //starts at 0
        String dateStr = cal.get(Calendar.DAY_OF_MONTH)+"-"+month+"-"+cal.get(Calendar.YEAR);
        return dateStr;
    }

    private String getUrl(String section){
        StringBuilder urlBd = new StringBuilder(baseUrl);
        urlBd.replace(urlBd.indexOf("datefrom"),urlBd.indexOf("&dateto"),"datefrom="+getDateStr());
        urlBd.replace(urlBd.lastIndexOf("s="),urlBd.length(),"s="+section);
        return urlBd.toString();
    }

    private ArticleInfo getArticleInfo(String articleUrl) throws IOException {
        Document articleDoc = Jsoup.connect(articleUrl).timeout(CONN_TIMEOUT).get();
        Element articleZone = articleDoc.select("#article_zone").first();

        String title = articleZone.select(".art_titulo").first().text();
        String content = articleZone.select("#ArticleBody").text();

        //Extra info which is technically not required
        Element img = articleZone.select("#article-photos").first().select("img").first();
        String imgUrl = "";
        if(img != null) {
            imgUrl = img.attr("src");
        }

        ArticleInfo artInfo = new ArticleInfo(NEWS_OUTLET);
        artInfo.title = title;
        artInfo.articleUrl = articleUrl;
        artInfo.content = content;
        artInfo.imgUrl = imgUrl;

        //articles returned are only for todays date
        artInfo.datePublished = Calendar.getInstance().getTime();
        artInfo.datePublishedStr = getDateStr();
        artInfo.timeExtracted = artInfo.datePublished;

        log.info(artInfo.toString());
        return artInfo;
    }

    private List<ArticleInfo> extractArticlesInSection(String sectionKey) throws IOException {
        String section = sectionsMap.get(sectionKey);
        String pageUrl = getUrl(section);

        log.info("PageUrl: "+pageUrl);
        log.info("Section: "+section);

        Document rootDoc = Jsoup.connect(pageUrl).timeout(CONN_TIMEOUT).get();
        Element articlesTab = rootDoc.select("#users").first();
        Elements articles = articlesTab.select(".list-group-item");
        List<ArticleInfo> articleInfoList = new ArrayList<ArticleInfo>();

        for(int i=0; i< articles.size(); i++){
            Element article = articles.get(i);
            Element titleElem = article.select(".lnktitle").first();
            String articleUrl = titleElem.absUrl("href");

            log.info("Parsing Article: "+titleElem.text());
            log.info("ArticleNumber: " + i);

            log.info("ArticleUrl: "+articleUrl);

            try {
                ArticleInfo artInfo = getArticleInfo(articleUrl);
                artInfo.section = sectionKey;

                articleInfoList.add(artInfo);
            } catch (IOException | NullPointerException e) {
                log.info("Failed: Check article Schema or Url could be broken");
                e.printStackTrace();
            }
        }

        int NumErrors = articles.size() - articleInfoList.size();

        log.info("------------------------------------------------------------------------");
        log.info("Summary: " + "Section: " + sectionKey + " Articles Extracted: " + articleInfoList.size() + " Errors Parsing: " + NumErrors);
        log.info("------------------------------------------------------------------------\n");

        return articleInfoList;
    }

    @Override
    void extractArticles() {
        for(String section: sectionsMap.keySet()){
            try {
                List<ArticleInfo> articleInfos = extractArticlesInSection(section);

                store(articleInfos);
            } catch (IOException | NullPointerException e) {
                log.info("ERROR in Section: "+sectionsMap.get(section)+" with key: "+section);
                log.info("Check Page format or maybe could not connect to URL");
                e.printStackTrace();
            }
        }
    }
}
