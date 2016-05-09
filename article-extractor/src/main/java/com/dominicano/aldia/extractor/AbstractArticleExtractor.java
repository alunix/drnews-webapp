package com.dominicano.aldia.extractor;

import com.dominicano.aldia.dao.ArticleDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public abstract class AbstractArticleExtractor {

    abstract void extractArticles();

    protected void store(List<ArticleInfo> articleInfoList){
        ArticleDAO.store(articleInfoList);
    }
}
