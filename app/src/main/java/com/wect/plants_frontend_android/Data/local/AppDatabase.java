package com.wect.plants_frontend_android.Data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wect.plants_frontend_android.Data.local.Databases.DAO.ArticlesDao;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.LikeArticleDao;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.MessageDao;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.PlantDao;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.UserDao;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Articles;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.LikeArticle;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Message;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.Plant;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 使用 @Database 注解声明数据库配置
// 定义包含的实体类 (entities)
// Room 数据库的核心入口类，负责数据库的创建和管理。
@Database(entities = {
        User.class,
        Articles.class,
        LikeArticle.class,
        Plant.class,
        Message.class
}, version = 2) // 版本号需要增加
public abstract class AppDatabase extends RoomDatabase {
    // 提供 DAO 接口的抽象方法
    public abstract UserDao userDao();
    public abstract ArticlesDao articlesDao();
    public abstract LikeArticleDao likeArticleDao();
    public abstract PlantDao plantDao();
    public abstract MessageDao messageDao();
    /**
     * 获取DAO实例
     * UserDao dao = AppDatabase.getDatabase(context).userDao();
     */

    private static volatile AppDatabase INSTANCE;

    // 添加数据库写入执行器
    private static final int NUMBER_OF_THREADS = 4; // 线程数
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    // 实现单例模式保证全局唯一实例
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
