package com.wect.plants_frontend_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wect.plants_frontend_android.Model.NormalItem;
import com.wect.plants_frontend_android.R;

import java.util.List;

public class NormalItemsAdapter extends RecyclerView.Adapter<NormalItemsAdapter.ViewHolder> {

    private List<NormalItem> items;
    private Context context;

    /**
     * 构造函数
     * @param context
     * @param items
     */
    public NormalItemsAdapter(Context context, List<NormalItem> items) {
        this.context = context;
        this.items = items;
    }

    /**
     * ViewHolder 对象
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, stats;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.normal_item_img);
            name = itemView.findViewById(R.id.normal_item_name);
            stats = itemView.findViewById(R.id.normal_item_stats);
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
                .inflate(R.layout.adapter_normal_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     *  数据绑定到 ViewHolder 里的控件
     * @return
     */
    @Override
    public void onBindViewHolder(@NonNull NormalItemsAdapter.ViewHolder holder, int position) {
        NormalItem item = items.get(position);
        holder.name.setText(item.name);
        holder.stats.setText(item.stats);
        holder.img.setImageResource(item.imageResId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
