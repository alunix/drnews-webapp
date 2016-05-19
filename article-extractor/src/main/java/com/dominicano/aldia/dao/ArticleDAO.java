package com.dominicano.aldia.dao;

import com.dominicano.aldia.extractor.ArticleInfo;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;
import org.javalite.activejdbc.validation.ValidationException;
import org.postgresql.util.PSQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ArticleDAO {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/news_data_test";
    public static final String USER = "delvistaveras";

    private static Logger log = Logger.getLogger(ArticleDAO.class.getSimpleName());

    //Prevent Instantiation
    private ArticleDAO(){

    }

    static {
        Base.open("org.postgresql.Driver", DB_URL, USER, "");
    }

    public static void printAll(){
        List<Article> articles = Article.findAll();

        articles.stream().forEach(artl -> System.out.println(artl));
    }

    private static Article newArticle(ArticleInfo articleInfo){
        Article article = new Article();

        article.setNewsOutlet(articleInfo.newsOutlet);
        article.setTitle(articleInfo.title);
        article.setSection(articleInfo.section);
        article.setContent(articleInfo.content);

        article.setAuthor(articleInfo.author);
        article.setPlace(articleInfo.place);

        article.setArticleUrl(articleInfo.articleUrl);
        article.setImgUrl(articleInfo.imgUrl);

        article.setDatePublished(articleInfo.datePublished);
        article.setTimeExtracted(articleInfo.timeExtracted);

        return article;
    }

    private static void store(ArticleInfo articleInfo) throws DBException{
        Article article = newArticle(articleInfo);
        article.saveIt();
    }

    public static void store(List<ArticleInfo> articleInfos){
        int numErrors = 0;
        for(ArticleInfo articleInfo: articleInfos){
            try {
                store(articleInfo);
            }
            catch (DBException e){
                numErrors++;
                log.info("Failed to store object check Validation Failure below");
                e.printStackTrace();
            }
        }

        log.info("Tried to Store: "+articleInfos.size()+" DBException Errors: "+numErrors);
    }
}