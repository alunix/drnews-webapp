package com.dominicano.aldia.dao;

import org.javalite.activejdbc.Model;

import java.util.Date;

public class Article extends Model{

    public String getNewsOutlet(){
        return this.getString("news_outlet");
    }

    public void setNewsOutlet(String newsoutlet){
        this.set("news_outlet", newsoutlet);
    }

    public String getTitle(){
        return this.getString("title");
    }

    public void setTitle(String title){
        this.set("title", title);
    }

    public String getSection(){
        return this.getString("section");
    }

    public void setSection(String section){
        this.set("section", section);
    }

    public String getContent(){
        return this.getString("content");
    }

    public void setContent(String content){
        this.set("content", content);
    }

    public String getAuthor(){
        return this.getString("author");
    }

    public void setAuthor(String author){
        this.set("author", author);
    }

    public String getPlace(){
        return this.getString("place");
    }

    public void setPlace(String place){
        this.set("place", place);
    }

    public String getArticleUrl(){
        return this.getString("article_url");
    }

    public void setArticleUrl(String articleUrl){
        this.set("article_url", articleUrl);
    }

    public String getImgUrl(){
        return this.getString("img_url");
    }

    public void setImgUrl(String imgUrl){
        this.set("img_url", imgUrl);
    }

    public Date getDatePublished(){
        return this.getDate("date_published");
    }

    public void setDatePublished(Date datePublished){
        this.setDate("date_published", datePublished);
    }

    public Date getTimeExtracted(){
        return this.getDate("time_extracted");
    }

    public void setTimeExtracted(Date timeExtracted){
        this.setDate("time_extracted", timeExtracted);
    }
}