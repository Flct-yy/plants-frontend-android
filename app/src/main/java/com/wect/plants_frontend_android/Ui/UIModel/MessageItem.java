package com.wect.plants_frontend_android.Ui.UIModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageItem {
    // 基础属性
    private String senderName;
    private String senderAvatar;
    private String content;
    private int messageType;
    private long sendTime; // 建议改用long类型存储时间戳（毫秒）

    // 消息类型常量
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_VOICE = 3;
    public static final int TYPE_VIDEO = 4;

    // ================= 构造函数 =================

    // 全参数构造函数
    public MessageItem(String senderName, String senderAvatar,
                       String content, int messageType, long sendTime) {
        this.senderName = senderName;
        this.senderAvatar = senderAvatar;
        this.content = content;
        this.messageType = messageType;
        this.sendTime = sendTime;
    }

    // 简化构造函数（默认当前时间）
    public MessageItem(String senderName, String senderAvatar,
                       String content, int messageType) {
        this(senderName, senderAvatar, content, messageType, System.currentTimeMillis());
    }

    // 空构造函数（用于序列化/反序列化）
    public MessageItem() {}

    // ================= Getter/Setter =================

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName != null ? senderName : "";
    }

    public String getSenderAvatar() {
        return senderAvatar;
    }

    public void setSenderAvatar(String senderAvatar) {
        this.senderAvatar = senderAvatar != null ? senderAvatar : "";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content != null ? content : "";
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime > 0 ? sendTime : System.currentTimeMillis();
    }

    // ================= 实用方法 =================

    /**
     * 是否是文本消息
     */
    public boolean isTextType() {
        return messageType == TYPE_TEXT;
    }

    /**
     * 获取格式化后的时间字符串
     */
    public String getFormattedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(sendTime));
    }

    /**
     * 带智能判断的友好时间显示
     * - 1分钟内：刚刚
     * - 1小时内：X分钟前
     * - 今天：HH:mm
     * - 昨天：昨天 HH:mm
     * - 更早：yyyy-MM-dd
     */
    public String getFriendlyTime() {
        long now = System.currentTimeMillis();
        long diff = now - sendTime;

        // 定义时间常量（毫秒）
        final long SECOND = 1000;
        final long MINUTE = 60 * SECOND;
        final long HOUR = 60 * MINUTE;
        final long DAY = 24 * HOUR;

        if (diff < MINUTE) {
            return "刚刚";
        } else if (diff < HOUR) {
            return (diff / MINUTE) + "分钟前";
        } else if (diff < DAY) {
            // 今天内显示时间
            SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return todayFormat.format(new Date(sendTime));
        } else if (diff < 2 * DAY) {
            // 昨天
            SimpleDateFormat yesterdayFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return "昨天 " + yesterdayFormat.format(new Date(sendTime));
        } else {
            // 更早日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.format(new Date(sendTime));
        }
    }

    /**
     * 显示消息的简短预览
     */
    public String getPreview() {
        if (isTextType()) {
            return content.length() > 20 ? content.substring(0, 20) + "..." : content;
        }
        return "[非文本消息]";
    }
}
