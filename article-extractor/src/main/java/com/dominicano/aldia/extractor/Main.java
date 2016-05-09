package com.dominicano.aldia.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dominicano.aldia.dao.Article;
import com.dominicano.aldia.dao.ArticleDAO;
import org.javalite.activejdbc.Base;

public class Main {

    public static void main(String[] arg) throws Exception {
        ArticleInfo articleInfo = new ArticleInfo("ElCaribe");
        articleInfo.title = "El Carnival Feliz ano";
        articleInfo.section = "Deportes";
        articleInfo.content = "ljadflkjs";
        articleInfo.articleUrl = "www.listin.dom";

        //ArticleDAO.store(articleInfo);
        //AbstractArticleExtractor extractor = new ElListinArticleExtractor();
        AbstractArticleExtractor extractor = new ElCaribeExtractor();
        extractor.extractArticles();


    }
}