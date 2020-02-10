package com.example.musicapp.Module;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.TabMeMenuDown;
import com.example.musicapp.R;

import java.util.List;

public class TabMeMenu extends FrameLayout {
    private Context mContext;
    private TextView itemTabMe_tvTitle;
    private ImageView itemTabMe_ivIncrease,itemTabMe_ivShare,itemTabMe_ivMore;
    private String strTitle;
    private boolean itemTabMe_isSelect;
    private RecyclerView itemTabMe_rvShow;
    private View mView;
    private List<Object> objectList;
    private ItemTabMeMenuAdapter adapter;

    //设置title文字
    public String getTitleText() {
        return strTitle;
    }
    public void setTitleText(String strTitle) {
        if(strTitle != null){
            this.strTitle = strTitle;
            itemTabMe_tvTitle.setText(strTitle);
        }
    }

    public List<Object> getObjectList() {
        return objectList;
    }
    public void setAdapter(List<Object> objectList) {
        this.objectList = objectList;
        adapter = new ItemTabMeMenuAdapter(mContext,objectList);
        itemTabMe_rvShow.setAdapter(adapter);
        MyLayoutManage manage = new MyLayoutManage(mContext,RecyclerView.VERTICAL,false);
        itemTabMe_rvShow.setLayoutManager(manage);
    }
    public ItemTabMeMenuAdapter getAdapter(){
        return adapter;
    }

    //获取itemTabMe_ivIncrease的对象
    public ImageView getItemTabMe_ivIncrease() {
        return itemTabMe_ivIncrease;
    }
    //获取itemTabMe_ivShare的对象
    public ImageView getItemTabMe_ivShare() {
        return itemTabMe_ivShare;
    }
    //获取itemTabMe_ivMore的对象
    public ImageView getItemTabMe_ivMore() {
        return itemTabMe_ivMore;
    }

    //设置是否选中，默认不选中
    public boolean isSelect(){
        return itemTabMe_isSelect;
    }
    public void setSelect(boolean itemTabMe_isSelect){
        this.itemTabMe_isSelect = itemTabMe_isSelect;
        itemTabMe_rvShow.setVisibility(itemTabMe_isSelect ? VISIBLE : GONE);
    }

    public TabMeMenu(Context context) {
        this(context,null);
    }

    public TabMeMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabMeMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.item_tabme_menu,this,true);
        itemTabMe_tvTitle = mView.findViewById(R.id.itemTabMe_tvTitle);
        itemTabMe_ivIncrease = mView.findViewById(R.id.itemTabMe_ivIncrease);
        itemTabMe_ivShare = mView.findViewById(R.id.itemTabMe_ivShare);
        itemTabMe_ivMore = mView.findViewById(R.id.itemTabMe_ivMore);
        itemTabMe_rvShow = mView.findViewById(R.id.itemTabMe_rvShow);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TabMeMenu);
        setTitleText(typedArray.getString(R.styleable.TabMeMenu_titleText));
        setSelect(typedArray.getBoolean(R.styleable.TabMeMenu_isSelect,false));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelect()){
                    itemTabMe_isSelect = false;
                }else{
                    itemTabMe_isSelect = true;
                }
                setSelect(itemTabMe_isSelect);
            }
        });
        typedArray.recycle();
    }

    class MyLayoutManage extends LinearLayoutManager{
        int totalHeight = 0;
        public MyLayoutManage(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
            detachAndScrapAttachedViews(recycler);
            calculateChildrenSite(recycler);
        }

        private void calculateChildrenSite(RecyclerView.Recycler recycler) {
            totalHeight = 0;
            int width;
            int height;
            for (int i = 0; i < getItemCount(); i++) {
                // 遍历Recycler中保存的View取出来
                View view = recycler.getViewForPosition(i);
                addView(view); // 因为刚刚进行了detach操作，所以现在可以重新添加
                measureChildWithMargins(view, 0, 0); // 通知测量view的margin值
                width = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
                height = getDecoratedMeasuredHeight(view);

                Rect mTmpRect = new Rect();
                //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
                calculateItemDecorationsForChild(view, mTmpRect);

                // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
                //包括ItemDecorator设置的距离。
                layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
                totalHeight += height;
            }
        }
    }

    public static class ItemTabMeMenuAdapter extends RecyclerView.Adapter<ItemTabMeMenuAdapter.MyViewHolder>{
        private LayoutInflater inflater;
        private List<Object> objects;
        private Context context;


        public ItemTabMeMenuAdapter(Context context,List<Object> objects){
            this.objects = objects;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_tabme_menudowm,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            TabMeMenuDown model = (TabMeMenuDown) objects.get(position);
            if(model != null){
                holder.itemTabMeDown_ivIcon.setImageResource(model.getIcon());
                holder.itemTabMeDown_tvName.setText(model.getName());
                holder.itemTabMeDown_tvSongNum.setText(String.format(context.getString(R.string.song_num),model.getSongNum()));
                if(model.getHintImg() != 0){
                    holder.itemTabMeDown_ivHintImg.setVisibility(VISIBLE);
                    holder.itemTabMeDown_ivHintImg.setImageResource(model.getHintImg());
                }else{
                    holder.itemTabMeDown_ivHintImg.setVisibility(GONE);
                }
                if(model.getSongDownNum() != 0){
                    holder.itemTabMeDown_tvSongDownNum.setVisibility(VISIBLE);
                    holder.itemTabMeDown_tvSongDownNum.setText(String.format(context.getString(R.string.song_down_num),model.getSongDownNum()));
                }else{
                    holder.itemTabMeDown_tvSongDownNum.setVisibility(GONE);
                }
            }
        }

        @Override
        public int getItemCount() {
            return objects.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView itemTabMeDown_ivIcon,itemTabMeDown_ivHintImg,itemTabMeDown_ivMore;
            TextView itemTabMeDown_tvName,itemTabMeDown_tvSongNum,itemTabMeDown_tvSongDownNum;
            private MyViewHolder(View view){
                super(view);
                itemTabMeDown_ivIcon = view.findViewById(R.id.itemTabMeDown_ivIcon);
                itemTabMeDown_tvName = view.findViewById(R.id.itemTabMeDown_tvName);
                itemTabMeDown_tvSongNum = view.findViewById(R.id.itemTabMeDown_tvSongNum);
                itemTabMeDown_tvSongDownNum = view.findViewById(R.id.itemTabMeDown_tvSongDownNum);
                itemTabMeDown_ivHintImg = view.findViewById(R.id.itemTabMeDown_ivHintImg);
                itemTabMeDown_ivMore = view.findViewById(R.id.itemTabMeDown_ivMore);
            }
        }
    }

}