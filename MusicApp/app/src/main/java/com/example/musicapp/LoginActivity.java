package com.example.musicapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.Model.RegisterUser.RegisterUser;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserName,edPwd;
    SharedPreferences sharedPreferences;
    private CheckBox showPwd;
    private Call<RegisterUser> call;
    private Call<RegisterUser> call2;

    public interface GetRequest_RegisterUser{
        @POST("user/login")
        Call<RegisterUser> getCall(@Query("userName") String userName, @Query("passWord") String passWord);
    }

    public interface GetRequest_RegisterUser2{
        @GET("user/{userName}")
        Call<RegisterUser> getCall(@Path("userName") String userName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName = (EditText) findViewById(R.id.edUserName);
        edPwd = (EditText) findViewById(R.id.edPwd);
        showPwd = (CheckBox) findViewById(R.id.showPwd);
        sharedPreferences = getSharedPreferences("mSetting",MODE_PRIVATE);
    }
    public void login(View view){
        switch (view.getId()){
            case R.id.Login:
                String userName = edUserName.getText().toString();
                String userPwd = edPwd.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://47.107.232.78:8080/")//设置网络请求的Url地址
                        .addConverterFactory(GsonConverterFactory.create())//设置数据解析器
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                GetRequest_RegisterUser getRequest_registerUser = retrofit.create(GetRequest_RegisterUser.class);
                if(call != null){
                    call.cancel();
                }
                call = getRequest_registerUser.getCall(userName,userPwd);
                call.enqueue(new Callback<RegisterUser>() {
                    @Override
                    public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {
                        RegisterUser registerUser = response.body();
                        if(registerUser != null){
                            if(registerUser.getCode() == 200){
                                GetRequest_RegisterUser2 getRequest_registerUser2 = retrofit.create(GetRequest_RegisterUser2.class);
                                if(call2 != null){
                                    call2.cancel();
                                }
                                call2 = getRequest_registerUser2.getCall(userName);
                                call2.enqueue(new Callback<RegisterUser>() {
                                    @Override
                                    public void onResponse(Call<RegisterUser> call2, Response<RegisterUser> response2) {
                                        RegisterUser registerUser2 = response2.body();
                                        SharedPreferences preferences = getSharedPreferences("mSetting",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("userName",userName);
                                        editor.putString("userHeader",Uri.encode(registerUser2.getData().getImg(), "-![.:/,%?&=]"));
                                        editor.commit();
                                        EventBus.getDefault().post("User");
                                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    @Override
                                    public void onFailure(Call<RegisterUser> call, Throwable t) {

                                    }
                                });
                            }else{
                                Toast.makeText(LoginActivity.this,registerUser.getMsg(),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterUser> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"登录超时，请重新登录",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.register:
                startActivityForResult(new Intent(this,RegisterActivity.class),1001);
                break;
            case R.id.showPwd:
                if(showPwd.isChecked()) edPwd.setInputType(TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else edPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);//俩个条件一起写才起效
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2001){
            edUserName.setText(data.getStringExtra("userName"));
            edPwd.setText(data.getStringExtra("passWord"));
        }
    }
}
