package com.example.musicapp.Fragment;

import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Adapter.BaseRecycleAdapter;
import com.example.musicapp.Adapter.BaseRecycleViewHolder;
import com.example.musicapp.Factory.MyFragment;
import com.example.musicapp.Model.CommentList.CommentList;
import com.example.musicapp.Model.CommentList.data;
import com.example.musicapp.Model.PingLun;
import com.example.musicapp.Module.AutoLayoutManage;
import com.example.musicapp.Module.AutoViewPager;
import com.example.musicapp.R;
import com.example.musicapp.Utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.example.musicapp.Utils.RetrofitUtil.GetCommentListForGetLike;

public class TabListenPingLun extends MyFragment {
    private RecyclerView recyclerView;
    private BaseRecycleAdapter<data> adapter;

    @Override
    public View initView() {
        View view = View.inflate(getContext(), R.layout.fragment_listen_pinglun,null);
        recyclerView = view.findViewById(R.id.recycleView);

        return view;
    }

    @Override
    public void loadData() {
        RetrofitUtil retrofitUtil = new RetrofitUtil(getContext(),GetCommentListForGetLike,null,new int[]{0,10});
        retrofitUtil.setAnInterface(new RetrofitUtil.AnInterface() {
            @Override
            public void setObject(Object object) {
                CommentList commentList = (CommentList) object;
                if(commentList != null){
                    List<data> listData = commentList.getData();
                    adapter = new BaseRecycleAdapter<data>(getContext(),listData,R.layout.item_tablisten_pinlun) {
                        @Override
                        public void convert(BaseRecycleViewHolder holder, data item, int position) {
                            holder.setText(R.id.content,item.getCommentText());
                            holder.setText(R.id.songName,item.getFileName());
                            holder.setImageResource(R.id.songHeader,item.getSongImage());
                            holder.setText(R.id.author,item.getUserName());
                            holder.setImageResource(R.id.authorHeader,item.getUserHeader());
                            holder.setText(R.id.getLike,String.valueOf(item.getGetLike()));
                            holder.setText(R.id.comment,String.valueOf(item.getGetComment()));
                        }
                    };
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                    recyclerView.setAdapter(adapter);
                }
            }
        });
    }
}
