package com.dominicano.aldia.extractor;

import java.util.Date;

public class ArticleInfo {
    public String newsOutlet = "";
    public String title = "";
    public String content = "";
    public String section = "";

    public String author = "";
    public String place = "";

    public String articleUrl = "";
    public String imgUrl = "";

    public String datePublishedStr = "";
    public Date datePublished;

    public Date timeExtracted;

    public ArticleInfo(String newsOutlet){
        this.newsOutlet = newsOutlet;
    }

    @Override
    public String toString(){
        return "newsOutlet: " + newsOutlet + "\n" +
                "title: " + title + "\n" +
                "author: " + author + "\n" +
                "datePublishedStr: " + datePublishedStr + "\n" +
                "datePublished: " + datePublished + "\n" +
                "timeExtracted: " + timeExtracted + "\n" +
                "place: " + place + "\n" +
                "articleUrl: " + articleUrl + "\n" +
                "imgUrl: " + imgUrl + "\n" +
                "content: " + content + "\n";

    }
}
