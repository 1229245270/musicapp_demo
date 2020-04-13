package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Model.CommentList.CommentList;
import com.example.musicapp.Model.CommentList.data;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.example.musicapp.Utils.RetrofitUtil.GetCommentListForSong;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView commentRecycleView;
    private ImageView back;
    private List<data> dataList;
    private BaseRecycleAdapter<data> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        EventBus.getDefault().register(this);
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");

        back = findViewById(R.id.back);
        commentRecycleView = findViewById(R.id.commentRecycleView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RetrofitUtil retrofitUtil = new RetrofitUtil(getApplicationContext(),GetCommentListForSong,new String[]{fileName},new int[]{0,10});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                CommentList commentList = (CommentList) object;
                if(commentList != null){
                    List<data> dataList = commentList.getData();
                    adapter = new BaseRecycleAdapter<data>(getApplicationContext(),dataList,R.layout.item_comment) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, data item, int position) {
                            holder.setImageResource(R.id.userHeader,item.getUserHeader());
                            holder.setText(R.id.userName,item.getUserName());
                            holder.setText(R.id.commentTime,item.getCreateDate() + "");
                            holder.setText(R.id.commentText,item.getCommentText());
                            holder.setText(R.id.getLike,String.valueOf(item.getGetLike()));
                            holder.setText(R.id.moreComment,item.getGetComment() + "回复>");
                        }
                    };
                    commentRecycleView.setAdapter(adapter);
                    commentRecycleView.setLayoutManager(new AutoLayoutManage(getApplicationContext(),RecyclerView.VERTICAL,false));
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
