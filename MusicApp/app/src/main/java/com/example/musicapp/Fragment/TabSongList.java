package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.Foot;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongList.SongList;
import com.example.musicapp.Model.SongList.data;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.R;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;
import com.example.musicapp.Utils.AppBarStateChangeListener;
import com.example.musicapp.Utils.RetrofitUtil;
import com.example.musicapp.Utils.SongListUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABSONGLIST;
import static com.example.musicapp.Utils.RetrofitUtil.GetSongListForList;

public class TabSongList extends MyFragment {
    private RecyclerView listRecycleView;
    private List<Object> objectList;
    private List<Song> baseSongList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private BaseRecycleAdapter<Object> adapter;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsing_toolbar;
    private ImageView listImage;
    private int current = 0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        String songPath = preferences.getString("songPath","");
        if(!songPath.equals("")){
            for(Object object : objectList){
                if(object instanceof Song){
                    if(((Song) object).getSongPath().equals(songPath)){
                        ((Song) object).setSelect(true);
                    }else{
                        ((Song) object).setSelect(false);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_songlist,null);
        listRecycleView = view.findViewById(R.id.listRecycleView);
        toolbar = view.findViewById(R.id.toolbar);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        collapsing_toolbar = view.findViewById(R.id.collapsing_toolbar);
        listImage = view.findViewById(R.id.listImage);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return view;
    }


    @Override
    public void loadData() {
        EventBus.getDefault().register(this);
        objectList = new ArrayList<>();
        preferences = getActivity().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        editor = preferences.edit();

        Bundle bundle = getArguments();
        String listName = "";
        String userName = "";
        String listHeader = "";
        if(bundle != null){
            listName = bundle.getString("listName","");
            userName = bundle.getString("userName","");
            listHeader = bundle.getString("listImage","");
        }/*
        if(!listHeader.equals("")){
            Picasso.get().load(listHeader).error(R.drawable.include_default).into(listImage);
        }*/
        toolbar.setTitle(listName);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.white_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    collapsing_toolbar.setBackground(resource);
                }
            }
        };
        if(!listHeader.equals("")){
            Glide.with(getActivity()).load(listHeader).apply(RequestOptions.bitmapTransform(new BlurTransformation(24,3))).into(simpleTarget);
            Glide.with(getActivity()).load(listHeader).into(listImage);
        }
        collapsing_toolbar.setContentScrimColor(getResources().getColor(R.color.colorHover));
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state,int verticalOffset) {
                if( state == State.EXPANDED ) {
                    collapsing_toolbar.getForeground().setAlpha(0);
                }else if(state == State.COLLAPSED){
                }else {
                    collapsing_toolbar.getForeground().setAlpha((int)(Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange() * 122));
                    /*
                    if(Math.abs(verticalOffset) >= current + 20 || Math.abs(verticalOffset) <= current - 20){
                        collapsing_toolbar.setContentScrimColor(changeAlpha(getResources().getColor(R.color.color_transparent00), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
                        current = Math.abs(verticalOffset);
                    }*/
                }
            }
        });
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(), GetSongListForList, new String[]{userName, listName}, null);
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                SongList songList = (SongList) object;
                if(songList != null){
                    List<data> dataList = songList.getData();
                    baseSongList = new ArrayList<>();
                    if(dataList != null){
                        for(int i = 0; i < dataList.size();i++){
                            data data = dataList.get(i);
                            Song song = new Song();
                            song.setRowNum(i);
                            song.setSongName(data.getSongName());
                            song.setSinger(data.getSinger());
                            song.setFileName(data.getFileName());
                            song.setSongPath(Uri.encode(data.getPlayUrl(), "-![.:/,%?&=]"));
                            song.setSongHeader(Uri.encode(data.getImg(), "-![.:/,%?&=]"));
                            song.setSongMv(Uri.encode(data.getMv(), "-![.:/,%?&=]"));
                            song.setSongLyrics(data.getLyrics());
                            song.setCreateDate(data.getCreateDate());
                            song.setSelect(false);
                            baseSongList.add(song);
                        }
                    }
                    objectList.addAll(baseSongList);
                    objectList.add(new Foot());
                }
                adapter = new BaseRecycleAdapter<Object>(getContext(), objectList, R.layout.item_tabme_danqu, FLOG_TABSONGLIST) {
                    @Override
                    public void convert(BaseRecycleViewHolder holder, Object item, int position) {
                        if (item instanceof Foot) {
                            holder.setText(R.id.textView, "共" + (getItemCount() - 1) + "首歌曲");
                        } else if (item instanceof Song) {
                            holder.setText(R.id.songName, ((Song) item).getSongName());
                            holder.setText(R.id.singer, ((Song) item).getSinger());
                            holder.setSelect(R.id.show, R.id.hide, ((Song) item).isSelect());
                            holder.setText(R.id.fileName, ((Song) item).getSinger() + " - " + ((Song) item).getSongName());
                            holder.setImageResource(R.id.header, ((Song) item).getSongHeader());
                        }
                    }
                };
                listRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                listRecycleView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object object, int position) {
                        SongListUtil.onClickPlay(getContext(),object,baseSongList);
                    }

                    @Override
                    public void onItemLongClick(Object object, int position) {
                    }
                });
                EventBus.getDefault().post("");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
}
