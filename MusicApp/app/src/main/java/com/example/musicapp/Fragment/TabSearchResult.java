package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.DateBase.UsersTable;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.SearchSession;
import com.example.musicapp.Model.SongList.SongList;
import com.example.musicapp.Model.SongList.data;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service.MusicService;
import com.example.musicapp.Service.MyServiceConn;
import com.example.musicapp.Utils.SongListUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.musicapp.Adapter.BaseRecycleAdapter.FLOG_TABSEARCH;

public class TabSearchResult extends MyFragment {
    private EditText tabSearch_eTSearch;
    private Button tabSearch_btnSearch;
    private ImageView tabSearch_ivBack,tabSearch_ivCha,tabSearch_ivYuyin;
    private RecyclerView tabSearchResult_rvSearch;
    private TextView load;

    private String TAG = "tabSearchResult";
    private String searchText;
    private List<Song> songList;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private BaseRecycleAdapter<Song> adapter;
    private Context context;
    private  Call<SongList> call;

    public interface GetRequest_SearchSong{
        @GET("song/like/{searchName}")
        Call<SongList> getCall(@Path("searchName") String searchName, @Query("page") int page,@Query("size") int size);
    }
    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_search_result,null);
        EventBus.getDefault().register(this);
        context = getContext();
        tabSearch_eTSearch = view.findViewById(R.id.tabSearch_eTSearch);
        tabSearch_btnSearch = view.findViewById(R.id.tabSearch_btnSearch);
        tabSearch_ivBack = view.findViewById(R.id.tabSearch_ivBack);
        tabSearch_ivCha = view.findViewById(R.id.tabSearch_ivCha);
        tabSearch_ivYuyin = view.findViewById(R.id.tabSearch_ivYuyin);
        tabSearchResult_rvSearch = view.findViewById(R.id.tabSearchResult_rvSearch);
        load = view.findViewById(R.id.load);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(String string){
        String songPath = preferences.getString("songPath","");
        if(!songPath.equals("")){
            for(Song song : songList){
                if(song.getSongPath().equals(songPath)){
                    song.setSelect(true);
                }else{
                    song.setSelect(false);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData() {
        Bundle bundle = getArguments();
        preferences = getContext().getSharedPreferences("mSetting",Context.MODE_PRIVATE);
        editor = preferences.edit();
        if(bundle != null){
            searchText = bundle.getString("searchText","");

        }
        tabSearch_btnSearch.setOnClickListener(view -> {
            String searchText2 = tabSearch_eTSearch.getText().toString();
            Bundle bundle1 = new Bundle();
            bundle1.putString("searchText", searchText2);

            SearchSession searchSession = new SearchSession(searchText2);
            UsersTable usersTable = new UsersTable(getContext());
            SearchSession havaSearch = usersTable.getSearchSessionBySearchSong(searchText2);
            if (havaSearch == null && !searchText2.equals("")) {
                new UsersTable(getContext()).insertSearchSession(searchSession);
            }
            TabSearch.handler.sendMessage(new Message());
            if (!searchText2.equals("")) {
                searchText = searchText2;
                caoZuo();
            } else {
                Toast.makeText(getContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
            }

        });
        tabSearch_ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        tabSearch_ivCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabSearch_eTSearch.setText("");
            }
        });
        tabSearch_eTSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 > 0) {
                    tabSearch_ivCha.setVisibility(View.VISIBLE);
                    //
                } else {
                    tabSearch_ivCha.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        caoZuo();

    }
    private void caoZuo(){
        load.setVisibility(View.VISIBLE);
        tabSearchResult_rvSearch.setVisibility(View.GONE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.107.232.78:8080/")//设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //创建网络请求接口实例
        //对发送请求进行封装
        GetRequest_SearchSong getRequest_searchSong = retrofit.create(GetRequest_SearchSong.class);
        if(call != null){
            call.cancel();
        }
        call = getRequest_searchSong.getCall(searchText,0,10);
        call.enqueue(new Callback<SongList>() {
            //请求成功回调
            @Override
            public void onResponse(Call<SongList> call, Response<SongList> response) {
                songList = new ArrayList<>();
                SongList body = response.body();
                if(body != null){
                    List<data> dataList = body.getData();
                    for(int i = 0;i < dataList.size(); i++){
                        Song song = new Song();
                        data data = dataList.get(i);
                        song.setRowNum(i);
                        song.setSongName(data.getSongName());
                        song.setSinger(data.getSinger());
                        song.setFileName(data.getFileName());
                        song.setSongPath(Uri.encode(data.getPlayUrl(), "-![.:/,%?&=]"));
                        song.setSongHeader(Uri.encode(data.getImg(), "-![.:/,%?&=]"));
                        song.setSongMv(Uri.encode(data.getMv(), "-![.:/,%?&=]"));
                        song.setSongLyrics(data.getLyrics());
                        song.setCreateDate(data.getCreateDate());
                        songList.add(song);
                    }
                    adapter = new BaseRecycleAdapter<Song>(getContext(), songList,R.layout.item_tabme_danqu,FLOG_TABSEARCH) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, Song item, int position) {
                            holder.setText(R.id.songName,item.getSongName());
                            holder.setText(R.id.singer,item.getSinger());
                            holder.setVisibility(R.id.getLike,false);
                            holder.setSelect(R.id.show,R.id.hide,item.isSelect());
                            holder.setText(R.id.fileName,item.getFileName());
                            holder.setImageResource(R.id.header,item.getSongHeader());
                        }
                    };
                    tabSearchResult_rvSearch.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                    tabSearchResult_rvSearch.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Object object, int position) {
                            SongListUtil.onClickPlay(getContext(),object,songList);
                        }
                        @Override
                        public void onItemLongClick(Object object, int position) {
                            //UpDateData(position,object);
                        }
                    });
                    load.setVisibility(View.GONE);
                    tabSearchResult_rvSearch.setVisibility(View.VISIBLE);
                }
                EventBus.getDefault().post("");
            }
            //请求失败回调
            @Override
            public void onFailure(Call<SongList> call, Throwable t) {
                System.out.println("连接失败");
                load.setText("连接失败");
                load.setVisibility(View.VISIBLE);
                tabSearchResult_rvSearch.setVisibility(View.GONE);
                Log.v("连接失败","call:" + call + " throwable:" + t);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
