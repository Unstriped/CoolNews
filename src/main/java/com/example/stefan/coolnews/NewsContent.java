package com.example.stefan.coolnews;

public class NewsContent {

    private String newsTitle;
    private String newsDescription;
    private String newsPubDate;
    private String newsLink;

    public NewsContent(){

    }

    public NewsContent(String newsTitle, String newsDescription, String newsPubDate, String newsLink){
        this.newsTitle = newsTitle;
        this.newsDescription = newsDescription;
        this.newsPubDate = newsPubDate;
        this.newsLink = newsLink;

    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public String getNewsPubDate() {
        return newsPubDate;
    }

    public String getNewsLink() {
        return newsLink;
    }



}
