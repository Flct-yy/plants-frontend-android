package com.wect.plants_frontend_android.Ui.Fragments.PlantList;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wect.plants_frontend_android.Ui.Adapter.PlantCardAdapter;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Utils.BarUtils;
import com.wect.plants_frontend_android.Utils.ToastUtil;
import com.wect.plants_frontend_android.Utils.UnitsUtils;
import com.wect.plants_frontend_android.Viewmodel.PlantListViewModel;

import java.util.ArrayList;

public class PlantListFragment extends Fragment {

    private PlantCardAdapter adapter;
    private PlantListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerPlants = view.findViewById(R.id.recycler_activities);
        recyclerPlants.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // 初始化适配器
        adapter = new PlantCardAdapter(new ArrayList<>());
        recyclerPlants.setAdapter(adapter);

        // 初始化ViewModel
        viewModel = new ViewModelProvider(this).get(PlantListViewModel.class);
        // 数据观察机制
        viewModel.getPlants().observe(getViewLifecycleOwner(), plants -> {
            adapter.setPlantList(plants);
        });

        // 设置滑动操作
        setupSwipeActions(recyclerPlants);

        // 适应状态栏的padding
        BarUtils.applyStatusBarPadding(view);

        return view;
    }


    /**
     * 绑定左右滑动事件
     * @param recyclerView
     */
    private void setupSwipeActions(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            /**
             * 用来做拖拽交换位置
             * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
             * @param viewHolder   The ViewHolder which is being dragged by the user.
             * @param target       The ViewHolder over which the currently active item is being
             *                     dragged.
             * @return
             */
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            /**
             * 当滑动超过阈值时调用
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition(); // 新API
                if (direction == ItemTouchHelper.RIGHT) {
                    // 左滑 -> 收藏
                    // 收藏逻辑
                    ToastUtil.show(requireContext(),"已收藏");
                    adapter.notifyItemChanged(position);
                } else if (direction == ItemTouchHelper.LEFT) {
                    // 右滑 -> 取消收藏
                    // 取消收藏逻辑
                    ToastUtil.show(requireContext(),"取消收藏");
                    adapter.notifyItemChanged(position);
                }
            }

            /**
             * 用来画滑动时的背景和图标
             */
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                // 限制滑动距离，比如 400px
                float maxSwipeDistance = 400f;
                if (dX > maxSwipeDistance) dX = maxSwipeDistance;
                if (dX < -maxSwipeDistance) dX = -maxSwipeDistance;

                // 获取 item 高度
                View itemView = viewHolder.itemView;

                float cornerRadius = UnitsUtils.dpToPx(requireContext(), 12f); // 16dp 圆角半径

                // 文字 Paint
                Paint textPaint = new Paint();
                textPaint.setColor(Color.WHITE);
                textPaint.setTextSize(UnitsUtils.dpToPx(requireContext(), 18f)); // 字体大小
                textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 加粗
                textPaint.setAntiAlias(true);
                textPaint.setTextAlign(Paint.Align.CENTER); // 水平居中

                // 计算垂直居中 Y 坐标
                float textHeight = textPaint.descent() - textPaint.ascent();
                float textOffset = (textHeight / 2) - textPaint.descent();
                float centerY = itemView.getTop() + (itemView.getHeight() / 2) + textOffset;

                if (dX > 0) { // 右滑
                    Paint bgPaint = new Paint();
                    bgPaint.setColor(Color.parseColor("#4CAF50")); // 绿色背景
                    bgPaint.setAntiAlias(true);

                    RectF background = new RectF(
                            itemView.getLeft(),
                            itemView.getTop(),
                            itemView.getLeft() + maxSwipeDistance - UnitsUtils.dpToPx(requireContext(), 8f),
                            itemView.getBottom()
                    );
                    c.drawRoundRect(background, cornerRadius, cornerRadius, bgPaint);

                    // 在背景中心绘制文字
                    float centerX = itemView.getLeft() + (maxSwipeDistance / 2);
                    c.drawText("收藏", centerX, centerY, textPaint);
                } else if (dX < 0) { // 左滑
                    Paint bgPaint = new Paint();
                    bgPaint.setColor(Color.parseColor("#EA1601")); // 红色背景
                    bgPaint.setAntiAlias(true);

                    RectF background = new RectF(
                            itemView.getRight() - maxSwipeDistance + UnitsUtils.dpToPx(requireContext(), 8f),
                            itemView.getTop(),
                            itemView.getRight(),
                            itemView.getBottom()
                    );
                    c.drawRoundRect(background, cornerRadius, cornerRadius, bgPaint);

                    // 在背景中心绘制文字
                    float centerX = itemView.getRight() - (maxSwipeDistance / 2);
                    c.drawText("取消", centerX, centerY, textPaint);
                }

                // 调用父类让 item 位置更新
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                // 让它滑到 maxSwipeDistance 就触发事件
                return 0.3f;
            }

        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
}

