package com.wect.plants_frontend_android.Data.Model;

public class NormalArticle {
    // 普通Item的数据
    public String user_name;
    public String article_title;
    public String article_time;

    public Boolean user_isFollow;

    public int article_imageUrl;
    public int user_img;
    public int article_watch;
    public int article_comment;
    public int article_like;

    public NormalArticle(String user_name, String article_title, String article_time,
                         Boolean user_isFollow, int user_img, int article_imageUrl,
                         int article_watch, int article_comment, int article_like) {
        this.user_name = user_name;
        this.user_img = user_img;
        this.user_isFollow = user_isFollow;
        this.article_title = article_title;
        this.article_time = article_time;
        this.article_imageUrl = article_imageUrl;
        this.article_watch = article_watch;
        this.article_comment = article_comment;
        this.article_like = article_like;
    }
}