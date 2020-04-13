package com.example.musicapp.Utils;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.musicapp.Model.CommentList.CommentList;
import com.example.musicapp.Model.GeDan;
import com.example.musicapp.Model.GetListList.GetListList;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.SongList.SongList;
import com.example.musicapp.Model.SongList.data;
import com.example.musicapp.Model.TabMeMenuDown;
import com.example.musicapp.Model.VideoList.VideoList;
import com.example.musicapp.Model.XinGe;
import com.example.musicapp.Model.ZhiboList.ZhiboList;
import com.example.musicapp.R;

import org.greenrobot.eventbus.EventBus;

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

public class RetrofitUtil {
    public static int GetCommentListForGetLike = 1;
    public static int GetSongListForTime = 2;
    public static int GetListListForUser = 3;
    public static int GetSongListForList = 4;
    public static int GetCommentListForSong = 5;
    public static int GetListListForTime = 6;
    public static int GetVideoForTime = 7;
    public static int GetZhiboForTime = 8;

    public interface AnInterface{
        void setObject(Object object);
    }
    private AnInterface anInterface;
    private Context context;

    public void setAnInterface(AnInterface anInterface) {
        this.anInterface = anInterface;
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://47.107.232.78:8080/")//设置网络请求的Url地址
            .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public RetrofitUtil(Context context,int flog,String[] strings,int[] ints){
        this.context = context;
        if(flog == GetCommentListForGetLike){
            getCommentListForGetLike(ints[0],ints[1]);
        }else if(flog == GetSongListForTime){
            getSongListForTime(ints[0],ints[1]);
        }else if(flog == GetListListForUser){
            getListListForUser(strings[0]);
        }else if(flog == GetSongListForList){
            getSongListForList(strings[0],strings[1]);
        }else if(flog == GetCommentListForSong){
            getCommentListForSong(strings[0],ints[0],ints[1]);
        }else if(flog == GetListListForTime){
            getListListForTime(ints[0],ints[1]);
        }else if(flog == GetVideoForTime){
            getVideoListForTime(ints[0],ints[1]);
        }else if(flog == GetZhiboForTime){
            getZhiboListForTime(ints[0],ints[1]);
        }
    }

    //按时间搜索歌曲,返回SongList
    private Call<SongList> call_SearchSongListForTime;
    private interface GetRequest_SearchSongListForTime{
        @GET("song/all")
        Call<SongList> getCall(@Query("page") int page,@Query("size") int size);
    }
    public void getSongListForTime(int page,int size){
        GetRequest_SearchSongListForTime request = retrofit.create(GetRequest_SearchSongListForTime.class);
        if(call_SearchSongListForTime != null){
            call_SearchSongListForTime.cancel();
        }
        call_SearchSongListForTime = request.getCall(page, size);
        call_SearchSongListForTime.enqueue(new Callback<SongList>() {
            @Override
            public void onResponse(Call<SongList> call, Response<SongList> response) {
                SongList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<SongList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //
    /*private static Call<GetListList> getListListAllCall;
    private interface GetRequest_ListAll{
        @GET("songList/all")
        Call<GetListList> getCall();
    }
    public void getListListAll(int number){
        if(getListListAllCall != null){
            getListListAllCall.cancel();
        }
        getListListAllCall = getRequestListAll.getCall();
        getListListAllCall.enqueue(new Callback<GetListList>() {
            @Override
            public void onResponse(Call<GetListList> call, Response<GetListList> response) {
                GetListList getListList = response.body();
                if(getListList != null){
                    List<com.example.musicapp.Model.GetListList.data> dataList = getListList.getData();
                    if(dataList != null) {
                        List<GeDan> objects = new ArrayList<>();
                        for (com.example.musicapp.Model.GetListList.data data : dataList) {
                            objects.add(new GeDan(data.getTag(),data.getImg(),111,data.getListName(),"根据你的收藏的《Kaskade、Project 46 - LastChance》推荐",data.getUserName(),data.getTotal()));
                        }
                        Message message = new Message();
                        message.what = TabListListAll;
                        message.obj = objects.subList(0,number);
                        EventBus.getDefault().post(message);
                    }
                }
            }
            @Override
            public void onFailure(Call<GetListList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    //搜索用户歌单列表,返回GetListList
    private Call<GetListList> call_getListListForTime;
    private interface GetRequest_getListListForTime{
        @GET("songList/all")
        Call<GetListList> getCall(@Query("page") int page,@Query("size") int size);
    }
    public void getListListForTime(int page,int size){
        GetRequest_getListListForTime request = retrofit.create(GetRequest_getListListForTime.class);
        if(call_getListListForTime != null){
            call_getListListForTime.cancel();
        }
        call_getListListForTime = request.getCall(page, size);
        call_getListListForTime.enqueue(new Callback<GetListList>() {
            @Override
            public void onResponse(Call<GetListList> call, Response<GetListList> response) {
                GetListList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<GetListList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //搜索用户歌单列表,返回GetListList
    private Call<VideoList> call_getVideoListForTime;
    private interface GetRequest_getVideoListForTime{
        @GET("video/all")
        Call<VideoList> getCall(@Query("page") int page,@Query("size") int size);
    }
    public void getVideoListForTime(int page,int size){
        GetRequest_getVideoListForTime request = retrofit.create(GetRequest_getVideoListForTime.class);
        if(call_getVideoListForTime != null){
            call_getVideoListForTime.cancel();
        }
        call_getVideoListForTime = request.getCall(page, size);
        call_getVideoListForTime.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                VideoList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //搜索用户歌单列表,返回GetListList
    private Call<ZhiboList> call_getZhiboListForTime;
    private interface GetRequest_getZhiboListForTime{
        @GET("zhibo/all")
        Call<ZhiboList> getCall(@Query("page") int page,@Query("size") int size);
    }
    public void getZhiboListForTime(int page,int size){
        GetRequest_getZhiboListForTime request = retrofit.create(GetRequest_getZhiboListForTime.class);
        if(call_getZhiboListForTime != null){
            call_getZhiboListForTime.cancel();
        }
        call_getZhiboListForTime = request.getCall(page, size);
        call_getZhiboListForTime.enqueue(new Callback<ZhiboList>() {
            @Override
            public void onResponse(Call<ZhiboList> call, Response<ZhiboList> response) {
                ZhiboList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<ZhiboList> call, Throwable t) {
                Log.v("1111",t.getMessage());
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //搜索用户歌单列表,返回GetListList
    private Call<GetListList> call_getListListForUser;
    private interface GetRequest_getListListForUser{
        @GET("songList/{userName}")
        Call<GetListList> getCall(@Path("userName") String userName);
    }
    public void getListListForUser(String userName){
        GetRequest_getListListForUser request = retrofit.create(GetRequest_getListListForUser.class);
        if(call_getListListForUser != null){
            call_getListListForUser.cancel();
        }
        call_getListListForUser = request.getCall(userName);
        call_getListListForUser.enqueue(new Callback<GetListList>() {
            @Override
            public void onResponse(Call<GetListList> call, Response<GetListList> response) {
                GetListList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<GetListList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //搜索歌单歌曲列表,返回SongList
    private  Call<SongList> call_searchSongListForList;
    private interface GetRequest_searchSongListForList{
        @GET("songList")
        Call<SongList> getCall(@Query("userName") String userName, @Query("listName") String listName);
    }
    public void getSongListForList(String userName, String listName){
        GetRequest_searchSongListForList request = retrofit.create(GetRequest_searchSongListForList.class);
        if(call_searchSongListForList != null){
            call_searchSongListForList.cancel();
        }
        call_searchSongListForList = request.getCall(userName,listName);
        call_searchSongListForList.enqueue(new Callback<SongList>() {
            @Override
            public void onResponse(Call<SongList> call, Response<SongList> response) {
                SongList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<SongList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //根据歌曲名搜索评论列表,返回CommentList
    private Call<CommentList> call_searchCommentListForSong;
    private interface GetRequest_searchCommentListForSong{
        @GET("comment/{fileName}")
        Call<CommentList> getCall(@Path("fileName") String fileName,@Query("page") int page,@Query("size") int size);
    }
    public void getCommentListForSong(String fileName,int page,int size){
        GetRequest_searchCommentListForSong getRequestCommentList = retrofit.create(GetRequest_searchCommentListForSong.class);
        if(call_searchCommentListForSong != null){
            call_searchCommentListForSong.cancel();
        }
        call_searchCommentListForSong = getRequestCommentList.getCall(fileName,page,size);
        call_searchCommentListForSong.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                CommentList body = response.body();
                anInterface.setObject(body);
            }
            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //搜索优质评论,返回CommentList
    private interface GetRequest_CommentListForGetLike{
        @GET("comment/all")
        Call<CommentList> getCall(@Query("page") int page,@Query("size") int size);
    }
    private Call<CommentList> call_CommentListForGetLike;
    private void getCommentListForGetLike(int page,int size){
        GetRequest_CommentListForGetLike request = retrofit.create(GetRequest_CommentListForGetLike.class);
        if(call_CommentListForGetLike != null){
            call_CommentListForGetLike.cancel();
        }
        call_CommentListForGetLike = request.getCall(page,size);
        call_CommentListForGetLike.enqueue(new Callback<CommentList>() {
            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                CommentList commentLists = response.body();
                anInterface.setObject(commentLists);
            }
            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                Toast.makeText(context,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
