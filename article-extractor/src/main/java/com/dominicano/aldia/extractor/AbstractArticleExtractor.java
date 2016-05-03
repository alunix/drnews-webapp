package com.dominicano.aldia.extractor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public abstract class AbstractArticleExtractor {

    protected class ArticleInfo {
        public String newsOutlet = "";
        public String title = "";
        public String author = "";
        public String datePublishedStr = "";
        public Date datePublished;
        public String section = "";
        public String place = "";
        public String articleUrl = "";
        public String imgUrl = "";
        public String content = "";

        public ArticleInfo(String newsOutlet){
            this.newsOutlet = newsOutlet;
        }

        @Override
        public String toString(){
            return "newsOutlet: " + newsOutlet + "\n" +
                    "title: " + title + "\n" +
                    "author: " + author + "\n" +
                    "datePublished: " + datePublishedStr + "\n" +
                    "dateObject: " + datePublished + "\n" +
                    "place: " + place + "\n" +
                    "articleUrl: " + articleUrl + "\n" +
                    "imgUrl: " + imgUrl + "\n" +
                    "content: " + content + "\n";

        }
    }

    abstract void fetchArticles() throws Exception;

    //TODO: needs to be implemented using javalite
    private void storeArticles(List<ArticleInfo> articleInfoList){

    }
}
