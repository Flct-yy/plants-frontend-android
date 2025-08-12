package com.wect.plants_frontend_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wect.plants_frontend_android.Model.NormalArticle;
import com.wect.plants_frontend_android.R;

import java.util.List;

import com.bumptech.glide.Glide;

public class NormalItemsAdapter extends RecyclerView.Adapter<NormalItemsAdapter.ViewHolder> {

    private List<NormalArticle> items;
    private Context context;

    /**
     * 构造函数
     */
    public NormalItemsAdapter(Context context, List<NormalArticle> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_img, user_Follow_icon, article_item_img;
        TextView user_name, article_time, user_Follow_text, article_title, article_watch, article_comment, article_like;

        public ViewHolder(View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.user_img);
            article_item_img = itemView.findViewById(R.id.article_item_img);
            user_Follow_icon = itemView.findViewById(R.id.user_Follow_icon);

            user_name = itemView.findViewById(R.id.user_name);
            article_time = itemView.findViewById(R.id.article_time);
            user_Follow_text = itemView.findViewById(R.id.user_Follow_text);
            article_title = itemView.findViewById(R.id.article_title);
            article_watch = itemView.findViewById(R.id.article_watch);
            article_comment = itemView.findViewById(R.id.article_comment);
            article_like = itemView.findViewById(R.id.article_like);
        }
    }

    /**
     * 创建 ViewHolder 对象
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public NormalItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.adapter_normal_article, parent, false);
        return new ViewHolder(v);
    }

    /**
     * 数据绑定到 ViewHolder 里的控件
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull NormalItemsAdapter.ViewHolder holder, int position) {
        NormalArticle item = items.get(position);

        holder.user_name.setText(item.user_name);
        holder.article_time.setText(item.article_time);
        holder.article_title.setText(item.article_title);
        holder.article_watch.setText(String.valueOf(item.article_watch));
        holder.article_comment.setText(String.valueOf(item.article_comment));
        holder.article_like.setText(String.valueOf(item.article_like));
        // 是否关注
        if(item.user_isFollow){
            holder.user_Follow_text.setText("已关注");
            holder.user_Follow_icon.setVisibility(View.GONE);
        }



        // 加载头像
        Glide.with(context)
                .load(item.user_img) // 这里是 String URL
                .placeholder(R.drawable.ic_user) // 占位图
                .into(holder.user_img);

        // 加载文章图片
        Glide.with(context)
                .load(item.article_imageUrl) // 这里是 String URL
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.article_item_img);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
