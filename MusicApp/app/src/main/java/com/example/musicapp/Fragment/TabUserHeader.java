package com.example.musicapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.LoginActivity;
import com.example.musicapp.Model.RegisterUser.RegisterUser;
import com.example.musicapp.R;
import com.example.musicapp.RegisterActivity;
import com.squareup.picasso.Picasso;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class TabUserHeader extends MyFragment {
    private ImageView userHeader;
    private TextView userButton;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Call<RegisterUser> call;
    private String imgPath;


    public interface GetRequest_RegisterUser{
        @Multipart
        @PUT("user/update")
        Call<RegisterUser> getCall(@Query("userName") String userName, @Part MultipartBody.Part file);
    }

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_me_userheader,null);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userHeader = view.findViewById(R.id.userHeader);
        userButton = view.findViewById(R.id.userButton);
        preferences = getContext().getSharedPreferences("mSetting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        return view;
    }

    @Override
    public void loadData() {
        String header = preferences.getString("userHeader","");
        String userName = preferences.getString("userName","");

        if(!header.equals("")){
            Picasso.get().load(header).error(R.drawable.include_default).into(userHeader);
        }
        userHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(TabUserHeader.this,PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true,120,120,1,1);
            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgPath != null && !imgPath.equals("")){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://47.107.232.78:8080/")//设置网络请求的Url地址
                            .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    GetRequest_RegisterUser getRequest_registerUser = retrofit.create(GetRequest_RegisterUser.class);
                    if(call != null){
                        call.cancel();
                    }
                    File file =  new File(imgPath);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    call = getRequest_registerUser.getCall(userName,body);
                    call.enqueue(new Callback<RegisterUser>() {
                        @Override
                        public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                            RegisterUser registerUser = response.body();
                            editor.putString("userHeader",registerUser.getData().getImg());
                            editor.commit();
                            EventBus.getDefault().post("User");
                            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }

                        @Override
                        public void onFailure(Call<RegisterUser> call, Throwable t) {
                            Toast.makeText(getContext(),"修改超时，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(),"请选择图片",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
                if (pictureBean.isCut()) {
                    userHeader.setImageBitmap(BitmapFactory.decodeFile(pictureBean.getPath()));
                } else {
                    userHeader.setImageURI(pictureBean.getUri());
                }
                imgPath = pictureBean.getPath();
            }
        }
    }
}
