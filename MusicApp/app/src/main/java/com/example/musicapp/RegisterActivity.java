package com.example.musicapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.Model.RegisterUser.RegisterUser;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

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
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class RegisterActivity extends AppCompatActivity {


    public interface GetRequest_RegisterUser{
        @Multipart
        @POST("user")
        Call<RegisterUser> getCall(@Query("userName") String userName,@Query("passWord") String passWord,@Part MultipartBody.Part file);
    }

    private Call<RegisterUser> call;
    private EditText edUserName,edUserPwd,edRePwd;
    private boolean boo1,boo2;
    private ImageView userHeader;
    private String imgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edUserName = (EditText) findViewById(R.id.edUserName);
        edUserPwd = (EditText) findViewById(R.id.edUserPwd);
        edRePwd = (EditText) findViewById(R.id.edRePwd);
        userHeader = findViewById(R.id.userHeader);
        userHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(RegisterActivity.this,PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(true,120,120,1,1);
            }
        });
    }
    public void register(View view){
        String userName = edUserName.getText().toString();
        String userPwd = edUserPwd.getText().toString();
        String rePwd = edRePwd.getText().toString();
        boo1 = (userName.equals("") || userPwd.equals("") );
        boo2 = !userPwd.equals(rePwd);
        if(boo1){
            Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
        }else if(boo2){
            Toast.makeText(this,"确认密码不一致",Toast.LENGTH_SHORT).show();
        }else{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://47.107.232.78:8080/")//设置网络请求的Url地址
                    .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            GetRequest_RegisterUser getRequest_registerUser = retrofit.create(GetRequest_RegisterUser.class);
            if(call != null){
                call.cancel();
            }
            MultipartBody.Part body;
            if(imgPath != null && !imgPath.equals("")){
                File file =  new File(imgPath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            }else{
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
                body = MultipartBody.Part.createFormData("file", null, requestFile);
            }
            call = getRequest_registerUser.getCall(userName,userPwd,body);
            call.enqueue(new Callback<RegisterUser>() {
                @Override
                public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                    RegisterUser registerUser = response.body();
                    if(registerUser != null){
                        if(registerUser.getCode() == 200){
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            intent.putExtra("userName",userName);
                            intent.putExtra("passWord",userPwd);

                            Log.v("user",registerUser.toString());
                            setResult(2001,intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,registerUser.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterUser> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this,"网络超时，请稍后重试",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void btnReturn(View view){
        setResult(2002,new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
