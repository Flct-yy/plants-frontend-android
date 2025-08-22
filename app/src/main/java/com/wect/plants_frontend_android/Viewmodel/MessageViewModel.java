package com.wect.plants_frontend_android.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wect.plants_frontend_android.Ui.UIModel.MessageItem;

import java.util.ArrayList;
import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MutableLiveData<List<MessageItem>> messages = new MutableLiveData<>();

    public MessageViewModel(@NonNull Application application) {
        super(application);
//        loadMessages();
        loadMockMessages();
    }

    // 生成模拟消息数据
    private void loadMockMessages() {
        List<MessageItem> mockData = new ArrayList<>();

        // 添加文本消息
        mockData.add(new MessageItem(
                "张三",
                "https://randomuser.me/api/portraits/men/1.jpg",
                "你好，今天天气不错",
                MessageItem.TYPE_TEXT,
                System.currentTimeMillis() - 1000 * 60 * 5 // 5分钟前
        ));

        // 添加图片消息
        mockData.add(new MessageItem(
                "李四",
                "https://randomuser.me/api/portraits/women/2.jpg",
                "看看我刚拍的照片",
                MessageItem.TYPE_IMAGE,
                System.currentTimeMillis() - 1000 * 60 * 30 // 30分钟前
        ));

        // 添加系统通知
        mockData.add(new MessageItem(
                "系统通知",
                null,
                "您收到一条新好友请求",
                MessageItem.TYPE_TEXT,
                System.currentTimeMillis() - 1000 * 60 * 60 * 3 // 3小时前
        ));

        // 添加文本消息
        mockData.add(new MessageItem(
                "张三",
                "https://randomuser.me/api/portraits/men/1.jpg",
                "你好，今天天气不错",
                MessageItem.TYPE_TEXT,
                System.currentTimeMillis() - 1000 * 60 * 5 // 5分钟前
        ));

        // 添加图片消息
        mockData.add(new MessageItem(
                "李四",
                "https://randomuser.me/api/portraits/women/2.jpg",
                "看看我刚拍的照片",
                MessageItem.TYPE_IMAGE,
                System.currentTimeMillis() - 1000 * 60 * 30 // 30分钟前
        ));

        // 添加系统通知
        mockData.add(new MessageItem(
                "系统通知",
                null,
                "您收到一条新好友请求",
                MessageItem.TYPE_TEXT,
                System.currentTimeMillis() - 1000 * 60 * 60 * 3 // 3小时前
        ));

        // 添加文本消息
        mockData.add(new MessageItem(
                "张三",
                "https://randomuser.me/api/portraits/men/1.jpg",
                "你好，今天天气不错",
                MessageItem.TYPE_TEXT,
                System.currentTimeMillis() - 1000 * 60 * 5 // 5分钟前
        ));

        // 添加图片消息
        mockData.add(new MessageItem(
                "李四",
                "https://randomuser.me/api/portraits/women/2.jpg",
                "看看我刚拍的照片",
                MessageItem.TYPE_IMAGE,
                System.currentTimeMillis() - 1000 * 60 * 30 // 30分钟前
        ));

        // 添加系统通知
        mockData.add(new MessageItem(
                "系统通知",
                null,
                "您收到一条新好友请求",
                MessageItem.TYPE_TEXT,
                System.currentTimeMillis() - 1000 * 60 * 60 * 3 // 3小时前
        ));


        messages.postValue(mockData);
    }

    public LiveData<List<MessageItem>> getMessages() {
        return messages;
    }

    private void loadMessages() {
        // 实际项目中应从Repository加载数据
        List<MessageItem> mockData = new ArrayList<>();
        mockData.add(new MessageItem());
        messages.postValue(mockData);
    }
}
