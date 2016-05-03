package com.dominicano.aldia.extractor;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ElCaribeExtractor extends AbstractArticleExtractor{

    public final static String NEWS_OUTLET = "Elcaribe";

    //Available Sections Todas, Panorama, Deportes, Gente
    public final static String[] sections = {"Panorama", "Deportes", "Gente"};

    Logger log = Logger.getLogger(this.getClass().getSimpleName());

    enum Age {
        ONEDAY("24+horas"),
        THREEDAYS("3+días"),
        WEEK("1+semana"),
        MONTH("1+mes");

        private String age;
        Age(String age) {
            this.age = age;
        }

        public String getStr(){
            return this.age;
        }
    }

    private final String baseUrl = "http://www.elcaribe.com.do/archivos?palabras=todas&antiguedad=24+horas&seccion=Panorama&multimedia=Art%C3%ADculo&pagina=1";

    private String getPageUrl(String url, int pageNum){
        StringBuilder strBd = new StringBuilder(url);
        strBd.replace(strBd.indexOf("pagina"),url.length(),"pagina="+pageNum);
        return strBd.toString();
    }

    private String getUrl(Age age, String section){
        StringBuilder strBd = new StringBuilder(baseUrl);
        strBd.replace(strBd.indexOf("antiguedad"), strBd.indexOf("&seccion"), "antiguedad="+age.getStr());
        strBd.replace(strBd.indexOf("seccion"), strBd.indexOf("&multimedia"), "seccion="+section);
        return strBd.toString();
    }

    private Date parseDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("Failed to parse date check if format has changed");
        }

        return  date;
    }

    //Schema used to generate the articles http://schema.org/Article
    private ArticleInfo getArticleInfo(String articleUrl) throws IOException {
        Document rootArticleDoc = Jsoup.connect(articleUrl).get();

        Element article = rootArticleDoc.select("article.contenidoArticulo").first();
        Element header = article.select("header").first();
        Element body = article.select("[itemprop='articleBody']").first();
        Element socialMediaBoxFigure = article.select(".multimediaCompartir figure").first();

        //get title, date, author
        String title = header.select("[itemprop='name']").text();
        String datePublished = header.select("[itemprop='datePublished']").text();
        String author = header.select("[itemprop='author'").text();

        //get the articles content
        String content = body.text();

        //get img url
        //TODO: get img caption
        String imgUrl = "";
        if(socialMediaBoxFigure != null) {
            imgUrl = socialMediaBoxFigure.select("img").first().attr("src");
        }

        ArticleInfo artInfo = new ArticleInfo(NEWS_OUTLET);
        artInfo.articleUrl = articleUrl;
        artInfo.title = title;
        artInfo.datePublishedStr = datePublished;
        artInfo.datePublished = parseDate(datePublished);
        artInfo.author = author;
        artInfo.content = content;
        artInfo.imgUrl = imgUrl;

        log.info(artInfo.toString());
        return artInfo;
    }

    private List<ArticleInfo> fetchPageOfArticles(String url, String section) throws IOException {
        Document rootDoc = Jsoup.connect(url).get();

        List<ArticleInfo> parsedArticles = new ArrayList<>();

        Element articleList = rootDoc.select("div.listado-articulos").first();
        Elements articles = articleList.select("article");

        for(int i=0; i< articles.size(); i++){
            Element article = articles.get(i);
            Element titleElem = article.select("h1").first();

            log.info("Parsing Article: "+titleElem.text());
            log.info("ArticleNumber: " + i);

            String articleLink = titleElem.select("a").first().attr("href");
            log.info("Link: "+articleLink);

            try {
                ArticleInfo artInfo = getArticleInfo(articleLink);
                artInfo.section = section;

                parsedArticles.add(artInfo);
            }catch (NullPointerException e){
                e.printStackTrace();
                log.info("Failed to Parse Article check if Schema has changed moving on to next article");
            }
        }

        return parsedArticles;
    }

    private void fetchSectionOfArticles(String section) throws IOException {
        int pageNum = 1;
        List<ArticleInfo> allArticles = new ArrayList<>();
        String url = getUrl(Age.ONEDAY, section);
        String pageUrl = getPageUrl(url, pageNum);

        log.info("PageNumber: " + pageNum);
        log.info("PageUrl: " + pageUrl);
        log.info("Section: " + section);
        List<ArticleInfo> articleList = fetchPageOfArticles(pageUrl, section);
        while(!articleList.isEmpty()){
            log.info("Fetched: " + articleList.size() + " Articles");
            allArticles.addAll(articleList);

            pageNum++;
            pageUrl = getPageUrl(pageUrl, pageNum);

            log.info("PageNumber: " + pageNum);
            log.info("PageUrl: " + pageUrl);

            articleList = fetchPageOfArticles(pageUrl, section);
        }

        int totalPages = pageNum-1;
        log.info("------------------------------------------------------------------------");
        log.info("Summary: " + "Section: " + section + "Pages: " + totalPages + " Articles Fetched: " + allArticles.size());
        log.info("------------------------------------------------------------------------\n");

        //TODO: Store articles
    }

    @Override
    public void fetchArticles() throws IOException {
        for (String section: sections){
            fetchSectionOfArticles(section);
        }
   }
}