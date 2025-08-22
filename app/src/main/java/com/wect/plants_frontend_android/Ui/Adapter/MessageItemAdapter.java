package com.wect.plants_frontend_android.Ui.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wect.plants_frontend_android.Ui.UIModel.MessageItem;
import com.wect.plants_frontend_android.R;

import java.util.List;

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.ViewHolder>{

    private List<MessageItem> MessageItem;

    public MessageItemAdapter(List<MessageItem> MessageItem) {
        this.MessageItem = MessageItem;
    }

    public void setMessageItem(List<MessageItem> MessageItem) {
        this.MessageItem = MessageItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_message_item, parent, false);
        return new MessageItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemAdapter.ViewHolder holder, int position) {
        MessageItem message = MessageItem.get(position);

        String avatarUri = message.getSenderAvatar() != null ?
                message.getSenderAvatar() :
                getDefaultAvatarUri(message.getSenderName());

        // 加载头像（使用ImageView的setImageURI）
        holder.senderAvatar.setImageURI(Uri.parse(avatarUri));
        holder.senderName.setText(message.getSenderName());
        holder.content.setText(message.getPreview());
        holder.sendTime.setText(message.getFriendlyTime());
    }

    // 生成默认头像（基于名称首字母）
    private String getDefaultAvatarUri(String name) {
        if (name == null || name.isEmpty()) name = "U";
        String initial = name.substring(0, 1).toUpperCase();
        return "https://ui-avatars.com/api/?name=" + initial + "&background=random";
    }

    @Override
    public int getItemCount() {
        return MessageItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView senderAvatar;
        TextView senderName, content, sendTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderAvatar = itemView.findViewById(R.id.sender_avatar);
            senderName = itemView.findViewById(R.id.sender_name);
            content = itemView.findViewById(R.id.content);
            sendTime = itemView.findViewById(R.id.send_time);
        }
    }
}
