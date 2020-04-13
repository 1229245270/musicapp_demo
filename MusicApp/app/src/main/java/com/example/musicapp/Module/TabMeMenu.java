package com.example.musicapp.Module;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class TabMeMenu extends FrameLayout {
    private Context mContext;
    private TextView itemTabMe_tvTitle;
    private ImageView itemTabMe_ivIncrease,itemTabMe_ivDaoru,itemTabMe_ivMore;
    private String strTitle;
    private boolean itemTabMe_isSelect;
    private RecyclerView itemTabMe_rvShow;
    private View mView;
    private List<TabMeMenuDown> tabMeMenuDowns;
    private ItemTabMeMenuAdapter adapter;
    private OnItemClickListen onItemClickListen;

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

    public void setOnClick(OnItemClickListen onItemClickListen){
        this.onItemClickListen = onItemClickListen;
    }
    public interface OnItemClickListen{
        void onItemClick(TabMeMenuDown tabMeMenuDown,int position);
    }

    public void setTabMeMenuDowns(List<TabMeMenuDown> tabMeMenuDowns) {
        this.tabMeMenuDowns = tabMeMenuDowns;
    }
    public List<TabMeMenuDown> getTabMeMenuDowns() {
        return tabMeMenuDowns;
    }
    public void setAdapter(List<TabMeMenuDown> tabMeMenuDowns) {
        this.tabMeMenuDowns = tabMeMenuDowns;
        adapter = new ItemTabMeMenuAdapter(mContext, this.tabMeMenuDowns);
        itemTabMe_rvShow.setAdapter(adapter);
        itemTabMe_rvShow.setLayoutManager(new AutoLayoutManage(mContext, LinearLayoutManager.VERTICAL, false));
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
        return itemTabMe_ivDaoru;
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
        itemTabMe_ivDaoru = mView.findViewById(R.id.itemTabMe_ivDaoru);
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


    public class ItemTabMeMenuAdapter extends RecyclerView.Adapter<ItemTabMeMenuAdapter.MyViewHolder>{
        private LayoutInflater inflater;
        private List<TabMeMenuDown> tabMeMenuDowns;
        private Context context;


        public ItemTabMeMenuAdapter(Context context,List<TabMeMenuDown> tabMeMenuDowns){
            this.tabMeMenuDowns = tabMeMenuDowns;
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
            TabMeMenuDown model = tabMeMenuDowns.get(position);
            if(model != null){
                Object img = model.getIcon();
                Log.v("model",model.toString());
                if(img instanceof String){
                    if(!model.getIcon().equals("")){
                        Picasso.get().load((String) model.getIcon()).error(R.drawable.include_default).into(holder.itemTabMeDown_ivIcon);
                    }
                }else if(img instanceof Integer){
                    holder.itemTabMeDown_ivIcon.setImageResource((Integer) img);
                }
                holder.itemTabMeDown_tvName.setText(model.getName());
                holder.itemTabMeDown_tvSongNum.setText(String.format(context.getString(R.string.song_num),model.getSongNum()));
                if(!model.getHint().equals("")){
                    holder.itemTabMeDown_tvHint.setVisibility(VISIBLE);
                    holder.itemTabMeDown_tvHint.setText(model.getHint());
                }else{
                    holder.itemTabMeDown_tvHint.setVisibility(GONE);
                }
                if(model.getSongDownNum() != 0){
                    holder.itemTabMeDown_tvSongDownNum.setVisibility(VISIBLE);
                    holder.itemTabMeDown_tvSongDownNum.setText(String.format(context.getString(R.string.song_down_num),model.getSongDownNum()));
                }else{
                    holder.itemTabMeDown_tvSongDownNum.setVisibility(GONE);
                }
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListen.onItemClick(model,position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return tabMeMenuDowns.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView itemTabMeDown_ivIcon,itemTabMeDown_ivHintImg,itemTabMeDown_ivMore;
            TextView itemTabMeDown_tvName,itemTabMeDown_tvSongNum,itemTabMeDown_tvSongDownNum,itemTabMeDown_tvHint;
            private MyViewHolder(View view){
                super(view);
                itemTabMeDown_ivIcon = view.findViewById(R.id.itemTabMeDown_ivIcon);
                itemTabMeDown_tvName = view.findViewById(R.id.itemTabMeDown_tvName);
                itemTabMeDown_tvSongNum = view.findViewById(R.id.itemTabMeDown_tvSongNum);
                itemTabMeDown_tvSongDownNum = view.findViewById(R.id.itemTabMeDown_tvSongDownNum);
                itemTabMeDown_tvHint = view.findViewById(R.id.itemTabMeDown_tvHint);
                itemTabMeDown_ivMore = view.findViewById(R.id.itemTabMeDown_ivMore);
            }
        }
    }

}
