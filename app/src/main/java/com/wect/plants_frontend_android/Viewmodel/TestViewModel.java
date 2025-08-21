package com.wect.plants_frontend_android.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wect.plants_frontend_android.Data.local.AppDatabase;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.ArticlesDao;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.LikeArticleDao;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.UserDao;
import com.wect.plants_frontend_android.Data.local.Databases.DTO.ArticleWithAuthorAndLike;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Articles;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.LikeArticle;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

import java.util.List;

public class TestViewModel extends AndroidViewModel {


    // 作用：初始化数据库访问组件
    public TestViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        UserDao userDao = db.userDao();
        ArticlesDao articlesDao = db.articlesDao();
        LikeArticleDao likeArticleDao = db.likeArticleDao();

    // 用户操作
        // 插入用户
        User user = new User("张三", "13800138000");
        userDao.insert(user);

        // 查询用户
        User userId = userDao.getUserById(1);
        List<User> allUsers = userDao.getAllUsers().getValue();

        // 更新用户头像
        userDao.updateAvatar(1, "https://example.com/avatar.jpg");

        // 删除用户
        userDao.delete(user);


    // 文章操作
        // 插入文章
        Articles article = new Articles();
        article.setTitle("我的第一篇文章");
        article.setContent("内容...");
        article.setAuthorId(1);
        article.setCreatedAt(System.currentTimeMillis());
        long articleId = articlesDao.insertArticle(article);

        // 查询文章
        List<Articles> allArticles = articlesDao.getAllArticles();
        Articles articles = articlesDao.getArticleById(articleId);

        // 更新浏览数
        articlesDao.increaseViews(articleId);

        // 删除文章
        articlesDao.deleteArticle(article);


    // 点赞操作
        // 点赞
        LikeArticle like = new LikeArticle();
        like.setUserId(1);
        like.setArticleId(articleId);
        likeArticleDao.insert(like);
        articlesDao.increaseLikes(articleId); // 同时更新文章点赞数

        // 取消点赞
        likeArticleDao.delete(like);
        articlesDao.decreaseLikes(articleId);

        // 检查是否点赞
        boolean isLiked = likeArticleDao.isArticleLikedByUser(1, articleId);

    // 实时数据观察（LiveData）
        // 观察用户列表变化
//        userDao.getAllUsers().observe(this, users -> {
            // UI 更新
//            adapter.submitList(users);
//        });

        // 观察文章列表变化
//        articlesDao.getAllArticlesLive().observe(this, articles -> {
            // 刷新文章列表
//        });
    // 关联查询（文章+作者）
        // 获取单篇文章及其作者
        ArticleWithAuthorAndLike articleWithAuthor = articlesDao.getArticleWithAuthor(articleId);
        String authorName = articleWithAuthor.author.getName();

        // 获取所有文章及其作者
        List<ArticleWithAuthorAndLike> allArticlesWithAuthors = articlesDao.getAllArticlesWithAuthors();
    // 分页查询
        // 获取热门用户（分页）
        int page = 1;
        int pageSize = 10;
        LiveData<List<User>> popularUsers = userDao.getPopularUsers(
                pageSize,
                (page - 1) * pageSize
        );
    }
}
