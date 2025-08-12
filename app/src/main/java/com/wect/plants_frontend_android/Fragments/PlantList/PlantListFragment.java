package com.wect.plants_frontend_android.Fragments.PlantList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wect.plants_frontend_android.Adapter.PlantCardAdapter;
import com.wect.plants_frontend_android.Model.PlantCard;
import com.wect.plants_frontend_android.R;
import com.wect.plants_frontend_android.Utils.ToastUtil;
import com.wect.plants_frontend_android.Utils.UnitsUtils;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PlantListFragment extends Fragment {

    private RecyclerView recyclerPlants;
    private PlantCardAdapter adapter;
    private List<PlantCard> plantList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerPlants = view.findViewById(R.id.recycler_activities);

        // 准备数据
        plantList = new ArrayList<>();
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "兰花",
                "Orchidaceae",
                "Orchid",
                "草兰",
                "兰科 兰属",
                "一级保护",
                "花香清幽"
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "玫瑰",
                "Rosa",
                "Rose",
                "月季",
                "蔷薇科 蔷薇属",
                "无保护等级",
                "花色多样"
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "向日葵",
                "Helianthus annuus",
                "Sunflower",
                "葵花",
                "菊科 向日葵属",
                "无保护等级",
                "花盘大、向阳转动"
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "银杏",
                "Ginkgo biloba",
                "Ginkgo",
                "白果树",
                "银杏科 银杏属",
                "二级保护",
                "叶形独特、寿命极长"
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "仙人掌",
                "Cactaceae",
                "Cactus",
                "刺球",
                "仙人掌科 仙人掌属",
                "无保护等级",
                "耐旱、多刺"
        ));
        plantList.add(new PlantCard(
                R.drawable.plant_card_img,
                "樱花",
                "Cerasus",
                "Cherry Blossom",
                "日本樱",
                "蔷薇科 樱属",
                "无保护等级",
                "花期短、观赏性强"
        ));


        // 设置列表布局（1列）
        recyclerPlants.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // 设置适配器
        adapter = new PlantCardAdapter(getContext(), plantList);
        recyclerPlants.setAdapter(adapter);

        // 绑定左右滑动事件
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
             * @param viewHolder The ViewHolder which has been swiped by the user.
             * @param direction  The direction to which the ViewHolder is swiped. It is one of
             *                   {@link #UP}, {@link #DOWN},
             *                   {@link #LEFT} or {@link #RIGHT}. If your
             *                   {@link #getMovementFlags(RecyclerView, ViewHolder)}
             *                   method
             *                   returned relative flags instead of {@link #LEFT} / {@link #RIGHT};
             *                   `direction` will be relative as well. ({@link #START} or {@link
             *                   #END}).
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
             * @param c                 The canvas which RecyclerView is drawing its children
             * @param recyclerView      The RecyclerView to which ItemTouchHelper is attached to
             * @param viewHolder        The ViewHolder which is being interacted by the User or it was
             *                          interacted and simply animating to its original position
             * @param dX                The amount of horizontal displacement caused by user's action
             * @param dY                The amount of vertical displacement caused by user's action
             * @param actionState       The type of interaction on the View. Is either {@link
             *                          #ACTION_STATE_DRAG} or {@link #ACTION_STATE_SWIPE}.
             * @param isCurrentlyActive True if this view is currently being controlled by the user or
             *                          false it is simply animating back to its original state.
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

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerPlants);

        return view;
    }
}
