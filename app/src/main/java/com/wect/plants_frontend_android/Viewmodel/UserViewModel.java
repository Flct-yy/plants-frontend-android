package com.wect.plants_frontend_android.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wect.plants_frontend_android.Data.local.AppDatabase;
import com.wect.plants_frontend_android.Data.local.Databases.DAO.UserDao;
import com.wect.plants_frontend_android.Data.local.Databases.Entities.User;

import java.util.List;

// 作用：UI 层和数据层的桥梁
public class UserViewModel extends AndroidViewModel {
    // 持有数据库 DAO 引用
    private final UserDao userDao;
    // 通过 LiveData 暴露数据给 UI
    private final LiveData<List<User>> allUsers;

    // 封装业务逻辑操作

    // 作用：初始化数据库访问组件
    public UserViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    // 作用：暴露可观察的用户列表数据
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    /**
     * // Activity/Fragment中观察数据变化
     * viewModel.getAllUsers().observe(this, users -> {
     *     // 更新RecyclerView等UI组件
     * });
     */

    // 作用：异步插入用户数据
    public void insertUser(User user) {
        // 通过 databaseWriteExecutor 在后台线程执行插入
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // 在后台线程执行插入
            // 使用 @Insert 注解的 DAO 方法
            userDao.insert(user);
        });
    }

    // 作用：更新现有用户数据
    public void updateUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
        });
    }

    // 作用：删除指定用户
    public void deleteUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.delete(user);
        });
    }
}
