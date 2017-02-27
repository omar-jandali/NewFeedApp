package com.example.janda_000.newsfeed;

/**
 * Created by janda_000 on 2/24/2017.
 */

public class Article {

    // the following are all of the different variables or peices of information that are going
    // to be used to create a article object
    private String aTitle;
    private String aSection;
    private String aDate;
    public String aUrl;

    // the following is the method that is going to be creating the actual article object to be
    // displayed for the user in the list of articles
    public Article(String title, String section, String date, String url){

        aTitle = title;
        aSection = section;
        aDate = date;
        aUrl = url;

    }

    // The following are all of the get functions that will be used to grab the assigned values and
    // set them to the appropriate views that will be displayed
    public String getTitle(){ return aTitle; }
    public String getSection(){ return aSection; }
    public String getDate(){ return aDate; }
    public String getUrl(){ return aUrl; }

    @Override
    public String toString(){
        return "Article{" +
                "title='" + aTitle + "\'" +
                "section='" + aSection + "\'" +
                "date='" + aDate + "\'" +
                "url='" + aUrl + "\'" +
                "}";

    }

}
